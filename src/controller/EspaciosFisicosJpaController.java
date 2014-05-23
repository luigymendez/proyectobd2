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
import modelo.Sedes;
import modelo.Sustentaciones;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.EspaciosFisicos;

/**
 *
 * @author ChristianFabian
 */
public class EspaciosFisicosJpaController implements Serializable {

    public EspaciosFisicosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EspaciosFisicos espaciosFisicos) throws PreexistingEntityException, Exception {
        if (espaciosFisicos.getSustentacionesCollection() == null) {
            espaciosFisicos.setSustentacionesCollection(new ArrayList<Sustentaciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sedes sedesId = espaciosFisicos.getSedesId();
            if (sedesId != null) {
                sedesId = em.getReference(sedesId.getClass(), sedesId.getId());
                espaciosFisicos.setSedesId(sedesId);
            }
            Collection<Sustentaciones> attachedSustentacionesCollection = new ArrayList<Sustentaciones>();
            for (Sustentaciones sustentacionesCollectionSustentacionesToAttach : espaciosFisicos.getSustentacionesCollection()) {
                sustentacionesCollectionSustentacionesToAttach = em.getReference(sustentacionesCollectionSustentacionesToAttach.getClass(), sustentacionesCollectionSustentacionesToAttach.getId());
                attachedSustentacionesCollection.add(sustentacionesCollectionSustentacionesToAttach);
            }
            espaciosFisicos.setSustentacionesCollection(attachedSustentacionesCollection);
            em.persist(espaciosFisicos);
            if (sedesId != null) {
                sedesId.getEspaciosFisicosCollection().add(espaciosFisicos);
                sedesId = em.merge(sedesId);
            }
            for (Sustentaciones sustentacionesCollectionSustentaciones : espaciosFisicos.getSustentacionesCollection()) {
                EspaciosFisicos oldEspaciosFisicosIdOfSustentacionesCollectionSustentaciones = sustentacionesCollectionSustentaciones.getEspaciosFisicosId();
                sustentacionesCollectionSustentaciones.setEspaciosFisicosId(espaciosFisicos);
                sustentacionesCollectionSustentaciones = em.merge(sustentacionesCollectionSustentaciones);
                if (oldEspaciosFisicosIdOfSustentacionesCollectionSustentaciones != null) {
                    oldEspaciosFisicosIdOfSustentacionesCollectionSustentaciones.getSustentacionesCollection().remove(sustentacionesCollectionSustentaciones);
                    oldEspaciosFisicosIdOfSustentacionesCollectionSustentaciones = em.merge(oldEspaciosFisicosIdOfSustentacionesCollectionSustentaciones);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEspaciosFisicos(espaciosFisicos.getId()) != null) {
                throw new PreexistingEntityException("EspaciosFisicos " + espaciosFisicos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EspaciosFisicos espaciosFisicos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EspaciosFisicos persistentEspaciosFisicos = em.find(EspaciosFisicos.class, espaciosFisicos.getId());
            Sedes sedesIdOld = persistentEspaciosFisicos.getSedesId();
            Sedes sedesIdNew = espaciosFisicos.getSedesId();
            Collection<Sustentaciones> sustentacionesCollectionOld = persistentEspaciosFisicos.getSustentacionesCollection();
            Collection<Sustentaciones> sustentacionesCollectionNew = espaciosFisicos.getSustentacionesCollection();
            List<String> illegalOrphanMessages = null;
            for (Sustentaciones sustentacionesCollectionOldSustentaciones : sustentacionesCollectionOld) {
                if (!sustentacionesCollectionNew.contains(sustentacionesCollectionOldSustentaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sustentaciones " + sustentacionesCollectionOldSustentaciones + " since its espaciosFisicosId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sedesIdNew != null) {
                sedesIdNew = em.getReference(sedesIdNew.getClass(), sedesIdNew.getId());
                espaciosFisicos.setSedesId(sedesIdNew);
            }
            Collection<Sustentaciones> attachedSustentacionesCollectionNew = new ArrayList<Sustentaciones>();
            for (Sustentaciones sustentacionesCollectionNewSustentacionesToAttach : sustentacionesCollectionNew) {
                sustentacionesCollectionNewSustentacionesToAttach = em.getReference(sustentacionesCollectionNewSustentacionesToAttach.getClass(), sustentacionesCollectionNewSustentacionesToAttach.getId());
                attachedSustentacionesCollectionNew.add(sustentacionesCollectionNewSustentacionesToAttach);
            }
            sustentacionesCollectionNew = attachedSustentacionesCollectionNew;
            espaciosFisicos.setSustentacionesCollection(sustentacionesCollectionNew);
            espaciosFisicos = em.merge(espaciosFisicos);
            if (sedesIdOld != null && !sedesIdOld.equals(sedesIdNew)) {
                sedesIdOld.getEspaciosFisicosCollection().remove(espaciosFisicos);
                sedesIdOld = em.merge(sedesIdOld);
            }
            if (sedesIdNew != null && !sedesIdNew.equals(sedesIdOld)) {
                sedesIdNew.getEspaciosFisicosCollection().add(espaciosFisicos);
                sedesIdNew = em.merge(sedesIdNew);
            }
            for (Sustentaciones sustentacionesCollectionNewSustentaciones : sustentacionesCollectionNew) {
                if (!sustentacionesCollectionOld.contains(sustentacionesCollectionNewSustentaciones)) {
                    EspaciosFisicos oldEspaciosFisicosIdOfSustentacionesCollectionNewSustentaciones = sustentacionesCollectionNewSustentaciones.getEspaciosFisicosId();
                    sustentacionesCollectionNewSustentaciones.setEspaciosFisicosId(espaciosFisicos);
                    sustentacionesCollectionNewSustentaciones = em.merge(sustentacionesCollectionNewSustentaciones);
                    if (oldEspaciosFisicosIdOfSustentacionesCollectionNewSustentaciones != null && !oldEspaciosFisicosIdOfSustentacionesCollectionNewSustentaciones.equals(espaciosFisicos)) {
                        oldEspaciosFisicosIdOfSustentacionesCollectionNewSustentaciones.getSustentacionesCollection().remove(sustentacionesCollectionNewSustentaciones);
                        oldEspaciosFisicosIdOfSustentacionesCollectionNewSustentaciones = em.merge(oldEspaciosFisicosIdOfSustentacionesCollectionNewSustentaciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = espaciosFisicos.getId();
                if (findEspaciosFisicos(id) == null) {
                    throw new NonexistentEntityException("The espaciosFisicos with id " + id + " no longer exists.");
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
            EspaciosFisicos espaciosFisicos;
            try {
                espaciosFisicos = em.getReference(EspaciosFisicos.class, id);
                espaciosFisicos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The espaciosFisicos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Sustentaciones> sustentacionesCollectionOrphanCheck = espaciosFisicos.getSustentacionesCollection();
            for (Sustentaciones sustentacionesCollectionOrphanCheckSustentaciones : sustentacionesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EspaciosFisicos (" + espaciosFisicos + ") cannot be destroyed since the Sustentaciones " + sustentacionesCollectionOrphanCheckSustentaciones + " in its sustentacionesCollection field has a non-nullable espaciosFisicosId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sedes sedesId = espaciosFisicos.getSedesId();
            if (sedesId != null) {
                sedesId.getEspaciosFisicosCollection().remove(espaciosFisicos);
                sedesId = em.merge(sedesId);
            }
            em.remove(espaciosFisicos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EspaciosFisicos> findEspaciosFisicosEntities() {
        return findEspaciosFisicosEntities(true, -1, -1);
    }

    public List<EspaciosFisicos> findEspaciosFisicosEntities(int maxResults, int firstResult) {
        return findEspaciosFisicosEntities(false, maxResults, firstResult);
    }

    private List<EspaciosFisicos> findEspaciosFisicosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EspaciosFisicos.class));
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

    public EspaciosFisicos findEspaciosFisicos(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EspaciosFisicos.class, id);
        } finally {
            em.close();
        }
    }

    public int getEspaciosFisicosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EspaciosFisicos> rt = cq.from(EspaciosFisicos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
