/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.tochar;

import de.markiewb.netbeans.plugins.hints.bigdecimal.*;
import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

public class ToStringFixTest {

    @Test
    public void testConvertToString() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String a=\"bor\"+'a|';\n"
                        + "    }\n"
                        + "}\n")
                .run(ToStringFix.class)
                .findWarning("3:25-3:25:hint:" + Bundle.ERR_ToStringFix())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String a=\"bor\"+\"a\";\n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testConvertToChar() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String a=\"bor\"+\"|a\";\n"
                        + "    }\n"
                        + "}\n")
                .run(ToStringFix.class)
                .findWarning("3:24-3:24:hint:" + Bundle.ERR_ToCharFix())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String a=\"bor\"+'a';\n"
                        + "    }\n"
                        + "}\n");

    }

}
