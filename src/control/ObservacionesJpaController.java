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
import modelo.Ideas;
import modelo.Observaciones;

/**
 *
 * @author ChristianFabian
 */
public class ObservacionesJpaController implements Serializable {

    public ObservacionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Observaciones observaciones) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ideas ideasId = observaciones.getIdeasId();
            if (ideasId != null) {
                ideasId = em.getReference(ideasId.getClass(), ideasId.getId());
                observaciones.setIdeasId(ideasId);
            }
            em.persist(observaciones);
            if (ideasId != null) {
                ideasId.getObservacionesCollection().add(observaciones);
                ideasId = em.merge(ideasId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findObservaciones(observaciones.getId()) != null) {
                throw new PreexistingEntityException("Observaciones " + observaciones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Observaciones observaciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Observaciones persistentObservaciones = em.find(Observaciones.class, observaciones.getId());
            Ideas ideasIdOld = persistentObservaciones.getIdeasId();
            Ideas ideasIdNew = observaciones.getIdeasId();
            if (ideasIdNew != null) {
                ideasIdNew = em.getReference(ideasIdNew.getClass(), ideasIdNew.getId());
                observaciones.setIdeasId(ideasIdNew);
            }
            observaciones = em.merge(observaciones);
            if (ideasIdOld != null && !ideasIdOld.equals(ideasIdNew)) {
                ideasIdOld.getObservacionesCollection().remove(observaciones);
                ideasIdOld = em.merge(ideasIdOld);
            }
            if (ideasIdNew != null && !ideasIdNew.equals(ideasIdOld)) {
                ideasIdNew.getObservacionesCollection().add(observaciones);
                ideasIdNew = em.merge(ideasIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = observaciones.getId();
                if (findObservaciones(id) == null) {
                    throw new NonexistentEntityException("The observaciones with id " + id + " no longer exists.");
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
            Observaciones observaciones;
            try {
                observaciones = em.getReference(Observaciones.class, id);
                observaciones.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The observaciones with id " + id + " no longer exists.", enfe);
            }
            Ideas ideasId = observaciones.getIdeasId();
            if (ideasId != null) {
                ideasId.getObservacionesCollection().remove(observaciones);
                ideasId = em.merge(ideasId);
            }
            em.remove(observaciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Observaciones> findObservacionesEntities() {
        return findObservacionesEntities(true, -1, -1);
    }

    public List<Observaciones> findObservacionesEntities(int maxResults, int firstResult) {
        return findObservacionesEntities(false, maxResults, firstResult);
    }

    private List<Observaciones> findObservacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Observaciones.class));
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

    public Observaciones findObservaciones(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Observaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getObservacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Observaciones> rt = cq.from(Observaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
