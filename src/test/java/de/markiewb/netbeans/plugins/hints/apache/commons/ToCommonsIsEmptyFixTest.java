package de.markiewb.netbeans.plugins.hints.apache.commons;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author markiewb
 */
public class ToCommonsIsEmptyFixTest {

    @Test
    public void testToIsEmpty1() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String s=null;\n"
                        + "        boolean a=s == null || s.length() == 0;\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(StringUtils.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(ToEmptyFix.class)
                .findWarning("4:18-4:46:hint:" + Bundle.ERR_ToCommonsIsEmpty())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import org.apache.commons.lang.StringUtils;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String s=null;\n"
                        + "        boolean a=StringUtils.isEmpty(s);\n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testToIsEmpty2() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String s=null;\n"
                        + "        boolean a=s == null || s.isEmpty();\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(StringUtils.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(ToEmptyFix.class)
                .findWarning("4:18-4:42:hint:" + Bundle.ERR_ToCommonsIsEmpty())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import org.apache.commons.lang.StringUtils;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String s=null;\n"
                        + "        boolean a=StringUtils.isEmpty(s);\n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testToIsEmpty3() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String s=null;\n"
                        + "        boolean a=s != null && s.isEmpty();\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(StringUtils.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(ToEmptyFix.class)
                .findWarning("4:18-4:42:hint:" + Bundle.ERR_ToCommonsIsEmpty())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import org.apache.commons.lang.StringUtils;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String s=null;\n"
                        + "        boolean a=StringUtils.isEmpty(s);\n"
                        + "    }\n"
                        + "}\n");

    }
    
    @Test
    public void testToIsEmpty4() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String s=null;\n"
                        + "        boolean a=s != null && s.length() == 0;\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(StringUtils.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(ToEmptyFix.class)
                .findWarning("4:18-4:46:hint:" + Bundle.ERR_ToCommonsIsEmpty())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import org.apache.commons.lang.StringUtils;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String s=null;\n"
                        + "        boolean a=StringUtils.isEmpty(s);\n"
                        + "    }\n"
                        + "}\n");

    }

}
