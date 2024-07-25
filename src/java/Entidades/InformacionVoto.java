/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Peralta
 */
@Entity
@Table(name = "informacion_voto")
@NamedQueries({
    @NamedQuery(name = "InformacionVoto.findAll", query = "SELECT i FROM InformacionVoto i"),
    @NamedQuery(name = "InformacionVoto.findByHoraVoto", query = "SELECT i FROM InformacionVoto i WHERE i.horaVoto = :horaVoto"),
    @NamedQuery(name = "InformacionVoto.findByFechaVoto", query = "SELECT i FROM InformacionVoto i WHERE i.fechaVoto = :fechaVoto"),
    @NamedQuery(name = "InformacionVoto.findByPk", query = "SELECT i FROM InformacionVoto i WHERE i.pk = :pk")})
public class InformacionVoto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "HoraVoto")
    @Temporal(TemporalType.TIME)
    private Date horaVoto;
    @Column(name = "FechaVoto")
    @Temporal(TemporalType.DATE)
    private Date fechaVoto;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PK")
    private Integer pk;
    @JoinColumn(name = "Votante", referencedColumnName = "ID_VOTANTE")
    @ManyToOne(optional = false)
    private Votante votante;

    public InformacionVoto() {
    }

    public InformacionVoto(Integer pk) {
        this.pk = pk;
    }

    public Date getHoraVoto() {
        return horaVoto;
    }

    public void setHoraVoto(Date horaVoto) {
        this.horaVoto = horaVoto;
    }

    public Date getFechaVoto() {
        return fechaVoto;
    }

    public void setFechaVoto(Date fechaVoto) {
        this.fechaVoto = fechaVoto;
    }

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public Votante getVotante() {
        return votante;
    }

    public void setVotante(Votante votante) {
        this.votante = votante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pk != null ? pk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InformacionVoto)) {
            return false;
        }
        InformacionVoto other = (InformacionVoto) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.InformacionVoto[ pk=" + pk + " ]";
    }
    
}
