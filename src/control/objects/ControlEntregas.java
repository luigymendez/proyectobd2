/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.objects;

import controller.EntregasJpaController;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Documentos;
import modelo.Entregas;
import modelo.Estados;
import modelo.Proyectos;
import utilerias.Formateador;

/**
 *
 * @author LuigyMendez
 */
public class ControlEntregas {

    private String mensajeAlerta;
    private EntregasJpaController entregasJpaController = new EntregasJpaController();

    /**
     *
     * @param observacion
     * @param proyecto
     * @return
     */
    public Entregas crearObjetoEntregas(String observacion, Proyectos proyecto) {
        Entregas entregas = null;
        Date fecha = Formateador.calendarToDate(Calendar.getInstance());

        if (observacion.length() < 50) {
            entregas = new Entregas();
            entregas.setFecha(fecha);
            entregas.setObservaciones(observacion);
            entregas.setProyectosId(proyecto);

        } else {
            mensajeAlerta = "Las observaciones de la entrega no pueden tener más de 50 caracteres";
        }
        if (entregas == null) {
            JOptionPane.showMessageDialog(null, mensajeAlerta);
        }

        return entregas;
    }

    /**
     *
     * @param valoracion
     * @param tipo
     * @param estadoSeleccionado
     * @param estadosEntregable
     * @param entrega
     * @return
     */
    public Documentos crearObjetoDocumento(String valoracion, String tipo, String estadoSeleccionado, List<Estados> estadosEntregable, Entregas entrega) {
        Documentos documento = null;
        Estados estadoActualEntrega = null;
        Date fecha = Formateador.calendarToDate(Calendar.getInstance());
        for (Estados estado : estadosEntregable) {
            if (estado.getNombreEstado().equals(estadoSeleccionado)) {
                estadoActualEntrega = estado;
            }
        }

        documento = new Documentos();
        documento.setEntregasId(entrega);
        documento.setEstadosId(estadoActualEntrega);
        documento.setTipo(tipo);
        documento.setValoracion(valoracion);

        return documento;
    }

    /**
     *
     * @param observacion
     * @param proyecto
     * @param entrega
     * @return
     */
    public Entregas modificarObjetoEntregas(String observacion, Proyectos proyecto, Entregas entrega) {
        Date fecha = Formateador.calendarToDate(Calendar.getInstance());

        if (observacion.length() < 50) {
            entrega.setFecha(fecha);
            entrega.setObservaciones(observacion);
            entrega.setProyectosId(proyecto);

        } else {
            mensajeAlerta = "Las observaciones de la entrega no pueden tener más de 50 caracteres";
            entrega = null;
        }
        if (entrega == null) {
            JOptionPane.showMessageDialog(null, mensajeAlerta);
        }

        return entrega;
    }

    /**
     *
     * @param valoracion
     * @param tipo
     * @param estadoSeleccionado
     * @param estadosEntregable
     * @param documento
     * @return
     */
    public Documentos modificarObjetoDocumento(String valoracion, String tipo, String estadoSeleccionado, List<Estados> estadosEntregable, Documentos documento) {
        Estados estadoActualEntrega = null;
        Date fecha = Formateador.calendarToDate(Calendar.getInstance());
        for (Estados estado : estadosEntregable) {
            if (estado.getNombreEstado().equals(estadoSeleccionado)) {
                estadoActualEntrega = estado;
            }
        }

        documento = new Documentos();
        documento.setEstadosId(estadoActualEntrega);
        documento.setTipo(tipo);
        documento.setValoracion(valoracion);

        return documento;
    }

}
