/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "ANTEPROYECTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Anteproyecto.findAll", query = "SELECT a FROM Anteproyecto a"),
    @NamedQuery(name = "Anteproyecto.findById", query = "SELECT a FROM Anteproyecto a WHERE a.id = :id"),
    @NamedQuery(name = "Anteproyecto.findByTitulo", query = "SELECT a FROM Anteproyecto a WHERE a.titulo = :titulo"),
    @NamedQuery(name = "Anteproyecto.findByPeriodo", query = "SELECT a FROM Anteproyecto a WHERE a.periodo = :periodo"),
    @NamedQuery(name = "Anteproyecto.findByGrupo", query = "SELECT a FROM Anteproyecto a WHERE a.grupo = :grupo"),
    @NamedQuery(name = "Anteproyecto.findByNotaDefinitiva", query = "SELECT a FROM Anteproyecto a WHERE a.notaDefinitiva = :notaDefinitiva"),
    @NamedQuery(name = "Anteproyecto.findByEstadosId", query = "SELECT a FROM Anteproyecto a WHERE a.estadosId = :estadosId")})
public class Anteproyecto implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "TITULO")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "PERIODO")
    private String periodo;
    @Basic(optional = false)
    @Column(name = "GRUPO")
    private String grupo;
    @Column(name = "NOTA_DEFINITIVA")
    private BigDecimal notaDefinitiva;
    @Column(name = "ESTADOS_ID")
    private BigInteger estadosId;
    @JoinColumn(name = "PROPUESTAS_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Propuestas propuestasId;
    @OneToMany(mappedBy = "anteproyectoId")
    private Collection<Avances> avancesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anteproyectoId")
    private Collection<Proyectos> proyectosCollection;

    public Anteproyecto() {
    }

    public Anteproyecto(BigDecimal id) {
        this.id = id;
    }

    public Anteproyecto(BigDecimal id, String titulo, String periodo, String grupo) {
        this.id = id;
        this.titulo = titulo;
        this.periodo = periodo;
        this.grupo = grupo;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public BigDecimal getNotaDefinitiva() {
        return notaDefinitiva;
    }

    public void setNotaDefinitiva(BigDecimal notaDefinitiva) {
        this.notaDefinitiva = notaDefinitiva;
    }

    public BigInteger getEstadosId() {
        return estadosId;
    }

    public void setEstadosId(BigInteger estadosId) {
        this.estadosId = estadosId;
    }

    public Propuestas getPropuestasId() {
        return propuestasId;
    }

    public void setPropuestasId(Propuestas propuestasId) {
        this.propuestasId = propuestasId;
    }

    @XmlTransient
    public Collection<Avances> getAvancesCollection() {
        return avancesCollection;
    }

    public void setAvancesCollection(Collection<Avances> avancesCollection) {
        this.avancesCollection = avancesCollection;
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
        if (!(object instanceof Anteproyecto)) {
            return false;
        }
        Anteproyecto other = (Anteproyecto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Anteproyecto[ id=" + id + " ]";
    }
    
}
