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
import modelo.Proyectos;
import modelo.Estados;
import modelo.ArchivosAdjuntos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Fichas;

/**
 *
 * @author ChristianFabian
 */
public class FichasJpaController implements Serializable {

    public FichasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fichas fichas) throws PreexistingEntityException, Exception {
        if (fichas.getArchivosAdjuntosCollection() == null) {
            fichas.setArchivosAdjuntosCollection(new ArrayList<ArchivosAdjuntos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyectos proyectosId = fichas.getProyectosId();
            if (proyectosId != null) {
                proyectosId = em.getReference(proyectosId.getClass(), proyectosId.getId());
                fichas.setProyectosId(proyectosId);
            }
            Estados estadosId = fichas.getEstadosId();
            if (estadosId != null) {
                estadosId = em.getReference(estadosId.getClass(), estadosId.getId());
                fichas.setEstadosId(estadosId);
            }
            Collection<ArchivosAdjuntos> attachedArchivosAdjuntosCollection = new ArrayList<ArchivosAdjuntos>();
            for (ArchivosAdjuntos archivosAdjuntosCollectionArchivosAdjuntosToAttach : fichas.getArchivosAdjuntosCollection()) {
                archivosAdjuntosCollectionArchivosAdjuntosToAttach = em.getReference(archivosAdjuntosCollectionArchivosAdjuntosToAttach.getClass(), archivosAdjuntosCollectionArchivosAdjuntosToAttach.getId());
                attachedArchivosAdjuntosCollection.add(archivosAdjuntosCollectionArchivosAdjuntosToAttach);
            }
            fichas.setArchivosAdjuntosCollection(attachedArchivosAdjuntosCollection);
            em.persist(fichas);
            if (proyectosId != null) {
                proyectosId.getFichasCollection().add(fichas);
                proyectosId = em.merge(proyectosId);
            }
            if (estadosId != null) {
                estadosId.getFichasCollection().add(fichas);
                estadosId = em.merge(estadosId);
            }
            for (ArchivosAdjuntos archivosAdjuntosCollectionArchivosAdjuntos : fichas.getArchivosAdjuntosCollection()) {
                archivosAdjuntosCollectionArchivosAdjuntos.getFichasCollection().add(fichas);
                archivosAdjuntosCollectionArchivosAdjuntos = em.merge(archivosAdjuntosCollectionArchivosAdjuntos);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFichas(fichas.getId()) != null) {
                throw new PreexistingEntityException("Fichas " + fichas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fichas fichas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fichas persistentFichas = em.find(Fichas.class, fichas.getId());
            Proyectos proyectosIdOld = persistentFichas.getProyectosId();
            Proyectos proyectosIdNew = fichas.getProyectosId();
            Estados estadosIdOld = persistentFichas.getEstadosId();
            Estados estadosIdNew = fichas.getEstadosId();
            Collection<ArchivosAdjuntos> archivosAdjuntosCollectionOld = persistentFichas.getArchivosAdjuntosCollection();
            Collection<ArchivosAdjuntos> archivosAdjuntosCollectionNew = fichas.getArchivosAdjuntosCollection();
            if (proyectosIdNew != null) {
                proyectosIdNew = em.getReference(proyectosIdNew.getClass(), proyectosIdNew.getId());
                fichas.setProyectosId(proyectosIdNew);
            }
            if (estadosIdNew != null) {
                estadosIdNew = em.getReference(estadosIdNew.getClass(), estadosIdNew.getId());
                fichas.setEstadosId(estadosIdNew);
            }
            Collection<ArchivosAdjuntos> attachedArchivosAdjuntosCollectionNew = new ArrayList<ArchivosAdjuntos>();
            for (ArchivosAdjuntos archivosAdjuntosCollectionNewArchivosAdjuntosToAttach : archivosAdjuntosCollectionNew) {
                archivosAdjuntosCollectionNewArchivosAdjuntosToAttach = em.getReference(archivosAdjuntosCollectionNewArchivosAdjuntosToAttach.getClass(), archivosAdjuntosCollectionNewArchivosAdjuntosToAttach.getId());
                attachedArchivosAdjuntosCollectionNew.add(archivosAdjuntosCollectionNewArchivosAdjuntosToAttach);
            }
            archivosAdjuntosCollectionNew = attachedArchivosAdjuntosCollectionNew;
            fichas.setArchivosAdjuntosCollection(archivosAdjuntosCollectionNew);
            fichas = em.merge(fichas);
            if (proyectosIdOld != null && !proyectosIdOld.equals(proyectosIdNew)) {
                proyectosIdOld.getFichasCollection().remove(fichas);
                proyectosIdOld = em.merge(proyectosIdOld);
            }
            if (proyectosIdNew != null && !proyectosIdNew.equals(proyectosIdOld)) {
                proyectosIdNew.getFichasCollection().add(fichas);
                proyectosIdNew = em.merge(proyectosIdNew);
            }
            if (estadosIdOld != null && !estadosIdOld.equals(estadosIdNew)) {
                estadosIdOld.getFichasCollection().remove(fichas);
                estadosIdOld = em.merge(estadosIdOld);
            }
            if (estadosIdNew != null && !estadosIdNew.equals(estadosIdOld)) {
                estadosIdNew.getFichasCollection().add(fichas);
                estadosIdNew = em.merge(estadosIdNew);
            }
            for (ArchivosAdjuntos archivosAdjuntosCollectionOldArchivosAdjuntos : archivosAdjuntosCollectionOld) {
                if (!archivosAdjuntosCollectionNew.contains(archivosAdjuntosCollectionOldArchivosAdjuntos)) {
                    archivosAdjuntosCollectionOldArchivosAdjuntos.getFichasCollection().remove(fichas);
                    archivosAdjuntosCollectionOldArchivosAdjuntos = em.merge(archivosAdjuntosCollectionOldArchivosAdjuntos);
                }
            }
            for (ArchivosAdjuntos archivosAdjuntosCollectionNewArchivosAdjuntos : archivosAdjuntosCollectionNew) {
                if (!archivosAdjuntosCollectionOld.contains(archivosAdjuntosCollectionNewArchivosAdjuntos)) {
                    archivosAdjuntosCollectionNewArchivosAdjuntos.getFichasCollection().add(fichas);
                    archivosAdjuntosCollectionNewArchivosAdjuntos = em.merge(archivosAdjuntosCollectionNewArchivosAdjuntos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = fichas.getId();
                if (findFichas(id) == null) {
                    throw new NonexistentEntityException("The fichas with id " + id + " no longer exists.");
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
            Fichas fichas;
            try {
                fichas = em.getReference(Fichas.class, id);
                fichas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fichas with id " + id + " no longer exists.", enfe);
            }
            Proyectos proyectosId = fichas.getProyectosId();
            if (proyectosId != null) {
                proyectosId.getFichasCollection().remove(fichas);
                proyectosId = em.merge(proyectosId);
            }
            Estados estadosId = fichas.getEstadosId();
            if (estadosId != null) {
                estadosId.getFichasCollection().remove(fichas);
                estadosId = em.merge(estadosId);
            }
            Collection<ArchivosAdjuntos> archivosAdjuntosCollection = fichas.getArchivosAdjuntosCollection();
            for (ArchivosAdjuntos archivosAdjuntosCollectionArchivosAdjuntos : archivosAdjuntosCollection) {
                archivosAdjuntosCollectionArchivosAdjuntos.getFichasCollection().remove(fichas);
                archivosAdjuntosCollectionArchivosAdjuntos = em.merge(archivosAdjuntosCollectionArchivosAdjuntos);
            }
            em.remove(fichas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fichas> findFichasEntities() {
        return findFichasEntities(true, -1, -1);
    }

    public List<Fichas> findFichasEntities(int maxResults, int firstResult) {
        return findFichasEntities(false, maxResults, firstResult);
    }

    private List<Fichas> findFichasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fichas.class));
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

    public Fichas findFichas(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fichas.class, id);
        } finally {
            em.close();
        }
    }

    public int getFichasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fichas> rt = cq.from(Fichas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
