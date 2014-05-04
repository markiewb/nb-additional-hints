package de.markiewb.netbeans.plugins.hints.tonumber;
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
 * "Portions Copyrighted 2013 rasa-silva"
 * "Portions Copyrighted 2013 Benno Markiewicz"
 */

import com.sun.source.tree.LiteralTree;
import de.markiewb.netbeans.plugins.hints.modifiers.*;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreePath;
import java.util.EnumSet;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
import org.netbeans.spi.java.hints.ErrorDescriptionFactory;
import org.netbeans.spi.java.hints.Hint;
import org.netbeans.spi.java.hints.HintContext;
import org.netbeans.spi.java.hints.TriggerTreeKind;
import org.netbeans.spi.java.hints.support.FixFactory;
import org.openide.util.NbBundle;

/**
 *
 */
@NbBundle.Messages({
    "ERR_ToNumber=Convert to number",
    "DN_ToNumber=Convert number in String literal to number literal",
    "DESC_ToNumber=Convert number in String literal to number literal. For example: <tt>\"123\" -> 123</tt><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",
    "ERR_ToString=Convert to string",
    "DN_ToString=Convert number literal to String literal",
    "DESC_ToString=Convert number literal to String literal . For example: <tt>123 -> \"123\"</tt><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>"})
public class ToNumber {

    @Hint(displayName = "#DN_ToNumber", description = "#DESC_ToNumber", category = "suggestions",
            hintKind = Hint.Kind.ACTION, severity = Severity.HINT)
    @TriggerTreeKind({Tree.Kind.STRING_LITERAL})
    public static ErrorDescription convert(HintContext ctx) {
        final TreePath path = ctx.getPath();

        Tree leaf = path.getLeaf();
        if (leaf.getKind() != Tree.Kind.STRING_LITERAL) {
            return null;
        }
        LiteralTree tree = (LiteralTree) leaf;

        String stringValue = (String) tree.getValue();
        
        try {
            Integer.parseInt(stringValue);
        } catch (NumberFormatException numberFormatException) {
            //String literal was not a number
            return null;
        }
        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_ToNumber(), ctx.getPath(), stringValue);
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_ToNumber(), fix);
    }

    @Hint(displayName = "#DN_ToString", description = "#DESC_ToString", category = "suggestions",
            hintKind = Hint.Kind.ACTION, severity = Severity.HINT)
    @TriggerTreeKind({Tree.Kind.INT_LITERAL, Tree.Kind.LONG_LITERAL})
    public static ErrorDescription convertToString(HintContext ctx) {
        final TreePath path = ctx.getPath();

        Tree leaf = path.getLeaf();
        LiteralTree tree = (LiteralTree) leaf;

        String value = tree.getValue().toString();

        Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_ToString(), ctx.getPath(), '"' + value + '"');
        return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_ToString(), fix);
    }
}
