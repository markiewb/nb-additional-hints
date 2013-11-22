package de.markiewb.netbeans.plugins.hints.modifiers;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

public class MakePublicTest {

    @Test
    public void testFixWorking_PackageProtected_TopLevel_Class() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "class Test {}\n")
                .run(MakePublic.class)
                .findWarning("1:6-1:10:hint:" + Bundle.ERR_MakePublic())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {}\n");
    }

    @Test
    public void testFixWorking_Protected_Method() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    protected static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakePublic.class)
                .findWarning("2:26-2:30:hint:" + Bundle.ERR_MakePublic())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Private_Method() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    private static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakePublic.class)
                .findWarning("2:24-2:28:hint:" + Bundle.ERR_MakePublic())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Protected_Field() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    protected String s = null;\n"
                        + "}\n")
                .run(MakePublic.class)
                .findWarning("2:21-2:22:hint:" + Bundle.ERR_MakePublic())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public String s = null;\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Private_Field() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    private String s = null;\n"
                        + "}\n")
                .run(MakePublic.class)
                .findWarning("2:19-2:20:hint:" + Bundle.ERR_MakePublic())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public String s = null;\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Public_InnerClass() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    private class Inner {}\n"
                        + "}\n")
                .run(MakePublic.class)
                .findWarning("2:18-2:23:hint:" + Bundle.ERR_MakePublic())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public class Inner {}\n"
                        + "}\n");
    }
}
