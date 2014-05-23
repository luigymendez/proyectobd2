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
import modelo.Horarios;
import modelo.Estados;
import modelo.Empresas;
import modelo.Docentes;
import modelo.Anteproyecto;
import modelo.Asesorias;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Entregas;
import modelo.Proyectos;

/**
 *
 * @author ChristianFabian
 */
public class ProyectosJpaController implements Serializable {

    public ProyectosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proyectos proyectos) throws PreexistingEntityException, Exception {
        if (proyectos.getAsesoriasCollection() == null) {
            proyectos.setAsesoriasCollection(new ArrayList<Asesorias>());
        }
        if (proyectos.getEntregasCollection() == null) {
            proyectos.setEntregasCollection(new ArrayList<Entregas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Horarios horariosId = proyectos.getHorariosId();
            if (horariosId != null) {
                horariosId = em.getReference(horariosId.getClass(), horariosId.getId());
                proyectos.setHorariosId(horariosId);
            }
            Estados estadosId = proyectos.getEstadosId();
            if (estadosId != null) {
                estadosId = em.getReference(estadosId.getClass(), estadosId.getId());
                proyectos.setEstadosId(estadosId);
            }
            Empresas empresasId = proyectos.getEmpresasId();
            if (empresasId != null) {
                empresasId = em.getReference(empresasId.getClass(), empresasId.getId());
                proyectos.setEmpresasId(empresasId);
            }
            Docentes docentesIdentificacion = proyectos.getDocentesIdentificacion();
            if (docentesIdentificacion != null) {
                docentesIdentificacion = em.getReference(docentesIdentificacion.getClass(), docentesIdentificacion.getIdentificacion());
                proyectos.setDocentesIdentificacion(docentesIdentificacion);
            }
            Anteproyecto anteproyectoId = proyectos.getAnteproyectoId();
            if (anteproyectoId != null) {
                anteproyectoId = em.getReference(anteproyectoId.getClass(), anteproyectoId.getId());
                proyectos.setAnteproyectoId(anteproyectoId);
            }
            Collection<Asesorias> attachedAsesoriasCollection = new ArrayList<Asesorias>();
            for (Asesorias asesoriasCollectionAsesoriasToAttach : proyectos.getAsesoriasCollection()) {
                asesoriasCollectionAsesoriasToAttach = em.getReference(asesoriasCollectionAsesoriasToAttach.getClass(), asesoriasCollectionAsesoriasToAttach.getId());
                attachedAsesoriasCollection.add(asesoriasCollectionAsesoriasToAttach);
            }
            proyectos.setAsesoriasCollection(attachedAsesoriasCollection);
            Collection<Entregas> attachedEntregasCollection = new ArrayList<Entregas>();
            for (Entregas entregasCollectionEntregasToAttach : proyectos.getEntregasCollection()) {
                entregasCollectionEntregasToAttach = em.getReference(entregasCollectionEntregasToAttach.getClass(), entregasCollectionEntregasToAttach.getId());
                attachedEntregasCollection.add(entregasCollectionEntregasToAttach);
            }
            proyectos.setEntregasCollection(attachedEntregasCollection);
            em.persist(proyectos);
            if (horariosId != null) {
                horariosId.getProyectosCollection().add(proyectos);
                horariosId = em.merge(horariosId);
            }
            if (estadosId != null) {
                estadosId.getProyectosCollection().add(proyectos);
                estadosId = em.merge(estadosId);
            }
            if (empresasId != null) {
                empresasId.getProyectosCollection().add(proyectos);
                empresasId = em.merge(empresasId);
            }
            if (docentesIdentificacion != null) {
                docentesIdentificacion.getProyectosCollection().add(proyectos);
                docentesIdentificacion = em.merge(docentesIdentificacion);
            }
            if (anteproyectoId != null) {
                anteproyectoId.getProyectosCollection().add(proyectos);
                anteproyectoId = em.merge(anteproyectoId);
            }
            for (Asesorias asesoriasCollectionAsesorias : proyectos.getAsesoriasCollection()) {
                Proyectos oldProyectosIdOfAsesoriasCollectionAsesorias = asesoriasCollectionAsesorias.getProyectosId();
                asesoriasCollectionAsesorias.setProyectosId(proyectos);
                asesoriasCollectionAsesorias = em.merge(asesoriasCollectionAsesorias);
                if (oldProyectosIdOfAsesoriasCollectionAsesorias != null) {
                    oldProyectosIdOfAsesoriasCollectionAsesorias.getAsesoriasCollection().remove(asesoriasCollectionAsesorias);
                    oldProyectosIdOfAsesoriasCollectionAsesorias = em.merge(oldProyectosIdOfAsesoriasCollectionAsesorias);
                }
            }
            for (Entregas entregasCollectionEntregas : proyectos.getEntregasCollection()) {
                Proyectos oldProyectosIdOfEntregasCollectionEntregas = entregasCollectionEntregas.getProyectosId();
                entregasCollectionEntregas.setProyectosId(proyectos);
                entregasCollectionEntregas = em.merge(entregasCollectionEntregas);
                if (oldProyectosIdOfEntregasCollectionEntregas != null) {
                    oldProyectosIdOfEntregasCollectionEntregas.getEntregasCollection().remove(entregasCollectionEntregas);
                    oldProyectosIdOfEntregasCollectionEntregas = em.merge(oldProyectosIdOfEntregasCollectionEntregas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProyectos(proyectos.getId()) != null) {
                throw new PreexistingEntityException("Proyectos " + proyectos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proyectos proyectos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyectos persistentProyectos = em.find(Proyectos.class, proyectos.getId());
            Horarios horariosIdOld = persistentProyectos.getHorariosId();
            Horarios horariosIdNew = proyectos.getHorariosId();
            Estados estadosIdOld = persistentProyectos.getEstadosId();
            Estados estadosIdNew = proyectos.getEstadosId();
            Empresas empresasIdOld = persistentProyectos.getEmpresasId();
            Empresas empresasIdNew = proyectos.getEmpresasId();
            Docentes docentesIdentificacionOld = persistentProyectos.getDocentesIdentificacion();
            Docentes docentesIdentificacionNew = proyectos.getDocentesIdentificacion();
            Anteproyecto anteproyectoIdOld = persistentProyectos.getAnteproyectoId();
            Anteproyecto anteproyectoIdNew = proyectos.getAnteproyectoId();
            Collection<Asesorias> asesoriasCollectionOld = persistentProyectos.getAsesoriasCollection();
            Collection<Asesorias> asesoriasCollectionNew = proyectos.getAsesoriasCollection();
            Collection<Entregas> entregasCollectionOld = persistentProyectos.getEntregasCollection();
            Collection<Entregas> entregasCollectionNew = proyectos.getEntregasCollection();
            List<String> illegalOrphanMessages = null;
            for (Asesorias asesoriasCollectionOldAsesorias : asesoriasCollectionOld) {
                if (!asesoriasCollectionNew.contains(asesoriasCollectionOldAsesorias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Asesorias " + asesoriasCollectionOldAsesorias + " since its proyectosId field is not nullable.");
                }
            }
            for (Entregas entregasCollectionOldEntregas : entregasCollectionOld) {
                if (!entregasCollectionNew.contains(entregasCollectionOldEntregas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Entregas " + entregasCollectionOldEntregas + " since its proyectosId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (horariosIdNew != null) {
                horariosIdNew = em.getReference(horariosIdNew.getClass(), horariosIdNew.getId());
                proyectos.setHorariosId(horariosIdNew);
            }
            if (estadosIdNew != null) {
                estadosIdNew = em.getReference(estadosIdNew.getClass(), estadosIdNew.getId());
                proyectos.setEstadosId(estadosIdNew);
            }
            if (empresasIdNew != null) {
                empresasIdNew = em.getReference(empresasIdNew.getClass(), empresasIdNew.getId());
                proyectos.setEmpresasId(empresasIdNew);
            }
            if (docentesIdentificacionNew != null) {
                docentesIdentificacionNew = em.getReference(docentesIdentificacionNew.getClass(), docentesIdentificacionNew.getIdentificacion());
                proyectos.setDocentesIdentificacion(docentesIdentificacionNew);
            }
            if (anteproyectoIdNew != null) {
                anteproyectoIdNew = em.getReference(anteproyectoIdNew.getClass(), anteproyectoIdNew.getId());
                proyectos.setAnteproyectoId(anteproyectoIdNew);
            }
            Collection<Asesorias> attachedAsesoriasCollectionNew = new ArrayList<Asesorias>();
            for (Asesorias asesoriasCollectionNewAsesoriasToAttach : asesoriasCollectionNew) {
                asesoriasCollectionNewAsesoriasToAttach = em.getReference(asesoriasCollectionNewAsesoriasToAttach.getClass(), asesoriasCollectionNewAsesoriasToAttach.getId());
                attachedAsesoriasCollectionNew.add(asesoriasCollectionNewAsesoriasToAttach);
            }
            asesoriasCollectionNew = attachedAsesoriasCollectionNew;
            proyectos.setAsesoriasCollection(asesoriasCollectionNew);
            Collection<Entregas> attachedEntregasCollectionNew = new ArrayList<Entregas>();
            for (Entregas entregasCollectionNewEntregasToAttach : entregasCollectionNew) {
                entregasCollectionNewEntregasToAttach = em.getReference(entregasCollectionNewEntregasToAttach.getClass(), entregasCollectionNewEntregasToAttach.getId());
                attachedEntregasCollectionNew.add(entregasCollectionNewEntregasToAttach);
            }
            entregasCollectionNew = attachedEntregasCollectionNew;
            proyectos.setEntregasCollection(entregasCollectionNew);
            proyectos = em.merge(proyectos);
            if (horariosIdOld != null && !horariosIdOld.equals(horariosIdNew)) {
                horariosIdOld.getProyectosCollection().remove(proyectos);
                horariosIdOld = em.merge(horariosIdOld);
            }
            if (horariosIdNew != null && !horariosIdNew.equals(horariosIdOld)) {
                horariosIdNew.getProyectosCollection().add(proyectos);
                horariosIdNew = em.merge(horariosIdNew);
            }
            if (estadosIdOld != null && !estadosIdOld.equals(estadosIdNew)) {
                estadosIdOld.getProyectosCollection().remove(proyectos);
                estadosIdOld = em.merge(estadosIdOld);
            }
            if (estadosIdNew != null && !estadosIdNew.equals(estadosIdOld)) {
                estadosIdNew.getProyectosCollection().add(proyectos);
                estadosIdNew = em.merge(estadosIdNew);
            }
            if (empresasIdOld != null && !empresasIdOld.equals(empresasIdNew)) {
                empresasIdOld.getProyectosCollection().remove(proyectos);
                empresasIdOld = em.merge(empresasIdOld);
            }
            if (empresasIdNew != null && !empresasIdNew.equals(empresasIdOld)) {
                empresasIdNew.getProyectosCollection().add(proyectos);
                empresasIdNew = em.merge(empresasIdNew);
            }
            if (docentesIdentificacionOld != null && !docentesIdentificacionOld.equals(docentesIdentificacionNew)) {
                docentesIdentificacionOld.getProyectosCollection().remove(proyectos);
                docentesIdentificacionOld = em.merge(docentesIdentificacionOld);
            }
            if (docentesIdentificacionNew != null && !docentesIdentificacionNew.equals(docentesIdentificacionOld)) {
                docentesIdentificacionNew.getProyectosCollection().add(proyectos);
                docentesIdentificacionNew = em.merge(docentesIdentificacionNew);
            }
            if (anteproyectoIdOld != null && !anteproyectoIdOld.equals(anteproyectoIdNew)) {
                anteproyectoIdOld.getProyectosCollection().remove(proyectos);
                anteproyectoIdOld = em.merge(anteproyectoIdOld);
            }
            if (anteproyectoIdNew != null && !anteproyectoIdNew.equals(anteproyectoIdOld)) {
                anteproyectoIdNew.getProyectosCollection().add(proyectos);
                anteproyectoIdNew = em.merge(anteproyectoIdNew);
            }
            for (Asesorias asesoriasCollectionNewAsesorias : asesoriasCollectionNew) {
                if (!asesoriasCollectionOld.contains(asesoriasCollectionNewAsesorias)) {
                    Proyectos oldProyectosIdOfAsesoriasCollectionNewAsesorias = asesoriasCollectionNewAsesorias.getProyectosId();
                    asesoriasCollectionNewAsesorias.setProyectosId(proyectos);
                    asesoriasCollectionNewAsesorias = em.merge(asesoriasCollectionNewAsesorias);
                    if (oldProyectosIdOfAsesoriasCollectionNewAsesorias != null && !oldProyectosIdOfAsesoriasCollectionNewAsesorias.equals(proyectos)) {
                        oldProyectosIdOfAsesoriasCollectionNewAsesorias.getAsesoriasCollection().remove(asesoriasCollectionNewAsesorias);
                        oldProyectosIdOfAsesoriasCollectionNewAsesorias = em.merge(oldProyectosIdOfAsesoriasCollectionNewAsesorias);
                    }
                }
            }
            for (Entregas entregasCollectionNewEntregas : entregasCollectionNew) {
                if (!entregasCollectionOld.contains(entregasCollectionNewEntregas)) {
                    Proyectos oldProyectosIdOfEntregasCollectionNewEntregas = entregasCollectionNewEntregas.getProyectosId();
                    entregasCollectionNewEntregas.setProyectosId(proyectos);
                    entregasCollectionNewEntregas = em.merge(entregasCollectionNewEntregas);
                    if (oldProyectosIdOfEntregasCollectionNewEntregas != null && !oldProyectosIdOfEntregasCollectionNewEntregas.equals(proyectos)) {
                        oldProyectosIdOfEntregasCollectionNewEntregas.getEntregasCollection().remove(entregasCollectionNewEntregas);
                        oldProyectosIdOfEntregasCollectionNewEntregas = em.merge(oldProyectosIdOfEntregasCollectionNewEntregas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = proyectos.getId();
                if (findProyectos(id) == null) {
                    throw new NonexistentEntityException("The proyectos with id " + id + " no longer exists.");
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
            Proyectos proyectos;
            try {
                proyectos = em.getReference(Proyectos.class, id);
                proyectos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proyectos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Asesorias> asesoriasCollectionOrphanCheck = proyectos.getAsesoriasCollection();
            for (Asesorias asesoriasCollectionOrphanCheckAsesorias : asesoriasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proyectos (" + proyectos + ") cannot be destroyed since the Asesorias " + asesoriasCollectionOrphanCheckAsesorias + " in its asesoriasCollection field has a non-nullable proyectosId field.");
            }
            Collection<Entregas> entregasCollectionOrphanCheck = proyectos.getEntregasCollection();
            for (Entregas entregasCollectionOrphanCheckEntregas : entregasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proyectos (" + proyectos + ") cannot be destroyed since the Entregas " + entregasCollectionOrphanCheckEntregas + " in its entregasCollection field has a non-nullable proyectosId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Horarios horariosId = proyectos.getHorariosId();
            if (horariosId != null) {
                horariosId.getProyectosCollection().remove(proyectos);
                horariosId = em.merge(horariosId);
            }
            Estados estadosId = proyectos.getEstadosId();
            if (estadosId != null) {
                estadosId.getProyectosCollection().remove(proyectos);
                estadosId = em.merge(estadosId);
            }
            Empresas empresasId = proyectos.getEmpresasId();
            if (empresasId != null) {
                empresasId.getProyectosCollection().remove(proyectos);
                empresasId = em.merge(empresasId);
            }
            Docentes docentesIdentificacion = proyectos.getDocentesIdentificacion();
            if (docentesIdentificacion != null) {
                docentesIdentificacion.getProyectosCollection().remove(proyectos);
                docentesIdentificacion = em.merge(docentesIdentificacion);
            }
            Anteproyecto anteproyectoId = proyectos.getAnteproyectoId();
            if (anteproyectoId != null) {
                anteproyectoId.getProyectosCollection().remove(proyectos);
                anteproyectoId = em.merge(anteproyectoId);
            }
            em.remove(proyectos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proyectos> findProyectosEntities() {
        return findProyectosEntities(true, -1, -1);
    }

    public List<Proyectos> findProyectosEntities(int maxResults, int firstResult) {
        return findProyectosEntities(false, maxResults, firstResult);
    }

    private List<Proyectos> findProyectosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proyectos.class));
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

    public Proyectos findProyectos(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proyectos.class, id);
        } finally {
            em.close();
        }
    }

    public int getProyectosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proyectos> rt = cq.from(Proyectos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
