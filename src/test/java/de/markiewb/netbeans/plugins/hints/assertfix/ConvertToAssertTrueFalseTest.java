/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.assertfix;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;
import org.openide.filesystems.FileUtil;

public class ConvertToAssertTrueFalseTest {

    @Test
    public void testERR_computeAssertTrueWithoutMessage() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        org.junit.Assert.assertEquals(true, false);\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(org.junit.Assert.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(ConvertToAssertTrueFalseNull.class)
                .findWarning("3:25-3:37:hint:" + Bundle.ERR_computeAssertTrueWithoutMessage())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import org.junit.Assert;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Assert.assertTrue(false);\n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testERR_computeAssertTrueWithMessage() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        org.junit.Assert.assertEquals(\"expected different result\", true, false);\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(org.junit.Assert.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(ConvertToAssertTrueFalseNull.class)
                .findWarning("3:25-3:37:hint:" + Bundle.ERR_computeAssertTrueWithMessage())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import org.junit.Assert;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Assert.assertTrue(\"expected different result\", false);\n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testERR_computeAssertFalseWithoutMessage() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        org.junit.Assert.assertEquals(false, false);\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(org.junit.Assert.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(ConvertToAssertTrueFalseNull.class)
                .findWarning("3:25-3:37:hint:" + Bundle.ERR_computeAssertFalseWithoutMessage())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import org.junit.Assert;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Assert.assertFalse(false);\n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testERR_computeAssertFalseWithMessage() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        org.junit.Assert.assertEquals(\"expected different result\", false, true);\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(org.junit.Assert.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(ConvertToAssertTrueFalseNull.class)
                .findWarning("3:25-3:37:hint:" + Bundle.ERR_computeAssertFalseWithMessage())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import org.junit.Assert;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Assert.assertFalse(\"expected different result\", true);\n"
                        + "    }\n"
                        + "}\n");

    }
}
