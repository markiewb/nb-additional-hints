/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.common;

import java.util.ArrayList;
import org.netbeans.spi.editor.hints.Fix;

/**
 *
 * @author Bender
 */
public class NonNullArrayList extends ArrayList<Fix> {

    public NonNullArrayList() {
    }

    @Override
    public boolean add(Fix e) {
	if (null != e) {
	    return super.add(e);
	}
	return false;
    }
    
}
