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
import modelo.Avances;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Anteproyecto;
import modelo.Proyectos;

/**
 *
 * @author ChristianFabian
 */
public class AnteproyectoJpaController implements Serializable {

    public AnteproyectoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Anteproyecto anteproyecto) throws PreexistingEntityException, Exception {
        if (anteproyecto.getAvancesCollection() == null) {
            anteproyecto.setAvancesCollection(new ArrayList<Avances>());
        }
        if (anteproyecto.getProyectosCollection() == null) {
            anteproyecto.setProyectosCollection(new ArrayList<Proyectos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Propuestas propuestasId = anteproyecto.getPropuestasId();
            if (propuestasId != null) {
                propuestasId = em.getReference(propuestasId.getClass(), propuestasId.getId());
                anteproyecto.setPropuestasId(propuestasId);
            }
            Collection<Avances> attachedAvancesCollection = new ArrayList<Avances>();
            for (Avances avancesCollectionAvancesToAttach : anteproyecto.getAvancesCollection()) {
                avancesCollectionAvancesToAttach = em.getReference(avancesCollectionAvancesToAttach.getClass(), avancesCollectionAvancesToAttach.getId());
                attachedAvancesCollection.add(avancesCollectionAvancesToAttach);
            }
            anteproyecto.setAvancesCollection(attachedAvancesCollection);
            Collection<Proyectos> attachedProyectosCollection = new ArrayList<Proyectos>();
            for (Proyectos proyectosCollectionProyectosToAttach : anteproyecto.getProyectosCollection()) {
                proyectosCollectionProyectosToAttach = em.getReference(proyectosCollectionProyectosToAttach.getClass(), proyectosCollectionProyectosToAttach.getId());
                attachedProyectosCollection.add(proyectosCollectionProyectosToAttach);
            }
            anteproyecto.setProyectosCollection(attachedProyectosCollection);
            em.persist(anteproyecto);
            if (propuestasId != null) {
                propuestasId.getAnteproyectoCollection().add(anteproyecto);
                propuestasId = em.merge(propuestasId);
            }
            for (Avances avancesCollectionAvances : anteproyecto.getAvancesCollection()) {
                Anteproyecto oldAnteproyectoIdOfAvancesCollectionAvances = avancesCollectionAvances.getAnteproyectoId();
                avancesCollectionAvances.setAnteproyectoId(anteproyecto);
                avancesCollectionAvances = em.merge(avancesCollectionAvances);
                if (oldAnteproyectoIdOfAvancesCollectionAvances != null) {
                    oldAnteproyectoIdOfAvancesCollectionAvances.getAvancesCollection().remove(avancesCollectionAvances);
                    oldAnteproyectoIdOfAvancesCollectionAvances = em.merge(oldAnteproyectoIdOfAvancesCollectionAvances);
                }
            }
            for (Proyectos proyectosCollectionProyectos : anteproyecto.getProyectosCollection()) {
                Anteproyecto oldAnteproyectoIdOfProyectosCollectionProyectos = proyectosCollectionProyectos.getAnteproyectoId();
                proyectosCollectionProyectos.setAnteproyectoId(anteproyecto);
                proyectosCollectionProyectos = em.merge(proyectosCollectionProyectos);
                if (oldAnteproyectoIdOfProyectosCollectionProyectos != null) {
                    oldAnteproyectoIdOfProyectosCollectionProyectos.getProyectosCollection().remove(proyectosCollectionProyectos);
                    oldAnteproyectoIdOfProyectosCollectionProyectos = em.merge(oldAnteproyectoIdOfProyectosCollectionProyectos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAnteproyecto(anteproyecto.getId()) != null) {
                throw new PreexistingEntityException("Anteproyecto " + anteproyecto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Anteproyecto anteproyecto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Anteproyecto persistentAnteproyecto = em.find(Anteproyecto.class, anteproyecto.getId());
            Propuestas propuestasIdOld = persistentAnteproyecto.getPropuestasId();
            Propuestas propuestasIdNew = anteproyecto.getPropuestasId();
            Collection<Avances> avancesCollectionOld = persistentAnteproyecto.getAvancesCollection();
            Collection<Avances> avancesCollectionNew = anteproyecto.getAvancesCollection();
            Collection<Proyectos> proyectosCollectionOld = persistentAnteproyecto.getProyectosCollection();
            Collection<Proyectos> proyectosCollectionNew = anteproyecto.getProyectosCollection();
            List<String> illegalOrphanMessages = null;
            for (Proyectos proyectosCollectionOldProyectos : proyectosCollectionOld) {
                if (!proyectosCollectionNew.contains(proyectosCollectionOldProyectos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Proyectos " + proyectosCollectionOldProyectos + " since its anteproyectoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (propuestasIdNew != null) {
                propuestasIdNew = em.getReference(propuestasIdNew.getClass(), propuestasIdNew.getId());
                anteproyecto.setPropuestasId(propuestasIdNew);
            }
            Collection<Avances> attachedAvancesCollectionNew = new ArrayList<Avances>();
            for (Avances avancesCollectionNewAvancesToAttach : avancesCollectionNew) {
                avancesCollectionNewAvancesToAttach = em.getReference(avancesCollectionNewAvancesToAttach.getClass(), avancesCollectionNewAvancesToAttach.getId());
                attachedAvancesCollectionNew.add(avancesCollectionNewAvancesToAttach);
            }
            avancesCollectionNew = attachedAvancesCollectionNew;
            anteproyecto.setAvancesCollection(avancesCollectionNew);
            Collection<Proyectos> attachedProyectosCollectionNew = new ArrayList<Proyectos>();
            for (Proyectos proyectosCollectionNewProyectosToAttach : proyectosCollectionNew) {
                proyectosCollectionNewProyectosToAttach = em.getReference(proyectosCollectionNewProyectosToAttach.getClass(), proyectosCollectionNewProyectosToAttach.getId());
                attachedProyectosCollectionNew.add(proyectosCollectionNewProyectosToAttach);
            }
            proyectosCollectionNew = attachedProyectosCollectionNew;
            anteproyecto.setProyectosCollection(proyectosCollectionNew);
            anteproyecto = em.merge(anteproyecto);
            if (propuestasIdOld != null && !propuestasIdOld.equals(propuestasIdNew)) {
                propuestasIdOld.getAnteproyectoCollection().remove(anteproyecto);
                propuestasIdOld = em.merge(propuestasIdOld);
            }
            if (propuestasIdNew != null && !propuestasIdNew.equals(propuestasIdOld)) {
                propuestasIdNew.getAnteproyectoCollection().add(anteproyecto);
                propuestasIdNew = em.merge(propuestasIdNew);
            }
            for (Avances avancesCollectionOldAvances : avancesCollectionOld) {
                if (!avancesCollectionNew.contains(avancesCollectionOldAvances)) {
                    avancesCollectionOldAvances.setAnteproyectoId(null);
                    avancesCollectionOldAvances = em.merge(avancesCollectionOldAvances);
                }
            }
            for (Avances avancesCollectionNewAvances : avancesCollectionNew) {
                if (!avancesCollectionOld.contains(avancesCollectionNewAvances)) {
                    Anteproyecto oldAnteproyectoIdOfAvancesCollectionNewAvances = avancesCollectionNewAvances.getAnteproyectoId();
                    avancesCollectionNewAvances.setAnteproyectoId(anteproyecto);
                    avancesCollectionNewAvances = em.merge(avancesCollectionNewAvances);
                    if (oldAnteproyectoIdOfAvancesCollectionNewAvances != null && !oldAnteproyectoIdOfAvancesCollectionNewAvances.equals(anteproyecto)) {
                        oldAnteproyectoIdOfAvancesCollectionNewAvances.getAvancesCollection().remove(avancesCollectionNewAvances);
                        oldAnteproyectoIdOfAvancesCollectionNewAvances = em.merge(oldAnteproyectoIdOfAvancesCollectionNewAvances);
                    }
                }
            }
            for (Proyectos proyectosCollectionNewProyectos : proyectosCollectionNew) {
                if (!proyectosCollectionOld.contains(proyectosCollectionNewProyectos)) {
                    Anteproyecto oldAnteproyectoIdOfProyectosCollectionNewProyectos = proyectosCollectionNewProyectos.getAnteproyectoId();
                    proyectosCollectionNewProyectos.setAnteproyectoId(anteproyecto);
                    proyectosCollectionNewProyectos = em.merge(proyectosCollectionNewProyectos);
                    if (oldAnteproyectoIdOfProyectosCollectionNewProyectos != null && !oldAnteproyectoIdOfProyectosCollectionNewProyectos.equals(anteproyecto)) {
                        oldAnteproyectoIdOfProyectosCollectionNewProyectos.getProyectosCollection().remove(proyectosCollectionNewProyectos);
                        oldAnteproyectoIdOfProyectosCollectionNewProyectos = em.merge(oldAnteproyectoIdOfProyectosCollectionNewProyectos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = anteproyecto.getId();
                if (findAnteproyecto(id) == null) {
                    throw new NonexistentEntityException("The anteproyecto with id " + id + " no longer exists.");
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
            Anteproyecto anteproyecto;
            try {
                anteproyecto = em.getReference(Anteproyecto.class, id);
                anteproyecto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The anteproyecto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Proyectos> proyectosCollectionOrphanCheck = anteproyecto.getProyectosCollection();
            for (Proyectos proyectosCollectionOrphanCheckProyectos : proyectosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Anteproyecto (" + anteproyecto + ") cannot be destroyed since the Proyectos " + proyectosCollectionOrphanCheckProyectos + " in its proyectosCollection field has a non-nullable anteproyectoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Propuestas propuestasId = anteproyecto.getPropuestasId();
            if (propuestasId != null) {
                propuestasId.getAnteproyectoCollection().remove(anteproyecto);
                propuestasId = em.merge(propuestasId);
            }
            Collection<Avances> avancesCollection = anteproyecto.getAvancesCollection();
            for (Avances avancesCollectionAvances : avancesCollection) {
                avancesCollectionAvances.setAnteproyectoId(null);
                avancesCollectionAvances = em.merge(avancesCollectionAvances);
            }
            em.remove(anteproyecto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Anteproyecto> findAnteproyectoEntities() {
        return findAnteproyectoEntities(true, -1, -1);
    }

    public List<Anteproyecto> findAnteproyectoEntities(int maxResults, int firstResult) {
        return findAnteproyectoEntities(false, maxResults, firstResult);
    }

    private List<Anteproyecto> findAnteproyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Anteproyecto.class));
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

    public Anteproyecto findAnteproyecto(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Anteproyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getAnteproyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Anteproyecto> rt = cq.from(Anteproyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
