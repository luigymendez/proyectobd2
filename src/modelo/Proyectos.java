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
@Table(name = "PROYECTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proyectos.findAll", query = "SELECT p FROM Proyectos p"),
    @NamedQuery(name = "Proyectos.findById", query = "SELECT p FROM Proyectos p WHERE p.id = :id"),
    @NamedQuery(name = "Proyectos.findByPeriodo", query = "SELECT p FROM Proyectos p WHERE p.periodo = :periodo"),
    @NamedQuery(name = "Proyectos.findByGrupo", query = "SELECT p FROM Proyectos p WHERE p.grupo = :grupo"),
    @NamedQuery(name = "Proyectos.findByNotaDefinitiva", query = "SELECT p FROM Proyectos p WHERE p.notaDefinitiva = :notaDefinitiva"),
    @NamedQuery(name = "Proyectos.findByFechaRecepcion", query = "SELECT p FROM Proyectos p WHERE p.fechaRecepcion = :fechaRecepcion"),
    @NamedQuery(name = "Proyectos.findByHoraRecepcion", query = "SELECT p FROM Proyectos p WHERE p.horaRecepcion = :horaRecepcion")})
public class Proyectos implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "PERIODO")
    private String periodo;
    @Basic(optional = false)
    @Column(name = "GRUPO")
    private String grupo;
    @Column(name = "NOTA_DEFINITIVA")
    private Double notaDefinitiva;
    @Basic(optional = false)
    @Column(name = "FECHA_RECEPCION")
    @Temporal(TemporalType.DATE)
    private Date fechaRecepcion;
    @Basic(optional = false)
    @Column(name = "HORA_RECEPCION")
    @Temporal(TemporalType.DATE)
    private Date horaRecepcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyectosId")
    private Collection<Asesorias> asesoriasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyectosId")
    private Collection<Entregas> entregasCollection;
    @JoinColumn(name = "HORARIOS_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Horarios horariosId;
    @JoinColumn(name = "ESTADOS_ID", referencedColumnName = "ID")
    @ManyToOne
    private Estados estadosId;
    @JoinColumn(name = "EMPRESAS_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Empresas empresasId;
    @JoinColumn(name = "DOCENTES_IDENTIFICACION", referencedColumnName = "IDENTIFICACION")
    @ManyToOne
    private Docentes docentesIdentificacion;
    @JoinColumn(name = "ANTEPROYECTO_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Anteproyecto anteproyectoId;

    public Proyectos() {
    }

    public Proyectos(BigDecimal id) {
        this.id = id;
    }

    public Proyectos(BigDecimal id, String periodo, String grupo, Date fechaRecepcion, Date horaRecepcion) {
        this.id = id;
        this.periodo = periodo;
        this.grupo = grupo;
        this.fechaRecepcion = fechaRecepcion;
        this.horaRecepcion = horaRecepcion;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public Double getNotaDefinitiva() {
        return notaDefinitiva;
    }

    public void setNotaDefinitiva(Double notaDefinitiva) {
        this.notaDefinitiva = notaDefinitiva;
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
    public Collection<Asesorias> getAsesoriasCollection() {
        return asesoriasCollection;
    }

    public void setAsesoriasCollection(Collection<Asesorias> asesoriasCollection) {
        this.asesoriasCollection = asesoriasCollection;
    }

    @XmlTransient
    public Collection<Entregas> getEntregasCollection() {
        return entregasCollection;
    }

    public void setEntregasCollection(Collection<Entregas> entregasCollection) {
        this.entregasCollection = entregasCollection;
    }

    public Horarios getHorariosId() {
        return horariosId;
    }

    public void setHorariosId(Horarios horariosId) {
        this.horariosId = horariosId;
    }

    public Estados getEstadosId() {
        return estadosId;
    }

    public void setEstadosId(Estados estadosId) {
        this.estadosId = estadosId;
    }

    public Empresas getEmpresasId() {
        return empresasId;
    }

    public void setEmpresasId(Empresas empresasId) {
        this.empresasId = empresasId;
    }

    public Docentes getDocentesIdentificacion() {
        return docentesIdentificacion;
    }

    public void setDocentesIdentificacion(Docentes docentesIdentificacion) {
        this.docentesIdentificacion = docentesIdentificacion;
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
        if (!(object instanceof Proyectos)) {
            return false;
        }
        Proyectos other = (Proyectos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Proyectos[ id=" + id + " ]";
    }
    
}
