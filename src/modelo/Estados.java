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
@Table(name = "ESTADOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estados.findAll", query = "SELECT e FROM Estados e"),
    @NamedQuery(name = "Estados.findById", query = "SELECT e FROM Estados e WHERE e.id = :id"),
    @NamedQuery(name = "Estados.findByNombreEstado", query = "SELECT e FROM Estados e WHERE e.nombreEstado = :nombreEstado"),
    @NamedQuery(name = "Estados.findByTipoEstado", query = "SELECT e FROM Estados e WHERE e.tipoEstado = :tipoEstado")})
public class Estados implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "NOMBRE_ESTADO")
    private String nombreEstado;
    @Column(name = "TIPO_ESTADO")
    private String tipoEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadosId")
    private Collection<Ideas> ideasCollection;
    @OneToMany(mappedBy = "estadosId")
    private Collection<Anteproyecto> anteproyectoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadosId")
    private Collection<Propuestas> propuestasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadosId")
    private Collection<Entregables> entregablesCollection;
    @OneToMany(mappedBy = "estadosId")
    private Collection<Fichas> fichasCollection;
    @OneToMany(mappedBy = "estadosId")
    private Collection<Proyectos> proyectosCollection;
    @OneToMany(mappedBy = "estadosId")
    private Collection<Sustentaciones> sustentacionesCollection;

    public Estados() {
    }

    public Estados(BigDecimal id) {
        this.id = id;
    }

    public Estados(BigDecimal id, String nombreEstado) {
        this.id = id;
        this.nombreEstado = nombreEstado;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public String getTipoEstado() {
        return tipoEstado;
    }

    public void setTipoEstado(String tipoEstado) {
        this.tipoEstado = tipoEstado;
    }

    @XmlTransient
    public Collection<Ideas> getIdeasCollection() {
        return ideasCollection;
    }

    public void setIdeasCollection(Collection<Ideas> ideasCollection) {
        this.ideasCollection = ideasCollection;
    }

    @XmlTransient
    public Collection<Anteproyecto> getAnteproyectoCollection() {
        return anteproyectoCollection;
    }

    public void setAnteproyectoCollection(Collection<Anteproyecto> anteproyectoCollection) {
        this.anteproyectoCollection = anteproyectoCollection;
    }

    @XmlTransient
    public Collection<Propuestas> getPropuestasCollection() {
        return propuestasCollection;
    }

    public void setPropuestasCollection(Collection<Propuestas> propuestasCollection) {
        this.propuestasCollection = propuestasCollection;
    }

    @XmlTransient
    public Collection<Entregables> getEntregablesCollection() {
        return entregablesCollection;
    }

    public void setEntregablesCollection(Collection<Entregables> entregablesCollection) {
        this.entregablesCollection = entregablesCollection;
    }

    @XmlTransient
    public Collection<Fichas> getFichasCollection() {
        return fichasCollection;
    }

    public void setFichasCollection(Collection<Fichas> fichasCollection) {
        this.fichasCollection = fichasCollection;
    }

    @XmlTransient
    public Collection<Proyectos> getProyectosCollection() {
        return proyectosCollection;
    }

    public void setProyectosCollection(Collection<Proyectos> proyectosCollection) {
        this.proyectosCollection = proyectosCollection;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estados)) {
            return false;
        }
        Estados other = (Estados) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Estados[ id=" + id + " ]";
    }
    
}
