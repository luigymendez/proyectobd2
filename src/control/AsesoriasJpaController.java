/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.NonexistentEntityException;
import control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Proyectos;
import modelo.Horarios;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Asesorias;

/**
 *
 * @author ChristianFabian
 */
public class AsesoriasJpaController implements Serializable {

    public AsesoriasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asesorias asesorias) throws PreexistingEntityException, Exception {
        if (asesorias.getHorariosCollection() == null) {
            asesorias.setHorariosCollection(new ArrayList<Horarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyectos proyectosId = asesorias.getProyectosId();
            if (proyectosId != null) {
                proyectosId = em.getReference(proyectosId.getClass(), proyectosId.getId());
                asesorias.setProyectosId(proyectosId);
            }
            Collection<Horarios> attachedHorariosCollection = new ArrayList<Horarios>();
            for (Horarios horariosCollectionHorariosToAttach : asesorias.getHorariosCollection()) {
                horariosCollectionHorariosToAttach = em.getReference(horariosCollectionHorariosToAttach.getClass(), horariosCollectionHorariosToAttach.getId());
                attachedHorariosCollection.add(horariosCollectionHorariosToAttach);
            }
            asesorias.setHorariosCollection(attachedHorariosCollection);
            em.persist(asesorias);
            if (proyectosId != null) {
                proyectosId.getAsesoriasCollection().add(asesorias);
                proyectosId = em.merge(proyectosId);
            }
            for (Horarios horariosCollectionHorarios : asesorias.getHorariosCollection()) {
                Asesorias oldAsesoriasIdOfHorariosCollectionHorarios = horariosCollectionHorarios.getAsesoriasId();
                horariosCollectionHorarios.setAsesoriasId(asesorias);
                horariosCollectionHorarios = em.merge(horariosCollectionHorarios);
                if (oldAsesoriasIdOfHorariosCollectionHorarios != null) {
                    oldAsesoriasIdOfHorariosCollectionHorarios.getHorariosCollection().remove(horariosCollectionHorarios);
                    oldAsesoriasIdOfHorariosCollectionHorarios = em.merge(oldAsesoriasIdOfHorariosCollectionHorarios);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAsesorias(asesorias.getId()) != null) {
                throw new PreexistingEntityException("Asesorias " + asesorias + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Asesorias asesorias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asesorias persistentAsesorias = em.find(Asesorias.class, asesorias.getId());
            Proyectos proyectosIdOld = persistentAsesorias.getProyectosId();
            Proyectos proyectosIdNew = asesorias.getProyectosId();
            Collection<Horarios> horariosCollectionOld = persistentAsesorias.getHorariosCollection();
            Collection<Horarios> horariosCollectionNew = asesorias.getHorariosCollection();
            if (proyectosIdNew != null) {
                proyectosIdNew = em.getReference(proyectosIdNew.getClass(), proyectosIdNew.getId());
                asesorias.setProyectosId(proyectosIdNew);
            }
            Collection<Horarios> attachedHorariosCollectionNew = new ArrayList<Horarios>();
            for (Horarios horariosCollectionNewHorariosToAttach : horariosCollectionNew) {
                horariosCollectionNewHorariosToAttach = em.getReference(horariosCollectionNewHorariosToAttach.getClass(), horariosCollectionNewHorariosToAttach.getId());
                attachedHorariosCollectionNew.add(horariosCollectionNewHorariosToAttach);
            }
            horariosCollectionNew = attachedHorariosCollectionNew;
            asesorias.setHorariosCollection(horariosCollectionNew);
            asesorias = em.merge(asesorias);
            if (proyectosIdOld != null && !proyectosIdOld.equals(proyectosIdNew)) {
                proyectosIdOld.getAsesoriasCollection().remove(asesorias);
                proyectosIdOld = em.merge(proyectosIdOld);
            }
            if (proyectosIdNew != null && !proyectosIdNew.equals(proyectosIdOld)) {
                proyectosIdNew.getAsesoriasCollection().add(asesorias);
                proyectosIdNew = em.merge(proyectosIdNew);
            }
            for (Horarios horariosCollectionOldHorarios : horariosCollectionOld) {
                if (!horariosCollectionNew.contains(horariosCollectionOldHorarios)) {
                    horariosCollectionOldHorarios.setAsesoriasId(null);
                    horariosCollectionOldHorarios = em.merge(horariosCollectionOldHorarios);
                }
            }
            for (Horarios horariosCollectionNewHorarios : horariosCollectionNew) {
                if (!horariosCollectionOld.contains(horariosCollectionNewHorarios)) {
                    Asesorias oldAsesoriasIdOfHorariosCollectionNewHorarios = horariosCollectionNewHorarios.getAsesoriasId();
                    horariosCollectionNewHorarios.setAsesoriasId(asesorias);
                    horariosCollectionNewHorarios = em.merge(horariosCollectionNewHorarios);
                    if (oldAsesoriasIdOfHorariosCollectionNewHorarios != null && !oldAsesoriasIdOfHorariosCollectionNewHorarios.equals(asesorias)) {
                        oldAsesoriasIdOfHorariosCollectionNewHorarios.getHorariosCollection().remove(horariosCollectionNewHorarios);
                        oldAsesoriasIdOfHorariosCollectionNewHorarios = em.merge(oldAsesoriasIdOfHorariosCollectionNewHorarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = asesorias.getId();
                if (findAsesorias(id) == null) {
                    throw new NonexistentEntityException("The asesorias with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asesorias asesorias;
            try {
                asesorias = em.getReference(Asesorias.class, id);
                asesorias.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asesorias with id " + id + " no longer exists.", enfe);
            }
            Proyectos proyectosId = asesorias.getProyectosId();
            if (proyectosId != null) {
                proyectosId.getAsesoriasCollection().remove(asesorias);
                proyectosId = em.merge(proyectosId);
            }
            Collection<Horarios> horariosCollection = asesorias.getHorariosCollection();
            for (Horarios horariosCollectionHorarios : horariosCollection) {
                horariosCollectionHorarios.setAsesoriasId(null);
                horariosCollectionHorarios = em.merge(horariosCollectionHorarios);
            }
            em.remove(asesorias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Asesorias> findAsesoriasEntities() {
        return findAsesoriasEntities(true, -1, -1);
    }

    public List<Asesorias> findAsesoriasEntities(int maxResults, int firstResult) {
        return findAsesoriasEntities(false, maxResults, firstResult);
    }

    private List<Asesorias> findAsesoriasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asesorias.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Asesorias findAsesorias(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asesorias.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsesoriasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asesorias> rt = cq.from(Asesorias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
