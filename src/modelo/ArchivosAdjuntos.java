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
@Table(name = "ARCHIVOS_ADJUNTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArchivosAdjuntos.findAll", query = "SELECT a FROM ArchivosAdjuntos a"),
    @NamedQuery(name = "ArchivosAdjuntos.findById", query = "SELECT a FROM ArchivosAdjuntos a WHERE a.id = :id"),
    @NamedQuery(name = "ArchivosAdjuntos.findByTipo", query = "SELECT a FROM ArchivosAdjuntos a WHERE a.tipo = :tipo"),
    @NamedQuery(name = "ArchivosAdjuntos.findByUrl", query = "SELECT a FROM ArchivosAdjuntos a WHERE a.url = :url")})
public class ArchivosAdjuntos implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "URL")
    private String url;
    @JoinTable(name = "DOCUMENTOS_ARCHIVOS", joinColumns = {
        @JoinColumn(name = "ARCHIVOS_ADJUNTOS_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "DOCUMENTOS_ID", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<Documentos> documentosCollection;
    @JoinTable(name = "AVANCES_ARCHIVOS", joinColumns = {
        @JoinColumn(name = "ARCHIVOS_ADJUNTOS_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "AVANCES_ID", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<Avances> avancesCollection;
    @JoinTable(name = "IDEAS_ARCHIVOS", joinColumns = {
        @JoinColumn(name = "ARCHIVOS_ADJUNTOS_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "IDEAS_ID", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<Ideas> ideasCollection;
    @JoinTable(name = "REVISIONES_ARCHIVOS_ADJUNTOS", joinColumns = {
        @JoinColumn(name = "ARCHIVOS_ADJUNTOS_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "REVISIONES_AVANCE_ID", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<RevisionesAvance> revisionesAvanceCollection;
    @OneToMany(mappedBy = "archivosAdjuntosId")
    private Collection<Sustentaciones> sustentacionesCollection;

    public ArchivosAdjuntos() {
    }

    public ArchivosAdjuntos(BigDecimal id) {
        this.id = id;
    }

    public ArchivosAdjuntos(BigDecimal id, String tipo) {
        this.id = id;
        this.tipo = tipo;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlTransient
    public Collection<Documentos> getDocumentosCollection() {
        return documentosCollection;
    }

    public void setDocumentosCollection(Collection<Documentos> documentosCollection) {
        this.documentosCollection = documentosCollection;
    }

    @XmlTransient
    public Collection<Avances> getAvancesCollection() {
        return avancesCollection;
    }

    public void setAvancesCollection(Collection<Avances> avancesCollection) {
        this.avancesCollection = avancesCollection;
    }

    @XmlTransient
    public Collection<Ideas> getIdeasCollection() {
        return ideasCollection;
    }

    public void setIdeasCollection(Collection<Ideas> ideasCollection) {
        this.ideasCollection = ideasCollection;
    }

    @XmlTransient
    public Collection<RevisionesAvance> getRevisionesAvanceCollection() {
        return revisionesAvanceCollection;
    }

    public void setRevisionesAvanceCollection(Collection<RevisionesAvance> revisionesAvanceCollection) {
        this.revisionesAvanceCollection = revisionesAvanceCollection;
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
        if (!(object instanceof ArchivosAdjuntos)) {
            return false;
        }
        ArchivosAdjuntos other = (ArchivosAdjuntos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ArchivosAdjuntos[ id=" + id + " ]";
    }
    
}
