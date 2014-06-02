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
import modelo.Proyectos;
import modelo.Documentos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Entregas;

/**
 *
 * @author ChristianFabian
 */
public class EntregasJpaController implements Serializable {

    public EntregasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("SwingBDIIPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Entregas entregas) throws PreexistingEntityException, Exception {
        if (entregas.getDocumentosCollection() == null) {
            entregas.setDocumentosCollection(new ArrayList<Documentos>());
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
            Collection<Documentos> attachedDocumentosCollection = new ArrayList<Documentos>();
            for (Documentos documentosCollectionDocumentosToAttach : entregas.getDocumentosCollection()) {
                documentosCollectionDocumentosToAttach = em.getReference(documentosCollectionDocumentosToAttach.getClass(), documentosCollectionDocumentosToAttach.getId());
                attachedDocumentosCollection.add(documentosCollectionDocumentosToAttach);
            }
            entregas.setDocumentosCollection(attachedDocumentosCollection);
            em.persist(entregas);
            if (proyectosId != null) {
                proyectosId.getEntregasCollection().add(entregas);
                proyectosId = em.merge(proyectosId);
            }
            for (Documentos documentosCollectionDocumentos : entregas.getDocumentosCollection()) {
                Entregas oldEntregasIdOfDocumentosCollectionDocumentos = documentosCollectionDocumentos.getEntregasId();
                documentosCollectionDocumentos.setEntregasId(entregas);
                documentosCollectionDocumentos = em.merge(documentosCollectionDocumentos);
                if (oldEntregasIdOfDocumentosCollectionDocumentos != null) {
                    oldEntregasIdOfDocumentosCollectionDocumentos.getDocumentosCollection().remove(documentosCollectionDocumentos);
                    oldEntregasIdOfDocumentosCollectionDocumentos = em.merge(oldEntregasIdOfDocumentosCollectionDocumentos);
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
            Collection<Documentos> documentosCollectionOld = persistentEntregas.getDocumentosCollection();
            Collection<Documentos> documentosCollectionNew = entregas.getDocumentosCollection();
            List<String> illegalOrphanMessages = null;
            for (Documentos documentosCollectionOldDocumentos : documentosCollectionOld) {
                if (!documentosCollectionNew.contains(documentosCollectionOldDocumentos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Documentos " + documentosCollectionOldDocumentos + " since its entregasId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (proyectosIdNew != null) {
                proyectosIdNew = em.getReference(proyectosIdNew.getClass(), proyectosIdNew.getId());
                entregas.setProyectosId(proyectosIdNew);
            }
            Collection<Documentos> attachedDocumentosCollectionNew = new ArrayList<Documentos>();
            for (Documentos documentosCollectionNewDocumentosToAttach : documentosCollectionNew) {
                documentosCollectionNewDocumentosToAttach = em.getReference(documentosCollectionNewDocumentosToAttach.getClass(), documentosCollectionNewDocumentosToAttach.getId());
                attachedDocumentosCollectionNew.add(documentosCollectionNewDocumentosToAttach);
            }
            documentosCollectionNew = attachedDocumentosCollectionNew;
            entregas.setDocumentosCollection(documentosCollectionNew);
            entregas = em.merge(entregas);
            if (proyectosIdOld != null && !proyectosIdOld.equals(proyectosIdNew)) {
                proyectosIdOld.getEntregasCollection().remove(entregas);
                proyectosIdOld = em.merge(proyectosIdOld);
            }
            if (proyectosIdNew != null && !proyectosIdNew.equals(proyectosIdOld)) {
                proyectosIdNew.getEntregasCollection().add(entregas);
                proyectosIdNew = em.merge(proyectosIdNew);
            }
            for (Documentos documentosCollectionNewDocumentos : documentosCollectionNew) {
                if (!documentosCollectionOld.contains(documentosCollectionNewDocumentos)) {
                    Entregas oldEntregasIdOfDocumentosCollectionNewDocumentos = documentosCollectionNewDocumentos.getEntregasId();
                    documentosCollectionNewDocumentos.setEntregasId(entregas);
                    documentosCollectionNewDocumentos = em.merge(documentosCollectionNewDocumentos);
                    if (oldEntregasIdOfDocumentosCollectionNewDocumentos != null && !oldEntregasIdOfDocumentosCollectionNewDocumentos.equals(entregas)) {
                        oldEntregasIdOfDocumentosCollectionNewDocumentos.getDocumentosCollection().remove(documentosCollectionNewDocumentos);
                        oldEntregasIdOfDocumentosCollectionNewDocumentos = em.merge(oldEntregasIdOfDocumentosCollectionNewDocumentos);
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
            Collection<Documentos> documentosCollectionOrphanCheck = entregas.getDocumentosCollection();
            for (Documentos documentosCollectionOrphanCheckDocumentos : documentosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Entregas (" + entregas + ") cannot be destroyed since the Documentos " + documentosCollectionOrphanCheckDocumentos + " in its documentosCollection field has a non-nullable entregasId field.");
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
