/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.optional;

import org.junit.Ignore;
import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

/**
 *
 * @author markiewb
 */
public class AccessOptionalTest {

    @Ignore("Test does not work because we are still compiling with 1.7")
    @Test
    public void testCaseA() throws Exception {
        HintTest.create()
//                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static int main(String[] args) {\n"
                        + "        java.util.Optional<String> o = null;\n"
                        + "        if (null==o){}\n"
                        + "    }\n"
                        + "}\n")
                .sourceLevel("1.8")
                .run(AccessOptional.class)
                .findWarning("3:14-3:14:hint:" + de.markiewb.netbeans.plugins.hints.optional.Bundle.ERR_AccessOptional())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static int main(String[] args) {\n"
                        + "        java.util.Optional<String> o = null;\n"
                        + "        if (!o.isPresent){}\n"
                        + "    }\n"
                        + "}\n");

    }
    
}
