/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.IllegalOrphanException;
import control.exceptions.NonexistentEntityException;
import control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Proyectos;
import modelo.Entregables;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Entregas;

/**
 *
 * @author ChristianFabian
 */
public class EntregasJpaController implements Serializable {

    public EntregasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Entregas entregas) throws PreexistingEntityException, Exception {
        if (entregas.getEntregablesCollection() == null) {
            entregas.setEntregablesCollection(new ArrayList<Entregables>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyectos proyectosId = entregas.getProyectosId();
            if (proyectosId != null) {
                proyectosId = em.getReference(proyectosId.getClass(), proyectosId.getId());
                entregas.setProyectosId(proyectosId);
            }
            Collection<Entregables> attachedEntregablesCollection = new ArrayList<Entregables>();
            for (Entregables entregablesCollectionEntregablesToAttach : entregas.getEntregablesCollection()) {
                entregablesCollectionEntregablesToAttach = em.getReference(entregablesCollectionEntregablesToAttach.getClass(), entregablesCollectionEntregablesToAttach.getId());
                attachedEntregablesCollection.add(entregablesCollectionEntregablesToAttach);
            }
            entregas.setEntregablesCollection(attachedEntregablesCollection);
            em.persist(entregas);
            if (proyectosId != null) {
                proyectosId.getEntregasCollection().add(entregas);
                proyectosId = em.merge(proyectosId);
            }
            for (Entregables entregablesCollectionEntregables : entregas.getEntregablesCollection()) {
                Entregas oldEntregasIdOfEntregablesCollectionEntregables = entregablesCollectionEntregables.getEntregasId();
                entregablesCollectionEntregables.setEntregasId(entregas);
                entregablesCollectionEntregables = em.merge(entregablesCollectionEntregables);
                if (oldEntregasIdOfEntregablesCollectionEntregables != null) {
                    oldEntregasIdOfEntregablesCollectionEntregables.getEntregablesCollection().remove(entregablesCollectionEntregables);
                    oldEntregasIdOfEntregablesCollectionEntregables = em.merge(oldEntregasIdOfEntregablesCollectionEntregables);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEntregas(entregas.getId()) != null) {
                throw new PreexistingEntityException("Entregas " + entregas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Entregas entregas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entregas persistentEntregas = em.find(Entregas.class, entregas.getId());
            Proyectos proyectosIdOld = persistentEntregas.getProyectosId();
            Proyectos proyectosIdNew = entregas.getProyectosId();
            Collection<Entregables> entregablesCollectionOld = persistentEntregas.getEntregablesCollection();
            Collection<Entregables> entregablesCollectionNew = entregas.getEntregablesCollection();
            List<String> illegalOrphanMessages = null;
            for (Entregables entregablesCollectionOldEntregables : entregablesCollectionOld) {
                if (!entregablesCollectionNew.contains(entregablesCollectionOldEntregables)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Entregables " + entregablesCollectionOldEntregables + " since its entregasId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (proyectosIdNew != null) {
                proyectosIdNew = em.getReference(proyectosIdNew.getClass(), proyectosIdNew.getId());
                entregas.setProyectosId(proyectosIdNew);
            }
            Collection<Entregables> attachedEntregablesCollectionNew = new ArrayList<Entregables>();
            for (Entregables entregablesCollectionNewEntregablesToAttach : entregablesCollectionNew) {
                entregablesCollectionNewEntregablesToAttach = em.getReference(entregablesCollectionNewEntregablesToAttach.getClass(), entregablesCollectionNewEntregablesToAttach.getId());
                attachedEntregablesCollectionNew.add(entregablesCollectionNewEntregablesToAttach);
            }
            entregablesCollectionNew = attachedEntregablesCollectionNew;
            entregas.setEntregablesCollection(entregablesCollectionNew);
            entregas = em.merge(entregas);
            if (proyectosIdOld != null && !proyectosIdOld.equals(proyectosIdNew)) {
                proyectosIdOld.getEntregasCollection().remove(entregas);
                proyectosIdOld = em.merge(proyectosIdOld);
            }
            if (proyectosIdNew != null && !proyectosIdNew.equals(proyectosIdOld)) {
                proyectosIdNew.getEntregasCollection().add(entregas);
                proyectosIdNew = em.merge(proyectosIdNew);
            }
            for (Entregables entregablesCollectionNewEntregables : entregablesCollectionNew) {
                if (!entregablesCollectionOld.contains(entregablesCollectionNewEntregables)) {
                    Entregas oldEntregasIdOfEntregablesCollectionNewEntregables = entregablesCollectionNewEntregables.getEntregasId();
                    entregablesCollectionNewEntregables.setEntregasId(entregas);
                    entregablesCollectionNewEntregables = em.merge(entregablesCollectionNewEntregables);
                    if (oldEntregasIdOfEntregablesCollectionNewEntregables != null && !oldEntregasIdOfEntregablesCollectionNewEntregables.equals(entregas)) {
                        oldEntregasIdOfEntregablesCollectionNewEntregables.getEntregablesCollection().remove(entregablesCollectionNewEntregables);
                        oldEntregasIdOfEntregablesCollectionNewEntregables = em.merge(oldEntregasIdOfEntregablesCollectionNewEntregables);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = entregas.getId();
                if (findEntregas(id) == null) {
                    throw new NonexistentEntityException("The entregas with id " + id + " no longer exists.");
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
            Entregas entregas;
            try {
                entregas = em.getReference(Entregas.class, id);
                entregas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entregas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Entregables> entregablesCollectionOrphanCheck = entregas.getEntregablesCollection();
            for (Entregables entregablesCollectionOrphanCheckEntregables : entregablesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Entregas (" + entregas + ") cannot be destroyed since the Entregables " + entregablesCollectionOrphanCheckEntregables + " in its entregablesCollection field has a non-nullable entregasId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Proyectos proyectosId = entregas.getProyectosId();
            if (proyectosId != null) {
                proyectosId.getEntregasCollection().remove(entregas);
                proyectosId = em.merge(proyectosId);
            }
            em.remove(entregas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Entregas> findEntregasEntities() {
        return findEntregasEntities(true, -1, -1);
    }

    public List<Entregas> findEntregasEntities(int maxResults, int firstResult) {
        return findEntregasEntities(false, maxResults, firstResult);
    }

    private List<Entregas> findEntregasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Entregas.class));
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

    public Entregas findEntregas(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Entregas.class, id);
        } finally {
            em.close();
        }
    }

    public int getEntregasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Entregas> rt = cq.from(Entregas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
