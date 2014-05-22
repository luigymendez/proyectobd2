/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.NonexistentEntityException;
import control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Docentes;
import modelo.Usuarios;

/**
 *
 * @author ChristianFabian
 */
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Docentes docentesIdentificacion = usuarios.getDocentesIdentificacion();
            if (docentesIdentificacion != null) {
                docentesIdentificacion = em.getReference(docentesIdentificacion.getClass(), docentesIdentificacion.getIdentificacion());
                usuarios.setDocentesIdentificacion(docentesIdentificacion);
            }
            em.persist(usuarios);
            if (docentesIdentificacion != null) {
                docentesIdentificacion.getUsuariosCollection().add(usuarios);
                docentesIdentificacion = em.merge(docentesIdentificacion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuarios(usuarios.getId()) != null) {
                throw new PreexistingEntityException("Usuarios " + usuarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getId());
            Docentes docentesIdentificacionOld = persistentUsuarios.getDocentesIdentificacion();
            Docentes docentesIdentificacionNew = usuarios.getDocentesIdentificacion();
            if (docentesIdentificacionNew != null) {
                docentesIdentificacionNew = em.getReference(docentesIdentificacionNew.getClass(), docentesIdentificacionNew.getIdentificacion());
                usuarios.setDocentesIdentificacion(docentesIdentificacionNew);
            }
            usuarios = em.merge(usuarios);
            if (docentesIdentificacionOld != null && !docentesIdentificacionOld.equals(docentesIdentificacionNew)) {
                docentesIdentificacionOld.getUsuariosCollection().remove(usuarios);
                docentesIdentificacionOld = em.merge(docentesIdentificacionOld);
            }
            if (docentesIdentificacionNew != null && !docentesIdentificacionNew.equals(docentesIdentificacionOld)) {
                docentesIdentificacionNew.getUsuariosCollection().add(usuarios);
                docentesIdentificacionNew = em.merge(docentesIdentificacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = usuarios.getId();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            Docentes docentesIdentificacion = usuarios.getDocentesIdentificacion();
            if (docentesIdentificacion != null) {
                docentesIdentificacion.getUsuariosCollection().remove(usuarios);
                docentesIdentificacion = em.merge(docentesIdentificacion);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
