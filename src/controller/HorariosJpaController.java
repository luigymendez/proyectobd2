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
import modelo.Docentes;
import modelo.Proyectos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import modelo.Horarios;

/**
 *
 * @author ChristianFabian
 */
public class HorariosJpaController implements Serializable {

    public HorariosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("SwingBDIIPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Horarios horarios) throws PreexistingEntityException, Exception {
        if (horarios.getProyectosCollection() == null) {
            horarios.setProyectosCollection(new ArrayList<Proyectos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Docentes docentesIdentificacion = horarios.getDocentesIdentificacion();
            if (docentesIdentificacion != null) {
                docentesIdentificacion = em.getReference(docentesIdentificacion.getClass(), docentesIdentificacion.getIdentificacion());
                horarios.setDocentesIdentificacion(docentesIdentificacion);
            }
            Collection<Proyectos> attachedProyectosCollection = new ArrayList<Proyectos>();
            for (Proyectos proyectosCollectionProyectosToAttach : horarios.getProyectosCollection()) {
                proyectosCollectionProyectosToAttach = em.getReference(proyectosCollectionProyectosToAttach.getClass(), proyectosCollectionProyectosToAttach.getId());
                attachedProyectosCollection.add(proyectosCollectionProyectosToAttach);
            }
            horarios.setProyectosCollection(attachedProyectosCollection);
            em.persist(horarios);
            if (docentesIdentificacion != null) {
                docentesIdentificacion.getHorariosCollection().add(horarios);
                docentesIdentificacion = em.merge(docentesIdentificacion);
            }
            for (Proyectos proyectosCollectionProyectos : horarios.getProyectosCollection()) {
                Horarios oldHorariosIdOfProyectosCollectionProyectos = proyectosCollectionProyectos.getHorariosId();
                proyectosCollectionProyectos.setHorariosId(horarios);
                proyectosCollectionProyectos = em.merge(proyectosCollectionProyectos);
                if (oldHorariosIdOfProyectosCollectionProyectos != null) {
                    oldHorariosIdOfProyectosCollectionProyectos.getProyectosCollection().remove(proyectosCollectionProyectos);
                    oldHorariosIdOfProyectosCollectionProyectos = em.merge(oldHorariosIdOfProyectosCollectionProyectos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHorarios(horarios.getId()) != null) {
                throw new PreexistingEntityException("Horarios " + horarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Horarios horarios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Horarios persistentHorarios = em.find(Horarios.class, horarios.getId());
            Docentes docentesIdentificacionOld = persistentHorarios.getDocentesIdentificacion();
            Docentes docentesIdentificacionNew = horarios.getDocentesIdentificacion();
            Collection<Proyectos> proyectosCollectionOld = persistentHorarios.getProyectosCollection();
            Collection<Proyectos> proyectosCollectionNew = horarios.getProyectosCollection();
            List<String> illegalOrphanMessages = null;
            for (Proyectos proyectosCollectionOldProyectos : proyectosCollectionOld) {
                if (!proyectosCollectionNew.contains(proyectosCollectionOldProyectos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Proyectos " + proyectosCollectionOldProyectos + " since its horariosId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (docentesIdentificacionNew != null) {
                docentesIdentificacionNew = em.getReference(docentesIdentificacionNew.getClass(), docentesIdentificacionNew.getIdentificacion());
                horarios.setDocentesIdentificacion(docentesIdentificacionNew);
            }
            Collection<Proyectos> attachedProyectosCollectionNew = new ArrayList<Proyectos>();
            for (Proyectos proyectosCollectionNewProyectosToAttach : proyectosCollectionNew) {
                proyectosCollectionNewProyectosToAttach = em.getReference(proyectosCollectionNewProyectosToAttach.getClass(), proyectosCollectionNewProyectosToAttach.getId());
                attachedProyectosCollectionNew.add(proyectosCollectionNewProyectosToAttach);
            }
            proyectosCollectionNew = attachedProyectosCollectionNew;
            horarios.setProyectosCollection(proyectosCollectionNew);
            horarios = em.merge(horarios);
            if (docentesIdentificacionOld != null && !docentesIdentificacionOld.equals(docentesIdentificacionNew)) {
                docentesIdentificacionOld.getHorariosCollection().remove(horarios);
                docentesIdentificacionOld = em.merge(docentesIdentificacionOld);
            }
            if (docentesIdentificacionNew != null && !docentesIdentificacionNew.equals(docentesIdentificacionOld)) {
                docentesIdentificacionNew.getHorariosCollection().add(horarios);
                docentesIdentificacionNew = em.merge(docentesIdentificacionNew);
            }
            for (Proyectos proyectosCollectionNewProyectos : proyectosCollectionNew) {
                if (!proyectosCollectionOld.contains(proyectosCollectionNewProyectos)) {
                    Horarios oldHorariosIdOfProyectosCollectionNewProyectos = proyectosCollectionNewProyectos.getHorariosId();
                    proyectosCollectionNewProyectos.setHorariosId(horarios);
                    proyectosCollectionNewProyectos = em.merge(proyectosCollectionNewProyectos);
                    if (oldHorariosIdOfProyectosCollectionNewProyectos != null && !oldHorariosIdOfProyectosCollectionNewProyectos.equals(horarios)) {
                        oldHorariosIdOfProyectosCollectionNewProyectos.getProyectosCollection().remove(proyectosCollectionNewProyectos);
                        oldHorariosIdOfProyectosCollectionNewProyectos = em.merge(oldHorariosIdOfProyectosCollectionNewProyectos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = horarios.getId();
                if (findHorarios(id) == null) {
                    throw new NonexistentEntityException("The horarios with id " + id + " no longer exists.");
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
            Horarios horarios;
            try {
                horarios = em.getReference(Horarios.class, id);
                horarios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The horarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Proyectos> proyectosCollectionOrphanCheck = horarios.getProyectosCollection();
            for (Proyectos proyectosCollectionOrphanCheckProyectos : proyectosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Horarios (" + horarios + ") cannot be destroyed since the Proyectos " + proyectosCollectionOrphanCheckProyectos + " in its proyectosCollection field has a non-nullable horariosId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Docentes docentesIdentificacion = horarios.getDocentesIdentificacion();
            if (docentesIdentificacion != null) {
                docentesIdentificacion.getHorariosCollection().remove(horarios);
                docentesIdentificacion = em.merge(docentesIdentificacion);
            }
            em.remove(horarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Horarios> findHorariosEntities() {
        return findHorariosEntities(true, -1, -1);
    }

    public List<Horarios> findHorariosEntities(int maxResults, int firstResult) {
        return findHorariosEntities(false, maxResults, firstResult);
    }

    private List<Horarios> findHorariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Horarios.class));
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

    public Horarios findHorarios(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Horarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getHorariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Horarios> rt = cq.from(Horarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
        /**
     * Este metodo retorna el siguiente id --> consecutivo
     * @return
     */
    public BigDecimal getNextID() {
        BigDecimal num;
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Horarios> criteriaQuery = cb.createQuery(Horarios.class);
        Root<Horarios> c = criteriaQuery.from(Horarios.class);
        Expression columnConsec = c.get("id");
        criteriaQuery.select(cb.max(columnConsec));
        Query query = em.createQuery(criteriaQuery);
        if(getHorariosCount()>0){
         num = new BigDecimal(((BigDecimal) query.getSingleResult()).intValue() +1);
        
        System.err.println(num);
        }else{
            num = new BigDecimal(1);
        }
        return num;
    }
    
}
