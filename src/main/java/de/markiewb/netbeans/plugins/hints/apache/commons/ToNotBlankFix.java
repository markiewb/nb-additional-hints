package de.markiewb.netbeans.plugins.hints.apache.commons;

import de.markiewb.netbeans.plugins.hints.common.StringUtils;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
import org.netbeans.spi.java.hints.ConstraintVariableType;
import static org.netbeans.spi.java.hints.ErrorDescriptionFactory.forName;
import org.netbeans.spi.java.hints.Hint;
import org.netbeans.spi.java.hints.HintContext;
import static org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix;
import org.netbeans.spi.java.hints.TriggerPattern;
import org.netbeans.spi.java.hints.TriggerPatterns;
import org.openide.util.NbBundle.Messages;

@Messages({
    "ERR_ToCommonsIsNotBlank=Convert to StringUtils.isNotBlank()",
    "DN_ToCommonsIsNotBlank=Convert to StringUtils.isNotBlank()",
    "DESC_ToCommonsIsNotBlank=Converts several patterns to use <tt>org.apache.commons.lang.StringUtils.isNotBlank()</tt>. For example <tt>$v != null && !$v.trim().isEmpty()</tt>.<p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",})
public class ToNotBlankFix {

    @Hint(displayName = "#DN_ToCommonsIsNotBlank", description = "#DESC_ToCommonsIsNotBlank", category = "suggestions", hintKind = Hint.Kind.INSPECTION, severity = Severity.HINT)
    @TriggerPatterns(
            {
                @TriggerPattern(value = "$v != null && $v.trim().length() > 0", constraints = @ConstraintVariableType(variable = "$v", type = "java.lang.String")),
                @TriggerPattern(value = "$v != null && !$v.trim().isEmpty()", constraints = @ConstraintVariableType(variable = "$v", type = "java.lang.String")),
            }
    )
    public static ErrorDescription hint(HintContext ctx) {

        Fix fix = rewriteFix(ctx, Bundle.ERR_ToCommonsIsNotBlank(), ctx.getPath(), "org.apache.commons.lang.StringUtils.isNotBlank($v)");
        return forName(ctx, ctx.getPath(), Bundle.ERR_ToCommonsIsNotBlank(), fix);
    }

}
