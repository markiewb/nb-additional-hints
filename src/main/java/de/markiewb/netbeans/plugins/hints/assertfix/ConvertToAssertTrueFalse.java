/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.assertfix;

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
    "DN_SimplifyAssertMethods=Convert to assertTrue/assertFalse",
    "DESC_SimplifyAssertMethods=Converts <tt>assertEquals</tt> expressions to their <tt>assertTrue/assertFalse</tt> counterparts. <br>For example: <tt>org.junit.Assert.assertEquals($msg, true, $var)</tt> will be transformed to <tt>org.junit.Assert.assertTrue($msg, $var)</tt><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",
})
@Hint(displayName = "#DN_SimplifyAssertMethods", description = "#DESC_SimplifyAssertMethods", category = "testing", hintKind = Hint.Kind.INSPECTION, severity = Severity.HINT)
public class ConvertToAssertTrueFalse {

    @TriggerPattern(value = "org.junit.Assert.assertEquals(true, $var)")
    @Messages("ERR_computeAssertTrueWithoutMessage=Replace with assertTrue")
    public static ErrorDescription computeAssertTrueWithoutMessage(HintContext ctx) {
        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_computeAssertTrueWithoutMessage(), ctx.getPath(), "org.junit.Assert.assertTrue($var)");
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_computeAssertTrueWithoutMessage(), fix);
    }

    @TriggerPattern(value = "org.junit.Assert.assertEquals(false, $var)")
    @Messages("ERR_computeAssertFalseWithoutMessage=Replace with assertFalse")
    public static ErrorDescription computeAssertFalseWithoutMessage(HintContext ctx) {
        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_computeAssertFalseWithoutMessage(), ctx.getPath(), "org.junit.Assert.assertFalse($var)");
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_computeAssertFalseWithoutMessage(), fix);
    }

    @TriggerPattern(value = "org.junit.Assert.assertEquals($msg, true, $var)", constraints = {
        @ConstraintVariableType(variable = "$msg", type = "java.lang.String")
    })
    @Messages("ERR_computeAssertTrueWithMessage=Replace with assertTrue")
    public static ErrorDescription computeAssertTrueWithMessage(HintContext ctx) {
        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_computeAssertTrueWithMessage(), ctx.getPath(), "org.junit.Assert.assertTrue($msg, $var)");
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_computeAssertTrueWithMessage(), fix);
    }

    @TriggerPattern(value = "org.junit.Assert.assertEquals($msg, false, $var)", constraints = {
        @ConstraintVariableType(variable = "$msg", type = "java.lang.String")
    })
    @Messages("ERR_computeAssertFalseWithMessage=Replace with assertFalse")
    public static ErrorDescription computeAssertFalseWithMessage(HintContext ctx) {
        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_computeAssertFalseWithMessage(), ctx.getPath(), "org.junit.Assert.assertFalse($msg, $var)");
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_computeAssertFalseWithMessage(), fix);
    }
    
    @TriggerPattern(value = "junit.framework.Assert.assertEquals(true, $var)")
    @Messages("ERR_computeAssertTrueWithoutMessage2=Replace with assertTrue")
    public static ErrorDescription computeAssertTrueWithoutMessage2(HintContext ctx) {
        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_computeAssertTrueWithoutMessage2(), ctx.getPath(), "junit.framework.Assert.assertTrue($var)");
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_computeAssertTrueWithoutMessage2(), fix);
    }

    @TriggerPattern(value = "junit.framework.Assert.assertEquals(false, $var)")
    @Messages("ERR_computeAssertFalseWithoutMessage2=Replace with assertFalse")
    public static ErrorDescription computeAssertFalseWithoutMessage2(HintContext ctx) {
        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_computeAssertFalseWithoutMessage2(), ctx.getPath(), "junit.framework.Assert.assertFalse($var)");
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_computeAssertFalseWithoutMessage2(), fix);
    }

    @TriggerPattern(value = "junit.framework.Assert.assertEquals($msg, true, $var)", constraints = {
        @ConstraintVariableType(variable = "$msg", type = "java.lang.String")
    })
    @Messages("ERR_computeAssertTrueWithMessage2=Replace with assertTrue")
    public static ErrorDescription computeAssertTrueWithMessage2(HintContext ctx) {
        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_computeAssertTrueWithMessage2(), ctx.getPath(), "junit.framework.Assert.assertTrue($msg, $var)");
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_computeAssertTrueWithMessage2(), fix);
    }

    @TriggerPattern(value = "junit.framework.Assert.assertEquals($msg, false, $var)", constraints = {
        @ConstraintVariableType(variable = "$msg", type = "java.lang.String")
    })
    @Messages("ERR_computeAssertFalseWithMessage2=Replace with assertFalse")
    public static ErrorDescription computeAssertFalseWithMessage2(HintContext ctx) {
        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_computeAssertFalseWithMessage2(), ctx.getPath(), "junit.framework.Assert.assertFalse($msg, $var)");
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_computeAssertFalseWithMessage2(), fix);
    }
}
