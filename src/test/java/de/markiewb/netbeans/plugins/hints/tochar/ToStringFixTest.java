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
    public void testConvert() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        char a='a';\n"
                        + "    }\n"
                        + "}\n")
                .run(UseBigDecimalConstantsFixONE.class)
                .findWarning("3:31-3:60:hint:" + Bundle.ERR_ToStringFix())
                .applyFix()
//                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        char a=\"a\";\n"
                        + "    }\n"
                        + "}\n");

    }

}
