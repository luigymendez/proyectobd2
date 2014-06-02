/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.objects;

import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import modelo.Horarios;
import modelo.Proyectos;
import utilerias.Formateador;
import utilerias.TecladoException;

/**
 *
 * @author LuigyMendez
 */
public class ControlHorariosProyecto {

    String mensajeAlerta;

    /**
     * El parametro args recibe args[0] = dia args[1] = fechainicio args[2] =
     * fecha fin args[3] = hora inicio args[4] = minuto inicio args[5] = hora
     * fin args[6] = minuto fin
     *
     * @param args
     * @return
     */
    public Horarios crearObjetoHorario(Object args[]) {
        Horarios horario = null;
        String dia = args[0].toString();
        Date fechaInicio = (Date) args[1];
        Date fechaFin = (Date) args[2];

        int horaInicio = TecladoException.getEntero(args[3].toString());
        int minutoInicio = TecladoException.getEntero(args[4].toString());
        int horaFin = TecladoException.getEntero(args[5].toString());
        int minutoFin = TecladoException.getEntero(args[6].toString());

        if (fechaInicio != null) {
            if (fechaFin != null) {
                if (horaInicio >= 0 && horaInicio < 24 && horaFin >= 0 && horaFin < 24 && minutoInicio >= 0 && minutoInicio < 60 && minutoFin >= 0 && minutoFin < 60) {
                    horario = new Horarios();
                    horario.setDia(dia);
                    horario.setFechaInicioAsesoria(fechaInicio);
                    horario.setFechaTerminacionAsesoria(fechaFin);
                    Calendar calInicio = Calendar.getInstance();
                    calInicio.set(Calendar.HOUR_OF_DAY, horaInicio);
                    calInicio.set(Calendar.MINUTE, minutoInicio);
                    horario.setHoraInicio(Formateador.calendarToDate(calInicio));
                    Calendar calFin = Calendar.getInstance();
                    calFin.set(Calendar.HOUR_OF_DAY, horaFin);
                    calFin.set(Calendar.MINUTE, minutoFin);
                    horario.setHoraFinalizacion(Formateador.calendarToDate(calInicio));

                } else {
                    mensajeAlerta = "La hora esta en formato de 24 horas. Desde 00:00 a 23:59";
                }
            } else {
                mensajeAlerta = "La fecha fin es obligatoria";
            }
        } else {
            mensajeAlerta = "La fecha de inicio es obligatoria";
        }
        if (horario == null) {
            JOptionPane.showMessageDialog(null, mensajeAlerta);
        }

        return horario;
    }

    /**
     * El parametro args recibe args[0] = dia args[1] = fechainicio args[2] =
     * fecha fin args[3] = hora inicio args[4] = minuto inicio args[5] = hora
     * fin args[6] = minuto fin
     *
     * @param args
     * @param horario
     * @return
     */
    public Horarios modificarObjetoHorario(Object args[], Horarios horario) {
        String dia = args[0].toString();
        Date fechaInicio = (Date) args[1];
        Date fechaFin = (Date) args[2];

        int horaInicio = TecladoException.getEntero(args[3].toString());
        int minutoInicio = TecladoException.getEntero(args[4].toString());
        int horaFin = TecladoException.getEntero(args[5].toString());
        int minutoFin = TecladoException.getEntero(args[6].toString());

        if (fechaInicio != null) {
            if (fechaFin != null) {
                if (horaInicio >= 0 && horaInicio < 24 && horaFin >= 0 && horaFin < 24 && minutoInicio >= 0 && minutoInicio < 60 && minutoFin >= 0 && minutoFin < 60) {

                    horario.setDia(dia);
                    horario.setFechaInicioAsesoria(fechaInicio);
                    horario.setFechaTerminacionAsesoria(fechaFin);
                    Calendar calInicio = Calendar.getInstance();
                    calInicio.set(Calendar.HOUR_OF_DAY, horaInicio);
                    calInicio.set(Calendar.MINUTE, minutoInicio);
                    horario.setHoraInicio(Formateador.calendarToDate(calInicio));
                    Calendar calFin = Calendar.getInstance();
                    calFin.set(Calendar.HOUR_OF_DAY, horaFin);
                    calFin.set(Calendar.MINUTE, minutoFin);
                    horario.setHoraFinalizacion(Formateador.calendarToDate(calInicio));

                } else {
                    mensajeAlerta = "La hora esta en formato de 24 horas. Desde 00:00 a 23:59";
                }
            } else {
                mensajeAlerta = "La fecha fin es obligatoria";
            }
        } else {
            mensajeAlerta = "La fecha de inicio es obligatoria";
        }
        if (horario == null) {
            JOptionPane.showMessageDialog(null, mensajeAlerta);
        }

        return horario;
    }
}
