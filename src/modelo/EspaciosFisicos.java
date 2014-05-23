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
@Table(name = "ESPACIOS_FISICOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EspaciosFisicos.findAll", query = "SELECT e FROM EspaciosFisicos e"),
    @NamedQuery(name = "EspaciosFisicos.findById", query = "SELECT e FROM EspaciosFisicos e WHERE e.id = :id"),
    @NamedQuery(name = "EspaciosFisicos.findByNombre", query = "SELECT e FROM EspaciosFisicos e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "EspaciosFisicos.findByCapacidad", query = "SELECT e FROM EspaciosFisicos e WHERE e.capacidad = :capacidad"),
    @NamedQuery(name = "EspaciosFisicos.findByDescripcion", query = "SELECT e FROM EspaciosFisicos e WHERE e.descripcion = :descripcion")})
public class EspaciosFisicos implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "CAPACIDAD")
    private BigInteger capacidad;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "SEDES_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Sedes sedesId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "espaciosFisicosId")
    private Collection<Sustentaciones> sustentacionesCollection;

    public EspaciosFisicos() {
    }

    public EspaciosFisicos(BigDecimal id) {
        this.id = id;
    }

    public EspaciosFisicos(BigDecimal id, String nombre, BigInteger capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigInteger getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(BigInteger capacidad) {
        this.capacidad = capacidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Sedes getSedesId() {
        return sedesId;
    }

    public void setSedesId(Sedes sedesId) {
        this.sedesId = sedesId;
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
        if (!(object instanceof EspaciosFisicos)) {
            return false;
        }
        EspaciosFisicos other = (EspaciosFisicos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.EspaciosFisicos[ id=" + id + " ]";
    }
    
}
