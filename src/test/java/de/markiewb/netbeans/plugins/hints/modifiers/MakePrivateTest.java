package de.markiewb.netbeans.plugins.hints.modifiers;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

public class MakePrivateTest {

    @Test
    public void testFixWorking_PackageProtected_Field() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    St|ring s = null;\n"
                        + "}\n")
                .run(MakePrivate.class)
                .findWarning("2:0-2:20:hint:" + Bundle.ERR_MakePrivate())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    private String s = null;\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_PackageProtected_Inner_Class() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    class Inn|er {}\n"
                        + "}\n")
                .run(MakePrivate.class)
                .findWarning("2:0-2:18:hint:" + Bundle.ERR_MakePrivate())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    private class Inner {}\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_PackageProtected_Constructor() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    T|est() {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakePrivate.class)
                .findWarning("2:0-2:12:hint:" + Bundle.ERR_MakePrivate())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    private Test() {\n"
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
                .run(MakePrivate.class)
                .findWarning("2:0-2:37:hint:" + Bundle.ERR_MakePrivate())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    private static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n");
    }

    /**
     * top level classes cannot be made private
     * @throws Exception 
     */
    @Test
    public void testFixWorking_PackageProtected_TopLevel_Class() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test; class Te|st {}")
                .run(MakePrivate.class)
                .assertWarnings();
    }

    @Test
    public void testFixWorking_Protected_Field() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    protected St|ring s = null;\n"
                        + "}\n")
                .run(MakePrivate.class)
                .findWarning("2:0-2:30:hint:" + Bundle.ERR_MakePrivate());
    }

    @Test
    public void testFixWorking_Protected_Inner_Class() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    protected class In|ner {}\n"
                        + "}\n")
                .run(MakePrivate.class)
                .findWarning("2:0-2:28:hint:" + Bundle.ERR_MakePrivate());
    }

    @Test
    public void testFixWorking_Protected_Constructor() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    protected T|est() {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakePrivate.class)
                .findWarning("2:0-2:22:hint:" + Bundle.ERR_MakePrivate())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    private Test() {\n"
                        + "    }\n"
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
                .run(MakePrivate.class)
                .findWarning("2:0-2:47:hint:" + Bundle.ERR_MakePrivate());
    }

    /**
     * 1:25 modifier protected not allowed here
     *
     * @throws Exception
     */
    @Test(expected = AssertionError.class)
    public void testFixWorking_Protected_TopLevel_Class() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test; protected class Te|st {}")
                .run(MakePrivate.class);
    }

    @Test
    public void testFixWorking_Public_Field() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public Strin|g s = null;\n"
                        + "}\n")
                .run(MakePrivate.class)
                .findWarning("2:0-2:27:hint:" + Bundle.ERR_MakePrivate())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    private String s = null;\n"
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
                .run(MakePrivate.class)
                .findWarning("2:0-2:25:hint:" + Bundle.ERR_MakePrivate())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    private class Inner {}\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Public_Constructor() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public T|est() {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakePrivate.class)
                .findWarning("2:0-2:19:hint:" + Bundle.ERR_MakePrivate())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    private Test() {\n"
                        + "    }\n"
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
                .run(MakePrivate.class)
                .findWarning("2:0-2:37:hint:" + Bundle.ERR_MakePrivate())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    private static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n")
                ;
    }

    /**
     * top level classes cannot be made private
     * @throws Exception 
     */
    @Test
    public void testFixWorking_Public_TopLevel_Class() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test; public class Te|st {}")
                .run(MakePrivate.class)
                .assertNotContainsWarnings("0:27-1:31:hint:" + Bundle.ERR_MakePrivate());
    }

    @Test
    public void testFixWorking_Public_TopLevel_Class_InnerBlock() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test; public class Test {|}")
                .run(MakePrivate.class)
                .assertWarnings();
    }

}
