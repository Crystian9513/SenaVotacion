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
import Entidades.Votante;
import java.util.ArrayList;
import java.util.List;
import Entidades.Candidato;
import Entidades.Tipodocumento;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Peralta
 */
public class TipodocumentoJpaController implements Serializable {

    public TipodocumentoJpaController( ) {
        this.emf = Persistence.createEntityManagerFactory("SenaVotacion2024PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipodocumento tipodocumento) throws PreexistingEntityException, Exception {
        if (tipodocumento.getVotanteList() == null) {
            tipodocumento.setVotanteList(new ArrayList<Votante>());
        }
        if (tipodocumento.getCandidatoList() == null) {
            tipodocumento.setCandidatoList(new ArrayList<Candidato>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Votante> attachedVotanteList = new ArrayList<Votante>();
            for (Votante votanteListVotanteToAttach : tipodocumento.getVotanteList()) {
                votanteListVotanteToAttach = em.getReference(votanteListVotanteToAttach.getClass(), votanteListVotanteToAttach.getIdVotante());
                attachedVotanteList.add(votanteListVotanteToAttach);
            }
            tipodocumento.setVotanteList(attachedVotanteList);
            List<Candidato> attachedCandidatoList = new ArrayList<Candidato>();
            for (Candidato candidatoListCandidatoToAttach : tipodocumento.getCandidatoList()) {
                candidatoListCandidatoToAttach = em.getReference(candidatoListCandidatoToAttach.getClass(), candidatoListCandidatoToAttach.getIdCandidato());
                attachedCandidatoList.add(candidatoListCandidatoToAttach);
            }
            tipodocumento.setCandidatoList(attachedCandidatoList);
            em.persist(tipodocumento);
            for (Votante votanteListVotante : tipodocumento.getVotanteList()) {
                Tipodocumento oldTipoDocumentoFkOfVotanteListVotante = votanteListVotante.getTipoDocumentoFk();
                votanteListVotante.setTipoDocumentoFk(tipodocumento);
                votanteListVotante = em.merge(votanteListVotante);
                if (oldTipoDocumentoFkOfVotanteListVotante != null) {
                    oldTipoDocumentoFkOfVotanteListVotante.getVotanteList().remove(votanteListVotante);
                    oldTipoDocumentoFkOfVotanteListVotante = em.merge(oldTipoDocumentoFkOfVotanteListVotante);
                }
            }
            for (Candidato candidatoListCandidato : tipodocumento.getCandidatoList()) {
                Tipodocumento oldTipoDocumentoFkOfCandidatoListCandidato = candidatoListCandidato.getTipoDocumentoFk();
                candidatoListCandidato.setTipoDocumentoFk(tipodocumento);
                candidatoListCandidato = em.merge(candidatoListCandidato);
                if (oldTipoDocumentoFkOfCandidatoListCandidato != null) {
                    oldTipoDocumentoFkOfCandidatoListCandidato.getCandidatoList().remove(candidatoListCandidato);
                    oldTipoDocumentoFkOfCandidatoListCandidato = em.merge(oldTipoDocumentoFkOfCandidatoListCandidato);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipodocumento(tipodocumento.getIdTipoDocumento()) != null) {
                throw new PreexistingEntityException("Tipodocumento " + tipodocumento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipodocumento tipodocumento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipodocumento persistentTipodocumento = em.find(Tipodocumento.class, tipodocumento.getIdTipoDocumento());
            List<Votante> votanteListOld = persistentTipodocumento.getVotanteList();
            List<Votante> votanteListNew = tipodocumento.getVotanteList();
            List<Candidato> candidatoListOld = persistentTipodocumento.getCandidatoList();
            List<Candidato> candidatoListNew = tipodocumento.getCandidatoList();
            List<String> illegalOrphanMessages = null;
            for (Votante votanteListOldVotante : votanteListOld) {
                if (!votanteListNew.contains(votanteListOldVotante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Votante " + votanteListOldVotante + " since its tipoDocumentoFk field is not nullable.");
                }
            }
            for (Candidato candidatoListOldCandidato : candidatoListOld) {
                if (!candidatoListNew.contains(candidatoListOldCandidato)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Candidato " + candidatoListOldCandidato + " since its tipoDocumentoFk field is not nullable.");
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
            tipodocumento.setVotanteList(votanteListNew);
            List<Candidato> attachedCandidatoListNew = new ArrayList<Candidato>();
            for (Candidato candidatoListNewCandidatoToAttach : candidatoListNew) {
                candidatoListNewCandidatoToAttach = em.getReference(candidatoListNewCandidatoToAttach.getClass(), candidatoListNewCandidatoToAttach.getIdCandidato());
                attachedCandidatoListNew.add(candidatoListNewCandidatoToAttach);
            }
            candidatoListNew = attachedCandidatoListNew;
            tipodocumento.setCandidatoList(candidatoListNew);
            tipodocumento = em.merge(tipodocumento);
            for (Votante votanteListNewVotante : votanteListNew) {
                if (!votanteListOld.contains(votanteListNewVotante)) {
                    Tipodocumento oldTipoDocumentoFkOfVotanteListNewVotante = votanteListNewVotante.getTipoDocumentoFk();
                    votanteListNewVotante.setTipoDocumentoFk(tipodocumento);
                    votanteListNewVotante = em.merge(votanteListNewVotante);
                    if (oldTipoDocumentoFkOfVotanteListNewVotante != null && !oldTipoDocumentoFkOfVotanteListNewVotante.equals(tipodocumento)) {
                        oldTipoDocumentoFkOfVotanteListNewVotante.getVotanteList().remove(votanteListNewVotante);
                        oldTipoDocumentoFkOfVotanteListNewVotante = em.merge(oldTipoDocumentoFkOfVotanteListNewVotante);
                    }
                }
            }
            for (Candidato candidatoListNewCandidato : candidatoListNew) {
                if (!candidatoListOld.contains(candidatoListNewCandidato)) {
                    Tipodocumento oldTipoDocumentoFkOfCandidatoListNewCandidato = candidatoListNewCandidato.getTipoDocumentoFk();
                    candidatoListNewCandidato.setTipoDocumentoFk(tipodocumento);
                    candidatoListNewCandidato = em.merge(candidatoListNewCandidato);
                    if (oldTipoDocumentoFkOfCandidatoListNewCandidato != null && !oldTipoDocumentoFkOfCandidatoListNewCandidato.equals(tipodocumento)) {
                        oldTipoDocumentoFkOfCandidatoListNewCandidato.getCandidatoList().remove(candidatoListNewCandidato);
                        oldTipoDocumentoFkOfCandidatoListNewCandidato = em.merge(oldTipoDocumentoFkOfCandidatoListNewCandidato);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipodocumento.getIdTipoDocumento();
                if (findTipodocumento(id) == null) {
                    throw new NonexistentEntityException("The tipodocumento with id " + id + " no longer exists.");
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
            Tipodocumento tipodocumento;
            try {
                tipodocumento = em.getReference(Tipodocumento.class, id);
                tipodocumento.getIdTipoDocumento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipodocumento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Votante> votanteListOrphanCheck = tipodocumento.getVotanteList();
            for (Votante votanteListOrphanCheckVotante : votanteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipodocumento (" + tipodocumento + ") cannot be destroyed since the Votante " + votanteListOrphanCheckVotante + " in its votanteList field has a non-nullable tipoDocumentoFk field.");
            }
            List<Candidato> candidatoListOrphanCheck = tipodocumento.getCandidatoList();
            for (Candidato candidatoListOrphanCheckCandidato : candidatoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipodocumento (" + tipodocumento + ") cannot be destroyed since the Candidato " + candidatoListOrphanCheckCandidato + " in its candidatoList field has a non-nullable tipoDocumentoFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipodocumento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipodocumento> findTipodocumentoEntities() {
        return findTipodocumentoEntities(true, -1, -1);
    }

    public List<Tipodocumento> findTipodocumentoEntities(int maxResults, int firstResult) {
        return findTipodocumentoEntities(false, maxResults, firstResult);
    }

    private List<Tipodocumento> findTipodocumentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipodocumento.class));
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

    public Tipodocumento findTipodocumento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipodocumento.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipodocumentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipodocumento> rt = cq.from(Tipodocumento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
