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
                .findWarning("3:17-3:17:hint:" + Bundle.LABEL_AddParentheses("true"))
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int s = (true) ? 1 : 0;\n"
                        + "    }\n"
                        + "}\n");

    }

    @Test
    public void testAddToReturnLiteral() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static int main(String[] args) {\n"
                        + "        return 4|2;\n"
                        + "    }\n"
                        + "}\n")
                .run(AddParentheses.class)
                .findWarning("3:16-3:16:hint:" + Bundle.LABEL_AddParentheses("42"))
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static int main(String[] args) {\n"
                        + "        return (42);\n"
                        + "    }\n"
                        + "}\n");

    }

    @Test
    public void testAddToReturnOperation() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static int main(String[] args) {\n"
                        + "        return 4|2 + 44;\n"
                        + "    }\n"
                        + "}\n")
                .run(AddParentheses.class)
                .findWarning("3:16-3:16:hint:" + Bundle.LABEL_AddParentheses("42 + 44"))
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static int main(String[] args) {\n"
                        + "        return (42 + 44);\n"
                        + "    }\n"
                        + "}\n");

    }

    @Test
    public void testAddToOperation_A() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int a = 4|2 + 44;\n"
                        + "    }\n"
                        + "}\n")
                .run(AddParentheses.class)
                .findWarning("3:17-3:17:hint:" + Bundle.LABEL_AddParentheses("42"))
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int a = (42) + 44;\n"
                        + "    }\n"
                        + "}\n");

    }

    @Test
    public void testAddToOperation_B() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int a = 4|2 + 44;\n"
                        + "    }\n"
                        + "}\n")
                .run(AddParentheses.class)
                .findWarning("3:17-3:17:hint:" + Bundle.LABEL_AddParentheses("42 + 44"))
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int a = (42 + 44);\n"
                        + "    }\n"
                        + "}\n");

    }

    @Test
    public void testAddToTernary_Surrounding() throws Exception {
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
    public void testAddToTernary_A() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int s = true ? 2|2 : 44;\n"
                        + "    }\n"
                        + "}\n")
                .run(AddParentheses.class)
                .findWarning("3:24-3:24:hint:" + Bundle.LABEL_AddParentheses("22"))
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int s = true ? (22) : 44;\n"
                        + "    }\n"
                        + "}\n");

    }

    @Test
    public void testAddToTernary_B() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int s = true ? 22 : 4|4;\n"
                        + "    }\n"
                        + "}\n")
                .run(AddParentheses.class)
                .findWarning("3:29-3:29:hint:" + Bundle.LABEL_AddParentheses("44"))
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int s = true ? 22 : (44);\n"
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
