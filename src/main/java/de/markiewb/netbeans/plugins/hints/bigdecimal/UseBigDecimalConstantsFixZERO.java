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
    "ERR_UseBigDecimalConstantsFixZERO=Convert to <tt>BigDecimal.ZERO</tt>",
    "DN_UseBigDecimalConstantsZERO=Convert to <tt>BigDecimal.ZERO</tt>",
    "DESC_UseBigDecimalConstantsZERO=Converts expressions like <tt>new java.math.BigDecimal(0)</tt> to use <tt>BigDecimal.ZERO</tt> ",})
public class UseBigDecimalConstantsFixZERO {

    @Hint(displayName = "#DN_UseBigDecimalConstantsZERO", description = "#DESC_UseBigDecimalConstantsZERO", category = "Suggestions", hintKind = Hint.Kind.INSPECTION, severity = Severity.HINT)
    @TriggerPatterns(
            {
                @TriggerPattern("new java.math.BigDecimal(\"0\")"),
                @TriggerPattern("new java.math.BigDecimal(\"0.0\")"),
                @TriggerPattern("new java.math.BigDecimal(\"0.00\")"),
                @TriggerPattern("new java.math.BigDecimal(0)"),
                @TriggerPattern("new java.math.BigDecimal(0L)"),
                @TriggerPattern("new java.math.BigDecimal(0f)"),
                @TriggerPattern("new java.math.BigDecimal(0.0f)"),
                @TriggerPattern("new java.math.BigDecimal(0.00f)"),
                @TriggerPattern("new java.math.BigDecimal(0d)"),
                @TriggerPattern("new java.math.BigDecimal(0.0d)"),
                @TriggerPattern("new java.math.BigDecimal(0.00d)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(0)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(0L)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(0f)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(0.0f)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(0.00f)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(0d)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(0.0d)"),
                @TriggerPattern("java.math.BigDecimal.valueOf(0.00d)"),}
    )
    public static ErrorDescription convertToZero(HintContext ctx) {

        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_UseBigDecimalConstantsFixZERO(), ctx.getPath(), "java.math.BigDecimal.ZERO");
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_UseBigDecimalConstantsFixZERO(), fix);
    }

}
