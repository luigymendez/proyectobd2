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
import javax.persistence.JoinTable;
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
@Table(name = "IDEAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ideas.findAll", query = "SELECT i FROM Ideas i"),
    @NamedQuery(name = "Ideas.findAllIds", query = "SELECT i.id FROM Ideas i"),//creo un query para traer todos los id nada más
    @NamedQuery(name = "Ideas.findById", query = "SELECT i FROM Ideas i WHERE i.id = :id"),
    @NamedQuery(name = "Ideas.findByPeriodo", query = "SELECT i FROM Ideas i WHERE i.periodo = :periodo"),
    @NamedQuery(name = "Ideas.findByGrupo", query = "SELECT i FROM Ideas i WHERE i.grupo = :grupo"),
    @NamedQuery(name = "Ideas.findByTitulo", query = "SELECT i FROM Ideas i WHERE i.titulo = :titulo"),
    @NamedQuery(name = "Ideas.findByDescripcion", query = "SELECT i FROM Ideas i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "Ideas.findByJustificacion", query = "SELECT i FROM Ideas i WHERE i.justificacion = :justificacion"),
    @NamedQuery(name = "Ideas.findByFecha", query = "SELECT i FROM Ideas i WHERE i.fecha = :fecha"),
    @NamedQuery(name = "Ideas.findByHora", query = "SELECT i FROM Ideas i WHERE i.hora = :hora")})
public class Ideas implements Serializable {
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
    @Basic(optional = false)
    @Column(name = "TITULO")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "JUSTIFICACION")
    private String justificacion;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "HORA")
    @Temporal(TemporalType.DATE)
    private Date hora;
    @JoinTable(name = "IDEAS_INTEGRANTES", joinColumns = {
        @JoinColumn(name = "IDEAS_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "INTEGRANTES_IDENTIFICACION", referencedColumnName = "IDENTIFICACION")})
    @ManyToMany
    private Collection<Integrantes> integrantesCollection;
    @ManyToMany(mappedBy = "ideasCollection")
    private Collection<ArchivosAdjuntos> archivosAdjuntosCollection;
    @JoinColumn(name = "ESTADOS_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Estados estadosId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ideasId")
    private Collection<Propuestas> propuestasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ideasId")
    private Collection<Observaciones> observacionesCollection;

    public Ideas() {
    }

    public Ideas(BigDecimal id) {
        this.id = id;
    }

    public Ideas(BigDecimal id, String periodo, String grupo, String titulo, String descripcion, String justificacion, Date fecha) {
        this.id = id;
        this.periodo = periodo;
        this.grupo = grupo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.justificacion = justificacion;
        this.fecha = fecha;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    @XmlTransient
    public Collection<Integrantes> getIntegrantesCollection() {
        return integrantesCollection;
    }

    public void setIntegrantesCollection(Collection<Integrantes> integrantesCollection) {
        this.integrantesCollection = integrantesCollection;
    }

    @XmlTransient
    public Collection<ArchivosAdjuntos> getArchivosAdjuntosCollection() {
        return archivosAdjuntosCollection;
    }

    public void setArchivosAdjuntosCollection(Collection<ArchivosAdjuntos> archivosAdjuntosCollection) {
        this.archivosAdjuntosCollection = archivosAdjuntosCollection;
    }

    public Estados getEstadosId() {
        return estadosId;
    }

    public void setEstadosId(Estados estadosId) {
        this.estadosId = estadosId;
    }

    @XmlTransient
    public Collection<Propuestas> getPropuestasCollection() {
        return propuestasCollection;
    }

    public void setPropuestasCollection(Collection<Propuestas> propuestasCollection) {
        this.propuestasCollection = propuestasCollection;
    }

    @XmlTransient
    public Collection<Observaciones> getObservacionesCollection() {
        return observacionesCollection;
    }

    public void setObservacionesCollection(Collection<Observaciones> observacionesCollection) {
        this.observacionesCollection = observacionesCollection;
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
        if (!(object instanceof Ideas)) {
            return false;
        }
        Ideas other = (Ideas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Ideas[ id=" + id + " ]";
    }
    
}
