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
@Table(name = "PROYECTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proyectos.findAll", query = "SELECT p FROM Proyectos p"),
    @NamedQuery(name = "Proyectos.findById", query = "SELECT p FROM Proyectos p WHERE p.id = :id"),
    @NamedQuery(name = "Proyectos.findByPeriodo", query = "SELECT p FROM Proyectos p WHERE p.periodo = :periodo"),
    @NamedQuery(name = "Proyectos.findByTitulo", query = "SELECT p FROM Proyectos p WHERE p.titulo = :titulo"),
    @NamedQuery(name = "Proyectos.findByGrupo", query = "SELECT p FROM Proyectos p WHERE p.grupo = :grupo"),
    @NamedQuery(name = "Proyectos.findByEmpresa", query = "SELECT p FROM Proyectos p WHERE p.empresa = :empresa"),
    @NamedQuery(name = "Proyectos.findByNotaDefinitiva", query = "SELECT p FROM Proyectos p WHERE p.notaDefinitiva = :notaDefinitiva")})
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
    @Column(name = "TITULO")
    private String titulo;
    @Column(name = "GRUPO")
    private String grupo;
    @Column(name = "EMPRESA")
    private String empresa;
    @Column(name = "NOTA_DEFINITIVA")
    private Double notaDefinitiva;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyectosId")
    private Collection<Asesorias> asesoriasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyectosId")
    private Collection<Horarios> horariosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyectosId")
    private Collection<Entregas> entregasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyectosId")
    private Collection<Fichas> fichasCollection;
    @JoinColumn(name = "ESTADOS_ID", referencedColumnName = "ID")
    @ManyToOne
    private Estados estadosId;
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

    public Proyectos(BigDecimal id, String periodo, String titulo) {
        this.id = id;
        this.periodo = periodo;
        this.titulo = titulo;
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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Double getNotaDefinitiva() {
        return notaDefinitiva;
    }

    public void setNotaDefinitiva(Double notaDefinitiva) {
        this.notaDefinitiva = notaDefinitiva;
    }

    @XmlTransient
    public Collection<Asesorias> getAsesoriasCollection() {
        return asesoriasCollection;
    }

    public void setAsesoriasCollection(Collection<Asesorias> asesoriasCollection) {
        this.asesoriasCollection = asesoriasCollection;
    }

    @XmlTransient
    public Collection<Horarios> getHorariosCollection() {
        return horariosCollection;
    }

    public void setHorariosCollection(Collection<Horarios> horariosCollection) {
        this.horariosCollection = horariosCollection;
    }

    @XmlTransient
    public Collection<Entregas> getEntregasCollection() {
        return entregasCollection;
    }

    public void setEntregasCollection(Collection<Entregas> entregasCollection) {
        this.entregasCollection = entregasCollection;
    }

    @XmlTransient
    public Collection<Fichas> getFichasCollection() {
        return fichasCollection;
    }

    public void setFichasCollection(Collection<Fichas> fichasCollection) {
        this.fichasCollection = fichasCollection;
    }

    public Estados getEstadosId() {
        return estadosId;
    }

    public void setEstadosId(Estados estadosId) {
        this.estadosId = estadosId;
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
