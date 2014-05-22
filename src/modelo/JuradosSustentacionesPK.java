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
public class JuradosSustentacionesPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "DOCENTES_IDENTIFICACION")
    private BigInteger docentesIdentificacion;
    @Basic(optional = false)
    @Column(name = "SUSTENTACIONES_ID")
    private BigInteger sustentacionesId;

    public JuradosSustentacionesPK() {
    }

    public JuradosSustentacionesPK(BigInteger docentesIdentificacion, BigInteger sustentacionesId) {
        this.docentesIdentificacion = docentesIdentificacion;
        this.sustentacionesId = sustentacionesId;
    }

    public BigInteger getDocentesIdentificacion() {
        return docentesIdentificacion;
    }

    public void setDocentesIdentificacion(BigInteger docentesIdentificacion) {
        this.docentesIdentificacion = docentesIdentificacion;
    }

    public BigInteger getSustentacionesId() {
        return sustentacionesId;
    }

    public void setSustentacionesId(BigInteger sustentacionesId) {
        this.sustentacionesId = sustentacionesId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (docentesIdentificacion != null ? docentesIdentificacion.hashCode() : 0);
        hash += (sustentacionesId != null ? sustentacionesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JuradosSustentacionesPK)) {
            return false;
        }
        JuradosSustentacionesPK other = (JuradosSustentacionesPK) object;
        if ((this.docentesIdentificacion == null && other.docentesIdentificacion != null) || (this.docentesIdentificacion != null && !this.docentesIdentificacion.equals(other.docentesIdentificacion))) {
            return false;
        }
        if ((this.sustentacionesId == null && other.sustentacionesId != null) || (this.sustentacionesId != null && !this.sustentacionesId.equals(other.sustentacionesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.JuradosSustentacionesPK[ docentesIdentificacion=" + docentesIdentificacion + ", sustentacionesId=" + sustentacionesId + " ]";
    }
    
}
