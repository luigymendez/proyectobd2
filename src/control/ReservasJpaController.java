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
import modelo.Auditorios;
import modelo.Sustentaciones;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Reservas;

/**
 *
 * @author ChristianFabian
 */
public class ReservasJpaController implements Serializable {

    public ReservasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reservas reservas) throws PreexistingEntityException, Exception {
        if (reservas.getSustentacionesCollection() == null) {
            reservas.setSustentacionesCollection(new ArrayList<Sustentaciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Auditorios auditoriosNumeroAuditorio = reservas.getAuditoriosNumeroAuditorio();
            if (auditoriosNumeroAuditorio != null) {
                auditoriosNumeroAuditorio = em.getReference(auditoriosNumeroAuditorio.getClass(), auditoriosNumeroAuditorio.getNumeroAuditorio());
                reservas.setAuditoriosNumeroAuditorio(auditoriosNumeroAuditorio);
            }
            Collection<Sustentaciones> attachedSustentacionesCollection = new ArrayList<Sustentaciones>();
            for (Sustentaciones sustentacionesCollectionSustentacionesToAttach : reservas.getSustentacionesCollection()) {
                sustentacionesCollectionSustentacionesToAttach = em.getReference(sustentacionesCollectionSustentacionesToAttach.getClass(), sustentacionesCollectionSustentacionesToAttach.getId());
                attachedSustentacionesCollection.add(sustentacionesCollectionSustentacionesToAttach);
            }
            reservas.setSustentacionesCollection(attachedSustentacionesCollection);
            em.persist(reservas);
            if (auditoriosNumeroAuditorio != null) {
                auditoriosNumeroAuditorio.getReservasCollection().add(reservas);
                auditoriosNumeroAuditorio = em.merge(auditoriosNumeroAuditorio);
            }
            for (Sustentaciones sustentacionesCollectionSustentaciones : reservas.getSustentacionesCollection()) {
                Reservas oldReservasNumeroReservaOfSustentacionesCollectionSustentaciones = sustentacionesCollectionSustentaciones.getReservasNumeroReserva();
                sustentacionesCollectionSustentaciones.setReservasNumeroReserva(reservas);
                sustentacionesCollectionSustentaciones = em.merge(sustentacionesCollectionSustentaciones);
                if (oldReservasNumeroReservaOfSustentacionesCollectionSustentaciones != null) {
                    oldReservasNumeroReservaOfSustentacionesCollectionSustentaciones.getSustentacionesCollection().remove(sustentacionesCollectionSustentaciones);
                    oldReservasNumeroReservaOfSustentacionesCollectionSustentaciones = em.merge(oldReservasNumeroReservaOfSustentacionesCollectionSustentaciones);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findReservas(reservas.getNumeroReserva()) != null) {
                throw new PreexistingEntityException("Reservas " + reservas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reservas reservas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reservas persistentReservas = em.find(Reservas.class, reservas.getNumeroReserva());
            Auditorios auditoriosNumeroAuditorioOld = persistentReservas.getAuditoriosNumeroAuditorio();
            Auditorios auditoriosNumeroAuditorioNew = reservas.getAuditoriosNumeroAuditorio();
            Collection<Sustentaciones> sustentacionesCollectionOld = persistentReservas.getSustentacionesCollection();
            Collection<Sustentaciones> sustentacionesCollectionNew = reservas.getSustentacionesCollection();
            List<String> illegalOrphanMessages = null;
            for (Sustentaciones sustentacionesCollectionOldSustentaciones : sustentacionesCollectionOld) {
                if (!sustentacionesCollectionNew.contains(sustentacionesCollectionOldSustentaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sustentaciones " + sustentacionesCollectionOldSustentaciones + " since its reservasNumeroReserva field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (auditoriosNumeroAuditorioNew != null) {
                auditoriosNumeroAuditorioNew = em.getReference(auditoriosNumeroAuditorioNew.getClass(), auditoriosNumeroAuditorioNew.getNumeroAuditorio());
                reservas.setAuditoriosNumeroAuditorio(auditoriosNumeroAuditorioNew);
            }
            Collection<Sustentaciones> attachedSustentacionesCollectionNew = new ArrayList<Sustentaciones>();
            for (Sustentaciones sustentacionesCollectionNewSustentacionesToAttach : sustentacionesCollectionNew) {
                sustentacionesCollectionNewSustentacionesToAttach = em.getReference(sustentacionesCollectionNewSustentacionesToAttach.getClass(), sustentacionesCollectionNewSustentacionesToAttach.getId());
                attachedSustentacionesCollectionNew.add(sustentacionesCollectionNewSustentacionesToAttach);
            }
            sustentacionesCollectionNew = attachedSustentacionesCollectionNew;
            reservas.setSustentacionesCollection(sustentacionesCollectionNew);
            reservas = em.merge(reservas);
            if (auditoriosNumeroAuditorioOld != null && !auditoriosNumeroAuditorioOld.equals(auditoriosNumeroAuditorioNew)) {
                auditoriosNumeroAuditorioOld.getReservasCollection().remove(reservas);
                auditoriosNumeroAuditorioOld = em.merge(auditoriosNumeroAuditorioOld);
            }
            if (auditoriosNumeroAuditorioNew != null && !auditoriosNumeroAuditorioNew.equals(auditoriosNumeroAuditorioOld)) {
                auditoriosNumeroAuditorioNew.getReservasCollection().add(reservas);
                auditoriosNumeroAuditorioNew = em.merge(auditoriosNumeroAuditorioNew);
            }
            for (Sustentaciones sustentacionesCollectionNewSustentaciones : sustentacionesCollectionNew) {
                if (!sustentacionesCollectionOld.contains(sustentacionesCollectionNewSustentaciones)) {
                    Reservas oldReservasNumeroReservaOfSustentacionesCollectionNewSustentaciones = sustentacionesCollectionNewSustentaciones.getReservasNumeroReserva();
                    sustentacionesCollectionNewSustentaciones.setReservasNumeroReserva(reservas);
                    sustentacionesCollectionNewSustentaciones = em.merge(sustentacionesCollectionNewSustentaciones);
                    if (oldReservasNumeroReservaOfSustentacionesCollectionNewSustentaciones != null && !oldReservasNumeroReservaOfSustentacionesCollectionNewSustentaciones.equals(reservas)) {
                        oldReservasNumeroReservaOfSustentacionesCollectionNewSustentaciones.getSustentacionesCollection().remove(sustentacionesCollectionNewSustentaciones);
                        oldReservasNumeroReservaOfSustentacionesCollectionNewSustentaciones = em.merge(oldReservasNumeroReservaOfSustentacionesCollectionNewSustentaciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = reservas.getNumeroReserva();
                if (findReservas(id) == null) {
                    throw new NonexistentEntityException("The reservas with id " + id + " no longer exists.");
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
            Reservas reservas;
            try {
                reservas = em.getReference(Reservas.class, id);
                reservas.getNumeroReserva();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reservas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Sustentaciones> sustentacionesCollectionOrphanCheck = reservas.getSustentacionesCollection();
            for (Sustentaciones sustentacionesCollectionOrphanCheckSustentaciones : sustentacionesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Reservas (" + reservas + ") cannot be destroyed since the Sustentaciones " + sustentacionesCollectionOrphanCheckSustentaciones + " in its sustentacionesCollection field has a non-nullable reservasNumeroReserva field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Auditorios auditoriosNumeroAuditorio = reservas.getAuditoriosNumeroAuditorio();
            if (auditoriosNumeroAuditorio != null) {
                auditoriosNumeroAuditorio.getReservasCollection().remove(reservas);
                auditoriosNumeroAuditorio = em.merge(auditoriosNumeroAuditorio);
            }
            em.remove(reservas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reservas> findReservasEntities() {
        return findReservasEntities(true, -1, -1);
    }

    public List<Reservas> findReservasEntities(int maxResults, int firstResult) {
        return findReservasEntities(false, maxResults, firstResult);
    }

    private List<Reservas> findReservasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reservas.class));
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

    public Reservas findReservas(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reservas.class, id);
        } finally {
            em.close();
        }
    }

    public int getReservasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reservas> rt = cq.from(Reservas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
