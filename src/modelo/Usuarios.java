/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ChristianFabian
 */
@Entity
@Table(name = "USUARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findById", query = "SELECT u FROM Usuarios u WHERE u.id = :id"),
    @NamedQuery(name = "Usuarios.findByUsuario", query = "SELECT u FROM Usuarios u WHERE u.usuario = :usuario"),
    @NamedQuery(name = "Usuarios.findByPassword", query = "SELECT u FROM Usuarios u WHERE u.password = :password"),
    @NamedQuery(name = "Usuarios.findByActivo", query = "SELECT u FROM Usuarios u WHERE u.activo = :activo")})
public class Usuarios implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "USUARIO")
    private BigInteger usuario;
    @Column(name = "PASSWORD")
    private BigInteger password;
    @Column(name = "ACTIVO")
    private Character activo;
    @JoinColumn(name = "DOCENTES_IDENTIFICACION", referencedColumnName = "IDENTIFICACION")
    @ManyToOne(optional = false)
    private Docentes docentesIdentificacion;

    public Usuarios() {
    }

    public Usuarios(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        BigDecimal oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public BigInteger getUsuario() {
        return usuario;
    }

    public void setUsuario(BigInteger usuario) {
        BigInteger oldUsuario = this.usuario;
        this.usuario = usuario;
        changeSupport.firePropertyChange("usuario", oldUsuario, usuario);
    }

    public BigInteger getPassword() {
        return password;
    }

    public void setPassword(BigInteger password) {
        BigInteger oldPassword = this.password;
        this.password = password;
        changeSupport.firePropertyChange("password", oldPassword, password);
    }

    public Character getActivo() {
        return activo;
    }

    public void setActivo(Character activo) {
        Character oldActivo = this.activo;
        this.activo = activo;
        changeSupport.firePropertyChange("activo", oldActivo, activo);
    }

    public Docentes getDocentesIdentificacion() {
        return docentesIdentificacion;
    }

    public void setDocentesIdentificacion(Docentes docentesIdentificacion) {
        Docentes oldDocentesIdentificacion = this.docentesIdentificacion;
        this.docentesIdentificacion = docentesIdentificacion;
        changeSupport.firePropertyChange("docentesIdentificacion", oldDocentesIdentificacion, docentesIdentificacion);
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
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Usuarios[ id=" + id + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
