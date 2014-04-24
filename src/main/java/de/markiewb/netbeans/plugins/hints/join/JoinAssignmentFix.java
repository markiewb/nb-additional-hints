/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
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
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
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
 * "Portions Copyrighted 2014 Benno Markiewicz"
 */
package de.markiewb.netbeans.plugins.hints.join;

import com.sun.source.util.TreePath;
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
    "DN_JoinAssignment=Join assignment",
    "DESC_JoinAssignment=Join assignment. <br>For example: <tt>int foo; foo=42;</tt> will be transformed to <tt>int foo = 42;</tt><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",})
@Hint(displayName = "#DN_JoinAssignment", description = "#DESC_JoinAssignment", category = "suggestions", hintKind = Hint.Kind.ACTION, severity = Severity.HINT)
public class JoinAssignmentFix {

    @TriggerPattern(value = "$type $name;$name=$value;")
    @Messages("ERR_JoinAssignment=Join assignment")
    public static ErrorDescription computeAssertTrueWithoutMessage(HintContext ctx) {
        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_JoinAssignment(), ctx.getPath(), "$type $name = $value;");
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_JoinAssignment(), fix);
    }

}
