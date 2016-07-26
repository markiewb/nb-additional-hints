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
    private void method() {
          String a = null,b = null;
        //hints apply
        String var1;
        String suffix = null;
        var1 = (suffix == null) ? "A" : "B";
        String var2 = (suffix != null) ? "A" : "B";
        
        String var3 = suffix != null ? "A" : "B";
    }
    private void parentheses() {
        //hints apply
        String var1; var1 = ("1".equals("2")) ? "A" : "B";
        String var2; var2 = "1".equals("2") ? "A" : "B";
        String var3 = ("1".equals("2")) ? "A" : "B";
        String var4 = "1".equals("2") ? "A" : "B";
    }
}
