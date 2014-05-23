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
@Table(name = "SUSTENTACIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sustentaciones.findAll", query = "SELECT s FROM Sustentaciones s"),
    @NamedQuery(name = "Sustentaciones.findById", query = "SELECT s FROM Sustentaciones s WHERE s.id = :id"),
    @NamedQuery(name = "Sustentaciones.findByFechaSustentacion", query = "SELECT s FROM Sustentaciones s WHERE s.fechaSustentacion = :fechaSustentacion"),
    @NamedQuery(name = "Sustentaciones.findByHoraInicio", query = "SELECT s FROM Sustentaciones s WHERE s.horaInicio = :horaInicio"),
    @NamedQuery(name = "Sustentaciones.findByHoraFinal", query = "SELECT s FROM Sustentaciones s WHERE s.horaFinal = :horaFinal"),
    @NamedQuery(name = "Sustentaciones.findByObservacionDocumento", query = "SELECT s FROM Sustentaciones s WHERE s.observacionDocumento = :observacionDocumento"),
    @NamedQuery(name = "Sustentaciones.findByCalificacion", query = "SELECT s FROM Sustentaciones s WHERE s.calificacion = :calificacion"),
    @NamedQuery(name = "Sustentaciones.findByLugar", query = "SELECT s FROM Sustentaciones s WHERE s.lugar = :lugar"),
    @NamedQuery(name = "Sustentaciones.findByObservacionSustentacionOral", query = "SELECT s FROM Sustentaciones s WHERE s.observacionSustentacionOral = :observacionSustentacionOral")})
public class Sustentaciones implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "FECHA_SUSTENTACION")
    @Temporal(TemporalType.DATE)
    private Date fechaSustentacion;
    @Basic(optional = false)
    @Column(name = "HORA_INICIO")
    @Temporal(TemporalType.DATE)
    private Date horaInicio;
    @Basic(optional = false)
    @Column(name = "HORA_FINAL")
    @Temporal(TemporalType.DATE)
    private Date horaFinal;
    @Column(name = "OBSERVACION_DOCUMENTO")
    private String observacionDocumento;
    @Column(name = "CALIFICACION")
    private BigDecimal calificacion;
    @Column(name = "LUGAR")
    private String lugar;
    @Column(name = "OBSERVACION_SUSTENTACION_ORAL")
    private String observacionSustentacionOral;
    @ManyToMany(mappedBy = "sustentacionesCollection")
    private Collection<Docentes> docentesCollection;
    @ManyToMany(mappedBy = "sustentacionesCollection")
    private Collection<Recursos> recursosCollection;
    @JoinColumn(name = "ESTADOS_ID", referencedColumnName = "ID")
    @ManyToOne
    private Estados estadosId;
    @JoinColumn(name = "ESPACIOS_FISICOS_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private EspaciosFisicos espaciosFisicosId;
    @JoinColumn(name = "ARCHIVOS_ADJUNTOS_ID", referencedColumnName = "ID")
    @ManyToOne
    private ArchivosAdjuntos archivosAdjuntosId;

    public Sustentaciones() {
    }

    public Sustentaciones(BigDecimal id) {
        this.id = id;
    }

    public Sustentaciones(BigDecimal id, Date fechaSustentacion, Date horaInicio, Date horaFinal) {
        this.id = id;
        this.fechaSustentacion = fechaSustentacion;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Date getFechaSustentacion() {
        return fechaSustentacion;
    }

    public void setFechaSustentacion(Date fechaSustentacion) {
        this.fechaSustentacion = fechaSustentacion;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Date horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getObservacionDocumento() {
        return observacionDocumento;
    }

    public void setObservacionDocumento(String observacionDocumento) {
        this.observacionDocumento = observacionDocumento;
    }

    public BigDecimal getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(BigDecimal calificacion) {
        this.calificacion = calificacion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getObservacionSustentacionOral() {
        return observacionSustentacionOral;
    }

    public void setObservacionSustentacionOral(String observacionSustentacionOral) {
        this.observacionSustentacionOral = observacionSustentacionOral;
    }

    @XmlTransient
    public Collection<Docentes> getDocentesCollection() {
        return docentesCollection;
    }

    public void setDocentesCollection(Collection<Docentes> docentesCollection) {
        this.docentesCollection = docentesCollection;
    }

    @XmlTransient
    public Collection<Recursos> getRecursosCollection() {
        return recursosCollection;
    }

    public void setRecursosCollection(Collection<Recursos> recursosCollection) {
        this.recursosCollection = recursosCollection;
    }

    public Estados getEstadosId() {
        return estadosId;
    }

    public void setEstadosId(Estados estadosId) {
        this.estadosId = estadosId;
    }

    public EspaciosFisicos getEspaciosFisicosId() {
        return espaciosFisicosId;
    }

    public void setEspaciosFisicosId(EspaciosFisicos espaciosFisicosId) {
        this.espaciosFisicosId = espaciosFisicosId;
    }

    public ArchivosAdjuntos getArchivosAdjuntosId() {
        return archivosAdjuntosId;
    }

    public void setArchivosAdjuntosId(ArchivosAdjuntos archivosAdjuntosId) {
        this.archivosAdjuntosId = archivosAdjuntosId;
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
        if (!(object instanceof Sustentaciones)) {
            return false;
        }
        Sustentaciones other = (Sustentaciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Sustentaciones[ id=" + id + " ]";
    }
    
}
