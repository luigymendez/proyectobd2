/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ChristianFabian
 */
@Entity
@Table(name = "ENTREGAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entregas.findAll", query = "SELECT e FROM Entregas e"),
    @NamedQuery(name = "Entregas.findById", query = "SELECT e FROM Entregas e WHERE e.id = :id"),
    @NamedQuery(name = "Entregas.findByFecha", query = "SELECT e FROM Entregas e WHERE e.fecha = :fecha"),
    @NamedQuery(name = "Entregas.findByDescripcion", query = "SELECT e FROM Entregas e WHERE e.descripcion = :descripcion")})
public class Entregas implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "PROYECTOS_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Proyectos proyectosId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entregasId")
    private Collection<Entregables> entregablesCollection;

    public Entregas() {
    }

    public Entregas(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Proyectos getProyectosId() {
        return proyectosId;
    }

    public void setProyectosId(Proyectos proyectosId) {
        this.proyectosId = proyectosId;
    }

    @XmlTransient
    public Collection<Entregables> getEntregablesCollection() {
        return entregablesCollection;
    }

    public void setEntregablesCollection(Collection<Entregables> entregablesCollection) {
        this.entregablesCollection = entregablesCollection;
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
        if (!(object instanceof Entregas)) {
            return false;
        }
        Entregas other = (Entregas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Entregas[ id=" + id + " ]";
    }
    
}
