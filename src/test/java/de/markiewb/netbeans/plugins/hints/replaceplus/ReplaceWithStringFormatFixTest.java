/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 * 
 * "Portions Copyrighted 2013 Benno Markiewicz"
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
        HintTest.create().setCaretMarker('|').
                input("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=\"Output contains \"|+4+\" entries\";\n"
                + "    }\n"
                + "}\n").
                run(ReplacePlusHint.class).
                findWarning("3:37-3:37:hint:" + Bundle.DN_ReplacePlus()).
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
        HintTest.create().setCaretMarker('|').
                input("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=\"A\"+|4;\n"
                + "    }\n"
                + "}\n").
                run(ReplacePlusHint.class).
                findWarning("3:23-3:23:hint:" + Bundle.DN_ReplacePlus()).
                applyFix(Bundle.LBL_ReplaceWithStringFormatFix()).
                assertCompilable().
                assertOutput("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=String.format(\"A%s\", 4);\n"
                + "    }\n"
                + "}\n");
    }
    /**
     * https://github.com/markiewb/nb-additional-hints/issues/1
     * @throws Exception 
     */
    @Test
    public void testFixWorkingQuotedStrings1() throws Exception {
	HintTest.create().setCaretMarker('|').
		input("package test;\n"
		+ "public class Test {\n"
		+ "    public static void main(String[] args) {\n"
		+ "	String b = \"Hello \\\"\"+|42+\"\\\"\";"
		+ "    }\n"
		+ "}\n").
		run(ReplacePlusHint.class).
		findWarning("3:23-3:23:hint:" + Bundle.DN_ReplacePlus()).
		applyFix(Bundle.LBL_ReplaceWithStringFormatFix()).
		assertCompilable().
		assertOutput("package test;\n"
		+ "public class Test {\n"
		+ "    public static void main(String[] args) {\n"
		+ "     String b = String.format(\"Hello \\\"%s\\\"\", 42);\n"
		+ "    }\n"
		+ "}\n");
    }

    @Test
    public void testFixWorkingLiteralPostfix() throws Exception {
        HintTest.create().setCaretMarker('|').
                input("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=4+\"|A\";\n"
                + "    }\n"
                + "}\n").
                run(ReplacePlusHint.class).
                findWarning("3:22-3:22:hint:" + Bundle.DN_ReplacePlus()).
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
    public void testOnlyLiterals() throws Exception {
        HintTest.create().setCaretMarker('|').
                input("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=\"A\"|+\"B\"+\"C\";\n"
                + "    }\n"
                + "}\n").
                run(ReplacePlusHint.class).
                assertNotContainsWarnings(Bundle.DN_ReplacePlus());
    }

    @Test
    public void testFixWorkingMixedB() throws Exception {
        HintTest.create().setCaretMarker('|').
                input("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=\"Output contains \" +| 4 + \" entries\" + \" and more at \" + new java.util.Date();\n"
                + "    }\n"
                + "}\n").
                run(ReplacePlusHint.class).
                findWarning("3:39-3:39:hint:" + Bundle.DN_ReplacePlus()).
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
        HintTest.create().setCaretMarker('|').
                input("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=\"ABC\\nDEF\\r\"|+4+\"GHI\";\n"
                + "    }\n"
                + "}\n").
                run(ReplacePlusHint.class).
                findWarning("3:31-3:31:hint:" + Bundle.DN_ReplacePlus()).
                applyFix(Bundle.LBL_ReplaceWithStringFormatFix()).
                assertCompilable().
                assertOutput("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=String.format(\"ABC\\nDEF\\r%sGHI\", 4);\n"
                + "    }\n"
                + "}\n");
    }    
    @Test
    public void testSingleLiteral() throws Exception {
        HintTest.create().setCaretMarker('|').
                input("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=\"A|\";\n"
                + "    }\n"
                + "}\n").
                run(ReplacePlusHint.class).
		assertNotContainsWarnings(Bundle.DN_ReplacePlus());
    }
}
