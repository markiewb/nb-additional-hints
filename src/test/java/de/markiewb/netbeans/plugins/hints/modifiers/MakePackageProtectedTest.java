package de.markiewb.netbeans.plugins.hints.modifiers;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

public class MakePackageProtectedTest {

    @Test
    public void testFixWorking_Public_TopLevel_Class() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {}\n")
                .run(MakePackageProtected.class)
                .findWarning("1:13-1:17:hint:" + Bundle.ERR_MakePackageProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "class Test {}\n");
    }

    @Test
    public void testFixWorking_Public_Method() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakePackageProtected.class)
                .findWarning("2:23-2:27:hint:" + Bundle.ERR_MakePackageProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    static void main(String[] args) {\n"
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
                .run(MakePackageProtected.class)
                .findWarning("2:24-2:28:hint:" + Bundle.ERR_MakePackageProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Protected_Method() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    protected static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakePackageProtected.class)
                .findWarning("2:26-2:30:hint:" + Bundle.ERR_MakePackageProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    static void main(String[] args) {\n"
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
                .run(MakePackageProtected.class)
                .findWarning("2:21-2:22:hint:" + Bundle.ERR_MakePackageProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    String s = null;\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Private_Field() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    private String s = null;\n"
                        + "}\n")
                .run(MakePackageProtected.class)
                .findWarning("2:19-2:20:hint:" + Bundle.ERR_MakePackageProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    String s = null;\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Public_Field() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public String s = null;\n"
                        + "}\n")
                .run(MakePackageProtected.class)
                .findWarning("2:18-2:19:hint:" + Bundle.ERR_MakePackageProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    String s = null;\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Public_InnerClass() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public class Inner {}\n"
                        + "}\n")
                .run(MakePackageProtected.class)
                .findWarning("2:17-2:22:hint:" + Bundle.ERR_MakePackageProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    class Inner {}\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Private_InnerClass() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    private class Inner {}\n"
                        + "}\n")
                .run(MakePackageProtected.class)
                .findWarning("2:18-2:23:hint:" + Bundle.ERR_MakePackageProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    class Inner {}\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Protected_InnerClass() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    protected class Inner {}\n"
                        + "}\n")
                .run(MakePackageProtected.class)
                .findWarning("2:20-2:25:hint:" + Bundle.ERR_MakePackageProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    class Inner {}\n"
                        + "}\n");
    }
}
