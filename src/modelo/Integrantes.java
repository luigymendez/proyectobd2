/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "INTEGRANTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Integrantes.findAll", query = "SELECT i FROM Integrantes i"),
    @NamedQuery(name = "Integrantes.findByIdentificacion", query = "SELECT i FROM Integrantes i WHERE i.identificacion = :identificacion"),
    @NamedQuery(name = "Integrantes.findByNombres", query = "SELECT i FROM Integrantes i WHERE i.nombres = :nombres"),
    @NamedQuery(name = "Integrantes.findByApellidos", query = "SELECT i FROM Integrantes i WHERE i.apellidos = :apellidos"),
    @NamedQuery(name = "Integrantes.findByTelefono", query = "SELECT i FROM Integrantes i WHERE i.telefono = :telefono"),
    @NamedQuery(name = "Integrantes.findByEmail1", query = "SELECT i FROM Integrantes i WHERE i.email1 = :email1"),
    @NamedQuery(name = "Integrantes.findByEmail2", query = "SELECT i FROM Integrantes i WHERE i.email2 = :email2")})
public class Integrantes implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDENTIFICACION")
    private BigDecimal identificacion;
    @Basic(optional = false)
    @Column(name = "NOMBRES")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Column(name = "TELEFONO")
    private String telefono;
    @Basic(optional = false)
    @Column(name = "EMAIL_1")
    private String email1;
    @Column(name = "EMAIL_2")
    private String email2;
    @ManyToMany(mappedBy = "integrantesCollection")
    private Collection<Ideas> ideasCollection;

    public Integrantes() {
    }

    public Integrantes(BigDecimal identificacion) {
        this.identificacion = identificacion;
    }

    public Integrantes(BigDecimal identificacion, String nombres, String apellidos, String email1) {
        this.identificacion = identificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email1 = email1;
    }

    public BigDecimal getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(BigDecimal identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    @XmlTransient
    public Collection<Ideas> getIdeasCollection() {
        return ideasCollection;
    }

    public void setIdeasCollection(Collection<Ideas> ideasCollection) {
        this.ideasCollection = ideasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (identificacion != null ? identificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Integrantes)) {
            return false;
        }
        Integrantes other = (Integrantes) object;
        if ((this.identificacion == null && other.identificacion != null) || (this.identificacion != null && !this.identificacion.equals(other.identificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Integrantes[ identificacion=" + identificacion + " ]";
    }
    
}
