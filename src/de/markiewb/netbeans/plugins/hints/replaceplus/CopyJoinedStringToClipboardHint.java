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
package de.markiewb.netbeans.plugins.hints.replaceplus;

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
import de.markiewb.netbeans.plugins.hints.common.NonNullArrayList;
import de.markiewb.netbeans.plugins.hints.literals.BuildArgumentsVisitor;
import org.netbeans.spi.java.hints.HintContext;
import org.netbeans.spi.java.hints.TriggerTreeKind;
import org.openide.util.NbBundle.Messages;

/**
 *
 * Based on http://hg.netbeans.org/main/contrib/file/tip/editor.hints.i18n/src/org/netbeans/modules/editor/hints/i18n
 * from Jan Lahoda.
 */
@Hint(displayName = "#DN_CopyJoinedStringToClipboard",
        description = "#DESC_CopyJoinedStringToClipboard",
        category = "suggestions") //NOI18N
@Messages({"DN_CopyJoinedStringToClipboard=Copy joined literals to clipboard",
    "DESC_CopyJoinedStringToClipboard=Copy the joined literals to clipboard. It converts escaped linebreaks into real linebreaks too."})
public class CopyJoinedStringToClipboardHint {

    public static final EnumSet<Kind> TREEKINDS = EnumSet.of(Kind.STRING_LITERAL, Kind.PLUS);

    @TriggerTreeKind(value = {Kind.STRING_LITERAL, Kind.PLUS})
    public static ErrorDescription computeHint(HintContext ctx) {
        ErrorDescription run = new CopyJoinedStringToClipboardHint().run(ctx.getInfo(), ctx.getPath());
        return run;
    }
    static Logger LOG = Logger.getLogger(CopyJoinedStringToClipboardHint.class.getName());
    private AtomicBoolean cancelled = new AtomicBoolean(false);

    public void cancel() {
        cancelled.set(true);
    }

    private static boolean checkParentKind(TreePath tp, int parentIndex, Kind requiredKind) {
        while (parentIndex-- > 0 && tp != null) {
            tp = tp.getParentPath();
        }

        if (tp == null) {
            return false;
        }

        return tp.getLeaf().
                getKind() == requiredKind;
    }

    public ErrorDescription run(CompilationInfo compilationInfo, TreePath treePath) {

        //TODO: generate unique 
        try {
            final DataObject od = DataObject.find(compilationInfo.getFileObject());
            final Document doc = compilationInfo.getDocument();

            if (doc == null || treePath.getParentPath() == null || !getTreeKinds().
                    contains(treePath.getLeaf().
                    getKind())) {
                return null;
            }

            //check that the treePath is a "top-level" String expression:
            TreePath expression = treePath;

            while (expression.getParentPath().
                    getLeaf().
                    getKind() == Kind.PLUS) {
                expression = expression.getParentPath();
            }

            if (expression != treePath) {
                return null;
            }

//            //@Annotation("..."):
//            if (checkParentKind(treePath, 1, Kind.ASSIGNMENT) && checkParentKind(treePath, 2, Kind.ANNOTATION)) {
//                return null;
//            }

            //@Annotation({"...", "..."}):
            TreePath tp = treePath;

            while (tp != null) {
                tp = tp.getParentPath();
            }
//            if (checkParentKind(treePath, 1, Kind.NEW_ARRAY) && checkParentKind(treePath, 2, Kind.ASSIGNMENT) && checkParentKind(treePath, 3, Kind.ANNOTATION)) {
//                return null;
//            }

            final long hardCodedOffset = compilationInfo.getTrees().
                    getSourcePositions().
                    getStartPosition(compilationInfo.getCompilationUnit(), treePath.getLeaf());
            final long hardCodedOffsetEnd = compilationInfo.getTrees().
                    getSourcePositions().
                    getEndPosition(compilationInfo.getCompilationUnit(), treePath.getLeaf());
            BuildArgumentsVisitor v = new BuildArgumentsVisitor(compilationInfo);

            v.scan(treePath, null);
            BuildArgumentsVisitor.Result data = v.toResult();
            List<Fix> fixes = new NonNullArrayList();
            fixes.add(CopyJoinedStringToClipboardFix.create(od, TreePathHandle.create(treePath, compilationInfo), data));

	    if (!fixes.isEmpty()){
            return ErrorDescriptionFactory.
                    createErrorDescription(Severity.HINT, Bundle.DN_CopyJoinedStringToClipboard(), fixes, compilationInfo.
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
}
