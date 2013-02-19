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
public class ReplaceWithStringFormatFixTest {

    @Test
    public void testFixWorkingMixedA() throws Exception {
        HintTest.create().
                input("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=\"Output contains \"+4+\" entries\";\n"
                + "    }\n"
                + "}\n").
                run(ReplacePlusHint.class).
                findWarning("3:19-3:50:hint:" + Bundle.DN_ReplacePlus()).
                applyFix(Bundle.LBL_ReplaceWithStringFormatFix()).
                assertCompilable().
                assertOutput("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=String.format(\"Output contains %s entries\", 4);\n"
                + "    }\n"
                + "}\n");
    }

    @Test
    public void testFixWorkingLiteralPrefix() throws Exception {
        HintTest.create().
                input("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=\"A\"+4;\n"
                + "    }\n"
                + "}\n").
                run(ReplacePlusHint.class).
                findWarning("3:19-3:24:hint:" + Bundle.DN_ReplacePlus()).
                applyFix(Bundle.LBL_ReplaceWithStringFormatFix()).
                assertCompilable().
                assertOutput("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=String.format(\"A%s\", 4);\n"
                + "    }\n"
                + "}\n");
    }

    @Test
    public void testFixWorkingLiteralPostfix() throws Exception {
        HintTest.create().
                input("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=4+\"A\";\n"
                + "    }\n"
                + "}\n").
                run(ReplacePlusHint.class).
                findWarning("3:19-3:24:hint:" + Bundle.DN_ReplacePlus()).
                applyFix(Bundle.LBL_ReplaceWithStringFormatFix()).
                assertCompilable().
                assertOutput("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=String.format(\"%sA\", 4);\n"
                + "    }\n"
                + "}\n");
    }
    
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
                applyFix(Bundle.LBL_ReplaceWithStringFormatFix()).
                assertCompilable().
                assertOutput("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=String.format(\"ABC\");\n"
                + "    }\n"
                + "}\n");
    }

    @Test
    public void testFixWorkingMixedB() throws Exception {
        HintTest.create().
                input("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=\"Output contains \" + 4 + \" entries\" + \" and more at \" + new java.util.Date();\n"
                + "    }\n"
                + "}\n").
                run(ReplacePlusHint.class).
                findWarning("3:19-3:95:hint:" + Bundle.DN_ReplacePlus()).
                applyFix(Bundle.LBL_ReplaceWithStringFormatFix()).
                assertCompilable().
                assertOutput("package test;\n"
                + "import java.util.Date;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=String.format(\"Output contains %s entries and more at %s\", 4, new Date());\n"
                + "    }\n"
                + "}\n");

    }
@Test
    public void testFixWorkingWithLineBreaks() throws Exception {
        HintTest.create().
                input("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=\"ABC\\nDEF\\r\"+4+\"GHI\";\n"
                + "    }\n"
                + "}\n").
                run(ReplacePlusHint.class).
                findWarning("3:19-3:39:hint:" + Bundle.DN_ReplacePlus()).
                applyFix(Bundle.LBL_ReplaceWithStringFormatFix()).
                assertCompilable().
                assertOutput("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=String.format(\"ABC\\nDEF\\r%sGHI\", 4);\n"
                + "    }\n"
                + "}\n");
    }    
}
