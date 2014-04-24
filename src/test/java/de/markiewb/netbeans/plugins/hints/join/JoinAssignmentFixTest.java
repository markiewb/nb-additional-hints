/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.join;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

/**
 *
 * @author markiewb
 */
public class JoinAssignmentFixTest {

    @Test
    public void testSimpleJoin_secondLine() throws Exception {
        HintTest.create().
                setCaretMarker('|').
                input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int foo;\n"
                        + "        f|oo = 42;\n"
                        + "    }\n"
                        + "}\n", true).
                run(JoinAssignmentFix.class).
                findWarning("4:9-4:9:hint:" + Bundle.ERR_JoinAssignment()).
                applyFix(Bundle.ERR_JoinAssignment()).
                assertCompilable().
                assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int foo = 42;\n"
                        + "    }\n"
                        + "}\n"
                );

    }
    @Test
    public void testSimpleJoin_firstLine() throws Exception {
        HintTest.create().
                setCaretMarker('|').
                input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int f|oo;\n"
                        + "        foo = 42;\n"
                        + "    }\n"
                        + "}\n", true).
                run(JoinAssignmentFix.class).
                findWarning("3:13-3:13:hint:" + Bundle.ERR_JoinAssignment()).
                applyFix(Bundle.ERR_JoinAssignment()).
                assertCompilable().
                assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int foo = 42;\n"
                        + "    }\n"
                        + "}\n"
                );

    }
    @Test
    public void testSimpleJoin_severalCandiates() throws Exception {
        HintTest.create().
                setCaretMarker('|').
                input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int bar;\n"
                        + "        bar = 42;\n"
                        + "        int f|oo;\n"
                        + "        foo = 42;\n"
                        + "    }\n"
                        + "}\n", true).
                run(JoinAssignmentFix.class).
                findWarning("5:13-5:13:hint:" + Bundle.ERR_JoinAssignment()).
                applyFix(Bundle.ERR_JoinAssignment()).
                assertCompilable().
                assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int bar;\n"
                        + "        bar = 42;\n"
                        + "        int foo = 42;\n"
                        + "    }\n"
                        + "}\n"
                );

    }

}
