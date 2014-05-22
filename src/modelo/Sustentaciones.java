/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
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
    private String fechaSustentacion;
    @Basic(optional = false)
    @Column(name = "HORA_INICIO")
    private String horaInicio;
    @Basic(optional = false)
    @Column(name = "HORA_FINAL")
    private String horaFinal;
    @Basic(optional = false)
    @Column(name = "OBSERVACION_DOCUMENTO")
    private String observacionDocumento;
    @Basic(optional = false)
    @Column(name = "OBSERVACION_SUSTENTACION_ORAL")
    private String observacionSustentacionOral;
    @ManyToMany(mappedBy = "sustentacionesCollection")
    private Collection<Docentes> docentesCollection;
    @JoinColumn(name = "RESERVAS_NUMERO_RESERVA", referencedColumnName = "NUMERO_RESERVA")
    @ManyToOne(optional = false)
    private Reservas reservasNumeroReserva;
    @JoinColumn(name = "ESTADOS_ID", referencedColumnName = "ID")
    @ManyToOne
    private Estados estadosId;
    @JoinColumn(name = "ARCHIVOS_ADJUNTOS_ID", referencedColumnName = "ID")
    @ManyToOne
    private ArchivosAdjuntos archivosAdjuntosId;

    public Sustentaciones() {
    }

    public Sustentaciones(BigDecimal id) {
        this.id = id;
    }

    public Sustentaciones(BigDecimal id, String fechaSustentacion, String horaInicio, String horaFinal, String observacionDocumento, String observacionSustentacionOral) {
        this.id = id;
        this.fechaSustentacion = fechaSustentacion;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        this.observacionDocumento = observacionDocumento;
        this.observacionSustentacionOral = observacionSustentacionOral;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getFechaSustentacion() {
        return fechaSustentacion;
    }

    public void setFechaSustentacion(String fechaSustentacion) {
        this.fechaSustentacion = fechaSustentacion;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getObservacionDocumento() {
        return observacionDocumento;
    }

    public void setObservacionDocumento(String observacionDocumento) {
        this.observacionDocumento = observacionDocumento;
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

    public Reservas getReservasNumeroReserva() {
        return reservasNumeroReserva;
    }

    public void setReservasNumeroReserva(Reservas reservasNumeroReserva) {
        this.reservasNumeroReserva = reservasNumeroReserva;
    }

    public Estados getEstadosId() {
        return estadosId;
    }

    public void setEstadosId(Estados estadosId) {
        this.estadosId = estadosId;
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
