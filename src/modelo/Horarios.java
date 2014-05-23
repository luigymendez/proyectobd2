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
@Table(name = "HORARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Horarios.findAll", query = "SELECT h FROM Horarios h"),
    @NamedQuery(name = "Horarios.findById", query = "SELECT h FROM Horarios h WHERE h.id = :id"),
    @NamedQuery(name = "Horarios.findByDia", query = "SELECT h FROM Horarios h WHERE h.dia = :dia"),
    @NamedQuery(name = "Horarios.findByFechaInicioAsesoria", query = "SELECT h FROM Horarios h WHERE h.fechaInicioAsesoria = :fechaInicioAsesoria"),
    @NamedQuery(name = "Horarios.findByFechaTerminacionAsesoria", query = "SELECT h FROM Horarios h WHERE h.fechaTerminacionAsesoria = :fechaTerminacionAsesoria"),
    @NamedQuery(name = "Horarios.findByHoraInicio", query = "SELECT h FROM Horarios h WHERE h.horaInicio = :horaInicio"),
    @NamedQuery(name = "Horarios.findByHoraFinalizacion", query = "SELECT h FROM Horarios h WHERE h.horaFinalizacion = :horaFinalizacion")})
public class Horarios implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "DIA")
    private String dia;
    @Column(name = "FECHA_INICIO_ASESORIA")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioAsesoria;
    @Column(name = "FECHA_TERMINACION_ASESORIA")
    @Temporal(TemporalType.DATE)
    private Date fechaTerminacionAsesoria;
    @Column(name = "HORA_INICIO")
    @Temporal(TemporalType.DATE)
    private Date horaInicio;
    @Column(name = "HORA_FINALIZACION")
    @Temporal(TemporalType.DATE)
    private Date horaFinalizacion;
    @JoinColumn(name = "DOCENTES_IDENTIFICACION", referencedColumnName = "IDENTIFICACION")
    @ManyToOne(optional = false)
    private Docentes docentesIdentificacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "horariosId")
    private Collection<Proyectos> proyectosCollection;

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

    public Date getFechaInicioAsesoria() {
        return fechaInicioAsesoria;
    }

    public void setFechaInicioAsesoria(Date fechaInicioAsesoria) {
        this.fechaInicioAsesoria = fechaInicioAsesoria;
    }

    public Date getFechaTerminacionAsesoria() {
        return fechaTerminacionAsesoria;
    }

    public void setFechaTerminacionAsesoria(Date fechaTerminacionAsesoria) {
        this.fechaTerminacionAsesoria = fechaTerminacionAsesoria;
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

    public Docentes getDocentesIdentificacion() {
        return docentesIdentificacion;
    }

    public void setDocentesIdentificacion(Docentes docentesIdentificacion) {
        this.docentesIdentificacion = docentesIdentificacion;
    }

    @XmlTransient
    public Collection<Proyectos> getProyectosCollection() {
        return proyectosCollection;
    }

    public void setProyectosCollection(Collection<Proyectos> proyectosCollection) {
        this.proyectosCollection = proyectosCollection;
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
