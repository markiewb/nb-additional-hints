package de.markiewb.netbeans.plugins.hints.bigdecimal;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

public class UseBDConstFixTENTest {

    @Test
    public void testConvert_A() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.math.BigDecimal a=new java.math.BigDecimal(\"10\");\n"
                        + "    }\n"
                        + "}\n")
                .run(UseBDConstFixTEN.class)
                .findWarning("3:31-3:61:hint:" + Bundle.ERR_UseBDConstFixTEN())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import java.math.BigDecimal;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.math.BigDecimal a=BigDecimal.TEN;\n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testConvert_B() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.math.BigDecimal a=new java.math.BigDecimal(\"10.0\");\n"
                        + "    }\n"
                        + "}\n")
                .run(UseBDConstFixTEN.class)
                .findWarning("3:31-3:63:hint:" + Bundle.ERR_UseBDConstFixTEN())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import java.math.BigDecimal;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.math.BigDecimal a=BigDecimal.TEN;\n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testConvert_C() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.math.BigDecimal a=new java.math.BigDecimal(10.0);\n"
                        + "    }\n"
                        + "}\n")
                .run(UseBDConstFixTEN.class)
                .findWarning("3:31-3:61:hint:" + Bundle.ERR_UseBDConstFixTEN())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import java.math.BigDecimal;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.math.BigDecimal a=BigDecimal.TEN;\n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testConvert_D() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.math.BigDecimal a=new java.math.BigDecimal(10.00d);\n"
                        + "    }\n"
                        + "}\n")
                .run(UseBDConstFixTEN.class)
                .findWarning("3:31-3:63:hint:" + Bundle.ERR_UseBDConstFixTEN())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import java.math.BigDecimal;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        java.math.BigDecimal a=BigDecimal.TEN;\n"
                        + "    }\n"
                        + "}\n");

    }
}
