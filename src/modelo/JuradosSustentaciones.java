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
@Table(name = "JURADOS_SUSTENTACIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "JuradosSustentaciones.findAll", query = "SELECT j FROM JuradosSustentaciones j"),
    @NamedQuery(name = "JuradosSustentaciones.findByDocentesIdentificacion", query = "SELECT j FROM JuradosSustentaciones j WHERE j.juradosSustentacionesPK.docentesIdentificacion = :docentesIdentificacion"),
    @NamedQuery(name = "JuradosSustentaciones.findBySustentacionesId", query = "SELECT j FROM JuradosSustentaciones j WHERE j.juradosSustentacionesPK.sustentacionesId = :sustentacionesId")})
public class JuradosSustentaciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected JuradosSustentacionesPK juradosSustentacionesPK;

    public JuradosSustentaciones() {
    }

    public JuradosSustentaciones(JuradosSustentacionesPK juradosSustentacionesPK) {
        this.juradosSustentacionesPK = juradosSustentacionesPK;
    }

    public JuradosSustentaciones(BigInteger docentesIdentificacion, BigInteger sustentacionesId) {
        this.juradosSustentacionesPK = new JuradosSustentacionesPK(docentesIdentificacion, sustentacionesId);
    }

    public JuradosSustentacionesPK getJuradosSustentacionesPK() {
        return juradosSustentacionesPK;
    }

    public void setJuradosSustentacionesPK(JuradosSustentacionesPK juradosSustentacionesPK) {
        this.juradosSustentacionesPK = juradosSustentacionesPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (juradosSustentacionesPK != null ? juradosSustentacionesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JuradosSustentaciones)) {
            return false;
        }
        JuradosSustentaciones other = (JuradosSustentaciones) object;
        if ((this.juradosSustentacionesPK == null && other.juradosSustentacionesPK != null) || (this.juradosSustentacionesPK != null && !this.juradosSustentacionesPK.equals(other.juradosSustentacionesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.JuradosSustentaciones[ juradosSustentacionesPK=" + juradosSustentacionesPK + " ]";
    }
    
}
