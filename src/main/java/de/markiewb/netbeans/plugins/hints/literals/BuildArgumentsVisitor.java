/* 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2014 Sun Microsystems, Inc.
 * Portions Copyrighted 2014 benno.markiewicz@googlemail.com
 */
package de.markiewb.netbeans.plugins.hints.literals;

import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.SourcePositions;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import de.markiewb.netbeans.plugins.hints.common.StringUtils;
import static de.markiewb.netbeans.plugins.hints.common.StringUtils.escapeQuotes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import org.netbeans.api.java.source.CompilationInfo;

/**
 *
 * @author markiewb
 */
public final class BuildArgumentsVisitor extends TreePathScanner<Void, Object> {

    private CompilationInfo info;

    //    private int argIndex;
    public Result toResult() {
	return result;
    }
    private Result result = new Result();

    public BuildArgumentsVisitor(CompilationInfo info) {
	this.info = info;
    }

    @Override
    public Void visitBinary(BinaryTree tree, Object p) {
	handleTree(new TreePath(getCurrentPath(), tree.getLeftOperand()));
	handleTree(new TreePath(getCurrentPath(), tree.getRightOperand()));
	return null;
    }

    /**
     * Represents the token of the source code and its kind.
     */
    public static class TokenPair {

	private boolean isArgument;
	private String text;

	public static TokenPair createArgument(String text) {
	    return new TokenPair(true, text);
	}

	public static TokenPair createLiteral(String text) {
	    return new TokenPair(false, text);
	}

	public boolean isIsArgument() {
	    return isArgument;
	}

	public String getText() {
	    return text;
	}

	private TokenPair(boolean isArgument, String text) {
	    this.isArgument = isArgument;
	    this.text = text;
	}
    }

    public static final class Result {

	private final List<TokenPair> collection = new ArrayList<TokenPair>();

	public List<TokenPair> get() {
	    return collection;
	}

	void add(TokenPair item) {
	    collection.add(item);
	}

	public boolean hasOnlyNonLiterals() {
	    for (TokenPair pair : collection) {
		if (!pair.isArgument) {
		    return false;
		}
	    }
	    return true;
	}

	public List<String> getArguments() {
	    List<String> list = new ArrayList<String>();


	    for (TokenPair item : collection) {
		if (item.isIsArgument()) {
		    list.add(item.getText());
		}
	    }
	    return list;
	}
    }

    @Override
    public Void visitLiteral(LiteralTree tree, Object p) {
	String value = tree.getValue().
		toString();
	if (!value.isEmpty()) {
	    result.add(TokenPair.createLiteral(escapeQuotes(value)));
	}
	return null;
    }
    private static final EnumSet<Tree.Kind> TREEKINDS = EnumSet.of(Tree.Kind.STRING_LITERAL, Tree.Kind.PLUS);

    public void handleTree(TreePath tp) {
	Tree tree = tp.getLeaf();
	if (TREEKINDS.contains(tree.getKind())) {
	    scan(tree, null);
	    return;
	}
	SourcePositions pos = info.getTrees().
		getSourcePositions();
	int start = (int) pos.getStartPosition(tp.getCompilationUnit(), tree);
	int end = (int) pos.getEndPosition(tp.getCompilationUnit(), tree);
	final String text = info.getText().
		substring(start, end);
	result.add(TokenPair.createArgument(text));
    }
}
