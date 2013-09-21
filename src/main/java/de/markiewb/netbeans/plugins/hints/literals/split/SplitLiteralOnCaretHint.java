package de.markiewb.netbeans.plugins.hints.literals.split;

import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.SourcePositions;
import com.sun.source.util.TreePath;
import org.netbeans.api.java.source.CompilationInfo;
import org.netbeans.api.java.source.TreeMaker;
import org.netbeans.api.java.source.WorkingCopy;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
import org.netbeans.spi.java.hints.ConstraintVariableType;
import org.netbeans.spi.java.hints.ErrorDescriptionFactory;
import org.netbeans.spi.java.hints.Hint;
import org.netbeans.spi.java.hints.HintContext;
import org.netbeans.spi.java.hints.JavaFix;
import org.netbeans.spi.java.hints.TriggerPattern;
import org.netbeans.spi.java.hints.TriggerTreeKind;
import org.openide.util.NbBundle.Messages;

@Hint(displayName = "#DN_SplitLiteralOnCaretHint", description = "#DESC_SplitLiteralOnCaretHint", category = "suggestions", hintKind = Hint.Kind.ACTION, severity = Severity.HINT)
@Messages({
    "DN_SplitLiteralOnCaretHint=Split at caret",
    "DESC_SplitLiteralOnCaretHint=Splits the literal at the current caret.<p>For example: <code>\"Foo|Bar\"</code> will be transformed into <code>\"Foo\" + \"Bar\"</code></p>"
}
)
public class SplitLiteralOnCaretHint {

    @TriggerTreeKind(Tree.Kind.STRING_LITERAL)
    @Messages("ERR_SplitLiteralOnCaretHint=Split at caret")
    public static ErrorDescription computeWarning(HintContext ctx) {
        CompilationInfo wc = ctx.getInfo();
        final SourcePositions scp = wc.getTrees().getSourcePositions();
        int posStart = (int) scp.getStartPosition(wc.getCompilationUnit(), ctx.getPath().getLeaf());
        int posEnd = (int) scp.getEndPosition(wc.getCompilationUnit(), ctx.getPath().getLeaf());
        final int caretLocation = ctx.getCaretLocation();

        //don't support empty string
        //don't support caret at start
        //don't support caret at end
        if (posStart + 1 < caretLocation && caretLocation < posEnd - 1) {
            Fix fix = new FixImpl(ctx.getInfo(), ctx.getPath(), caretLocation).toEditorFix();
            return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_SplitLiteralOnCaretHint(), fix);
        } else {
            return null;
        }
    }

    private static final class FixImpl extends JavaFix {

        private int caretLocation = -1;

        public FixImpl(CompilationInfo info, TreePath tp) {
            super(info, tp);
        }

        private FixImpl(CompilationInfo info, TreePath path, int caretLocation) {
            super(info, path);
            this.caretLocation = caretLocation;
        }

        @Override
        @Messages("FIX_SplitLiteralOnCaretHint=Split at caret")
        protected String getText() {
            return Bundle.FIX_SplitLiteralOnCaretHint();
        }

        @Override
        protected void performRewrite(TransformationContext ctx) {
            LiteralTree literal = (LiteralTree) ctx.getPath().getLeaf();
            WorkingCopy wc = ctx.getWorkingCopy();
            final SourcePositions scp = wc.getTrees().
                    getSourcePositions();

            int posStart = (int) scp.getStartPosition(wc.getCompilationUnit(), ctx.getPath().getLeaf());
            int posEnd = (int) scp.getEndPosition(wc.getCompilationUnit(), ctx.getPath().getLeaf());
            //split the text "foo|bar"
            String prefix = wc.getText().substring(posStart + 1, caretLocation);
            String postfix = wc.getText().substring(caretLocation, posEnd - 1);

            //create "foo"+"bar"
            final TreeMaker tm = wc.getTreeMaker();
            BinaryTree newLiteral = tm.Binary(Tree.Kind.PLUS, tm.Literal(prefix), tm.Literal(postfix));

            wc.rewrite(literal, newLiteral);
        }
    }
}
