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
import modelo.Estados;
import modelo.Entregas;
import modelo.ArchivosAdjuntos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Entregables;

/**
 *
 * @author ChristianFabian
 */
public class EntregablesJpaController implements Serializable {

    public EntregablesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Entregables entregables) throws PreexistingEntityException, Exception {
        if (entregables.getArchivosAdjuntosCollection() == null) {
            entregables.setArchivosAdjuntosCollection(new ArrayList<ArchivosAdjuntos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estados estadosId = entregables.getEstadosId();
            if (estadosId != null) {
                estadosId = em.getReference(estadosId.getClass(), estadosId.getId());
                entregables.setEstadosId(estadosId);
            }
            Entregas entregasId = entregables.getEntregasId();
            if (entregasId != null) {
                entregasId = em.getReference(entregasId.getClass(), entregasId.getId());
                entregables.setEntregasId(entregasId);
            }
            Collection<ArchivosAdjuntos> attachedArchivosAdjuntosCollection = new ArrayList<ArchivosAdjuntos>();
            for (ArchivosAdjuntos archivosAdjuntosCollectionArchivosAdjuntosToAttach : entregables.getArchivosAdjuntosCollection()) {
                archivosAdjuntosCollectionArchivosAdjuntosToAttach = em.getReference(archivosAdjuntosCollectionArchivosAdjuntosToAttach.getClass(), archivosAdjuntosCollectionArchivosAdjuntosToAttach.getId());
                attachedArchivosAdjuntosCollection.add(archivosAdjuntosCollectionArchivosAdjuntosToAttach);
            }
            entregables.setArchivosAdjuntosCollection(attachedArchivosAdjuntosCollection);
            em.persist(entregables);
            if (estadosId != null) {
                estadosId.getEntregablesCollection().add(entregables);
                estadosId = em.merge(estadosId);
            }
            if (entregasId != null) {
                entregasId.getEntregablesCollection().add(entregables);
                entregasId = em.merge(entregasId);
            }
            for (ArchivosAdjuntos archivosAdjuntosCollectionArchivosAdjuntos : entregables.getArchivosAdjuntosCollection()) {
                archivosAdjuntosCollectionArchivosAdjuntos.getEntregablesCollection().add(entregables);
                archivosAdjuntosCollectionArchivosAdjuntos = em.merge(archivosAdjuntosCollectionArchivosAdjuntos);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEntregables(entregables.getId()) != null) {
                throw new PreexistingEntityException("Entregables " + entregables + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Entregables entregables) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entregables persistentEntregables = em.find(Entregables.class, entregables.getId());
            Estados estadosIdOld = persistentEntregables.getEstadosId();
            Estados estadosIdNew = entregables.getEstadosId();
            Entregas entregasIdOld = persistentEntregables.getEntregasId();
            Entregas entregasIdNew = entregables.getEntregasId();
            Collection<ArchivosAdjuntos> archivosAdjuntosCollectionOld = persistentEntregables.getArchivosAdjuntosCollection();
            Collection<ArchivosAdjuntos> archivosAdjuntosCollectionNew = entregables.getArchivosAdjuntosCollection();
            if (estadosIdNew != null) {
                estadosIdNew = em.getReference(estadosIdNew.getClass(), estadosIdNew.getId());
                entregables.setEstadosId(estadosIdNew);
            }
            if (entregasIdNew != null) {
                entregasIdNew = em.getReference(entregasIdNew.getClass(), entregasIdNew.getId());
                entregables.setEntregasId(entregasIdNew);
            }
            Collection<ArchivosAdjuntos> attachedArchivosAdjuntosCollectionNew = new ArrayList<ArchivosAdjuntos>();
            for (ArchivosAdjuntos archivosAdjuntosCollectionNewArchivosAdjuntosToAttach : archivosAdjuntosCollectionNew) {
                archivosAdjuntosCollectionNewArchivosAdjuntosToAttach = em.getReference(archivosAdjuntosCollectionNewArchivosAdjuntosToAttach.getClass(), archivosAdjuntosCollectionNewArchivosAdjuntosToAttach.getId());
                attachedArchivosAdjuntosCollectionNew.add(archivosAdjuntosCollectionNewArchivosAdjuntosToAttach);
            }
            archivosAdjuntosCollectionNew = attachedArchivosAdjuntosCollectionNew;
            entregables.setArchivosAdjuntosCollection(archivosAdjuntosCollectionNew);
            entregables = em.merge(entregables);
            if (estadosIdOld != null && !estadosIdOld.equals(estadosIdNew)) {
                estadosIdOld.getEntregablesCollection().remove(entregables);
                estadosIdOld = em.merge(estadosIdOld);
            }
            if (estadosIdNew != null && !estadosIdNew.equals(estadosIdOld)) {
                estadosIdNew.getEntregablesCollection().add(entregables);
                estadosIdNew = em.merge(estadosIdNew);
            }
            if (entregasIdOld != null && !entregasIdOld.equals(entregasIdNew)) {
                entregasIdOld.getEntregablesCollection().remove(entregables);
                entregasIdOld = em.merge(entregasIdOld);
            }
            if (entregasIdNew != null && !entregasIdNew.equals(entregasIdOld)) {
                entregasIdNew.getEntregablesCollection().add(entregables);
                entregasIdNew = em.merge(entregasIdNew);
            }
            for (ArchivosAdjuntos archivosAdjuntosCollectionOldArchivosAdjuntos : archivosAdjuntosCollectionOld) {
                if (!archivosAdjuntosCollectionNew.contains(archivosAdjuntosCollectionOldArchivosAdjuntos)) {
                    archivosAdjuntosCollectionOldArchivosAdjuntos.getEntregablesCollection().remove(entregables);
                    archivosAdjuntosCollectionOldArchivosAdjuntos = em.merge(archivosAdjuntosCollectionOldArchivosAdjuntos);
                }
            }
            for (ArchivosAdjuntos archivosAdjuntosCollectionNewArchivosAdjuntos : archivosAdjuntosCollectionNew) {
                if (!archivosAdjuntosCollectionOld.contains(archivosAdjuntosCollectionNewArchivosAdjuntos)) {
                    archivosAdjuntosCollectionNewArchivosAdjuntos.getEntregablesCollection().add(entregables);
                    archivosAdjuntosCollectionNewArchivosAdjuntos = em.merge(archivosAdjuntosCollectionNewArchivosAdjuntos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = entregables.getId();
                if (findEntregables(id) == null) {
                    throw new NonexistentEntityException("The entregables with id " + id + " no longer exists.");
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
            Entregables entregables;
            try {
                entregables = em.getReference(Entregables.class, id);
                entregables.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entregables with id " + id + " no longer exists.", enfe);
            }
            Estados estadosId = entregables.getEstadosId();
            if (estadosId != null) {
                estadosId.getEntregablesCollection().remove(entregables);
                estadosId = em.merge(estadosId);
            }
            Entregas entregasId = entregables.getEntregasId();
            if (entregasId != null) {
                entregasId.getEntregablesCollection().remove(entregables);
                entregasId = em.merge(entregasId);
            }
            Collection<ArchivosAdjuntos> archivosAdjuntosCollection = entregables.getArchivosAdjuntosCollection();
            for (ArchivosAdjuntos archivosAdjuntosCollectionArchivosAdjuntos : archivosAdjuntosCollection) {
                archivosAdjuntosCollectionArchivosAdjuntos.getEntregablesCollection().remove(entregables);
                archivosAdjuntosCollectionArchivosAdjuntos = em.merge(archivosAdjuntosCollectionArchivosAdjuntos);
            }
            em.remove(entregables);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Entregables> findEntregablesEntities() {
        return findEntregablesEntities(true, -1, -1);
    }

    public List<Entregables> findEntregablesEntities(int maxResults, int firstResult) {
        return findEntregablesEntities(false, maxResults, firstResult);
    }

    private List<Entregables> findEntregablesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Entregables.class));
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

    public Entregables findEntregables(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Entregables.class, id);
        } finally {
            em.close();
        }
    }

    public int getEntregablesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Entregables> rt = cq.from(Entregables.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
