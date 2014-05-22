/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.NonexistentEntityException;
import control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Reservas;
import modelo.Estados;
import modelo.ArchivosAdjuntos;
import modelo.Docentes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reservas reservasNumeroReserva = sustentaciones.getReservasNumeroReserva();
            if (reservasNumeroReserva != null) {
                reservasNumeroReserva = em.getReference(reservasNumeroReserva.getClass(), reservasNumeroReserva.getNumeroReserva());
                sustentaciones.setReservasNumeroReserva(reservasNumeroReserva);
            }
            Estados estadosId = sustentaciones.getEstadosId();
            if (estadosId != null) {
                estadosId = em.getReference(estadosId.getClass(), estadosId.getId());
                sustentaciones.setEstadosId(estadosId);
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
            em.persist(sustentaciones);
            if (reservasNumeroReserva != null) {
                reservasNumeroReserva.getSustentacionesCollection().add(sustentaciones);
                reservasNumeroReserva = em.merge(reservasNumeroReserva);
            }
            if (estadosId != null) {
                estadosId.getSustentacionesCollection().add(sustentaciones);
                estadosId = em.merge(estadosId);
            }
            if (archivosAdjuntosId != null) {
                archivosAdjuntosId.getSustentacionesCollection().add(sustentaciones);
                archivosAdjuntosId = em.merge(archivosAdjuntosId);
            }
            for (Docentes docentesCollectionDocentes : sustentaciones.getDocentesCollection()) {
                docentesCollectionDocentes.getSustentacionesCollection().add(sustentaciones);
                docentesCollectionDocentes = em.merge(docentesCollectionDocentes);
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
            Reservas reservasNumeroReservaOld = persistentSustentaciones.getReservasNumeroReserva();
            Reservas reservasNumeroReservaNew = sustentaciones.getReservasNumeroReserva();
            Estados estadosIdOld = persistentSustentaciones.getEstadosId();
            Estados estadosIdNew = sustentaciones.getEstadosId();
            ArchivosAdjuntos archivosAdjuntosIdOld = persistentSustentaciones.getArchivosAdjuntosId();
            ArchivosAdjuntos archivosAdjuntosIdNew = sustentaciones.getArchivosAdjuntosId();
            Collection<Docentes> docentesCollectionOld = persistentSustentaciones.getDocentesCollection();
            Collection<Docentes> docentesCollectionNew = sustentaciones.getDocentesCollection();
            if (reservasNumeroReservaNew != null) {
                reservasNumeroReservaNew = em.getReference(reservasNumeroReservaNew.getClass(), reservasNumeroReservaNew.getNumeroReserva());
                sustentaciones.setReservasNumeroReserva(reservasNumeroReservaNew);
            }
            if (estadosIdNew != null) {
                estadosIdNew = em.getReference(estadosIdNew.getClass(), estadosIdNew.getId());
                sustentaciones.setEstadosId(estadosIdNew);
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
            sustentaciones = em.merge(sustentaciones);
            if (reservasNumeroReservaOld != null && !reservasNumeroReservaOld.equals(reservasNumeroReservaNew)) {
                reservasNumeroReservaOld.getSustentacionesCollection().remove(sustentaciones);
                reservasNumeroReservaOld = em.merge(reservasNumeroReservaOld);
            }
            if (reservasNumeroReservaNew != null && !reservasNumeroReservaNew.equals(reservasNumeroReservaOld)) {
                reservasNumeroReservaNew.getSustentacionesCollection().add(sustentaciones);
                reservasNumeroReservaNew = em.merge(reservasNumeroReservaNew);
            }
            if (estadosIdOld != null && !estadosIdOld.equals(estadosIdNew)) {
                estadosIdOld.getSustentacionesCollection().remove(sustentaciones);
                estadosIdOld = em.merge(estadosIdOld);
            }
            if (estadosIdNew != null && !estadosIdNew.equals(estadosIdOld)) {
                estadosIdNew.getSustentacionesCollection().add(sustentaciones);
                estadosIdNew = em.merge(estadosIdNew);
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
            Reservas reservasNumeroReserva = sustentaciones.getReservasNumeroReserva();
            if (reservasNumeroReserva != null) {
                reservasNumeroReserva.getSustentacionesCollection().remove(sustentaciones);
                reservasNumeroReserva = em.merge(reservasNumeroReserva);
            }
            Estados estadosId = sustentaciones.getEstadosId();
            if (estadosId != null) {
                estadosId.getSustentacionesCollection().remove(sustentaciones);
                estadosId = em.merge(estadosId);
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
