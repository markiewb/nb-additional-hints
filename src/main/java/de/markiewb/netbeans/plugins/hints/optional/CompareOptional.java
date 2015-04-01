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
package de.markiewb.netbeans.plugins.hints.optional;

import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
import org.netbeans.spi.java.hints.ConstraintVariableType;
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
    "DN_AccessOptional=Replace with Optional.isPresent()",
    "DESC_AccessOptional=<tt>java.util.Optional</tt> should not be null, so replace comparisons using <tt>null</tt> with <tt>isPresent()</tt>. <p>For example: <tt>Optional<T> var=...; if (null!=var){...}</tt> will be transformed to <tt>Optional<T> var=...; if (var.isPresent()){...}</tt></p><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",})
public class CompareOptional {

    @TriggerPatterns({
        @TriggerPattern(value = "null!=$var1", constraints = @ConstraintVariableType(variable = "$var1", type = "java.util.Optional")),
        @TriggerPattern(value = "$var2!=null", constraints = @ConstraintVariableType(variable = "$var2", type = "java.util.Optional")),
        @TriggerPattern(value = "null==$var3", constraints = @ConstraintVariableType(variable = "$var3", type = "java.util.Optional")),
        @TriggerPattern(value = "$var4==null", constraints = @ConstraintVariableType(variable = "$var4", type = "java.util.Optional")),
    })
    @Hint(displayName = "#DN_AccessOptional", description = "#DESC_AccessOptional", category = "bugs", hintKind = Hint.Kind.INSPECTION, severity = Severity.ERROR)
    @NbBundle.Messages("ERR_AccessOptional=Replace with Optional.isPresent()")
    public static ErrorDescription toFix(HintContext ctx) {
        String result = null;
        if (ctx.getVariables().containsKey("$var1")) {
            result = "$var1.isPresent()";
        }
        if (ctx.getVariables().containsKey("$var2")) {
            result = "$var2.isPresent()";
        }
        if (ctx.getVariables().containsKey("$var3")) {
            result = "!$var3.isPresent()";
        }
        if (ctx.getVariables().containsKey("$var4")) {
            result = "!$var4.isPresent()";
        }
        if (result != null) {
            Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_AccessOptional(), ctx.getPath(), result);
            return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_AccessOptional(), fix);
        }
        return null;
    }

}
