/*
 * To change this template, choose Tools | Templates
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
 * @author ChristianFabian
 */
@Embeddable
public class GrupoIntegrantesPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "IDEAS_ID")
    private BigInteger ideasId;
    @Basic(optional = false)
    @Column(name = "INTEGRANTES_IDENTIFICACION")
    private BigInteger integrantesIdentificacion;

    public GrupoIntegrantesPK() {
    }

    public GrupoIntegrantesPK(BigInteger ideasId, BigInteger integrantesIdentificacion) {
        this.ideasId = ideasId;
        this.integrantesIdentificacion = integrantesIdentificacion;
    }

    public BigInteger getIdeasId() {
        return ideasId;
    }

    public void setIdeasId(BigInteger ideasId) {
        this.ideasId = ideasId;
    }

    public BigInteger getIntegrantesIdentificacion() {
        return integrantesIdentificacion;
    }

    public void setIntegrantesIdentificacion(BigInteger integrantesIdentificacion) {
        this.integrantesIdentificacion = integrantesIdentificacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ideasId != null ? ideasId.hashCode() : 0);
        hash += (integrantesIdentificacion != null ? integrantesIdentificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoIntegrantesPK)) {
            return false;
        }
        GrupoIntegrantesPK other = (GrupoIntegrantesPK) object;
        if ((this.ideasId == null && other.ideasId != null) || (this.ideasId != null && !this.ideasId.equals(other.ideasId))) {
            return false;
        }
        if ((this.integrantesIdentificacion == null && other.integrantesIdentificacion != null) || (this.integrantesIdentificacion != null && !this.integrantesIdentificacion.equals(other.integrantesIdentificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.GrupoIntegrantesPK[ ideasId=" + ideasId + ", integrantesIdentificacion=" + integrantesIdentificacion + " ]";
    }
    
}
