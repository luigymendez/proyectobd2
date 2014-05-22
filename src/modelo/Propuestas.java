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
@Table(name = "PROPUESTAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Propuestas.findAll", query = "SELECT p FROM Propuestas p"),
    @NamedQuery(name = "Propuestas.findById", query = "SELECT p FROM Propuestas p WHERE p.id = :id"),
    @NamedQuery(name = "Propuestas.findByPeriodo", query = "SELECT p FROM Propuestas p WHERE p.periodo = :periodo"),
    @NamedQuery(name = "Propuestas.findByGrupo", query = "SELECT p FROM Propuestas p WHERE p.grupo = :grupo"),
    @NamedQuery(name = "Propuestas.findByTitulo", query = "SELECT p FROM Propuestas p WHERE p.titulo = :titulo"),
    @NamedQuery(name = "Propuestas.findByNotaDefinitiva", query = "SELECT p FROM Propuestas p WHERE p.notaDefinitiva = :notaDefinitiva")})
public class Propuestas implements Serializable {
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
    @Column(name = "NOTA_DEFINITIVA")
    private BigDecimal notaDefinitiva;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propuestasId")
    private Collection<Anteproyecto> anteproyectoCollection;
    @OneToMany(mappedBy = "propuestasId")
    private Collection<Avances> avancesCollection;
    @JoinColumn(name = "IDEAS_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Ideas ideasId;
    @JoinColumn(name = "ESTADOS_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Estados estadosId;

    public Propuestas() {
    }

    public Propuestas(BigDecimal id) {
        this.id = id;
    }

    public Propuestas(BigDecimal id, String periodo, String grupo, String titulo) {
        this.id = id;
        this.periodo = periodo;
        this.grupo = grupo;
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

    public BigDecimal getNotaDefinitiva() {
        return notaDefinitiva;
    }

    public void setNotaDefinitiva(BigDecimal notaDefinitiva) {
        this.notaDefinitiva = notaDefinitiva;
    }

    @XmlTransient
    public Collection<Anteproyecto> getAnteproyectoCollection() {
        return anteproyectoCollection;
    }

    public void setAnteproyectoCollection(Collection<Anteproyecto> anteproyectoCollection) {
        this.anteproyectoCollection = anteproyectoCollection;
    }

    @XmlTransient
    public Collection<Avances> getAvancesCollection() {
        return avancesCollection;
    }

    public void setAvancesCollection(Collection<Avances> avancesCollection) {
        this.avancesCollection = avancesCollection;
    }

    public Ideas getIdeasId() {
        return ideasId;
    }

    public void setIdeasId(Ideas ideasId) {
        this.ideasId = ideasId;
    }

    public Estados getEstadosId() {
        return estadosId;
    }

    public void setEstadosId(Estados estadosId) {
        this.estadosId = estadosId;
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
        if (!(object instanceof Propuestas)) {
            return false;
        }
        Propuestas other = (Propuestas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Propuestas[ id=" + id + " ]";
    }
    
}
