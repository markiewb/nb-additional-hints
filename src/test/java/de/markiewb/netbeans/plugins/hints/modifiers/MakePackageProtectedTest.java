package de.markiewb.netbeans.plugins.hints.modifiers;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

public class MakePackageProtectedTest {

    @Test
    public void testPreserveGenerics_PublicMethod() throws Exception {
        HintTest.create().setCaretMarker('|')
                .sourceLevel("1.6")
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public <T extends java.lang.Number> java.util.Set<T> fi|nd(){return null;};\n"
                        + "}\n")
                .run(MakePackageProtected.class)
                .findWarning("2:59-2:59:hint:" + Bundle.ERR_MakePackageProtected())
                .applyFix()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    <T extends java.lang.Number> java.util.Set<T> find(){return null;};\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Private_Field() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    private Strin|g s = null;\n"
                        + "}\n")
                .run(MakePackageProtected.class)
                .findWarning("2:17-2:17:hint:" + Bundle.ERR_MakePackageProtected())
                .applyFix().assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    String s = null;\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Private_Inner_Class() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    private class In|ner {}\n"
                        + "}\n")
                .run(MakePackageProtected.class)
                .findWarning("2:20-2:20:hint:" + Bundle.ERR_MakePackageProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    class Inner {}\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Private_Method() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    private static void m|ain(String[] args) {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakePackageProtected.class)
                .findWarning("2:25-2:25:hint:" + Bundle.ERR_MakePackageProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    static void main(String[] args) {\n"
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
                .input("package test; private class Test {}")
                .run(MakePackageProtected.class);
    }

    @Test
    public void testFixWorking_Protected_Field() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    protected Stri|ng s = null;\n"
                        + "}\n")
                .run(MakePackageProtected.class)
                .findWarning("2:18-2:18:hint:" + Bundle.ERR_MakePackageProtected())
                .applyFix().assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    String s = null;\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Protected_Inner_Class() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    protected class In|ner {}\n"
                        + "}\n")
                .run(MakePackageProtected.class)
                .findWarning("2:22-2:22:hint:" + Bundle.ERR_MakePackageProtected())
                .applyFix().assertCompilable().
                assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    class Inner {}\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Protected_Method() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    protected static void ma|in(String[] args) {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakePackageProtected.class)
                .findWarning("2:28-2:28:hint:" + Bundle.ERR_MakePackageProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    static void main(String[] args) {\n"
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
                .run(MakePackageProtected.class);
    }

    @Test
    public void testFixWorking_Public_Field() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public Str|ing s = null;\n"
                        + "}\n")
                .run(MakePackageProtected.class)
                .findWarning("2:14-2:14:hint:" + Bundle.ERR_MakePackageProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    String s = null;\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Public_Inner_Class() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public class In|ner {}\n"
                        + "}\n")
                .run(MakePackageProtected.class)
                .findWarning("2:19-2:19:hint:" + Bundle.ERR_MakePackageProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    class Inner {}\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Public_Method() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void ma|in(String[] args) {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakePackageProtected.class)
                .findWarning("2:25-2:25:hint:" + Bundle.ERR_MakePackageProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Public_TopLevel_Class() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test; public class Te|st {}")
                .run(MakePackageProtected.class)
                .findWarning("0:29-0:29:hint:" + Bundle.ERR_MakePackageProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test; class Test {}");
    }

    @Test
    public void testFixWorking_Public_TopLevel_Class_InnerBlock() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test; public class Test {|}")
                .run(MakePackageProtected.class)
                .assertWarnings();
    }
}
