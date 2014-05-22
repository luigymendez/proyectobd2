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
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ChristianFabian
 */
@Entity
@Table(name = "DOCENTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Docentes.findAll", query = "SELECT d FROM Docentes d"),
    @NamedQuery(name = "Docentes.findByIdentificacion", query = "SELECT d FROM Docentes d WHERE d.identificacion = :identificacion"),
    @NamedQuery(name = "Docentes.findByNombres", query = "SELECT d FROM Docentes d WHERE d.nombres = :nombres"),
    @NamedQuery(name = "Docentes.findByApellidos", query = "SELECT d FROM Docentes d WHERE d.apellidos = :apellidos"),
    @NamedQuery(name = "Docentes.findByTelefono", query = "SELECT d FROM Docentes d WHERE d.telefono = :telefono"),
    @NamedQuery(name = "Docentes.findByCorreoInstitucional", query = "SELECT d FROM Docentes d WHERE d.correoInstitucional = :correoInstitucional"),
    @NamedQuery(name = "Docentes.findByCorreoPersonal", query = "SELECT d FROM Docentes d WHERE d.correoPersonal = :correoPersonal"),
    @NamedQuery(name = "Docentes.findByRol", query = "SELECT d FROM Docentes d WHERE d.rol = :rol")})
public class Docentes implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDENTIFICACION")
    private BigDecimal identificacion;
    @Column(name = "NOMBRES")
    private String nombres;
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Column(name = "TELEFONO")
    private BigInteger telefono;
    @Column(name = "CORREO_INSTITUCIONAL")
    private String correoInstitucional;
    @Column(name = "CORREO_PERSONAL")
    private BigInteger correoPersonal;
    @Column(name = "ROL")
    private String rol;
    @JoinTable(name = "DOCENTES_SUSTENTACIONES", joinColumns = {
        @JoinColumn(name = "DOCENTES_IDENTIFICACION", referencedColumnName = "IDENTIFICACION")}, inverseJoinColumns = {
        @JoinColumn(name = "SUSTENTACIONES_ID", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<Sustentaciones> sustentacionesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "docentesIdentificacion")
    private Collection<Usuarios> usuariosCollection;
    @OneToMany(mappedBy = "docentesIdentificacion")
    private Collection<Proyectos> proyectosCollection;

    public Docentes() {
    }

    public Docentes(BigDecimal identificacion) {
        this.identificacion = identificacion;
    }

    public BigDecimal getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(BigDecimal identificacion) {
        BigDecimal oldIdentificacion = this.identificacion;
        this.identificacion = identificacion;
        changeSupport.firePropertyChange("identificacion", oldIdentificacion, identificacion);
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        String oldNombres = this.nombres;
        this.nombres = nombres;
        changeSupport.firePropertyChange("nombres", oldNombres, nombres);
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        String oldApellidos = this.apellidos;
        this.apellidos = apellidos;
        changeSupport.firePropertyChange("apellidos", oldApellidos, apellidos);
    }

    public BigInteger getTelefono() {
        return telefono;
    }

    public void setTelefono(BigInteger telefono) {
        BigInteger oldTelefono = this.telefono;
        this.telefono = telefono;
        changeSupport.firePropertyChange("telefono", oldTelefono, telefono);
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        String oldCorreoInstitucional = this.correoInstitucional;
        this.correoInstitucional = correoInstitucional;
        changeSupport.firePropertyChange("correoInstitucional", oldCorreoInstitucional, correoInstitucional);
    }

    public BigInteger getCorreoPersonal() {
        return correoPersonal;
    }

    public void setCorreoPersonal(BigInteger correoPersonal) {
        BigInteger oldCorreoPersonal = this.correoPersonal;
        this.correoPersonal = correoPersonal;
        changeSupport.firePropertyChange("correoPersonal", oldCorreoPersonal, correoPersonal);
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        String oldRol = this.rol;
        this.rol = rol;
        changeSupport.firePropertyChange("rol", oldRol, rol);
    }

    @XmlTransient
    public Collection<Sustentaciones> getSustentacionesCollection() {
        return sustentacionesCollection;
    }

    public void setSustentacionesCollection(Collection<Sustentaciones> sustentacionesCollection) {
        this.sustentacionesCollection = sustentacionesCollection;
    }

    @XmlTransient
    public Collection<Usuarios> getUsuariosCollection() {
        return usuariosCollection;
    }

    public void setUsuariosCollection(Collection<Usuarios> usuariosCollection) {
        this.usuariosCollection = usuariosCollection;
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
        hash += (identificacion != null ? identificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Docentes)) {
            return false;
        }
        Docentes other = (Docentes) object;
        if ((this.identificacion == null && other.identificacion != null) || (this.identificacion != null && !this.identificacion.equals(other.identificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Docentes[ identificacion=" + identificacion + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
