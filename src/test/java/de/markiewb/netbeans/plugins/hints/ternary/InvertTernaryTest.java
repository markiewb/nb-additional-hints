/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.ternary;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.netbeans.modules.java.hints.test.api.HintTest;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.java.hints.HintContext;
import org.netbeans.spi.java.hints.TriggerPattern;

/**
 *
 * @author markiewb
 */
public class InvertTernaryTest {
//        "($var1 != null) ? $a : $b"),
//        "($var2 == null) ? $a : $b"),
//        "($var3 > $c) ? $a : $b"),
//        "($var4 < $c) ? $a : $b"),
//        "($var5 >= $c) ? $a : $b"),
//        "($var6 =< $c) ? $a : $b"),

    @Test
    public void testNotEquals() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Double val = null;\n"
                        + "        int a = (v|al != null) ? 1 : 0; \n"
                        + "    }\n"
                        + "}\n")
                .run(InvertTernary.class)
                .findWarning("4:18-4:18:hint:" + Bundle.ERR_InvertTernary())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Double val = null;\n"
                        + "        int a = (val == null) ? 0 : 1; \n"
                        + "    }\n"
                        + "}\n");

    }

    @Test
    public void testEquals() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Double val = null;\n"
                        + "        int a = (v|al == null) ? 1 : 0; \n"
                        + "    }\n"
                        + "}\n")
                .run(InvertTernary.class)
                .findWarning("4:18-4:18:hint:" + Bundle.ERR_InvertTernary())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Double val = null;\n"
                        + "        int a = (val != null) ? 0 : 1; \n"
                        + "    }\n"
                        + "}\n");

    }

    @Test
    public void testGreater() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Double val = null;\n"
                        + "        int a = (v|al > 42.0) ? 1 : 0; \n"
                        + "    }\n"
                        + "}\n")
                .run(InvertTernary.class)
                .findWarning("4:18-4:18:hint:" + Bundle.ERR_InvertTernary())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Double val = null;\n"
                        + "        int a = (val <= 42.0) ? 0 : 1; \n"
                        + "    }\n"
                        + "}\n");

    }

    @Test
    public void testLower() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Double val = null;\n"
                        + "        int a = (v|al < 42.0) ? 1 : 0; \n"
                        + "    }\n"
                        + "}\n")
                .run(InvertTernary.class)
                .findWarning("4:18-4:18:hint:" + Bundle.ERR_InvertTernary())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Double val = null;\n"
                        + "        int a = (val >= 42.0) ? 0 : 1; \n"
                        + "    }\n"
                        + "}\n");

    }

    @Test
    public void testGreaterEquals() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Double val = null;\n"
                        + "        int a = (v|al >= 42.0) ? 1 : 0; \n"
                        + "    }\n"
                        + "}\n")
                .run(InvertTernary.class)
                .findWarning("4:18-4:18:hint:" + Bundle.ERR_InvertTernary())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Double val = null;\n"
                        + "        int a = (val < 42.0) ? 0 : 1; \n"
                        + "    }\n"
                        + "}\n");

    }

    @Test
    public void testLowerEquals() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Double val = null;\n"
                        + "        int a = (v|al <= 42.0) ? 1 : 0; \n"
                        + "    }\n"
                        + "}\n")
                .run(InvertTernary.class)
                .findWarning("4:18-4:18:hint:" + Bundle.ERR_InvertTernary())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Double val = null;\n"
                        + "        int a = (val > 42.0) ? 0 : 1; \n"
                        + "    }\n"
                        + "}\n");

    }

}
