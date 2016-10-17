/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2014 Sun Microsystems, Inc.
 * Portions Copyrighted 2014 benno.markiewicz@googlemail.com
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
    "DN_SimplifyAssertMethods=Convert to assertTrue/assertFalse/assertNull",
    "DESC_SimplifyAssertMethods=Converts <tt>assertEquals</tt> expressions to their <tt>assertTrue/assertFalse/assertNull</tt> counterparts. <br>For example: <tt>assertEquals($msg, true, $var)</tt> will be transformed to <tt>assertTrue($msg, $var)</tt><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",})
@Hint(displayName = "#DN_SimplifyAssertMethods", description = "#DESC_SimplifyAssertMethods", category = "testing", hintKind = Hint.Kind.INSPECTION, severity = Severity.HINT)
public class ConvertToAssertTrueFalseNull {

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

    @TriggerPattern(value = "org.junit.Assert.assertEquals(null, $var)")
    @Messages("ERR_computeAssertNullWithoutMessage=Replace with assertNull")
    public static ErrorDescription computeAssertNullWithoutMessage(HintContext ctx) {
        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_computeAssertNullWithoutMessage(), ctx.getPath(), "org.junit.Assert.assertNull($var)");
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_computeAssertNullWithoutMessage(), fix);
    }

    @TriggerPattern(value = "org.junit.Assert.assertEquals($msg, null, $var)", constraints = {
        @ConstraintVariableType(variable = "$msg", type = "java.lang.String")
    })
    @Messages("ERR_computeAssertNullWithMessage=Replace with assertNull")
    public static ErrorDescription computeAssertNullWithMessage(HintContext ctx) {
        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_computeAssertNullWithMessage(), ctx.getPath(), "org.junit.Assert.assertNull($msg, $var)");
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_computeAssertNullWithMessage(), fix);
    }

}
