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

import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreePath;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
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
    "DN_InvertTernary=Invert ternary if/else",
    "DESC_InvertTernary=Negates the condition and switches the if/else blocks of a ternary statement. <p>For example: <tt>(a != null) ? \"foo\" : \"bar\"</tt> will be transformed to <tt>(a == null) ? \"bar\" : \"foo\"</tt></p><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",})
public class InvertTernary {

    @TriggerPatterns({
        @TriggerPattern(value = "($var1 != $c) ? $a : $b"),
        @TriggerPattern(value = "($var2 == $c) ? $a : $b"),
        @TriggerPattern(value = "($var3 > $c) ? $a : $b"),
        @TriggerPattern(value = "($var4 < $c) ? $a : $b"),
        @TriggerPattern(value = "($var5 >= $c) ? $a : $b"),
        @TriggerPattern(value = "($var6 <= $c) ? $a : $b"),
        @TriggerPattern(value = "(!$var7) ? $a : $b"),
        @TriggerPattern(value = "($var8) ? $a : $b"),
    })
    @Hint(displayName = "#DN_InvertTernary", description = "#DESC_InvertTernary", category = "suggestions", hintKind = Hint.Kind.ACTION, severity = Severity.HINT)
    @NbBundle.Messages("ERR_InvertTernary=Invert ternary if/else")
    public static ErrorDescription toTernary(HintContext ctx) {
        String result = null;
        if (ctx.getVariables().containsKey("$var1")) {
            result = "($var1 == $c) ? $b : $a";
        }
        if (ctx.getVariables().containsKey("$var2")) {
            result = "($var2 != $c) ? $b : $a";
        }
        if (ctx.getVariables().containsKey("$var3")) {
            result = "($var3 <= $c) ? $b : $a";
        }
        if (ctx.getVariables().containsKey("$var4")) {
            result = "($var4 >= $c) ? $b : $a";
        }
        if (ctx.getVariables().containsKey("$var5")) {
            result = "($var5 < $c) ? $b : $a";
        }
        if (ctx.getVariables().containsKey("$var6")) {
            result = "($var6 > $c) ? $b : $a";
        }
        //negated !var7
        if (ctx.getVariables().containsKey("$var7")) {
            TreePath get = ctx.getVariables().get("$var7");
            if (get.getLeaf().getKind() == Tree.Kind.IDENTIFIER) 
            {
                //var is a boolean variable
                result = "($var7) ? $b : $a";
            }
            if (get.getLeaf().getKind() == Tree.Kind.BOOLEAN_LITERAL) 
            {
                //var is a boolean literal like 'true'/'false'
                //!true?a:b -> true?b:a
                LiteralTree tree=(LiteralTree) get.getLeaf();
                if ("true".equals(tree.getValue().toString())){
                    result = "(true) ? $b : $a";
                }
                //!true?a:b -> false?b:a
                if ("false".equals(tree.getValue().toString())){
                    result = "(false) ? $b : $a";
                }
            }
        }
        //non-negated var8
        if (ctx.getVariables().containsKey("$var8")) {
            TreePath get = ctx.getVariables().get("$var8");
            if (get.getLeaf().getKind() == Tree.Kind.IDENTIFIER) {
                //var is a boolean variable
                result = "(!$var8) ? $b : $a";
            }
            if (get.getLeaf().getKind() == Tree.Kind.BOOLEAN_LITERAL) 
            {
                //var is a boolean literal like 'true'/'false'
                LiteralTree tree=(LiteralTree) get.getLeaf();
                if ("true".equals(tree.getValue().toString())){
                    result = "(false) ? $b : $a";
                }
                if ("false".equals(tree.getValue().toString())){
                    result = "(true) ? $b : $a";
                }
            }

        }
        if (result != null) {
            Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_InvertTernary(), ctx.getPath(), result);
            return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_InvertTernary(), fix);
        }
        return null;
    }

}
