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
import modelo.Proyectos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import modelo.Empresas;
import utilerias.TecladoException;

/**
 *
 * @author ChristianFabian
 */
public class EmpresasJpaController implements Serializable {

    public EmpresasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("SwingBDIIPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empresas empresas) throws PreexistingEntityException, Exception {
        if (empresas.getProyectosCollection() == null) {
            empresas.setProyectosCollection(new ArrayList<Proyectos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Proyectos> attachedProyectosCollection = new ArrayList<Proyectos>();
            for (Proyectos proyectosCollectionProyectosToAttach : empresas.getProyectosCollection()) {
                proyectosCollectionProyectosToAttach = em.getReference(proyectosCollectionProyectosToAttach.getClass(), proyectosCollectionProyectosToAttach.getId());
                attachedProyectosCollection.add(proyectosCollectionProyectosToAttach);
            }
            empresas.setProyectosCollection(attachedProyectosCollection);
            em.persist(empresas);
            for (Proyectos proyectosCollectionProyectos : empresas.getProyectosCollection()) {
                Empresas oldEmpresasIdOfProyectosCollectionProyectos = proyectosCollectionProyectos.getEmpresasId();
                proyectosCollectionProyectos.setEmpresasId(empresas);
                proyectosCollectionProyectos = em.merge(proyectosCollectionProyectos);
                if (oldEmpresasIdOfProyectosCollectionProyectos != null) {
                    oldEmpresasIdOfProyectosCollectionProyectos.getProyectosCollection().remove(proyectosCollectionProyectos);
                    oldEmpresasIdOfProyectosCollectionProyectos = em.merge(oldEmpresasIdOfProyectosCollectionProyectos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpresas(empresas.getId()) != null) {
                throw new PreexistingEntityException("Empresas " + empresas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empresas empresas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresas persistentEmpresas = em.find(Empresas.class, empresas.getId());
            Collection<Proyectos> proyectosCollectionOld = persistentEmpresas.getProyectosCollection();
            Collection<Proyectos> proyectosCollectionNew = empresas.getProyectosCollection();
            List<String> illegalOrphanMessages = null;
            for (Proyectos proyectosCollectionOldProyectos : proyectosCollectionOld) {
                if (!proyectosCollectionNew.contains(proyectosCollectionOldProyectos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Proyectos " + proyectosCollectionOldProyectos + " since its empresasId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Proyectos> attachedProyectosCollectionNew = new ArrayList<Proyectos>();
            for (Proyectos proyectosCollectionNewProyectosToAttach : proyectosCollectionNew) {
                proyectosCollectionNewProyectosToAttach = em.getReference(proyectosCollectionNewProyectosToAttach.getClass(), proyectosCollectionNewProyectosToAttach.getId());
                attachedProyectosCollectionNew.add(proyectosCollectionNewProyectosToAttach);
            }
            proyectosCollectionNew = attachedProyectosCollectionNew;
            empresas.setProyectosCollection(proyectosCollectionNew);
            empresas = em.merge(empresas);
            for (Proyectos proyectosCollectionNewProyectos : proyectosCollectionNew) {
                if (!proyectosCollectionOld.contains(proyectosCollectionNewProyectos)) {
                    Empresas oldEmpresasIdOfProyectosCollectionNewProyectos = proyectosCollectionNewProyectos.getEmpresasId();
                    proyectosCollectionNewProyectos.setEmpresasId(empresas);
                    proyectosCollectionNewProyectos = em.merge(proyectosCollectionNewProyectos);
                    if (oldEmpresasIdOfProyectosCollectionNewProyectos != null && !oldEmpresasIdOfProyectosCollectionNewProyectos.equals(empresas)) {
                        oldEmpresasIdOfProyectosCollectionNewProyectos.getProyectosCollection().remove(proyectosCollectionNewProyectos);
                        oldEmpresasIdOfProyectosCollectionNewProyectos = em.merge(oldEmpresasIdOfProyectosCollectionNewProyectos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = empresas.getId();
                if (findEmpresas(id) == null) {
                    throw new NonexistentEntityException("The empresas with id " + id + " no longer exists.");
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
            Empresas empresas;
            try {
                empresas = em.getReference(Empresas.class, id);
                empresas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empresas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Proyectos> proyectosCollectionOrphanCheck = empresas.getProyectosCollection();
            for (Proyectos proyectosCollectionOrphanCheckProyectos : proyectosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresas (" + empresas + ") cannot be destroyed since the Proyectos " + proyectosCollectionOrphanCheckProyectos + " in its proyectosCollection field has a non-nullable empresasId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(empresas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empresas> findEmpresasEntities() {
        return findEmpresasEntities(true, -1, -1);
    }

    public List<Empresas> findEmpresasEntities(int maxResults, int firstResult) {
        return findEmpresasEntities(false, maxResults, firstResult);
    }

    private List<Empresas> findEmpresasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empresas.class));
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

    public Empresas findEmpresas(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empresas.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpresasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empresas> rt = cq.from(Empresas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    /**
     * Este metodo retorna una empresa por buscada por el nombre
     *
     * @param nombre
     * @return
     */
    public Empresas getEmpresasByNombre(String nombre) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Empresas> criteriaQuery = cb.createQuery(Empresas.class);
        Root<Empresas> c = criteriaQuery.from(Empresas.class);
        criteriaQuery.select(c);
        criteriaQuery.where(
                cb.equal(c.get("nombre"), nombre)
        );
        TypedQuery<Empresas> query = em.createQuery(criteriaQuery);
        List<Empresas> lista = query.getResultList();
        if (lista != null) {
            if (lista.size() > 0) {
                return lista.get(0);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Este metodo hace una consulta con un like, se hace un like inteligente
     * porque se recibe un valor de busqueda que puede ser la identificacion o
     * un nombre
     *
     * @param valorDeBusqueda
     * @return
     */
    public List<Empresas> encontrarCoincidenciasPorNombre(String valorDeBusqueda) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Empresas> criteriaQuery = cb.createQuery(Empresas.class);
        Root<Empresas> c = criteriaQuery.from(Empresas.class);
        criteriaQuery.select(c);
        Expression<String> columnaDeBusqueda;

        //Si el valor se pudo parsear la columna de busqueda sera la identificacion
        columnaDeBusqueda = c.get("nombre");

        criteriaQuery.where(
                cb.and(
                        cb.like(columnaDeBusqueda, "%" + valorDeBusqueda + "%")
                )
        );
        TypedQuery<Empresas> query = em.createQuery(criteriaQuery);
        //para esta consulta con like solo entregaremos como maximo 10 resultados que se mostraran en la tabla
        return query.getResultList();
    }

}
