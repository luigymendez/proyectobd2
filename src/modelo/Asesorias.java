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
@Table(name = "ASESORIAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asesorias.findAll", query = "SELECT a FROM Asesorias a"),
    @NamedQuery(name = "Asesorias.findById", query = "SELECT a FROM Asesorias a WHERE a.id = :id"),
    @NamedQuery(name = "Asesorias.findByPeriodo", query = "SELECT a FROM Asesorias a WHERE a.periodo = :periodo"),
    @NamedQuery(name = "Asesorias.findByTitulo", query = "SELECT a FROM Asesorias a WHERE a.titulo = :titulo"),
    @NamedQuery(name = "Asesorias.findByGrupo", query = "SELECT a FROM Asesorias a WHERE a.grupo = :grupo"),
    @NamedQuery(name = "Asesorias.findByCompromisos", query = "SELECT a FROM Asesorias a WHERE a.compromisos = :compromisos"),
    @NamedQuery(name = "Asesorias.findByRealizada", query = "SELECT a FROM Asesorias a WHERE a.realizada = :realizada"),
    @NamedQuery(name = "Asesorias.findByFecha", query = "SELECT a FROM Asesorias a WHERE a.fecha = :fecha"),
    @NamedQuery(name = "Asesorias.findByHoraInicio", query = "SELECT a FROM Asesorias a WHERE a.horaInicio = :horaInicio"),
    @NamedQuery(name = "Asesorias.findByHoraFin", query = "SELECT a FROM Asesorias a WHERE a.horaFin = :horaFin")})
public class Asesorias implements Serializable {
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
    @Column(name = "TITULO")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "GRUPO")
    private String grupo;
    @Column(name = "COMPROMISOS")
    private String compromisos;
    @Column(name = "REALIZADA")
    private Character realizada;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "HORA_INICIO")
    @Temporal(TemporalType.DATE)
    private Date horaInicio;
    @Column(name = "HORA_FIN")
    @Temporal(TemporalType.DATE)
    private Date horaFin;
    @JoinColumn(name = "PROYECTOS_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Proyectos proyectosId;
    @OneToMany(mappedBy = "asesoriasId")
    private Collection<Horarios> horariosCollection;

    public Asesorias() {
    }

    public Asesorias(BigDecimal id) {
        this.id = id;
    }

    public Asesorias(BigDecimal id, String periodo, String titulo, String grupo) {
        this.id = id;
        this.periodo = periodo;
        this.titulo = titulo;
        this.grupo = grupo;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
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

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public Proyectos getProyectosId() {
        return proyectosId;
    }

    public void setProyectosId(Proyectos proyectosId) {
        this.proyectosId = proyectosId;
    }

    @XmlTransient
    public Collection<Horarios> getHorariosCollection() {
        return horariosCollection;
    }

    public void setHorariosCollection(Collection<Horarios> horariosCollection) {
        this.horariosCollection = horariosCollection;
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
