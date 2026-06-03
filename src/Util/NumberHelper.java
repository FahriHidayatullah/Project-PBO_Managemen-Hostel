/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberHelper {

    private static final NumberFormat IDR = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public static String format(double amount) {
        return IDR.format(amount);
    }
}
