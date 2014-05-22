/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.NonexistentEntityException;
import control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.GrupoIntegrantes;
import modelo.GrupoIntegrantesPK;

/**
 *
 * @author ChristianFabian
 */
public class GrupoIntegrantesJpaController implements Serializable {

    public GrupoIntegrantesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GrupoIntegrantes grupoIntegrantes) throws PreexistingEntityException, Exception {
        if (grupoIntegrantes.getGrupoIntegrantesPK() == null) {
            grupoIntegrantes.setGrupoIntegrantesPK(new GrupoIntegrantesPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(grupoIntegrantes);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGrupoIntegrantes(grupoIntegrantes.getGrupoIntegrantesPK()) != null) {
                throw new PreexistingEntityException("GrupoIntegrantes " + grupoIntegrantes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GrupoIntegrantes grupoIntegrantes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            grupoIntegrantes = em.merge(grupoIntegrantes);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                GrupoIntegrantesPK id = grupoIntegrantes.getGrupoIntegrantesPK();
                if (findGrupoIntegrantes(id) == null) {
                    throw new NonexistentEntityException("The grupoIntegrantes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(GrupoIntegrantesPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GrupoIntegrantes grupoIntegrantes;
            try {
                grupoIntegrantes = em.getReference(GrupoIntegrantes.class, id);
                grupoIntegrantes.getGrupoIntegrantesPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupoIntegrantes with id " + id + " no longer exists.", enfe);
            }
            em.remove(grupoIntegrantes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GrupoIntegrantes> findGrupoIntegrantesEntities() {
        return findGrupoIntegrantesEntities(true, -1, -1);
    }

    public List<GrupoIntegrantes> findGrupoIntegrantesEntities(int maxResults, int firstResult) {
        return findGrupoIntegrantesEntities(false, maxResults, firstResult);
    }

    private List<GrupoIntegrantes> findGrupoIntegrantesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GrupoIntegrantes.class));
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

    public GrupoIntegrantes findGrupoIntegrantes(GrupoIntegrantesPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GrupoIntegrantes.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoIntegrantesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GrupoIntegrantes> rt = cq.from(GrupoIntegrantes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
