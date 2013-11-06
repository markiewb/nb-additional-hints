package de.markiewb.netbeans.plugins.hints.tochar;

import com.sun.source.tree.Tree;
import de.markiewb.netbeans.plugins.hints.bigdecimal.*;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
import org.netbeans.spi.java.hints.ErrorDescriptionFactory;
import org.netbeans.spi.java.hints.Hint;
import org.netbeans.spi.java.hints.HintContext;
import org.netbeans.spi.java.hints.TriggerPattern;
import org.netbeans.spi.java.hints.TriggerPatterns;
import org.netbeans.spi.java.hints.TriggerTreeKind;
import org.openide.util.NbBundle.Messages;

@Messages({
    "ERR_ToStringFix=Convert char to string",
    "DN_ToString=Convert char to string",
    "DESC_ToString=Converts a char to a string. For example <tt>'c'</tt> will be transformed to <tt>\"c\"</tt>",})
public class ToStringFix {

    @Hint(displayName = "#DN_ToString", description = "#DESC_ToString", category = "Suggestions", hintKind = Hint.Kind.INSPECTION, severity = Severity.HINT)
    @TriggerTreeKind(Tree.Kind.CHAR_LITERAL)
    public static ErrorDescription convertFromCharToString(HintContext ctx) {

        
        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_ToStringFix(), ctx.getPath(), "\"" + ctx.getPath().toString() + "\"");
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_ToStringFix(), fix);
    }

}
