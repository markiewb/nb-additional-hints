package de.markiewb.netbeans.plugins.hints;

import de.markiewb.netbeans.plugins.hints.assertfix.*;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
import org.netbeans.spi.java.hints.ConstraintVariableType;
import org.netbeans.spi.java.hints.ErrorDescriptionFactory;
import org.netbeans.spi.java.hints.Hint;
import org.netbeans.spi.java.hints.HintContext;
import org.netbeans.spi.java.hints.TriggerPattern;
import org.openide.util.NbBundle.Messages;

@Messages({
    "DN_JoinAssignment=Join assignment",
    "DESC_JoinAssignment=Join assignment. <br>For example: <tt>int foo; foo=42;</tt> will be transformed to <tt>int foo = 42;</tt><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",})
@Hint(displayName = "#DN_JoinAssignment", description = "#DESC_JoinAssignment", category = "suggestions", hintKind = Hint.Kind.INSPECTION, severity = Severity.HINT)
public class JoinAssignment {

    @TriggerPattern(value = "$1 $2;$2 = $3;")
    @Messages("ERR_JoinAssignment=Join assignment")
    public static ErrorDescription computeAssertTrueWithoutMessage(HintContext ctx) {
        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_JoinAssignment(), ctx.getPath(), "$1 $2 = $3;");
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_JoinAssignment(), fix);
    }

}
