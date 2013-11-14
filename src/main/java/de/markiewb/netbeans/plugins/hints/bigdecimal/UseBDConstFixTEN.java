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
    "ERR_UseBDConstFixTEN=Convert to BigDecimal.TEN",
    "DN_UseBigDecimalConstantsTEN=Convert to BigDecimal.TEN",
    "DESC_UseBigDecimalConstantsTEN=Converts expressions like <tt>new java.math.BigDecimal(10)</tt> to <tt>BigDecimal.TEN</tt> ",})
public class UseBDConstFixTEN {

    @Hint(displayName = "#DN_UseBigDecimalConstantsTEN", description = "#DESC_UseBigDecimalConstantsTEN", category = "suggestions", hintKind = Hint.Kind.INSPECTION, severity = Severity.HINT)
    @TriggerPatterns(
            {
                @TriggerPattern("new java.math.BigDecimal(\"10\")"),
                @TriggerPattern("new java.math.BigDecimal(\"10.0\")"),
                @TriggerPattern("new java.math.BigDecimal(\"10.00\")"),
                @TriggerPattern("new java.math.BigDecimal(10)"),
                @TriggerPattern("new java.math.BigDecimal(10L)"),
                @TriggerPattern("new java.math.BigDecimal(10f)"),
                @TriggerPattern("new java.math.BigDecimal(10.0f)"),
                @TriggerPattern("new java.math.BigDecimal(10.00f)"),
                @TriggerPattern("new java.math.BigDecimal(10d)"),
                @TriggerPattern("new java.math.BigDecimal(10.0d)"),
                @TriggerPattern("new java.math.BigDecimal(10.00d)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(10)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(10L)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(10f)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(10.0f)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(10.00f)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(10d)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(10.0d)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(10.00d)"),}
    )
    public static ErrorDescription convert(HintContext ctx) {

        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_UseBDConstFixTEN(), ctx.getPath(), "java.math.BigDecimal.TEN");
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_UseBDConstFixTEN(), fix);
    }

   
}
