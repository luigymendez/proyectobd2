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
import modelo.Estados;
import modelo.Entregas;
import modelo.ArchivosAdjuntos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Documentos;

/**
 *
 * @author ChristianFabian
 */
public class DocumentosJpaController implements Serializable {

    public DocumentosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Documentos documentos) throws PreexistingEntityException, Exception {
        if (documentos.getArchivosAdjuntosCollection() == null) {
            documentos.setArchivosAdjuntosCollection(new ArrayList<ArchivosAdjuntos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estados estadosId = documentos.getEstadosId();
            if (estadosId != null) {
                estadosId = em.getReference(estadosId.getClass(), estadosId.getId());
                documentos.setEstadosId(estadosId);
            }
            Entregas entregasId = documentos.getEntregasId();
            if (entregasId != null) {
                entregasId = em.getReference(entregasId.getClass(), entregasId.getId());
                documentos.setEntregasId(entregasId);
            }
            Collection<ArchivosAdjuntos> attachedArchivosAdjuntosCollection = new ArrayList<ArchivosAdjuntos>();
            for (ArchivosAdjuntos archivosAdjuntosCollectionArchivosAdjuntosToAttach : documentos.getArchivosAdjuntosCollection()) {
                archivosAdjuntosCollectionArchivosAdjuntosToAttach = em.getReference(archivosAdjuntosCollectionArchivosAdjuntosToAttach.getClass(), archivosAdjuntosCollectionArchivosAdjuntosToAttach.getId());
                attachedArchivosAdjuntosCollection.add(archivosAdjuntosCollectionArchivosAdjuntosToAttach);
            }
            documentos.setArchivosAdjuntosCollection(attachedArchivosAdjuntosCollection);
            em.persist(documentos);
            if (estadosId != null) {
                estadosId.getDocumentosCollection().add(documentos);
                estadosId = em.merge(estadosId);
            }
            if (entregasId != null) {
                entregasId.getDocumentosCollection().add(documentos);
                entregasId = em.merge(entregasId);
            }
            for (ArchivosAdjuntos archivosAdjuntosCollectionArchivosAdjuntos : documentos.getArchivosAdjuntosCollection()) {
                archivosAdjuntosCollectionArchivosAdjuntos.getDocumentosCollection().add(documentos);
                archivosAdjuntosCollectionArchivosAdjuntos = em.merge(archivosAdjuntosCollectionArchivosAdjuntos);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDocumentos(documentos.getId()) != null) {
                throw new PreexistingEntityException("Documentos " + documentos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Documentos documentos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Documentos persistentDocumentos = em.find(Documentos.class, documentos.getId());
            Estados estadosIdOld = persistentDocumentos.getEstadosId();
            Estados estadosIdNew = documentos.getEstadosId();
            Entregas entregasIdOld = persistentDocumentos.getEntregasId();
            Entregas entregasIdNew = documentos.getEntregasId();
            Collection<ArchivosAdjuntos> archivosAdjuntosCollectionOld = persistentDocumentos.getArchivosAdjuntosCollection();
            Collection<ArchivosAdjuntos> archivosAdjuntosCollectionNew = documentos.getArchivosAdjuntosCollection();
            if (estadosIdNew != null) {
                estadosIdNew = em.getReference(estadosIdNew.getClass(), estadosIdNew.getId());
                documentos.setEstadosId(estadosIdNew);
            }
            if (entregasIdNew != null) {
                entregasIdNew = em.getReference(entregasIdNew.getClass(), entregasIdNew.getId());
                documentos.setEntregasId(entregasIdNew);
            }
            Collection<ArchivosAdjuntos> attachedArchivosAdjuntosCollectionNew = new ArrayList<ArchivosAdjuntos>();
            for (ArchivosAdjuntos archivosAdjuntosCollectionNewArchivosAdjuntosToAttach : archivosAdjuntosCollectionNew) {
                archivosAdjuntosCollectionNewArchivosAdjuntosToAttach = em.getReference(archivosAdjuntosCollectionNewArchivosAdjuntosToAttach.getClass(), archivosAdjuntosCollectionNewArchivosAdjuntosToAttach.getId());
                attachedArchivosAdjuntosCollectionNew.add(archivosAdjuntosCollectionNewArchivosAdjuntosToAttach);
            }
            archivosAdjuntosCollectionNew = attachedArchivosAdjuntosCollectionNew;
            documentos.setArchivosAdjuntosCollection(archivosAdjuntosCollectionNew);
            documentos = em.merge(documentos);
            if (estadosIdOld != null && !estadosIdOld.equals(estadosIdNew)) {
                estadosIdOld.getDocumentosCollection().remove(documentos);
                estadosIdOld = em.merge(estadosIdOld);
            }
            if (estadosIdNew != null && !estadosIdNew.equals(estadosIdOld)) {
                estadosIdNew.getDocumentosCollection().add(documentos);
                estadosIdNew = em.merge(estadosIdNew);
            }
            if (entregasIdOld != null && !entregasIdOld.equals(entregasIdNew)) {
                entregasIdOld.getDocumentosCollection().remove(documentos);
                entregasIdOld = em.merge(entregasIdOld);
            }
            if (entregasIdNew != null && !entregasIdNew.equals(entregasIdOld)) {
                entregasIdNew.getDocumentosCollection().add(documentos);
                entregasIdNew = em.merge(entregasIdNew);
            }
            for (ArchivosAdjuntos archivosAdjuntosCollectionOldArchivosAdjuntos : archivosAdjuntosCollectionOld) {
                if (!archivosAdjuntosCollectionNew.contains(archivosAdjuntosCollectionOldArchivosAdjuntos)) {
                    archivosAdjuntosCollectionOldArchivosAdjuntos.getDocumentosCollection().remove(documentos);
                    archivosAdjuntosCollectionOldArchivosAdjuntos = em.merge(archivosAdjuntosCollectionOldArchivosAdjuntos);
                }
            }
            for (ArchivosAdjuntos archivosAdjuntosCollectionNewArchivosAdjuntos : archivosAdjuntosCollectionNew) {
                if (!archivosAdjuntosCollectionOld.contains(archivosAdjuntosCollectionNewArchivosAdjuntos)) {
                    archivosAdjuntosCollectionNewArchivosAdjuntos.getDocumentosCollection().add(documentos);
                    archivosAdjuntosCollectionNewArchivosAdjuntos = em.merge(archivosAdjuntosCollectionNewArchivosAdjuntos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = documentos.getId();
                if (findDocumentos(id) == null) {
                    throw new NonexistentEntityException("The documentos with id " + id + " no longer exists.");
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
            Documentos documentos;
            try {
                documentos = em.getReference(Documentos.class, id);
                documentos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The documentos with id " + id + " no longer exists.", enfe);
            }
            Estados estadosId = documentos.getEstadosId();
            if (estadosId != null) {
                estadosId.getDocumentosCollection().remove(documentos);
                estadosId = em.merge(estadosId);
            }
            Entregas entregasId = documentos.getEntregasId();
            if (entregasId != null) {
                entregasId.getDocumentosCollection().remove(documentos);
                entregasId = em.merge(entregasId);
            }
            Collection<ArchivosAdjuntos> archivosAdjuntosCollection = documentos.getArchivosAdjuntosCollection();
            for (ArchivosAdjuntos archivosAdjuntosCollectionArchivosAdjuntos : archivosAdjuntosCollection) {
                archivosAdjuntosCollectionArchivosAdjuntos.getDocumentosCollection().remove(documentos);
                archivosAdjuntosCollectionArchivosAdjuntos = em.merge(archivosAdjuntosCollectionArchivosAdjuntos);
            }
            em.remove(documentos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Documentos> findDocumentosEntities() {
        return findDocumentosEntities(true, -1, -1);
    }

    public List<Documentos> findDocumentosEntities(int maxResults, int firstResult) {
        return findDocumentosEntities(false, maxResults, firstResult);
    }

    private List<Documentos> findDocumentosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Documentos.class));
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

    public Documentos findDocumentos(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Documentos.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocumentosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Documentos> rt = cq.from(Documentos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
