package de.markiewb.netbeans.plugins.hints.assertfix;

import org.junit.Test;
import static org.junit.Assert.*;
import org.netbeans.modules.java.hints.test.api.HintTest;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author markiewb
 */
public class UpgradeToOJAssertTest {



    @Test
    public void testAssertEquals_StaticStarImport() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "import static junit.framework.Assert.*;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        assertEquals(false, false);\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(junit.framework.Assert.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(UpgradeToOJAssert.class)
                .findWarning("1:0-1:39:warning:" + Bundle.ERR_UpgradeToOJAssert())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import static org.junit.Assert.*;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        assertEquals(false, false);\n"
                        + "    }\n"
                        + "}\n");

    }

    @Test
    public void testAssertEquals_StaticImport() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "import static junit.framework.Assert.assertEquals;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        assertEquals(false, false);\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(junit.framework.Assert.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(UpgradeToOJAssert.class)
                .findWarning("1:0-1:50:warning:" + Bundle.ERR_UpgradeToOJAssert())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import static org.junit.Assert.assertEquals;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        assertEquals(false, false);\n"
                        + "    }\n"
                        + "}\n");

    }

    @Test
    public void testAssertEquals_Import() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "import junit.framework.Assert;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Assert.assertEquals(false, false);\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(junit.framework.Assert.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(UpgradeToOJAssert.class)
                .findWarning("1:0-1:30:warning:" + Bundle.ERR_UpgradeToOJAssert())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import org.junit.Assert;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Assert.assertEquals(false, false);\n"
                        + "    }\n"
                        + "}\n");

    }

    @Test
    public void testAssertEquals_FQNImport() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        junit.framework.Assert.assertEquals(false, false);\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(junit.framework.Assert.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(UpgradeToOJAssert.class)
                .findWarning("3:8-3:57:warning:" + Bundle.ERR_UpgradeToOJAssert())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import org.junit.Assert;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Assert.assertEquals(false, false);\n"
                        + "    }\n"
                        + "}\n");

    }


}
