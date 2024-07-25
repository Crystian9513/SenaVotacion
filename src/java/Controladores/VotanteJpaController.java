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
import Entidades.AgendaVotacion;
import Entidades.Formacion;
import Entidades.Sede;
import Entidades.Tipodocumento;
import Entidades.InformacionVoto;
import Entidades.Votante;
import com.password4j.Hash;
import com.password4j.Password;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Peralta
 */
public class VotanteJpaController implements Serializable {

    public VotanteJpaController() {
        this.emf = Persistence.createEntityManagerFactory("SenaVotacion2024PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Votante votante) throws PreexistingEntityException, Exception {
        if (votante.getInformacionVotoList() == null) {
            votante.setInformacionVotoList(new ArrayList<InformacionVoto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AgendaVotacion agendaFk = votante.getAgendaFk();
            if (agendaFk != null) {
                agendaFk = em.getReference(agendaFk.getClass(), agendaFk.getIdAgenda());
                votante.setAgendaFk(agendaFk);
            }
            Formacion formacionFk = votante.getFormacionFk();
            if (formacionFk != null) {
                formacionFk = em.getReference(formacionFk.getClass(), formacionFk.getIdFormacion());
                votante.setFormacionFk(formacionFk);
            }
            Sede sedeFk = votante.getSedeFk();
            if (sedeFk != null) {
                sedeFk = em.getReference(sedeFk.getClass(), sedeFk.getIdSede());
                votante.setSedeFk(sedeFk);
            }
            Tipodocumento tipoDocumentoFk = votante.getTipoDocumentoFk();
            if (tipoDocumentoFk != null) {
                tipoDocumentoFk = em.getReference(tipoDocumentoFk.getClass(), tipoDocumentoFk.getIdTipoDocumento());
                votante.setTipoDocumentoFk(tipoDocumentoFk);
            }
            List<InformacionVoto> attachedInformacionVotoList = new ArrayList<InformacionVoto>();
            for (InformacionVoto informacionVotoListInformacionVotoToAttach : votante.getInformacionVotoList()) {
                informacionVotoListInformacionVotoToAttach = em.getReference(informacionVotoListInformacionVotoToAttach.getClass(), informacionVotoListInformacionVotoToAttach.getPk());
                attachedInformacionVotoList.add(informacionVotoListInformacionVotoToAttach);
            }
            votante.setInformacionVotoList(attachedInformacionVotoList);
            em.persist(votante);
            if (agendaFk != null) {
                agendaFk.getVotanteList().add(votante);
                agendaFk = em.merge(agendaFk);
            }
            if (formacionFk != null) {
                formacionFk.getVotanteList().add(votante);
                formacionFk = em.merge(formacionFk);
            }
            if (sedeFk != null) {
                sedeFk.getVotanteList().add(votante);
                sedeFk = em.merge(sedeFk);
            }
            if (tipoDocumentoFk != null) {
                tipoDocumentoFk.getVotanteList().add(votante);
                tipoDocumentoFk = em.merge(tipoDocumentoFk);
            }
            for (InformacionVoto informacionVotoListInformacionVoto : votante.getInformacionVotoList()) {
                Votante oldVotanteOfInformacionVotoListInformacionVoto = informacionVotoListInformacionVoto.getVotante();
                informacionVotoListInformacionVoto.setVotante(votante);
                informacionVotoListInformacionVoto = em.merge(informacionVotoListInformacionVoto);
                if (oldVotanteOfInformacionVotoListInformacionVoto != null) {
                    oldVotanteOfInformacionVotoListInformacionVoto.getInformacionVotoList().remove(informacionVotoListInformacionVoto);
                    oldVotanteOfInformacionVotoListInformacionVoto = em.merge(oldVotanteOfInformacionVotoListInformacionVoto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVotante(votante.getIdVotante()) != null) {
                throw new PreexistingEntityException("Votante " + votante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Votante votante) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Votante persistentVotante = em.find(Votante.class, votante.getIdVotante());
            AgendaVotacion agendaFkOld = persistentVotante.getAgendaFk();
            AgendaVotacion agendaFkNew = votante.getAgendaFk();
            Formacion formacionFkOld = persistentVotante.getFormacionFk();
            Formacion formacionFkNew = votante.getFormacionFk();
            Sede sedeFkOld = persistentVotante.getSedeFk();
            Sede sedeFkNew = votante.getSedeFk();
            Tipodocumento tipoDocumentoFkOld = persistentVotante.getTipoDocumentoFk();
            Tipodocumento tipoDocumentoFkNew = votante.getTipoDocumentoFk();
            List<InformacionVoto> informacionVotoListOld = persistentVotante.getInformacionVotoList();
            List<InformacionVoto> informacionVotoListNew = votante.getInformacionVotoList();
            List<String> illegalOrphanMessages = null;
            for (InformacionVoto informacionVotoListOldInformacionVoto : informacionVotoListOld) {
                if (!informacionVotoListNew.contains(informacionVotoListOldInformacionVoto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InformacionVoto " + informacionVotoListOldInformacionVoto + " since its votante field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (agendaFkNew != null) {
                agendaFkNew = em.getReference(agendaFkNew.getClass(), agendaFkNew.getIdAgenda());
                votante.setAgendaFk(agendaFkNew);
            }
            if (formacionFkNew != null) {
                formacionFkNew = em.getReference(formacionFkNew.getClass(), formacionFkNew.getIdFormacion());
                votante.setFormacionFk(formacionFkNew);
            }
            if (sedeFkNew != null) {
                sedeFkNew = em.getReference(sedeFkNew.getClass(), sedeFkNew.getIdSede());
                votante.setSedeFk(sedeFkNew);
            }
            if (tipoDocumentoFkNew != null) {
                tipoDocumentoFkNew = em.getReference(tipoDocumentoFkNew.getClass(), tipoDocumentoFkNew.getIdTipoDocumento());
                votante.setTipoDocumentoFk(tipoDocumentoFkNew);
            }
            List<InformacionVoto> attachedInformacionVotoListNew = new ArrayList<InformacionVoto>();
            for (InformacionVoto informacionVotoListNewInformacionVotoToAttach : informacionVotoListNew) {
                informacionVotoListNewInformacionVotoToAttach = em.getReference(informacionVotoListNewInformacionVotoToAttach.getClass(), informacionVotoListNewInformacionVotoToAttach.getPk());
                attachedInformacionVotoListNew.add(informacionVotoListNewInformacionVotoToAttach);
            }
            informacionVotoListNew = attachedInformacionVotoListNew;
            votante.setInformacionVotoList(informacionVotoListNew);
            votante = em.merge(votante);
            if (agendaFkOld != null && !agendaFkOld.equals(agendaFkNew)) {
                agendaFkOld.getVotanteList().remove(votante);
                agendaFkOld = em.merge(agendaFkOld);
            }
            if (agendaFkNew != null && !agendaFkNew.equals(agendaFkOld)) {
                agendaFkNew.getVotanteList().add(votante);
                agendaFkNew = em.merge(agendaFkNew);
            }
            if (formacionFkOld != null && !formacionFkOld.equals(formacionFkNew)) {
                formacionFkOld.getVotanteList().remove(votante);
                formacionFkOld = em.merge(formacionFkOld);
            }
            if (formacionFkNew != null && !formacionFkNew.equals(formacionFkOld)) {
                formacionFkNew.getVotanteList().add(votante);
                formacionFkNew = em.merge(formacionFkNew);
            }
            if (sedeFkOld != null && !sedeFkOld.equals(sedeFkNew)) {
                sedeFkOld.getVotanteList().remove(votante);
                sedeFkOld = em.merge(sedeFkOld);
            }
            if (sedeFkNew != null && !sedeFkNew.equals(sedeFkOld)) {
                sedeFkNew.getVotanteList().add(votante);
                sedeFkNew = em.merge(sedeFkNew);
            }
            if (tipoDocumentoFkOld != null && !tipoDocumentoFkOld.equals(tipoDocumentoFkNew)) {
                tipoDocumentoFkOld.getVotanteList().remove(votante);
                tipoDocumentoFkOld = em.merge(tipoDocumentoFkOld);
            }
            if (tipoDocumentoFkNew != null && !tipoDocumentoFkNew.equals(tipoDocumentoFkOld)) {
                tipoDocumentoFkNew.getVotanteList().add(votante);
                tipoDocumentoFkNew = em.merge(tipoDocumentoFkNew);
            }
            for (InformacionVoto informacionVotoListNewInformacionVoto : informacionVotoListNew) {
                if (!informacionVotoListOld.contains(informacionVotoListNewInformacionVoto)) {
                    Votante oldVotanteOfInformacionVotoListNewInformacionVoto = informacionVotoListNewInformacionVoto.getVotante();
                    informacionVotoListNewInformacionVoto.setVotante(votante);
                    informacionVotoListNewInformacionVoto = em.merge(informacionVotoListNewInformacionVoto);
                    if (oldVotanteOfInformacionVotoListNewInformacionVoto != null && !oldVotanteOfInformacionVotoListNewInformacionVoto.equals(votante)) {
                        oldVotanteOfInformacionVotoListNewInformacionVoto.getInformacionVotoList().remove(informacionVotoListNewInformacionVoto);
                        oldVotanteOfInformacionVotoListNewInformacionVoto = em.merge(oldVotanteOfInformacionVotoListNewInformacionVoto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = votante.getIdVotante();
                if (findVotante(id) == null) {
                    throw new NonexistentEntityException("The votante with id " + id + " no longer exists.");
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
            Votante votante;
            try {
                votante = em.getReference(Votante.class, id);
                votante.getIdVotante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The votante with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<InformacionVoto> informacionVotoListOrphanCheck = votante.getInformacionVotoList();
            for (InformacionVoto informacionVotoListOrphanCheckInformacionVoto : informacionVotoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Votante (" + votante + ") cannot be destroyed since the InformacionVoto " + informacionVotoListOrphanCheckInformacionVoto + " in its informacionVotoList field has a non-nullable votante field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            AgendaVotacion agendaFk = votante.getAgendaFk();
            if (agendaFk != null) {
                agendaFk.getVotanteList().remove(votante);
                agendaFk = em.merge(agendaFk);
            }
            Formacion formacionFk = votante.getFormacionFk();
            if (formacionFk != null) {
                formacionFk.getVotanteList().remove(votante);
                formacionFk = em.merge(formacionFk);
            }
            Sede sedeFk = votante.getSedeFk();
            if (sedeFk != null) {
                sedeFk.getVotanteList().remove(votante);
                sedeFk = em.merge(sedeFk);
            }
            Tipodocumento tipoDocumentoFk = votante.getTipoDocumentoFk();
            if (tipoDocumentoFk != null) {
                tipoDocumentoFk.getVotanteList().remove(votante);
                tipoDocumentoFk = em.merge(tipoDocumentoFk);
            }
            em.remove(votante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Votante> findVotanteEntities() {
        return findVotanteEntities(true, -1, -1);
    }

    public List<Votante> findVotanteEntities(int maxResults, int firstResult) {
        return findVotanteEntities(false, maxResults, firstResult);
    }

    private List<Votante> findVotanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Votante.class));
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

    public Votante findVotante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Votante.class, id);
        } finally {
            em.close();
        }
    }

    public int getVotanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Votante> rt = cq.from(Votante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public String EncryptarClave(String clave) {
        Hash hash = Password.hash(clave).addPepper().withScrypt();
        return hash.getResult();
    }

    public boolean DencryptarClave(String clavehast, String clavelogin) {

        return Password.check(clavelogin, clavehast).addPepper().withScrypt();
    
    }
    
}
