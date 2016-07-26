package de.markiewb.netbeans.plugins.hints.assertfix;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;
import org.openide.filesystems.FileUtil;

public class ConvertToAssertNullTest {

    @Test
    public void testERR_computeAssertNullWithoutMessage() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        org.junit.Assert.assertEquals(null, new Long(0));\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(org.junit.Assert.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(ConvertToAssertTrueFalseNull.class)
                .findWarning("3:25-3:37:hint:" + Bundle.ERR_computeAssertNullWithoutMessage())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import org.junit.Assert;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Assert.assertNull(new Long(0));\n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testERR_computeAssertNullWithMessage() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        org.junit.Assert.assertEquals(\"expected different result\", null, new Long(0));\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(org.junit.Assert.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(ConvertToAssertTrueFalseNull.class)
                .findWarning("3:25-3:37:hint:" + Bundle.ERR_computeAssertNullWithMessage())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import org.junit.Assert;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Assert.assertNull(\"expected different result\", new Long(0));\n"
                        + "    }\n"
                        + "}\n");

    }
}
