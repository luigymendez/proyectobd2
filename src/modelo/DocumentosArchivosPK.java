/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author LuigyMendez
 */
@Embeddable
public class DocumentosArchivosPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ARCHIVOS_ADJUNTOS_ID")
    private BigInteger archivosAdjuntosId;
    @Basic(optional = false)
    @Column(name = "DOCUMENTOS_ID")
    private BigInteger documentosId;

    public DocumentosArchivosPK() {
    }

    public DocumentosArchivosPK(BigInteger archivosAdjuntosId, BigInteger documentosId) {
        this.archivosAdjuntosId = archivosAdjuntosId;
        this.documentosId = documentosId;
    }

    public BigInteger getArchivosAdjuntosId() {
        return archivosAdjuntosId;
    }

    public void setArchivosAdjuntosId(BigInteger archivosAdjuntosId) {
        this.archivosAdjuntosId = archivosAdjuntosId;
    }

    public BigInteger getDocumentosId() {
        return documentosId;
    }

    public void setDocumentosId(BigInteger documentosId) {
        this.documentosId = documentosId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (archivosAdjuntosId != null ? archivosAdjuntosId.hashCode() : 0);
        hash += (documentosId != null ? documentosId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentosArchivosPK)) {
            return false;
        }
        DocumentosArchivosPK other = (DocumentosArchivosPK) object;
        if ((this.archivosAdjuntosId == null && other.archivosAdjuntosId != null) || (this.archivosAdjuntosId != null && !this.archivosAdjuntosId.equals(other.archivosAdjuntosId))) {
            return false;
        }
        if ((this.documentosId == null && other.documentosId != null) || (this.documentosId != null && !this.documentosId.equals(other.documentosId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.DocumentosArchivosPK[ archivosAdjuntosId=" + archivosAdjuntosId + ", documentosId=" + documentosId + " ]";
    }
    
}
