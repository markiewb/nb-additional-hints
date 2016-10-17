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
                .assertWarnings();

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
                .assertWarnings();

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
                .assertWarnings();

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
                .assertWarnings();

    }
}
