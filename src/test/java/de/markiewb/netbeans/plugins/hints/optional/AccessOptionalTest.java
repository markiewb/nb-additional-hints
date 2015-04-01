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

    @Test
    public void testCaseA() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.util.Optional<String> o = null;\n"
                        + "        if (null==o){}\n"
                        + "    }\n"
                        + "}\n")
                .sourceLevel("1.8")
                .run(CompareOptional.class)
                .findWarning("4:12-4:19:error:" + de.markiewb.netbeans.plugins.hints.optional.Bundle.ERR_AccessOptional())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.util.Optional<String> o = null;\n"
                        + "        if (!o.isPresent()){}\n"
                        + "    }\n"
                        + "}\n");

    }

    @Test
    public void testCaseB() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.util.Optional<String> o = null;\n"
                        + "        if (o==null){}\n"
                        + "    }\n"
                        + "}\n")
                .sourceLevel("1.8")
                .run(CompareOptional.class)
                .findWarning("4:12-4:19:error:" + de.markiewb.netbeans.plugins.hints.optional.Bundle.ERR_AccessOptional())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.util.Optional<String> o = null;\n"
                        + "        if (!o.isPresent()){}\n"
                        + "    }\n"
                        + "}\n");

    }

    @Test
    public void testCaseC() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.util.Optional<String> o = null;\n"
                        + "        if (null!=o){}\n"
                        + "    }\n"
                        + "}\n")
                .sourceLevel("1.8")
                .run(CompareOptional.class)
                .findWarning("4:12-4:19:error:" + de.markiewb.netbeans.plugins.hints.optional.Bundle.ERR_AccessOptional())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.util.Optional<String> o = null;\n"
                        + "        if (o.isPresent()){}\n"
                        + "    }\n"
                        + "}\n");

    }

    @Test
    public void testCaseD() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.util.Optional<String> o = null;\n"
                        + "        if (o!=null){}\n"
                        + "    }\n"
                        + "}\n")
                .sourceLevel("1.8")
                .run(CompareOptional.class)
                .findWarning("4:12-4:19:error:" + de.markiewb.netbeans.plugins.hints.optional.Bundle.ERR_AccessOptional())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.util.Optional<String> o = null;\n"
                        + "        if (o.isPresent()){}\n"
                        + "    }\n"
                        + "}\n");

    }

}
