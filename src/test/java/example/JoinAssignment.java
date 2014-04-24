/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import java.util.Date;

/**
 *
 * @author markiewb
 */
public class JoinAssignment {

    public static void main(String[] args) {
        int foo;
        foo = 42;

        {
            //comment before bar
            int bar;
        //comment after bar
            //comment before number
            bar = 42;
            //comment after number
        }

        {
            //comment before bar
            int bar;
            //comment after bar
            Date date = new Date();
            //comment before number
            bar = 42;
            //comment after number
        }

    }

}
