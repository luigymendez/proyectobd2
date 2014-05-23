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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "AVANCES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Avances.findAll", query = "SELECT a FROM Avances a"),
    @NamedQuery(name = "Avances.findById", query = "SELECT a FROM Avances a WHERE a.id = :id"),
    @NamedQuery(name = "Avances.findByDescripcion", query = "SELECT a FROM Avances a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "Avances.findByFechaRecepcion", query = "SELECT a FROM Avances a WHERE a.fechaRecepcion = :fechaRecepcion"),
    @NamedQuery(name = "Avances.findByHoraRecepcion", query = "SELECT a FROM Avances a WHERE a.horaRecepcion = :horaRecepcion")})
public class Avances implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "FECHA_RECEPCION")
    @Temporal(TemporalType.DATE)
    private Date fechaRecepcion;
    @Basic(optional = false)
    @Column(name = "HORA_RECEPCION")
    @Temporal(TemporalType.DATE)
    private Date horaRecepcion;
    @ManyToMany(mappedBy = "avancesCollection")
    private Collection<ArchivosAdjuntos> archivosAdjuntosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "avancesId")
    private Collection<RevisionesAvance> revisionesAvanceCollection;
    @JoinColumn(name = "PROPUESTAS_ID", referencedColumnName = "ID")
    @ManyToOne
    private Propuestas propuestasId;
    @JoinColumn(name = "ANTEPROYECTO_ID", referencedColumnName = "ID")
    @ManyToOne
    private Anteproyecto anteproyectoId;

    public Avances() {
    }

    public Avances(BigDecimal id) {
        this.id = id;
    }

    public Avances(BigDecimal id, String descripcion, Date fechaRecepcion, Date horaRecepcion) {
        this.id = id;
        this.descripcion = descripcion;
        this.fechaRecepcion = fechaRecepcion;
        this.horaRecepcion = horaRecepcion;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
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

    @XmlTransient
    public Collection<RevisionesAvance> getRevisionesAvanceCollection() {
        return revisionesAvanceCollection;
    }

    public void setRevisionesAvanceCollection(Collection<RevisionesAvance> revisionesAvanceCollection) {
        this.revisionesAvanceCollection = revisionesAvanceCollection;
    }

    public Propuestas getPropuestasId() {
        return propuestasId;
    }

    public void setPropuestasId(Propuestas propuestasId) {
        this.propuestasId = propuestasId;
    }

    public Anteproyecto getAnteproyectoId() {
        return anteproyectoId;
    }

    public void setAnteproyectoId(Anteproyecto anteproyectoId) {
        this.anteproyectoId = anteproyectoId;
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
        if (!(object instanceof Avances)) {
            return false;
        }
        Avances other = (Avances) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Avances[ id=" + id + " ]";
    }
    
}
