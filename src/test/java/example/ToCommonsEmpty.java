/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author markiewb
 */
public class ToCommonsEmpty {

    public static void main(String[] args) {
        String s = "";

        boolean a = s == null || s.length() == 0;
        boolean b = s == null || s.isEmpty();
        boolean c = s != null && s.isEmpty();
        boolean d = s != null && s.length() == 0;
    }
    
    public static void main2(String[] args) {
        String s = "";

        boolean a = s == null || s.length() == 0;
        boolean b = s == null || s.isEmpty();
        boolean c = s != null && s.isEmpty();
        boolean d = s != null && s.length() == 0;
    }

}
