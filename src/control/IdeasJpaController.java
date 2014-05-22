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
import modelo.Estados;
import modelo.Integrantes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.ArchivosAdjuntos;
import modelo.Ideas;
import modelo.Propuestas;
import modelo.Observaciones;

/**
 *
 * @author ChristianFabian
 */
public class IdeasJpaController implements Serializable {

    public IdeasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ideas ideas) throws PreexistingEntityException, Exception {
        if (ideas.getIntegrantesCollection() == null) {
            ideas.setIntegrantesCollection(new ArrayList<Integrantes>());
        }
        if (ideas.getArchivosAdjuntosCollection() == null) {
            ideas.setArchivosAdjuntosCollection(new ArrayList<ArchivosAdjuntos>());
        }
        if (ideas.getPropuestasCollection() == null) {
            ideas.setPropuestasCollection(new ArrayList<Propuestas>());
        }
        if (ideas.getObservacionesCollection() == null) {
            ideas.setObservacionesCollection(new ArrayList<Observaciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estados estadosId = ideas.getEstadosId();
            if (estadosId != null) {
                estadosId = em.getReference(estadosId.getClass(), estadosId.getId());
                ideas.setEstadosId(estadosId);
            }
            Collection<Integrantes> attachedIntegrantesCollection = new ArrayList<Integrantes>();
            for (Integrantes integrantesCollectionIntegrantesToAttach : ideas.getIntegrantesCollection()) {
                integrantesCollectionIntegrantesToAttach = em.getReference(integrantesCollectionIntegrantesToAttach.getClass(), integrantesCollectionIntegrantesToAttach.getIdentificacion());
                attachedIntegrantesCollection.add(integrantesCollectionIntegrantesToAttach);
            }
            ideas.setIntegrantesCollection(attachedIntegrantesCollection);
            Collection<ArchivosAdjuntos> attachedArchivosAdjuntosCollection = new ArrayList<ArchivosAdjuntos>();
            for (ArchivosAdjuntos archivosAdjuntosCollectionArchivosAdjuntosToAttach : ideas.getArchivosAdjuntosCollection()) {
                archivosAdjuntosCollectionArchivosAdjuntosToAttach = em.getReference(archivosAdjuntosCollectionArchivosAdjuntosToAttach.getClass(), archivosAdjuntosCollectionArchivosAdjuntosToAttach.getId());
                attachedArchivosAdjuntosCollection.add(archivosAdjuntosCollectionArchivosAdjuntosToAttach);
            }
            ideas.setArchivosAdjuntosCollection(attachedArchivosAdjuntosCollection);
            Collection<Propuestas> attachedPropuestasCollection = new ArrayList<Propuestas>();
            for (Propuestas propuestasCollectionPropuestasToAttach : ideas.getPropuestasCollection()) {
                propuestasCollectionPropuestasToAttach = em.getReference(propuestasCollectionPropuestasToAttach.getClass(), propuestasCollectionPropuestasToAttach.getId());
                attachedPropuestasCollection.add(propuestasCollectionPropuestasToAttach);
            }
            ideas.setPropuestasCollection(attachedPropuestasCollection);
            Collection<Observaciones> attachedObservacionesCollection = new ArrayList<Observaciones>();
            for (Observaciones observacionesCollectionObservacionesToAttach : ideas.getObservacionesCollection()) {
                observacionesCollectionObservacionesToAttach = em.getReference(observacionesCollectionObservacionesToAttach.getClass(), observacionesCollectionObservacionesToAttach.getId());
                attachedObservacionesCollection.add(observacionesCollectionObservacionesToAttach);
            }
            ideas.setObservacionesCollection(attachedObservacionesCollection);
            em.persist(ideas);
            if (estadosId != null) {
                estadosId.getIdeasCollection().add(ideas);
                estadosId = em.merge(estadosId);
            }
            for (Integrantes integrantesCollectionIntegrantes : ideas.getIntegrantesCollection()) {
                integrantesCollectionIntegrantes.getIdeasCollection().add(ideas);
                integrantesCollectionIntegrantes = em.merge(integrantesCollectionIntegrantes);
            }
            for (ArchivosAdjuntos archivosAdjuntosCollectionArchivosAdjuntos : ideas.getArchivosAdjuntosCollection()) {
                archivosAdjuntosCollectionArchivosAdjuntos.getIdeasCollection().add(ideas);
                archivosAdjuntosCollectionArchivosAdjuntos = em.merge(archivosAdjuntosCollectionArchivosAdjuntos);
            }
            for (Propuestas propuestasCollectionPropuestas : ideas.getPropuestasCollection()) {
                Ideas oldIdeasIdOfPropuestasCollectionPropuestas = propuestasCollectionPropuestas.getIdeasId();
                propuestasCollectionPropuestas.setIdeasId(ideas);
                propuestasCollectionPropuestas = em.merge(propuestasCollectionPropuestas);
                if (oldIdeasIdOfPropuestasCollectionPropuestas != null) {
                    oldIdeasIdOfPropuestasCollectionPropuestas.getPropuestasCollection().remove(propuestasCollectionPropuestas);
                    oldIdeasIdOfPropuestasCollectionPropuestas = em.merge(oldIdeasIdOfPropuestasCollectionPropuestas);
                }
            }
            for (Observaciones observacionesCollectionObservaciones : ideas.getObservacionesCollection()) {
                Ideas oldIdeasIdOfObservacionesCollectionObservaciones = observacionesCollectionObservaciones.getIdeasId();
                observacionesCollectionObservaciones.setIdeasId(ideas);
                observacionesCollectionObservaciones = em.merge(observacionesCollectionObservaciones);
                if (oldIdeasIdOfObservacionesCollectionObservaciones != null) {
                    oldIdeasIdOfObservacionesCollectionObservaciones.getObservacionesCollection().remove(observacionesCollectionObservaciones);
                    oldIdeasIdOfObservacionesCollectionObservaciones = em.merge(oldIdeasIdOfObservacionesCollectionObservaciones);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIdeas(ideas.getId()) != null) {
                throw new PreexistingEntityException("Ideas " + ideas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ideas ideas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ideas persistentIdeas = em.find(Ideas.class, ideas.getId());
            Estados estadosIdOld = persistentIdeas.getEstadosId();
            Estados estadosIdNew = ideas.getEstadosId();
            Collection<Integrantes> integrantesCollectionOld = persistentIdeas.getIntegrantesCollection();
            Collection<Integrantes> integrantesCollectionNew = ideas.getIntegrantesCollection();
            Collection<ArchivosAdjuntos> archivosAdjuntosCollectionOld = persistentIdeas.getArchivosAdjuntosCollection();
            Collection<ArchivosAdjuntos> archivosAdjuntosCollectionNew = ideas.getArchivosAdjuntosCollection();
            Collection<Propuestas> propuestasCollectionOld = persistentIdeas.getPropuestasCollection();
            Collection<Propuestas> propuestasCollectionNew = ideas.getPropuestasCollection();
            Collection<Observaciones> observacionesCollectionOld = persistentIdeas.getObservacionesCollection();
            Collection<Observaciones> observacionesCollectionNew = ideas.getObservacionesCollection();
            List<String> illegalOrphanMessages = null;
            for (Propuestas propuestasCollectionOldPropuestas : propuestasCollectionOld) {
                if (!propuestasCollectionNew.contains(propuestasCollectionOldPropuestas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Propuestas " + propuestasCollectionOldPropuestas + " since its ideasId field is not nullable.");
                }
            }
            for (Observaciones observacionesCollectionOldObservaciones : observacionesCollectionOld) {
                if (!observacionesCollectionNew.contains(observacionesCollectionOldObservaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Observaciones " + observacionesCollectionOldObservaciones + " since its ideasId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (estadosIdNew != null) {
                estadosIdNew = em.getReference(estadosIdNew.getClass(), estadosIdNew.getId());
                ideas.setEstadosId(estadosIdNew);
            }
            Collection<Integrantes> attachedIntegrantesCollectionNew = new ArrayList<Integrantes>();
            for (Integrantes integrantesCollectionNewIntegrantesToAttach : integrantesCollectionNew) {
                integrantesCollectionNewIntegrantesToAttach = em.getReference(integrantesCollectionNewIntegrantesToAttach.getClass(), integrantesCollectionNewIntegrantesToAttach.getIdentificacion());
                attachedIntegrantesCollectionNew.add(integrantesCollectionNewIntegrantesToAttach);
            }
            integrantesCollectionNew = attachedIntegrantesCollectionNew;
            ideas.setIntegrantesCollection(integrantesCollectionNew);
            Collection<ArchivosAdjuntos> attachedArchivosAdjuntosCollectionNew = new ArrayList<ArchivosAdjuntos>();
            for (ArchivosAdjuntos archivosAdjuntosCollectionNewArchivosAdjuntosToAttach : archivosAdjuntosCollectionNew) {
                archivosAdjuntosCollectionNewArchivosAdjuntosToAttach = em.getReference(archivosAdjuntosCollectionNewArchivosAdjuntosToAttach.getClass(), archivosAdjuntosCollectionNewArchivosAdjuntosToAttach.getId());
                attachedArchivosAdjuntosCollectionNew.add(archivosAdjuntosCollectionNewArchivosAdjuntosToAttach);
            }
            archivosAdjuntosCollectionNew = attachedArchivosAdjuntosCollectionNew;
            ideas.setArchivosAdjuntosCollection(archivosAdjuntosCollectionNew);
            Collection<Propuestas> attachedPropuestasCollectionNew = new ArrayList<Propuestas>();
            for (Propuestas propuestasCollectionNewPropuestasToAttach : propuestasCollectionNew) {
                propuestasCollectionNewPropuestasToAttach = em.getReference(propuestasCollectionNewPropuestasToAttach.getClass(), propuestasCollectionNewPropuestasToAttach.getId());
                attachedPropuestasCollectionNew.add(propuestasCollectionNewPropuestasToAttach);
            }
            propuestasCollectionNew = attachedPropuestasCollectionNew;
            ideas.setPropuestasCollection(propuestasCollectionNew);
            Collection<Observaciones> attachedObservacionesCollectionNew = new ArrayList<Observaciones>();
            for (Observaciones observacionesCollectionNewObservacionesToAttach : observacionesCollectionNew) {
                observacionesCollectionNewObservacionesToAttach = em.getReference(observacionesCollectionNewObservacionesToAttach.getClass(), observacionesCollectionNewObservacionesToAttach.getId());
                attachedObservacionesCollectionNew.add(observacionesCollectionNewObservacionesToAttach);
            }
            observacionesCollectionNew = attachedObservacionesCollectionNew;
            ideas.setObservacionesCollection(observacionesCollectionNew);
            ideas = em.merge(ideas);
            if (estadosIdOld != null && !estadosIdOld.equals(estadosIdNew)) {
                estadosIdOld.getIdeasCollection().remove(ideas);
                estadosIdOld = em.merge(estadosIdOld);
            }
            if (estadosIdNew != null && !estadosIdNew.equals(estadosIdOld)) {
                estadosIdNew.getIdeasCollection().add(ideas);
                estadosIdNew = em.merge(estadosIdNew);
            }
            for (Integrantes integrantesCollectionOldIntegrantes : integrantesCollectionOld) {
                if (!integrantesCollectionNew.contains(integrantesCollectionOldIntegrantes)) {
                    integrantesCollectionOldIntegrantes.getIdeasCollection().remove(ideas);
                    integrantesCollectionOldIntegrantes = em.merge(integrantesCollectionOldIntegrantes);
                }
            }
            for (Integrantes integrantesCollectionNewIntegrantes : integrantesCollectionNew) {
                if (!integrantesCollectionOld.contains(integrantesCollectionNewIntegrantes)) {
                    integrantesCollectionNewIntegrantes.getIdeasCollection().add(ideas);
                    integrantesCollectionNewIntegrantes = em.merge(integrantesCollectionNewIntegrantes);
                }
            }
            for (ArchivosAdjuntos archivosAdjuntosCollectionOldArchivosAdjuntos : archivosAdjuntosCollectionOld) {
                if (!archivosAdjuntosCollectionNew.contains(archivosAdjuntosCollectionOldArchivosAdjuntos)) {
                    archivosAdjuntosCollectionOldArchivosAdjuntos.getIdeasCollection().remove(ideas);
                    archivosAdjuntosCollectionOldArchivosAdjuntos = em.merge(archivosAdjuntosCollectionOldArchivosAdjuntos);
                }
            }
            for (ArchivosAdjuntos archivosAdjuntosCollectionNewArchivosAdjuntos : archivosAdjuntosCollectionNew) {
                if (!archivosAdjuntosCollectionOld.contains(archivosAdjuntosCollectionNewArchivosAdjuntos)) {
                    archivosAdjuntosCollectionNewArchivosAdjuntos.getIdeasCollection().add(ideas);
                    archivosAdjuntosCollectionNewArchivosAdjuntos = em.merge(archivosAdjuntosCollectionNewArchivosAdjuntos);
                }
            }
            for (Propuestas propuestasCollectionNewPropuestas : propuestasCollectionNew) {
                if (!propuestasCollectionOld.contains(propuestasCollectionNewPropuestas)) {
                    Ideas oldIdeasIdOfPropuestasCollectionNewPropuestas = propuestasCollectionNewPropuestas.getIdeasId();
                    propuestasCollectionNewPropuestas.setIdeasId(ideas);
                    propuestasCollectionNewPropuestas = em.merge(propuestasCollectionNewPropuestas);
                    if (oldIdeasIdOfPropuestasCollectionNewPropuestas != null && !oldIdeasIdOfPropuestasCollectionNewPropuestas.equals(ideas)) {
                        oldIdeasIdOfPropuestasCollectionNewPropuestas.getPropuestasCollection().remove(propuestasCollectionNewPropuestas);
                        oldIdeasIdOfPropuestasCollectionNewPropuestas = em.merge(oldIdeasIdOfPropuestasCollectionNewPropuestas);
                    }
                }
            }
            for (Observaciones observacionesCollectionNewObservaciones : observacionesCollectionNew) {
                if (!observacionesCollectionOld.contains(observacionesCollectionNewObservaciones)) {
                    Ideas oldIdeasIdOfObservacionesCollectionNewObservaciones = observacionesCollectionNewObservaciones.getIdeasId();
                    observacionesCollectionNewObservaciones.setIdeasId(ideas);
                    observacionesCollectionNewObservaciones = em.merge(observacionesCollectionNewObservaciones);
                    if (oldIdeasIdOfObservacionesCollectionNewObservaciones != null && !oldIdeasIdOfObservacionesCollectionNewObservaciones.equals(ideas)) {
                        oldIdeasIdOfObservacionesCollectionNewObservaciones.getObservacionesCollection().remove(observacionesCollectionNewObservaciones);
                        oldIdeasIdOfObservacionesCollectionNewObservaciones = em.merge(oldIdeasIdOfObservacionesCollectionNewObservaciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = ideas.getId();
                if (findIdeas(id) == null) {
                    throw new NonexistentEntityException("The ideas with id " + id + " no longer exists.");
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
            Ideas ideas;
            try {
                ideas = em.getReference(Ideas.class, id);
                ideas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ideas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Propuestas> propuestasCollectionOrphanCheck = ideas.getPropuestasCollection();
            for (Propuestas propuestasCollectionOrphanCheckPropuestas : propuestasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ideas (" + ideas + ") cannot be destroyed since the Propuestas " + propuestasCollectionOrphanCheckPropuestas + " in its propuestasCollection field has a non-nullable ideasId field.");
            }
            Collection<Observaciones> observacionesCollectionOrphanCheck = ideas.getObservacionesCollection();
            for (Observaciones observacionesCollectionOrphanCheckObservaciones : observacionesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ideas (" + ideas + ") cannot be destroyed since the Observaciones " + observacionesCollectionOrphanCheckObservaciones + " in its observacionesCollection field has a non-nullable ideasId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Estados estadosId = ideas.getEstadosId();
            if (estadosId != null) {
                estadosId.getIdeasCollection().remove(ideas);
                estadosId = em.merge(estadosId);
            }
            Collection<Integrantes> integrantesCollection = ideas.getIntegrantesCollection();
            for (Integrantes integrantesCollectionIntegrantes : integrantesCollection) {
                integrantesCollectionIntegrantes.getIdeasCollection().remove(ideas);
                integrantesCollectionIntegrantes = em.merge(integrantesCollectionIntegrantes);
            }
            Collection<ArchivosAdjuntos> archivosAdjuntosCollection = ideas.getArchivosAdjuntosCollection();
            for (ArchivosAdjuntos archivosAdjuntosCollectionArchivosAdjuntos : archivosAdjuntosCollection) {
                archivosAdjuntosCollectionArchivosAdjuntos.getIdeasCollection().remove(ideas);
                archivosAdjuntosCollectionArchivosAdjuntos = em.merge(archivosAdjuntosCollectionArchivosAdjuntos);
            }
            em.remove(ideas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ideas> findIdeasEntities() {
        return findIdeasEntities(true, -1, -1);
    }

    public List<Ideas> findIdeasEntities(int maxResults, int firstResult) {
        return findIdeasEntities(false, maxResults, firstResult);
    }

    private List<Ideas> findIdeasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ideas.class));
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

    public Ideas findIdeas(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ideas.class, id);
        } finally {
            em.close();
        }
    }

    public int getIdeasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ideas> rt = cq.from(Ideas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
