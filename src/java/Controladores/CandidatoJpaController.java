/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.AgendaVotacion;
import Entidades.Candidato;
import Entidades.Formacion;
import Entidades.Sede;
import Entidades.Tipodocumento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Peralta
 */
public class CandidatoJpaController implements Serializable {

    public CandidatoJpaController( ) {
        this.emf = Persistence.createEntityManagerFactory("SenaVotacion2024PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Candidato candidato) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AgendaVotacion agendaFk = candidato.getAgendaFk();
            if (agendaFk != null) {
                agendaFk = em.getReference(agendaFk.getClass(), agendaFk.getIdAgenda());
                candidato.setAgendaFk(agendaFk);
            }
            Formacion formacionFk = candidato.getFormacionFk();
            if (formacionFk != null) {
                formacionFk = em.getReference(formacionFk.getClass(), formacionFk.getIdFormacion());
                candidato.setFormacionFk(formacionFk);
            }
            Sede sedeFk = candidato.getSedeFk();
            if (sedeFk != null) {
                sedeFk = em.getReference(sedeFk.getClass(), sedeFk.getIdSede());
                candidato.setSedeFk(sedeFk);
            }
            Tipodocumento tipoDocumentoFk = candidato.getTipoDocumentoFk();
            if (tipoDocumentoFk != null) {
                tipoDocumentoFk = em.getReference(tipoDocumentoFk.getClass(), tipoDocumentoFk.getIdTipoDocumento());
                candidato.setTipoDocumentoFk(tipoDocumentoFk);
            }
            em.persist(candidato);
            if (agendaFk != null) {
                agendaFk.getCandidatoList().add(candidato);
                agendaFk = em.merge(agendaFk);
            }
            if (formacionFk != null) {
                formacionFk.getCandidatoList().add(candidato);
                formacionFk = em.merge(formacionFk);
            }
            if (sedeFk != null) {
                sedeFk.getCandidatoList().add(candidato);
                sedeFk = em.merge(sedeFk);
            }
            if (tipoDocumentoFk != null) {
                tipoDocumentoFk.getCandidatoList().add(candidato);
                tipoDocumentoFk = em.merge(tipoDocumentoFk);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCandidato(candidato.getIdCandidato()) != null) {
                throw new PreexistingEntityException("Candidato " + candidato + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Candidato candidato) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Candidato persistentCandidato = em.find(Candidato.class, candidato.getIdCandidato());
            AgendaVotacion agendaFkOld = persistentCandidato.getAgendaFk();
            AgendaVotacion agendaFkNew = candidato.getAgendaFk();
            Formacion formacionFkOld = persistentCandidato.getFormacionFk();
            Formacion formacionFkNew = candidato.getFormacionFk();
            Sede sedeFkOld = persistentCandidato.getSedeFk();
            Sede sedeFkNew = candidato.getSedeFk();
            Tipodocumento tipoDocumentoFkOld = persistentCandidato.getTipoDocumentoFk();
            Tipodocumento tipoDocumentoFkNew = candidato.getTipoDocumentoFk();
            if (agendaFkNew != null) {
                agendaFkNew = em.getReference(agendaFkNew.getClass(), agendaFkNew.getIdAgenda());
                candidato.setAgendaFk(agendaFkNew);
            }
            if (formacionFkNew != null) {
                formacionFkNew = em.getReference(formacionFkNew.getClass(), formacionFkNew.getIdFormacion());
                candidato.setFormacionFk(formacionFkNew);
            }
            if (sedeFkNew != null) {
                sedeFkNew = em.getReference(sedeFkNew.getClass(), sedeFkNew.getIdSede());
                candidato.setSedeFk(sedeFkNew);
            }
            if (tipoDocumentoFkNew != null) {
                tipoDocumentoFkNew = em.getReference(tipoDocumentoFkNew.getClass(), tipoDocumentoFkNew.getIdTipoDocumento());
                candidato.setTipoDocumentoFk(tipoDocumentoFkNew);
            }
            candidato = em.merge(candidato);
            if (agendaFkOld != null && !agendaFkOld.equals(agendaFkNew)) {
                agendaFkOld.getCandidatoList().remove(candidato);
                agendaFkOld = em.merge(agendaFkOld);
            }
            if (agendaFkNew != null && !agendaFkNew.equals(agendaFkOld)) {
                agendaFkNew.getCandidatoList().add(candidato);
                agendaFkNew = em.merge(agendaFkNew);
            }
            if (formacionFkOld != null && !formacionFkOld.equals(formacionFkNew)) {
                formacionFkOld.getCandidatoList().remove(candidato);
                formacionFkOld = em.merge(formacionFkOld);
            }
            if (formacionFkNew != null && !formacionFkNew.equals(formacionFkOld)) {
                formacionFkNew.getCandidatoList().add(candidato);
                formacionFkNew = em.merge(formacionFkNew);
            }
            if (sedeFkOld != null && !sedeFkOld.equals(sedeFkNew)) {
                sedeFkOld.getCandidatoList().remove(candidato);
                sedeFkOld = em.merge(sedeFkOld);
            }
            if (sedeFkNew != null && !sedeFkNew.equals(sedeFkOld)) {
                sedeFkNew.getCandidatoList().add(candidato);
                sedeFkNew = em.merge(sedeFkNew);
            }
            if (tipoDocumentoFkOld != null && !tipoDocumentoFkOld.equals(tipoDocumentoFkNew)) {
                tipoDocumentoFkOld.getCandidatoList().remove(candidato);
                tipoDocumentoFkOld = em.merge(tipoDocumentoFkOld);
            }
            if (tipoDocumentoFkNew != null && !tipoDocumentoFkNew.equals(tipoDocumentoFkOld)) {
                tipoDocumentoFkNew.getCandidatoList().add(candidato);
                tipoDocumentoFkNew = em.merge(tipoDocumentoFkNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = candidato.getIdCandidato();
                if (findCandidato(id) == null) {
                    throw new NonexistentEntityException("The candidato with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Candidato candidato;
            try {
                candidato = em.getReference(Candidato.class, id);
                candidato.getIdCandidato();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The candidato with id " + id + " no longer exists.", enfe);
            }
            AgendaVotacion agendaFk = candidato.getAgendaFk();
            if (agendaFk != null) {
                agendaFk.getCandidatoList().remove(candidato);
                agendaFk = em.merge(agendaFk);
            }
            Formacion formacionFk = candidato.getFormacionFk();
            if (formacionFk != null) {
                formacionFk.getCandidatoList().remove(candidato);
                formacionFk = em.merge(formacionFk);
            }
            Sede sedeFk = candidato.getSedeFk();
            if (sedeFk != null) {
                sedeFk.getCandidatoList().remove(candidato);
                sedeFk = em.merge(sedeFk);
            }
            Tipodocumento tipoDocumentoFk = candidato.getTipoDocumentoFk();
            if (tipoDocumentoFk != null) {
                tipoDocumentoFk.getCandidatoList().remove(candidato);
                tipoDocumentoFk = em.merge(tipoDocumentoFk);
            }
            em.remove(candidato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Candidato> findCandidatoEntities() {
        return findCandidatoEntities(true, -1, -1);
    }

    public List<Candidato> findCandidatoEntities(int maxResults, int firstResult) {
        return findCandidatoEntities(false, maxResults, firstResult);
    }

    private List<Candidato> findCandidatoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Candidato.class));
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

    public Candidato findCandidato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Candidato.class, id);
        } finally {
            em.close();
        }
    }

    public int getCandidatoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Candidato> rt = cq.from(Candidato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Candidato findCandidatoByDocumento(int numeroDocumento) {
    EntityManager em = getEntityManager();
    try {
        Query query = em.createQuery("SELECT c FROM Candidato c WHERE c.numeroDocumento = :numeroDocumento");
        query.setParameter("numeroDocumento", numeroDocumento);
        List<Candidato> candidatos = query.getResultList();
        if (!candidatos.isEmpty()) {
            return candidatos.get(0); // Devuelve el primer candidato encontrado
        } else {
            return null; // Devuelve null si no se encuentra ningún candidato con el número de documento dado
        }
    } finally {
        em.close();
    }
}
    public List<Candidato> obtenerCandidatosPorIdAgenda(int idAgenda) {
    EntityManager em = getEntityManager();
    try {
        Query query = em.createQuery("SELECT c FROM Candidato c WHERE c.agendaFk.idAgenda = :idAgenda");
        query.setParameter("idAgenda", idAgenda);
        return query.getResultList();
    } finally {
        em.close();
    }
}

    

    
}
