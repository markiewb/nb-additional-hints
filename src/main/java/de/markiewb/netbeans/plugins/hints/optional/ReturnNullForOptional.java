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

import com.sun.source.tree.Tree;
import com.sun.source.util.TreePath;
import javax.lang.model.element.ExecutableElement;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
import static org.netbeans.spi.java.hints.ErrorDescriptionFactory.forName;
import org.netbeans.spi.java.hints.Hint;
import org.netbeans.spi.java.hints.HintContext;
import static org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix;
import org.netbeans.spi.java.hints.TriggerTreeKind;
import org.openide.util.NbBundle.Messages;

/**
 * FYI: Cannot create a unit test for this fix because we still compile with
 * JDK7, but we use JDK8 language features.
 *
 * @author markiewb
 */
@Messages({
    "ERR_ReturnNullForOptional=Convert to Optional.empty(). The return value null looks suspicious for methods with return value java.util.Optional.",
    "DN_ReturnNullForOptional=Convert return null to return Optional.empty()",
    "DESC_ReturnNullForOptional=The return value <tt>null</tt> looks suspicious for methods with return value <tt>java.util.Optional</tt>, so replace it with <tt>Optional.empty()</tt>. For example <tt>return null</tt> will be transformed to <tt>return Optional.empty()</tt><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>"})

public class ReturnNullForOptional {

    @Hint(displayName = "#DN_ReturnNullForOptional", description = "#DESC_ReturnNullForOptional", category = "bugs", hintKind = Hint.Kind.INSPECTION, severity = Severity.ERROR)
    @TriggerTreeKind(Tree.Kind.NULL_LITERAL)
    public static ErrorDescription nullForOpt(HintContext ctx) {

        final TreePath nullTP = ctx.getPath();
        final TreePath returnTP = nullTP.getParentPath();
        if (null == returnTP || Tree.Kind.RETURN != returnTP.getLeaf().getKind()) {
            return null;
        }
        TreePath methodTP = getSurroundingMethod(returnTP);
        if (null == methodTP) {
            return null;
        }
        ExecutableElement method = (ExecutableElement) ctx.getInfo().getTrees().getElement(methodTP);
        final String returnTyp = method.getReturnType().toString();
        if (returnTyp.startsWith("java.util.Optional<") || returnTyp.equals("java.util.Optional")) {
            Fix fix = rewriteFix(ctx, Bundle.DN_ReturnNullForOptional(), ctx.getPath(), "Optional.empty()");
            return forName(ctx, ctx.getPath(), Bundle.ERR_ReturnNullForOptional(), fix);

        }
        return null;
    }

    private static TreePath getSurroundingMethod(final TreePath startingTreePath) {
        TreePath tp = startingTreePath;
        while (null != tp && tp.getLeaf().getKind() != Tree.Kind.METHOD) {
            tp = tp.getParentPath();
        }
        return tp;
    }

}
