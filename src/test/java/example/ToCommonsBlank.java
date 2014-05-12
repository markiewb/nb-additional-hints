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
public class ToCommonsBlank {

    public static void main(String[] args) {
        String s = "";
        boolean a = s == null || s.trim().length() == 0;
        boolean b = s == null || s.trim().isEmpty();
        boolean c = s != null && s.trim().length() == 0;
        boolean d = s != null && s.trim().isEmpty();
    }
}
