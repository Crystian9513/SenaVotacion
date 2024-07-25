/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Formacion;
import java.util.ArrayList;
import java.util.List;
import Entidades.Votante;
import Entidades.Candidato;
import Entidades.Sede;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Peralta
 */
public class SedeJpaController implements Serializable {

    public SedeJpaController( ) {
      this.emf = Persistence.createEntityManagerFactory("SenaVotacion2024PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sede sede) throws PreexistingEntityException, Exception {
        if (sede.getFormacionList() == null) {
            sede.setFormacionList(new ArrayList<Formacion>());
        }
        if (sede.getVotanteList() == null) {
            sede.setVotanteList(new ArrayList<Votante>());
        }
        if (sede.getCandidatoList() == null) {
            sede.setCandidatoList(new ArrayList<Candidato>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Formacion> attachedFormacionList = new ArrayList<Formacion>();
            for (Formacion formacionListFormacionToAttach : sede.getFormacionList()) {
                formacionListFormacionToAttach = em.getReference(formacionListFormacionToAttach.getClass(), formacionListFormacionToAttach.getIdFormacion());
                attachedFormacionList.add(formacionListFormacionToAttach);
            }
            sede.setFormacionList(attachedFormacionList);
            List<Votante> attachedVotanteList = new ArrayList<Votante>();
            for (Votante votanteListVotanteToAttach : sede.getVotanteList()) {
                votanteListVotanteToAttach = em.getReference(votanteListVotanteToAttach.getClass(), votanteListVotanteToAttach.getIdVotante());
                attachedVotanteList.add(votanteListVotanteToAttach);
            }
            sede.setVotanteList(attachedVotanteList);
            List<Candidato> attachedCandidatoList = new ArrayList<Candidato>();
            for (Candidato candidatoListCandidatoToAttach : sede.getCandidatoList()) {
                candidatoListCandidatoToAttach = em.getReference(candidatoListCandidatoToAttach.getClass(), candidatoListCandidatoToAttach.getIdCandidato());
                attachedCandidatoList.add(candidatoListCandidatoToAttach);
            }
            sede.setCandidatoList(attachedCandidatoList);
            em.persist(sede);
            for (Formacion formacionListFormacion : sede.getFormacionList()) {
                Sede oldSedeIdOfFormacionListFormacion = formacionListFormacion.getSedeId();
                formacionListFormacion.setSedeId(sede);
                formacionListFormacion = em.merge(formacionListFormacion);
                if (oldSedeIdOfFormacionListFormacion != null) {
                    oldSedeIdOfFormacionListFormacion.getFormacionList().remove(formacionListFormacion);
                    oldSedeIdOfFormacionListFormacion = em.merge(oldSedeIdOfFormacionListFormacion);
                }
            }
            for (Votante votanteListVotante : sede.getVotanteList()) {
                Sede oldSedeFkOfVotanteListVotante = votanteListVotante.getSedeFk();
                votanteListVotante.setSedeFk(sede);
                votanteListVotante = em.merge(votanteListVotante);
                if (oldSedeFkOfVotanteListVotante != null) {
                    oldSedeFkOfVotanteListVotante.getVotanteList().remove(votanteListVotante);
                    oldSedeFkOfVotanteListVotante = em.merge(oldSedeFkOfVotanteListVotante);
                }
            }
            for (Candidato candidatoListCandidato : sede.getCandidatoList()) {
                Sede oldSedeFkOfCandidatoListCandidato = candidatoListCandidato.getSedeFk();
                candidatoListCandidato.setSedeFk(sede);
                candidatoListCandidato = em.merge(candidatoListCandidato);
                if (oldSedeFkOfCandidatoListCandidato != null) {
                    oldSedeFkOfCandidatoListCandidato.getCandidatoList().remove(candidatoListCandidato);
                    oldSedeFkOfCandidatoListCandidato = em.merge(oldSedeFkOfCandidatoListCandidato);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSede(sede.getIdSede()) != null) {
                throw new PreexistingEntityException("Sede " + sede + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sede sede) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sede persistentSede = em.find(Sede.class, sede.getIdSede());
            List<Formacion> formacionListOld = persistentSede.getFormacionList();
            List<Formacion> formacionListNew = sede.getFormacionList();
            List<Votante> votanteListOld = persistentSede.getVotanteList();
            List<Votante> votanteListNew = sede.getVotanteList();
            List<Candidato> candidatoListOld = persistentSede.getCandidatoList();
            List<Candidato> candidatoListNew = sede.getCandidatoList();
            List<String> illegalOrphanMessages = null;
            for (Formacion formacionListOldFormacion : formacionListOld) {
                if (!formacionListNew.contains(formacionListOldFormacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Formacion " + formacionListOldFormacion + " since its sedeId field is not nullable.");
                }
            }
            for (Votante votanteListOldVotante : votanteListOld) {
                if (!votanteListNew.contains(votanteListOldVotante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Votante " + votanteListOldVotante + " since its sedeFk field is not nullable.");
                }
            }
            for (Candidato candidatoListOldCandidato : candidatoListOld) {
                if (!candidatoListNew.contains(candidatoListOldCandidato)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Candidato " + candidatoListOldCandidato + " since its sedeFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Formacion> attachedFormacionListNew = new ArrayList<Formacion>();
            for (Formacion formacionListNewFormacionToAttach : formacionListNew) {
                formacionListNewFormacionToAttach = em.getReference(formacionListNewFormacionToAttach.getClass(), formacionListNewFormacionToAttach.getIdFormacion());
                attachedFormacionListNew.add(formacionListNewFormacionToAttach);
            }
            formacionListNew = attachedFormacionListNew;
            sede.setFormacionList(formacionListNew);
            List<Votante> attachedVotanteListNew = new ArrayList<Votante>();
            for (Votante votanteListNewVotanteToAttach : votanteListNew) {
                votanteListNewVotanteToAttach = em.getReference(votanteListNewVotanteToAttach.getClass(), votanteListNewVotanteToAttach.getIdVotante());
                attachedVotanteListNew.add(votanteListNewVotanteToAttach);
            }
            votanteListNew = attachedVotanteListNew;
            sede.setVotanteList(votanteListNew);
            List<Candidato> attachedCandidatoListNew = new ArrayList<Candidato>();
            for (Candidato candidatoListNewCandidatoToAttach : candidatoListNew) {
                candidatoListNewCandidatoToAttach = em.getReference(candidatoListNewCandidatoToAttach.getClass(), candidatoListNewCandidatoToAttach.getIdCandidato());
                attachedCandidatoListNew.add(candidatoListNewCandidatoToAttach);
            }
            candidatoListNew = attachedCandidatoListNew;
            sede.setCandidatoList(candidatoListNew);
            sede = em.merge(sede);
            for (Formacion formacionListNewFormacion : formacionListNew) {
                if (!formacionListOld.contains(formacionListNewFormacion)) {
                    Sede oldSedeIdOfFormacionListNewFormacion = formacionListNewFormacion.getSedeId();
                    formacionListNewFormacion.setSedeId(sede);
                    formacionListNewFormacion = em.merge(formacionListNewFormacion);
                    if (oldSedeIdOfFormacionListNewFormacion != null && !oldSedeIdOfFormacionListNewFormacion.equals(sede)) {
                        oldSedeIdOfFormacionListNewFormacion.getFormacionList().remove(formacionListNewFormacion);
                        oldSedeIdOfFormacionListNewFormacion = em.merge(oldSedeIdOfFormacionListNewFormacion);
                    }
                }
            }
            for (Votante votanteListNewVotante : votanteListNew) {
                if (!votanteListOld.contains(votanteListNewVotante)) {
                    Sede oldSedeFkOfVotanteListNewVotante = votanteListNewVotante.getSedeFk();
                    votanteListNewVotante.setSedeFk(sede);
                    votanteListNewVotante = em.merge(votanteListNewVotante);
                    if (oldSedeFkOfVotanteListNewVotante != null && !oldSedeFkOfVotanteListNewVotante.equals(sede)) {
                        oldSedeFkOfVotanteListNewVotante.getVotanteList().remove(votanteListNewVotante);
                        oldSedeFkOfVotanteListNewVotante = em.merge(oldSedeFkOfVotanteListNewVotante);
                    }
                }
            }
            for (Candidato candidatoListNewCandidato : candidatoListNew) {
                if (!candidatoListOld.contains(candidatoListNewCandidato)) {
                    Sede oldSedeFkOfCandidatoListNewCandidato = candidatoListNewCandidato.getSedeFk();
                    candidatoListNewCandidato.setSedeFk(sede);
                    candidatoListNewCandidato = em.merge(candidatoListNewCandidato);
                    if (oldSedeFkOfCandidatoListNewCandidato != null && !oldSedeFkOfCandidatoListNewCandidato.equals(sede)) {
                        oldSedeFkOfCandidatoListNewCandidato.getCandidatoList().remove(candidatoListNewCandidato);
                        oldSedeFkOfCandidatoListNewCandidato = em.merge(oldSedeFkOfCandidatoListNewCandidato);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sede.getIdSede();
                if (findSede(id) == null) {
                    throw new NonexistentEntityException("The sede with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sede sede;
            try {
                sede = em.getReference(Sede.class, id);
                sede.getIdSede();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sede with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Formacion> formacionListOrphanCheck = sede.getFormacionList();
            for (Formacion formacionListOrphanCheckFormacion : formacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sede (" + sede + ") cannot be destroyed since the Formacion " + formacionListOrphanCheckFormacion + " in its formacionList field has a non-nullable sedeId field.");
            }
            List<Votante> votanteListOrphanCheck = sede.getVotanteList();
            for (Votante votanteListOrphanCheckVotante : votanteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sede (" + sede + ") cannot be destroyed since the Votante " + votanteListOrphanCheckVotante + " in its votanteList field has a non-nullable sedeFk field.");
            }
            List<Candidato> candidatoListOrphanCheck = sede.getCandidatoList();
            for (Candidato candidatoListOrphanCheckCandidato : candidatoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sede (" + sede + ") cannot be destroyed since the Candidato " + candidatoListOrphanCheckCandidato + " in its candidatoList field has a non-nullable sedeFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sede);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sede> findSedeEntities() {
        return findSedeEntities(true, -1, -1);
    }

    public List<Sede> findSedeEntities(int maxResults, int firstResult) {
        return findSedeEntities(false, maxResults, firstResult);
    }

    private List<Sede> findSedeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sede.class));
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

    public Sede findSede(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sede.class, id);
        } finally {
            em.close();
        }
    }

    public int getSedeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sede> rt = cq.from(Sede.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
