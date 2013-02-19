/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.breakonlinefeed;

import de.markiewb.netbeans.plugins.hints.replaceplus.*;
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
public class BreakStringOnLineFeedFixTest {

    @Test
    public void testFixWorkingMixedA() throws Exception {
        HintTest.create().
                input("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo = \"Hello,\\n\\rworld\\n!\\n\";\n"
                + "    }\n"
                + "}\n").
                run(BreakStringOnLineFeedHint.class).
                findWarning("3:21-3:43:hint:" + Bundle.DN_BreakStringOnLineFeed()).
                applyFix(Bundle.LBL_BreakStringOnLineFeedFix()).
                assertCompilable().
                assertOutput("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo = \"Hello,\\n\\r\" + \"world\\n\" + \"!\\n\";\n"
                + "    }\n"
                + "}\n");
    }


}
