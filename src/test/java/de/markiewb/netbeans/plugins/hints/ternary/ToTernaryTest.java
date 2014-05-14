/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.ternary;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

/**
 *
 * @author markiewb
 */
public class ToTernaryTest {

    @Test
    public void testToTernaryReturn() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static int main(String[] args) {\n"
                        + "        if (tr|ue){return 1;}else{return 0;}\n"
                        + "    }\n"
                        + "}\n")
                .run(ToTernary.class)
                .findWarning("3:14-3:14:hint:" + Bundle.ERR_ToTernaryReturn())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static int main(String[] args) {\n"
                        + "        return (true) ? 1 : 0;\n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testToTernaryAssign() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int s;\n"
                        + "        if (t|rue){s = 1;}else{s = 0;}\n"
                        + "    }\n"
                        + "}\n")
                .run(ToTernary.class)
                .findWarning("4:13-4:13:hint:" + Bundle.ERR_ToTernaryAssign())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int s;\n"
                        + "        s = (true) ? 1 : 0;\n"
                        + "    }\n"
                        + "}\n");

    }

}
