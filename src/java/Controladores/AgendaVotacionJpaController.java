/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.AgendaVotacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Votante;
import java.util.ArrayList;
import java.util.List;
import Entidades.Candidato;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Peralta
 */
public class AgendaVotacionJpaController implements Serializable {

    public AgendaVotacionJpaController( ) {
       this.emf = Persistence.createEntityManagerFactory("SenaVotacion2024PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AgendaVotacion agendaVotacion) {
        if (agendaVotacion.getVotanteList() == null) {
            agendaVotacion.setVotanteList(new ArrayList<Votante>());
        }
        if (agendaVotacion.getCandidatoList() == null) {
            agendaVotacion.setCandidatoList(new ArrayList<Candidato>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Votante> attachedVotanteList = new ArrayList<Votante>();
            for (Votante votanteListVotanteToAttach : agendaVotacion.getVotanteList()) {
                votanteListVotanteToAttach = em.getReference(votanteListVotanteToAttach.getClass(), votanteListVotanteToAttach.getIdVotante());
                attachedVotanteList.add(votanteListVotanteToAttach);
            }
            agendaVotacion.setVotanteList(attachedVotanteList);
            List<Candidato> attachedCandidatoList = new ArrayList<Candidato>();
            for (Candidato candidatoListCandidatoToAttach : agendaVotacion.getCandidatoList()) {
                candidatoListCandidatoToAttach = em.getReference(candidatoListCandidatoToAttach.getClass(), candidatoListCandidatoToAttach.getIdCandidato());
                attachedCandidatoList.add(candidatoListCandidatoToAttach);
            }
            agendaVotacion.setCandidatoList(attachedCandidatoList);
            em.persist(agendaVotacion);
            for (Votante votanteListVotante : agendaVotacion.getVotanteList()) {
                AgendaVotacion oldAgendaFkOfVotanteListVotante = votanteListVotante.getAgendaFk();
                votanteListVotante.setAgendaFk(agendaVotacion);
                votanteListVotante = em.merge(votanteListVotante);
                if (oldAgendaFkOfVotanteListVotante != null) {
                    oldAgendaFkOfVotanteListVotante.getVotanteList().remove(votanteListVotante);
                    oldAgendaFkOfVotanteListVotante = em.merge(oldAgendaFkOfVotanteListVotante);
                }
            }
            for (Candidato candidatoListCandidato : agendaVotacion.getCandidatoList()) {
                AgendaVotacion oldAgendaFkOfCandidatoListCandidato = candidatoListCandidato.getAgendaFk();
                candidatoListCandidato.setAgendaFk(agendaVotacion);
                candidatoListCandidato = em.merge(candidatoListCandidato);
                if (oldAgendaFkOfCandidatoListCandidato != null) {
                    oldAgendaFkOfCandidatoListCandidato.getCandidatoList().remove(candidatoListCandidato);
                    oldAgendaFkOfCandidatoListCandidato = em.merge(oldAgendaFkOfCandidatoListCandidato);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AgendaVotacion agendaVotacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AgendaVotacion persistentAgendaVotacion = em.find(AgendaVotacion.class, agendaVotacion.getIdAgenda());
            List<Votante> votanteListOld = persistentAgendaVotacion.getVotanteList();
            List<Votante> votanteListNew = agendaVotacion.getVotanteList();
            List<Candidato> candidatoListOld = persistentAgendaVotacion.getCandidatoList();
            List<Candidato> candidatoListNew = agendaVotacion.getCandidatoList();
            List<String> illegalOrphanMessages = null;
            for (Votante votanteListOldVotante : votanteListOld) {
                if (!votanteListNew.contains(votanteListOldVotante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Votante " + votanteListOldVotante + " since its agendaFk field is not nullable.");
                }
            }
            for (Candidato candidatoListOldCandidato : candidatoListOld) {
                if (!candidatoListNew.contains(candidatoListOldCandidato)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Candidato " + candidatoListOldCandidato + " since its agendaFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Votante> attachedVotanteListNew = new ArrayList<Votante>();
            for (Votante votanteListNewVotanteToAttach : votanteListNew) {
                votanteListNewVotanteToAttach = em.getReference(votanteListNewVotanteToAttach.getClass(), votanteListNewVotanteToAttach.getIdVotante());
                attachedVotanteListNew.add(votanteListNewVotanteToAttach);
            }
            votanteListNew = attachedVotanteListNew;
            agendaVotacion.setVotanteList(votanteListNew);
            List<Candidato> attachedCandidatoListNew = new ArrayList<Candidato>();
            for (Candidato candidatoListNewCandidatoToAttach : candidatoListNew) {
                candidatoListNewCandidatoToAttach = em.getReference(candidatoListNewCandidatoToAttach.getClass(), candidatoListNewCandidatoToAttach.getIdCandidato());
                attachedCandidatoListNew.add(candidatoListNewCandidatoToAttach);
            }
            candidatoListNew = attachedCandidatoListNew;
            agendaVotacion.setCandidatoList(candidatoListNew);
            agendaVotacion = em.merge(agendaVotacion);
            for (Votante votanteListNewVotante : votanteListNew) {
                if (!votanteListOld.contains(votanteListNewVotante)) {
                    AgendaVotacion oldAgendaFkOfVotanteListNewVotante = votanteListNewVotante.getAgendaFk();
                    votanteListNewVotante.setAgendaFk(agendaVotacion);
                    votanteListNewVotante = em.merge(votanteListNewVotante);
                    if (oldAgendaFkOfVotanteListNewVotante != null && !oldAgendaFkOfVotanteListNewVotante.equals(agendaVotacion)) {
                        oldAgendaFkOfVotanteListNewVotante.getVotanteList().remove(votanteListNewVotante);
                        oldAgendaFkOfVotanteListNewVotante = em.merge(oldAgendaFkOfVotanteListNewVotante);
                    }
                }
            }
            for (Candidato candidatoListNewCandidato : candidatoListNew) {
                if (!candidatoListOld.contains(candidatoListNewCandidato)) {
                    AgendaVotacion oldAgendaFkOfCandidatoListNewCandidato = candidatoListNewCandidato.getAgendaFk();
                    candidatoListNewCandidato.setAgendaFk(agendaVotacion);
                    candidatoListNewCandidato = em.merge(candidatoListNewCandidato);
                    if (oldAgendaFkOfCandidatoListNewCandidato != null && !oldAgendaFkOfCandidatoListNewCandidato.equals(agendaVotacion)) {
                        oldAgendaFkOfCandidatoListNewCandidato.getCandidatoList().remove(candidatoListNewCandidato);
                        oldAgendaFkOfCandidatoListNewCandidato = em.merge(oldAgendaFkOfCandidatoListNewCandidato);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = agendaVotacion.getIdAgenda();
                if (findAgendaVotacion(id) == null) {
                    throw new NonexistentEntityException("The agendaVotacion with id " + id + " no longer exists.");
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
            AgendaVotacion agendaVotacion;
            try {
                agendaVotacion = em.getReference(AgendaVotacion.class, id);
                agendaVotacion.getIdAgenda();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The agendaVotacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Votante> votanteListOrphanCheck = agendaVotacion.getVotanteList();
            for (Votante votanteListOrphanCheckVotante : votanteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AgendaVotacion (" + agendaVotacion + ") cannot be destroyed since the Votante " + votanteListOrphanCheckVotante + " in its votanteList field has a non-nullable agendaFk field.");
            }
            List<Candidato> candidatoListOrphanCheck = agendaVotacion.getCandidatoList();
            for (Candidato candidatoListOrphanCheckCandidato : candidatoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AgendaVotacion (" + agendaVotacion + ") cannot be destroyed since the Candidato " + candidatoListOrphanCheckCandidato + " in its candidatoList field has a non-nullable agendaFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(agendaVotacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AgendaVotacion> findAgendaVotacionEntities() {
        return findAgendaVotacionEntities(true, -1, -1);
    }

    public List<AgendaVotacion> findAgendaVotacionEntities(int maxResults, int firstResult) {
        return findAgendaVotacionEntities(false, maxResults, firstResult);
    }

    private List<AgendaVotacion> findAgendaVotacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AgendaVotacion.class));
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

    public AgendaVotacion findAgendaVotacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AgendaVotacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getAgendaVotacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AgendaVotacion> rt = cq.from(AgendaVotacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public AgendaVotacion findAgendaVotacionByNombre(String nombre) {
    EntityManager em = getEntityManager();
    try {
        Query query = em.createQuery("SELECT a FROM AgendaVotacion a WHERE a.nombre = :nombre");
        query.setParameter("nombre", nombre);
        List<AgendaVotacion> resultados = query.getResultList();
        // Si hay resultados, devolver el primero; de lo contrario, devolver null
        return resultados.isEmpty() ? null : resultados.get(0);
    } finally {
        em.close();
    }
}

    
}
