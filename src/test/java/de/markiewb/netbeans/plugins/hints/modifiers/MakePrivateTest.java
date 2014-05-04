package de.markiewb.netbeans.plugins.hints.modifiers;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

public class MakePrivateTest {

    @Test
    public void testFixWorking_PackageProtected_Field() throws Exception {
        HintTest.create().setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    String s = null;\n"
                        + "}\n")
                .run(MakePrivate.class)
                .findWarning("2:11-2:12:hint:" + Bundle.ERR_MakePrivate())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    private String s = null;\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_PackageProtected_Inner_Class() throws Exception {
        HintTest.create().setCaretMarker('|').setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    class Inner {}\n"
                        + "}\n")
                .run(MakePrivate.class)
                .findWarning("2:10-2:15:hint:" + Bundle.ERR_MakePrivate())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    private class Inner {}\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_PackageProtected_Method() throws Exception {
        HintTest.create().setCaretMarker('|').setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakePrivate.class)
                .findWarning("2:16-2:20:hint:" + Bundle.ERR_MakePrivate())
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
        HintTest.create().setCaretMarker('|').setCaretMarker('|')
                .input("package test; class Test {}")
                .run(MakePrivate.class)
                .assertNotContainsWarnings("0:20-0:24:hint:" + Bundle.ERR_MakePrivate());
    }

    @Test
    public void testFixWorking_Protected_Field() throws Exception {
        HintTest.create().setCaretMarker('|').setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    protected String s = null;\n"
                        + "}\n")
                .run(MakePrivate.class)
                .findWarning("2:21-2:22:hint:" + Bundle.ERR_MakePrivate());
    }

    @Test
    public void testFixWorking_Protected_Inner_Class() throws Exception {
        HintTest.create().setCaretMarker('|').setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    protected class Inner {}\n"
                        + "}\n")
                .run(MakePrivate.class)
                .findWarning("2:20-2:25:hint:" + Bundle.ERR_MakePrivate());
    }

    @Test
    public void testFixWorking_Protected_Method() throws Exception {
        HintTest.create().setCaretMarker('|').setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    protected static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakePrivate.class)
                .findWarning("2:26-2:30:hint:" + Bundle.ERR_MakePrivate());
    }

    /**
     * 1:25 modifier protected not allowed here
     *
     * @throws Exception
     */
    @Test(expected = AssertionError.class)
    public void testFixWorking_Protected_TopLevel_Class() throws Exception {
        HintTest.create().setCaretMarker('|').setCaretMarker('|')
                .input("package test; protected class Test {}")
                .run(MakePrivate.class);
    }

    @Test
    public void testFixWorking_Public_Field() throws Exception {
        HintTest.create().setCaretMarker('|').setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public String s = null;\n"
                        + "}\n")
                .run(MakePrivate.class)
                .findWarning("2:18-2:19:hint:" + Bundle.ERR_MakePrivate())
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
        HintTest.create().setCaretMarker('|').setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public class Inner {}\n"
                        + "}\n")
                .run(MakePrivate.class)
                .findWarning("2:17-2:22:hint:" + Bundle.ERR_MakePrivate())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    private class Inner {}\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Public_Method() throws Exception {
        HintTest.create().setCaretMarker('|').setCaretMarker('|')
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakePrivate.class)
                .findWarning("2:16-2:20:hint:" + Bundle.ERR_MakePrivate())
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
        HintTest.create().setCaretMarker('|').setCaretMarker('|')
                .input("package test; public class Test {}")
                .run(MakePrivate.class)
                .assertNotContainsWarnings("0:27-1:31:hint:" + Bundle.ERR_MakePrivate());
    }
}
