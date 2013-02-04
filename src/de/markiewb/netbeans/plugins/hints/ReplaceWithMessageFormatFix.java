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
package de.markiewb.netbeans.plugins.hints;

import de.markiewb.netbeans.plugins.hints.common.StringUtils;
import de.markiewb.netbeans.plugins.hints.common.ImportFQNsHack;
import com.sun.source.tree.Scope;
import com.sun.source.tree.Tree;
import com.sun.source.util.SourcePositions;
import com.sun.source.util.TreePath;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.netbeans.api.java.source.CancellableTask;
import org.netbeans.api.java.source.JavaSource;
import org.netbeans.api.java.source.JavaSource.Phase;
import org.netbeans.api.java.source.TreePathHandle;
import org.netbeans.api.java.source.WorkingCopy;
import org.netbeans.spi.editor.hints.ChangeInfo;
import org.netbeans.spi.editor.hints.Fix;
import org.openide.ErrorManager;
import org.openide.loaders.DataObject;
import org.openide.util.MapFormat;

/**
 * Based on
 * http://hg.netbeans.org/main/contrib/file/tip/editor.hints.i18n/src/org/netbeans/modules/editor/hints/i18n
 *
 * @author Jan Lahoda
 */
public class ReplaceWithMessageFormatFix implements Fix {

    private DataObject od;
    private TreePathHandle handle;
    private String format;
    private List<String> argument;

    public ReplaceWithMessageFormatFix(DataObject od, TreePathHandle handle, String format, List<String> argument) {
	super();
	this.od = od;
	this.handle = handle;
	this.format = format;
	this.argument = argument;
    }

    @Override
    public String getText() {
	return "Replace '+' with 'java.text.MessageFormat.format()'";
    }

    @Override
    public ChangeInfo implement() throws IOException {

	JavaSource js = JavaSource.forFileObject(od.getPrimaryFile());

	String _format = "java.text.MessageFormat.format(\"{format}\"{arguments})";

	Map<String, String> table = new HashMap<String, String>();
	table.put("format", format); // NOI18N

	StringBuilder arguments = new StringBuilder();
	arguments.append(StringUtils.join(argument, ", "));
	if (argument.isEmpty()) {
	    table.put("arguments", ""); // NOI18N
	} else {
	    table.put("arguments", ", " + arguments.toString()); // NOI18N
	}

	final String text = new MapFormat(table).format(_format);
	rewrite(js, text);

	return null;
    }

    private void rewrite(JavaSource js, final String text) {
	try {

	    js.runModificationTask(new CancellableTask<WorkingCopy>() {
		public void cancel() {
		}

		public void run(WorkingCopy cont) throws Exception {
		    cont.toPhase(Phase.PARSED);
		    TreePath path = handle.resolve(cont);
		    Scope context = cont.getTrees().getScope(path);
		    SourcePositions[] pos = new SourcePositions[1];

		    Tree t = cont.getTreeUtilities().parseExpression(text, pos);
		    cont.getTreeUtilities().attributeTree(t, context);

		    new ImportFQNsHack(cont).scan(new TreePath(path.getParentPath(), t), null);

		    cont.rewrite(path.getLeaf(), t);
		}
	    }).commit();


	} catch (IOException ex) {
	    ErrorManager.getDefault().notify(ex);
	}
    }
}
