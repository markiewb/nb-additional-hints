/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints;

import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.SourcePositions;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.java.source.CompilationInfo;

/**
 *
 * @author Bender
 */
class BuildArgumentsVisitor extends TreePathScanner<Void, Object> {
    private CompilationInfo info;
    StringBuffer format = new StringBuffer();
    List<String> arguments = new ArrayList<String>();
    boolean wasLiteral;
    private int argIndex;

    BuildArgumentsVisitor(CompilationInfo info) {
	this.info = info;
    }

    @Override
    public Void visitBinary(BinaryTree tree, Object p) {
	handleTree(new TreePath(getCurrentPath(), tree.getLeftOperand()));
	handleTree(new TreePath(getCurrentPath(), tree.getRightOperand()));
	return null;
    }

    @Override
    public Void visitLiteral(LiteralTree tree, Object p) {
	String value = tree.getValue().toString();
	if (!value.isEmpty()) {
	    wasLiteral = true;
	    format.append(value);
	}
	return null;
    }

    public void handleTree(TreePath tp) {
	Tree tree = tp.getLeaf();
	if (I18NChecker.TREEKINDS.contains(tree.getKind())) {
	    scan(tree, null);
	    return;
	}
	//create placeholder in format string - like {0}
	format.append("{").append(argIndex++).append("}");
	//use original values as arguments
	SourcePositions pos = info.getTrees().getSourcePositions();
	int start = (int) pos.getStartPosition(tp.getCompilationUnit(), tree);
	int end = (int) pos.getEndPosition(tp.getCompilationUnit(), tree);
	arguments.add(info.getText().substring(start, end));
    }
    
}
