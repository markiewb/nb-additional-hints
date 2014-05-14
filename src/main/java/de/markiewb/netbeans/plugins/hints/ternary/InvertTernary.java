package de.markiewb.netbeans.plugins.hints.ternary;

import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
import org.netbeans.spi.java.hints.ErrorDescriptionFactory;
import org.netbeans.spi.java.hints.Hint;
import org.netbeans.spi.java.hints.HintContext;
import org.netbeans.spi.java.hints.TriggerPattern;
import org.netbeans.spi.java.hints.TriggerPatterns;
import org.openide.util.NbBundle;

/**
 *
 * @author markiewb
 */
@NbBundle.Messages({
    "DN_InvertTernary=Invert ternary if/else",
    "DESC_InvertTernary=Negates the condition and switches the if/else blocks of a ternary statement. <p>For example: <tt>(a != null) ? \"foo\" : \"bar\"</tt> will be transformed to <tt>(a == null) ? \"bar\" : \"foo\"</tt></p><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",})
public class InvertTernary {

    @TriggerPatterns({
        @TriggerPattern(value = "($var1 != null) ? $a : $b"),
        @TriggerPattern(value = "($var2 == null) ? $a : $b"),
        @TriggerPattern(value = "($var3 > $c) ? $a : $b"),
        @TriggerPattern(value = "($var4 < $c) ? $a : $b"),
        @TriggerPattern(value = "($var5 >= $c) ? $a : $b"),
        @TriggerPattern(value = "($var6 <= $c) ? $a : $b"),
    })
    @Hint(displayName = "#DN_InvertTernary", description = "#DESC_InvertTernary", category = "suggestions", hintKind = Hint.Kind.ACTION, severity = Severity.HINT)
    @NbBundle.Messages("ERR_InvertTernary=Invert ternary if/else")
    public static ErrorDescription toTernary(HintContext ctx) {
        String result = null;
        if (ctx.getVariables().containsKey("$var1")) {
            result = "($var1 == null) ? $b : $a";
        }
        if (ctx.getVariables().containsKey("$var2")) {
            result = "($var2 != null) ? $b : $a";
        }
        if (ctx.getVariables().containsKey("$var3")) {
            result = "($var3 <= $c) ? $b : $a";
        }
        if (ctx.getVariables().containsKey("$var4")) {
            result = "($var4 >= $c) ? $b : $a";
        }
        if (ctx.getVariables().containsKey("$var5")) {
            result = "($var5 < $c) ? $b : $a";
        }
        if (ctx.getVariables().containsKey("$var6")) {
            result = "($var6 > $c) ? $b : $a";
        }
        if (result != null) {
            Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_InvertTernary(), ctx.getPath(), result);
            return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_InvertTernary(), fix);
        }
        return null;
    }

}
