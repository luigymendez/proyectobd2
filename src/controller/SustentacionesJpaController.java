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
import modelo.Estados;
import modelo.EspaciosFisicos;
import modelo.ArchivosAdjuntos;
import modelo.Docentes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Recursos;
import modelo.Sustentaciones;

/**
 *
 * @author ChristianFabian
 */
public class SustentacionesJpaController implements Serializable {

    public SustentacionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sustentaciones sustentaciones) throws PreexistingEntityException, Exception {
        if (sustentaciones.getDocentesCollection() == null) {
            sustentaciones.setDocentesCollection(new ArrayList<Docentes>());
        }
        if (sustentaciones.getRecursosCollection() == null) {
            sustentaciones.setRecursosCollection(new ArrayList<Recursos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estados estadosId = sustentaciones.getEstadosId();
            if (estadosId != null) {
                estadosId = em.getReference(estadosId.getClass(), estadosId.getId());
                sustentaciones.setEstadosId(estadosId);
            }
            EspaciosFisicos espaciosFisicosId = sustentaciones.getEspaciosFisicosId();
            if (espaciosFisicosId != null) {
                espaciosFisicosId = em.getReference(espaciosFisicosId.getClass(), espaciosFisicosId.getId());
                sustentaciones.setEspaciosFisicosId(espaciosFisicosId);
            }
            ArchivosAdjuntos archivosAdjuntosId = sustentaciones.getArchivosAdjuntosId();
            if (archivosAdjuntosId != null) {
                archivosAdjuntosId = em.getReference(archivosAdjuntosId.getClass(), archivosAdjuntosId.getId());
                sustentaciones.setArchivosAdjuntosId(archivosAdjuntosId);
            }
            Collection<Docentes> attachedDocentesCollection = new ArrayList<Docentes>();
            for (Docentes docentesCollectionDocentesToAttach : sustentaciones.getDocentesCollection()) {
                docentesCollectionDocentesToAttach = em.getReference(docentesCollectionDocentesToAttach.getClass(), docentesCollectionDocentesToAttach.getIdentificacion());
                attachedDocentesCollection.add(docentesCollectionDocentesToAttach);
            }
            sustentaciones.setDocentesCollection(attachedDocentesCollection);
            Collection<Recursos> attachedRecursosCollection = new ArrayList<Recursos>();
            for (Recursos recursosCollectionRecursosToAttach : sustentaciones.getRecursosCollection()) {
                recursosCollectionRecursosToAttach = em.getReference(recursosCollectionRecursosToAttach.getClass(), recursosCollectionRecursosToAttach.getId());
                attachedRecursosCollection.add(recursosCollectionRecursosToAttach);
            }
            sustentaciones.setRecursosCollection(attachedRecursosCollection);
            em.persist(sustentaciones);
            if (estadosId != null) {
                estadosId.getSustentacionesCollection().add(sustentaciones);
                estadosId = em.merge(estadosId);
            }
            if (espaciosFisicosId != null) {
                espaciosFisicosId.getSustentacionesCollection().add(sustentaciones);
                espaciosFisicosId = em.merge(espaciosFisicosId);
            }
            if (archivosAdjuntosId != null) {
                archivosAdjuntosId.getSustentacionesCollection().add(sustentaciones);
                archivosAdjuntosId = em.merge(archivosAdjuntosId);
            }
            for (Docentes docentesCollectionDocentes : sustentaciones.getDocentesCollection()) {
                docentesCollectionDocentes.getSustentacionesCollection().add(sustentaciones);
                docentesCollectionDocentes = em.merge(docentesCollectionDocentes);
            }
            for (Recursos recursosCollectionRecursos : sustentaciones.getRecursosCollection()) {
                recursosCollectionRecursos.getSustentacionesCollection().add(sustentaciones);
                recursosCollectionRecursos = em.merge(recursosCollectionRecursos);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSustentaciones(sustentaciones.getId()) != null) {
                throw new PreexistingEntityException("Sustentaciones " + sustentaciones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sustentaciones sustentaciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sustentaciones persistentSustentaciones = em.find(Sustentaciones.class, sustentaciones.getId());
            Estados estadosIdOld = persistentSustentaciones.getEstadosId();
            Estados estadosIdNew = sustentaciones.getEstadosId();
            EspaciosFisicos espaciosFisicosIdOld = persistentSustentaciones.getEspaciosFisicosId();
            EspaciosFisicos espaciosFisicosIdNew = sustentaciones.getEspaciosFisicosId();
            ArchivosAdjuntos archivosAdjuntosIdOld = persistentSustentaciones.getArchivosAdjuntosId();
            ArchivosAdjuntos archivosAdjuntosIdNew = sustentaciones.getArchivosAdjuntosId();
            Collection<Docentes> docentesCollectionOld = persistentSustentaciones.getDocentesCollection();
            Collection<Docentes> docentesCollectionNew = sustentaciones.getDocentesCollection();
            Collection<Recursos> recursosCollectionOld = persistentSustentaciones.getRecursosCollection();
            Collection<Recursos> recursosCollectionNew = sustentaciones.getRecursosCollection();
            if (estadosIdNew != null) {
                estadosIdNew = em.getReference(estadosIdNew.getClass(), estadosIdNew.getId());
                sustentaciones.setEstadosId(estadosIdNew);
            }
            if (espaciosFisicosIdNew != null) {
                espaciosFisicosIdNew = em.getReference(espaciosFisicosIdNew.getClass(), espaciosFisicosIdNew.getId());
                sustentaciones.setEspaciosFisicosId(espaciosFisicosIdNew);
            }
            if (archivosAdjuntosIdNew != null) {
                archivosAdjuntosIdNew = em.getReference(archivosAdjuntosIdNew.getClass(), archivosAdjuntosIdNew.getId());
                sustentaciones.setArchivosAdjuntosId(archivosAdjuntosIdNew);
            }
            Collection<Docentes> attachedDocentesCollectionNew = new ArrayList<Docentes>();
            for (Docentes docentesCollectionNewDocentesToAttach : docentesCollectionNew) {
                docentesCollectionNewDocentesToAttach = em.getReference(docentesCollectionNewDocentesToAttach.getClass(), docentesCollectionNewDocentesToAttach.getIdentificacion());
                attachedDocentesCollectionNew.add(docentesCollectionNewDocentesToAttach);
            }
            docentesCollectionNew = attachedDocentesCollectionNew;
            sustentaciones.setDocentesCollection(docentesCollectionNew);
            Collection<Recursos> attachedRecursosCollectionNew = new ArrayList<Recursos>();
            for (Recursos recursosCollectionNewRecursosToAttach : recursosCollectionNew) {
                recursosCollectionNewRecursosToAttach = em.getReference(recursosCollectionNewRecursosToAttach.getClass(), recursosCollectionNewRecursosToAttach.getId());
                attachedRecursosCollectionNew.add(recursosCollectionNewRecursosToAttach);
            }
            recursosCollectionNew = attachedRecursosCollectionNew;
            sustentaciones.setRecursosCollection(recursosCollectionNew);
            sustentaciones = em.merge(sustentaciones);
            if (estadosIdOld != null && !estadosIdOld.equals(estadosIdNew)) {
                estadosIdOld.getSustentacionesCollection().remove(sustentaciones);
                estadosIdOld = em.merge(estadosIdOld);
            }
            if (estadosIdNew != null && !estadosIdNew.equals(estadosIdOld)) {
                estadosIdNew.getSustentacionesCollection().add(sustentaciones);
                estadosIdNew = em.merge(estadosIdNew);
            }
            if (espaciosFisicosIdOld != null && !espaciosFisicosIdOld.equals(espaciosFisicosIdNew)) {
                espaciosFisicosIdOld.getSustentacionesCollection().remove(sustentaciones);
                espaciosFisicosIdOld = em.merge(espaciosFisicosIdOld);
            }
            if (espaciosFisicosIdNew != null && !espaciosFisicosIdNew.equals(espaciosFisicosIdOld)) {
                espaciosFisicosIdNew.getSustentacionesCollection().add(sustentaciones);
                espaciosFisicosIdNew = em.merge(espaciosFisicosIdNew);
            }
            if (archivosAdjuntosIdOld != null && !archivosAdjuntosIdOld.equals(archivosAdjuntosIdNew)) {
                archivosAdjuntosIdOld.getSustentacionesCollection().remove(sustentaciones);
                archivosAdjuntosIdOld = em.merge(archivosAdjuntosIdOld);
            }
            if (archivosAdjuntosIdNew != null && !archivosAdjuntosIdNew.equals(archivosAdjuntosIdOld)) {
                archivosAdjuntosIdNew.getSustentacionesCollection().add(sustentaciones);
                archivosAdjuntosIdNew = em.merge(archivosAdjuntosIdNew);
            }
            for (Docentes docentesCollectionOldDocentes : docentesCollectionOld) {
                if (!docentesCollectionNew.contains(docentesCollectionOldDocentes)) {
                    docentesCollectionOldDocentes.getSustentacionesCollection().remove(sustentaciones);
                    docentesCollectionOldDocentes = em.merge(docentesCollectionOldDocentes);
                }
            }
            for (Docentes docentesCollectionNewDocentes : docentesCollectionNew) {
                if (!docentesCollectionOld.contains(docentesCollectionNewDocentes)) {
                    docentesCollectionNewDocentes.getSustentacionesCollection().add(sustentaciones);
                    docentesCollectionNewDocentes = em.merge(docentesCollectionNewDocentes);
                }
            }
            for (Recursos recursosCollectionOldRecursos : recursosCollectionOld) {
                if (!recursosCollectionNew.contains(recursosCollectionOldRecursos)) {
                    recursosCollectionOldRecursos.getSustentacionesCollection().remove(sustentaciones);
                    recursosCollectionOldRecursos = em.merge(recursosCollectionOldRecursos);
                }
            }
            for (Recursos recursosCollectionNewRecursos : recursosCollectionNew) {
                if (!recursosCollectionOld.contains(recursosCollectionNewRecursos)) {
                    recursosCollectionNewRecursos.getSustentacionesCollection().add(sustentaciones);
                    recursosCollectionNewRecursos = em.merge(recursosCollectionNewRecursos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = sustentaciones.getId();
                if (findSustentaciones(id) == null) {
                    throw new NonexistentEntityException("The sustentaciones with id " + id + " no longer exists.");
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
            Sustentaciones sustentaciones;
            try {
                sustentaciones = em.getReference(Sustentaciones.class, id);
                sustentaciones.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sustentaciones with id " + id + " no longer exists.", enfe);
            }
            Estados estadosId = sustentaciones.getEstadosId();
            if (estadosId != null) {
                estadosId.getSustentacionesCollection().remove(sustentaciones);
                estadosId = em.merge(estadosId);
            }
            EspaciosFisicos espaciosFisicosId = sustentaciones.getEspaciosFisicosId();
            if (espaciosFisicosId != null) {
                espaciosFisicosId.getSustentacionesCollection().remove(sustentaciones);
                espaciosFisicosId = em.merge(espaciosFisicosId);
            }
            ArchivosAdjuntos archivosAdjuntosId = sustentaciones.getArchivosAdjuntosId();
            if (archivosAdjuntosId != null) {
                archivosAdjuntosId.getSustentacionesCollection().remove(sustentaciones);
                archivosAdjuntosId = em.merge(archivosAdjuntosId);
            }
            Collection<Docentes> docentesCollection = sustentaciones.getDocentesCollection();
            for (Docentes docentesCollectionDocentes : docentesCollection) {
                docentesCollectionDocentes.getSustentacionesCollection().remove(sustentaciones);
                docentesCollectionDocentes = em.merge(docentesCollectionDocentes);
            }
            Collection<Recursos> recursosCollection = sustentaciones.getRecursosCollection();
            for (Recursos recursosCollectionRecursos : recursosCollection) {
                recursosCollectionRecursos.getSustentacionesCollection().remove(sustentaciones);
                recursosCollectionRecursos = em.merge(recursosCollectionRecursos);
            }
            em.remove(sustentaciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sustentaciones> findSustentacionesEntities() {
        return findSustentacionesEntities(true, -1, -1);
    }

    public List<Sustentaciones> findSustentacionesEntities(int maxResults, int firstResult) {
        return findSustentacionesEntities(false, maxResults, firstResult);
    }

    private List<Sustentaciones> findSustentacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sustentaciones.class));
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

    public Sustentaciones findSustentaciones(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sustentaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getSustentacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sustentaciones> rt = cq.from(Sustentaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
