package de.markiewb.netbeans.plugins.hints.tochar;

import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.Tree;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
import static org.netbeans.spi.java.hints.ErrorDescriptionFactory.forName;
import org.netbeans.spi.java.hints.Hint;
import org.netbeans.spi.java.hints.HintContext;
import static org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix;
import org.netbeans.spi.java.hints.TriggerTreeKind;
import org.openide.util.NbBundle.Messages;

@Messages({
    "ERR_ToStringFix=Convert char to string",
    "DN_ToString=Convert char to string",
    "DESC_ToString=Converts a char to a string. For example <tt>'c'</tt> will be transformed to <tt>\"c\"</tt>",
    "ERR_ToCharFix=Convert string to char",
    "DN_ToChar=Convert string to char",
    "DESC_ToChar=Converts a single string literal to a char. For example <tt>\"c\"</tt> will be transformed to <tt>'c'</tt>",})
public class ToStringFix {

    @Hint(displayName = "#DN_ToString", description = "#DESC_ToString", category = "suggestions", hintKind = Hint.Kind.ACTION, severity = Severity.HINT)
    @TriggerTreeKind(Tree.Kind.CHAR_LITERAL)
    public static ErrorDescription convertFromCharToString(HintContext ctx) {

        Fix fix = rewriteFix(ctx, Bundle.ERR_ToStringFix(), ctx.getPath(), "\"" + ctx.getPath().getLeaf().toString().substring(1, 2) + "\"");
        return forName(ctx, ctx.getPath(), Bundle.ERR_ToStringFix(), fix);
    }

    @Hint(displayName = "#DN_ToChar", description = "#DESC_ToChar", category = "suggestions", hintKind = Hint.Kind.ACTION, severity = Severity.HINT)
    @TriggerTreeKind(Tree.Kind.STRING_LITERAL)
    public static ErrorDescription convertFromStringToChar(HintContext ctx) {

        LiteralTree lit = (LiteralTree) ctx.getPath().getLeaf();
        String toString = lit.getValue().toString();
        if (toString.length() == 1) {
            Fix fix = rewriteFix(ctx, Bundle.ERR_ToCharFix(), ctx.getPath(), "'" + toString + "'");
            return forName(ctx, ctx.getPath(), Bundle.ERR_ToCharFix(), fix);
        } else {
            return null;
        }
    }

}
