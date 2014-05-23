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
@Table(name = "ASESORIAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asesorias.findAll", query = "SELECT a FROM Asesorias a"),
    @NamedQuery(name = "Asesorias.findById", query = "SELECT a FROM Asesorias a WHERE a.id = :id"),
    @NamedQuery(name = "Asesorias.findByCompromisos", query = "SELECT a FROM Asesorias a WHERE a.compromisos = :compromisos"),
    @NamedQuery(name = "Asesorias.findByRealizada", query = "SELECT a FROM Asesorias a WHERE a.realizada = :realizada"),
    @NamedQuery(name = "Asesorias.findByFecha", query = "SELECT a FROM Asesorias a WHERE a.fecha = :fecha"),
    @NamedQuery(name = "Asesorias.findByHoraInicio", query = "SELECT a FROM Asesorias a WHERE a.horaInicio = :horaInicio"),
    @NamedQuery(name = "Asesorias.findByHoraFinalizacion", query = "SELECT a FROM Asesorias a WHERE a.horaFinalizacion = :horaFinalizacion"),
    @NamedQuery(name = "Asesorias.findByResumen", query = "SELECT a FROM Asesorias a WHERE a.resumen = :resumen")})
public class Asesorias implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "COMPROMISOS")
    private String compromisos;
    @Column(name = "REALIZADA")
    private Character realizada;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "HORA_INICIO")
    @Temporal(TemporalType.DATE)
    private Date horaInicio;
    @Basic(optional = false)
    @Column(name = "HORA_FINALIZACION")
    @Temporal(TemporalType.DATE)
    private Date horaFinalizacion;
    @Column(name = "RESUMEN")
    private String resumen;
    @JoinColumn(name = "PROYECTOS_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Proyectos proyectosId;

    public Asesorias() {
    }

    public Asesorias(BigDecimal id) {
        this.id = id;
    }

    public Asesorias(BigDecimal id, String compromisos, Date fecha, Date horaInicio, Date horaFinalizacion) {
        this.id = id;
        this.compromisos = compromisos;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFinalizacion = horaFinalizacion;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getCompromisos() {
        return compromisos;
    }

    public void setCompromisos(String compromisos) {
        this.compromisos = compromisos;
    }

    public Character getRealizada() {
        return realizada;
    }

    public void setRealizada(Character realizada) {
        this.realizada = realizada;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public Proyectos getProyectosId() {
        return proyectosId;
    }

    public void setProyectosId(Proyectos proyectosId) {
        this.proyectosId = proyectosId;
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
        if (!(object instanceof Asesorias)) {
            return false;
        }
        Asesorias other = (Asesorias) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Asesorias[ id=" + id + " ]";
    }
    
}
