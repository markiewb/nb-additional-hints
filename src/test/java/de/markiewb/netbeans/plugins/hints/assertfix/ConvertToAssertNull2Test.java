package de.markiewb.netbeans.plugins.hints.assertfix;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;
import org.openide.filesystems.FileUtil;

public class ConvertToAssertNull2Test {

    @Test
    public void testERR_computeAssertNullWithoutMessage() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        junit.framework.Assert.assertEquals(null, new Long(0));\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(junit.framework.Assert.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(ConvertToAssertTrueFalseNull.class)
                .findWarning("3:31-3:43:hint:" + Bundle.ERR_computeAssertNullWithoutMessage2())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import junit.framework.Assert;\n"
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
                        + "        junit.framework.Assert.assertEquals(\"expected different result\", null, new Long(0));\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(junit.framework.Assert.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(ConvertToAssertTrueFalseNull.class)
                .findWarning("3:31-3:43:hint:" + Bundle.ERR_computeAssertNullWithMessage2())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import junit.framework.Assert;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Assert.assertNull(\"expected different result\", new Long(0));\n"
                        + "    }\n"
                        + "}\n");

    }
}
