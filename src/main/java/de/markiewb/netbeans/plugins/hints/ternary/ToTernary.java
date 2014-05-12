package de.markiewb.netbeans.plugins.hints.ternary;

import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
import org.netbeans.spi.java.hints.ErrorDescriptionFactory;
import org.netbeans.spi.java.hints.Hint;
import org.netbeans.spi.java.hints.HintContext;
import org.netbeans.spi.java.hints.TriggerPattern;
import org.openide.util.NbBundle;

/**
 *
 * @author markiewb
 */
@NbBundle.Messages({
    "DN_ToTernaryReturn=Convert to XXX",
    "DESC_ToTernaryReturn=Converts xxxx. <br>For example: <tt>xxxx</tt> will be transformed to <tt>xxx</tt><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",
    "DN_ToTernaryAssign=Convert to XXX",
    "DESC_ToTernaryAssign=Converts xxxx. <br>For example: <tt>xxxx</tt> will be transformed to <tt>xxx</tt><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",})
public class ToTernary {

    @TriggerPattern(value = "if ($cond$){return $a;}else{return $b;}")
    @Hint(displayName = "#DN_ToTernaryReturn", description = "#DESC_ToTernaryReturn", category = "suggestions", hintKind = Hint.Kind.ACTION, severity = Severity.HINT)
    @NbBundle.Messages("ERR_ToTernaryReturn=XXX")
    public static ErrorDescription toTernaryReturn(HintContext ctx) {
        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_ToTernaryReturn(), ctx.getPath(), "return ($cond$)?$a:$b;");
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_ToTernaryReturn(), fix);
    }

    @TriggerPattern(value = "$type $var;if ($cond$){$var = $a;}else{$var = $b;}")
    @Hint(displayName = "#DN_ToTernaryAssign", description = "#DESC_ToTernaryAssign", category = "suggestions", hintKind = Hint.Kind.ACTION, severity = Severity.HINT)
    @NbBundle.Messages("ERR_ToTernaryAssign=XXX")
    public static ErrorDescription toTernaryAssign(HintContext ctx) {
        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_ToTernaryAssign(), ctx.getPath(), "$type $var = ($cond$)?$a:$b;");
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_ToTernaryAssign(), fix);
    }
}
