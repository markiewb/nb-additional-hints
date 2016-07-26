/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.assertfix;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;
import org.openide.filesystems.FileUtil;

public class ConvertToAssertTrueFalse2Test {

    @Test
    public void testERR_computeAssertTrueWithoutMessage2() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        junit.framework.Assert.assertEquals(true, false);\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(junit.framework.Assert.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(ConvertToAssertTrueFalseNull.class)
                .findWarning("3:31-3:43:hint:" + Bundle.ERR_computeAssertTrueWithoutMessage2())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import junit.framework.Assert;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Assert.assertTrue(false);\n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testERR_computeAssertTrueWithMessage2() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        junit.framework.Assert.assertEquals(\"expected different result\", true, false);\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(junit.framework.Assert.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(ConvertToAssertTrueFalseNull.class)
                .findWarning("3:31-3:43:hint:" + Bundle.ERR_computeAssertTrueWithMessage2())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import junit.framework.Assert;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Assert.assertTrue(\"expected different result\", false);\n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testERR_computeAssertFalseWithoutMessage2() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        junit.framework.Assert.assertEquals(false, false);\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(junit.framework.Assert.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(ConvertToAssertTrueFalseNull.class)
                .findWarning("3:31-3:43:hint:" + Bundle.ERR_computeAssertFalseWithoutMessage2())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import junit.framework.Assert;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Assert.assertFalse(false);\n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testERR_computeAssertFalseWithMessage2() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        junit.framework.Assert.assertEquals(\"expected different result\", false, true);\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(junit.framework.Assert.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(ConvertToAssertTrueFalseNull.class)
                .findWarning("3:31-3:43:hint:" + Bundle.ERR_computeAssertFalseWithMessage2())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import junit.framework.Assert;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Assert.assertFalse(\"expected different result\", true);\n"
                        + "    }\n"
                        + "}\n");

    }
}
