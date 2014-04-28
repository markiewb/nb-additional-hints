/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.markiewb.netbeans.plugins.hints.arrays;

import de.markiewb.netbeans.plugins.hints.tochar.ToStringFix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.netbeans.modules.java.hints.test.api.HintTest;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.java.hints.HintContext;

/**
 *
 * @author markiewb
 */
public class NewArrayFixTest {

    @Test
    public void testToZeroArray() throws Exception {
        HintTest.create()
                .input("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String[] a=new String[] {};\n"
                        + "    }\n"
                        + "}\n")
                .run(NewArrayFix.class)
                .findWarning("3:23-3:26:hint:" + Bundle.ERR_ToArrayZero("String"))
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n"
                        + "public class Test {\n"
                        + "    public static void main(String[] args) {\n"
                        + "        String[] a=new String[0];\n"
                        + "    }\n"
                        + "}\n");

    }

    /**
     * Test of convertFromStringToArrayEmpty method, of class NewArrayFix.
     */
    @Test
    public void testConvertFromStringToArrayEmpty() {
        System.out.println("convertFromStringToArrayEmpty");
        HintContext ctx = null;
        ErrorDescription expResult = null;
        ErrorDescription result = NewArrayFix.convertFromStringToArrayEmpty(ctx);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
