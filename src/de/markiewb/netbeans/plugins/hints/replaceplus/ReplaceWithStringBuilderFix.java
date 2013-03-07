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

import de.markiewb.netbeans.plugins.hints.common.StringUtils;
import de.markiewb.netbeans.plugins.hints.literals.AbstractReplaceWithFix;
import de.markiewb.netbeans.plugins.hints.literals.BuildArgumentsVisitor;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.java.source.TreePathHandle;
import org.openide.loaders.DataObject;
import org.openide.util.NbBundle;

/**
 * Fix which converts patterns like
 * <pre>"Contains "+4+ "entries"</pre> into
 * <pre>new StringBuilder().append("Contains ").append(4).append(" entries").toString()</pre>.<br/>
 * Based on http://hg.netbeans.org/main/contrib/file/tip/editor.hints.i18n/src/org/netbeans/modules/editor/hints/i18n
 * from Jan Lahoda.
 *
 * @author markiewb
 */
public class ReplaceWithStringBuilderFix extends AbstractReplaceWithFix {

    public static ReplaceWithStringBuilderFix create(DataObject od, TreePathHandle handle, BuildArgumentsVisitor.Result data) {
        if (ReplaceWithStringBuilderFix.supports(data)) {
            return new ReplaceWithStringBuilderFix(od, handle, data);
        }
        return null;
    }

    private ReplaceWithStringBuilderFix(DataObject od, TreePathHandle handle, BuildArgumentsVisitor.Result data) {
        super(handle, od, data);
    }

    private static boolean supports(BuildArgumentsVisitor.Result data) {

        if (data.hasOnlyNonLiterals()) {
            //?? ignore zero-length string literals and 
            //ignore "plus" expressions without a literal
            return false;
        }

        return true;
    }

    @NbBundle.Messages({"LBL_ReplaceWithStringBuilderFix=Replace '+' with 'StringBuilder.append()'"})
    @Override
    public String getText() {
        return Bundle.LBL_ReplaceWithStringBuilderFix();
    }

    /**
     * Convert "ABC"+"DEF"+42+"GHI" to "ABCDEF"+42+"GHI"
     *
     * @param get
     * @return
     */
    private List<BuildArgumentsVisitor.TokenPair> concatSubsequentLiterals(List<BuildArgumentsVisitor.TokenPair> get) {
        List<BuildArgumentsVisitor.TokenPair> res = new ArrayList<BuildArgumentsVisitor.TokenPair>();

        boolean previousLiteral = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < get.size(); i++) {
            BuildArgumentsVisitor.TokenPair tokenPair = get.get(i);
            if (!tokenPair.isIsArgument()) {
                sb.append(tokenPair.getText());
                previousLiteral = true;
                continue;
            } else {
                if (previousLiteral) {
                    res.add(BuildArgumentsVisitor.TokenPair.createLiteral(StringUtils.escapeLF(sb.toString())));
                    previousLiteral = false;
                    sb = new StringBuilder();
                }
                res.add(BuildArgumentsVisitor.TokenPair.createArgument(tokenPair.getText()));
            }

        }
        if (previousLiteral) {
            res.add(BuildArgumentsVisitor.TokenPair.createLiteral(StringUtils.escapeLF(sb.toString())));
            previousLiteral = false;
            sb = new StringBuilder();
        }

        return res;
    }

    @Override
    protected String getNewExpression() {
        StringBuilder formatBuilder = new StringBuilder();
        final List<BuildArgumentsVisitor.TokenPair> get = getData().
                get();
        List<BuildArgumentsVisitor.TokenPair> filter = concatSubsequentLiterals(get);

        formatBuilder.append("new java.lang.StringBuilder()");
        for (BuildArgumentsVisitor.TokenPair pair : filter) {
            formatBuilder.append(".append(");
            if (!pair.isIsArgument()) {
                formatBuilder.append('"');
            }
            formatBuilder.append(pair.getText());
            if (!pair.isIsArgument()) {
                formatBuilder.append('"');
            }
            formatBuilder.append(")");
        }
        formatBuilder.append(".toString()");
        return formatBuilder.toString();
    }
}
