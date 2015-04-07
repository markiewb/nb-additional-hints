package de.markiewb.netbeans.plugins.hints.modifiers;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

public class MakeProtectedTest {

    @Test
    public void testFixWorking_PackageProtected_Field() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    Str|ing s = null;\n"
                        + "}\n")
                .run(MakeProtected.class)
                .findWarning("2:7-2:7:hint:" + Bundle.ERR_MakeProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    protected String s = null;\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_PackageProtected_Inner_Class() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    class I|nner {}\n"
                        + "}\n")
                .run(MakeProtected.class)
                .findWarning("2:11-2:11:hint:" + Bundle.ERR_MakeProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    protected class Inner {}\n"
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
                .run(MakeProtected.class)
                .findWarning("2:17-2:17:hint:" + Bundle.ERR_MakeProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    protected static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n");
    }

    /**
     * top level classes cannot be made protected
     * @throws Exception 
     */
    @Test
    public void testFixWorking_PackageProtected_TopLevel_Class() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test; class T|est {}")
                .run(MakeProtected.class)
                .assertWarnings();
    }

    @Test
    public void testFixWorking_Private_Field() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    private Str|ing s = null;\n"
                        + "}\n")
                .run(MakeProtected.class)
                .findWarning("2:15-2:15:hint:" + Bundle.ERR_MakeProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    protected String s = null;\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Private_Inner_Class() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    private class In|ner {}\n"
                        + "}\n")
                .run(MakeProtected.class)
                .findWarning("2:20-2:20:hint:" + Bundle.ERR_MakeProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    protected class Inner {}\n"
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
                .run(MakeProtected.class)
                .findWarning("2:25-2:25:hint:" + Bundle.ERR_MakeProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    protected static void main(String[] args) {\n"
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
                .run(MakeProtected.class);
    }

    @Test
    public void testFixWorking_Public_Field() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public Stri|ng s = null;\n"
                        + "}\n")
                .run(MakeProtected.class)
                .findWarning("2:15-2:15:hint:" + Bundle.ERR_MakeProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    protected String s = null;\n"
                        + "}\n")
                ;
    }

    @Test
    public void testFixWorking_Public_Inner_Class() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public class In|ner {}\n"
                        + "}\n")
                .run(MakeProtected.class)
                .findWarning("2:19-2:19:hint:" + Bundle.ERR_MakeProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    protected class Inner {}\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Public_Method() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    static void ma|in(String[] args) {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakeProtected.class)
                .findWarning("2:18-2:18:hint:" + Bundle.ERR_MakeProtected())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    protected static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n")
                ;
    }

    @Test
    public void testFixWorking_Public_TopLevel_Class() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test; public class Te|st {}")
                .run(MakeProtected.class)
                .assertWarnings();
    }

    @Test
    public void testFixWorking_Public_TopLevel_Class_InnerBlock() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test; public class Test {|}")
                .run(MakeProtected.class)
                .assertWarnings();
    }

}
