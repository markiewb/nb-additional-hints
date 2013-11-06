package de.markiewb.netbeans.plugins.hints.bigdecimal;

import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
import org.netbeans.spi.java.hints.ErrorDescriptionFactory;
import org.netbeans.spi.java.hints.Hint;
import org.netbeans.spi.java.hints.HintContext;
import org.netbeans.spi.java.hints.TriggerPattern;
import org.netbeans.spi.java.hints.TriggerPatterns;
import org.openide.util.NbBundle.Messages;

@Messages({
    "ERR_UseBigDecimalConstantsFixONE=Convert to <tt>BigDecimal.ONE</tt>",
    "DN_UseBigDecimalConstantsONE=Convert to <tt>BigDecimal.ONE</tt>",
    "DESC_UseBigDecimalConstantsONE=Converts expressions like <tt>new java.math.BigDecimal(1)</tt> to <tt>BigDecimal.ONE</tt> ",})
public class UseBigDecimalConstantsFixONE {

    @Hint(displayName = "#DN_UseBigDecimalConstantsONE", description = "#DESC_UseBigDecimalConstantsONE", category = "Suggestions", hintKind = Hint.Kind.INSPECTION, severity = Severity.HINT)
    @TriggerPatterns(
            {
                @TriggerPattern("new java.math.BigDecimal(\"1\")"),
                @TriggerPattern("new java.math.BigDecimal(\"1.0\")"),
                @TriggerPattern("new java.math.BigDecimal(\"1.00\")"),
                @TriggerPattern("new java.math.BigDecimal(1)"),
                @TriggerPattern("new java.math.BigDecimal(1L)"),
                @TriggerPattern("new java.math.BigDecimal(1f)"),
                @TriggerPattern("new java.math.BigDecimal(1.0f)"),
                @TriggerPattern("new java.math.BigDecimal(1.00f)"),
                @TriggerPattern("new java.math.BigDecimal(1d)"),
                @TriggerPattern("new java.math.BigDecimal(1.0d)"),
                @TriggerPattern("new java.math.BigDecimal(1.00d)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(1)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(1L)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(1f)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(1.0f)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(1.00f)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(1d)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(1.0d)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(1.00d)"),}
    )
    public static ErrorDescription convertToZero(HintContext ctx) {

        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_UseBigDecimalConstantsFixONE(), ctx.getPath(), "java.math.BigDecimal.ONE");
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_UseBigDecimalConstantsFixONE(), fix);
    }

   
}
