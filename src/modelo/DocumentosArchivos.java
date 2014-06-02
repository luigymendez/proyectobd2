/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LuigyMendez
 */
@Entity
@Table(name = "DOCUMENTOS_ARCHIVOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocumentosArchivos.findAll", query = "SELECT d FROM DocumentosArchivos d"),
    @NamedQuery(name = "DocumentosArchivos.findByArchivosAdjuntosId", query = "SELECT d FROM DocumentosArchivos d WHERE d.documentosArchivosPK.archivosAdjuntosId = :archivosAdjuntosId"),
    @NamedQuery(name = "DocumentosArchivos.findByDocumentosId", query = "SELECT d FROM DocumentosArchivos d WHERE d.documentosArchivosPK.documentosId = :documentosId"),
    @NamedQuery(name = "DocumentosArchivos.findByEt", query = "SELECT d FROM DocumentosArchivos d WHERE d.et = :et")})
public class DocumentosArchivos implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DocumentosArchivosPK documentosArchivosPK;
    @Column(name = "ET")
    private BigInteger et;

    public DocumentosArchivos() {
    }

    public DocumentosArchivos(DocumentosArchivosPK documentosArchivosPK) {
        this.documentosArchivosPK = documentosArchivosPK;
    }

    public DocumentosArchivos(BigInteger archivosAdjuntosId, BigInteger documentosId) {
        this.documentosArchivosPK = new DocumentosArchivosPK(archivosAdjuntosId, documentosId);
    }

    public DocumentosArchivosPK getDocumentosArchivosPK() {
        return documentosArchivosPK;
    }

    public void setDocumentosArchivosPK(DocumentosArchivosPK documentosArchivosPK) {
        this.documentosArchivosPK = documentosArchivosPK;
    }

    public BigInteger getEt() {
        return et;
    }

    public void setEt(BigInteger et) {
        this.et = et;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (documentosArchivosPK != null ? documentosArchivosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentosArchivos)) {
            return false;
        }
        DocumentosArchivos other = (DocumentosArchivos) object;
        if ((this.documentosArchivosPK == null && other.documentosArchivosPK != null) || (this.documentosArchivosPK != null && !this.documentosArchivosPK.equals(other.documentosArchivosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.DocumentosArchivos[ documentosArchivosPK=" + documentosArchivosPK + " ]";
    }
    
}
