/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.objects;

import controller.AnteproyectoJpaController;
import controller.EmpresasJpaController;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.xml.soap.Text;
import modelo.Anteproyecto;
import modelo.Empresas;
import modelo.Estados;
import modelo.Proyectos;
import utilerias.Formateador;
import utilerias.TecladoException;

/**
 *
 * @author LuigyMendez
 */
public class ControlProyectos {

    private Proyectos proyecto;
    private String mensajeAlerta;
    private EmpresasJpaController  empresasJpaController= new EmpresasJpaController();
    private AnteproyectoJpaController anteproyectoJpaController= new AnteproyectoJpaController();
    
    /**
     * El parametro args recibe 
     *      args[0] = jYearChooser2.getYear();
            args[1] = jComboBoxPeriodo.getSelectedItem().toString();
            args[2] = jTextFieldGrupo.getText();
            args[3] = fechaRecepcionDateChooser.getDate();
            args[4] = jTextFieldHora1.getText();
            args[5] = jTextFieldMinuto1.getText();
            args[6] = jTextFieldEmpresa.getText();
            args[7] = jTextFieldAnteproyecto.getText();
            args[8] = jTextFieldNota.getText();
            args[9] = jComboBoxEstado.getSelectedItem().toString();
     * @param args
     * @param estados
     * @return
     */
    public Proyectos crearObjetoProyecto(Object args[],List<Estados> estados) {
        Estados estadoActual = null;
        String periodo = args[0] +"-"+args[1];
        String grupo = args[2].toString();
        Date fechaRecepcion = (Date) args[3];
        int hora= TecladoException.getEntero(args[4].toString());
        int minuto= TecladoException.getEntero(args[5].toString());
        Empresas empresa = empresasJpaController.getEmpresasByNombre(args[6].toString());
        Anteproyecto antProy = anteproyectoJpaController.getAnteproyectoByTitulo(args[7].toString());
        for (Estados estado : estados) {
            if(estado.getNombreEstado().equals(args[9].toString())){
                estadoActual = estado;
            }
        }

        
        if (grupo.length() > 0) {
            if (fechaRecepcion != null) {
                if (empresa != null) {
                    if (antProy != null) {
                        proyecto = new Proyectos();
                        proyecto.setAnteproyectoId(antProy);
                        proyecto.setEmpresasId(empresa);
                        proyecto.setEstadosId(estadoActual);
                        proyecto.setFechaRecepcion(fechaRecepcion);
                        proyecto.setGrupo(grupo);
                        fechaRecepcion.setHours(hora);
                        fechaRecepcion.setMinutes(minuto);
                        proyecto.setHoraRecepcion(fechaRecepcion);
                        if(args[8].toString().length() > 0){
                            proyecto.setNotaDefinitiva(TecladoException.getDouble(args[8].toString()));
                        }
                        proyecto.setPeriodo(periodo);
                    }
                } else {
                    mensajeAlerta = "Se debe especificar la empresa u organización beneficiaria";
                }
            } else {
                mensajeAlerta = "La fecha de recepción es obligatoria";
            }
        } else {
            mensajeAlerta = "El grupo es obligatorio";
        }
        if (proyecto == null) {
            JOptionPane.showMessageDialog(null, mensajeAlerta);
        }

        return proyecto;
    }

     /**
     * El parametro args recibe 
     *      args[0] = jYearChooser2.getYear();
            args[1] = jComboBoxPeriodo.getSelectedItem().toString();
            args[2] = jTextFieldGrupo.getText();
            args[3] = fechaRecepcionDateChooser.getDate();
            args[4] = jTextFieldHora1.getText();
            args[5] = jTextFieldMinuto1.getText();
            args[6] = jTextFieldEmpresa.getText();
            args[7] = jTextFieldAnteproyecto.getText();
            args[8] = jTextFieldNota.getText();
            args[9] = jComboBoxEstado.getSelectedItem().toString();
     * @param args
     * @param estados
     * @return
     */
    public Proyectos modificarObjetoProyecto(Object args[],List<Estados> estados,Proyectos proyecto) {
        Estados estadoActual = null;
        String periodo = args[0] +"-"+args[1];
        String grupo = args[2].toString();
        Date fechaRecepcion = (Date) args[3];
        int hora= TecladoException.getEntero(args[4].toString());
        int minuto= TecladoException.getEntero(args[5].toString());
        Empresas empresa = empresasJpaController.getEmpresasByNombre(args[6].toString());
        Anteproyecto antProy = null;
        for (Estados estado : estados) {
            if(estado.getNombreEstado().equals(args[9].toString())){
                estadoActual = estado;
            }
        }

        
        if (grupo.length() > 0) {
            if (fechaRecepcion != null) {
                if (empresa != null) {
                    if (antProy != null) {
                        proyecto.setAnteproyectoId(antProy);
                        proyecto.setDocentesIdentificacion(null);
                        
                        proyecto.setEmpresasId(empresa);
                        proyecto.setEstadosId(estadoActual);
                        proyecto.setFechaRecepcion(fechaRecepcion);
                        proyecto.setGrupo(grupo);
                        fechaRecepcion.setHours(hora);
                        fechaRecepcion.setMinutes(minuto);
                        proyecto.setHoraRecepcion(fechaRecepcion);
                        if(args[8].toString().length() > 0){
                            proyecto.setNotaDefinitiva(TecladoException.getDouble(args[8].toString()));
                        }
                        proyecto.setHorariosId(null);
                        proyecto.setPeriodo(periodo);
                    }
                } else {
                    mensajeAlerta = "Se debe especificar la empresa u organización beneficiaria";
                }
            } else {
                mensajeAlerta = "La fecha de recepción es obligatoria";
            }
        } else {
            mensajeAlerta = "El grupo es obligatorio";
        }
        if (proyecto == null) {
            JOptionPane.showMessageDialog(null, mensajeAlerta);
        }

        return proyecto;
    }

}
