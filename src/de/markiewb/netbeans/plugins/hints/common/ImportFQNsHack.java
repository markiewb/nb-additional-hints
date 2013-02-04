/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.common;

import com.sun.source.tree.MemberSelectTree;
import com.sun.source.util.TreePathScanner;
import javax.lang.model.element.Element;
import org.netbeans.api.java.source.WorkingCopy;

/**
 *
 * @author Bender
 */
public final class ImportFQNsHack extends TreePathScanner<Void, Void> {
    private WorkingCopy wc;

    public ImportFQNsHack(WorkingCopy wc) {
	this.wc = wc;
    }

    @Override
    public Void visitMemberSelect(MemberSelectTree node, Void p) {
	Element e = wc.getTrees().getElement(getCurrentPath());
	if (e != null && (e.getKind().isClass() || e.getKind().isInterface())) {
	    wc.rewrite(node, wc.getTreeMaker().QualIdent(e));
	    return null;
	} else {
	    return super.visitMemberSelect(node, p);
	}
    }
    
}
