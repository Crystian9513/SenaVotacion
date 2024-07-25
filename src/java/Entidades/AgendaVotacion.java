/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Peralta
 */
@Entity
@Table(name = "agenda_votacion")
@NamedQueries({
    @NamedQuery(name = "AgendaVotacion.findAll", query = "SELECT a FROM AgendaVotacion a"),
    @NamedQuery(name = "AgendaVotacion.findByIdAgenda", query = "SELECT a FROM AgendaVotacion a WHERE a.idAgenda = :idAgenda"),
    @NamedQuery(name = "AgendaVotacion.findByNombre", query = "SELECT a FROM AgendaVotacion a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "AgendaVotacion.findByFechaInicio", query = "SELECT a FROM AgendaVotacion a WHERE a.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "AgendaVotacion.findByFechaFin", query = "SELECT a FROM AgendaVotacion a WHERE a.fechaFin = :fechaFin"),
    @NamedQuery(name = "AgendaVotacion.findByHoraInicio", query = "SELECT a FROM AgendaVotacion a WHERE a.horaInicio = :horaInicio"),
    @NamedQuery(name = "AgendaVotacion.findByHoraFin", query = "SELECT a FROM AgendaVotacion a WHERE a.horaFin = :horaFin"),
    @NamedQuery(name = "AgendaVotacion.findByDescrpcion", query = "SELECT a FROM AgendaVotacion a WHERE a.descrpcion = :descrpcion")})
public class AgendaVotacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_AGENDA")
    private Integer idAgenda;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Basic(optional = false)
    @Column(name = "HORA_INICIO")
    @Temporal(TemporalType.TIME)
    private Date horaInicio;
    @Basic(optional = false)
    @Column(name = "HORA_FIN")
    @Temporal(TemporalType.TIME)
    private Date horaFin;
    @Column(name = "DESCRPCION")
    private String descrpcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agendaFk")
    private List<Votante> votanteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agendaFk")
    private List<Candidato> candidatoList;

    public AgendaVotacion() {
    }

    public AgendaVotacion(Integer idAgenda) {
        this.idAgenda = idAgenda;
    }

    public AgendaVotacion(Integer idAgenda, String nombre, Date fechaInicio, Date fechaFin, Date horaInicio, Date horaFin) {
        this.idAgenda = idAgenda;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Integer getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(Integer idAgenda) {
        this.idAgenda = idAgenda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public String getDescrpcion() {
        return descrpcion;
    }

    public void setDescrpcion(String descrpcion) {
        this.descrpcion = descrpcion;
    }

    public List<Votante> getVotanteList() {
        return votanteList;
    }

    public void setVotanteList(List<Votante> votanteList) {
        this.votanteList = votanteList;
    }

    public List<Candidato> getCandidatoList() {
        return candidatoList;
    }

    public void setCandidatoList(List<Candidato> candidatoList) {
        this.candidatoList = candidatoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAgenda != null ? idAgenda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgendaVotacion)) {
            return false;
        }
        AgendaVotacion other = (AgendaVotacion) object;
        if ((this.idAgenda == null && other.idAgenda != null) || (this.idAgenda != null && !this.idAgenda.equals(other.idAgenda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
