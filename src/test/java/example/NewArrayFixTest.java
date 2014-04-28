/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package example;

import de.markiewb.netbeans.plugins.hints.arrays.*;
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

    public static void main(String[] args) {
        String[] a= new String[] {};
        String[] b=new String[0];
    }

    
}
