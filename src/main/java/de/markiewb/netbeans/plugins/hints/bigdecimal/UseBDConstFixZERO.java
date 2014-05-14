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
    "ERR_UseBDConstFixZERO=Convert to BigDecimal.ZERO",
    "DN_UseBigDecimalConstantsZERO=Convert to BigDecimal.ZERO",
    "DESC_UseBigDecimalConstantsZERO=Converts expressions like <tt>new java.math.BigDecimal(0)</tt> to use <tt>BigDecimal.ZERO</tt><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p> ",})
public class UseBDConstFixZERO {

    @Hint(displayName = "#DN_UseBigDecimalConstantsZERO", description = "#DESC_UseBigDecimalConstantsZERO", category = "suggestions", hintKind = Hint.Kind.INSPECTION, severity = Severity.HINT)
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
    public static ErrorDescription convert(HintContext ctx) {

        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_UseBDConstFixZERO(), ctx.getPath(), "java.math.BigDecimal.ZERO");
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_UseBDConstFixZERO(), fix);
    }

}
