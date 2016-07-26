package de.markiewb.netbeans.plugins.hints.parentheses;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

/**
 *
 * @author markiewb
 */
public class RemoveParenthesesTest {

    public RemoveParenthesesTest() {
    }

    @Test
    public void testRemove() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int s = (|42);\n"
                        + "    }\n"
                        + "}\n")
                .run(RemoveParentheses.class)
                .findWarning("3:17-3:17:hint:" + Bundle.LABEL_RemoveParentheses("42"))
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int s = 42;\n"
                        + "    }\n"
                        + "}\n");

    }

    @Test
    public void testDontRemove() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int s = 34*(|78+48);\n"
                        + "    }\n"
                        + "}\n")
                .run(RemoveParentheses.class)
                .assertWarnings();
    }
    @Test
    public void testDontRemove2() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int s = 34/(|78+48);\n"
                        + "    }\n"
                        + "}\n")
                .run(RemoveParentheses.class)
                .assertWarnings();
    }

}
