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
 * "Portions Copyrighted 2013 Benno Markiewicz"
 */
package de.markiewb.netbeans.plugins.hints.breakonlinefeed;

import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.Tree;
import de.markiewb.netbeans.plugins.hints.replaceplus.*;
import com.sun.source.util.TreePath;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import javax.swing.text.Document;
import org.netbeans.api.java.source.CompilationInfo;
import org.netbeans.api.java.source.TreePathHandle;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
import org.netbeans.spi.java.hints.Hint;
import org.openide.ErrorManager;
import org.openide.loaders.DataObject;
import com.sun.source.tree.Tree.Kind;
import com.sun.source.util.SourcePositions;
import java.util.Arrays;
import java.util.HashSet;
import org.netbeans.spi.java.hints.HintContext;
import org.netbeans.spi.java.hints.TriggerTreeKind;
import org.openide.util.NbBundle.Messages;

/**
 * See {@link BreakStringOnLineFeedFix} Based on
 * http://hg.netbeans.org/main/contrib/file/tip/editor.hints.i18n/src/org/netbeans/modules/editor/hints/i18n from Jan
 * Lahoda.
 */
@Hint(displayName = "#DN_BreakStringOnLineFeed",
        description = "#DESC_BreakStringOnLineFeed",
        category = "suggestions", hintKind = Hint.Kind.ACTION, severity = Severity.HINT) //NOI18N
@Messages({"DN_BreakStringOnLineFeed=Split the String on linebreaks",
    "DESC_BreakStringOnLineFeed="
	+ "Split the String on linebreaks symbols into separate concatenated literals. For example"
	+"<tt>\"FOO\\nBAR\"</tt> will be transformed into"
	+"<tt>\"FOO\\n\" + \"BAR\"</tt><br/><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>"
	})
public class BreakStringOnLineFeedHint {

    public static final EnumSet<Kind> TREEKINDS = EnumSet.of(Kind.STRING_LITERAL);

    @TriggerTreeKind(value = {Kind.STRING_LITERAL})
    public static ErrorDescription computeHint(HintContext ctx) {
        ErrorDescription run = new BreakStringOnLineFeedHint().run(ctx.getInfo(), ctx.getPath());
        return run;
    }
    static Logger LOG = Logger.getLogger(BreakStringOnLineFeedHint.class.getName());
    private AtomicBoolean cancelled = new AtomicBoolean(false);

    public void cancel() {
        cancelled.set(true);
    }

    public ErrorDescription run(CompilationInfo compilationInfo, TreePath treePath) {

        try {
            final DataObject od = DataObject.find(compilationInfo.getFileObject());
            final Document doc = compilationInfo.getDocument();

            if (doc == null || treePath.getParentPath() == null || !getTreeKinds().
                    contains(treePath.getLeaf().
                    getKind())) {
                return null;
            }

            LiteralTree literal = (LiteralTree) treePath.getLeaf();
            String text = "" + literal.getValue();
            boolean containsInnerLF = containsInnerLineFeed(text);
            if (!containsInnerLF) {
                return null;
            }
            //ignore unneccessary hint on "foo\n" or "\nfoo"
            if (containsInnerLF && endsWithLineFeed(text) && !containsInnerLineFeed(text.trim())) {
                return null;
            }
            final SourcePositions pos = compilationInfo.getTrees().
                    getSourcePositions();

            final long hardCodedOffset = pos.
                    getStartPosition(compilationInfo.getCompilationUnit(), treePath.getLeaf());
            final long hardCodedOffsetEnd = pos.
                    getEndPosition(compilationInfo.getCompilationUnit(), treePath.getLeaf());

            final List<Fix> fixes = new ArrayList<Fix>();
            fixes.add(BreakStringOnLineFeedFix.create(od, TreePathHandle.create(treePath, compilationInfo), text));
	    if (!fixes.isEmpty()) {
		return ErrorDescriptionFactory.
			createErrorDescription(Severity.HINT, Bundle.DN_BreakStringOnLineFeed(), fixes, compilationInfo.
			getFileObject(), (int) hardCodedOffset, (int) hardCodedOffsetEnd);
	    }
        } catch (IndexOutOfBoundsException ex) {
            ErrorManager.getDefault().
                    notify(ErrorManager.INFORMATIONAL, ex);
        } catch (IOException ex) {
            ErrorManager.getDefault().
                    notify(ex);
        }
        return null;
    }

    private Set<Kind> getTreeKinds() {
        return TREEKINDS;
    }
    private static String[] supportedLineBreaks = new String[]{"\n\r", "\n", "\r"};

    private boolean endsWithLineFeed(final String text) {
        for (String string : supportedLineBreaks) {
            if (text.endsWith(string)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsInnerLineFeed(final String text) {
        boolean containsInnerLF = false;
        for (String string : supportedLineBreaks) {
            if (text.contains(string)) {
                return true;
            }
        }
        return false;
    }
}
