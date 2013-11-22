package de.markiewb.netbeans.plugins.hints.modifiers;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

public class MakePrivateTest {

    @Test
    public void testFixWorking_Protected_Method() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    protected static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakePrivate.class)
                .findWarning("2:26-2:30:hint:" + Bundle.ERR_MakePrivate())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    private static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Public_Method() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "    }\n"
                        + "}\n")
                .run(MakePrivate.class)
                .findWarning("2:23-2:27:hint:" + Bundle.ERR_MakePrivate())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    private static void main(String[] args) {\n"
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
                .run(MakePrivate.class)
                .findWarning("2:21-2:22:hint:" + Bundle.ERR_MakePrivate())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    private String s = null;\n"
                        + "}\n");
    }

    @Test
    public void testFixWorking_Public_Field() throws Exception {
        HintTest.create()
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
                        + "}\n");
    }

    @Test
    public void testFixWorking_Public_InnerClass() throws Exception {
        HintTest.create()
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
}
