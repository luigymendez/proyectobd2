/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.EspaciosFisicos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Sedes;

/**
 *
 * @author ChristianFabian
 */
public class SedesJpaController implements Serializable {

    public SedesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sedes sedes) throws PreexistingEntityException, Exception {
        if (sedes.getEspaciosFisicosCollection() == null) {
            sedes.setEspaciosFisicosCollection(new ArrayList<EspaciosFisicos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<EspaciosFisicos> attachedEspaciosFisicosCollection = new ArrayList<EspaciosFisicos>();
            for (EspaciosFisicos espaciosFisicosCollectionEspaciosFisicosToAttach : sedes.getEspaciosFisicosCollection()) {
                espaciosFisicosCollectionEspaciosFisicosToAttach = em.getReference(espaciosFisicosCollectionEspaciosFisicosToAttach.getClass(), espaciosFisicosCollectionEspaciosFisicosToAttach.getId());
                attachedEspaciosFisicosCollection.add(espaciosFisicosCollectionEspaciosFisicosToAttach);
            }
            sedes.setEspaciosFisicosCollection(attachedEspaciosFisicosCollection);
            em.persist(sedes);
            for (EspaciosFisicos espaciosFisicosCollectionEspaciosFisicos : sedes.getEspaciosFisicosCollection()) {
                Sedes oldSedesIdOfEspaciosFisicosCollectionEspaciosFisicos = espaciosFisicosCollectionEspaciosFisicos.getSedesId();
                espaciosFisicosCollectionEspaciosFisicos.setSedesId(sedes);
                espaciosFisicosCollectionEspaciosFisicos = em.merge(espaciosFisicosCollectionEspaciosFisicos);
                if (oldSedesIdOfEspaciosFisicosCollectionEspaciosFisicos != null) {
                    oldSedesIdOfEspaciosFisicosCollectionEspaciosFisicos.getEspaciosFisicosCollection().remove(espaciosFisicosCollectionEspaciosFisicos);
                    oldSedesIdOfEspaciosFisicosCollectionEspaciosFisicos = em.merge(oldSedesIdOfEspaciosFisicosCollectionEspaciosFisicos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSedes(sedes.getId()) != null) {
                throw new PreexistingEntityException("Sedes " + sedes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sedes sedes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sedes persistentSedes = em.find(Sedes.class, sedes.getId());
            Collection<EspaciosFisicos> espaciosFisicosCollectionOld = persistentSedes.getEspaciosFisicosCollection();
            Collection<EspaciosFisicos> espaciosFisicosCollectionNew = sedes.getEspaciosFisicosCollection();
            List<String> illegalOrphanMessages = null;
            for (EspaciosFisicos espaciosFisicosCollectionOldEspaciosFisicos : espaciosFisicosCollectionOld) {
                if (!espaciosFisicosCollectionNew.contains(espaciosFisicosCollectionOldEspaciosFisicos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EspaciosFisicos " + espaciosFisicosCollectionOldEspaciosFisicos + " since its sedesId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<EspaciosFisicos> attachedEspaciosFisicosCollectionNew = new ArrayList<EspaciosFisicos>();
            for (EspaciosFisicos espaciosFisicosCollectionNewEspaciosFisicosToAttach : espaciosFisicosCollectionNew) {
                espaciosFisicosCollectionNewEspaciosFisicosToAttach = em.getReference(espaciosFisicosCollectionNewEspaciosFisicosToAttach.getClass(), espaciosFisicosCollectionNewEspaciosFisicosToAttach.getId());
                attachedEspaciosFisicosCollectionNew.add(espaciosFisicosCollectionNewEspaciosFisicosToAttach);
            }
            espaciosFisicosCollectionNew = attachedEspaciosFisicosCollectionNew;
            sedes.setEspaciosFisicosCollection(espaciosFisicosCollectionNew);
            sedes = em.merge(sedes);
            for (EspaciosFisicos espaciosFisicosCollectionNewEspaciosFisicos : espaciosFisicosCollectionNew) {
                if (!espaciosFisicosCollectionOld.contains(espaciosFisicosCollectionNewEspaciosFisicos)) {
                    Sedes oldSedesIdOfEspaciosFisicosCollectionNewEspaciosFisicos = espaciosFisicosCollectionNewEspaciosFisicos.getSedesId();
                    espaciosFisicosCollectionNewEspaciosFisicos.setSedesId(sedes);
                    espaciosFisicosCollectionNewEspaciosFisicos = em.merge(espaciosFisicosCollectionNewEspaciosFisicos);
                    if (oldSedesIdOfEspaciosFisicosCollectionNewEspaciosFisicos != null && !oldSedesIdOfEspaciosFisicosCollectionNewEspaciosFisicos.equals(sedes)) {
                        oldSedesIdOfEspaciosFisicosCollectionNewEspaciosFisicos.getEspaciosFisicosCollection().remove(espaciosFisicosCollectionNewEspaciosFisicos);
                        oldSedesIdOfEspaciosFisicosCollectionNewEspaciosFisicos = em.merge(oldSedesIdOfEspaciosFisicosCollectionNewEspaciosFisicos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = sedes.getId();
                if (findSedes(id) == null) {
                    throw new NonexistentEntityException("The sedes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sedes sedes;
            try {
                sedes = em.getReference(Sedes.class, id);
                sedes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sedes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<EspaciosFisicos> espaciosFisicosCollectionOrphanCheck = sedes.getEspaciosFisicosCollection();
            for (EspaciosFisicos espaciosFisicosCollectionOrphanCheckEspaciosFisicos : espaciosFisicosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sedes (" + sedes + ") cannot be destroyed since the EspaciosFisicos " + espaciosFisicosCollectionOrphanCheckEspaciosFisicos + " in its espaciosFisicosCollection field has a non-nullable sedesId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sedes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sedes> findSedesEntities() {
        return findSedesEntities(true, -1, -1);
    }

    public List<Sedes> findSedesEntities(int maxResults, int firstResult) {
        return findSedesEntities(false, maxResults, firstResult);
    }

    private List<Sedes> findSedesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sedes.class));
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

    public Sedes findSedes(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sedes.class, id);
        } finally {
            em.close();
        }
    }

    public int getSedesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sedes> rt = cq.from(Sedes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
