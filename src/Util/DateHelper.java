/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.text.SimpleDateFormat;
import java.sql.Date;

public class DateHelper {

    public static String format(Date d) {
        return d == null ? "-" : new SimpleDateFormat("dd MMM yyyy").format(d);
    }

    public static Date parse(String s) {
        try {
            return new Date(new SimpleDateFormat("yyyy-MM-dd").parse(s).getTime());
        } catch (Exception e) {
            return null;
        }
    }

    public static int daysBetween(Date in, Date out) {
        if (in == null || out == null) {
            return 0;
        }
        return (int) ((out.getTime() - in.getTime()) / 86400000);
    }
}
