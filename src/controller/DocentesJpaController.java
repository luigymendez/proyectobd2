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
import modelo.Sustentaciones;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import modelo.Docentes;
import modelo.Roles;
import modelo.Usuarios;
import modelo.Horarios;
import modelo.Proyectos;
import utilerias.TecladoException;

/**
 *
 * @author ChristianFabian
 */
public class DocentesJpaController implements Serializable {

    public DocentesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("SwingBDIIPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Docentes docentes) throws PreexistingEntityException, Exception {
        if (docentes.getSustentacionesCollection() == null) {
            docentes.setSustentacionesCollection(new ArrayList<Sustentaciones>());
        }
        if (docentes.getRolesCollection() == null) {
            docentes.setRolesCollection(new ArrayList<Roles>());
        }
        if (docentes.getUsuariosCollection() == null) {
            docentes.setUsuariosCollection(new ArrayList<Usuarios>());
        }
        if (docentes.getHorariosCollection() == null) {
            docentes.setHorariosCollection(new ArrayList<Horarios>());
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
            Collection<Roles> attachedRolesCollection = new ArrayList<Roles>();
            for (Roles rolesCollectionRolesToAttach : docentes.getRolesCollection()) {
                rolesCollectionRolesToAttach = em.getReference(rolesCollectionRolesToAttach.getClass(), rolesCollectionRolesToAttach.getRolesId());
                attachedRolesCollection.add(rolesCollectionRolesToAttach);
            }
            docentes.setRolesCollection(attachedRolesCollection);
            Collection<Usuarios> attachedUsuariosCollection = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionUsuariosToAttach : docentes.getUsuariosCollection()) {
                usuariosCollectionUsuariosToAttach = em.getReference(usuariosCollectionUsuariosToAttach.getClass(), usuariosCollectionUsuariosToAttach.getId());
                attachedUsuariosCollection.add(usuariosCollectionUsuariosToAttach);
            }
            docentes.setUsuariosCollection(attachedUsuariosCollection);
            Collection<Horarios> attachedHorariosCollection = new ArrayList<Horarios>();
            for (Horarios horariosCollectionHorariosToAttach : docentes.getHorariosCollection()) {
                horariosCollectionHorariosToAttach = em.getReference(horariosCollectionHorariosToAttach.getClass(), horariosCollectionHorariosToAttach.getId());
                attachedHorariosCollection.add(horariosCollectionHorariosToAttach);
            }
            docentes.setHorariosCollection(attachedHorariosCollection);
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
            for (Roles rolesCollectionRoles : docentes.getRolesCollection()) {
                rolesCollectionRoles.getDocentesCollection().add(docentes);
                rolesCollectionRoles = em.merge(rolesCollectionRoles);
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
            for (Horarios horariosCollectionHorarios : docentes.getHorariosCollection()) {
                Docentes oldDocentesIdentificacionOfHorariosCollectionHorarios = horariosCollectionHorarios.getDocentesIdentificacion();
                horariosCollectionHorarios.setDocentesIdentificacion(docentes);
                horariosCollectionHorarios = em.merge(horariosCollectionHorarios);
                if (oldDocentesIdentificacionOfHorariosCollectionHorarios != null) {
                    oldDocentesIdentificacionOfHorariosCollectionHorarios.getHorariosCollection().remove(horariosCollectionHorarios);
                    oldDocentesIdentificacionOfHorariosCollectionHorarios = em.merge(oldDocentesIdentificacionOfHorariosCollectionHorarios);
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
            Collection<Roles> rolesCollectionOld = persistentDocentes.getRolesCollection();
            Collection<Roles> rolesCollectionNew = docentes.getRolesCollection();
            Collection<Usuarios> usuariosCollectionOld = persistentDocentes.getUsuariosCollection();
            Collection<Usuarios> usuariosCollectionNew = docentes.getUsuariosCollection();
            Collection<Horarios> horariosCollectionOld = persistentDocentes.getHorariosCollection();
            Collection<Horarios> horariosCollectionNew = docentes.getHorariosCollection();
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
            for (Horarios horariosCollectionOldHorarios : horariosCollectionOld) {
                if (!horariosCollectionNew.contains(horariosCollectionOldHorarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Horarios " + horariosCollectionOldHorarios + " since its docentesIdentificacion field is not nullable.");
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
            Collection<Roles> attachedRolesCollectionNew = new ArrayList<Roles>();
            for (Roles rolesCollectionNewRolesToAttach : rolesCollectionNew) {
                rolesCollectionNewRolesToAttach = em.getReference(rolesCollectionNewRolesToAttach.getClass(), rolesCollectionNewRolesToAttach.getRolesId());
                attachedRolesCollectionNew.add(rolesCollectionNewRolesToAttach);
            }
            rolesCollectionNew = attachedRolesCollectionNew;
            docentes.setRolesCollection(rolesCollectionNew);
            Collection<Usuarios> attachedUsuariosCollectionNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionNewUsuariosToAttach : usuariosCollectionNew) {
                usuariosCollectionNewUsuariosToAttach = em.getReference(usuariosCollectionNewUsuariosToAttach.getClass(), usuariosCollectionNewUsuariosToAttach.getId());
                attachedUsuariosCollectionNew.add(usuariosCollectionNewUsuariosToAttach);
            }
            usuariosCollectionNew = attachedUsuariosCollectionNew;
            docentes.setUsuariosCollection(usuariosCollectionNew);
            Collection<Horarios> attachedHorariosCollectionNew = new ArrayList<Horarios>();
            for (Horarios horariosCollectionNewHorariosToAttach : horariosCollectionNew) {
                horariosCollectionNewHorariosToAttach = em.getReference(horariosCollectionNewHorariosToAttach.getClass(), horariosCollectionNewHorariosToAttach.getId());
                attachedHorariosCollectionNew.add(horariosCollectionNewHorariosToAttach);
            }
            horariosCollectionNew = attachedHorariosCollectionNew;
            docentes.setHorariosCollection(horariosCollectionNew);
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
            for (Roles rolesCollectionOldRoles : rolesCollectionOld) {
                if (!rolesCollectionNew.contains(rolesCollectionOldRoles)) {
                    rolesCollectionOldRoles.getDocentesCollection().remove(docentes);
                    rolesCollectionOldRoles = em.merge(rolesCollectionOldRoles);
                }
            }
            for (Roles rolesCollectionNewRoles : rolesCollectionNew) {
                if (!rolesCollectionOld.contains(rolesCollectionNewRoles)) {
                    rolesCollectionNewRoles.getDocentesCollection().add(docentes);
                    rolesCollectionNewRoles = em.merge(rolesCollectionNewRoles);
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
            for (Horarios horariosCollectionNewHorarios : horariosCollectionNew) {
                if (!horariosCollectionOld.contains(horariosCollectionNewHorarios)) {
                    Docentes oldDocentesIdentificacionOfHorariosCollectionNewHorarios = horariosCollectionNewHorarios.getDocentesIdentificacion();
                    horariosCollectionNewHorarios.setDocentesIdentificacion(docentes);
                    horariosCollectionNewHorarios = em.merge(horariosCollectionNewHorarios);
                    if (oldDocentesIdentificacionOfHorariosCollectionNewHorarios != null && !oldDocentesIdentificacionOfHorariosCollectionNewHorarios.equals(docentes)) {
                        oldDocentesIdentificacionOfHorariosCollectionNewHorarios.getHorariosCollection().remove(horariosCollectionNewHorarios);
                        oldDocentesIdentificacionOfHorariosCollectionNewHorarios = em.merge(oldDocentesIdentificacionOfHorariosCollectionNewHorarios);
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
            Collection<Horarios> horariosCollectionOrphanCheck = docentes.getHorariosCollection();
            for (Horarios horariosCollectionOrphanCheckHorarios : horariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Docentes (" + docentes + ") cannot be destroyed since the Horarios " + horariosCollectionOrphanCheckHorarios + " in its horariosCollection field has a non-nullable docentesIdentificacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Sustentaciones> sustentacionesCollection = docentes.getSustentacionesCollection();
            for (Sustentaciones sustentacionesCollectionSustentaciones : sustentacionesCollection) {
                sustentacionesCollectionSustentaciones.getDocentesCollection().remove(docentes);
                sustentacionesCollectionSustentaciones = em.merge(sustentacionesCollectionSustentaciones);
            }
            Collection<Roles> rolesCollection = docentes.getRolesCollection();
            for (Roles rolesCollectionRoles : rolesCollection) {
                rolesCollectionRoles.getDocentesCollection().remove(docentes);
                rolesCollectionRoles = em.merge(rolesCollectionRoles);
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
    
    
    
    
    
    
    
        /**
     * Este metodo hace una consulta con un like, se hace un like inteligente porque
     * se recibe un valor de busqueda que puede ser la identificacion o un nombre
     * retorna los docentes con rol de director
     * @param valorDeBusqueda
     * @return 
     */
    public List<Docentes> encontrarCoincidenciasPorIdNombre(String valorDeBusqueda) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Docentes> criteriaQuery = cb.createQuery(Docentes.class);
        Root<Docentes> c = criteriaQuery.from(Docentes.class);
        criteriaQuery.select(c);
        Expression<String> columnaDeBusqueda;
        //Como el valor de busqueda puede ser un numero o un nombre , intentamos parsearlo a un long
        // para ver si se hace la consulta por la identificacion , sino se hace por el nombre
        long ident = TecladoException.getLong(valorDeBusqueda);
        if (ident > 0) {
            //Si el valor se pudo parsear la columna de busqueda sera la identificacion
            columnaDeBusqueda= c.get("identificacion");
        } else {
            //Sino la columna de busqueda sera el nombres
            columnaDeBusqueda= c.get("nombres");
        } 
        criteriaQuery.where(
                        cb.like(columnaDeBusqueda,"%"+ valorDeBusqueda+"%")
                
        );
        TypedQuery<Docentes> query = em.createQuery(criteriaQuery);
        //para esta consulta con like solo entregaremos como maximo 10 resultados que se mostraran en la tabla
        return query.getResultList();
        
    }
    
}
