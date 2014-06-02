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
import modelo.Documentos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import modelo.ArchivosAdjuntos;
import modelo.Avances;
import modelo.Entregas;
import modelo.Ideas;
import modelo.RevisionesAvance;
import modelo.Sustentaciones;

/**
 *
 * @author ChristianFabian
 */
public class ArchivosAdjuntosJpaController implements Serializable {

    public ArchivosAdjuntosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("SwingBDIIPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ArchivosAdjuntos archivosAdjuntos) throws PreexistingEntityException, Exception {
        if (archivosAdjuntos.getDocumentosCollection() == null) {
            archivosAdjuntos.setDocumentosCollection(new ArrayList<Documentos>());
        }
        if (archivosAdjuntos.getAvancesCollection() == null) {
            archivosAdjuntos.setAvancesCollection(new ArrayList<Avances>());
        }
        if (archivosAdjuntos.getIdeasCollection() == null) {
            archivosAdjuntos.setIdeasCollection(new ArrayList<Ideas>());
        }
        if (archivosAdjuntos.getRevisionesAvanceCollection() == null) {
            archivosAdjuntos.setRevisionesAvanceCollection(new ArrayList<RevisionesAvance>());
        }
        if (archivosAdjuntos.getSustentacionesCollection() == null) {
            archivosAdjuntos.setSustentacionesCollection(new ArrayList<Sustentaciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Documentos> attachedDocumentosCollection = new ArrayList<Documentos>();
            for (Documentos documentosCollectionDocumentosToAttach : archivosAdjuntos.getDocumentosCollection()) {
                documentosCollectionDocumentosToAttach = em.getReference(documentosCollectionDocumentosToAttach.getClass(), documentosCollectionDocumentosToAttach.getId());
                attachedDocumentosCollection.add(documentosCollectionDocumentosToAttach);
            }
            archivosAdjuntos.setDocumentosCollection(attachedDocumentosCollection);
            Collection<Avances> attachedAvancesCollection = new ArrayList<Avances>();
            for (Avances avancesCollectionAvancesToAttach : archivosAdjuntos.getAvancesCollection()) {
                avancesCollectionAvancesToAttach = em.getReference(avancesCollectionAvancesToAttach.getClass(), avancesCollectionAvancesToAttach.getId());
                attachedAvancesCollection.add(avancesCollectionAvancesToAttach);
            }
            archivosAdjuntos.setAvancesCollection(attachedAvancesCollection);
            Collection<Ideas> attachedIdeasCollection = new ArrayList<Ideas>();
            for (Ideas ideasCollectionIdeasToAttach : archivosAdjuntos.getIdeasCollection()) {
                ideasCollectionIdeasToAttach = em.getReference(ideasCollectionIdeasToAttach.getClass(), ideasCollectionIdeasToAttach.getId());
                attachedIdeasCollection.add(ideasCollectionIdeasToAttach);
            }
            archivosAdjuntos.setIdeasCollection(attachedIdeasCollection);
            Collection<RevisionesAvance> attachedRevisionesAvanceCollection = new ArrayList<RevisionesAvance>();
            for (RevisionesAvance revisionesAvanceCollectionRevisionesAvanceToAttach : archivosAdjuntos.getRevisionesAvanceCollection()) {
                revisionesAvanceCollectionRevisionesAvanceToAttach = em.getReference(revisionesAvanceCollectionRevisionesAvanceToAttach.getClass(), revisionesAvanceCollectionRevisionesAvanceToAttach.getId());
                attachedRevisionesAvanceCollection.add(revisionesAvanceCollectionRevisionesAvanceToAttach);
            }
            archivosAdjuntos.setRevisionesAvanceCollection(attachedRevisionesAvanceCollection);
            Collection<Sustentaciones> attachedSustentacionesCollection = new ArrayList<Sustentaciones>();
            for (Sustentaciones sustentacionesCollectionSustentacionesToAttach : archivosAdjuntos.getSustentacionesCollection()) {
                sustentacionesCollectionSustentacionesToAttach = em.getReference(sustentacionesCollectionSustentacionesToAttach.getClass(), sustentacionesCollectionSustentacionesToAttach.getId());
                attachedSustentacionesCollection.add(sustentacionesCollectionSustentacionesToAttach);
            }
            archivosAdjuntos.setSustentacionesCollection(attachedSustentacionesCollection);
            em.persist(archivosAdjuntos);
            for (Documentos documentosCollectionDocumentos : archivosAdjuntos.getDocumentosCollection()) {
                documentosCollectionDocumentos.getArchivosAdjuntosCollection().add(archivosAdjuntos);
                documentosCollectionDocumentos = em.merge(documentosCollectionDocumentos);
            }
            for (Avances avancesCollectionAvances : archivosAdjuntos.getAvancesCollection()) {
                avancesCollectionAvances.getArchivosAdjuntosCollection().add(archivosAdjuntos);
                avancesCollectionAvances = em.merge(avancesCollectionAvances);
            }
            for (Ideas ideasCollectionIdeas : archivosAdjuntos.getIdeasCollection()) {
                ideasCollectionIdeas.getArchivosAdjuntosCollection().add(archivosAdjuntos);
                ideasCollectionIdeas = em.merge(ideasCollectionIdeas);
            }
            for (RevisionesAvance revisionesAvanceCollectionRevisionesAvance : archivosAdjuntos.getRevisionesAvanceCollection()) {
                revisionesAvanceCollectionRevisionesAvance.getArchivosAdjuntosCollection().add(archivosAdjuntos);
                revisionesAvanceCollectionRevisionesAvance = em.merge(revisionesAvanceCollectionRevisionesAvance);
            }
            for (Sustentaciones sustentacionesCollectionSustentaciones : archivosAdjuntos.getSustentacionesCollection()) {
                ArchivosAdjuntos oldArchivosAdjuntosIdOfSustentacionesCollectionSustentaciones = sustentacionesCollectionSustentaciones.getArchivosAdjuntosId();
                sustentacionesCollectionSustentaciones.setArchivosAdjuntosId(archivosAdjuntos);
                sustentacionesCollectionSustentaciones = em.merge(sustentacionesCollectionSustentaciones);
                if (oldArchivosAdjuntosIdOfSustentacionesCollectionSustentaciones != null) {
                    oldArchivosAdjuntosIdOfSustentacionesCollectionSustentaciones.getSustentacionesCollection().remove(sustentacionesCollectionSustentaciones);
                    oldArchivosAdjuntosIdOfSustentacionesCollectionSustentaciones = em.merge(oldArchivosAdjuntosIdOfSustentacionesCollectionSustentaciones);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findArchivosAdjuntos(archivosAdjuntos.getId()) != null) {
                throw new PreexistingEntityException("ArchivosAdjuntos " + archivosAdjuntos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ArchivosAdjuntos archivosAdjuntos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ArchivosAdjuntos persistentArchivosAdjuntos = em.find(ArchivosAdjuntos.class, archivosAdjuntos.getId());
            Collection<Documentos> documentosCollectionOld = persistentArchivosAdjuntos.getDocumentosCollection();
            Collection<Documentos> documentosCollectionNew = archivosAdjuntos.getDocumentosCollection();
            Collection<Avances> avancesCollectionOld = persistentArchivosAdjuntos.getAvancesCollection();
            Collection<Avances> avancesCollectionNew = archivosAdjuntos.getAvancesCollection();
            Collection<Ideas> ideasCollectionOld = persistentArchivosAdjuntos.getIdeasCollection();
            Collection<Ideas> ideasCollectionNew = archivosAdjuntos.getIdeasCollection();
            Collection<RevisionesAvance> revisionesAvanceCollectionOld = persistentArchivosAdjuntos.getRevisionesAvanceCollection();
            Collection<RevisionesAvance> revisionesAvanceCollectionNew = archivosAdjuntos.getRevisionesAvanceCollection();
            Collection<Sustentaciones> sustentacionesCollectionOld = persistentArchivosAdjuntos.getSustentacionesCollection();
            Collection<Sustentaciones> sustentacionesCollectionNew = archivosAdjuntos.getSustentacionesCollection();
            Collection<Documentos> attachedDocumentosCollectionNew = new ArrayList<Documentos>();
            for (Documentos documentosCollectionNewDocumentosToAttach : documentosCollectionNew) {
                documentosCollectionNewDocumentosToAttach = em.getReference(documentosCollectionNewDocumentosToAttach.getClass(), documentosCollectionNewDocumentosToAttach.getId());
                attachedDocumentosCollectionNew.add(documentosCollectionNewDocumentosToAttach);
            }
            documentosCollectionNew = attachedDocumentosCollectionNew;
            archivosAdjuntos.setDocumentosCollection(documentosCollectionNew);
            Collection<Avances> attachedAvancesCollectionNew = new ArrayList<Avances>();
            for (Avances avancesCollectionNewAvancesToAttach : avancesCollectionNew) {
                avancesCollectionNewAvancesToAttach = em.getReference(avancesCollectionNewAvancesToAttach.getClass(), avancesCollectionNewAvancesToAttach.getId());
                attachedAvancesCollectionNew.add(avancesCollectionNewAvancesToAttach);
            }
            avancesCollectionNew = attachedAvancesCollectionNew;
            archivosAdjuntos.setAvancesCollection(avancesCollectionNew);
            Collection<Ideas> attachedIdeasCollectionNew = new ArrayList<Ideas>();
            for (Ideas ideasCollectionNewIdeasToAttach : ideasCollectionNew) {
                ideasCollectionNewIdeasToAttach = em.getReference(ideasCollectionNewIdeasToAttach.getClass(), ideasCollectionNewIdeasToAttach.getId());
                attachedIdeasCollectionNew.add(ideasCollectionNewIdeasToAttach);
            }
            ideasCollectionNew = attachedIdeasCollectionNew;
            archivosAdjuntos.setIdeasCollection(ideasCollectionNew);
            Collection<RevisionesAvance> attachedRevisionesAvanceCollectionNew = new ArrayList<RevisionesAvance>();
            for (RevisionesAvance revisionesAvanceCollectionNewRevisionesAvanceToAttach : revisionesAvanceCollectionNew) {
                revisionesAvanceCollectionNewRevisionesAvanceToAttach = em.getReference(revisionesAvanceCollectionNewRevisionesAvanceToAttach.getClass(), revisionesAvanceCollectionNewRevisionesAvanceToAttach.getId());
                attachedRevisionesAvanceCollectionNew.add(revisionesAvanceCollectionNewRevisionesAvanceToAttach);
            }
            revisionesAvanceCollectionNew = attachedRevisionesAvanceCollectionNew;
            archivosAdjuntos.setRevisionesAvanceCollection(revisionesAvanceCollectionNew);
            Collection<Sustentaciones> attachedSustentacionesCollectionNew = new ArrayList<Sustentaciones>();
            for (Sustentaciones sustentacionesCollectionNewSustentacionesToAttach : sustentacionesCollectionNew) {
                sustentacionesCollectionNewSustentacionesToAttach = em.getReference(sustentacionesCollectionNewSustentacionesToAttach.getClass(), sustentacionesCollectionNewSustentacionesToAttach.getId());
                attachedSustentacionesCollectionNew.add(sustentacionesCollectionNewSustentacionesToAttach);
            }
            sustentacionesCollectionNew = attachedSustentacionesCollectionNew;
            archivosAdjuntos.setSustentacionesCollection(sustentacionesCollectionNew);
            archivosAdjuntos = em.merge(archivosAdjuntos);
            for (Documentos documentosCollectionOldDocumentos : documentosCollectionOld) {
                if (!documentosCollectionNew.contains(documentosCollectionOldDocumentos)) {
                    documentosCollectionOldDocumentos.getArchivosAdjuntosCollection().remove(archivosAdjuntos);
                    documentosCollectionOldDocumentos = em.merge(documentosCollectionOldDocumentos);
                }
            }
            for (Documentos documentosCollectionNewDocumentos : documentosCollectionNew) {
                if (!documentosCollectionOld.contains(documentosCollectionNewDocumentos)) {
                    documentosCollectionNewDocumentos.getArchivosAdjuntosCollection().add(archivosAdjuntos);
                    documentosCollectionNewDocumentos = em.merge(documentosCollectionNewDocumentos);
                }
            }
            for (Avances avancesCollectionOldAvances : avancesCollectionOld) {
                if (!avancesCollectionNew.contains(avancesCollectionOldAvances)) {
                    avancesCollectionOldAvances.getArchivosAdjuntosCollection().remove(archivosAdjuntos);
                    avancesCollectionOldAvances = em.merge(avancesCollectionOldAvances);
                }
            }
            for (Avances avancesCollectionNewAvances : avancesCollectionNew) {
                if (!avancesCollectionOld.contains(avancesCollectionNewAvances)) {
                    avancesCollectionNewAvances.getArchivosAdjuntosCollection().add(archivosAdjuntos);
                    avancesCollectionNewAvances = em.merge(avancesCollectionNewAvances);
                }
            }
            for (Ideas ideasCollectionOldIdeas : ideasCollectionOld) {
                if (!ideasCollectionNew.contains(ideasCollectionOldIdeas)) {
                    ideasCollectionOldIdeas.getArchivosAdjuntosCollection().remove(archivosAdjuntos);
                    ideasCollectionOldIdeas = em.merge(ideasCollectionOldIdeas);
                }
            }
            for (Ideas ideasCollectionNewIdeas : ideasCollectionNew) {
                if (!ideasCollectionOld.contains(ideasCollectionNewIdeas)) {
                    ideasCollectionNewIdeas.getArchivosAdjuntosCollection().add(archivosAdjuntos);
                    ideasCollectionNewIdeas = em.merge(ideasCollectionNewIdeas);
                }
            }
            for (RevisionesAvance revisionesAvanceCollectionOldRevisionesAvance : revisionesAvanceCollectionOld) {
                if (!revisionesAvanceCollectionNew.contains(revisionesAvanceCollectionOldRevisionesAvance)) {
                    revisionesAvanceCollectionOldRevisionesAvance.getArchivosAdjuntosCollection().remove(archivosAdjuntos);
                    revisionesAvanceCollectionOldRevisionesAvance = em.merge(revisionesAvanceCollectionOldRevisionesAvance);
                }
            }
            for (RevisionesAvance revisionesAvanceCollectionNewRevisionesAvance : revisionesAvanceCollectionNew) {
                if (!revisionesAvanceCollectionOld.contains(revisionesAvanceCollectionNewRevisionesAvance)) {
                    revisionesAvanceCollectionNewRevisionesAvance.getArchivosAdjuntosCollection().add(archivosAdjuntos);
                    revisionesAvanceCollectionNewRevisionesAvance = em.merge(revisionesAvanceCollectionNewRevisionesAvance);
                }
            }
            for (Sustentaciones sustentacionesCollectionOldSustentaciones : sustentacionesCollectionOld) {
                if (!sustentacionesCollectionNew.contains(sustentacionesCollectionOldSustentaciones)) {
                    sustentacionesCollectionOldSustentaciones.setArchivosAdjuntosId(null);
                    sustentacionesCollectionOldSustentaciones = em.merge(sustentacionesCollectionOldSustentaciones);
                }
            }
            for (Sustentaciones sustentacionesCollectionNewSustentaciones : sustentacionesCollectionNew) {
                if (!sustentacionesCollectionOld.contains(sustentacionesCollectionNewSustentaciones)) {
                    ArchivosAdjuntos oldArchivosAdjuntosIdOfSustentacionesCollectionNewSustentaciones = sustentacionesCollectionNewSustentaciones.getArchivosAdjuntosId();
                    sustentacionesCollectionNewSustentaciones.setArchivosAdjuntosId(archivosAdjuntos);
                    sustentacionesCollectionNewSustentaciones = em.merge(sustentacionesCollectionNewSustentaciones);
                    if (oldArchivosAdjuntosIdOfSustentacionesCollectionNewSustentaciones != null && !oldArchivosAdjuntosIdOfSustentacionesCollectionNewSustentaciones.equals(archivosAdjuntos)) {
                        oldArchivosAdjuntosIdOfSustentacionesCollectionNewSustentaciones.getSustentacionesCollection().remove(sustentacionesCollectionNewSustentaciones);
                        oldArchivosAdjuntosIdOfSustentacionesCollectionNewSustentaciones = em.merge(oldArchivosAdjuntosIdOfSustentacionesCollectionNewSustentaciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = archivosAdjuntos.getId();
                if (findArchivosAdjuntos(id) == null) {
                    throw new NonexistentEntityException("The archivosAdjuntos with id " + id + " no longer exists.");
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
            ArchivosAdjuntos archivosAdjuntos;
            try {
                archivosAdjuntos = em.getReference(ArchivosAdjuntos.class, id);
                archivosAdjuntos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The archivosAdjuntos with id " + id + " no longer exists.", enfe);
            }
            Collection<Documentos> documentosCollection = archivosAdjuntos.getDocumentosCollection();
            for (Documentos documentosCollectionDocumentos : documentosCollection) {
                documentosCollectionDocumentos.getArchivosAdjuntosCollection().remove(archivosAdjuntos);
                documentosCollectionDocumentos = em.merge(documentosCollectionDocumentos);
            }
            Collection<Avances> avancesCollection = archivosAdjuntos.getAvancesCollection();
            for (Avances avancesCollectionAvances : avancesCollection) {
                avancesCollectionAvances.getArchivosAdjuntosCollection().remove(archivosAdjuntos);
                avancesCollectionAvances = em.merge(avancesCollectionAvances);
            }
            Collection<Ideas> ideasCollection = archivosAdjuntos.getIdeasCollection();
            for (Ideas ideasCollectionIdeas : ideasCollection) {
                ideasCollectionIdeas.getArchivosAdjuntosCollection().remove(archivosAdjuntos);
                ideasCollectionIdeas = em.merge(ideasCollectionIdeas);
            }
            Collection<RevisionesAvance> revisionesAvanceCollection = archivosAdjuntos.getRevisionesAvanceCollection();
            for (RevisionesAvance revisionesAvanceCollectionRevisionesAvance : revisionesAvanceCollection) {
                revisionesAvanceCollectionRevisionesAvance.getArchivosAdjuntosCollection().remove(archivosAdjuntos);
                revisionesAvanceCollectionRevisionesAvance = em.merge(revisionesAvanceCollectionRevisionesAvance);
            }
            Collection<Sustentaciones> sustentacionesCollection = archivosAdjuntos.getSustentacionesCollection();
            for (Sustentaciones sustentacionesCollectionSustentaciones : sustentacionesCollection) {
                sustentacionesCollectionSustentaciones.setArchivosAdjuntosId(null);
                sustentacionesCollectionSustentaciones = em.merge(sustentacionesCollectionSustentaciones);
            }
            em.remove(archivosAdjuntos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ArchivosAdjuntos> findArchivosAdjuntosEntities() {
        return findArchivosAdjuntosEntities(true, -1, -1);
    }

    public List<ArchivosAdjuntos> findArchivosAdjuntosEntities(int maxResults, int firstResult) {
        return findArchivosAdjuntosEntities(false, maxResults, firstResult);
    }

    private List<ArchivosAdjuntos> findArchivosAdjuntosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ArchivosAdjuntos.class));
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

    public ArchivosAdjuntos findArchivosAdjuntos(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ArchivosAdjuntos.class, id);
        } finally {
            em.close();
        }
    }

    public int getArchivosAdjuntosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ArchivosAdjuntos> rt = cq.from(ArchivosAdjuntos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
       /**
     * Este metodo retorna el siguiente id --> consecutivo
     *
     * @return
     */
    public BigDecimal getNextID() {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ArchivosAdjuntos> criteriaQuery = cb.createQuery(ArchivosAdjuntos.class);
        Root<ArchivosAdjuntos> c = criteriaQuery.from(ArchivosAdjuntos.class);
        Expression columnConsec = c.get("id");
        criteriaQuery.select(cb.max(columnConsec));
        Query query = em.createQuery(criteriaQuery);
        BigDecimal num = new BigDecimal(((BigDecimal) query.getSingleResult()).intValue() + 1);

        System.err.println(num);
        return num;
    }
    
}
