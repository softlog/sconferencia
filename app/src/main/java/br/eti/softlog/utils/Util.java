package br.eti.softlog.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrador on 2018/03/03.
 */

public final class Util {

    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    public static String getDateFormat(Date data) {
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL, new Locale("pt", "BR"));
        String dataExtenso = formatador.format(data);
        return dataExtenso;
    }

    public static String getDateFormat(String data) throws ParseException {

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date datedata = formato.parse(data);
        formato.applyPattern("dd/MM/yyyy HH:mm");
        String dataFormatada = formato.format(data);

        return dataFormatada;
    }

    public static  String getDateFormatYMD(Date data) {

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formato.applyPattern("yyyy-MM-dd");

        String dataFormatada = formato.format(data);
        return dataFormatada;
    }

    public static  String getDateTimeFormatYMD(Date data) {

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formato.applyPattern("yyyy-MM-dd HH:mm:ss");

        String dataFormatada = formato.format(data);
        return dataFormatada;
    }

}

