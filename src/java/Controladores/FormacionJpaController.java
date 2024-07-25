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
import Entidades.Sede;
import Entidades.Votante;
import java.util.ArrayList;
import java.util.List;
import Entidades.Candidato;
import Entidades.Formacion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Peralta
 */
public class FormacionJpaController implements Serializable {

    public FormacionJpaController( ) {
       this.emf = Persistence.createEntityManagerFactory("SenaVotacion2024PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Formacion formacion) throws PreexistingEntityException, Exception {
        if (formacion.getVotanteList() == null) {
            formacion.setVotanteList(new ArrayList<Votante>());
        }
        if (formacion.getCandidatoList() == null) {
            formacion.setCandidatoList(new ArrayList<Candidato>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sede sedeId = formacion.getSedeId();
            if (sedeId != null) {
                sedeId = em.getReference(sedeId.getClass(), sedeId.getIdSede());
                formacion.setSedeId(sedeId);
            }
            List<Votante> attachedVotanteList = new ArrayList<Votante>();
            for (Votante votanteListVotanteToAttach : formacion.getVotanteList()) {
                votanteListVotanteToAttach = em.getReference(votanteListVotanteToAttach.getClass(), votanteListVotanteToAttach.getIdVotante());
                attachedVotanteList.add(votanteListVotanteToAttach);
            }
            formacion.setVotanteList(attachedVotanteList);
            List<Candidato> attachedCandidatoList = new ArrayList<Candidato>();
            for (Candidato candidatoListCandidatoToAttach : formacion.getCandidatoList()) {
                candidatoListCandidatoToAttach = em.getReference(candidatoListCandidatoToAttach.getClass(), candidatoListCandidatoToAttach.getIdCandidato());
                attachedCandidatoList.add(candidatoListCandidatoToAttach);
            }
            formacion.setCandidatoList(attachedCandidatoList);
            em.persist(formacion);
            if (sedeId != null) {
                sedeId.getFormacionList().add(formacion);
                sedeId = em.merge(sedeId);
            }
            for (Votante votanteListVotante : formacion.getVotanteList()) {
                Formacion oldFormacionFkOfVotanteListVotante = votanteListVotante.getFormacionFk();
                votanteListVotante.setFormacionFk(formacion);
                votanteListVotante = em.merge(votanteListVotante);
                if (oldFormacionFkOfVotanteListVotante != null) {
                    oldFormacionFkOfVotanteListVotante.getVotanteList().remove(votanteListVotante);
                    oldFormacionFkOfVotanteListVotante = em.merge(oldFormacionFkOfVotanteListVotante);
                }
            }
            for (Candidato candidatoListCandidato : formacion.getCandidatoList()) {
                Formacion oldFormacionFkOfCandidatoListCandidato = candidatoListCandidato.getFormacionFk();
                candidatoListCandidato.setFormacionFk(formacion);
                candidatoListCandidato = em.merge(candidatoListCandidato);
                if (oldFormacionFkOfCandidatoListCandidato != null) {
                    oldFormacionFkOfCandidatoListCandidato.getCandidatoList().remove(candidatoListCandidato);
                    oldFormacionFkOfCandidatoListCandidato = em.merge(oldFormacionFkOfCandidatoListCandidato);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFormacion(formacion.getIdFormacion()) != null) {
                throw new PreexistingEntityException("Formacion " + formacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Formacion formacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Formacion persistentFormacion = em.find(Formacion.class, formacion.getIdFormacion());
            Sede sedeIdOld = persistentFormacion.getSedeId();
            Sede sedeIdNew = formacion.getSedeId();
            List<Votante> votanteListOld = persistentFormacion.getVotanteList();
            List<Votante> votanteListNew = formacion.getVotanteList();
            List<Candidato> candidatoListOld = persistentFormacion.getCandidatoList();
            List<Candidato> candidatoListNew = formacion.getCandidatoList();
            List<String> illegalOrphanMessages = null;
            for (Votante votanteListOldVotante : votanteListOld) {
                if (!votanteListNew.contains(votanteListOldVotante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Votante " + votanteListOldVotante + " since its formacionFk field is not nullable.");
                }
            }
            for (Candidato candidatoListOldCandidato : candidatoListOld) {
                if (!candidatoListNew.contains(candidatoListOldCandidato)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Candidato " + candidatoListOldCandidato + " since its formacionFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sedeIdNew != null) {
                sedeIdNew = em.getReference(sedeIdNew.getClass(), sedeIdNew.getIdSede());
                formacion.setSedeId(sedeIdNew);
            }
            List<Votante> attachedVotanteListNew = new ArrayList<Votante>();
            for (Votante votanteListNewVotanteToAttach : votanteListNew) {
                votanteListNewVotanteToAttach = em.getReference(votanteListNewVotanteToAttach.getClass(), votanteListNewVotanteToAttach.getIdVotante());
                attachedVotanteListNew.add(votanteListNewVotanteToAttach);
            }
            votanteListNew = attachedVotanteListNew;
            formacion.setVotanteList(votanteListNew);
            List<Candidato> attachedCandidatoListNew = new ArrayList<Candidato>();
            for (Candidato candidatoListNewCandidatoToAttach : candidatoListNew) {
                candidatoListNewCandidatoToAttach = em.getReference(candidatoListNewCandidatoToAttach.getClass(), candidatoListNewCandidatoToAttach.getIdCandidato());
                attachedCandidatoListNew.add(candidatoListNewCandidatoToAttach);
            }
            candidatoListNew = attachedCandidatoListNew;
            formacion.setCandidatoList(candidatoListNew);
            formacion = em.merge(formacion);
            if (sedeIdOld != null && !sedeIdOld.equals(sedeIdNew)) {
                sedeIdOld.getFormacionList().remove(formacion);
                sedeIdOld = em.merge(sedeIdOld);
            }
            if (sedeIdNew != null && !sedeIdNew.equals(sedeIdOld)) {
                sedeIdNew.getFormacionList().add(formacion);
                sedeIdNew = em.merge(sedeIdNew);
            }
            for (Votante votanteListNewVotante : votanteListNew) {
                if (!votanteListOld.contains(votanteListNewVotante)) {
                    Formacion oldFormacionFkOfVotanteListNewVotante = votanteListNewVotante.getFormacionFk();
                    votanteListNewVotante.setFormacionFk(formacion);
                    votanteListNewVotante = em.merge(votanteListNewVotante);
                    if (oldFormacionFkOfVotanteListNewVotante != null && !oldFormacionFkOfVotanteListNewVotante.equals(formacion)) {
                        oldFormacionFkOfVotanteListNewVotante.getVotanteList().remove(votanteListNewVotante);
                        oldFormacionFkOfVotanteListNewVotante = em.merge(oldFormacionFkOfVotanteListNewVotante);
                    }
                }
            }
            for (Candidato candidatoListNewCandidato : candidatoListNew) {
                if (!candidatoListOld.contains(candidatoListNewCandidato)) {
                    Formacion oldFormacionFkOfCandidatoListNewCandidato = candidatoListNewCandidato.getFormacionFk();
                    candidatoListNewCandidato.setFormacionFk(formacion);
                    candidatoListNewCandidato = em.merge(candidatoListNewCandidato);
                    if (oldFormacionFkOfCandidatoListNewCandidato != null && !oldFormacionFkOfCandidatoListNewCandidato.equals(formacion)) {
                        oldFormacionFkOfCandidatoListNewCandidato.getCandidatoList().remove(candidatoListNewCandidato);
                        oldFormacionFkOfCandidatoListNewCandidato = em.merge(oldFormacionFkOfCandidatoListNewCandidato);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = formacion.getIdFormacion();
                if (findFormacion(id) == null) {
                    throw new NonexistentEntityException("The formacion with id " + id + " no longer exists.");
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
            Formacion formacion;
            try {
                formacion = em.getReference(Formacion.class, id);
                formacion.getIdFormacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The formacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Votante> votanteListOrphanCheck = formacion.getVotanteList();
            for (Votante votanteListOrphanCheckVotante : votanteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Formacion (" + formacion + ") cannot be destroyed since the Votante " + votanteListOrphanCheckVotante + " in its votanteList field has a non-nullable formacionFk field.");
            }
            List<Candidato> candidatoListOrphanCheck = formacion.getCandidatoList();
            for (Candidato candidatoListOrphanCheckCandidato : candidatoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Formacion (" + formacion + ") cannot be destroyed since the Candidato " + candidatoListOrphanCheckCandidato + " in its candidatoList field has a non-nullable formacionFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sede sedeId = formacion.getSedeId();
            if (sedeId != null) {
                sedeId.getFormacionList().remove(formacion);
                sedeId = em.merge(sedeId);
            }
            em.remove(formacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Formacion> findFormacionEntities() {
        return findFormacionEntities(true, -1, -1);
    }

    public List<Formacion> findFormacionEntities(int maxResults, int firstResult) {
        return findFormacionEntities(false, maxResults, firstResult);
    }

    private List<Formacion> findFormacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Formacion.class));
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

    public Formacion findFormacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Formacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getFormacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Formacion> rt = cq.from(Formacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
