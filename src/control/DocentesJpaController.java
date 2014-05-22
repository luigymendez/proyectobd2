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
import modelo.Sustentaciones;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Docentes;
import modelo.Usuarios;
import modelo.Proyectos;

/**
 *
 * @author ChristianFabian
 */
public class DocentesJpaController implements Serializable {

    public DocentesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Docentes docentes) throws PreexistingEntityException, Exception {
        if (docentes.getSustentacionesCollection() == null) {
            docentes.setSustentacionesCollection(new ArrayList<Sustentaciones>());
        }
        if (docentes.getUsuariosCollection() == null) {
            docentes.setUsuariosCollection(new ArrayList<Usuarios>());
        }
        if (docentes.getProyectosCollection() == null) {
            docentes.setProyectosCollection(new ArrayList<Proyectos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Sustentaciones> attachedSustentacionesCollection = new ArrayList<Sustentaciones>();
            for (Sustentaciones sustentacionesCollectionSustentacionesToAttach : docentes.getSustentacionesCollection()) {
                sustentacionesCollectionSustentacionesToAttach = em.getReference(sustentacionesCollectionSustentacionesToAttach.getClass(), sustentacionesCollectionSustentacionesToAttach.getId());
                attachedSustentacionesCollection.add(sustentacionesCollectionSustentacionesToAttach);
            }
            docentes.setSustentacionesCollection(attachedSustentacionesCollection);
            Collection<Usuarios> attachedUsuariosCollection = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionUsuariosToAttach : docentes.getUsuariosCollection()) {
                usuariosCollectionUsuariosToAttach = em.getReference(usuariosCollectionUsuariosToAttach.getClass(), usuariosCollectionUsuariosToAttach.getId());
                attachedUsuariosCollection.add(usuariosCollectionUsuariosToAttach);
            }
            docentes.setUsuariosCollection(attachedUsuariosCollection);
            Collection<Proyectos> attachedProyectosCollection = new ArrayList<Proyectos>();
            for (Proyectos proyectosCollectionProyectosToAttach : docentes.getProyectosCollection()) {
                proyectosCollectionProyectosToAttach = em.getReference(proyectosCollectionProyectosToAttach.getClass(), proyectosCollectionProyectosToAttach.getId());
                attachedProyectosCollection.add(proyectosCollectionProyectosToAttach);
            }
            docentes.setProyectosCollection(attachedProyectosCollection);
            em.persist(docentes);
            for (Sustentaciones sustentacionesCollectionSustentaciones : docentes.getSustentacionesCollection()) {
                sustentacionesCollectionSustentaciones.getDocentesCollection().add(docentes);
                sustentacionesCollectionSustentaciones = em.merge(sustentacionesCollectionSustentaciones);
            }
            for (Usuarios usuariosCollectionUsuarios : docentes.getUsuariosCollection()) {
                Docentes oldDocentesIdentificacionOfUsuariosCollectionUsuarios = usuariosCollectionUsuarios.getDocentesIdentificacion();
                usuariosCollectionUsuarios.setDocentesIdentificacion(docentes);
                usuariosCollectionUsuarios = em.merge(usuariosCollectionUsuarios);
                if (oldDocentesIdentificacionOfUsuariosCollectionUsuarios != null) {
                    oldDocentesIdentificacionOfUsuariosCollectionUsuarios.getUsuariosCollection().remove(usuariosCollectionUsuarios);
                    oldDocentesIdentificacionOfUsuariosCollectionUsuarios = em.merge(oldDocentesIdentificacionOfUsuariosCollectionUsuarios);
                }
            }
            for (Proyectos proyectosCollectionProyectos : docentes.getProyectosCollection()) {
                Docentes oldDocentesIdentificacionOfProyectosCollectionProyectos = proyectosCollectionProyectos.getDocentesIdentificacion();
                proyectosCollectionProyectos.setDocentesIdentificacion(docentes);
                proyectosCollectionProyectos = em.merge(proyectosCollectionProyectos);
                if (oldDocentesIdentificacionOfProyectosCollectionProyectos != null) {
                    oldDocentesIdentificacionOfProyectosCollectionProyectos.getProyectosCollection().remove(proyectosCollectionProyectos);
                    oldDocentesIdentificacionOfProyectosCollectionProyectos = em.merge(oldDocentesIdentificacionOfProyectosCollectionProyectos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDocentes(docentes.getIdentificacion()) != null) {
                throw new PreexistingEntityException("Docentes " + docentes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Docentes docentes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Docentes persistentDocentes = em.find(Docentes.class, docentes.getIdentificacion());
            Collection<Sustentaciones> sustentacionesCollectionOld = persistentDocentes.getSustentacionesCollection();
            Collection<Sustentaciones> sustentacionesCollectionNew = docentes.getSustentacionesCollection();
            Collection<Usuarios> usuariosCollectionOld = persistentDocentes.getUsuariosCollection();
            Collection<Usuarios> usuariosCollectionNew = docentes.getUsuariosCollection();
            Collection<Proyectos> proyectosCollectionOld = persistentDocentes.getProyectosCollection();
            Collection<Proyectos> proyectosCollectionNew = docentes.getProyectosCollection();
            List<String> illegalOrphanMessages = null;
            for (Usuarios usuariosCollectionOldUsuarios : usuariosCollectionOld) {
                if (!usuariosCollectionNew.contains(usuariosCollectionOldUsuarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuarios " + usuariosCollectionOldUsuarios + " since its docentesIdentificacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Sustentaciones> attachedSustentacionesCollectionNew = new ArrayList<Sustentaciones>();
            for (Sustentaciones sustentacionesCollectionNewSustentacionesToAttach : sustentacionesCollectionNew) {
                sustentacionesCollectionNewSustentacionesToAttach = em.getReference(sustentacionesCollectionNewSustentacionesToAttach.getClass(), sustentacionesCollectionNewSustentacionesToAttach.getId());
                attachedSustentacionesCollectionNew.add(sustentacionesCollectionNewSustentacionesToAttach);
            }
            sustentacionesCollectionNew = attachedSustentacionesCollectionNew;
            docentes.setSustentacionesCollection(sustentacionesCollectionNew);
            Collection<Usuarios> attachedUsuariosCollectionNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionNewUsuariosToAttach : usuariosCollectionNew) {
                usuariosCollectionNewUsuariosToAttach = em.getReference(usuariosCollectionNewUsuariosToAttach.getClass(), usuariosCollectionNewUsuariosToAttach.getId());
                attachedUsuariosCollectionNew.add(usuariosCollectionNewUsuariosToAttach);
            }
            usuariosCollectionNew = attachedUsuariosCollectionNew;
            docentes.setUsuariosCollection(usuariosCollectionNew);
            Collection<Proyectos> attachedProyectosCollectionNew = new ArrayList<Proyectos>();
            for (Proyectos proyectosCollectionNewProyectosToAttach : proyectosCollectionNew) {
                proyectosCollectionNewProyectosToAttach = em.getReference(proyectosCollectionNewProyectosToAttach.getClass(), proyectosCollectionNewProyectosToAttach.getId());
                attachedProyectosCollectionNew.add(proyectosCollectionNewProyectosToAttach);
            }
            proyectosCollectionNew = attachedProyectosCollectionNew;
            docentes.setProyectosCollection(proyectosCollectionNew);
            docentes = em.merge(docentes);
            for (Sustentaciones sustentacionesCollectionOldSustentaciones : sustentacionesCollectionOld) {
                if (!sustentacionesCollectionNew.contains(sustentacionesCollectionOldSustentaciones)) {
                    sustentacionesCollectionOldSustentaciones.getDocentesCollection().remove(docentes);
                    sustentacionesCollectionOldSustentaciones = em.merge(sustentacionesCollectionOldSustentaciones);
                }
            }
            for (Sustentaciones sustentacionesCollectionNewSustentaciones : sustentacionesCollectionNew) {
                if (!sustentacionesCollectionOld.contains(sustentacionesCollectionNewSustentaciones)) {
                    sustentacionesCollectionNewSustentaciones.getDocentesCollection().add(docentes);
                    sustentacionesCollectionNewSustentaciones = em.merge(sustentacionesCollectionNewSustentaciones);
                }
            }
            for (Usuarios usuariosCollectionNewUsuarios : usuariosCollectionNew) {
                if (!usuariosCollectionOld.contains(usuariosCollectionNewUsuarios)) {
                    Docentes oldDocentesIdentificacionOfUsuariosCollectionNewUsuarios = usuariosCollectionNewUsuarios.getDocentesIdentificacion();
                    usuariosCollectionNewUsuarios.setDocentesIdentificacion(docentes);
                    usuariosCollectionNewUsuarios = em.merge(usuariosCollectionNewUsuarios);
                    if (oldDocentesIdentificacionOfUsuariosCollectionNewUsuarios != null && !oldDocentesIdentificacionOfUsuariosCollectionNewUsuarios.equals(docentes)) {
                        oldDocentesIdentificacionOfUsuariosCollectionNewUsuarios.getUsuariosCollection().remove(usuariosCollectionNewUsuarios);
                        oldDocentesIdentificacionOfUsuariosCollectionNewUsuarios = em.merge(oldDocentesIdentificacionOfUsuariosCollectionNewUsuarios);
                    }
                }
            }
            for (Proyectos proyectosCollectionOldProyectos : proyectosCollectionOld) {
                if (!proyectosCollectionNew.contains(proyectosCollectionOldProyectos)) {
                    proyectosCollectionOldProyectos.setDocentesIdentificacion(null);
                    proyectosCollectionOldProyectos = em.merge(proyectosCollectionOldProyectos);
                }
            }
            for (Proyectos proyectosCollectionNewProyectos : proyectosCollectionNew) {
                if (!proyectosCollectionOld.contains(proyectosCollectionNewProyectos)) {
                    Docentes oldDocentesIdentificacionOfProyectosCollectionNewProyectos = proyectosCollectionNewProyectos.getDocentesIdentificacion();
                    proyectosCollectionNewProyectos.setDocentesIdentificacion(docentes);
                    proyectosCollectionNewProyectos = em.merge(proyectosCollectionNewProyectos);
                    if (oldDocentesIdentificacionOfProyectosCollectionNewProyectos != null && !oldDocentesIdentificacionOfProyectosCollectionNewProyectos.equals(docentes)) {
                        oldDocentesIdentificacionOfProyectosCollectionNewProyectos.getProyectosCollection().remove(proyectosCollectionNewProyectos);
                        oldDocentesIdentificacionOfProyectosCollectionNewProyectos = em.merge(oldDocentesIdentificacionOfProyectosCollectionNewProyectos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = docentes.getIdentificacion();
                if (findDocentes(id) == null) {
                    throw new NonexistentEntityException("The docentes with id " + id + " no longer exists.");
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
            Docentes docentes;
            try {
                docentes = em.getReference(Docentes.class, id);
                docentes.getIdentificacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The docentes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Usuarios> usuariosCollectionOrphanCheck = docentes.getUsuariosCollection();
            for (Usuarios usuariosCollectionOrphanCheckUsuarios : usuariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Docentes (" + docentes + ") cannot be destroyed since the Usuarios " + usuariosCollectionOrphanCheckUsuarios + " in its usuariosCollection field has a non-nullable docentesIdentificacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Sustentaciones> sustentacionesCollection = docentes.getSustentacionesCollection();
            for (Sustentaciones sustentacionesCollectionSustentaciones : sustentacionesCollection) {
                sustentacionesCollectionSustentaciones.getDocentesCollection().remove(docentes);
                sustentacionesCollectionSustentaciones = em.merge(sustentacionesCollectionSustentaciones);
            }
            Collection<Proyectos> proyectosCollection = docentes.getProyectosCollection();
            for (Proyectos proyectosCollectionProyectos : proyectosCollection) {
                proyectosCollectionProyectos.setDocentesIdentificacion(null);
                proyectosCollectionProyectos = em.merge(proyectosCollectionProyectos);
            }
            em.remove(docentes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Docentes> findDocentesEntities() {
        return findDocentesEntities(true, -1, -1);
    }

    public List<Docentes> findDocentesEntities(int maxResults, int firstResult) {
        return findDocentesEntities(false, maxResults, firstResult);
    }

    private List<Docentes> findDocentesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Docentes.class));
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

    public Docentes findDocentes(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Docentes.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocentesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Docentes> rt = cq.from(Docentes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
