package de.markiewb.netbeans.plugins.hints.parentheses;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.ParenthesizedTree;
import com.sun.source.tree.Tree.Kind;
import static com.sun.source.tree.Tree.Kind.*;
import com.sun.source.util.TreePath;
import org.netbeans.api.java.source.TreeMaker;
import org.netbeans.api.java.source.TreePathHandle;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
import org.netbeans.spi.java.hints.ErrorDescriptionFactory;
import org.netbeans.spi.java.hints.Hint;
import org.netbeans.spi.java.hints.HintContext;
import org.netbeans.spi.java.hints.JavaFix;
import org.netbeans.spi.java.hints.TriggerTreeKind;
import org.openide.util.NbBundle;

/**
 * @since 1.6
 * @author markiewb
 */
@NbBundle.Messages({"DN_AddParentheses=Add parentheses",
    "# {0} - text",
    "LABEL_AddParentheses=Add parentheses to {0}",
    "DESC_AddParentheses=Add parentheses<p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",})
public class AddParentheses {

    @TriggerTreeKind({INT_LITERAL, STRING_LITERAL, BOOLEAN_LITERAL, CHAR_LITERAL, DOUBLE_LITERAL, FLOAT_LITERAL, LOGICAL_COMPLEMENT, LONG_LITERAL, CONDITIONAL_EXPRESSION, METHOD_INVOCATION, DIVIDE, MINUS, PLUS, MULTIPLY, XOR})
    @Hint(displayName = "#DN_AddParentheses", description = "#DESC_AddParentheses", category = "suggestions", hintKind = Hint.Kind.ACTION, severity = Severity.HINT)
    public static ErrorDescription remove(HintContext ctx) {
        // FIXME if errors, do not provide hints

        // (42) --do not do-> ((42))
        if (Kind.PARENTHESIZED.equals(ctx.getPath().getParentPath().getLeaf().getKind())) {
            return null;
        }
        ExpressionTree expressionTree = (ExpressionTree) ctx.getPath().getLeaf();
        String expr = expressionTree.toString();

        String label = Bundle.LABEL_AddParentheses(expr);
        Fix fix = new FixImpl(TreePathHandle.create(ctx.getPath(), ctx.getInfo()), label).toEditorFix();
        ErrorDescription forTree = ErrorDescriptionFactory.forTree(ctx, expressionTree, label, fix);
        return forTree;

    }

    static class FixImpl extends JavaFix {

        private final String label;

        FixImpl(TreePathHandle handle, String label) {
            super(handle, label);
            this.label = label;
        }

        @Override
        protected String getText() {

            return label;
        }

        @Override
        protected void performRewrite(JavaFix.TransformationContext ctx) throws Exception {
            TreePath path = ctx.getPath();

            TreeMaker tm = ctx.getWorkingCopy().getTreeMaker();
            // replace add parentheses
            ExpressionTree tree = (ExpressionTree) path.getLeaf();
            ParenthesizedTree parenthesized = tm.Parenthesized(tree);

            ctx.getWorkingCopy().rewrite(tree, parenthesized);
        }
    }
}
