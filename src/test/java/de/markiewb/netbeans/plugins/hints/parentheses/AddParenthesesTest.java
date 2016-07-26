package de.markiewb.netbeans.plugins.hints.parentheses;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

/**
 *
 * @author markiewb
 */
public class AddParenthesesTest {

    @Test
    public void testAdd() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int s = 4|2;\n"
                        + "    }\n"
                        + "}\n")
                .run(AddParentheses.class)
                .findWarning("3:17-3:17:hint:" + Bundle.LABEL_AddParentheses("42"))
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int s = (42);\n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testAddComplement() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        boolean s = !t|rue;\n"
                        + "    }\n"
                        + "}\n")
                .run(AddParentheses.class)
                .findWarning("3:22-3:22:hint:" + Bundle.LABEL_AddParentheses("!true"))
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        boolean s = (!true);\n"
                        + "    }\n"
                        + "}\n");

    }

    @Test
    public void testAddToTernary() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int s = t|rue ? 1 : 0;\n"
                        + "    }\n"
                        + "}\n")
                .run(AddParentheses.class)
                .findWarning("3:17-3:17:hint:" + Bundle.LABEL_AddParentheses("true ? 1 : 0"))
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int s = (true ? 1 : 0);\n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testAddToTernaryReturn() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public int main(String[] args) {\n"
                        + "        return t|rue ? 1 : 0;\n"
                        + "    }\n"
                        + "}\n")
                .run(AddParentheses.class)
                .findWarning("3:16-3:16:hint:" + Bundle.LABEL_AddParentheses("true ? 1 : 0"))
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public int main(String[] args) {\n"
                        + "        return (true ? 1 : 0);\n"
                        + "    }\n"
                        + "}\n");

    }

    @Test
    public void testDontAddTwice() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int s = (4|2);\n"
                        + "    }\n"
                        + "}\n")
                .run(AddParentheses.class)
                .assertWarnings();
    }

}
