/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.IllegalOrphanException;
import control.exceptions.NonexistentEntityException;
import control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Ideas;
import modelo.Estados;
import modelo.Anteproyecto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Avances;
import modelo.Propuestas;

/**
 *
 * @author ChristianFabian
 */
public class PropuestasJpaController implements Serializable {

    public PropuestasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Propuestas propuestas) throws PreexistingEntityException, Exception {
        if (propuestas.getAnteproyectoCollection() == null) {
            propuestas.setAnteproyectoCollection(new ArrayList<Anteproyecto>());
        }
        if (propuestas.getAvancesCollection() == null) {
            propuestas.setAvancesCollection(new ArrayList<Avances>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ideas ideasId = propuestas.getIdeasId();
            if (ideasId != null) {
                ideasId = em.getReference(ideasId.getClass(), ideasId.getId());
                propuestas.setIdeasId(ideasId);
            }
            Estados estadosId = propuestas.getEstadosId();
            if (estadosId != null) {
                estadosId = em.getReference(estadosId.getClass(), estadosId.getId());
                propuestas.setEstadosId(estadosId);
            }
            Collection<Anteproyecto> attachedAnteproyectoCollection = new ArrayList<Anteproyecto>();
            for (Anteproyecto anteproyectoCollectionAnteproyectoToAttach : propuestas.getAnteproyectoCollection()) {
                anteproyectoCollectionAnteproyectoToAttach = em.getReference(anteproyectoCollectionAnteproyectoToAttach.getClass(), anteproyectoCollectionAnteproyectoToAttach.getId());
                attachedAnteproyectoCollection.add(anteproyectoCollectionAnteproyectoToAttach);
            }
            propuestas.setAnteproyectoCollection(attachedAnteproyectoCollection);
            Collection<Avances> attachedAvancesCollection = new ArrayList<Avances>();
            for (Avances avancesCollectionAvancesToAttach : propuestas.getAvancesCollection()) {
                avancesCollectionAvancesToAttach = em.getReference(avancesCollectionAvancesToAttach.getClass(), avancesCollectionAvancesToAttach.getId());
                attachedAvancesCollection.add(avancesCollectionAvancesToAttach);
            }
            propuestas.setAvancesCollection(attachedAvancesCollection);
            em.persist(propuestas);
            if (ideasId != null) {
                ideasId.getPropuestasCollection().add(propuestas);
                ideasId = em.merge(ideasId);
            }
            if (estadosId != null) {
                estadosId.getPropuestasCollection().add(propuestas);
                estadosId = em.merge(estadosId);
            }
            for (Anteproyecto anteproyectoCollectionAnteproyecto : propuestas.getAnteproyectoCollection()) {
                Propuestas oldPropuestasIdOfAnteproyectoCollectionAnteproyecto = anteproyectoCollectionAnteproyecto.getPropuestasId();
                anteproyectoCollectionAnteproyecto.setPropuestasId(propuestas);
                anteproyectoCollectionAnteproyecto = em.merge(anteproyectoCollectionAnteproyecto);
                if (oldPropuestasIdOfAnteproyectoCollectionAnteproyecto != null) {
                    oldPropuestasIdOfAnteproyectoCollectionAnteproyecto.getAnteproyectoCollection().remove(anteproyectoCollectionAnteproyecto);
                    oldPropuestasIdOfAnteproyectoCollectionAnteproyecto = em.merge(oldPropuestasIdOfAnteproyectoCollectionAnteproyecto);
                }
            }
            for (Avances avancesCollectionAvances : propuestas.getAvancesCollection()) {
                Propuestas oldPropuestasIdOfAvancesCollectionAvances = avancesCollectionAvances.getPropuestasId();
                avancesCollectionAvances.setPropuestasId(propuestas);
                avancesCollectionAvances = em.merge(avancesCollectionAvances);
                if (oldPropuestasIdOfAvancesCollectionAvances != null) {
                    oldPropuestasIdOfAvancesCollectionAvances.getAvancesCollection().remove(avancesCollectionAvances);
                    oldPropuestasIdOfAvancesCollectionAvances = em.merge(oldPropuestasIdOfAvancesCollectionAvances);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPropuestas(propuestas.getId()) != null) {
                throw new PreexistingEntityException("Propuestas " + propuestas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Propuestas propuestas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Propuestas persistentPropuestas = em.find(Propuestas.class, propuestas.getId());
            Ideas ideasIdOld = persistentPropuestas.getIdeasId();
            Ideas ideasIdNew = propuestas.getIdeasId();
            Estados estadosIdOld = persistentPropuestas.getEstadosId();
            Estados estadosIdNew = propuestas.getEstadosId();
            Collection<Anteproyecto> anteproyectoCollectionOld = persistentPropuestas.getAnteproyectoCollection();
            Collection<Anteproyecto> anteproyectoCollectionNew = propuestas.getAnteproyectoCollection();
            Collection<Avances> avancesCollectionOld = persistentPropuestas.getAvancesCollection();
            Collection<Avances> avancesCollectionNew = propuestas.getAvancesCollection();
            List<String> illegalOrphanMessages = null;
            for (Anteproyecto anteproyectoCollectionOldAnteproyecto : anteproyectoCollectionOld) {
                if (!anteproyectoCollectionNew.contains(anteproyectoCollectionOldAnteproyecto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Anteproyecto " + anteproyectoCollectionOldAnteproyecto + " since its propuestasId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (ideasIdNew != null) {
                ideasIdNew = em.getReference(ideasIdNew.getClass(), ideasIdNew.getId());
                propuestas.setIdeasId(ideasIdNew);
            }
            if (estadosIdNew != null) {
                estadosIdNew = em.getReference(estadosIdNew.getClass(), estadosIdNew.getId());
                propuestas.setEstadosId(estadosIdNew);
            }
            Collection<Anteproyecto> attachedAnteproyectoCollectionNew = new ArrayList<Anteproyecto>();
            for (Anteproyecto anteproyectoCollectionNewAnteproyectoToAttach : anteproyectoCollectionNew) {
                anteproyectoCollectionNewAnteproyectoToAttach = em.getReference(anteproyectoCollectionNewAnteproyectoToAttach.getClass(), anteproyectoCollectionNewAnteproyectoToAttach.getId());
                attachedAnteproyectoCollectionNew.add(anteproyectoCollectionNewAnteproyectoToAttach);
            }
            anteproyectoCollectionNew = attachedAnteproyectoCollectionNew;
            propuestas.setAnteproyectoCollection(anteproyectoCollectionNew);
            Collection<Avances> attachedAvancesCollectionNew = new ArrayList<Avances>();
            for (Avances avancesCollectionNewAvancesToAttach : avancesCollectionNew) {
                avancesCollectionNewAvancesToAttach = em.getReference(avancesCollectionNewAvancesToAttach.getClass(), avancesCollectionNewAvancesToAttach.getId());
                attachedAvancesCollectionNew.add(avancesCollectionNewAvancesToAttach);
            }
            avancesCollectionNew = attachedAvancesCollectionNew;
            propuestas.setAvancesCollection(avancesCollectionNew);
            propuestas = em.merge(propuestas);
            if (ideasIdOld != null && !ideasIdOld.equals(ideasIdNew)) {
                ideasIdOld.getPropuestasCollection().remove(propuestas);
                ideasIdOld = em.merge(ideasIdOld);
            }
            if (ideasIdNew != null && !ideasIdNew.equals(ideasIdOld)) {
                ideasIdNew.getPropuestasCollection().add(propuestas);
                ideasIdNew = em.merge(ideasIdNew);
            }
            if (estadosIdOld != null && !estadosIdOld.equals(estadosIdNew)) {
                estadosIdOld.getPropuestasCollection().remove(propuestas);
                estadosIdOld = em.merge(estadosIdOld);
            }
            if (estadosIdNew != null && !estadosIdNew.equals(estadosIdOld)) {
                estadosIdNew.getPropuestasCollection().add(propuestas);
                estadosIdNew = em.merge(estadosIdNew);
            }
            for (Anteproyecto anteproyectoCollectionNewAnteproyecto : anteproyectoCollectionNew) {
                if (!anteproyectoCollectionOld.contains(anteproyectoCollectionNewAnteproyecto)) {
                    Propuestas oldPropuestasIdOfAnteproyectoCollectionNewAnteproyecto = anteproyectoCollectionNewAnteproyecto.getPropuestasId();
                    anteproyectoCollectionNewAnteproyecto.setPropuestasId(propuestas);
                    anteproyectoCollectionNewAnteproyecto = em.merge(anteproyectoCollectionNewAnteproyecto);
                    if (oldPropuestasIdOfAnteproyectoCollectionNewAnteproyecto != null && !oldPropuestasIdOfAnteproyectoCollectionNewAnteproyecto.equals(propuestas)) {
                        oldPropuestasIdOfAnteproyectoCollectionNewAnteproyecto.getAnteproyectoCollection().remove(anteproyectoCollectionNewAnteproyecto);
                        oldPropuestasIdOfAnteproyectoCollectionNewAnteproyecto = em.merge(oldPropuestasIdOfAnteproyectoCollectionNewAnteproyecto);
                    }
                }
            }
            for (Avances avancesCollectionOldAvances : avancesCollectionOld) {
                if (!avancesCollectionNew.contains(avancesCollectionOldAvances)) {
                    avancesCollectionOldAvances.setPropuestasId(null);
                    avancesCollectionOldAvances = em.merge(avancesCollectionOldAvances);
                }
            }
            for (Avances avancesCollectionNewAvances : avancesCollectionNew) {
                if (!avancesCollectionOld.contains(avancesCollectionNewAvances)) {
                    Propuestas oldPropuestasIdOfAvancesCollectionNewAvances = avancesCollectionNewAvances.getPropuestasId();
                    avancesCollectionNewAvances.setPropuestasId(propuestas);
                    avancesCollectionNewAvances = em.merge(avancesCollectionNewAvances);
                    if (oldPropuestasIdOfAvancesCollectionNewAvances != null && !oldPropuestasIdOfAvancesCollectionNewAvances.equals(propuestas)) {
                        oldPropuestasIdOfAvancesCollectionNewAvances.getAvancesCollection().remove(avancesCollectionNewAvances);
                        oldPropuestasIdOfAvancesCollectionNewAvances = em.merge(oldPropuestasIdOfAvancesCollectionNewAvances);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = propuestas.getId();
                if (findPropuestas(id) == null) {
                    throw new NonexistentEntityException("The propuestas with id " + id + " no longer exists.");
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
            Propuestas propuestas;
            try {
                propuestas = em.getReference(Propuestas.class, id);
                propuestas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The propuestas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Anteproyecto> anteproyectoCollectionOrphanCheck = propuestas.getAnteproyectoCollection();
            for (Anteproyecto anteproyectoCollectionOrphanCheckAnteproyecto : anteproyectoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Propuestas (" + propuestas + ") cannot be destroyed since the Anteproyecto " + anteproyectoCollectionOrphanCheckAnteproyecto + " in its anteproyectoCollection field has a non-nullable propuestasId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Ideas ideasId = propuestas.getIdeasId();
            if (ideasId != null) {
                ideasId.getPropuestasCollection().remove(propuestas);
                ideasId = em.merge(ideasId);
            }
            Estados estadosId = propuestas.getEstadosId();
            if (estadosId != null) {
                estadosId.getPropuestasCollection().remove(propuestas);
                estadosId = em.merge(estadosId);
            }
            Collection<Avances> avancesCollection = propuestas.getAvancesCollection();
            for (Avances avancesCollectionAvances : avancesCollection) {
                avancesCollectionAvances.setPropuestasId(null);
                avancesCollectionAvances = em.merge(avancesCollectionAvances);
            }
            em.remove(propuestas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Propuestas> findPropuestasEntities() {
        return findPropuestasEntities(true, -1, -1);
    }

    public List<Propuestas> findPropuestasEntities(int maxResults, int firstResult) {
        return findPropuestasEntities(false, maxResults, firstResult);
    }

    private List<Propuestas> findPropuestasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Propuestas.class));
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

    public Propuestas findPropuestas(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Propuestas.class, id);
        } finally {
            em.close();
        }
    }

    public int getPropuestasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Propuestas> rt = cq.from(Propuestas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
