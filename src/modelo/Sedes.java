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
@Table(name = "SEDES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sedes.findAll", query = "SELECT s FROM Sedes s"),
    @NamedQuery(name = "Sedes.findById", query = "SELECT s FROM Sedes s WHERE s.id = :id"),
    @NamedQuery(name = "Sedes.findByNombre", query = "SELECT s FROM Sedes s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "Sedes.findByDireccion", query = "SELECT s FROM Sedes s WHERE s.direccion = :direccion")})
public class Sedes implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "DIRECCION")
    private String direccion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sedesId")
    private Collection<EspaciosFisicos> espaciosFisicosCollection;

    public Sedes() {
    }

    public Sedes(BigDecimal id) {
        this.id = id;
    }

    public Sedes(BigDecimal id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @XmlTransient
    public Collection<EspaciosFisicos> getEspaciosFisicosCollection() {
        return espaciosFisicosCollection;
    }

    public void setEspaciosFisicosCollection(Collection<EspaciosFisicos> espaciosFisicosCollection) {
        this.espaciosFisicosCollection = espaciosFisicosCollection;
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
        if (!(object instanceof Sedes)) {
            return false;
        }
        Sedes other = (Sedes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Sedes[ id=" + id + " ]";
    }
    
}
