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
//        @TriggerPattern(value = "($var1 != $c) ? $a : $b"),
//        @TriggerPattern(value = "($var2 == $c) ? $a : $b"),
//        @TriggerPattern(value = "($var3 > $c) ? $a : $b"),
//        @TriggerPattern(value = "($var4 < $c) ? $a : $b"),
//        @TriggerPattern(value = "($var5 >= $c) ? $a : $b"),
//        @TriggerPattern(value = "($var6 <= $c) ? $a : $b"),
//        @TriggerPattern(value = "(!$var7) ? $a : $b"),
//        @TriggerPattern(value = "($var8) ? $a : $b"),

    @Test
    public void testNotEquals() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Double val = null;\n"
                        + "        int a = (v|al != 9.0) ? 1 : 0; \n"
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
                        + "        int a = (val == 9.0) ? 0 : 1; \n"
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
                        + "        int a = (v|al == 9.0) ? 1 : 0; \n"
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
                        + "        int a = (val != 9.0) ? 0 : 1; \n"
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
    
    @Test
    public void testTrueVariable() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        boolean b = true;\n"
                        + "        int a = (b|) ? 1 : 0; \n"
                        + "    }\n"
                        + "}\n")
                .run(InvertTernary.class)
                .findWarning("4:18-4:18:hint:" + Bundle.ERR_InvertTernary())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        boolean b = true;\n"
                        + "        int a = (!b) ? 0 : 1; \n"
                        + "    }\n"
                        + "}\n");

    }
    
    @Test
    public void testTrueLiteral() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int a = (tru|e) ? 1 : 0; \n"
                        + "    }\n"
                        + "}\n")
                .run(InvertTernary.class)
                .findWarning("3:20-3:20:hint:" + Bundle.ERR_InvertTernary())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int a = (false) ? 0 : 1; \n"
                        + "    }\n"
                        + "}\n");

    }
    
    @Test
    public void testTrueLiteralNot() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int a = (!tru|e) ? 1 : 0; \n"
                        + "    }\n"
                        + "}\n")
                .run(InvertTernary.class)
                .findWarning("3:21-3:21:hint:" + Bundle.ERR_InvertTernary())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int a = (true) ? 0 : 1; \n"
                        + "    }\n"
                        + "}\n");

    }
    
    @Test
    public void testFalseVariable() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        boolean b = true;\n"
                        + "        int a = (!|b) ? 1 : 0; \n"
                        + "    }\n"
                        + "}\n")
                .run(InvertTernary.class)
                .findWarning("4:18-4:18:hint:" + Bundle.ERR_InvertTernary())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        boolean b = true;\n"
                        + "        int a = (b) ? 0 : 1; \n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testFalseLiteral() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int a = (fa|lse) ? 1 : 0; \n"
                        + "    }\n"
                        + "}\n")
                .run(InvertTernary.class)
                .findWarning("3:19-3:19:hint:" + Bundle.ERR_InvertTernary())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int a = (true) ? 0 : 1; \n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testFalseLiteralNot() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int a = (!fa|lse) ? 1 : 0; \n"
                        + "    }\n"
                        + "}\n")
                .run(InvertTernary.class)
                .findWarning("3:20-3:20:hint:" + Bundle.ERR_InvertTernary())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int a = (false) ? 0 : 1; \n"
                        + "    }\n"
                        + "}\n");

    }

}
