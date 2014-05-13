/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author LuigyMendez
 */
public class Formateador {

    /**
     * Este metodo recibe una instancia de un date y lo convierte en un
     * calendar.
     *
     * @param date
     * @return
     */
    public static Calendar dateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * Este metodo parsea un objeto calendar a un tipo de date con el formato
     * para ser guardado en mysql yyyy-MM-dd hh:mm:ss
     *
     * @param calendar
     * @return
     */
    public static Date calendarToDate(Calendar calendar) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            date = format.parse(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1)
                    + "-" + calendar.get(Calendar.DATE) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)
                    + ":" + calendar.get(Calendar.SECOND));

        } catch (ParseException ex) {
            System.err.println("Error de parseo calendarToDate");
        }
        return date;
    }

    /**
     * Este metodo retorna los dias que tiene un mes especifico
     *
     * @param mes
     * @param anio
     * @return
     */
    public static int diasDelMes(int mes, int anio) {

        switch (mes) {
            case 0:  // Enero
            case 2:  // Marzo
            case 4:  // Mayo
            case 6:  // Julio
            case 7:  // Agosto
            case 9:  // Octubre
            case 11: // Diciembre
                return 31;
            case 3:  // Abril
            case 5:  // Junio
            case 8:  // Septiembre
            case 10: // Noviembre
                return 30;
            case 1:  // Febrero
                if (((anio % 100 == 0) && (anio % 400 == 0))
                        || ((anio % 100 != 0) && (anio % 4 == 0))) {
                    return 29;  // Año Bisiesto
                } else {
                    return 28;
                }
            default:
                return -1;
        }
    }

    /**
     * Este metodo crea una fecha tipo Date a partir de dos strings que recibe
     * fecha en formato dd/MM/aaaa hora en formato : hh:mm:ss
     *
     * @param StringFecha
     * @param StringHora
     * @return
     */
    public static Date crearFechaDate(String StringFecha, String StringHora) {
        Date date = null;
        String[] splitFecha = StringFecha.split("/");
        String[] splitHora = StringHora.split(":");

        if (splitFecha.length == 3 || splitHora.length == 3) {
            int anio, mes, dia, hora, minuto, seg;
            dia = TecladoException.getEntero(splitFecha[0]);
            mes = TecladoException.getEntero(splitFecha[1]);
            anio = TecladoException.getEntero(splitFecha[2]);

            hora = TecladoException.getEntero(splitHora[0]);
            minuto = TecladoException.getEntero(splitHora[1]);
            seg = TecladoException.getEntero(splitHora[2]);

            if (dia <= diasDelMes(mes, anio)) {

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    date = format.parse(anio + "-" + mes + "-" + dia + " " + hora + ":" + minuto
                            + ":" + seg);

                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "La fecha ingresada no tiene el formato correcto (dd/MM/aaaa)");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Comprueba la fecha que esta ingresando..");
            }
        }
        return date;
    }
}
