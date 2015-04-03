/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.optional;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

/**
 *
 * @author markiewb
 */
public class AssingNullToOptionalTest {

    @Test
    public void testCaseAssignmentToExisting() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "import java.util.Optional;\n"
                        + "public class Test {\n"
                        + "    public void method() {\n"
                        + "        Optional o;\n"
                        + "        o = null;\n"
                        + "    }\n"
                        + "}")
                .sourceLevel("1.8")
                .run(AssignNullToOptional.class)
                .findWarning("5:8-5:16:error:" + de.markiewb.netbeans.plugins.hints.optional.Bundle.ERR_AssignNull())
                .applyFix()
                .assertOutput("package test;\n"
                        + "import java.util.Optional;\n"
                        + "public class Test {\n"
                        + "    public void method() {\n"
                        + "        Optional o;\n"
                        + "        o = Optional.empty();\n"
                        + "    }\n"
                        + "}");

    }
    @Test
    public void testCaseAssignmentToNewVariable() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "import java.util.Optional;\n"
                        + "public class Test {\n"
                        + "    public void method() {\n"
                        + "        Optional o = null;\n"
                        + "    }\n"
                        + "}")
                .sourceLevel("1.8")
                .run(AssignNullToOptional.class)
                .findWarning("4:17-4:18:error:" + de.markiewb.netbeans.plugins.hints.optional.Bundle.ERR_AssignNull())
                .applyFix()
                .assertOutput("package test;\n"
                        + "import java.util.Optional;\n"
                        + "public class Test {\n"
                        + "    public void method() {\n"
                        + "        Optional o = Optional.empty();\n"
                        + "    }\n"
                        + "}");

    }

    /**
     * https://github.com/markiewb/nb-additional-hints/issues/57
     * @throws Exception 
     */
    @Test
    public void testAssigmentFalsePositive() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public void method() {\n"
                        + "        String o = null;\n"
                        + "    }\n"
                        + "}")
                .sourceLevel("1.8")
                .run(AssignNullToOptional.class)
                .assertWarnings();
    }
    /**
     * https://github.com/markiewb/nb-additional-hints/issues/57
     * @throws Exception 
     */
    @Test
    public void testAssigmentFalsePositive2() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public void method() {\n"
                        + "        java.util.List<String> o = null;\n"
                        + "    }\n"
                        + "}")
                .sourceLevel("1.8")
                .run(AssignNullToOptional.class)
                .assertWarnings();
    }
    /**
     * https://github.com/markiewb/nb-additional-hints/issues/57
     * @throws Exception 
     */
    @Test
    public void testAssigmentFalsePositive3() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public void method() {\n"
                        + "        String o;\n"
                        + "        o = null;\n"
                        + "    }\n"
                        + "}")
                .sourceLevel("1.8")
                .run(AssignNullToOptional.class)
                .assertWarnings();
    }
}
