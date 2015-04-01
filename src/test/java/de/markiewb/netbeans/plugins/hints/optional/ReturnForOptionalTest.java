/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.optional;

import java.util.Optional;
import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

/**
 *
 * @author markiewb
 */
public class ReturnForOptionalTest {

    @Test
    public void testCaseSimpleCase_FullQualifiedName_null() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public java.util.Optional method() {\n"
                        + "        return null;\n"
                        + "    }\n"
                        + "}")
                .sourceLevel("1.8")
                .run(ReturnForOptional.class)
                .findWarning("3:8-3:14:error:" + de.markiewb.netbeans.plugins.hints.optional.Bundle.ERR_ReturnForOptionalEmpty())
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
    public void testCaseSimpleCase_Nested_null() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "import java.util.Optional;\n"
                        + "public class Test {\n"
                        + "    public Optional method() {\n"
                        + "        {{return null;}}\n"
                        + "    }\n"
                        + "}")
                .sourceLevel("1.8")
                .run(ReturnForOptional.class)
                .findWarning("4:10-4:16:error:" + de.markiewb.netbeans.plugins.hints.optional.Bundle.ERR_ReturnForOptionalEmpty())
                .applyFix()
                .assertOutput("package test;\n"
                        + "import java.util.Optional;\n"
                        + "public class Test {\n"
                        + "    public Optional method() {\n"
                        + "        {{return Optional.empty();}}\n"
                        + "    }\n"
                        + "}");

    }
    @Test
    public void testCaseSimpleCase_ignoreExistingFQNOptional() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public java.util.Optional method() {\n"
                        + "        return java.util.Optional.empty();\n"
                        + "    }\n"
                        + "}")
                .sourceLevel("1.8")
                .run(ReturnForOptional.class)
                .assertWarnings();

    }
    @Test
    public void testCaseSimpleCase_ignoreExistingOptional() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "import java.util.Optional;\n"
                        + "public class Test {\n"
                        + "    public Optional method() {\n"
                        + "        return Optional.empty();\n"
                        + "    }\n"
                        + "}")
                .sourceLevel("1.8")
                .run(ReturnForOptional.class)
                .assertWarnings();

    }

    @Test
    public void testCaseSimpleCase_ignoreExistingOptionalVariable() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public java.util.Optional method(java.util.Optional o) {\n"
                        + "        return o;\n"
                        + "    }\n"
                        + "}")
                .sourceLevel("1.8")
                .run(ReturnForOptional.class)
                .assertWarnings();

    }
    @Test
    public void testCaseSimpleCase_notNull_2_of() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "import java.util.Optional;\n"
                        + "public class Test {\n"
                        + "    public Optional method() {\n"
                        + "        return \"ABC\";\n"
                        + "    }\n"
                        + "}", false)
                .sourceLevel("1.8")
                .run(ReturnForOptional.class)
                .findWarning("4:8-4:14:error:" + de.markiewb.netbeans.plugins.hints.optional.Bundle.ERR_ReturnForOptionalNullable())
                .applyFix(Bundle.DN_ReturnForOptionalOf())
                .assertOutput("package test;\n"
                        + "import java.util.Optional;\n"
                        + "public class Test {\n"
                        + "    public Optional method() {\n"
                        + "        return Optional.of(\"ABC\");\n"
                        + "    }\n"
                        + "}");
    }
    @Test
    public void testCaseSimpleCase_notNull_2_ofNullable() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "import java.util.Optional;\n"
                        + "public class Test {\n"
                        + "    public Optional method() {\n"
                        + "        return \"ABC\";\n"
                        + "    }\n"
                        + "}", false)
                .sourceLevel("1.8")
                .run(ReturnForOptional.class)
                .findWarning("4:8-4:14:error:" + de.markiewb.netbeans.plugins.hints.optional.Bundle.ERR_ReturnForOptionalNullable())
                .applyFix(Bundle.DN_ReturnForOptionalOfNullable())
                .assertOutput("package test;\n"
                        + "import java.util.Optional;\n"
                        + "public class Test {\n"
                        + "    public Optional method() {\n"
                        + "        return Optional.ofNullable(\"ABC\");\n"
                        + "    }\n"
                        + "}");
    }

    @Test
    public void testCaseSimpleCase_null() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "import java.util.Optional;\n"
                        + "public class Test {\n"
                        + "    public Optional method() {\n"
                        + "        return null;\n"
                        + "    }\n"
                        + "}")
                .sourceLevel("1.8")
                .run(ReturnForOptional.class)
                .findWarning("4:8-4:14:error:" + de.markiewb.netbeans.plugins.hints.optional.Bundle.ERR_ReturnForOptionalEmpty())
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
    public void testCaseSimpleCase_null_nontypedMethod() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "import java.util.Optional;\n"
                        + "public class Test {\n"
                        + "    public Optional method() {\n"
                        + "        return null;\n"
                        + "    }\n"
                        + "}")
                .sourceLevel("1.8")
                .run(ReturnForOptional.class)
                .findWarning("4:8-4:14:error:" + de.markiewb.netbeans.plugins.hints.optional.Bundle.ERR_ReturnForOptionalEmpty())
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
    public void testCaseSimpleCase_null_typedMethod() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "import java.util.Optional;\n"
                        + "public class Test {\n"
                        + "    public Optional<String> method() {\n"
                        + "        return null;\n"
                        + "    }\n"
                        + "}")
                .sourceLevel("1.8")
                .run(ReturnForOptional.class)
                .findWarning("4:8-4:14:error:" + de.markiewb.netbeans.plugins.hints.optional.Bundle.ERR_ReturnForOptionalEmpty())
                .applyFix()
                .assertOutput("package test;\n"
                        + "import java.util.Optional;\n"
                        + "public class Test {\n"
                        + "    public Optional<String> method() {\n"
                        + "        return Optional.empty();\n"
                        + "    }\n"
                        + "}");

    }

}
