/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ChristianFabian
 */
@Entity
@Table(name = "HORARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Horarios.findAll", query = "SELECT h FROM Horarios h"),
    @NamedQuery(name = "Horarios.findById", query = "SELECT h FROM Horarios h WHERE h.id = :id"),
    @NamedQuery(name = "Horarios.findByDia", query = "SELECT h FROM Horarios h WHERE h.dia = :dia"),
    @NamedQuery(name = "Horarios.findByPeriodo", query = "SELECT h FROM Horarios h WHERE h.periodo = :periodo"),
    @NamedQuery(name = "Horarios.findByFechaInicio", query = "SELECT h FROM Horarios h WHERE h.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Horarios.findByFechaTerminacion", query = "SELECT h FROM Horarios h WHERE h.fechaTerminacion = :fechaTerminacion"),
    @NamedQuery(name = "Horarios.findByHoraInicio", query = "SELECT h FROM Horarios h WHERE h.horaInicio = :horaInicio"),
    @NamedQuery(name = "Horarios.findByHoraFinalizacion", query = "SELECT h FROM Horarios h WHERE h.horaFinalizacion = :horaFinalizacion"),
    @NamedQuery(name = "Horarios.findByDisponible", query = "SELECT h FROM Horarios h WHERE h.disponible = :disponible")})
public class Horarios implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "DIA")
    private String dia;
    @Column(name = "PERIODO")
    private String periodo;
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "FECHA_TERMINACION")
    @Temporal(TemporalType.DATE)
    private Date fechaTerminacion;
    @Column(name = "HORA_INICIO")
    @Temporal(TemporalType.DATE)
    private Date horaInicio;
    @Column(name = "HORA_FINALIZACION")
    @Temporal(TemporalType.DATE)
    private Date horaFinalizacion;
    @Column(name = "DISPONIBLE")
    private Character disponible;
    @JoinColumn(name = "PROYECTOS_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Proyectos proyectosId;
    @JoinColumn(name = "ASESORIAS_ID", referencedColumnName = "ID")
    @ManyToOne
    private Asesorias asesoriasId;

    public Horarios() {
    }

    public Horarios(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaTerminacion() {
        return fechaTerminacion;
    }

    public void setFechaTerminacion(Date fechaTerminacion) {
        this.fechaTerminacion = fechaTerminacion;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFinalizacion() {
        return horaFinalizacion;
    }

    public void setHoraFinalizacion(Date horaFinalizacion) {
        this.horaFinalizacion = horaFinalizacion;
    }

    public Character getDisponible() {
        return disponible;
    }

    public void setDisponible(Character disponible) {
        this.disponible = disponible;
    }

    public Proyectos getProyectosId() {
        return proyectosId;
    }

    public void setProyectosId(Proyectos proyectosId) {
        this.proyectosId = proyectosId;
    }

    public Asesorias getAsesoriasId() {
        return asesoriasId;
    }

    public void setAsesoriasId(Asesorias asesoriasId) {
        this.asesoriasId = asesoriasId;
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
        if (!(object instanceof Horarios)) {
            return false;
        }
        Horarios other = (Horarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Horarios[ id=" + id + " ]";
    }
    
}
