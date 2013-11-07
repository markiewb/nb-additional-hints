package de.markiewb.netbeans.plugins.hints.bigdecimal;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

public class UseBDConstFixONETest {

    @Test
    public void testConvert_A() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.math.BigDecimal a=new java.math.BigDecimal(\"1\");\n"
                        + "    }\n"
                        + "}\n")
                .run(UseBDConstFixONE.class)
                .findWarning("3:31-3:60:hint:" + Bundle.ERR_UseBDConstFixONE())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import java.math.BigDecimal;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.math.BigDecimal a=BigDecimal.ONE;\n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testConvert_B() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.math.BigDecimal a=new java.math.BigDecimal(\"1.0\");\n"
                        + "    }\n"
                        + "}\n")
                .run(UseBDConstFixONE.class)
                .findWarning("3:31-3:62:hint:" + Bundle.ERR_UseBDConstFixONE())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import java.math.BigDecimal;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.math.BigDecimal a=BigDecimal.ONE;\n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testConvert_C() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.math.BigDecimal a=new java.math.BigDecimal(1.0);\n"
                        + "    }\n"
                        + "}\n")
                .run(UseBDConstFixONE.class)
                .findWarning("3:31-3:60:hint:" + Bundle.ERR_UseBDConstFixONE())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import java.math.BigDecimal;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.math.BigDecimal a=BigDecimal.ONE;\n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testConvert_D() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.math.BigDecimal a=new java.math.BigDecimal(1.00d);\n"
                        + "    }\n"
                        + "}\n")
                .run(UseBDConstFixONE.class)
                .findWarning("3:31-3:62:hint:" + Bundle.ERR_UseBDConstFixONE())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import java.math.BigDecimal;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.math.BigDecimal a=BigDecimal.ONE;\n"
                        + "    }\n"
                        + "}\n");

    }
}
