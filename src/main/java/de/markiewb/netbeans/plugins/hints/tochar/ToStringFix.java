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
package de.markiewb.netbeans.plugins.hints.tochar;

import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.Tree;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
import static org.netbeans.spi.java.hints.ErrorDescriptionFactory.forName;
import org.netbeans.spi.java.hints.Hint;
import org.netbeans.spi.java.hints.HintContext;
import static org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix;
import org.netbeans.spi.java.hints.TriggerTreeKind;
import org.openide.util.NbBundle.Messages;

@Messages({
    "ERR_ToStringFix=Convert char to string",
    "DN_ToString=Convert char to string",
    "DESC_ToString=Converts a char to a string. For example <tt>'c'</tt> will be transformed to <tt>\"c\"</tt><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",
    "ERR_ToCharFix=Convert string to char",
    "DN_ToChar=Convert string to char",
    "DESC_ToChar=Converts a single string literal to a char. For example <tt>\"c\"</tt> will be transformed to <tt>'c'</tt><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",})
public class ToStringFix {

    @Hint(displayName = "#DN_ToString", description = "#DESC_ToString", category = "suggestions", hintKind = Hint.Kind.ACTION, severity = Severity.HINT)
    @TriggerTreeKind(Tree.Kind.CHAR_LITERAL)
    public static ErrorDescription convertFromCharToString(HintContext ctx) {

        Fix fix = rewriteFix(ctx, Bundle.ERR_ToStringFix(), ctx.getPath(), "\"" + ctx.getPath().getLeaf().toString().substring(1, 2) + "\"");
        return forName(ctx, ctx.getPath(), Bundle.ERR_ToStringFix(), fix);
    }

    @Hint(displayName = "#DN_ToChar", description = "#DESC_ToChar", category = "suggestions", hintKind = Hint.Kind.ACTION, severity = Severity.HINT)
    @TriggerTreeKind(Tree.Kind.STRING_LITERAL)
    public static ErrorDescription convertFromStringToChar(HintContext ctx) {

        LiteralTree lit = (LiteralTree) ctx.getPath().getLeaf();
        String toString = lit.getValue().toString();
        if (toString.length() == 1) {
            Fix fix = rewriteFix(ctx, Bundle.ERR_ToCharFix(), ctx.getPath(), "'" + toString + "'");
            return forName(ctx, ctx.getPath(), Bundle.ERR_ToCharFix(), fix);
        } else {
            return null;
        }
    }

}
