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
import modelo.JuradosSustentaciones;
import modelo.JuradosSustentacionesPK;

/**
 *
 * @author ChristianFabian
 */
public class JuradosSustentacionesJpaController implements Serializable {

    public JuradosSustentacionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(JuradosSustentaciones juradosSustentaciones) throws PreexistingEntityException, Exception {
        if (juradosSustentaciones.getJuradosSustentacionesPK() == null) {
            juradosSustentaciones.setJuradosSustentacionesPK(new JuradosSustentacionesPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(juradosSustentaciones);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findJuradosSustentaciones(juradosSustentaciones.getJuradosSustentacionesPK()) != null) {
                throw new PreexistingEntityException("JuradosSustentaciones " + juradosSustentaciones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(JuradosSustentaciones juradosSustentaciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            juradosSustentaciones = em.merge(juradosSustentaciones);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                JuradosSustentacionesPK id = juradosSustentaciones.getJuradosSustentacionesPK();
                if (findJuradosSustentaciones(id) == null) {
                    throw new NonexistentEntityException("The juradosSustentaciones with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(JuradosSustentacionesPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            JuradosSustentaciones juradosSustentaciones;
            try {
                juradosSustentaciones = em.getReference(JuradosSustentaciones.class, id);
                juradosSustentaciones.getJuradosSustentacionesPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The juradosSustentaciones with id " + id + " no longer exists.", enfe);
            }
            em.remove(juradosSustentaciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<JuradosSustentaciones> findJuradosSustentacionesEntities() {
        return findJuradosSustentacionesEntities(true, -1, -1);
    }

    public List<JuradosSustentaciones> findJuradosSustentacionesEntities(int maxResults, int firstResult) {
        return findJuradosSustentacionesEntities(false, maxResults, firstResult);
    }

    private List<JuradosSustentaciones> findJuradosSustentacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(JuradosSustentaciones.class));
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

    public JuradosSustentaciones findJuradosSustentaciones(JuradosSustentacionesPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(JuradosSustentaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getJuradosSustentacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<JuradosSustentaciones> rt = cq.from(JuradosSustentaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
