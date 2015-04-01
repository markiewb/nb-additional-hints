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
import com.sun.source.tree.Tree.Kind;
import com.sun.source.util.TreePath;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import org.netbeans.api.java.source.CompilationInfo;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
import static org.netbeans.spi.java.hints.ErrorDescriptionFactory.forName;
import org.netbeans.spi.java.hints.Hint;
import org.netbeans.spi.java.hints.HintContext;
import static org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix;
import org.netbeans.spi.java.hints.TriggerPattern;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author markiewb
 */
@Messages({
    "ERR_ReturnForOptionalEmpty=Convert to Optional.empty(). The return value null looks suspicious for methods with return value java.util.Optional.",
    "ERR_ReturnForOptionalNullable=Convert to Optional.ofNullable()/Optional.of(). The return value should be an Optional instance for a method with a return value java.util.Optional.",
    "DN_ReturnForOptionalOfNullable=Convert to return Optional.ofNullable()",
    "DN_ReturnForOptionalOf=Convert to return Optional.of()",
    "DN_ReturnForOptionalEmpty=Convert to return Optional.empty()",
    "DN_ReturnForOptional=Introduce java.util.Optional return values",
    "DESC_ReturnForOptional=" + "Provides some fixes for return statements within methods with the return type <tt>java.util.Optional</tt>."
    + "<p>For example <ul><li><tt>return null</tt> will be transformed to <tt>return Optional.empty()</tt></li><li><tt>return xxx</tt> will be transformed to <tt>return Optional.of(xxx)</tt> or <tt>return Optional.ofNullable(xxx)</tt></li></ul></p><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>"})

public class ReturnForOptional {

    @Hint(displayName = "#DN_ReturnForOptional", description = "#DESC_ReturnForOptional", category = "bugs", hintKind = Hint.Kind.INSPECTION, severity = Severity.ERROR)
    @TriggerPattern("return $a")
    public static ErrorDescription toFix(HintContext ctx) {

        final TreePath returnTP = ctx.getPath();
        TreePath methodTP = getSurroundingMethod(returnTP);
        if (null == methodTP) {
            return null;
        }
        final CompilationInfo ci = ctx.getInfo();
        ExecutableElement method = (ExecutableElement) ci.getTrees().getElement(methodTP);
        TypeMirror methodReturnType = method.getReturnType();
        final String returnType = ci.getTypes().erasure(methodReturnType).toString();
        if ("java.util.Optional".equals(returnType)) {

            TreePath path = ctx.getVariables().get("$a");
            //ignore existing "return Optional..."/"return o"
            if (path.getLeaf().getKind()== Kind.IDENTIFIER || path.getLeaf().getKind()== Kind.METHOD_INVOCATION){
                    TypeMirror typeMirror = ci.getTypes().erasure(ci.getTrees().getTypeMirror(path));
                    if ("java.util.Optional".equals(typeMirror.toString())){
                        return null;
                    }
            }
            
            if (path.getLeaf().getKind() == Kind.NULL_LITERAL) {
                Fix fix = rewriteFix(ctx, Bundle.DN_ReturnForOptionalEmpty(), returnTP, "return java.util.Optional.empty()");
                return forName(ctx, returnTP, Bundle.ERR_ReturnForOptionalEmpty(), fix);
            } else {
                Fix fixA = rewriteFix(ctx, Bundle.DN_ReturnForOptionalOfNullable(), returnTP, "return java.util.Optional.ofNullable($a)");
                Fix fixB = rewriteFix(ctx, Bundle.DN_ReturnForOptionalOf(), returnTP, "return java.util.Optional.of($a)");
                return forName(ctx, returnTP, Bundle.ERR_ReturnForOptionalNullable(), fixA, fixB);
            }

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
