/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

/**
 *
 * @author markiewb
 */
public class ToTernary {

    static int s;

    public static int main(String[] args) {
        boolean b=true;
        int b1 = (b) ? 1 : 0;  //
        int b2 = (!b) ? 1 : 0;
        int b3 = (true) ? 1 : 0;
        int b4 = (false) ? 1 : 0;
        int b5 = (!true) ? 1 : 0;
        int b6 = (!false) ? 1 : 0;
        return (true) ? 1 : 0;
    }
}
