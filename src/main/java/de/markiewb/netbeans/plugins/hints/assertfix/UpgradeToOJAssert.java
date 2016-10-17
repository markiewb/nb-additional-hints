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
 * Portions Copyrighted 2016 benno.markiewicz@googlemail.com
 */
package de.markiewb.netbeans.plugins.hints.assertfix;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.ImportTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreePath;
import java.util.Collections;
import org.netbeans.api.java.source.TreeMaker;
import org.netbeans.api.java.source.TreePathHandle;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
import org.netbeans.spi.java.hints.ErrorDescriptionFactory;
import org.netbeans.spi.java.hints.Hint;
import org.netbeans.spi.java.hints.HintContext;
import org.netbeans.spi.java.hints.JavaFix;
import org.netbeans.spi.java.hints.TriggerTreeKind;
import org.openide.util.NbBundle;

/**
 * Replace junit.framework.Assert with org.junit.Assert
 *
 * @author markiewb
 * @since 1.6.0
 */
@NbBundle.Messages({
    "ERR_UpgradeToOJAssert=Replace with org.junit.Assert",
    "DESC_UpgradeToOJAssert=Replace usages of the deprecated <tt>junit.framework.Assert</tt> class to <tt>org.junit.Assert</tt>.<p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",})
public class UpgradeToOJAssert {

    @TriggerTreeKind({Tree.Kind.IMPORT, Tree.Kind.METHOD_INVOCATION})
    @Hint(displayName = "#ERR_UpgradeToOJAssert", description = "#DESC_UpgradeToOJAssert", category = "testing", hintKind = Hint.Kind.INSPECTION, severity = Severity.WARNING)
    public static ErrorDescription toFix(HintContext ctx) {
        if (ctx.getPath().getLeaf().getKind() == Tree.Kind.METHOD_INVOCATION) {
            MethodInvocationTree methodInvocationTree = (MethodInvocationTree) ctx.getPath().getLeaf();
            ExpressionTree methodSelect = methodInvocationTree.getMethodSelect();
            //junit.framework.Assert.assertEquals
            if (methodSelect.getKind() == Tree.Kind.MEMBER_SELECT) {
                MemberSelectTree methodSelect1 = (MemberSelectTree) methodSelect;
                if (methodSelect1.getExpression().toString().equals("junit.framework.Assert")) {
                    Fix fix = new FixMethodInvocationImpl(TreePathHandle.create(ctx.getPath(), ctx.getInfo()), Bundle.ERR_UpgradeToOJAssert()).toEditorFix();
                    ErrorDescription forTree = ErrorDescriptionFactory.forTree(ctx, ctx.getPath(), Bundle.ERR_UpgradeToOJAssert(), fix);
                    return forTree;
                }
            }

        }
        //IMPORT
        if (ctx.getPath().getLeaf().getKind() == Tree.Kind.IMPORT) {
            TreePath importTP = ctx.getPath();
            ImportTree importTree = (ImportTree) importTP.getLeaf();
            MemberSelectTree importTree1 = (MemberSelectTree) importTree.getQualifiedIdentifier();
            if (importTree1.toString().startsWith("junit.framework.Assert.") || importTree1.toString().equals("junit.framework.Assert")) {
                Fix fix = new FixImportImpl(TreePathHandle.create(importTP, ctx.getInfo()), Bundle.ERR_UpgradeToOJAssert()).toEditorFix();
                ErrorDescription forTree = ErrorDescriptionFactory.forTree(ctx, importTP, Bundle.ERR_UpgradeToOJAssert(), fix);
                return forTree;
            }

        }
        return null;
    }

    static class FixImportImpl extends JavaFix {

        private final String label;

        FixImportImpl(TreePathHandle handle, String label) {
            super(handle, label);
            this.label = label;
        }

        @Override
        protected String getText() {

            return label;
        }

        @Override
        protected void performRewrite(JavaFix.TransformationContext ctx) throws Exception {
            TreePath path = ctx.getPath();

            CompilationUnitTree cu = ctx.getWorkingCopy().getCompilationUnit();
            TreeMaker tm = ctx.getWorkingCopy().getTreeMaker();
            ImportTree importTree = (ImportTree) path.getLeaf();
            ImportTree Import;
            String oldName = importTree.getQualifiedIdentifier().toString();
            if (oldName.equals("junit.framework.Assert")) {
                Import = tm.Import(tm.QualIdent("org.junit.Assert"), importTree.isStatic());
            } else if (oldName.equals("junit.framework.Assert.*")) {
                Import = tm.Import(tm.QualIdent("org.junit.Assert.*"), importTree.isStatic());
            } else if (oldName.startsWith("junit.framework.Assert.")) {
                Import = tm.Import(tm.QualIdent("org.junit.Assert." + oldName.replace("junit.framework.Assert.", "")), importTree.isStatic());
            } else {
                return;
            }
            CompilationUnitTree newCompilationUnit;
            newCompilationUnit = tm.removeCompUnitImport(cu, importTree);
            newCompilationUnit = tm.addCompUnitImport(newCompilationUnit, Import);

            ctx.getWorkingCopy().rewrite(cu, newCompilationUnit);
        }
    }

    static class FixMethodInvocationImpl extends JavaFix {

        private final String label;

        FixMethodInvocationImpl(TreePathHandle handle, String label) {
            super(handle, label);
            this.label = label;
        }

        @Override
        protected String getText() {

            return label;
        }

        @Override
        protected void performRewrite(JavaFix.TransformationContext ctx) throws Exception {
            TreePath path = ctx.getPath();

            TreeMaker tm = ctx.getWorkingCopy().getTreeMaker();

            MethodInvocationTree oldInvocation = (MethodInvocationTree) path.getLeaf();
            MemberSelectTree selector = ((MemberSelectTree) oldInvocation.getMethodSelect());

            ExpressionTree ms = tm.MemberSelect(tm.QualIdent("org.junit.Assert"), selector.getIdentifier());
            MethodInvocationTree newInvocation = tm.MethodInvocation(Collections.<ExpressionTree>emptyList(), ms, oldInvocation.getArguments());

            ctx.getWorkingCopy().rewrite(oldInvocation, newInvocation);
        }
    }

}
