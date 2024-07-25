/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Peralta
 */
@Entity
@Table(name = "votante")
@NamedQueries({
    @NamedQuery(name = "Votante.findAll", query = "SELECT v FROM Votante v"),
    @NamedQuery(name = "Votante.findByIdVotante", query = "SELECT v FROM Votante v WHERE v.idVotante = :idVotante"),
    @NamedQuery(name = "Votante.findByNombresCompleto", query = "SELECT v FROM Votante v WHERE v.nombresCompleto = :nombresCompleto")})
public class Votante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_VOTANTE")
    private Integer idVotante;
    @Basic(optional = false)
    @Column(name = "NOMBRES_COMPLETO")
    private String nombresCompleto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "votante")
    private List<InformacionVoto> informacionVotoList;
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

    public Votante() {
    }

    public Votante(Integer idVotante) {
        this.idVotante = idVotante;
    }

    public Votante(Integer idVotante, String nombresCompleto) {
        this.idVotante = idVotante;
        this.nombresCompleto = nombresCompleto;
    }

    public Integer getIdVotante() {
        return idVotante;
    }

    public void setIdVotante(Integer idVotante) {
        this.idVotante = idVotante;
    }

    public String getNombresCompleto() {
        return nombresCompleto;
    }

    public void setNombresCompleto(String nombresCompleto) {
        this.nombresCompleto = nombresCompleto;
    }

    public List<InformacionVoto> getInformacionVotoList() {
        return informacionVotoList;
    }

    public void setInformacionVotoList(List<InformacionVoto> informacionVotoList) {
        this.informacionVotoList = informacionVotoList;
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
        hash += (idVotante != null ? idVotante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Votante)) {
            return false;
        }
        Votante other = (Votante) object;
        if ((this.idVotante == null && other.idVotante != null) || (this.idVotante != null && !this.idVotante.equals(other.idVotante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombresCompleto;
    }
    
}
