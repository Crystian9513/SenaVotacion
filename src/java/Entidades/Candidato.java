/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Peralta
 */
@Entity
@Table(name = "candidato")
@NamedQueries({
    @NamedQuery(name = "Candidato.findAll", query = "SELECT c FROM Candidato c"),
    @NamedQuery(name = "Candidato.findByIdCandidato", query = "SELECT c FROM Candidato c WHERE c.idCandidato = :idCandidato"),
    @NamedQuery(name = "Candidato.findByNumeroDocumento", query = "SELECT c FROM Candidato c WHERE c.numeroDocumento = :numeroDocumento"),
    @NamedQuery(name = "Candidato.findByNombres", query = "SELECT c FROM Candidato c WHERE c.nombres = :nombres"),
    @NamedQuery(name = "Candidato.findByApellidos", query = "SELECT c FROM Candidato c WHERE c.apellidos = :apellidos"),
    @NamedQuery(name = "Candidato.findByNumeroVotos", query = "SELECT c FROM Candidato c WHERE c.numeroVotos = :numeroVotos"),
    @NamedQuery(name = "Candidato.findByFotografia", query = "SELECT c FROM Candidato c WHERE c.fotografia = :fotografia")})
public class Candidato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CANDIDATO")
    private Integer idCandidato;
    @Basic(optional = false)
    @Column(name = "NUMERO_DOCUMENTO")
    private int numeroDocumento;
    @Basic(optional = false)
    @Column(name = "NOMBRES")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "NumeroVotos")
    private int numeroVotos;
    @Column(name = "FOTOGRAFIA")
    private String fotografia;
    @Basic(optional = false)
    @Lob
    @Column(name = "PROPUESTA_CAMPANA")
    private String propuestaCampana;
    @JoinColumn(name = "AGENDA_FK", referencedColumnName = "ID_AGENDA")
    @ManyToOne(optional = false)
    private AgendaVotacion agendaFk;
    @JoinColumn(name = "FORMACION_FK", referencedColumnName = "ID_FORMACION")
    @ManyToOne(optional = false)
    private Formacion formacionFk;
    @JoinColumn(name = "SEDE_FK", referencedColumnName = "ID_SEDE")
    @ManyToOne(optional = false)
    private Sede sedeFk;
    @JoinColumn(name = "TIPO_DOCUMENTO_FK", referencedColumnName = "ID_TIPO_DOCUMENTO")
    @ManyToOne(optional = false)
    private Tipodocumento tipoDocumentoFk;

    public Candidato() {
    }

    public Candidato(Integer idCandidato) {
        this.idCandidato = idCandidato;
    }

    public Candidato(Integer idCandidato, int numeroDocumento, String nombres, String apellidos, int numeroVotos, String propuestaCampana) {
        this.idCandidato = idCandidato;
        this.numeroDocumento = numeroDocumento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.numeroVotos = numeroVotos;
        this.propuestaCampana = propuestaCampana;
    }

    public Integer getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(Integer idCandidato) {
        this.idCandidato = idCandidato;
    }

    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getNumeroVotos() {
        return numeroVotos;
    }

    public void setNumeroVotos(int numeroVotos) {
        this.numeroVotos = numeroVotos;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    public String getPropuestaCampana() {
        return propuestaCampana;
    }

    public void setPropuestaCampana(String propuestaCampana) {
        this.propuestaCampana = propuestaCampana;
    }

    public AgendaVotacion getAgendaFk() {
        return agendaFk;
    }

    public void setAgendaFk(AgendaVotacion agendaFk) {
        this.agendaFk = agendaFk;
    }

    public Formacion getFormacionFk() {
        return formacionFk;
    }

    public void setFormacionFk(Formacion formacionFk) {
        this.formacionFk = formacionFk;
    }

    public Sede getSedeFk() {
        return sedeFk;
    }

    public void setSedeFk(Sede sedeFk) {
        this.sedeFk = sedeFk;
    }

    public Tipodocumento getTipoDocumentoFk() {
        return tipoDocumentoFk;
    }

    public void setTipoDocumentoFk(Tipodocumento tipoDocumentoFk) {
        this.tipoDocumentoFk = tipoDocumentoFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCandidato != null ? idCandidato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Candidato)) {
            return false;
        }
        Candidato other = (Candidato) object;
        if ((this.idCandidato == null && other.idCandidato != null) || (this.idCandidato != null && !this.idCandidato.equals(other.idCandidato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombres;
    }
    
    
    
}
