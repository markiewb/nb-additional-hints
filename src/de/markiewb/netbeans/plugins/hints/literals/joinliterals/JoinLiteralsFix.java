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
 */
package de.markiewb.netbeans.plugins.hints.literals.joinliterals;

import de.markiewb.netbeans.plugins.hints.common.StringUtils;
import de.markiewb.netbeans.plugins.hints.literals.AbstractReplaceWithFix;
import de.markiewb.netbeans.plugins.hints.literals.BuildArgumentsVisitor;
import org.netbeans.api.java.source.TreePathHandle;
import org.openide.loaders.DataObject;
import org.openide.util.NbBundle;

/**
 * Fix which converts patterns like
 * <pre>"Foo "+"bar"+"bla"</pre> into
 * <pre>"Foo barbla"</pre>.<br/>
 * Based on http://hg.netbeans.org/main/contrib/file/tip/editor.hints.i18n/src/org/netbeans/modules/editor/hints/i18n
 * from Jan Lahoda.
 *
 * @author markiewb
 */
public class JoinLiteralsFix extends AbstractReplaceWithFix {

    public static JoinLiteralsFix create(DataObject od, TreePathHandle handle, BuildArgumentsVisitor.Result data) {
        if (JoinLiteralsFix.supports(data)) {
            return new JoinLiteralsFix(od, handle, data);
        }
        return null;
    }

    private JoinLiteralsFix(DataObject od, TreePathHandle handle, BuildArgumentsVisitor.Result data) {
        super(handle, od, data);
    }

    private static boolean supports(BuildArgumentsVisitor.Result data) {

        if (data.hasOnlyNonLiterals()) {
            //?? ignore zero-length string literals and 
            //ignore "plus" expressions without a literal
            return false;
        }
        if (data.getArguments().isEmpty()) {
            //only literals can be joined
            return true;
        }

        //ignore others
        return false;
    }

    @NbBundle.Messages({"LBL_JoinLiterals=Join literals"})
    @Override
    public String getText() {
        return Bundle.LBL_JoinLiterals();
    }

    @Override
    protected String getNewExpression() {
        StringBuilder formatBuilder = new StringBuilder();

        formatBuilder.append("\"");
        for (BuildArgumentsVisitor.TokenPair pair : getData().get()) {
            formatBuilder.append(StringUtils.escapeLF(pair.getText()));
        }
        formatBuilder.append("\"");
        return formatBuilder.toString();
    }

}
