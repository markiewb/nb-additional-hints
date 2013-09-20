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
package de.markiewb.netbeans.plugins.hints.literals.joinliterals;

import de.markiewb.netbeans.plugins.hints.replaceplus.ReplacePlusHint;
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
    
    /**
     * https://github.com/markiewb/nb-additional-hints/issues/1
     *
     * @throws Exception
     */
    @Test
    public void testFixWorkingQuotedStrings1() throws Exception {
	HintTest.create().
		input("package test;\n"
		+ "public class Test {\n"
		+ "    public static void main(String[] args) {\n"
		+ "        String foo=\"A\\\"___\\\"\"+\"B\"+\"C\";\n"
		+ "    }\n"
		+ "}\n").
		run(JoinLiteralsHint.class).
		findWarning("3:19-3:37:hint:" + Bundle.DN_JoinLiterals()).
		applyFix(Bundle.LBL_JoinLiterals()).
		assertCompilable().
		assertOutput("package test;\n"
		+ "public class Test {\n"
		+ "    public static void main(String[] args) {\n"
		+ "     String foo=\"A\\\"___\\\"BC\";\n"
		+ "    }\n"
		+ "}\n");
    }
}
