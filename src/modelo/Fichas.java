/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ChristianFabian
 */
@Entity
@Table(name = "FICHAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fichas.findAll", query = "SELECT f FROM Fichas f"),
    @NamedQuery(name = "Fichas.findById", query = "SELECT f FROM Fichas f WHERE f.id = :id"),
    @NamedQuery(name = "Fichas.findByFecha", query = "SELECT f FROM Fichas f WHERE f.fecha = :fecha"),
    @NamedQuery(name = "Fichas.findByHoraRecepcion", query = "SELECT f FROM Fichas f WHERE f.horaRecepcion = :horaRecepcion")})
public class Fichas implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "HORA_RECEPCION")
    @Temporal(TemporalType.DATE)
    private Date horaRecepcion;
    @ManyToMany(mappedBy = "fichasCollection")
    private Collection<ArchivosAdjuntos> archivosAdjuntosCollection;
    @JoinColumn(name = "PROYECTOS_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Proyectos proyectosId;
    @JoinColumn(name = "ESTADOS_ID", referencedColumnName = "ID")
    @ManyToOne
    private Estados estadosId;

    public Fichas() {
    }

    public Fichas(BigDecimal id) {
        this.id = id;
    }

    public Fichas(BigDecimal id, Date horaRecepcion) {
        this.id = id;
        this.horaRecepcion = horaRecepcion;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHoraRecepcion() {
        return horaRecepcion;
    }

    public void setHoraRecepcion(Date horaRecepcion) {
        this.horaRecepcion = horaRecepcion;
    }

    @XmlTransient
    public Collection<ArchivosAdjuntos> getArchivosAdjuntosCollection() {
        return archivosAdjuntosCollection;
    }

    public void setArchivosAdjuntosCollection(Collection<ArchivosAdjuntos> archivosAdjuntosCollection) {
        this.archivosAdjuntosCollection = archivosAdjuntosCollection;
    }

    public Proyectos getProyectosId() {
        return proyectosId;
    }

    public void setProyectosId(Proyectos proyectosId) {
        this.proyectosId = proyectosId;
    }

    public Estados getEstadosId() {
        return estadosId;
    }

    public void setEstadosId(Estados estadosId) {
        this.estadosId = estadosId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fichas)) {
            return false;
        }
        Fichas other = (Fichas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Fichas[ id=" + id + " ]";
    }
    
}
