/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;
public class ValidationHelper {
    public static boolean isEmpty(String s) { return s==null || s.trim().isEmpty(); }
    public static boolean isNumeric(String s) { try { Double.parseDouble(s); return true; } catch(Exception e) { return false; } }
}