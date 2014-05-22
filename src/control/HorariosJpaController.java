/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.NonexistentEntityException;
import control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Proyectos;
import modelo.Asesorias;
import modelo.Horarios;

/**
 *
 * @author ChristianFabian
 */
public class HorariosJpaController implements Serializable {

    public HorariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Horarios horarios) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyectos proyectosId = horarios.getProyectosId();
            if (proyectosId != null) {
                proyectosId = em.getReference(proyectosId.getClass(), proyectosId.getId());
                horarios.setProyectosId(proyectosId);
            }
            Asesorias asesoriasId = horarios.getAsesoriasId();
            if (asesoriasId != null) {
                asesoriasId = em.getReference(asesoriasId.getClass(), asesoriasId.getId());
                horarios.setAsesoriasId(asesoriasId);
            }
            em.persist(horarios);
            if (proyectosId != null) {
                proyectosId.getHorariosCollection().add(horarios);
                proyectosId = em.merge(proyectosId);
            }
            if (asesoriasId != null) {
                asesoriasId.getHorariosCollection().add(horarios);
                asesoriasId = em.merge(asesoriasId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHorarios(horarios.getId()) != null) {
                throw new PreexistingEntityException("Horarios " + horarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Horarios horarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Horarios persistentHorarios = em.find(Horarios.class, horarios.getId());
            Proyectos proyectosIdOld = persistentHorarios.getProyectosId();
            Proyectos proyectosIdNew = horarios.getProyectosId();
            Asesorias asesoriasIdOld = persistentHorarios.getAsesoriasId();
            Asesorias asesoriasIdNew = horarios.getAsesoriasId();
            if (proyectosIdNew != null) {
                proyectosIdNew = em.getReference(proyectosIdNew.getClass(), proyectosIdNew.getId());
                horarios.setProyectosId(proyectosIdNew);
            }
            if (asesoriasIdNew != null) {
                asesoriasIdNew = em.getReference(asesoriasIdNew.getClass(), asesoriasIdNew.getId());
                horarios.setAsesoriasId(asesoriasIdNew);
            }
            horarios = em.merge(horarios);
            if (proyectosIdOld != null && !proyectosIdOld.equals(proyectosIdNew)) {
                proyectosIdOld.getHorariosCollection().remove(horarios);
                proyectosIdOld = em.merge(proyectosIdOld);
            }
            if (proyectosIdNew != null && !proyectosIdNew.equals(proyectosIdOld)) {
                proyectosIdNew.getHorariosCollection().add(horarios);
                proyectosIdNew = em.merge(proyectosIdNew);
            }
            if (asesoriasIdOld != null && !asesoriasIdOld.equals(asesoriasIdNew)) {
                asesoriasIdOld.getHorariosCollection().remove(horarios);
                asesoriasIdOld = em.merge(asesoriasIdOld);
            }
            if (asesoriasIdNew != null && !asesoriasIdNew.equals(asesoriasIdOld)) {
                asesoriasIdNew.getHorariosCollection().add(horarios);
                asesoriasIdNew = em.merge(asesoriasIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = horarios.getId();
                if (findHorarios(id) == null) {
                    throw new NonexistentEntityException("The horarios with id " + id + " no longer exists.");
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
            Horarios horarios;
            try {
                horarios = em.getReference(Horarios.class, id);
                horarios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The horarios with id " + id + " no longer exists.", enfe);
            }
            Proyectos proyectosId = horarios.getProyectosId();
            if (proyectosId != null) {
                proyectosId.getHorariosCollection().remove(horarios);
                proyectosId = em.merge(proyectosId);
            }
            Asesorias asesoriasId = horarios.getAsesoriasId();
            if (asesoriasId != null) {
                asesoriasId.getHorariosCollection().remove(horarios);
                asesoriasId = em.merge(asesoriasId);
            }
            em.remove(horarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Horarios> findHorariosEntities() {
        return findHorariosEntities(true, -1, -1);
    }

    public List<Horarios> findHorariosEntities(int maxResults, int firstResult) {
        return findHorariosEntities(false, maxResults, firstResult);
    }

    private List<Horarios> findHorariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Horarios.class));
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

    public Horarios findHorarios(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Horarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getHorariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Horarios> rt = cq.from(Horarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
