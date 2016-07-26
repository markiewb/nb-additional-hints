package de.markiewb.netbeans.plugins.hints.parentheses;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.ParenthesizedTree;
import com.sun.source.tree.Tree.Kind;
import com.sun.source.util.TreePath;
import java.util.EnumSet;
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
@NbBundle.Messages({"DN_RemoveParentheses=Remove parentheses",
    "# {0} - text",
    "LABEL_RemoveParentheses=Remove parentheses from ({0})",
    "DESC_RemoveParentheses=Remove parentheses<p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",})
public class RemoveParentheses {

    @TriggerTreeKind(Kind.PARENTHESIZED)
    @Hint(displayName = "#DN_RemoveParentheses", description = "#DESC_RemoveParentheses", category = "suggestions", hintKind = Hint.Kind.ACTION, severity = Severity.HINT)
    public static ErrorDescription remove(HintContext ctx) {
        // FIXME if errors, do not provide hints
        Kind kind = ctx.getPath().getParentPath().getLeaf().getKind();
        if (EnumSet.of(Kind.MULTIPLY, Kind.DIVIDE).contains(kind)) {
            /**
             * Don't support
             *
             * <pre>
             * int t = (34 * (78 + 48));
             * int u = (34 / (78 + 48));
             * </pre>
             */
            return null;
        }
        ParenthesizedTree parenTree = (ParenthesizedTree) ctx.getPath().getLeaf();
        ExpressionTree innerTree = parenTree.getExpression();
        String expr = innerTree.toString();
        String label = Bundle.LABEL_RemoveParentheses(expr);
        // remove ()
        // replace parent with current
        Fix fix = new FixImpl(TreePathHandle.create(ctx.getPath(), ctx.getInfo()), label).toEditorFix();
        ErrorDescription forTree = ErrorDescriptionFactory.forTree(ctx, innerTree, label, fix);
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

            // replace () with its content
            ParenthesizedTree parenTree = (ParenthesizedTree) path.getLeaf();
            ExpressionTree innerTree = parenTree.getExpression();

            ctx.getWorkingCopy().rewrite(parenTree, innerTree);
        }
    }
}
