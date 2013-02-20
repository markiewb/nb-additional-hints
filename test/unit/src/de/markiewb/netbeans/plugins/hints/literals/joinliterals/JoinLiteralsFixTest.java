/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.literals.joinliterals;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

public class JoinLiteralsFixTest {

    @Test
    public void testFixWorkingOnlyLiterals() throws Exception {
	HintTest.create().
		input("package test;\n"
		+ "public class Test {\n"
		+ "    public static void main(String[] args) {\n"
		+ "        String foo=\"A\"+\"B\"+\"C\";\n"
		+ "    }\n"
		+ "}\n").
		run(JoinLiteralsHint.class).
		findWarning("3:19-3:30:hint:" + Bundle.DN_JoinLiterals()).
		applyFix(Bundle.LBL_JoinLiterals()).
		assertCompilable().
		assertOutput("package test;\n"
		+ "public class Test {\n"
		+ "    public static void main(String[] args) {\n"
		+ "        String foo=\"ABC\";\n"
		+ "    }\n"
		+ "}\n");
    }

    @Test
    public void testLiteralsInAnnotation() throws Exception {
	HintTest.create().
		input("package test;\n"
		+ "public class Test {\n"
		+ "    @SuppressWarnings(\"Foo\" + \"Bar\")\n"
		+ "    public static void main(String[] args) {}\n"
		+ "}\n").
		run(JoinLiteralsHint.class).
		findWarning("2:22-2:35:hint:" + Bundle.DN_JoinLiterals()).
		applyFix(Bundle.LBL_JoinLiterals()).
		assertCompilable().
		assertOutput("package test;\n"
		+ "public class Test {\n"
		+ "    @SuppressWarnings(\"FooBar\")\n"
		+ "    public static void main(String[] args) {}\n"
		+ "}\n");
    }

    @Test
    public void testFixWorkingWithLineBreaks() throws Exception {
	HintTest.create().
		input("package test;\n"
		+ "public class Test {\n"
		+ "    public static void main(String[] args) {\n"
		+ "        String foo=\"ABC\\nD\"+\"EF\\rGHI\";\n"
		+ "    }\n"
		+ "}\n").
		run(JoinLiteralsHint.class).
		findWarning("3:19-3:37:hint:" + Bundle.DN_JoinLiterals()).
		applyFix(Bundle.LBL_JoinLiterals()).
		assertCompilable().
		assertOutput("package test;\n"
		+ "public class Test {\n"
		+ "    public static void main(String[] args) {\n"
		+ "        String foo=\"ABC\\nDEF\\rGHI\";\n"
		+ "    }\n"
		+ "}\n");
    }

    @Test
    public void testSingleLiteral() throws Exception {
	HintTest.create().
		input("package test;\n"
		+ "public class Test {\n"
		+ "    public static void main(String[] args) {\n"
		+ "        String foo=\"A\";\n"
		+ "    }\n"
		+ "}\n").
		run(JoinLiteralsHint.class).
		assertNotContainsWarnings(Bundle.DN_JoinLiterals());
    }
}
