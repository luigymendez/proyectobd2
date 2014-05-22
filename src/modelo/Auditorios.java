/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ChristianFabian
 */
@Entity
@Table(name = "AUDITORIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Auditorios.findAll", query = "SELECT a FROM Auditorios a"),
    @NamedQuery(name = "Auditorios.findByNumeroAuditorio", query = "SELECT a FROM Auditorios a WHERE a.numeroAuditorio = :numeroAuditorio"),
    @NamedQuery(name = "Auditorios.findByNombre", query = "SELECT a FROM Auditorios a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Auditorios.findByCapacidad", query = "SELECT a FROM Auditorios a WHERE a.capacidad = :capacidad")})
public class Auditorios implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "NUMERO_AUDITORIO")
    private BigDecimal numeroAuditorio;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "CAPACIDAD")
    private BigInteger capacidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auditoriosNumeroAuditorio")
    private Collection<Reservas> reservasCollection;

    public Auditorios() {
    }

    public Auditorios(BigDecimal numeroAuditorio) {
        this.numeroAuditorio = numeroAuditorio;
    }

    public Auditorios(BigDecimal numeroAuditorio, String nombre, BigInteger capacidad) {
        this.numeroAuditorio = numeroAuditorio;
        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    public BigDecimal getNumeroAuditorio() {
        return numeroAuditorio;
    }

    public void setNumeroAuditorio(BigDecimal numeroAuditorio) {
        this.numeroAuditorio = numeroAuditorio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigInteger getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(BigInteger capacidad) {
        this.capacidad = capacidad;
    }

    @XmlTransient
    public Collection<Reservas> getReservasCollection() {
        return reservasCollection;
    }

    public void setReservasCollection(Collection<Reservas> reservasCollection) {
        this.reservasCollection = reservasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroAuditorio != null ? numeroAuditorio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Auditorios)) {
            return false;
        }
        Auditorios other = (Auditorios) object;
        if ((this.numeroAuditorio == null && other.numeroAuditorio != null) || (this.numeroAuditorio != null && !this.numeroAuditorio.equals(other.numeroAuditorio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Auditorios[ numeroAuditorio=" + numeroAuditorio + " ]";
    }
    
}
