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
package de.markiewb.netbeans.plugins.hints.literals.split;

import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.SourcePositions;
import com.sun.source.util.TreePath;
import de.markiewb.netbeans.plugins.hints.common.StringUtils;
import java.text.MessageFormat;
import org.netbeans.api.java.source.CompilationInfo;
import org.netbeans.api.java.source.WorkingCopy;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
import org.netbeans.spi.java.hints.ErrorDescriptionFactory;
import org.netbeans.spi.java.hints.Hint;
import org.netbeans.spi.java.hints.HintContext;
import org.netbeans.spi.java.hints.JavaFix;
import org.netbeans.spi.java.hints.TriggerTreeKind;
import org.openide.util.NbBundle.Messages;

@Hint(displayName = "#DN_SplitLiteralOnCaretHint", description = "#DESC_SplitLiteralOnCaretHint", category = "suggestions", hintKind = Hint.Kind.ACTION, severity = Severity.HINT)
@Messages({
    "DN_SplitLiteralOnCaretHint=Split\t at caret",
    "DESC_SplitLiteralOnCaretHint=Splits the literal at the current caret.<p>For example: <tt>\"Foo|Bar\"</tt> will be transformed into <tt>\"Foo\" + \"Bar\"</tt></p><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>"
}
)
public class SplitLiteralOnCaretHint {

    @TriggerTreeKind(Tree.Kind.STRING_LITERAL)
    @Messages("ERR_SplitLiteralOnCaretHint=Split at caret")
    public static ErrorDescription computeWarning(HintContext ctx) {
        CompilationInfo wc = ctx.getInfo();
        final SourcePositions scp = wc.getTrees().getSourcePositions();
        int posStart = (int) scp.getStartPosition(wc.getCompilationUnit(), ctx.getPath().getLeaf());
        int posEnd = (int) scp.getEndPosition(wc.getCompilationUnit(), ctx.getPath().getLeaf());
        final int caretLocation = ctx.getCaretLocation();

        //don't support empty string
        //don't support caret at start
        //don't support caret at end
        if (posStart + 1 < caretLocation && caretLocation < posEnd - 1) {
            Fix fix = new FixImpl(ctx.getInfo(), ctx.getPath(), caretLocation).toEditorFix();
            return ErrorDescriptionFactory.forName(ctx, ctx.getPath(), Bundle.ERR_SplitLiteralOnCaretHint(), fix);
        } else {
            return null;
        }
    }

    private static final class FixImpl extends JavaFix {

        private int caretLocation = -1;

        public FixImpl(CompilationInfo info, TreePath tp) {
            super(info, tp);
        }

        private FixImpl(CompilationInfo info, TreePath path, int caretLocation) {
            super(info, path);
            this.caretLocation = caretLocation;
        }

        @Override
        @Messages("FIX_SplitLiteralOnCaretHint=Split at caret")
        protected String getText() {
            return Bundle.FIX_SplitLiteralOnCaretHint();
        }

        @Override
        protected void performRewrite(TransformationContext ctx) {
            LiteralTree literal = (LiteralTree) ctx.getPath().getLeaf();
            WorkingCopy wc = ctx.getWorkingCopy();
            final SourcePositions scp = wc.getTrees().
                    getSourcePositions();

            int posStart = (int) scp.getStartPosition(wc.getCompilationUnit(), ctx.getPath().getLeaf());
            int posEnd = (int) scp.getEndPosition(wc.getCompilationUnit(), ctx.getPath().getLeaf());
            //split the text "foo|bar"
            String prefix = wc.getText().substring(posStart + 1, caretLocation);
            String postfix = wc.getText().substring(caretLocation, posEnd - 1);

            prefix=StringUtils.escapeLF(prefix);
            postfix=StringUtils.escapeLF(postfix);
            
            SourcePositions[] pos = new SourcePositions[1];
            //create "foo"+"bar"
            String newLiteral = MessageFormat.format("\"{0}\" + \"{1}\"", prefix, postfix);
            Tree t = wc.getTreeUtilities().parseExpression(newLiteral, pos);
            wc.rewrite(literal, t);
        }
    }
}
