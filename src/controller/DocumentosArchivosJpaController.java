/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.DocumentosArchivos;
import modelo.DocumentosArchivosPK;

/**
 *
 * @author LuigyMendez
 */
public class DocumentosArchivosJpaController implements Serializable {

    public DocumentosArchivosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("SwingBDIIPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DocumentosArchivos documentosArchivos) throws PreexistingEntityException, Exception {
        if (documentosArchivos.getDocumentosArchivosPK() == null) {
            documentosArchivos.setDocumentosArchivosPK(new DocumentosArchivosPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(documentosArchivos);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDocumentosArchivos(documentosArchivos.getDocumentosArchivosPK()) != null) {
                throw new PreexistingEntityException("DocumentosArchivos " + documentosArchivos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DocumentosArchivos documentosArchivos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            documentosArchivos = em.merge(documentosArchivos);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                DocumentosArchivosPK id = documentosArchivos.getDocumentosArchivosPK();
                if (findDocumentosArchivos(id) == null) {
                    throw new NonexistentEntityException("The documentosArchivos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(DocumentosArchivosPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DocumentosArchivos documentosArchivos;
            try {
                documentosArchivos = em.getReference(DocumentosArchivos.class, id);
                documentosArchivos.getDocumentosArchivosPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The documentosArchivos with id " + id + " no longer exists.", enfe);
            }
            em.remove(documentosArchivos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DocumentosArchivos> findDocumentosArchivosEntities() {
        return findDocumentosArchivosEntities(true, -1, -1);
    }

    public List<DocumentosArchivos> findDocumentosArchivosEntities(int maxResults, int firstResult) {
        return findDocumentosArchivosEntities(false, maxResults, firstResult);
    }

    private List<DocumentosArchivos> findDocumentosArchivosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DocumentosArchivos.class));
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

    public DocumentosArchivos findDocumentosArchivos(DocumentosArchivosPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DocumentosArchivos.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocumentosArchivosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DocumentosArchivos> rt = cq.from(DocumentosArchivos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
