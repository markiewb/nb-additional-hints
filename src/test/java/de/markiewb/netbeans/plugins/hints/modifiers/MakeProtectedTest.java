package de.markiewb.netbeans.plugins.hints.modifiers;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

public class MakeProtectedTest {

    @Test
    public void testFixWorking_Public_Method() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakeProtected.class)
                .findWarning("2:23-2:27:hint:" + Bundle.ERR_MakeProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    protected static void main(String[] args) {\n"
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
                .run(MakeProtected.class)
                .findWarning("2:24-2:28:hint:" + Bundle.ERR_MakeProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    protected static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_PackageProtected_Method() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakeProtected.class)
                .findWarning("2:16-2:20:hint:" + Bundle.ERR_MakeProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    protected static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_PackageProtected_Field() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    String s = null;\n"
                        + "}\n")
                .run(MakeProtected.class)
                .findWarning("2:11-2:12:hint:" + Bundle.ERR_MakeProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    protected String s = null;\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Private_Field() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    private String s = null;\n"
                        + "}\n")
                .run(MakeProtected.class)
                .findWarning("2:19-2:20:hint:" + Bundle.ERR_MakeProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    protected String s = null;\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Public_Field() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public String s = null;\n"
                        + "}\n")
                .run(MakeProtected.class)
                .findWarning("2:18-2:19:hint:" + Bundle.ERR_MakeProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    protected String s = null;\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Public_InnerClass() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public class Inner {}\n"
                        + "}\n")
                .run(MakeProtected.class)
                .findWarning("2:17-2:22:hint:" + Bundle.ERR_MakeProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    protected class Inner {}\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Private_InnerClass() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    private class Inner {}\n"
                        + "}\n")
                .run(MakeProtected.class)
                .findWarning("2:18-2:23:hint:" + Bundle.ERR_MakeProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    protected class Inner {}\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_PackageProtected_InnerClass() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    class Inner {}\n"
                        + "}\n")
                .run(MakeProtected.class)
                .findWarning("2:10-2:15:hint:" + Bundle.ERR_MakeProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    protected class Inner {}\n"
                        + "}\n");
    }
}
