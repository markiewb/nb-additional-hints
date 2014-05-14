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
