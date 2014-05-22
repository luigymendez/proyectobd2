/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "REVISIONES_AVANCE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RevisionesAvance.findAll", query = "SELECT r FROM RevisionesAvance r"),
    @NamedQuery(name = "RevisionesAvance.findById", query = "SELECT r FROM RevisionesAvance r WHERE r.id = :id"),
    @NamedQuery(name = "RevisionesAvance.findByFechaRevision", query = "SELECT r FROM RevisionesAvance r WHERE r.fechaRevision = :fechaRevision"),
    @NamedQuery(name = "RevisionesAvance.findByHoraRevision", query = "SELECT r FROM RevisionesAvance r WHERE r.horaRevision = :horaRevision"),
    @NamedQuery(name = "RevisionesAvance.findByCalificacionAvance", query = "SELECT r FROM RevisionesAvance r WHERE r.calificacionAvance = :calificacionAvance"),
    @NamedQuery(name = "RevisionesAvance.findByPorcentajeAvance", query = "SELECT r FROM RevisionesAvance r WHERE r.porcentajeAvance = :porcentajeAvance"),
    @NamedQuery(name = "RevisionesAvance.findByResumenObservacion", query = "SELECT r FROM RevisionesAvance r WHERE r.resumenObservacion = :resumenObservacion"),
    @NamedQuery(name = "RevisionesAvance.findByRevisionId", query = "SELECT r FROM RevisionesAvance r WHERE r.revisionId = :revisionId")})
public class RevisionesAvance implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "FECHA_REVISION")
    @Temporal(TemporalType.DATE)
    private Date fechaRevision;
    @Basic(optional = false)
    @Column(name = "HORA_REVISION")
    @Temporal(TemporalType.DATE)
    private Date horaRevision;
    @Column(name = "CALIFICACION_AVANCE")
    private BigDecimal calificacionAvance;
    @Basic(optional = false)
    @Column(name = "PORCENTAJE_AVANCE")
    private BigDecimal porcentajeAvance;
    @Lob
    @Column(name = "ARCHIVO_FORMATO_EVALUACION")
    private Serializable archivoFormatoEvaluacion;
    @Column(name = "RESUMEN_OBSERVACION")
    private String resumenObservacion;
    @Lob
    @Column(name = "ARCHIVO_DETALLE_OBSERVACIONES")
    private Serializable archivoDetalleObservaciones;
    @Basic(optional = false)
    @Column(name = "REVISION_ID")
    private BigInteger revisionId;
    @ManyToMany(mappedBy = "revisionesAvanceCollection")
    private Collection<ArchivosAdjuntos> archivosAdjuntosCollection;
    @JoinColumn(name = "AVANCES_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Avances avancesId;

    public RevisionesAvance() {
    }

    public RevisionesAvance(BigDecimal id) {
        this.id = id;
    }

    public RevisionesAvance(BigDecimal id, Date fechaRevision, Date horaRevision, BigDecimal porcentajeAvance, BigInteger revisionId) {
        this.id = id;
        this.fechaRevision = fechaRevision;
        this.horaRevision = horaRevision;
        this.porcentajeAvance = porcentajeAvance;
        this.revisionId = revisionId;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Date getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(Date fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    public Date getHoraRevision() {
        return horaRevision;
    }

    public void setHoraRevision(Date horaRevision) {
        this.horaRevision = horaRevision;
    }

    public BigDecimal getCalificacionAvance() {
        return calificacionAvance;
    }

    public void setCalificacionAvance(BigDecimal calificacionAvance) {
        this.calificacionAvance = calificacionAvance;
    }

    public BigDecimal getPorcentajeAvance() {
        return porcentajeAvance;
    }

    public void setPorcentajeAvance(BigDecimal porcentajeAvance) {
        this.porcentajeAvance = porcentajeAvance;
    }

    public Serializable getArchivoFormatoEvaluacion() {
        return archivoFormatoEvaluacion;
    }

    public void setArchivoFormatoEvaluacion(Serializable archivoFormatoEvaluacion) {
        this.archivoFormatoEvaluacion = archivoFormatoEvaluacion;
    }

    public String getResumenObservacion() {
        return resumenObservacion;
    }

    public void setResumenObservacion(String resumenObservacion) {
        this.resumenObservacion = resumenObservacion;
    }

    public Serializable getArchivoDetalleObservaciones() {
        return archivoDetalleObservaciones;
    }

    public void setArchivoDetalleObservaciones(Serializable archivoDetalleObservaciones) {
        this.archivoDetalleObservaciones = archivoDetalleObservaciones;
    }

    public BigInteger getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(BigInteger revisionId) {
        this.revisionId = revisionId;
    }

    @XmlTransient
    public Collection<ArchivosAdjuntos> getArchivosAdjuntosCollection() {
        return archivosAdjuntosCollection;
    }

    public void setArchivosAdjuntosCollection(Collection<ArchivosAdjuntos> archivosAdjuntosCollection) {
        this.archivosAdjuntosCollection = archivosAdjuntosCollection;
    }

    public Avances getAvancesId() {
        return avancesId;
    }

    public void setAvancesId(Avances avancesId) {
        this.avancesId = avancesId;
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
        if (!(object instanceof RevisionesAvance)) {
            return false;
        }
        RevisionesAvance other = (RevisionesAvance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.RevisionesAvance[ id=" + id + " ]";
    }
    
}
