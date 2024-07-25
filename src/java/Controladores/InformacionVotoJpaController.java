/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Entidades.InformacionVoto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Votante;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Peralta
 */
public class InformacionVotoJpaController implements Serializable {

    public InformacionVotoJpaController( ) {
         this.emf = Persistence.createEntityManagerFactory("SenaVotacion2024PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InformacionVoto informacionVoto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Votante votante = informacionVoto.getVotante();
            if (votante != null) {
                votante = em.getReference(votante.getClass(), votante.getIdVotante());
                informacionVoto.setVotante(votante);
            }
            em.persist(informacionVoto);
            if (votante != null) {
                votante.getInformacionVotoList().add(informacionVoto);
                votante = em.merge(votante);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InformacionVoto informacionVoto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InformacionVoto persistentInformacionVoto = em.find(InformacionVoto.class, informacionVoto.getPk());
            Votante votanteOld = persistentInformacionVoto.getVotante();
            Votante votanteNew = informacionVoto.getVotante();
            if (votanteNew != null) {
                votanteNew = em.getReference(votanteNew.getClass(), votanteNew.getIdVotante());
                informacionVoto.setVotante(votanteNew);
            }
            informacionVoto = em.merge(informacionVoto);
            if (votanteOld != null && !votanteOld.equals(votanteNew)) {
                votanteOld.getInformacionVotoList().remove(informacionVoto);
                votanteOld = em.merge(votanteOld);
            }
            if (votanteNew != null && !votanteNew.equals(votanteOld)) {
                votanteNew.getInformacionVotoList().add(informacionVoto);
                votanteNew = em.merge(votanteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = informacionVoto.getPk();
                if (findInformacionVoto(id) == null) {
                    throw new NonexistentEntityException("The informacionVoto with id " + id + " no longer exists.");
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
            InformacionVoto informacionVoto;
            try {
                informacionVoto = em.getReference(InformacionVoto.class, id);
                informacionVoto.getPk();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The informacionVoto with id " + id + " no longer exists.", enfe);
            }
            Votante votante = informacionVoto.getVotante();
            if (votante != null) {
                votante.getInformacionVotoList().remove(informacionVoto);
                votante = em.merge(votante);
            }
            em.remove(informacionVoto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InformacionVoto> findInformacionVotoEntities() {
        return findInformacionVotoEntities(true, -1, -1);
    }

    public List<InformacionVoto> findInformacionVotoEntities(int maxResults, int firstResult) {
        return findInformacionVotoEntities(false, maxResults, firstResult);
    }

    private List<InformacionVoto> findInformacionVotoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InformacionVoto.class));
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

    public InformacionVoto findInformacionVoto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InformacionVoto.class, id);
        } finally {
            em.close();
        }
    }
    



    public int getInformacionVotoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InformacionVoto> rt = cq.from(InformacionVoto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
