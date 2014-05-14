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
    "DN_ToTernaryReturn=Convert to ternary return",
    "DESC_ToTernaryReturn=Converts if statement to ternary return statement. <p>For example: <tt>if ($cond) {return $a;} else {return $b;}</tt> will be transformed to <tt>return ($cond) ? $a : $b;</tt></p><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",
    "DN_ToIfElseReturn=Convert to if/else return",
    "DESC_ToIfElseReturn=Converts ternary return statement to if/else statement. <p>For example: <tt>return ($cond) ? $a : $b;</tt> will be transformed to <tt>if ($cond) {return $a;} else {return $b;}</tt></p><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",
    "DN_ToTernaryAssign=Convert to ternary assignment",
    "DESC_ToTernaryAssign=Converts if statement to ternary assignment statement. <p>For example: <tt>if ($cond) {$var = $a;} else {$var = $b;}</tt> will be transformed to <tt>$var = ($cond) ? $a : $b;</tt></p><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",
    "DN_ToIfElseAssign=Convert to if/else assignment",
    "DESC_ToIfElseAssign=Converts ternary assignment statement to if/else statement. <p>For example: <tt>$var = ($cond) ? $a : $b;</tt> will be transformed to <tt>if ($cond) {$var = $a;} else {$var = $b;}</tt></p><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",})
public class ToTernary {

    @TriggerPattern(value = "if ($cond) {return $a;} else {return $b;}")
    @Hint(displayName = "#DN_ToTernaryReturn", description = "#DESC_ToTernaryReturn", category = "suggestions", hintKind = Hint.Kind.ACTION, severity = Severity.HINT)
    @NbBundle.Messages("ERR_ToTernaryReturn=Convert to ternary return")
    public static ErrorDescription toTernaryReturn(HintContext ctx) {
        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_ToTernaryReturn(), ctx.getPath(), "return ($cond)?$a:$b;");
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_ToTernaryReturn(), fix);
    }

    @TriggerPattern(value = "return ($cond)?$a:$b;")
    @Hint(displayName = "#DN_ToIfElseReturn", description = "#DESC_ToIfElseReturn", category = "suggestions", hintKind = Hint.Kind.ACTION, severity = Severity.HINT)
    @NbBundle.Messages("ERR_ToIfElseReturn=Convert to if/else return")
    public static ErrorDescription toIfElseReturn(HintContext ctx) {
        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_ToIfElseReturn(), ctx.getPath(), "if ($cond) {return $a;} else {return $b;}");
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_ToIfElseReturn(), fix);
    }

    @TriggerPattern(value = "if ($cond) {$var = $a;}else {$var = $b;}")
    @Hint(displayName = "#DN_ToTernaryAssign", description = "#DESC_ToTernaryAssign", category = "suggestions", hintKind = Hint.Kind.ACTION, severity = Severity.HINT)
    @NbBundle.Messages("ERR_ToTernaryAssign=Convert to ternary assignment")
    public static ErrorDescription toTernaryAssign(HintContext ctx) {
        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_ToTernaryAssign(), ctx.getPath(), "$var=($cond)?$a:$b;");
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_ToTernaryAssign(), fix);
    }

    @TriggerPattern(value = "$var=($cond)?$a:$b;")
    @Hint(displayName = "#DN_ToIfElseAssign", description = "#DESC_ToIfElseAssign", category = "suggestions", hintKind = Hint.Kind.ACTION, severity = Severity.HINT)
    @NbBundle.Messages("ERR_ToIfElseAssign=Convert to if/else assignment")
    public static ErrorDescription toIfElseAssign(HintContext ctx) {
        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_ToIfElseAssign(), ctx.getPath(), "if ($cond) {$var = $a;}else {$var = $b;}");
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_ToIfElseAssign(), fix);
    }
}
