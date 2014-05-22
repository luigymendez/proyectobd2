/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ChristianFabian
 */
@Entity
@Table(name = "OBSERVACIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Observaciones.findAll", query = "SELECT o FROM Observaciones o"),
    @NamedQuery(name = "Observaciones.findById", query = "SELECT o FROM Observaciones o WHERE o.id = :id"),
    @NamedQuery(name = "Observaciones.findByTitulo", query = "SELECT o FROM Observaciones o WHERE o.titulo = :titulo"),
    @NamedQuery(name = "Observaciones.findByDescripcion", query = "SELECT o FROM Observaciones o WHERE o.descripcion = :descripcion"),
    @NamedQuery(name = "Observaciones.findByFecha", query = "SELECT o FROM Observaciones o WHERE o.fecha = :fecha"),
    @NamedQuery(name = "Observaciones.findByHora", query = "SELECT o FROM Observaciones o WHERE o.hora = :hora")})
public class Observaciones implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "TITULO")
    private String titulo;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "HORA")
    @Temporal(TemporalType.DATE)
    private Date hora;
    @JoinColumn(name = "IDEAS_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Ideas ideasId;

    public Observaciones() {
    }

    public Observaciones(BigDecimal id) {
        this.id = id;
    }

    public Observaciones(BigDecimal id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Ideas getIdeasId() {
        return ideasId;
    }

    public void setIdeasId(Ideas ideasId) {
        this.ideasId = ideasId;
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
        if (!(object instanceof Observaciones)) {
            return false;
        }
        Observaciones other = (Observaciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Observaciones[ id=" + id + " ]";
    }
    
}
