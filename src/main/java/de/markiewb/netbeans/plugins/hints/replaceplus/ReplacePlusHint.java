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

import com.sun.source.util.TreePath;
import java.io.IOException;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import javax.swing.text.Document;
import org.netbeans.api.java.source.CompilationInfo;
import org.netbeans.api.java.source.TreePathHandle;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
import org.netbeans.spi.java.hints.Hint;
import org.openide.ErrorManager;
import org.openide.loaders.DataObject;
import com.sun.source.tree.Tree.Kind;
import de.markiewb.netbeans.plugins.hints.common.NonNullArrayList;
import de.markiewb.netbeans.plugins.hints.literals.BuildArgumentsVisitor;
import org.netbeans.spi.java.hints.BooleanOption;
import org.netbeans.spi.java.hints.HintContext;
import org.netbeans.spi.java.hints.TriggerTreeKind;
import org.openide.util.NbBundle.Messages;

/**
 * Based on http://hg.netbeans.org/main/contrib/file/tip/editor.hints.i18n/src/org/netbeans/modules/editor/hints/i18n
 * from Jan Lahoda.
 * 
 * @author markiewb
 */
@Hint(displayName = "#DN_ReplacePlus",
        description = "#DESC_ReplacePlus",
        category = "suggestions", hintKind = Hint.Kind.ACTION, severity = Severity.HINT) //NOI18N
@Messages({"DN_ReplacePlus=Replace + with ...", 
    "DESC_ReplacePlus=Replace String concatenation with "
	+ "<ul>"
	+ "<li><tt>java.text.Message.format()</tt> or</li>"
	+ "<li><tt>java.lang.String.format()</tt> or</li>"
	+ "<li><tt>java.lang.StringBuilder().append()</tt></li>"
	+ "</ul>"
	+ "For example: <tt>\"Found \" + variable + \" entries\"</tt> can be transformed into"
	+ "<ul>"
	+ "<li><tt>Message.format(\"Found {0} entries\", variable)</tt> or</li>"
	+ "<li><tt>String.format(\"Found %s entries\", variable)</tt> or</li>"
	+ "<li><tt>new StringBuilder().append(\"Found \").append(variable).append(\" entries\").toString()</tt></li>"
	+ "</ul>"
	+ "<p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",
        "OPTNAME_SupportMessageFormat=<html>support <tt>Message.format()</tt>",
        "OPTDESC_SupportMessageFormat=",
        "OPTNAME_SupportStringFormat=<html>support <tt>String.format()</tt>",
        "OPTDESC_SupportStringFormat=",
        "OPTNAME_SupportStringBuilder=<html>support <tt>StringBuilder().append()</tt>",
        "OPTDESC_SupportStringBuilder="
})
public class ReplacePlusHint {
    
    private static final boolean DEFAULT_SUPPORTMESSAGEFORMAT = true;
    @BooleanOption(displayName = "#OPTNAME_SupportMessageFormat",tooltip = "#OPTDESC_SupportMessageFormat",defaultValue = DEFAULT_SUPPORTMESSAGEFORMAT)
    public static final String OPTION_SUPPORTMESSAGEFORMAT = "support.messageformat";

    private static final boolean DEFAULT_SUPPORTSTRINGFORMAT = true;
    @BooleanOption(displayName = "#OPTNAME_SupportStringFormat",tooltip = "#OPTDESC_SupportStringFormat",defaultValue = DEFAULT_SUPPORTSTRINGFORMAT)
    public static final String OPTION_SUPPORTSTRINGFORMAT = "support.stringformat";

    private static final boolean DEFAULT_SUPPORTSTRINGBUILDER = true;
    @BooleanOption(displayName = "#OPTNAME_SupportStringBuilder",tooltip = "#OPTDESC_SupportStringBuilder",defaultValue = DEFAULT_SUPPORTSTRINGBUILDER)
    public static final String OPTION_SUPPORTSTRINGBUILDER = "support.stringbuilder";
    
    public static final EnumSet<Kind> TREEKINDS = EnumSet.of(Kind.STRING_LITERAL, Kind.PLUS);
    
    
    private HintContext ctx;

    public ReplacePlusHint(HintContext ctx) {
        this.ctx = ctx;
    }
    

//    @UseOptions({OPTION_SUPPORTMESSAGEFORMAT})
    @TriggerTreeKind(value = {Kind.STRING_LITERAL, Kind.PLUS})
    public static ErrorDescription computeHint(HintContext ctx) {
        Config config = new Config();
        config.supportMessageFormat = ctx.getPreferences().getBoolean(OPTION_SUPPORTMESSAGEFORMAT, DEFAULT_SUPPORTMESSAGEFORMAT);
        config.supportStringFormat = ctx.getPreferences().getBoolean(OPTION_SUPPORTSTRINGFORMAT, DEFAULT_SUPPORTSTRINGFORMAT);
        config.supportStringBuilder = ctx.getPreferences().getBoolean(OPTION_SUPPORTSTRINGBUILDER, DEFAULT_SUPPORTSTRINGBUILDER);

        ErrorDescription run = new ReplacePlusHint(ctx).run(config);
        return run;
    }
    static Logger LOG = Logger.getLogger(ReplacePlusHint.class.getName());
    private AtomicBoolean cancelled = new AtomicBoolean(false);

    static class Config{
        boolean supportMessageFormat;
        boolean supportStringFormat;
        boolean supportStringBuilder;
    }
    
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

    public ErrorDescription run(Config config) {

        TreePath treePath = ctx.getPath();
        CompilationInfo compilationInfo = ctx.getInfo();
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

            //ignore @Annotation("..."):
            if (checkParentKind(treePath, 1, Kind.ASSIGNMENT) && checkParentKind(treePath, 2, Kind.ANNOTATION)) {
                return null;
            }
            
            //ignore @Annotation({"...", "..."}): ???
            if (checkParentKind(treePath, 1, Kind.NEW_ARRAY) && checkParentKind(treePath, 2, Kind.ASSIGNMENT) && checkParentKind(treePath, 3, Kind.ANNOTATION)) {
                return null;
            }

            /**
             * <pre>Method("B"+42);</pre>
             */
            final boolean isMethodInvocation = checkParentKind(treePath, 1, Kind.METHOD_INVOCATION);
            /**
             * <pre>String foo="B"+42;</pre>
             */
            final boolean isVariableAssignment = checkParentKind(treePath, 1, Kind.VARIABLE);
            /**
             * <pre>String foo; foo="B"+42;</pre>
             */
            final boolean isAssignment = checkParentKind(treePath, 1, Kind.ASSIGNMENT);
            /**
             * <pre>throw new RuntimeException("B"+42)</pre>
             */
            final boolean isNewClass = checkParentKind(treePath, 1, Kind.NEW_CLASS);

            //ignore errornous expressions "a"+42+"b"; with no assignment
            /**
             * https://github.com/markiewb/nb-additional-hints/issues/3
             * Detecting erroneous expressions via Kind.ERRONEOUS seems not to
             * work so we use a whitelist here instead.
             */
            if (!isMethodInvocation && !isVariableAssignment && !isAssignment && !isNewClass) {
                return null;
            }

            BuildArgumentsVisitor v = new BuildArgumentsVisitor(compilationInfo);

            v.scan(treePath, null);
            BuildArgumentsVisitor.Result data = v.toResult();

	    if (data.getArguments().isEmpty()) {
		//only literals like "A"+"B" (without variable) is not supported
		return null;
	    }
	    //only join joinable terms, at least 2 terms are required
	    if (data.get().size() >= 2) {
		List<Fix> fixes = new NonNullArrayList();
                if (config.supportMessageFormat) {
                    fixes.add(ReplaceWithMessageFormatFix.create(od, TreePathHandle.create(treePath, compilationInfo), data));
                }
                if (config.supportStringFormat) {

                    fixes.add(ReplaceWithStringFormatFix.create(od, TreePathHandle.create(treePath, compilationInfo), data));
                }
                if (config.supportStringBuilder) {

                    fixes.add(ReplaceWithStringBuilderFix.create(od, TreePathHandle.create(treePath, compilationInfo), data));
                }
		if (!fixes.isEmpty()) {
                    Fix[] fixs = fixes.toArray(new Fix[fixes.size()]);
                    
                    return org.netbeans.spi.java.hints.ErrorDescriptionFactory.forName(ctx, treePath, Bundle.DN_ReplacePlus(), fixs);
		}
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
