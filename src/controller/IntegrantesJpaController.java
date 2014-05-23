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
import modelo.Ideas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Integrantes;

/**
 *
 * @author ChristianFabian
 */
public class IntegrantesJpaController implements Serializable {

    public IntegrantesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Integrantes integrantes) throws PreexistingEntityException, Exception {
        if (integrantes.getIdeasCollection() == null) {
            integrantes.setIdeasCollection(new ArrayList<Ideas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Ideas> attachedIdeasCollection = new ArrayList<Ideas>();
            for (Ideas ideasCollectionIdeasToAttach : integrantes.getIdeasCollection()) {
                ideasCollectionIdeasToAttach = em.getReference(ideasCollectionIdeasToAttach.getClass(), ideasCollectionIdeasToAttach.getId());
                attachedIdeasCollection.add(ideasCollectionIdeasToAttach);
            }
            integrantes.setIdeasCollection(attachedIdeasCollection);
            em.persist(integrantes);
            for (Ideas ideasCollectionIdeas : integrantes.getIdeasCollection()) {
                ideasCollectionIdeas.getIntegrantesCollection().add(integrantes);
                ideasCollectionIdeas = em.merge(ideasCollectionIdeas);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIntegrantes(integrantes.getIdentificacion()) != null) {
                throw new PreexistingEntityException("Integrantes " + integrantes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Integrantes integrantes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Integrantes persistentIntegrantes = em.find(Integrantes.class, integrantes.getIdentificacion());
            Collection<Ideas> ideasCollectionOld = persistentIntegrantes.getIdeasCollection();
            Collection<Ideas> ideasCollectionNew = integrantes.getIdeasCollection();
            Collection<Ideas> attachedIdeasCollectionNew = new ArrayList<Ideas>();
            for (Ideas ideasCollectionNewIdeasToAttach : ideasCollectionNew) {
                ideasCollectionNewIdeasToAttach = em.getReference(ideasCollectionNewIdeasToAttach.getClass(), ideasCollectionNewIdeasToAttach.getId());
                attachedIdeasCollectionNew.add(ideasCollectionNewIdeasToAttach);
            }
            ideasCollectionNew = attachedIdeasCollectionNew;
            integrantes.setIdeasCollection(ideasCollectionNew);
            integrantes = em.merge(integrantes);
            for (Ideas ideasCollectionOldIdeas : ideasCollectionOld) {
                if (!ideasCollectionNew.contains(ideasCollectionOldIdeas)) {
                    ideasCollectionOldIdeas.getIntegrantesCollection().remove(integrantes);
                    ideasCollectionOldIdeas = em.merge(ideasCollectionOldIdeas);
                }
            }
            for (Ideas ideasCollectionNewIdeas : ideasCollectionNew) {
                if (!ideasCollectionOld.contains(ideasCollectionNewIdeas)) {
                    ideasCollectionNewIdeas.getIntegrantesCollection().add(integrantes);
                    ideasCollectionNewIdeas = em.merge(ideasCollectionNewIdeas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = integrantes.getIdentificacion();
                if (findIntegrantes(id) == null) {
                    throw new NonexistentEntityException("The integrantes with id " + id + " no longer exists.");
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
            Integrantes integrantes;
            try {
                integrantes = em.getReference(Integrantes.class, id);
                integrantes.getIdentificacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The integrantes with id " + id + " no longer exists.", enfe);
            }
            Collection<Ideas> ideasCollection = integrantes.getIdeasCollection();
            for (Ideas ideasCollectionIdeas : ideasCollection) {
                ideasCollectionIdeas.getIntegrantesCollection().remove(integrantes);
                ideasCollectionIdeas = em.merge(ideasCollectionIdeas);
            }
            em.remove(integrantes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Integrantes> findIntegrantesEntities() {
        return findIntegrantesEntities(true, -1, -1);
    }

    public List<Integrantes> findIntegrantesEntities(int maxResults, int firstResult) {
        return findIntegrantesEntities(false, maxResults, firstResult);
    }

    private List<Integrantes> findIntegrantesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Integrantes.class));
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

    public Integrantes findIntegrantes(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Integrantes.class, id);
        } finally {
            em.close();
        }
    }

    public int getIntegrantesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Integrantes> rt = cq.from(Integrantes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
