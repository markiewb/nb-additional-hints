/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.replaceplus;

import de.markiewb.netbeans.plugins.hints.replaceplus.ReplacePlusHint;
import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

/* TODO to make this test work:
 - add test dependency on Java Hints Test API (and JUnit 4)
 - to ensure that the newest Java language features supported by the IDE are available,
 regardless of which JDK you build the module with:
 -- for Ant-based modules, add "requires.nb.javac=true" into nbproject/project.properties
 -- for Maven-based modules, use dependency:copy in validate phase to create
 target/endorsed/org-netbeans-libs-javacapi-*.jar and add to endorseddirs
 in maven-compiler-plugin and maven-surefire-plugin configuration
 See: http://wiki.netbeans.org/JavaHintsTestMaven
 */
public class ReplaceWithSingleStringFixTest {

    @Test
    public void testFixWorkingOnlyLiterals() throws Exception {
        HintTest.create().
                input("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=\"A\"+\"B\"+\"C\";\n"
                + "    }\n"
                + "}\n").
                run(ReplacePlusHint.class).
                findWarning("3:19-3:30:hint:" + Bundle.DN_ReplacePlus()).
                applyFix(Bundle.LBL_ReplaceWithSingleStringFix()).
                assertCompilable().
                assertOutput("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=\"ABC\";\n"
                + "    }\n"
                + "}\n");
    }
@Test
    public void testFixWorkingWithLineBreaks() throws Exception {
        HintTest.create().
                input("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=\"ABC\\nDEF\\rGHI\";\n"
                + "    }\n"
                + "}\n").
                run(ReplacePlusHint.class).
                findWarning("3:19-3:34:hint:" + Bundle.DN_ReplacePlus()).
                applyFix(Bundle.LBL_ReplaceWithSingleStringFix()).
                assertCompilable().
                assertOutput("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=\"ABC\\nDEF\\rGHI\";\n"
                + "    }\n"
                + "}\n");
    }    
}
