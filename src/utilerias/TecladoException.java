package utilerias;

import javax.swing.JOptionPane;

/**
 *
 * @author luis mendez
 */
public class TecladoException {

    /**
     * Valida que el numero a parsear sea un entero, si se produce la excepcion
     * retorna un -1
     * @param cadena
     * @return
     */
    
    public static int getEntero(String cadena) {
        int num;
        try {
            num = Integer.parseInt(cadena);
        } catch (NumberFormatException e) {
            num = -1;
        }
        return num;
    }

    public static long getLong(String cadena) {
        long num;
        try {
            num = Long.parseLong(cadena);
        } catch (NumberFormatException e) {
            num = -1;
        } catch (StringIndexOutOfBoundsException e) {
            num = -1;
        }
        return num;
    }

    public static double getDouble(String cadena) {
        double num;
        try {
            num = Double.parseDouble((cadena));
        } catch (NumberFormatException e) {
            num = -1;
        } catch (StringIndexOutOfBoundsException e) {
            num = -1;
        }
        return num;
    }

    public static float getFloat(String cadena) {
        float num;
        try {
            num = Float.parseFloat((cadena));
        } catch (NumberFormatException e) {
            num = -1;
        } catch (StringIndexOutOfBoundsException e) {
            num = -1;
        }
        return num;
    }

    public static char getChar(String cadena) {
        char a;
        a = '0';
        try {
            a = (cadena).charAt(0);
        } catch (StringIndexOutOfBoundsException e) {
        }
        return a;
    }

    /***
     * Este metodo valida una contraseña que sea mayor a 8 caracteres , que no tenga
     * caracteres especiales y que contenga almenos 2 numeros, el metodo retorna
     * una cadena vacía si la contraseña es válida !
     * @param contra
     * @return 
     */
    public static String validarContrasenia(String contra) {
        //La contraseña debe ser mayor a 8 caracteres y contener almenos 2 numeros 
        char[] vectorChar = contra.toCharArray();
        int contNum = 0;
        if (vectorChar.length >= 8) {
            for (int i = 0; i < vectorChar.length; i++) {
                if (vectorChar[i] >= 48 && vectorChar[i] <= 57) { // Es un digito
                    contNum++;
                } else if (vectorChar[i] >= 65 && vectorChar[i] <= 90) {
                } else if (vectorChar[i] >= 97 && vectorChar[i] <= 122) {
                } else {
                    return "La contraseña no puede tener caracteres especiales";
                }
            }
            if(contNum<2){
                return "La contraseña debe  tener almenos 2 digitos";
            }
        } else {
           return "La contraseña debe ser mayor a 8 caracteres";
        }
        return "";
    }
}
