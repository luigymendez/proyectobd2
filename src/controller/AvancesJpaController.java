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
import modelo.Propuestas;
import modelo.Anteproyecto;
import modelo.ArchivosAdjuntos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Avances;
import modelo.RevisionesAvance;

/**
 *
 * @author ChristianFabian
 */
public class AvancesJpaController implements Serializable {

    public AvancesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Avances avances) throws PreexistingEntityException, Exception {
        if (avances.getArchivosAdjuntosCollection() == null) {
            avances.setArchivosAdjuntosCollection(new ArrayList<ArchivosAdjuntos>());
        }
        if (avances.getRevisionesAvanceCollection() == null) {
            avances.setRevisionesAvanceCollection(new ArrayList<RevisionesAvance>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Propuestas propuestasId = avances.getPropuestasId();
            if (propuestasId != null) {
                propuestasId = em.getReference(propuestasId.getClass(), propuestasId.getId());
                avances.setPropuestasId(propuestasId);
            }
            Anteproyecto anteproyectoId = avances.getAnteproyectoId();
            if (anteproyectoId != null) {
                anteproyectoId = em.getReference(anteproyectoId.getClass(), anteproyectoId.getId());
                avances.setAnteproyectoId(anteproyectoId);
            }
            Collection<ArchivosAdjuntos> attachedArchivosAdjuntosCollection = new ArrayList<ArchivosAdjuntos>();
            for (ArchivosAdjuntos archivosAdjuntosCollectionArchivosAdjuntosToAttach : avances.getArchivosAdjuntosCollection()) {
                archivosAdjuntosCollectionArchivosAdjuntosToAttach = em.getReference(archivosAdjuntosCollectionArchivosAdjuntosToAttach.getClass(), archivosAdjuntosCollectionArchivosAdjuntosToAttach.getId());
                attachedArchivosAdjuntosCollection.add(archivosAdjuntosCollectionArchivosAdjuntosToAttach);
            }
            avances.setArchivosAdjuntosCollection(attachedArchivosAdjuntosCollection);
            Collection<RevisionesAvance> attachedRevisionesAvanceCollection = new ArrayList<RevisionesAvance>();
            for (RevisionesAvance revisionesAvanceCollectionRevisionesAvanceToAttach : avances.getRevisionesAvanceCollection()) {
                revisionesAvanceCollectionRevisionesAvanceToAttach = em.getReference(revisionesAvanceCollectionRevisionesAvanceToAttach.getClass(), revisionesAvanceCollectionRevisionesAvanceToAttach.getId());
                attachedRevisionesAvanceCollection.add(revisionesAvanceCollectionRevisionesAvanceToAttach);
            }
            avances.setRevisionesAvanceCollection(attachedRevisionesAvanceCollection);
            em.persist(avances);
            if (propuestasId != null) {
                propuestasId.getAvancesCollection().add(avances);
                propuestasId = em.merge(propuestasId);
            }
            if (anteproyectoId != null) {
                anteproyectoId.getAvancesCollection().add(avances);
                anteproyectoId = em.merge(anteproyectoId);
            }
            for (ArchivosAdjuntos archivosAdjuntosCollectionArchivosAdjuntos : avances.getArchivosAdjuntosCollection()) {
                archivosAdjuntosCollectionArchivosAdjuntos.getAvancesCollection().add(avances);
                archivosAdjuntosCollectionArchivosAdjuntos = em.merge(archivosAdjuntosCollectionArchivosAdjuntos);
            }
            for (RevisionesAvance revisionesAvanceCollectionRevisionesAvance : avances.getRevisionesAvanceCollection()) {
                Avances oldAvancesIdOfRevisionesAvanceCollectionRevisionesAvance = revisionesAvanceCollectionRevisionesAvance.getAvancesId();
                revisionesAvanceCollectionRevisionesAvance.setAvancesId(avances);
                revisionesAvanceCollectionRevisionesAvance = em.merge(revisionesAvanceCollectionRevisionesAvance);
                if (oldAvancesIdOfRevisionesAvanceCollectionRevisionesAvance != null) {
                    oldAvancesIdOfRevisionesAvanceCollectionRevisionesAvance.getRevisionesAvanceCollection().remove(revisionesAvanceCollectionRevisionesAvance);
                    oldAvancesIdOfRevisionesAvanceCollectionRevisionesAvance = em.merge(oldAvancesIdOfRevisionesAvanceCollectionRevisionesAvance);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAvances(avances.getId()) != null) {
                throw new PreexistingEntityException("Avances " + avances + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Avances avances) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Avances persistentAvances = em.find(Avances.class, avances.getId());
            Propuestas propuestasIdOld = persistentAvances.getPropuestasId();
            Propuestas propuestasIdNew = avances.getPropuestasId();
            Anteproyecto anteproyectoIdOld = persistentAvances.getAnteproyectoId();
            Anteproyecto anteproyectoIdNew = avances.getAnteproyectoId();
            Collection<ArchivosAdjuntos> archivosAdjuntosCollectionOld = persistentAvances.getArchivosAdjuntosCollection();
            Collection<ArchivosAdjuntos> archivosAdjuntosCollectionNew = avances.getArchivosAdjuntosCollection();
            Collection<RevisionesAvance> revisionesAvanceCollectionOld = persistentAvances.getRevisionesAvanceCollection();
            Collection<RevisionesAvance> revisionesAvanceCollectionNew = avances.getRevisionesAvanceCollection();
            List<String> illegalOrphanMessages = null;
            for (RevisionesAvance revisionesAvanceCollectionOldRevisionesAvance : revisionesAvanceCollectionOld) {
                if (!revisionesAvanceCollectionNew.contains(revisionesAvanceCollectionOldRevisionesAvance)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RevisionesAvance " + revisionesAvanceCollectionOldRevisionesAvance + " since its avancesId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (propuestasIdNew != null) {
                propuestasIdNew = em.getReference(propuestasIdNew.getClass(), propuestasIdNew.getId());
                avances.setPropuestasId(propuestasIdNew);
            }
            if (anteproyectoIdNew != null) {
                anteproyectoIdNew = em.getReference(anteproyectoIdNew.getClass(), anteproyectoIdNew.getId());
                avances.setAnteproyectoId(anteproyectoIdNew);
            }
            Collection<ArchivosAdjuntos> attachedArchivosAdjuntosCollectionNew = new ArrayList<ArchivosAdjuntos>();
            for (ArchivosAdjuntos archivosAdjuntosCollectionNewArchivosAdjuntosToAttach : archivosAdjuntosCollectionNew) {
                archivosAdjuntosCollectionNewArchivosAdjuntosToAttach = em.getReference(archivosAdjuntosCollectionNewArchivosAdjuntosToAttach.getClass(), archivosAdjuntosCollectionNewArchivosAdjuntosToAttach.getId());
                attachedArchivosAdjuntosCollectionNew.add(archivosAdjuntosCollectionNewArchivosAdjuntosToAttach);
            }
            archivosAdjuntosCollectionNew = attachedArchivosAdjuntosCollectionNew;
            avances.setArchivosAdjuntosCollection(archivosAdjuntosCollectionNew);
            Collection<RevisionesAvance> attachedRevisionesAvanceCollectionNew = new ArrayList<RevisionesAvance>();
            for (RevisionesAvance revisionesAvanceCollectionNewRevisionesAvanceToAttach : revisionesAvanceCollectionNew) {
                revisionesAvanceCollectionNewRevisionesAvanceToAttach = em.getReference(revisionesAvanceCollectionNewRevisionesAvanceToAttach.getClass(), revisionesAvanceCollectionNewRevisionesAvanceToAttach.getId());
                attachedRevisionesAvanceCollectionNew.add(revisionesAvanceCollectionNewRevisionesAvanceToAttach);
            }
            revisionesAvanceCollectionNew = attachedRevisionesAvanceCollectionNew;
            avances.setRevisionesAvanceCollection(revisionesAvanceCollectionNew);
            avances = em.merge(avances);
            if (propuestasIdOld != null && !propuestasIdOld.equals(propuestasIdNew)) {
                propuestasIdOld.getAvancesCollection().remove(avances);
                propuestasIdOld = em.merge(propuestasIdOld);
            }
            if (propuestasIdNew != null && !propuestasIdNew.equals(propuestasIdOld)) {
                propuestasIdNew.getAvancesCollection().add(avances);
                propuestasIdNew = em.merge(propuestasIdNew);
            }
            if (anteproyectoIdOld != null && !anteproyectoIdOld.equals(anteproyectoIdNew)) {
                anteproyectoIdOld.getAvancesCollection().remove(avances);
                anteproyectoIdOld = em.merge(anteproyectoIdOld);
            }
            if (anteproyectoIdNew != null && !anteproyectoIdNew.equals(anteproyectoIdOld)) {
                anteproyectoIdNew.getAvancesCollection().add(avances);
                anteproyectoIdNew = em.merge(anteproyectoIdNew);
            }
            for (ArchivosAdjuntos archivosAdjuntosCollectionOldArchivosAdjuntos : archivosAdjuntosCollectionOld) {
                if (!archivosAdjuntosCollectionNew.contains(archivosAdjuntosCollectionOldArchivosAdjuntos)) {
                    archivosAdjuntosCollectionOldArchivosAdjuntos.getAvancesCollection().remove(avances);
                    archivosAdjuntosCollectionOldArchivosAdjuntos = em.merge(archivosAdjuntosCollectionOldArchivosAdjuntos);
                }
            }
            for (ArchivosAdjuntos archivosAdjuntosCollectionNewArchivosAdjuntos : archivosAdjuntosCollectionNew) {
                if (!archivosAdjuntosCollectionOld.contains(archivosAdjuntosCollectionNewArchivosAdjuntos)) {
                    archivosAdjuntosCollectionNewArchivosAdjuntos.getAvancesCollection().add(avances);
                    archivosAdjuntosCollectionNewArchivosAdjuntos = em.merge(archivosAdjuntosCollectionNewArchivosAdjuntos);
                }
            }
            for (RevisionesAvance revisionesAvanceCollectionNewRevisionesAvance : revisionesAvanceCollectionNew) {
                if (!revisionesAvanceCollectionOld.contains(revisionesAvanceCollectionNewRevisionesAvance)) {
                    Avances oldAvancesIdOfRevisionesAvanceCollectionNewRevisionesAvance = revisionesAvanceCollectionNewRevisionesAvance.getAvancesId();
                    revisionesAvanceCollectionNewRevisionesAvance.setAvancesId(avances);
                    revisionesAvanceCollectionNewRevisionesAvance = em.merge(revisionesAvanceCollectionNewRevisionesAvance);
                    if (oldAvancesIdOfRevisionesAvanceCollectionNewRevisionesAvance != null && !oldAvancesIdOfRevisionesAvanceCollectionNewRevisionesAvance.equals(avances)) {
                        oldAvancesIdOfRevisionesAvanceCollectionNewRevisionesAvance.getRevisionesAvanceCollection().remove(revisionesAvanceCollectionNewRevisionesAvance);
                        oldAvancesIdOfRevisionesAvanceCollectionNewRevisionesAvance = em.merge(oldAvancesIdOfRevisionesAvanceCollectionNewRevisionesAvance);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = avances.getId();
                if (findAvances(id) == null) {
                    throw new NonexistentEntityException("The avances with id " + id + " no longer exists.");
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
            Avances avances;
            try {
                avances = em.getReference(Avances.class, id);
                avances.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avances with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<RevisionesAvance> revisionesAvanceCollectionOrphanCheck = avances.getRevisionesAvanceCollection();
            for (RevisionesAvance revisionesAvanceCollectionOrphanCheckRevisionesAvance : revisionesAvanceCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Avances (" + avances + ") cannot be destroyed since the RevisionesAvance " + revisionesAvanceCollectionOrphanCheckRevisionesAvance + " in its revisionesAvanceCollection field has a non-nullable avancesId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Propuestas propuestasId = avances.getPropuestasId();
            if (propuestasId != null) {
                propuestasId.getAvancesCollection().remove(avances);
                propuestasId = em.merge(propuestasId);
            }
            Anteproyecto anteproyectoId = avances.getAnteproyectoId();
            if (anteproyectoId != null) {
                anteproyectoId.getAvancesCollection().remove(avances);
                anteproyectoId = em.merge(anteproyectoId);
            }
            Collection<ArchivosAdjuntos> archivosAdjuntosCollection = avances.getArchivosAdjuntosCollection();
            for (ArchivosAdjuntos archivosAdjuntosCollectionArchivosAdjuntos : archivosAdjuntosCollection) {
                archivosAdjuntosCollectionArchivosAdjuntos.getAvancesCollection().remove(avances);
                archivosAdjuntosCollectionArchivosAdjuntos = em.merge(archivosAdjuntosCollectionArchivosAdjuntos);
            }
            em.remove(avances);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Avances> findAvancesEntities() {
        return findAvancesEntities(true, -1, -1);
    }

    public List<Avances> findAvancesEntities(int maxResults, int firstResult) {
        return findAvancesEntities(false, maxResults, firstResult);
    }

    private List<Avances> findAvancesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Avances.class));
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

    public Avances findAvances(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Avances.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvancesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Avances> rt = cq.from(Avances.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
