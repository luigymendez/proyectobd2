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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "DOCENTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Docentes.findAll", query = "SELECT d FROM Docentes d"),
    @NamedQuery(name = "Docentes.findByIdentificacion", query = "SELECT d FROM Docentes d WHERE d.identificacion = :identificacion"),
    @NamedQuery(name = "Docentes.findByNombres", query = "SELECT d FROM Docentes d WHERE d.nombres = :nombres"),
    @NamedQuery(name = "Docentes.findByApellidos", query = "SELECT d FROM Docentes d WHERE d.apellidos = :apellidos"),
    @NamedQuery(name = "Docentes.findByTelefono", query = "SELECT d FROM Docentes d WHERE d.telefono = :telefono"),
    @NamedQuery(name = "Docentes.findByCorreoInstitucional", query = "SELECT d FROM Docentes d WHERE d.correoInstitucional = :correoInstitucional"),
    @NamedQuery(name = "Docentes.findByCorreoPersonal", query = "SELECT d FROM Docentes d WHERE d.correoPersonal = :correoPersonal")})
public class Docentes implements Serializable {
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
    @Basic(optional = false)
    @Column(name = "TELEFONO")
    private BigInteger telefono;
    @Column(name = "CORREO_INSTITUCIONAL")
    private String correoInstitucional;
    @Basic(optional = false)
    @Column(name = "CORREO_PERSONAL")
    private BigInteger correoPersonal;
    @JoinTable(name = "DOCENTES_SUSTENTACIONES", joinColumns = {
        @JoinColumn(name = "DOCENTES_IDENTIFICACION", referencedColumnName = "IDENTIFICACION")}, inverseJoinColumns = {
        @JoinColumn(name = "SUSTENTACIONES_ID", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<Sustentaciones> sustentacionesCollection;
    @JoinTable(name = "DOCENTES_ROLES", joinColumns = {
        @JoinColumn(name = "DOCENTES_IDENTIFICACION", referencedColumnName = "IDENTIFICACION")}, inverseJoinColumns = {
        @JoinColumn(name = "ROLES_ROLES_ID", referencedColumnName = "ROLES_ID")})
    @ManyToMany
    private Collection<Roles> rolesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "docentesIdentificacion")
    private Collection<Usuarios> usuariosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "docentesIdentificacion")
    private Collection<Horarios> horariosCollection;
    @OneToMany(mappedBy = "docentesIdentificacion")
    private Collection<Proyectos> proyectosCollection;

    public Docentes() {
    }

    public Docentes(BigDecimal identificacion) {
        this.identificacion = identificacion;
    }

    public Docentes(BigDecimal identificacion, String nombres, String apellidos, BigInteger telefono, BigInteger correoPersonal) {
        this.identificacion = identificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correoPersonal = correoPersonal;
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

    public BigInteger getTelefono() {
        return telefono;
    }

    public void setTelefono(BigInteger telefono) {
        this.telefono = telefono;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public BigInteger getCorreoPersonal() {
        return correoPersonal;
    }

    public void setCorreoPersonal(BigInteger correoPersonal) {
        this.correoPersonal = correoPersonal;
    }

    @XmlTransient
    public Collection<Sustentaciones> getSustentacionesCollection() {
        return sustentacionesCollection;
    }

    public void setSustentacionesCollection(Collection<Sustentaciones> sustentacionesCollection) {
        this.sustentacionesCollection = sustentacionesCollection;
    }

    @XmlTransient
    public Collection<Roles> getRolesCollection() {
        return rolesCollection;
    }

    public void setRolesCollection(Collection<Roles> rolesCollection) {
        this.rolesCollection = rolesCollection;
    }

    @XmlTransient
    public Collection<Usuarios> getUsuariosCollection() {
        return usuariosCollection;
    }

    public void setUsuariosCollection(Collection<Usuarios> usuariosCollection) {
        this.usuariosCollection = usuariosCollection;
    }

    @XmlTransient
    public Collection<Horarios> getHorariosCollection() {
        return horariosCollection;
    }

    public void setHorariosCollection(Collection<Horarios> horariosCollection) {
        this.horariosCollection = horariosCollection;
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
    
}
