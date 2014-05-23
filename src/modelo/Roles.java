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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ChristianFabian
 */
@Entity
@Table(name = "ROLES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Roles.findAll", query = "SELECT r FROM Roles r"),
    @NamedQuery(name = "Roles.findById", query = "SELECT r FROM Roles r WHERE r.id = :id"),
    @NamedQuery(name = "Roles.findByNombre", query = "SELECT r FROM Roles r WHERE r.nombre = :nombre"),
    @NamedQuery(name = "Roles.findByRolesId", query = "SELECT r FROM Roles r WHERE r.rolesId = :rolesId")})
public class Roles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "ID")
    private BigInteger id;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ROLES_ID")
    private BigDecimal rolesId;
    @ManyToMany(mappedBy = "rolesCollection")
    private Collection<Docentes> docentesCollection;

    public Roles() {
    }

    public Roles(BigDecimal rolesId) {
        this.rolesId = rolesId;
    }

    public Roles(BigDecimal rolesId, BigInteger id, String nombre) {
        this.rolesId = rolesId;
        this.id = id;
        this.nombre = nombre;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getRolesId() {
        return rolesId;
    }

    public void setRolesId(BigDecimal rolesId) {
        this.rolesId = rolesId;
    }

    @XmlTransient
    public Collection<Docentes> getDocentesCollection() {
        return docentesCollection;
    }

    public void setDocentesCollection(Collection<Docentes> docentesCollection) {
        this.docentesCollection = docentesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rolesId != null ? rolesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roles)) {
            return false;
        }
        Roles other = (Roles) object;
        if ((this.rolesId == null && other.rolesId != null) || (this.rolesId != null && !this.rolesId.equals(other.rolesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Roles[ rolesId=" + rolesId + " ]";
    }
    
}
