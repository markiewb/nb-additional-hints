/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.apache.commons;

import de.markiewb.netbeans.plugins.hints.tochar.ToStringFix;
import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.netbeans.modules.java.hints.test.api.HintTest;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.java.hints.HintContext;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author markiewb
 */
public class ToCommonsIsBlankFixTest {

    @Test
    public void testToIsBlank1() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String s=null;\n"
                        + "        boolean a=s == null || s.trim().length() == 0;\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(StringUtils.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(ToBlankFix.class)
                .findWarning("4:18-4:53:hint:" + Bundle.ERR_ToCommonsIsBlank())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import org.apache.commons.lang.StringUtils;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String s=null;\n"
                        + "        boolean a=StringUtils.isBlank(s);\n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testToIsBlank2() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String s=null;\n"
                        + "        boolean a=s == null || s.trim().isEmpty();\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(StringUtils.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(ToBlankFix.class)
                .findWarning("4:18-4:49:hint:" + Bundle.ERR_ToCommonsIsBlank())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import org.apache.commons.lang.StringUtils;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String s=null;\n"
                        + "        boolean a=StringUtils.isBlank(s);\n"
                        + "    }\n"
                        + "}\n");

    }
    @Test
    public void testToIsBlank3() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String s=null;\n"
                        + "        boolean a=s != null && s.trim().length() == 0;\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(StringUtils.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(ToBlankFix.class)
                .findWarning("4:18-4:53:hint:" + Bundle.ERR_ToCommonsIsBlank())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import org.apache.commons.lang.StringUtils;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String s=null;\n"
                        + "        boolean a=StringUtils.isBlank(s);\n"
                        + "    }\n"
                        + "}\n");

    }
    
    @Test
    public void testToIsBlank4() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String s=null;\n"
                        + "        boolean a=s != null && s.trim().isEmpty();\n"
                        + "    }\n"
                        + "}\n")
                .classpath(FileUtil.getArchiveRoot(StringUtils.class.getProtectionDomain().getCodeSource().getLocation()))
                .run(ToBlankFix.class)
                .findWarning("4:18-4:49:hint:" + Bundle.ERR_ToCommonsIsBlank())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "import org.apache.commons.lang.StringUtils;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String s=null;\n"
                        + "        boolean a=StringUtils.isBlank(s);\n"
                        + "    }\n"
                        + "}\n");

    }

}
