/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ChristianFabian
 */
@Entity
@Table(name = "RESERVAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservas.findAll", query = "SELECT r FROM Reservas r"),
    @NamedQuery(name = "Reservas.findByNumeroReserva", query = "SELECT r FROM Reservas r WHERE r.numeroReserva = :numeroReserva"),
    @NamedQuery(name = "Reservas.findByFechaReserva", query = "SELECT r FROM Reservas r WHERE r.fechaReserva = :fechaReserva"),
    @NamedQuery(name = "Reservas.findByHoraInicio", query = "SELECT r FROM Reservas r WHERE r.horaInicio = :horaInicio"),
    @NamedQuery(name = "Reservas.findByHoraFinal", query = "SELECT r FROM Reservas r WHERE r.horaFinal = :horaFinal")})
public class Reservas implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "NUMERO_RESERVA")
    private BigDecimal numeroReserva;
    @Basic(optional = false)
    @Column(name = "FECHA_RESERVA")
    private String fechaReserva;
    @Basic(optional = false)
    @Column(name = "HORA_INICIO")
    private String horaInicio;
    @Basic(optional = false)
    @Column(name = "HORA_FINAL")
    private String horaFinal;
    @JoinColumn(name = "AUDITORIOS_NUMERO_AUDITORIO", referencedColumnName = "NUMERO_AUDITORIO")
    @ManyToOne(optional = false)
    private Auditorios auditoriosNumeroAuditorio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reservasNumeroReserva")
    private Collection<Sustentaciones> sustentacionesCollection;

    public Reservas() {
    }

    public Reservas(BigDecimal numeroReserva) {
        this.numeroReserva = numeroReserva;
    }

    public Reservas(BigDecimal numeroReserva, String fechaReserva, String horaInicio, String horaFinal) {
        this.numeroReserva = numeroReserva;
        this.fechaReserva = fechaReserva;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
    }

    public BigDecimal getNumeroReserva() {
        return numeroReserva;
    }

    public void setNumeroReserva(BigDecimal numeroReserva) {
        this.numeroReserva = numeroReserva;
    }

    public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
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

    public Auditorios getAuditoriosNumeroAuditorio() {
        return auditoriosNumeroAuditorio;
    }

    public void setAuditoriosNumeroAuditorio(Auditorios auditoriosNumeroAuditorio) {
        this.auditoriosNumeroAuditorio = auditoriosNumeroAuditorio;
    }

    @XmlTransient
    public Collection<Sustentaciones> getSustentacionesCollection() {
        return sustentacionesCollection;
    }

    public void setSustentacionesCollection(Collection<Sustentaciones> sustentacionesCollection) {
        this.sustentacionesCollection = sustentacionesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroReserva != null ? numeroReserva.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservas)) {
            return false;
        }
        Reservas other = (Reservas) object;
        if ((this.numeroReserva == null && other.numeroReserva != null) || (this.numeroReserva != null && !this.numeroReserva.equals(other.numeroReserva))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Reservas[ numeroReserva=" + numeroReserva + " ]";
    }
    
}
