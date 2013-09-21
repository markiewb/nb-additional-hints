/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.literals.split;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

/* TODO to make this test work:
 - to ensure that the newest Java language features supported by the IDE are available,
 regardless of which JDK you build the module with:
 -- for Ant-based modules, add "requires.nb.javac=true" into nbproject/project.properties
 -- for Maven-based modules, use dependency:copy in validate phase to create
 target/endorsed/org-netbeans-libs-javacapi-*.jar and add to endorseddirs
 in maven-compiler-plugin and maven-surefire-plugin configuration
 See: http://wiki.netbeans.org/JavaHintsTestMaven
 */
public class SplitLiteralOnCaretHintTest {

    @Test
    public void testWarningProduced() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String a=\"foo|bar\";\n"
                        + "    }\n"
                        + "}\n")
                .run(SplitLiteralOnCaretHint.class)
                .assertWarnings("3:21-3:21:hint:" + Bundle.ERR_SplitLiteralOnCaretHint());
    }

    @Test
    public void testWarningNotProduced_Empty() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String a=\"|\";\n"
                        + "    }\n"
                        + "}\n")
                .run(SplitLiteralOnCaretHint.class)
                .assertWarnings();
    }

    @Test
    public void testWarningNotProduced_AtStart() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String a=\"|foobar\";\n"
                        + "    }\n"
                        + "}\n")
                .run(SplitLiteralOnCaretHint.class)
                .assertWarnings();
    }

    @Test
    public void testWarningNotProduced_AtEnd() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String a=\"foobar|\";\n"
                        + "    }\n"
                        + "}\n")
                .run(SplitLiteralOnCaretHint.class)
                .assertWarnings();
    }

    @Test
    public void testFixWorking() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String a=\"foo|bar\";\n"
                        + "    }\n"
                        + "}\n")
                .run(SplitLiteralOnCaretHint.class)
                .findWarning("3:21-3:21:hint:" + Bundle.ERR_SplitLiteralOnCaretHint())
                .applyFix()
                .assertCompilable()
                //TODO: change to match expected output
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String a=\"foo\" + \"bar\";\n"
                        + "    }\n"
                        + "}\n");
    }
}
