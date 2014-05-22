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
import modelo.Avances;
import modelo.ArchivosAdjuntos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.RevisionesAvance;

/**
 *
 * @author ChristianFabian
 */
public class RevisionesAvanceJpaController implements Serializable {

    public RevisionesAvanceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RevisionesAvance revisionesAvance) throws PreexistingEntityException, Exception {
        if (revisionesAvance.getArchivosAdjuntosCollection() == null) {
            revisionesAvance.setArchivosAdjuntosCollection(new ArrayList<ArchivosAdjuntos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Avances avancesId = revisionesAvance.getAvancesId();
            if (avancesId != null) {
                avancesId = em.getReference(avancesId.getClass(), avancesId.getId());
                revisionesAvance.setAvancesId(avancesId);
            }
            Collection<ArchivosAdjuntos> attachedArchivosAdjuntosCollection = new ArrayList<ArchivosAdjuntos>();
            for (ArchivosAdjuntos archivosAdjuntosCollectionArchivosAdjuntosToAttach : revisionesAvance.getArchivosAdjuntosCollection()) {
                archivosAdjuntosCollectionArchivosAdjuntosToAttach = em.getReference(archivosAdjuntosCollectionArchivosAdjuntosToAttach.getClass(), archivosAdjuntosCollectionArchivosAdjuntosToAttach.getId());
                attachedArchivosAdjuntosCollection.add(archivosAdjuntosCollectionArchivosAdjuntosToAttach);
            }
            revisionesAvance.setArchivosAdjuntosCollection(attachedArchivosAdjuntosCollection);
            em.persist(revisionesAvance);
            if (avancesId != null) {
                avancesId.getRevisionesAvanceCollection().add(revisionesAvance);
                avancesId = em.merge(avancesId);
            }
            for (ArchivosAdjuntos archivosAdjuntosCollectionArchivosAdjuntos : revisionesAvance.getArchivosAdjuntosCollection()) {
                archivosAdjuntosCollectionArchivosAdjuntos.getRevisionesAvanceCollection().add(revisionesAvance);
                archivosAdjuntosCollectionArchivosAdjuntos = em.merge(archivosAdjuntosCollectionArchivosAdjuntos);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRevisionesAvance(revisionesAvance.getId()) != null) {
                throw new PreexistingEntityException("RevisionesAvance " + revisionesAvance + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RevisionesAvance revisionesAvance) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RevisionesAvance persistentRevisionesAvance = em.find(RevisionesAvance.class, revisionesAvance.getId());
            Avances avancesIdOld = persistentRevisionesAvance.getAvancesId();
            Avances avancesIdNew = revisionesAvance.getAvancesId();
            Collection<ArchivosAdjuntos> archivosAdjuntosCollectionOld = persistentRevisionesAvance.getArchivosAdjuntosCollection();
            Collection<ArchivosAdjuntos> archivosAdjuntosCollectionNew = revisionesAvance.getArchivosAdjuntosCollection();
            if (avancesIdNew != null) {
                avancesIdNew = em.getReference(avancesIdNew.getClass(), avancesIdNew.getId());
                revisionesAvance.setAvancesId(avancesIdNew);
            }
            Collection<ArchivosAdjuntos> attachedArchivosAdjuntosCollectionNew = new ArrayList<ArchivosAdjuntos>();
            for (ArchivosAdjuntos archivosAdjuntosCollectionNewArchivosAdjuntosToAttach : archivosAdjuntosCollectionNew) {
                archivosAdjuntosCollectionNewArchivosAdjuntosToAttach = em.getReference(archivosAdjuntosCollectionNewArchivosAdjuntosToAttach.getClass(), archivosAdjuntosCollectionNewArchivosAdjuntosToAttach.getId());
                attachedArchivosAdjuntosCollectionNew.add(archivosAdjuntosCollectionNewArchivosAdjuntosToAttach);
            }
            archivosAdjuntosCollectionNew = attachedArchivosAdjuntosCollectionNew;
            revisionesAvance.setArchivosAdjuntosCollection(archivosAdjuntosCollectionNew);
            revisionesAvance = em.merge(revisionesAvance);
            if (avancesIdOld != null && !avancesIdOld.equals(avancesIdNew)) {
                avancesIdOld.getRevisionesAvanceCollection().remove(revisionesAvance);
                avancesIdOld = em.merge(avancesIdOld);
            }
            if (avancesIdNew != null && !avancesIdNew.equals(avancesIdOld)) {
                avancesIdNew.getRevisionesAvanceCollection().add(revisionesAvance);
                avancesIdNew = em.merge(avancesIdNew);
            }
            for (ArchivosAdjuntos archivosAdjuntosCollectionOldArchivosAdjuntos : archivosAdjuntosCollectionOld) {
                if (!archivosAdjuntosCollectionNew.contains(archivosAdjuntosCollectionOldArchivosAdjuntos)) {
                    archivosAdjuntosCollectionOldArchivosAdjuntos.getRevisionesAvanceCollection().remove(revisionesAvance);
                    archivosAdjuntosCollectionOldArchivosAdjuntos = em.merge(archivosAdjuntosCollectionOldArchivosAdjuntos);
                }
            }
            for (ArchivosAdjuntos archivosAdjuntosCollectionNewArchivosAdjuntos : archivosAdjuntosCollectionNew) {
                if (!archivosAdjuntosCollectionOld.contains(archivosAdjuntosCollectionNewArchivosAdjuntos)) {
                    archivosAdjuntosCollectionNewArchivosAdjuntos.getRevisionesAvanceCollection().add(revisionesAvance);
                    archivosAdjuntosCollectionNewArchivosAdjuntos = em.merge(archivosAdjuntosCollectionNewArchivosAdjuntos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = revisionesAvance.getId();
                if (findRevisionesAvance(id) == null) {
                    throw new NonexistentEntityException("The revisionesAvance with id " + id + " no longer exists.");
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
            RevisionesAvance revisionesAvance;
            try {
                revisionesAvance = em.getReference(RevisionesAvance.class, id);
                revisionesAvance.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The revisionesAvance with id " + id + " no longer exists.", enfe);
            }
            Avances avancesId = revisionesAvance.getAvancesId();
            if (avancesId != null) {
                avancesId.getRevisionesAvanceCollection().remove(revisionesAvance);
                avancesId = em.merge(avancesId);
            }
            Collection<ArchivosAdjuntos> archivosAdjuntosCollection = revisionesAvance.getArchivosAdjuntosCollection();
            for (ArchivosAdjuntos archivosAdjuntosCollectionArchivosAdjuntos : archivosAdjuntosCollection) {
                archivosAdjuntosCollectionArchivosAdjuntos.getRevisionesAvanceCollection().remove(revisionesAvance);
                archivosAdjuntosCollectionArchivosAdjuntos = em.merge(archivosAdjuntosCollectionArchivosAdjuntos);
            }
            em.remove(revisionesAvance);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RevisionesAvance> findRevisionesAvanceEntities() {
        return findRevisionesAvanceEntities(true, -1, -1);
    }

    public List<RevisionesAvance> findRevisionesAvanceEntities(int maxResults, int firstResult) {
        return findRevisionesAvanceEntities(false, maxResults, firstResult);
    }

    private List<RevisionesAvance> findRevisionesAvanceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RevisionesAvance.class));
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

    public RevisionesAvance findRevisionesAvance(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RevisionesAvance.class, id);
        } finally {
            em.close();
        }
    }

    public int getRevisionesAvanceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RevisionesAvance> rt = cq.from(RevisionesAvance.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
