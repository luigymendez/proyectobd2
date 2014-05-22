/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ChristianFabian
 */
@Entity
@Table(name = "GRUPO_INTEGRANTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoIntegrantes.findAll", query = "SELECT g FROM GrupoIntegrantes g"),
    @NamedQuery(name = "GrupoIntegrantes.findByIdeasId", query = "SELECT g FROM GrupoIntegrantes g WHERE g.grupoIntegrantesPK.ideasId = :ideasId"),
    @NamedQuery(name = "GrupoIntegrantes.findByIntegrantesIdentificacion", query = "SELECT g FROM GrupoIntegrantes g WHERE g.grupoIntegrantesPK.integrantesIdentificacion = :integrantesIdentificacion")})
public class GrupoIntegrantes implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GrupoIntegrantesPK grupoIntegrantesPK;

    public GrupoIntegrantes() {
    }

    public GrupoIntegrantes(GrupoIntegrantesPK grupoIntegrantesPK) {
        this.grupoIntegrantesPK = grupoIntegrantesPK;
    }

    public GrupoIntegrantes(BigInteger ideasId, BigInteger integrantesIdentificacion) {
        this.grupoIntegrantesPK = new GrupoIntegrantesPK(ideasId, integrantesIdentificacion);
    }

    public GrupoIntegrantesPK getGrupoIntegrantesPK() {
        return grupoIntegrantesPK;
    }

    public void setGrupoIntegrantesPK(GrupoIntegrantesPK grupoIntegrantesPK) {
        this.grupoIntegrantesPK = grupoIntegrantesPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grupoIntegrantesPK != null ? grupoIntegrantesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoIntegrantes)) {
            return false;
        }
        GrupoIntegrantes other = (GrupoIntegrantes) object;
        if ((this.grupoIntegrantesPK == null && other.grupoIntegrantesPK != null) || (this.grupoIntegrantesPK != null && !this.grupoIntegrantesPK.equals(other.grupoIntegrantesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.GrupoIntegrantes[ grupoIntegrantesPK=" + grupoIntegrantesPK + " ]";
    }
    
}
