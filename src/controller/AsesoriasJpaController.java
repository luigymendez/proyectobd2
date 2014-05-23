/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Asesorias;
import modelo.Proyectos;

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
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyectos proyectosId = asesorias.getProyectosId();
            if (proyectosId != null) {
                proyectosId = em.getReference(proyectosId.getClass(), proyectosId.getId());
                asesorias.setProyectosId(proyectosId);
            }
            em.persist(asesorias);
            if (proyectosId != null) {
                proyectosId.getAsesoriasCollection().add(asesorias);
                proyectosId = em.merge(proyectosId);
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
            if (proyectosIdNew != null) {
                proyectosIdNew = em.getReference(proyectosIdNew.getClass(), proyectosIdNew.getId());
                asesorias.setProyectosId(proyectosIdNew);
            }
            asesorias = em.merge(asesorias);
            if (proyectosIdOld != null && !proyectosIdOld.equals(proyectosIdNew)) {
                proyectosIdOld.getAsesoriasCollection().remove(asesorias);
                proyectosIdOld = em.merge(proyectosIdOld);
            }
            if (proyectosIdNew != null && !proyectosIdNew.equals(proyectosIdOld)) {
                proyectosIdNew.getAsesoriasCollection().add(asesorias);
                proyectosIdNew = em.merge(proyectosIdNew);
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
