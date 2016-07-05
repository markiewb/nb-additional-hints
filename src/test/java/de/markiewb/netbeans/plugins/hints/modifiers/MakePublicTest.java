package de.markiewb.netbeans.plugins.hints.modifiers;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

public class MakePublicTest {

    @Test
    public void testFixWorking_PackageProtected_Field() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    Stri|ng s = null;\n"
                        + "}\n")
                .run(MakePublic.class)
                .findWarning("2:8-2:8:hint:" + Bundle.ERR_MakePublic())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public String s = null;\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_PackageProtected_Inner_Class() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    class In|ner {}\n"
                        + "}\n")
                .run(MakePublic.class)
                .findWarning("2:12-2:12:hint:" + Bundle.ERR_MakePublic())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public class Inner {}\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_PackageProtected_Constructor() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    Tes|t() {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakePublic.class)
                .findWarning("2:7-2:7:hint:" + Bundle.ERR_MakePublic())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public Test() {\n"
                        + "    }\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_PackageProtected_Method() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    static void m|ain(String[] args) {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakePublic.class)
                .findWarning("2:17-2:17:hint:" + Bundle.ERR_MakePublic())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_PackageProtected_TopLevel_Class() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test; class T|est {}")
                .run(MakePublic.class)
                .findWarning("0:21-0:21:hint:" + Bundle.ERR_MakePublic())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test; public class Test {}");
    }

    @Test
    public void testFixWorking_Private_Field() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    private Stri|ng s = null;\n"
                        + "}\n")
                .run(MakePublic.class)
                .findWarning("2:16-2:16:hint:" + Bundle.ERR_MakePublic())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public String s = null;\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Private_Inner_Class() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    private class In|ner {}\n"
                        + "}\n")
                .run(MakePublic.class)
                .findWarning("2:20-2:20:hint:" + Bundle.ERR_MakePublic())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public class Inner {}\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Private_Constructor() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    private Tes|t() {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakePublic.class)
                .findWarning("2:15-2:15:hint:" + Bundle.ERR_MakePublic())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public Test() {\n"
                        + "    }\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Private_Method() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    private static void ma|in(String[] args) {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakePublic.class)
                .findWarning("2:26-2:26:hint:" + Bundle.ERR_MakePublic())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n");
    }

    /**
     * 1:23 modifier private not allowed here
     *
     * @throws Exception
     */
    @Test(expected = AssertionError.class)
    public void testFixWorking_Private_TopLevel_Class() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test; private class Te|st {}")
                .run(MakePublic.class);
    }

    @Test
    public void testFixWorking_Protected_Field() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    protected Str|ing s = null;\n"
                        + "}\n")
                .run(MakePublic.class)
                .findWarning("2:17-2:17:hint:" + Bundle.ERR_MakePublic())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public String s = null;\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Protected_Inner_Class() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    protected class In|ner {}\n"
                        + "}\n")
                .run(MakePublic.class)
                .findWarning("2:22-2:22:hint:" + Bundle.ERR_MakePublic())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public class Inner {}\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Protected_Constructor() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    protected Te|st() {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakePublic.class)
                .findWarning("2:16-2:16:hint:" + Bundle.ERR_MakePublic())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public Test() {\n"
                        + "    }\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Protected_Method() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    protected static void m|ain(String[] args) {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakePublic.class)
                .findWarning("2:27-2:27:hint:" + Bundle.ERR_MakePublic())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n");
    }

    /**
     * 1:25 modifier protected not allowed here
     *
     * @throws Exception
     */
    @Test(expected = AssertionError.class)
    public void testFixWorking_Protected_TopLevel_Class() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test; protected class Test {}")
                .run(MakePublic.class);
    }

    @Test
    public void testFixWorking_Public_TopLevel_Class_InnerBlock() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test; public class Test {|}")
                .run(MakePublic.class)
                .assertWarnings();
    }
}
