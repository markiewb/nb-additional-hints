package de.markiewb.netbeans.plugins.hints.tonumber;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

/**
 *
 * @author markiewb
 */
public class ToNumberTest {

    @Test
    public void testConvertIntegerToString() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String a=\"bor\"+1|23;\n"
                        + "    }\n"
                        + "}\n")
                .run(ToNumber.class)
                .findWarning("3:24-3:24:hint:" + Bundle.ERR_ToString())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String a=\"bor\"+\"123\";\n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testConvertLongToString() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String a=\"bor\"+1|23L;\n"
                        + "    }\n"
                        + "}\n")
                .run(ToNumber.class)
                .findWarning("3:24-3:24:hint:" + Bundle.ERR_ToString())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String a=\"bor\"+\"123\";\n"
                        + "    }\n"
                        + "}\n");

    }

    @Test
    public void testConvertToNumber() throws Exception {
        HintTest.create()
                .setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String a=\"bor\"+\"|123\";\n"
                        + "    }\n"
                        + "}\n")
                .run(ToNumber.class)
                .findWarning("3:24-3:24:hint:" + Bundle.ERR_ToNumber())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String a=\"bor\"+123;\n"
                        + "    }\n"
                        + "}\n");

    }

}
