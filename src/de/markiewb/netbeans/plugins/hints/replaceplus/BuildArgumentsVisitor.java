/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.replaceplus;

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

    //    private int argIndex;
    public Result toResult() {
        return result;
    }
    private Result result = new Result();

    BuildArgumentsVisitor(CompilationInfo info) {
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
    static class TokenPair {

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

    public static class Result {

        private final List<TokenPair> collection = new ArrayList<TokenPair>();

        List<TokenPair> get() {
            return collection;
        }

        void add(TokenPair item) {
            collection.add(item);
        }

        boolean hasOnlyNonLiterals() {
            for (TokenPair pair : collection) {
                if (!pair.isArgument) {
                    return false;
                }
            }
            return true;
        }

        List<String> getArguments() {
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
            result.add(TokenPair.createLiteral(value));
        }
        return null;
    }

    public void handleTree(TreePath tp) {
        Tree tree = tp.getLeaf();
        if (ReplacePlusHint.TREEKINDS.contains(tree.getKind())) {
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
