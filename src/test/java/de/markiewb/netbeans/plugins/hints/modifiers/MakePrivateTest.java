/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.modifiers;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

public class MakePrivateTest {

    @Test
    public void testFixWorking_Public_Method() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakePrivate.class)
                .findWarning("2:4-2:17:hint:" + Bundle.ERR_MakePrivate())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    private static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Public_Field() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public String s = null;\n"
                        + "}\n")
                .run(MakePrivate.class)
                .findWarning("2:4-2:10:hint:" + Bundle.ERR_MakePrivate())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    private String s = null;\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Public_InnerClass() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public class Inner {}\n"
                        + "}\n")
                .run(MakePrivate.class)
                .findWarning("2:4-2:10:hint:" + Bundle.ERR_MakePrivate())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    private class Inner {}\n"
                        + "}\n");
    }
}
