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
package de.markiewb.netbeans.plugins.hints.structure;

import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;
import static com.sun.source.tree.Tree.Kind.ANNOTATION;
import com.sun.source.util.TreePath;
import java.util.EnumSet;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
import org.netbeans.spi.java.hints.ErrorDescriptionFactory;
import org.netbeans.spi.java.hints.Hint;
import org.netbeans.spi.java.hints.HintContext;
import org.netbeans.spi.java.hints.TriggerTreeKind;
import org.openide.util.NbBundle;

/**
 *
 * @author markiewb
 */
@NbBundle.Messages({
    "DN_AddThisToMember=Add \"this.\" prefix",
    "DESC_AddThisToMember=Adds \"this.\" as prefix for non-static member variables/methods.<p>The hint is disabled by default, because there are only a few specific usecases for this hint.</p><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",})
public class AddThisToMember {

    private static final EnumSet<ElementKind> supportedKinds = EnumSet.of(ElementKind.FIELD, ElementKind.METHOD);

    @Hint(displayName = "#DN_AddThisToMember", description = "#DESC_AddThisToMember", category = "suggestions", hintKind = Hint.Kind.INSPECTION, severity = Severity.WARNING, enabled = false)
    @NbBundle.Messages("ERR_AddThisToMember=Add \"this.\" prefix")
    @TriggerTreeKind(Tree.Kind.IDENTIFIER)
    public static ErrorDescription toTernary(HintContext ctx) {
        TreePath path = ctx.getPath();

        Element element = ctx.getInfo().getTrees().getElement(path);
        if (element == null || element.getModifiers().contains(Modifier.STATIC)) {
            return null;
        }
        if (!supportedKinds.contains(element.getKind())) {
            return null;
        }

        //do not tranform @SuppressWarnings("ABC") to @SuppressWarnings(this."ABC")
        if (isWithinAnnotation(path)) {
            return null;
        }

        //do not transform patterns like "this.dialog" to "this.this.dialog"
        final String memberName = ((IdentifierTree) path.getLeaf()).getName().toString();
        if ("this".equals(memberName)) {
            return null;
        }
        //do not transform patterns like "super.dialog" to "this.super().dialog"
        if ("super".equals(memberName)) {
            return null;
        }

        String result = "this." + memberName;
        if (result != null) {
            Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_AddThisToMember(), path, result);
            return ErrorDescriptionFactory.forName(ctx, path, Bundle.ERR_AddThisToMember(), fix);
        }
        return null;
    }

    private static boolean isWithinAnnotation(TreePath path) {

        for (TreePath p = path; null != p; p = p.getParentPath()) {
            if (ANNOTATION.equals(p.getLeaf().getKind())) {
                return true;
            }
        }
        return false;
    }

}
