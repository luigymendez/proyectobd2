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
import modelo.Ideas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import modelo.Propuestas;
import modelo.Proyectos;
import modelo.Documentos;
import modelo.Estados;
import modelo.Sustentaciones;

/**
 *
 * @author ChristianFabian
 */
public class EstadosJpaController implements Serializable {

    public EstadosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("SwingBDIIPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estados estados) throws PreexistingEntityException, Exception {
        if (estados.getIdeasCollection() == null) {
            estados.setIdeasCollection(new ArrayList<Ideas>());
        }
        if (estados.getPropuestasCollection() == null) {
            estados.setPropuestasCollection(new ArrayList<Propuestas>());
        }
        if (estados.getProyectosCollection() == null) {
            estados.setProyectosCollection(new ArrayList<Proyectos>());
        }
        if (estados.getDocumentosCollection() == null) {
            estados.setDocumentosCollection(new ArrayList<Documentos>());
        }
        if (estados.getSustentacionesCollection() == null) {
            estados.setSustentacionesCollection(new ArrayList<Sustentaciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Ideas> attachedIdeasCollection = new ArrayList<Ideas>();
            for (Ideas ideasCollectionIdeasToAttach : estados.getIdeasCollection()) {
                ideasCollectionIdeasToAttach = em.getReference(ideasCollectionIdeasToAttach.getClass(), ideasCollectionIdeasToAttach.getId());
                attachedIdeasCollection.add(ideasCollectionIdeasToAttach);
            }
            estados.setIdeasCollection(attachedIdeasCollection);
            Collection<Propuestas> attachedPropuestasCollection = new ArrayList<Propuestas>();
            for (Propuestas propuestasCollectionPropuestasToAttach : estados.getPropuestasCollection()) {
                propuestasCollectionPropuestasToAttach = em.getReference(propuestasCollectionPropuestasToAttach.getClass(), propuestasCollectionPropuestasToAttach.getId());
                attachedPropuestasCollection.add(propuestasCollectionPropuestasToAttach);
            }
            estados.setPropuestasCollection(attachedPropuestasCollection);
            Collection<Proyectos> attachedProyectosCollection = new ArrayList<Proyectos>();
            for (Proyectos proyectosCollectionProyectosToAttach : estados.getProyectosCollection()) {
                proyectosCollectionProyectosToAttach = em.getReference(proyectosCollectionProyectosToAttach.getClass(), proyectosCollectionProyectosToAttach.getId());
                attachedProyectosCollection.add(proyectosCollectionProyectosToAttach);
            }
            estados.setProyectosCollection(attachedProyectosCollection);
            Collection<Documentos> attachedDocumentosCollection = new ArrayList<Documentos>();
            for (Documentos documentosCollectionDocumentosToAttach : estados.getDocumentosCollection()) {
                documentosCollectionDocumentosToAttach = em.getReference(documentosCollectionDocumentosToAttach.getClass(), documentosCollectionDocumentosToAttach.getId());
                attachedDocumentosCollection.add(documentosCollectionDocumentosToAttach);
            }
            estados.setDocumentosCollection(attachedDocumentosCollection);
            Collection<Sustentaciones> attachedSustentacionesCollection = new ArrayList<Sustentaciones>();
            for (Sustentaciones sustentacionesCollectionSustentacionesToAttach : estados.getSustentacionesCollection()) {
                sustentacionesCollectionSustentacionesToAttach = em.getReference(sustentacionesCollectionSustentacionesToAttach.getClass(), sustentacionesCollectionSustentacionesToAttach.getId());
                attachedSustentacionesCollection.add(sustentacionesCollectionSustentacionesToAttach);
            }
            estados.setSustentacionesCollection(attachedSustentacionesCollection);
            em.persist(estados);
            for (Ideas ideasCollectionIdeas : estados.getIdeasCollection()) {
                Estados oldEstadosIdOfIdeasCollectionIdeas = ideasCollectionIdeas.getEstadosId();
                ideasCollectionIdeas.setEstadosId(estados);
                ideasCollectionIdeas = em.merge(ideasCollectionIdeas);
                if (oldEstadosIdOfIdeasCollectionIdeas != null) {
                    oldEstadosIdOfIdeasCollectionIdeas.getIdeasCollection().remove(ideasCollectionIdeas);
                    oldEstadosIdOfIdeasCollectionIdeas = em.merge(oldEstadosIdOfIdeasCollectionIdeas);
                }
            }
            for (Propuestas propuestasCollectionPropuestas : estados.getPropuestasCollection()) {
                Estados oldEstadosIdOfPropuestasCollectionPropuestas = propuestasCollectionPropuestas.getEstadosId();
                propuestasCollectionPropuestas.setEstadosId(estados);
                propuestasCollectionPropuestas = em.merge(propuestasCollectionPropuestas);
                if (oldEstadosIdOfPropuestasCollectionPropuestas != null) {
                    oldEstadosIdOfPropuestasCollectionPropuestas.getPropuestasCollection().remove(propuestasCollectionPropuestas);
                    oldEstadosIdOfPropuestasCollectionPropuestas = em.merge(oldEstadosIdOfPropuestasCollectionPropuestas);
                }
            }
            for (Proyectos proyectosCollectionProyectos : estados.getProyectosCollection()) {
                Estados oldEstadosIdOfProyectosCollectionProyectos = proyectosCollectionProyectos.getEstadosId();
                proyectosCollectionProyectos.setEstadosId(estados);
                proyectosCollectionProyectos = em.merge(proyectosCollectionProyectos);
                if (oldEstadosIdOfProyectosCollectionProyectos != null) {
                    oldEstadosIdOfProyectosCollectionProyectos.getProyectosCollection().remove(proyectosCollectionProyectos);
                    oldEstadosIdOfProyectosCollectionProyectos = em.merge(oldEstadosIdOfProyectosCollectionProyectos);
                }
            }
            for (Documentos documentosCollectionDocumentos : estados.getDocumentosCollection()) {
                Estados oldEstadosIdOfDocumentosCollectionDocumentos = documentosCollectionDocumentos.getEstadosId();
                documentosCollectionDocumentos.setEstadosId(estados);
                documentosCollectionDocumentos = em.merge(documentosCollectionDocumentos);
                if (oldEstadosIdOfDocumentosCollectionDocumentos != null) {
                    oldEstadosIdOfDocumentosCollectionDocumentos.getDocumentosCollection().remove(documentosCollectionDocumentos);
                    oldEstadosIdOfDocumentosCollectionDocumentos = em.merge(oldEstadosIdOfDocumentosCollectionDocumentos);
                }
            }
            for (Sustentaciones sustentacionesCollectionSustentaciones : estados.getSustentacionesCollection()) {
                Estados oldEstadosIdOfSustentacionesCollectionSustentaciones = sustentacionesCollectionSustentaciones.getEstadosId();
                sustentacionesCollectionSustentaciones.setEstadosId(estados);
                sustentacionesCollectionSustentaciones = em.merge(sustentacionesCollectionSustentaciones);
                if (oldEstadosIdOfSustentacionesCollectionSustentaciones != null) {
                    oldEstadosIdOfSustentacionesCollectionSustentaciones.getSustentacionesCollection().remove(sustentacionesCollectionSustentaciones);
                    oldEstadosIdOfSustentacionesCollectionSustentaciones = em.merge(oldEstadosIdOfSustentacionesCollectionSustentaciones);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstados(estados.getId()) != null) {
                throw new PreexistingEntityException("Estados " + estados + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estados estados) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estados persistentEstados = em.find(Estados.class, estados.getId());
            Collection<Ideas> ideasCollectionOld = persistentEstados.getIdeasCollection();
            Collection<Ideas> ideasCollectionNew = estados.getIdeasCollection();
            Collection<Propuestas> propuestasCollectionOld = persistentEstados.getPropuestasCollection();
            Collection<Propuestas> propuestasCollectionNew = estados.getPropuestasCollection();
            Collection<Proyectos> proyectosCollectionOld = persistentEstados.getProyectosCollection();
            Collection<Proyectos> proyectosCollectionNew = estados.getProyectosCollection();
            Collection<Documentos> documentosCollectionOld = persistentEstados.getDocumentosCollection();
            Collection<Documentos> documentosCollectionNew = estados.getDocumentosCollection();
            Collection<Sustentaciones> sustentacionesCollectionOld = persistentEstados.getSustentacionesCollection();
            Collection<Sustentaciones> sustentacionesCollectionNew = estados.getSustentacionesCollection();
            List<String> illegalOrphanMessages = null;
            for (Ideas ideasCollectionOldIdeas : ideasCollectionOld) {
                if (!ideasCollectionNew.contains(ideasCollectionOldIdeas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ideas " + ideasCollectionOldIdeas + " since its estadosId field is not nullable.");
                }
            }
            for (Propuestas propuestasCollectionOldPropuestas : propuestasCollectionOld) {
                if (!propuestasCollectionNew.contains(propuestasCollectionOldPropuestas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Propuestas " + propuestasCollectionOldPropuestas + " since its estadosId field is not nullable.");
                }
            }
            for (Documentos documentosCollectionOldDocumentos : documentosCollectionOld) {
                if (!documentosCollectionNew.contains(documentosCollectionOldDocumentos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Documentos " + documentosCollectionOldDocumentos + " since its estadosId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Ideas> attachedIdeasCollectionNew = new ArrayList<Ideas>();
            for (Ideas ideasCollectionNewIdeasToAttach : ideasCollectionNew) {
                ideasCollectionNewIdeasToAttach = em.getReference(ideasCollectionNewIdeasToAttach.getClass(), ideasCollectionNewIdeasToAttach.getId());
                attachedIdeasCollectionNew.add(ideasCollectionNewIdeasToAttach);
            }
            ideasCollectionNew = attachedIdeasCollectionNew;
            estados.setIdeasCollection(ideasCollectionNew);
            Collection<Propuestas> attachedPropuestasCollectionNew = new ArrayList<Propuestas>();
            for (Propuestas propuestasCollectionNewPropuestasToAttach : propuestasCollectionNew) {
                propuestasCollectionNewPropuestasToAttach = em.getReference(propuestasCollectionNewPropuestasToAttach.getClass(), propuestasCollectionNewPropuestasToAttach.getId());
                attachedPropuestasCollectionNew.add(propuestasCollectionNewPropuestasToAttach);
            }
            propuestasCollectionNew = attachedPropuestasCollectionNew;
            estados.setPropuestasCollection(propuestasCollectionNew);
            Collection<Proyectos> attachedProyectosCollectionNew = new ArrayList<Proyectos>();
            for (Proyectos proyectosCollectionNewProyectosToAttach : proyectosCollectionNew) {
                proyectosCollectionNewProyectosToAttach = em.getReference(proyectosCollectionNewProyectosToAttach.getClass(), proyectosCollectionNewProyectosToAttach.getId());
                attachedProyectosCollectionNew.add(proyectosCollectionNewProyectosToAttach);
            }
            proyectosCollectionNew = attachedProyectosCollectionNew;
            estados.setProyectosCollection(proyectosCollectionNew);
            Collection<Documentos> attachedDocumentosCollectionNew = new ArrayList<Documentos>();
            for (Documentos documentosCollectionNewDocumentosToAttach : documentosCollectionNew) {
                documentosCollectionNewDocumentosToAttach = em.getReference(documentosCollectionNewDocumentosToAttach.getClass(), documentosCollectionNewDocumentosToAttach.getId());
                attachedDocumentosCollectionNew.add(documentosCollectionNewDocumentosToAttach);
            }
            documentosCollectionNew = attachedDocumentosCollectionNew;
            estados.setDocumentosCollection(documentosCollectionNew);
            Collection<Sustentaciones> attachedSustentacionesCollectionNew = new ArrayList<Sustentaciones>();
            for (Sustentaciones sustentacionesCollectionNewSustentacionesToAttach : sustentacionesCollectionNew) {
                sustentacionesCollectionNewSustentacionesToAttach = em.getReference(sustentacionesCollectionNewSustentacionesToAttach.getClass(), sustentacionesCollectionNewSustentacionesToAttach.getId());
                attachedSustentacionesCollectionNew.add(sustentacionesCollectionNewSustentacionesToAttach);
            }
            sustentacionesCollectionNew = attachedSustentacionesCollectionNew;
            estados.setSustentacionesCollection(sustentacionesCollectionNew);
            estados = em.merge(estados);
            for (Ideas ideasCollectionNewIdeas : ideasCollectionNew) {
                if (!ideasCollectionOld.contains(ideasCollectionNewIdeas)) {
                    Estados oldEstadosIdOfIdeasCollectionNewIdeas = ideasCollectionNewIdeas.getEstadosId();
                    ideasCollectionNewIdeas.setEstadosId(estados);
                    ideasCollectionNewIdeas = em.merge(ideasCollectionNewIdeas);
                    if (oldEstadosIdOfIdeasCollectionNewIdeas != null && !oldEstadosIdOfIdeasCollectionNewIdeas.equals(estados)) {
                        oldEstadosIdOfIdeasCollectionNewIdeas.getIdeasCollection().remove(ideasCollectionNewIdeas);
                        oldEstadosIdOfIdeasCollectionNewIdeas = em.merge(oldEstadosIdOfIdeasCollectionNewIdeas);
                    }
                }
            }
            for (Propuestas propuestasCollectionNewPropuestas : propuestasCollectionNew) {
                if (!propuestasCollectionOld.contains(propuestasCollectionNewPropuestas)) {
                    Estados oldEstadosIdOfPropuestasCollectionNewPropuestas = propuestasCollectionNewPropuestas.getEstadosId();
                    propuestasCollectionNewPropuestas.setEstadosId(estados);
                    propuestasCollectionNewPropuestas = em.merge(propuestasCollectionNewPropuestas);
                    if (oldEstadosIdOfPropuestasCollectionNewPropuestas != null && !oldEstadosIdOfPropuestasCollectionNewPropuestas.equals(estados)) {
                        oldEstadosIdOfPropuestasCollectionNewPropuestas.getPropuestasCollection().remove(propuestasCollectionNewPropuestas);
                        oldEstadosIdOfPropuestasCollectionNewPropuestas = em.merge(oldEstadosIdOfPropuestasCollectionNewPropuestas);
                    }
                }
            }
            for (Proyectos proyectosCollectionOldProyectos : proyectosCollectionOld) {
                if (!proyectosCollectionNew.contains(proyectosCollectionOldProyectos)) {
                    proyectosCollectionOldProyectos.setEstadosId(null);
                    proyectosCollectionOldProyectos = em.merge(proyectosCollectionOldProyectos);
                }
            }
            for (Proyectos proyectosCollectionNewProyectos : proyectosCollectionNew) {
                if (!proyectosCollectionOld.contains(proyectosCollectionNewProyectos)) {
                    Estados oldEstadosIdOfProyectosCollectionNewProyectos = proyectosCollectionNewProyectos.getEstadosId();
                    proyectosCollectionNewProyectos.setEstadosId(estados);
                    proyectosCollectionNewProyectos = em.merge(proyectosCollectionNewProyectos);
                    if (oldEstadosIdOfProyectosCollectionNewProyectos != null && !oldEstadosIdOfProyectosCollectionNewProyectos.equals(estados)) {
                        oldEstadosIdOfProyectosCollectionNewProyectos.getProyectosCollection().remove(proyectosCollectionNewProyectos);
                        oldEstadosIdOfProyectosCollectionNewProyectos = em.merge(oldEstadosIdOfProyectosCollectionNewProyectos);
                    }
                }
            }
            for (Documentos documentosCollectionNewDocumentos : documentosCollectionNew) {
                if (!documentosCollectionOld.contains(documentosCollectionNewDocumentos)) {
                    Estados oldEstadosIdOfDocumentosCollectionNewDocumentos = documentosCollectionNewDocumentos.getEstadosId();
                    documentosCollectionNewDocumentos.setEstadosId(estados);
                    documentosCollectionNewDocumentos = em.merge(documentosCollectionNewDocumentos);
                    if (oldEstadosIdOfDocumentosCollectionNewDocumentos != null && !oldEstadosIdOfDocumentosCollectionNewDocumentos.equals(estados)) {
                        oldEstadosIdOfDocumentosCollectionNewDocumentos.getDocumentosCollection().remove(documentosCollectionNewDocumentos);
                        oldEstadosIdOfDocumentosCollectionNewDocumentos = em.merge(oldEstadosIdOfDocumentosCollectionNewDocumentos);
                    }
                }
            }
            for (Sustentaciones sustentacionesCollectionOldSustentaciones : sustentacionesCollectionOld) {
                if (!sustentacionesCollectionNew.contains(sustentacionesCollectionOldSustentaciones)) {
                    sustentacionesCollectionOldSustentaciones.setEstadosId(null);
                    sustentacionesCollectionOldSustentaciones = em.merge(sustentacionesCollectionOldSustentaciones);
                }
            }
            for (Sustentaciones sustentacionesCollectionNewSustentaciones : sustentacionesCollectionNew) {
                if (!sustentacionesCollectionOld.contains(sustentacionesCollectionNewSustentaciones)) {
                    Estados oldEstadosIdOfSustentacionesCollectionNewSustentaciones = sustentacionesCollectionNewSustentaciones.getEstadosId();
                    sustentacionesCollectionNewSustentaciones.setEstadosId(estados);
                    sustentacionesCollectionNewSustentaciones = em.merge(sustentacionesCollectionNewSustentaciones);
                    if (oldEstadosIdOfSustentacionesCollectionNewSustentaciones != null && !oldEstadosIdOfSustentacionesCollectionNewSustentaciones.equals(estados)) {
                        oldEstadosIdOfSustentacionesCollectionNewSustentaciones.getSustentacionesCollection().remove(sustentacionesCollectionNewSustentaciones);
                        oldEstadosIdOfSustentacionesCollectionNewSustentaciones = em.merge(oldEstadosIdOfSustentacionesCollectionNewSustentaciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = estados.getId();
                if (findEstados(id) == null) {
                    throw new NonexistentEntityException("The estados with id " + id + " no longer exists.");
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
            Estados estados;
            try {
                estados = em.getReference(Estados.class, id);
                estados.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estados with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Ideas> ideasCollectionOrphanCheck = estados.getIdeasCollection();
            for (Ideas ideasCollectionOrphanCheckIdeas : ideasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estados (" + estados + ") cannot be destroyed since the Ideas " + ideasCollectionOrphanCheckIdeas + " in its ideasCollection field has a non-nullable estadosId field.");
            }
            Collection<Propuestas> propuestasCollectionOrphanCheck = estados.getPropuestasCollection();
            for (Propuestas propuestasCollectionOrphanCheckPropuestas : propuestasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estados (" + estados + ") cannot be destroyed since the Propuestas " + propuestasCollectionOrphanCheckPropuestas + " in its propuestasCollection field has a non-nullable estadosId field.");
            }
            Collection<Documentos> documentosCollectionOrphanCheck = estados.getDocumentosCollection();
            for (Documentos documentosCollectionOrphanCheckDocumentos : documentosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estados (" + estados + ") cannot be destroyed since the Documentos " + documentosCollectionOrphanCheckDocumentos + " in its documentosCollection field has a non-nullable estadosId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Proyectos> proyectosCollection = estados.getProyectosCollection();
            for (Proyectos proyectosCollectionProyectos : proyectosCollection) {
                proyectosCollectionProyectos.setEstadosId(null);
                proyectosCollectionProyectos = em.merge(proyectosCollectionProyectos);
            }
            Collection<Sustentaciones> sustentacionesCollection = estados.getSustentacionesCollection();
            for (Sustentaciones sustentacionesCollectionSustentaciones : sustentacionesCollection) {
                sustentacionesCollectionSustentaciones.setEstadosId(null);
                sustentacionesCollectionSustentaciones = em.merge(sustentacionesCollectionSustentaciones);
            }
            em.remove(estados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estados> findEstadosEntities() {
        return findEstadosEntities(true, -1, -1);
    }

    public List<Estados> findEstadosEntities(int maxResults, int firstResult) {
        return findEstadosEntities(false, maxResults, firstResult);
    }

    private List<Estados> findEstadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estados.class));
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

    public Estados findEstados(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estados.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estados> rt = cq.from(Estados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
    /**
     * Este metodo retorna una lista con los estados de un modulo en especifico
     *
     * @param modulo
     * @return
     */
    public List<Estados> getEstadosByModulo(String modulo) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Estados> criteriaQuery = cb.createQuery(Estados.class);
        Root<Estados> c = criteriaQuery.from(Estados.class);
        criteriaQuery.select(c);
        criteriaQuery.where(
                cb.equal(c.get("modulo"), modulo)
        );
        TypedQuery<Estados> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }
    
}
