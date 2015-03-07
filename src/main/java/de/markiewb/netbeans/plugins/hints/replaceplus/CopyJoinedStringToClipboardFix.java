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
package de.markiewb.netbeans.plugins.hints.replaceplus;

import de.markiewb.netbeans.plugins.hints.common.StringUtils;
import de.markiewb.netbeans.plugins.hints.literals.BuildArgumentsVisitor;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import org.netbeans.api.java.source.JavaSource;
import org.netbeans.spi.editor.hints.ChangeInfo;
import org.netbeans.spi.editor.hints.Fix;
import org.openide.awt.StatusDisplayer;
import org.openide.loaders.DataObject;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.datatransfer.ExClipboard;

/**
 * Copies the joined string of a concatenated String expression to the clipboard. For example
 * <pre>"Foo "+"bar"+"bla"</pre> will be copied to the clipboard
 * <pre>"Foobarbla"</pre><br/><p>
 * Escaped linebreaks will be converted into real linebreaks. For example:
 * <code>A\nB</code> will be copied to clipboard as
 * <pre>
 * A
 * B
 * </pre>
 * </p>
 * Based on http://hg.netbeans.org/main/contrib/file/tip/editor.hints.i18n/src/org/netbeans/modules/editor/hints/i18n
 * from Jan Lahoda.
 *
 * @author markiewb
 */
public class CopyJoinedStringToClipboardFix implements Fix {

    public static CopyJoinedStringToClipboardFix create(DataObject od, BuildArgumentsVisitor.Result data) {
        if (CopyJoinedStringToClipboardFix.supports(data)) {
            return new CopyJoinedStringToClipboardFix(od, data);
        }
        return null;
    }
    private final DataObject od;
    private final BuildArgumentsVisitor.Result data;

    private CopyJoinedStringToClipboardFix(DataObject od, BuildArgumentsVisitor.Result data) {
        this.od = od;
        this.data = data;
    }

    private static boolean supports(BuildArgumentsVisitor.Result data) {

        if (data.hasOnlyNonLiterals()) {
            //?? ignore zero-length string literals and 
            //ignore "plus" expressions without a literal
            return false;
        }
        if (data.getArguments().
                isEmpty() && data.get().size()>1) {
            //only literals can be joined
            return true;
        }

        //ignore others
        return false;
    }

    @NbBundle.Messages({"LBL_CopyJoinedStringToClipboardFix=Copy joined literals to clipboard"})
    @Override
    public String getText() {
        return Bundle.LBL_CopyJoinedStringToClipboardFix();
    }

    @Override
    public final ChangeInfo implement() throws IOException {
        JavaSource js = JavaSource.forFileObject(od.getPrimaryFile());
        rewrite(js, getNewExpression());

        return null;
    }

    protected final void rewrite(JavaSource js, final String text) {
        ExClipboard clipboard = Lookup.getDefault().
                lookup(ExClipboard.class);
        clipboard.setContents(new StringSelection(text), null);
	StatusDisplayer.getDefault().setStatusText(String.format("Copied '%s' to clipboard", text));
    }

    protected String getNewExpression() {
        StringBuilder formatBuilder = new StringBuilder();

        for (BuildArgumentsVisitor.TokenPair pair : data.
                get()) {
            formatBuilder.append(pair.getText());
        }
        return StringUtils.unescapeQuotes(formatBuilder.toString());
    }
}
