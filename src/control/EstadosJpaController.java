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
import modelo.Ideas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Anteproyecto;
import modelo.Propuestas;
import modelo.Entregables;
import modelo.Estados;
import modelo.Fichas;
import modelo.Proyectos;
import modelo.Sustentaciones;

/**
 *
 * @author ChristianFabian
 */
public class EstadosJpaController implements Serializable {

    public EstadosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estados estados) throws PreexistingEntityException, Exception {
        if (estados.getIdeasCollection() == null) {
            estados.setIdeasCollection(new ArrayList<Ideas>());
        }
        if (estados.getAnteproyectoCollection() == null) {
            estados.setAnteproyectoCollection(new ArrayList<Anteproyecto>());
        }
        if (estados.getPropuestasCollection() == null) {
            estados.setPropuestasCollection(new ArrayList<Propuestas>());
        }
        if (estados.getEntregablesCollection() == null) {
            estados.setEntregablesCollection(new ArrayList<Entregables>());
        }
        if (estados.getFichasCollection() == null) {
            estados.setFichasCollection(new ArrayList<Fichas>());
        }
        if (estados.getProyectosCollection() == null) {
            estados.setProyectosCollection(new ArrayList<Proyectos>());
        }
        if (estados.getSustentacionesCollection() == null) {
            estados.setSustentacionesCollection(new ArrayList<Sustentaciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Ideas> attachedIdeasCollection = new ArrayList<Ideas>();
            for (Ideas ideasCollectionIdeasToAttach : estados.getIdeasCollection()) {
                ideasCollectionIdeasToAttach = em.getReference(ideasCollectionIdeasToAttach.getClass(), ideasCollectionIdeasToAttach.getId());
                attachedIdeasCollection.add(ideasCollectionIdeasToAttach);
            }
            estados.setIdeasCollection(attachedIdeasCollection);
            Collection<Anteproyecto> attachedAnteproyectoCollection = new ArrayList<Anteproyecto>();
            for (Anteproyecto anteproyectoCollectionAnteproyectoToAttach : estados.getAnteproyectoCollection()) {
                anteproyectoCollectionAnteproyectoToAttach = em.getReference(anteproyectoCollectionAnteproyectoToAttach.getClass(), anteproyectoCollectionAnteproyectoToAttach.getId());
                attachedAnteproyectoCollection.add(anteproyectoCollectionAnteproyectoToAttach);
            }
            estados.setAnteproyectoCollection(attachedAnteproyectoCollection);
            Collection<Propuestas> attachedPropuestasCollection = new ArrayList<Propuestas>();
            for (Propuestas propuestasCollectionPropuestasToAttach : estados.getPropuestasCollection()) {
                propuestasCollectionPropuestasToAttach = em.getReference(propuestasCollectionPropuestasToAttach.getClass(), propuestasCollectionPropuestasToAttach.getId());
                attachedPropuestasCollection.add(propuestasCollectionPropuestasToAttach);
            }
            estados.setPropuestasCollection(attachedPropuestasCollection);
            Collection<Entregables> attachedEntregablesCollection = new ArrayList<Entregables>();
            for (Entregables entregablesCollectionEntregablesToAttach : estados.getEntregablesCollection()) {
                entregablesCollectionEntregablesToAttach = em.getReference(entregablesCollectionEntregablesToAttach.getClass(), entregablesCollectionEntregablesToAttach.getId());
                attachedEntregablesCollection.add(entregablesCollectionEntregablesToAttach);
            }
            estados.setEntregablesCollection(attachedEntregablesCollection);
            Collection<Fichas> attachedFichasCollection = new ArrayList<Fichas>();
            for (Fichas fichasCollectionFichasToAttach : estados.getFichasCollection()) {
                fichasCollectionFichasToAttach = em.getReference(fichasCollectionFichasToAttach.getClass(), fichasCollectionFichasToAttach.getId());
                attachedFichasCollection.add(fichasCollectionFichasToAttach);
            }
            estados.setFichasCollection(attachedFichasCollection);
            Collection<Proyectos> attachedProyectosCollection = new ArrayList<Proyectos>();
            for (Proyectos proyectosCollectionProyectosToAttach : estados.getProyectosCollection()) {
                proyectosCollectionProyectosToAttach = em.getReference(proyectosCollectionProyectosToAttach.getClass(), proyectosCollectionProyectosToAttach.getId());
                attachedProyectosCollection.add(proyectosCollectionProyectosToAttach);
            }
            estados.setProyectosCollection(attachedProyectosCollection);
            Collection<Sustentaciones> attachedSustentacionesCollection = new ArrayList<Sustentaciones>();
            for (Sustentaciones sustentacionesCollectionSustentacionesToAttach : estados.getSustentacionesCollection()) {
                sustentacionesCollectionSustentacionesToAttach = em.getReference(sustentacionesCollectionSustentacionesToAttach.getClass(), sustentacionesCollectionSustentacionesToAttach.getId());
                attachedSustentacionesCollection.add(sustentacionesCollectionSustentacionesToAttach);
            }
            estados.setSustentacionesCollection(attachedSustentacionesCollection);
            em.persist(estados);
            for (Ideas ideasCollectionIdeas : estados.getIdeasCollection()) {
                Estados oldEstadosIdOfIdeasCollectionIdeas = ideasCollectionIdeas.getEstadosId();
                ideasCollectionIdeas.setEstadosId(estados);
                ideasCollectionIdeas = em.merge(ideasCollectionIdeas);
                if (oldEstadosIdOfIdeasCollectionIdeas != null) {
                    oldEstadosIdOfIdeasCollectionIdeas.getIdeasCollection().remove(ideasCollectionIdeas);
                    oldEstadosIdOfIdeasCollectionIdeas = em.merge(oldEstadosIdOfIdeasCollectionIdeas);
                }
            }
            for (Anteproyecto anteproyectoCollectionAnteproyecto : estados.getAnteproyectoCollection()) {
                Estados oldEstadosIdOfAnteproyectoCollectionAnteproyecto = anteproyectoCollectionAnteproyecto.getEstadosId();
                anteproyectoCollectionAnteproyecto.setEstadosId(estados);
                anteproyectoCollectionAnteproyecto = em.merge(anteproyectoCollectionAnteproyecto);
                if (oldEstadosIdOfAnteproyectoCollectionAnteproyecto != null) {
                    oldEstadosIdOfAnteproyectoCollectionAnteproyecto.getAnteproyectoCollection().remove(anteproyectoCollectionAnteproyecto);
                    oldEstadosIdOfAnteproyectoCollectionAnteproyecto = em.merge(oldEstadosIdOfAnteproyectoCollectionAnteproyecto);
                }
            }
            for (Propuestas propuestasCollectionPropuestas : estados.getPropuestasCollection()) {
                Estados oldEstadosIdOfPropuestasCollectionPropuestas = propuestasCollectionPropuestas.getEstadosId();
                propuestasCollectionPropuestas.setEstadosId(estados);
                propuestasCollectionPropuestas = em.merge(propuestasCollectionPropuestas);
                if (oldEstadosIdOfPropuestasCollectionPropuestas != null) {
                    oldEstadosIdOfPropuestasCollectionPropuestas.getPropuestasCollection().remove(propuestasCollectionPropuestas);
                    oldEstadosIdOfPropuestasCollectionPropuestas = em.merge(oldEstadosIdOfPropuestasCollectionPropuestas);
                }
            }
            for (Entregables entregablesCollectionEntregables : estados.getEntregablesCollection()) {
                Estados oldEstadosIdOfEntregablesCollectionEntregables = entregablesCollectionEntregables.getEstadosId();
                entregablesCollectionEntregables.setEstadosId(estados);
                entregablesCollectionEntregables = em.merge(entregablesCollectionEntregables);
                if (oldEstadosIdOfEntregablesCollectionEntregables != null) {
                    oldEstadosIdOfEntregablesCollectionEntregables.getEntregablesCollection().remove(entregablesCollectionEntregables);
                    oldEstadosIdOfEntregablesCollectionEntregables = em.merge(oldEstadosIdOfEntregablesCollectionEntregables);
                }
            }
            for (Fichas fichasCollectionFichas : estados.getFichasCollection()) {
                Estados oldEstadosIdOfFichasCollectionFichas = fichasCollectionFichas.getEstadosId();
                fichasCollectionFichas.setEstadosId(estados);
                fichasCollectionFichas = em.merge(fichasCollectionFichas);
                if (oldEstadosIdOfFichasCollectionFichas != null) {
                    oldEstadosIdOfFichasCollectionFichas.getFichasCollection().remove(fichasCollectionFichas);
                    oldEstadosIdOfFichasCollectionFichas = em.merge(oldEstadosIdOfFichasCollectionFichas);
                }
            }
            for (Proyectos proyectosCollectionProyectos : estados.getProyectosCollection()) {
                Estados oldEstadosIdOfProyectosCollectionProyectos = proyectosCollectionProyectos.getEstadosId();
                proyectosCollectionProyectos.setEstadosId(estados);
                proyectosCollectionProyectos = em.merge(proyectosCollectionProyectos);
                if (oldEstadosIdOfProyectosCollectionProyectos != null) {
                    oldEstadosIdOfProyectosCollectionProyectos.getProyectosCollection().remove(proyectosCollectionProyectos);
                    oldEstadosIdOfProyectosCollectionProyectos = em.merge(oldEstadosIdOfProyectosCollectionProyectos);
                }
            }
            for (Sustentaciones sustentacionesCollectionSustentaciones : estados.getSustentacionesCollection()) {
                Estados oldEstadosIdOfSustentacionesCollectionSustentaciones = sustentacionesCollectionSustentaciones.getEstadosId();
                sustentacionesCollectionSustentaciones.setEstadosId(estados);
                sustentacionesCollectionSustentaciones = em.merge(sustentacionesCollectionSustentaciones);
                if (oldEstadosIdOfSustentacionesCollectionSustentaciones != null) {
                    oldEstadosIdOfSustentacionesCollectionSustentaciones.getSustentacionesCollection().remove(sustentacionesCollectionSustentaciones);
                    oldEstadosIdOfSustentacionesCollectionSustentaciones = em.merge(oldEstadosIdOfSustentacionesCollectionSustentaciones);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstados(estados.getId()) != null) {
                throw new PreexistingEntityException("Estados " + estados + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estados estados) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estados persistentEstados = em.find(Estados.class, estados.getId());
            Collection<Ideas> ideasCollectionOld = persistentEstados.getIdeasCollection();
            Collection<Ideas> ideasCollectionNew = estados.getIdeasCollection();
            Collection<Anteproyecto> anteproyectoCollectionOld = persistentEstados.getAnteproyectoCollection();
            Collection<Anteproyecto> anteproyectoCollectionNew = estados.getAnteproyectoCollection();
            Collection<Propuestas> propuestasCollectionOld = persistentEstados.getPropuestasCollection();
            Collection<Propuestas> propuestasCollectionNew = estados.getPropuestasCollection();
            Collection<Entregables> entregablesCollectionOld = persistentEstados.getEntregablesCollection();
            Collection<Entregables> entregablesCollectionNew = estados.getEntregablesCollection();
            Collection<Fichas> fichasCollectionOld = persistentEstados.getFichasCollection();
            Collection<Fichas> fichasCollectionNew = estados.getFichasCollection();
            Collection<Proyectos> proyectosCollectionOld = persistentEstados.getProyectosCollection();
            Collection<Proyectos> proyectosCollectionNew = estados.getProyectosCollection();
            Collection<Sustentaciones> sustentacionesCollectionOld = persistentEstados.getSustentacionesCollection();
            Collection<Sustentaciones> sustentacionesCollectionNew = estados.getSustentacionesCollection();
            List<String> illegalOrphanMessages = null;
            for (Ideas ideasCollectionOldIdeas : ideasCollectionOld) {
                if (!ideasCollectionNew.contains(ideasCollectionOldIdeas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ideas " + ideasCollectionOldIdeas + " since its estadosId field is not nullable.");
                }
            }
            for (Propuestas propuestasCollectionOldPropuestas : propuestasCollectionOld) {
                if (!propuestasCollectionNew.contains(propuestasCollectionOldPropuestas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Propuestas " + propuestasCollectionOldPropuestas + " since its estadosId field is not nullable.");
                }
            }
            for (Entregables entregablesCollectionOldEntregables : entregablesCollectionOld) {
                if (!entregablesCollectionNew.contains(entregablesCollectionOldEntregables)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Entregables " + entregablesCollectionOldEntregables + " since its estadosId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Ideas> attachedIdeasCollectionNew = new ArrayList<Ideas>();
            for (Ideas ideasCollectionNewIdeasToAttach : ideasCollectionNew) {
                ideasCollectionNewIdeasToAttach = em.getReference(ideasCollectionNewIdeasToAttach.getClass(), ideasCollectionNewIdeasToAttach.getId());
                attachedIdeasCollectionNew.add(ideasCollectionNewIdeasToAttach);
            }
            ideasCollectionNew = attachedIdeasCollectionNew;
            estados.setIdeasCollection(ideasCollectionNew);
            Collection<Anteproyecto> attachedAnteproyectoCollectionNew = new ArrayList<Anteproyecto>();
            for (Anteproyecto anteproyectoCollectionNewAnteproyectoToAttach : anteproyectoCollectionNew) {
                anteproyectoCollectionNewAnteproyectoToAttach = em.getReference(anteproyectoCollectionNewAnteproyectoToAttach.getClass(), anteproyectoCollectionNewAnteproyectoToAttach.getId());
                attachedAnteproyectoCollectionNew.add(anteproyectoCollectionNewAnteproyectoToAttach);
            }
            anteproyectoCollectionNew = attachedAnteproyectoCollectionNew;
            estados.setAnteproyectoCollection(anteproyectoCollectionNew);
            Collection<Propuestas> attachedPropuestasCollectionNew = new ArrayList<Propuestas>();
            for (Propuestas propuestasCollectionNewPropuestasToAttach : propuestasCollectionNew) {
                propuestasCollectionNewPropuestasToAttach = em.getReference(propuestasCollectionNewPropuestasToAttach.getClass(), propuestasCollectionNewPropuestasToAttach.getId());
                attachedPropuestasCollectionNew.add(propuestasCollectionNewPropuestasToAttach);
            }
            propuestasCollectionNew = attachedPropuestasCollectionNew;
            estados.setPropuestasCollection(propuestasCollectionNew);
            Collection<Entregables> attachedEntregablesCollectionNew = new ArrayList<Entregables>();
            for (Entregables entregablesCollectionNewEntregablesToAttach : entregablesCollectionNew) {
                entregablesCollectionNewEntregablesToAttach = em.getReference(entregablesCollectionNewEntregablesToAttach.getClass(), entregablesCollectionNewEntregablesToAttach.getId());
                attachedEntregablesCollectionNew.add(entregablesCollectionNewEntregablesToAttach);
            }
            entregablesCollectionNew = attachedEntregablesCollectionNew;
            estados.setEntregablesCollection(entregablesCollectionNew);
            Collection<Fichas> attachedFichasCollectionNew = new ArrayList<Fichas>();
            for (Fichas fichasCollectionNewFichasToAttach : fichasCollectionNew) {
                fichasCollectionNewFichasToAttach = em.getReference(fichasCollectionNewFichasToAttach.getClass(), fichasCollectionNewFichasToAttach.getId());
                attachedFichasCollectionNew.add(fichasCollectionNewFichasToAttach);
            }
            fichasCollectionNew = attachedFichasCollectionNew;
            estados.setFichasCollection(fichasCollectionNew);
            Collection<Proyectos> attachedProyectosCollectionNew = new ArrayList<Proyectos>();
            for (Proyectos proyectosCollectionNewProyectosToAttach : proyectosCollectionNew) {
                proyectosCollectionNewProyectosToAttach = em.getReference(proyectosCollectionNewProyectosToAttach.getClass(), proyectosCollectionNewProyectosToAttach.getId());
                attachedProyectosCollectionNew.add(proyectosCollectionNewProyectosToAttach);
            }
            proyectosCollectionNew = attachedProyectosCollectionNew;
            estados.setProyectosCollection(proyectosCollectionNew);
            Collection<Sustentaciones> attachedSustentacionesCollectionNew = new ArrayList<Sustentaciones>();
            for (Sustentaciones sustentacionesCollectionNewSustentacionesToAttach : sustentacionesCollectionNew) {
                sustentacionesCollectionNewSustentacionesToAttach = em.getReference(sustentacionesCollectionNewSustentacionesToAttach.getClass(), sustentacionesCollectionNewSustentacionesToAttach.getId());
                attachedSustentacionesCollectionNew.add(sustentacionesCollectionNewSustentacionesToAttach);
            }
            sustentacionesCollectionNew = attachedSustentacionesCollectionNew;
            estados.setSustentacionesCollection(sustentacionesCollectionNew);
            estados = em.merge(estados);
            for (Ideas ideasCollectionNewIdeas : ideasCollectionNew) {
                if (!ideasCollectionOld.contains(ideasCollectionNewIdeas)) {
                    Estados oldEstadosIdOfIdeasCollectionNewIdeas = ideasCollectionNewIdeas.getEstadosId();
                    ideasCollectionNewIdeas.setEstadosId(estados);
                    ideasCollectionNewIdeas = em.merge(ideasCollectionNewIdeas);
                    if (oldEstadosIdOfIdeasCollectionNewIdeas != null && !oldEstadosIdOfIdeasCollectionNewIdeas.equals(estados)) {
                        oldEstadosIdOfIdeasCollectionNewIdeas.getIdeasCollection().remove(ideasCollectionNewIdeas);
                        oldEstadosIdOfIdeasCollectionNewIdeas = em.merge(oldEstadosIdOfIdeasCollectionNewIdeas);
                    }
                }
            }
            for (Anteproyecto anteproyectoCollectionOldAnteproyecto : anteproyectoCollectionOld) {
                if (!anteproyectoCollectionNew.contains(anteproyectoCollectionOldAnteproyecto)) {
                    anteproyectoCollectionOldAnteproyecto.setEstadosId(null);
                    anteproyectoCollectionOldAnteproyecto = em.merge(anteproyectoCollectionOldAnteproyecto);
                }
            }
            for (Anteproyecto anteproyectoCollectionNewAnteproyecto : anteproyectoCollectionNew) {
                if (!anteproyectoCollectionOld.contains(anteproyectoCollectionNewAnteproyecto)) {
                    Estados oldEstadosIdOfAnteproyectoCollectionNewAnteproyecto = anteproyectoCollectionNewAnteproyecto.getEstadosId();
                    anteproyectoCollectionNewAnteproyecto.setEstadosId(estados);
                    anteproyectoCollectionNewAnteproyecto = em.merge(anteproyectoCollectionNewAnteproyecto);
                    if (oldEstadosIdOfAnteproyectoCollectionNewAnteproyecto != null && !oldEstadosIdOfAnteproyectoCollectionNewAnteproyecto.equals(estados)) {
                        oldEstadosIdOfAnteproyectoCollectionNewAnteproyecto.getAnteproyectoCollection().remove(anteproyectoCollectionNewAnteproyecto);
                        oldEstadosIdOfAnteproyectoCollectionNewAnteproyecto = em.merge(oldEstadosIdOfAnteproyectoCollectionNewAnteproyecto);
                    }
                }
            }
            for (Propuestas propuestasCollectionNewPropuestas : propuestasCollectionNew) {
                if (!propuestasCollectionOld.contains(propuestasCollectionNewPropuestas)) {
                    Estados oldEstadosIdOfPropuestasCollectionNewPropuestas = propuestasCollectionNewPropuestas.getEstadosId();
                    propuestasCollectionNewPropuestas.setEstadosId(estados);
                    propuestasCollectionNewPropuestas = em.merge(propuestasCollectionNewPropuestas);
                    if (oldEstadosIdOfPropuestasCollectionNewPropuestas != null && !oldEstadosIdOfPropuestasCollectionNewPropuestas.equals(estados)) {
                        oldEstadosIdOfPropuestasCollectionNewPropuestas.getPropuestasCollection().remove(propuestasCollectionNewPropuestas);
                        oldEstadosIdOfPropuestasCollectionNewPropuestas = em.merge(oldEstadosIdOfPropuestasCollectionNewPropuestas);
                    }
                }
            }
            for (Entregables entregablesCollectionNewEntregables : entregablesCollectionNew) {
                if (!entregablesCollectionOld.contains(entregablesCollectionNewEntregables)) {
                    Estados oldEstadosIdOfEntregablesCollectionNewEntregables = entregablesCollectionNewEntregables.getEstadosId();
                    entregablesCollectionNewEntregables.setEstadosId(estados);
                    entregablesCollectionNewEntregables = em.merge(entregablesCollectionNewEntregables);
                    if (oldEstadosIdOfEntregablesCollectionNewEntregables != null && !oldEstadosIdOfEntregablesCollectionNewEntregables.equals(estados)) {
                        oldEstadosIdOfEntregablesCollectionNewEntregables.getEntregablesCollection().remove(entregablesCollectionNewEntregables);
                        oldEstadosIdOfEntregablesCollectionNewEntregables = em.merge(oldEstadosIdOfEntregablesCollectionNewEntregables);
                    }
                }
            }
            for (Fichas fichasCollectionOldFichas : fichasCollectionOld) {
                if (!fichasCollectionNew.contains(fichasCollectionOldFichas)) {
                    fichasCollectionOldFichas.setEstadosId(null);
                    fichasCollectionOldFichas = em.merge(fichasCollectionOldFichas);
                }
            }
            for (Fichas fichasCollectionNewFichas : fichasCollectionNew) {
                if (!fichasCollectionOld.contains(fichasCollectionNewFichas)) {
                    Estados oldEstadosIdOfFichasCollectionNewFichas = fichasCollectionNewFichas.getEstadosId();
                    fichasCollectionNewFichas.setEstadosId(estados);
                    fichasCollectionNewFichas = em.merge(fichasCollectionNewFichas);
                    if (oldEstadosIdOfFichasCollectionNewFichas != null && !oldEstadosIdOfFichasCollectionNewFichas.equals(estados)) {
                        oldEstadosIdOfFichasCollectionNewFichas.getFichasCollection().remove(fichasCollectionNewFichas);
                        oldEstadosIdOfFichasCollectionNewFichas = em.merge(oldEstadosIdOfFichasCollectionNewFichas);
                    }
                }
            }
            for (Proyectos proyectosCollectionOldProyectos : proyectosCollectionOld) {
                if (!proyectosCollectionNew.contains(proyectosCollectionOldProyectos)) {
                    proyectosCollectionOldProyectos.setEstadosId(null);
                    proyectosCollectionOldProyectos = em.merge(proyectosCollectionOldProyectos);
                }
            }
            for (Proyectos proyectosCollectionNewProyectos : proyectosCollectionNew) {
                if (!proyectosCollectionOld.contains(proyectosCollectionNewProyectos)) {
                    Estados oldEstadosIdOfProyectosCollectionNewProyectos = proyectosCollectionNewProyectos.getEstadosId();
                    proyectosCollectionNewProyectos.setEstadosId(estados);
                    proyectosCollectionNewProyectos = em.merge(proyectosCollectionNewProyectos);
                    if (oldEstadosIdOfProyectosCollectionNewProyectos != null && !oldEstadosIdOfProyectosCollectionNewProyectos.equals(estados)) {
                        oldEstadosIdOfProyectosCollectionNewProyectos.getProyectosCollection().remove(proyectosCollectionNewProyectos);
                        oldEstadosIdOfProyectosCollectionNewProyectos = em.merge(oldEstadosIdOfProyectosCollectionNewProyectos);
                    }
                }
            }
            for (Sustentaciones sustentacionesCollectionOldSustentaciones : sustentacionesCollectionOld) {
                if (!sustentacionesCollectionNew.contains(sustentacionesCollectionOldSustentaciones)) {
                    sustentacionesCollectionOldSustentaciones.setEstadosId(null);
                    sustentacionesCollectionOldSustentaciones = em.merge(sustentacionesCollectionOldSustentaciones);
                }
            }
            for (Sustentaciones sustentacionesCollectionNewSustentaciones : sustentacionesCollectionNew) {
                if (!sustentacionesCollectionOld.contains(sustentacionesCollectionNewSustentaciones)) {
                    Estados oldEstadosIdOfSustentacionesCollectionNewSustentaciones = sustentacionesCollectionNewSustentaciones.getEstadosId();
                    sustentacionesCollectionNewSustentaciones.setEstadosId(estados);
                    sustentacionesCollectionNewSustentaciones = em.merge(sustentacionesCollectionNewSustentaciones);
                    if (oldEstadosIdOfSustentacionesCollectionNewSustentaciones != null && !oldEstadosIdOfSustentacionesCollectionNewSustentaciones.equals(estados)) {
                        oldEstadosIdOfSustentacionesCollectionNewSustentaciones.getSustentacionesCollection().remove(sustentacionesCollectionNewSustentaciones);
                        oldEstadosIdOfSustentacionesCollectionNewSustentaciones = em.merge(oldEstadosIdOfSustentacionesCollectionNewSustentaciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = estados.getId();
                if (findEstados(id) == null) {
                    throw new NonexistentEntityException("The estados with id " + id + " no longer exists.");
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
            Estados estados;
            try {
                estados = em.getReference(Estados.class, id);
                estados.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estados with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Ideas> ideasCollectionOrphanCheck = estados.getIdeasCollection();
            for (Ideas ideasCollectionOrphanCheckIdeas : ideasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estados (" + estados + ") cannot be destroyed since the Ideas " + ideasCollectionOrphanCheckIdeas + " in its ideasCollection field has a non-nullable estadosId field.");
            }
            Collection<Propuestas> propuestasCollectionOrphanCheck = estados.getPropuestasCollection();
            for (Propuestas propuestasCollectionOrphanCheckPropuestas : propuestasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estados (" + estados + ") cannot be destroyed since the Propuestas " + propuestasCollectionOrphanCheckPropuestas + " in its propuestasCollection field has a non-nullable estadosId field.");
            }
            Collection<Entregables> entregablesCollectionOrphanCheck = estados.getEntregablesCollection();
            for (Entregables entregablesCollectionOrphanCheckEntregables : entregablesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estados (" + estados + ") cannot be destroyed since the Entregables " + entregablesCollectionOrphanCheckEntregables + " in its entregablesCollection field has a non-nullable estadosId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Anteproyecto> anteproyectoCollection = estados.getAnteproyectoCollection();
            for (Anteproyecto anteproyectoCollectionAnteproyecto : anteproyectoCollection) {
                anteproyectoCollectionAnteproyecto.setEstadosId(null);
                anteproyectoCollectionAnteproyecto = em.merge(anteproyectoCollectionAnteproyecto);
            }
            Collection<Fichas> fichasCollection = estados.getFichasCollection();
            for (Fichas fichasCollectionFichas : fichasCollection) {
                fichasCollectionFichas.setEstadosId(null);
                fichasCollectionFichas = em.merge(fichasCollectionFichas);
            }
            Collection<Proyectos> proyectosCollection = estados.getProyectosCollection();
            for (Proyectos proyectosCollectionProyectos : proyectosCollection) {
                proyectosCollectionProyectos.setEstadosId(null);
                proyectosCollectionProyectos = em.merge(proyectosCollectionProyectos);
            }
            Collection<Sustentaciones> sustentacionesCollection = estados.getSustentacionesCollection();
            for (Sustentaciones sustentacionesCollectionSustentaciones : sustentacionesCollection) {
                sustentacionesCollectionSustentaciones.setEstadosId(null);
                sustentacionesCollectionSustentaciones = em.merge(sustentacionesCollectionSustentaciones);
            }
            em.remove(estados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estados> findEstadosEntities() {
        return findEstadosEntities(true, -1, -1);
    }

    public List<Estados> findEstadosEntities(int maxResults, int firstResult) {
        return findEstadosEntities(false, maxResults, firstResult);
    }

    private List<Estados> findEstadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estados.class));
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

    public Estados findEstados(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estados.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estados> rt = cq.from(Estados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
