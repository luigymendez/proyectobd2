/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Sustentaciones;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Recursos;

/**
 *
 * @author ChristianFabian
 */
public class RecursosJpaController implements Serializable {

    public RecursosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Recursos recursos) throws PreexistingEntityException, Exception {
        if (recursos.getSustentacionesCollection() == null) {
            recursos.setSustentacionesCollection(new ArrayList<Sustentaciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Sustentaciones> attachedSustentacionesCollection = new ArrayList<Sustentaciones>();
            for (Sustentaciones sustentacionesCollectionSustentacionesToAttach : recursos.getSustentacionesCollection()) {
                sustentacionesCollectionSustentacionesToAttach = em.getReference(sustentacionesCollectionSustentacionesToAttach.getClass(), sustentacionesCollectionSustentacionesToAttach.getId());
                attachedSustentacionesCollection.add(sustentacionesCollectionSustentacionesToAttach);
            }
            recursos.setSustentacionesCollection(attachedSustentacionesCollection);
            em.persist(recursos);
            for (Sustentaciones sustentacionesCollectionSustentaciones : recursos.getSustentacionesCollection()) {
                sustentacionesCollectionSustentaciones.getRecursosCollection().add(recursos);
                sustentacionesCollectionSustentaciones = em.merge(sustentacionesCollectionSustentaciones);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRecursos(recursos.getId()) != null) {
                throw new PreexistingEntityException("Recursos " + recursos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Recursos recursos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Recursos persistentRecursos = em.find(Recursos.class, recursos.getId());
            Collection<Sustentaciones> sustentacionesCollectionOld = persistentRecursos.getSustentacionesCollection();
            Collection<Sustentaciones> sustentacionesCollectionNew = recursos.getSustentacionesCollection();
            Collection<Sustentaciones> attachedSustentacionesCollectionNew = new ArrayList<Sustentaciones>();
            for (Sustentaciones sustentacionesCollectionNewSustentacionesToAttach : sustentacionesCollectionNew) {
                sustentacionesCollectionNewSustentacionesToAttach = em.getReference(sustentacionesCollectionNewSustentacionesToAttach.getClass(), sustentacionesCollectionNewSustentacionesToAttach.getId());
                attachedSustentacionesCollectionNew.add(sustentacionesCollectionNewSustentacionesToAttach);
            }
            sustentacionesCollectionNew = attachedSustentacionesCollectionNew;
            recursos.setSustentacionesCollection(sustentacionesCollectionNew);
            recursos = em.merge(recursos);
            for (Sustentaciones sustentacionesCollectionOldSustentaciones : sustentacionesCollectionOld) {
                if (!sustentacionesCollectionNew.contains(sustentacionesCollectionOldSustentaciones)) {
                    sustentacionesCollectionOldSustentaciones.getRecursosCollection().remove(recursos);
                    sustentacionesCollectionOldSustentaciones = em.merge(sustentacionesCollectionOldSustentaciones);
                }
            }
            for (Sustentaciones sustentacionesCollectionNewSustentaciones : sustentacionesCollectionNew) {
                if (!sustentacionesCollectionOld.contains(sustentacionesCollectionNewSustentaciones)) {
                    sustentacionesCollectionNewSustentaciones.getRecursosCollection().add(recursos);
                    sustentacionesCollectionNewSustentaciones = em.merge(sustentacionesCollectionNewSustentaciones);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = recursos.getId();
                if (findRecursos(id) == null) {
                    throw new NonexistentEntityException("The recursos with id " + id + " no longer exists.");
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
            Recursos recursos;
            try {
                recursos = em.getReference(Recursos.class, id);
                recursos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recursos with id " + id + " no longer exists.", enfe);
            }
            Collection<Sustentaciones> sustentacionesCollection = recursos.getSustentacionesCollection();
            for (Sustentaciones sustentacionesCollectionSustentaciones : sustentacionesCollection) {
                sustentacionesCollectionSustentaciones.getRecursosCollection().remove(recursos);
                sustentacionesCollectionSustentaciones = em.merge(sustentacionesCollectionSustentaciones);
            }
            em.remove(recursos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Recursos> findRecursosEntities() {
        return findRecursosEntities(true, -1, -1);
    }

    public List<Recursos> findRecursosEntities(int maxResults, int firstResult) {
        return findRecursosEntities(false, maxResults, firstResult);
    }

    private List<Recursos> findRecursosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Recursos.class));
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

    public Recursos findRecursos(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Recursos.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecursosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Recursos> rt = cq.from(Recursos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
