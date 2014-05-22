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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "ENTREGABLES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entregables.findAll", query = "SELECT e FROM Entregables e"),
    @NamedQuery(name = "Entregables.findById", query = "SELECT e FROM Entregables e WHERE e.id = :id"),
    @NamedQuery(name = "Entregables.findByTipo", query = "SELECT e FROM Entregables e WHERE e.tipo = :tipo"),
    @NamedQuery(name = "Entregables.findByValoracion", query = "SELECT e FROM Entregables e WHERE e.valoracion = :valoracion")})
public class Entregables implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "VALORACION")
    private String valoracion;
    @ManyToMany(mappedBy = "entregablesCollection")
    private Collection<ArchivosAdjuntos> archivosAdjuntosCollection;
    @JoinColumn(name = "ESTADOS_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Estados estadosId;
    @JoinColumn(name = "ENTREGAS_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Entregas entregasId;

    public Entregables() {
    }

    public Entregables(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    @XmlTransient
    public Collection<ArchivosAdjuntos> getArchivosAdjuntosCollection() {
        return archivosAdjuntosCollection;
    }

    public void setArchivosAdjuntosCollection(Collection<ArchivosAdjuntos> archivosAdjuntosCollection) {
        this.archivosAdjuntosCollection = archivosAdjuntosCollection;
    }

    public Estados getEstadosId() {
        return estadosId;
    }

    public void setEstadosId(Estados estadosId) {
        this.estadosId = estadosId;
    }

    public Entregas getEntregasId() {
        return entregasId;
    }

    public void setEntregasId(Entregas entregasId) {
        this.entregasId = entregasId;
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
        if (!(object instanceof Entregables)) {
            return false;
        }
        Entregables other = (Entregables) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Entregables[ id=" + id + " ]";
    }
    
}
