/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.literals.split;

import org.junit.Test;
import org.netbeans.modules.java.hints.spiimpl.TestUtilities;
import org.netbeans.modules.java.hints.test.api.HintTest;

/* TODO to make this test work:
 * - to ensure that the newest Java language features supported by the IDE are available,
 * regardless of which JDK you build the module with:
 * -- for Ant-based modules, add "requires.nb.javac=true" into nbproject/project.properties
 * -- for Maven-based modules, use dependency:copy in validate phase to create
 * target/endorsed/org-netbeans-libs-javacapi-*.jar and add to endorseddirs
 * in maven-compiler-plugin and maven-surefire-plugin configuration
 * See: http://wiki.netbeans.org/JavaHintsTestMaven
 */
public class ExtractLiteralFromSelectionHintTest {
//
//    @Test
//    public void testFixWorking() throws Exception {
//        HintTest.create()
//                .setCaretMarker('|')
//                .input("package test;\n"
//                        + "public class Test {\n"
//                        + "    public static void main(String[] args) {\n"
//                        + "        String a=\"f|oo|bar\";\n"
//                        + "    }\n"
//                        + "}\n")
//                .run(ExtractLiteralFromSelectionHint.class)
//                .findWarning("3:21-3:21:verifier:" + Bundle.ERR_ExtractLiteralFromSelectionHint())
//                .applyFix()
//                .assertCompilable()
//                //TODO: change to match expected output
//                .assertOutput("package test;\n"
//                        + "public class Test {\n"
//                        + "    public static void main(String[] args) {\n"
//                        + "        String a=\"f\" + \"oo\" + \"bar\";\n"
//                        + "    }\n"
//                        + "}\n");
//
//        int[] span = new int[2];
//
//        String code = "";
//        code = TestUtilities.detectOffsets(code, span);
//    }

    private void performSimpleSelectionVerificationTest(String code, boolean awaited) throws Exception {
        int[] span = new int[2];

        code = TestUtilities
                .detectOffsets(code, span);

//        performSimpleSelectionVerificationTest(code, span[0], span[1], awaited);
    }
}
