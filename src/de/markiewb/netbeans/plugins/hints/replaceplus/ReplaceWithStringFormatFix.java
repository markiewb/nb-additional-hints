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
import de.markiewb.netbeans.plugins.hints.literals.BuildArgumentsVisitor.Result;
import java.util.HashMap;
import java.util.Map;
import org.netbeans.api.java.source.TreePathHandle;
import org.openide.loaders.DataObject;
import org.openide.util.MapFormat;
import org.openide.util.NbBundle;

/**
 * Fix which converts patterns like
 * <pre>"Contains "+4+ "entries"</pre> into
 * <pre>String.format("Contains %s entries",4)</pre>.<br/>
 * Based on http://hg.netbeans.org/main/contrib/file/tip/editor.hints.i18n/src/org/netbeans/modules/editor/hints/i18n
 * from Jan Lahoda.
 *
 * @author Jan Lahoda
 */
public class ReplaceWithStringFormatFix extends AbstractReplaceWithFix {

    public static ReplaceWithStringFormatFix create(DataObject od, TreePathHandle handle, BuildArgumentsVisitor.Result data) {
        if (ReplaceWithStringFormatFix.supports(data)) {
            return new ReplaceWithStringFormatFix(od, handle, data);
        }
        return null;
    }

    private ReplaceWithStringFormatFix(DataObject od, TreePathHandle handle, BuildArgumentsVisitor.Result data) {
        super(handle, od, data);
    }

    private static boolean supports(BuildArgumentsVisitor.Result data) {

        if (data.hasOnlyNonLiterals()) {
            //?? ignore zero-length string literals and 
            //ignore "plus" expressions without a literal like 4 + 5
            return false;
        }
        return true;
    }

    @NbBundle.Messages({"LBL_ReplaceWithStringFormatFix=Replace '+' with 'String.format()'"})
    @Override
    public String getText() {
        return Bundle.LBL_ReplaceWithStringFormatFix();
    }

    private String createFormat(BuildArgumentsVisitor.Result data) {
        //
        StringBuilder formatBuilder = new StringBuilder();
        for (BuildArgumentsVisitor.TokenPair pair : data.get()) {
            if (pair.isIsArgument()) {
                formatBuilder.append("%s");
            } else {
                formatBuilder.append(StringUtils.escapeLF(pair.getText()));
            }
        }
        String format = formatBuilder.toString();
        return format;
    }

    private String createArguments(Result data1) {
        if (data1.getArguments().
                isEmpty()) {
            return "";
        }
        return new StringBuilder().append(", ").
                append(StringUtils.join(data1.getArguments(), ", ")).
                toString();
    }

    @Override
    protected String getNewExpression() {
        Map<String, String> table = new HashMap<String, String>();

        table.put("format", createFormat(getData())); // NOI18N
        table.put("arguments", createArguments(getData())); // NOI18N

        String _formatTemplate = "java.lang.String.format(\"{format}\"{arguments})";
        final String text = new MapFormat(table).format(_formatTemplate);
        return text;
    }
}
