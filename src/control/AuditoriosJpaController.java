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
import modelo.Reservas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Auditorios;

/**
 *
 * @author ChristianFabian
 */
public class AuditoriosJpaController implements Serializable {

    public AuditoriosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Auditorios auditorios) throws PreexistingEntityException, Exception {
        if (auditorios.getReservasCollection() == null) {
            auditorios.setReservasCollection(new ArrayList<Reservas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Reservas> attachedReservasCollection = new ArrayList<Reservas>();
            for (Reservas reservasCollectionReservasToAttach : auditorios.getReservasCollection()) {
                reservasCollectionReservasToAttach = em.getReference(reservasCollectionReservasToAttach.getClass(), reservasCollectionReservasToAttach.getNumeroReserva());
                attachedReservasCollection.add(reservasCollectionReservasToAttach);
            }
            auditorios.setReservasCollection(attachedReservasCollection);
            em.persist(auditorios);
            for (Reservas reservasCollectionReservas : auditorios.getReservasCollection()) {
                Auditorios oldAuditoriosNumeroAuditorioOfReservasCollectionReservas = reservasCollectionReservas.getAuditoriosNumeroAuditorio();
                reservasCollectionReservas.setAuditoriosNumeroAuditorio(auditorios);
                reservasCollectionReservas = em.merge(reservasCollectionReservas);
                if (oldAuditoriosNumeroAuditorioOfReservasCollectionReservas != null) {
                    oldAuditoriosNumeroAuditorioOfReservasCollectionReservas.getReservasCollection().remove(reservasCollectionReservas);
                    oldAuditoriosNumeroAuditorioOfReservasCollectionReservas = em.merge(oldAuditoriosNumeroAuditorioOfReservasCollectionReservas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAuditorios(auditorios.getNumeroAuditorio()) != null) {
                throw new PreexistingEntityException("Auditorios " + auditorios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Auditorios auditorios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Auditorios persistentAuditorios = em.find(Auditorios.class, auditorios.getNumeroAuditorio());
            Collection<Reservas> reservasCollectionOld = persistentAuditorios.getReservasCollection();
            Collection<Reservas> reservasCollectionNew = auditorios.getReservasCollection();
            List<String> illegalOrphanMessages = null;
            for (Reservas reservasCollectionOldReservas : reservasCollectionOld) {
                if (!reservasCollectionNew.contains(reservasCollectionOldReservas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reservas " + reservasCollectionOldReservas + " since its auditoriosNumeroAuditorio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Reservas> attachedReservasCollectionNew = new ArrayList<Reservas>();
            for (Reservas reservasCollectionNewReservasToAttach : reservasCollectionNew) {
                reservasCollectionNewReservasToAttach = em.getReference(reservasCollectionNewReservasToAttach.getClass(), reservasCollectionNewReservasToAttach.getNumeroReserva());
                attachedReservasCollectionNew.add(reservasCollectionNewReservasToAttach);
            }
            reservasCollectionNew = attachedReservasCollectionNew;
            auditorios.setReservasCollection(reservasCollectionNew);
            auditorios = em.merge(auditorios);
            for (Reservas reservasCollectionNewReservas : reservasCollectionNew) {
                if (!reservasCollectionOld.contains(reservasCollectionNewReservas)) {
                    Auditorios oldAuditoriosNumeroAuditorioOfReservasCollectionNewReservas = reservasCollectionNewReservas.getAuditoriosNumeroAuditorio();
                    reservasCollectionNewReservas.setAuditoriosNumeroAuditorio(auditorios);
                    reservasCollectionNewReservas = em.merge(reservasCollectionNewReservas);
                    if (oldAuditoriosNumeroAuditorioOfReservasCollectionNewReservas != null && !oldAuditoriosNumeroAuditorioOfReservasCollectionNewReservas.equals(auditorios)) {
                        oldAuditoriosNumeroAuditorioOfReservasCollectionNewReservas.getReservasCollection().remove(reservasCollectionNewReservas);
                        oldAuditoriosNumeroAuditorioOfReservasCollectionNewReservas = em.merge(oldAuditoriosNumeroAuditorioOfReservasCollectionNewReservas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = auditorios.getNumeroAuditorio();
                if (findAuditorios(id) == null) {
                    throw new NonexistentEntityException("The auditorios with id " + id + " no longer exists.");
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
            Auditorios auditorios;
            try {
                auditorios = em.getReference(Auditorios.class, id);
                auditorios.getNumeroAuditorio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The auditorios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Reservas> reservasCollectionOrphanCheck = auditorios.getReservasCollection();
            for (Reservas reservasCollectionOrphanCheckReservas : reservasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Auditorios (" + auditorios + ") cannot be destroyed since the Reservas " + reservasCollectionOrphanCheckReservas + " in its reservasCollection field has a non-nullable auditoriosNumeroAuditorio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(auditorios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Auditorios> findAuditoriosEntities() {
        return findAuditoriosEntities(true, -1, -1);
    }

    public List<Auditorios> findAuditoriosEntities(int maxResults, int firstResult) {
        return findAuditoriosEntities(false, maxResults, firstResult);
    }

    private List<Auditorios> findAuditoriosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Auditorios.class));
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

    public Auditorios findAuditorios(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Auditorios.class, id);
        } finally {
            em.close();
        }
    }

    public int getAuditoriosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Auditorios> rt = cq.from(Auditorios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
