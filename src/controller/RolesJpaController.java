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
import modelo.Docentes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Roles;

/**
 *
 * @author ChristianFabian
 */
public class RolesJpaController implements Serializable {

    public RolesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Roles roles) throws PreexistingEntityException, Exception {
        if (roles.getDocentesCollection() == null) {
            roles.setDocentesCollection(new ArrayList<Docentes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Docentes> attachedDocentesCollection = new ArrayList<Docentes>();
            for (Docentes docentesCollectionDocentesToAttach : roles.getDocentesCollection()) {
                docentesCollectionDocentesToAttach = em.getReference(docentesCollectionDocentesToAttach.getClass(), docentesCollectionDocentesToAttach.getIdentificacion());
                attachedDocentesCollection.add(docentesCollectionDocentesToAttach);
            }
            roles.setDocentesCollection(attachedDocentesCollection);
            em.persist(roles);
            for (Docentes docentesCollectionDocentes : roles.getDocentesCollection()) {
                docentesCollectionDocentes.getRolesCollection().add(roles);
                docentesCollectionDocentes = em.merge(docentesCollectionDocentes);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRoles(roles.getRolesId()) != null) {
                throw new PreexistingEntityException("Roles " + roles + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Roles roles) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Roles persistentRoles = em.find(Roles.class, roles.getRolesId());
            Collection<Docentes> docentesCollectionOld = persistentRoles.getDocentesCollection();
            Collection<Docentes> docentesCollectionNew = roles.getDocentesCollection();
            Collection<Docentes> attachedDocentesCollectionNew = new ArrayList<Docentes>();
            for (Docentes docentesCollectionNewDocentesToAttach : docentesCollectionNew) {
                docentesCollectionNewDocentesToAttach = em.getReference(docentesCollectionNewDocentesToAttach.getClass(), docentesCollectionNewDocentesToAttach.getIdentificacion());
                attachedDocentesCollectionNew.add(docentesCollectionNewDocentesToAttach);
            }
            docentesCollectionNew = attachedDocentesCollectionNew;
            roles.setDocentesCollection(docentesCollectionNew);
            roles = em.merge(roles);
            for (Docentes docentesCollectionOldDocentes : docentesCollectionOld) {
                if (!docentesCollectionNew.contains(docentesCollectionOldDocentes)) {
                    docentesCollectionOldDocentes.getRolesCollection().remove(roles);
                    docentesCollectionOldDocentes = em.merge(docentesCollectionOldDocentes);
                }
            }
            for (Docentes docentesCollectionNewDocentes : docentesCollectionNew) {
                if (!docentesCollectionOld.contains(docentesCollectionNewDocentes)) {
                    docentesCollectionNewDocentes.getRolesCollection().add(roles);
                    docentesCollectionNewDocentes = em.merge(docentesCollectionNewDocentes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = roles.getRolesId();
                if (findRoles(id) == null) {
                    throw new NonexistentEntityException("The roles with id " + id + " no longer exists.");
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
            Roles roles;
            try {
                roles = em.getReference(Roles.class, id);
                roles.getRolesId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The roles with id " + id + " no longer exists.", enfe);
            }
            Collection<Docentes> docentesCollection = roles.getDocentesCollection();
            for (Docentes docentesCollectionDocentes : docentesCollection) {
                docentesCollectionDocentes.getRolesCollection().remove(roles);
                docentesCollectionDocentes = em.merge(docentesCollectionDocentes);
            }
            em.remove(roles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Roles> findRolesEntities() {
        return findRolesEntities(true, -1, -1);
    }

    public List<Roles> findRolesEntities(int maxResults, int firstResult) {
        return findRolesEntities(false, maxResults, firstResult);
    }

    private List<Roles> findRolesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Roles.class));
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

    public Roles findRoles(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Roles.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Roles> rt = cq.from(Roles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
