package de.markiewb.netbeans.plugins.hints.apache.commons;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author markiewb
 */
public class ToCommonsIsNotBlankFixTest {

    @Test
    public void testToIsNotBlank1() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String s=null;\n"
                        + "        boolean a=s != null && s.trim().length() > 0;\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(StringUtils.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(ToNotBlankFix.class)
                .findWarning("4:18-4:52:hint:" + Bundle.ERR_ToCommonsIsNotBlank())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import org.apache.commons.lang.StringUtils;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String s=null;\n"
                        + "        boolean a=StringUtils.isNotBlank(s);\n"
                        + "    }\n"
                        + "}\n");

    }

    @Test
    public void testToIsNotBlank2() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String s=null;\n"
                        + "        boolean a=s != null && !s.trim().isEmpty();\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(StringUtils.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(ToNotBlankFix.class)
                .findWarning("4:18-4:50:hint:" + Bundle.ERR_ToCommonsIsNotBlank())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import org.apache.commons.lang.StringUtils;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String s=null;\n"
                        + "        boolean a=StringUtils.isNotBlank(s);\n"
                        + "    }\n"
                        + "}\n");

    }
}
