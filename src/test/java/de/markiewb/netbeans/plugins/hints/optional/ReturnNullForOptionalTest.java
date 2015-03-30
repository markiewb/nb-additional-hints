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
public class ReturnNullForOptionalTest {

    @Test
    public void testCaseSimpleCase_FullQualifiedName() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public java.util.Optional method() {\n"
                        + "        return null;\n"
                        + "    }\n"
                        + "}")
                .sourceLevel("1.8")
                .run(ReturnNullForOptional.class)
                .findWarning("3:15-3:19:error:" + de.markiewb.netbeans.plugins.hints.optional.Bundle.ERR_ReturnNullForOptional())
                .applyFix()
                .assertOutput("package test;\n"
                        + "import java.util.Optional;\n"
                        + "public class Test {\n"
                        + "    public java.util.Optional method() {\n"
                        + "        return Optional.empty();\n"
                        + "    }\n"
                        + "}");

    }

    @Test
    public void testCaseSimpleCase() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "import java.util.Optional;\n"
                        + "public class Test {\n"
                        + "    public Optional method() {\n"
                        + "        return null;\n"
                        + "    }\n"
                        + "}")
                .sourceLevel("1.8")
                .run(ReturnNullForOptional.class)
                .findWarning("4:15-4:19:error:" + de.markiewb.netbeans.plugins.hints.optional.Bundle.ERR_ReturnNullForOptional())
                .applyFix()
                .assertOutput("package test;\n"
                        + "import java.util.Optional;\n"
                        + "public class Test {\n"
                        + "    public Optional method() {\n"
                        + "        return Optional.empty();\n"
                        + "    }\n"
                        + "}");

    }

    @Test
    public void testCaseSimpleCase_Nested() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "import java.util.Optional;\n"
                        + "public class Test {\n"
                        + "    public Optional method() {\n"
                        + "        {{return null;}}\n"
                        + "    }\n"
                        + "}")
                .sourceLevel("1.8")
                .run(ReturnNullForOptional.class)
                .findWarning("4:17-4:21:error:" + de.markiewb.netbeans.plugins.hints.optional.Bundle.ERR_ReturnNullForOptional())
                .applyFix()
                .assertOutput("package test;\n"
                        + "import java.util.Optional;\n"
                        + "public class Test {\n"
                        + "    public Optional method() {\n"
                        + "        {{return Optional.empty();}}\n"
                        + "    }\n"
                        + "}");

    }

}
