/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.ternary;

import de.markiewb.netbeans.plugins.hints.apache.commons.ToBlankFix;
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
public class ToTernaryTest {

    @Test
    public void testToTernaryAssign() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        int s; if (true){s = 1;}else{s = 0;};\n"
                        + "    }\n"
                        + "}\n")
                .run(ToTernary.class)
                .findWarning("4:18-4:53:hint:" + Bundle.ERR_ToTernaryAssign())
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String s=null;\n"
                        + "        int s = (true)?1:0;\n"
                        + "    }\n"
                        + "}\n");

    }

}
