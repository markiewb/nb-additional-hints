/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.structure;

import de.markiewb.netbeans.plugins.hints.tonumber.ToNumber;
import java.io.Serializable;
import java.math.BigDecimal;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

/**
 *
 * @author markiewb
 */
public class ChangeTypeOfVariableTest {

    public ChangeTypeOfVariableTest() {
    }

    @Test
    public void testNumberToSerializable() throws Exception {
        final String hintLabel = "3:10-3:10:hint:" + de.markiewb.netbeans.plugins.hints.structure.Bundle.DN_ChangeTypeOfVariable();
        final String serializeableMessage = de.markiewb.netbeans.plugins.hints.structure.Bundle.ERR_ChangeTypeOfVariable(Serializable.class.getCanonicalName());
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Nu|mber a = null;\n"
                        + "    }\n"
                        + "}\n")
                .run(ChangeTypeOfVariable.class)
                .assertWarnings(hintLabel)
                .findWarning(hintLabel)
                .assertFixes(serializeableMessage)
                .applyFix(serializeableMessage)
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import java.io.Serializable;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Serializable a = null;\n"
                        + "    }\n"
                        + "}\n");
    }

    @Ignore
    @Test
    public void testIntegerToSerializeable() throws Exception {
        final String hintLabel = "3:10-3:10:hint:" + de.markiewb.netbeans.plugins.hints.structure.Bundle.DN_ChangeTypeOfVariable();
        final String numberMessage = de.markiewb.netbeans.plugins.hints.structure.Bundle.ERR_ChangeTypeOfVariable(Number.class.getCanonicalName());
        final String serializeableMessage = de.markiewb.netbeans.plugins.hints.structure.Bundle.ERR_ChangeTypeOfVariable(Serializable.class.getCanonicalName());
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        In|teger a = null;\n"
                        + "    }\n"
                        + "}\n")
                .run(ChangeTypeOfVariable.class)
                .assertWarnings(hintLabel)
                .findWarning(hintLabel)
                .assertFixes(serializeableMessage, numberMessage)
                .applyFix(serializeableMessage)
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import java.io.Serializable;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Serializable a = null;\n"
                        + "    }\n"
                        + "}\n");

    }
    
    @Test
    public void testArrayListTo_Unsupported() throws Exception {
        final String hintLabel = "4:13-4:13:hint:" + de.markiewb.netbeans.plugins.hints.structure.Bundle.DN_ChangeTypeOfVariable();
        final String numberMessage = de.markiewb.netbeans.plugins.hints.structure.Bundle.ERR_ChangeTypeOfVariable(Number.class.getCanonicalName());
        final String serializeableMessage = de.markiewb.netbeans.plugins.hints.structure.Bundle.ERR_ChangeTypeOfVariable(Serializable.class.getCanonicalName());
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "import java.util.ArrayList;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Array|List a = null;\n"
                        + "    }\n"
                        + "}\n")
                .run(ChangeTypeOfVariable.class)
                .assertWarnings();
//                .findWarning(hintLabel)
//                .assertFixes(serializeableMessage, numberMessage)
//                .applyFix(serializeableMessage)
//                .assertCompilable()
//                .assertOutput("package test;\n"
//                        + "import java.util.List;\n"
//                        + "public class Test {\n"
//                        + "    public static void main(String[] args) {\n"
//                        + "        List a = null;\n"
//                        + "    }\n"
//                        + "}\n");

    }

}
