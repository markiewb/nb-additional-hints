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
package de.markiewb.netbeans.plugins.hints.structure;

import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreePath;
import de.markiewb.netbeans.plugins.hints.common.StringUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Modifier;
import org.netbeans.api.java.source.TreeMaker;
import org.netbeans.api.java.source.TreePathHandle;
import org.netbeans.api.java.source.WorkingCopy;
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
 * Removes superfluous modifiers (public, abstract) from method declarations within interfaces.
 *
 * @author markiewb
 */
@NbBundle.Messages({
    "# {0} - modifiers ",
    "# {1} - number of modifiers",
    "ERR_RemoveModsFromInterfaceParam=Remove {0} {1,choice,0#modifiers|1#modifier|1<modifiers}",
    "ERR_RemoveModsFromInterface=Remove public/abstract modifiers",
    "DN_RemoveModsFromInterface=Remove public/abstract modifiers from method declarations within interfaces",
    "DESC_RemoveModsFromInterface=Remove public/abstract modifiers from method declarations within interfaces. <p>JLS 9.4: <i>It is permitted, but discouraged as a matter of style, to redundantly specify the public and/or abstract modifier for a method declared in an interface.</i></p><p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",})
public class RemoveMethodModsFromIntf {

    @Hint(displayName = "#DN_RemoveModsFromInterface", description = "#DESC_RemoveModsFromInterface", category = "suggestions", hintKind = Hint.Kind.INSPECTION, severity = Severity.HINT)
    @TriggerTreeKind(Tree.Kind.METHOD)
    public static ErrorDescription convert(HintContext ctx) {

        TreePath path = ctx.getPath();

        if (path.getLeaf().getKind() != Tree.Kind.METHOD) {
            return null;
        }
        if (path.getParentPath().getLeaf().getKind() != Tree.Kind.INTERFACE) {
            return null;
        }

        ModifiersTree modifiers = ((MethodTree) path.getLeaf()).getModifiers();

        Set<Modifier> toBeRemoved = new HashSet<Modifier>();
        final Set<Modifier> flags = modifiers.getFlags();
        if (flags.contains(Modifier.ABSTRACT)) {
            toBeRemoved.add(Modifier.ABSTRACT);
        }
        if (flags.contains(Modifier.PUBLIC)) {
            toBeRemoved.add(Modifier.PUBLIC);
        }
        Fix fix;
        if (!toBeRemoved.isEmpty()) {
            fix = new FixImpl(TreePathHandle.create(ctx.getPath(), ctx.getInfo()), toBeRemoved).toEditorFix();
            return ErrorDescriptionFactory.forTree(ctx, ctx.getPath(), Bundle.ERR_RemoveModsFromInterface(), fix);
        } else {
            return null;
        }
    }

}

class FixImpl extends JavaFix {

    private final Set<Modifier> modifiers;

    FixImpl(TreePathHandle handle, Set<Modifier> modifiers) {
        super(handle);
        this.modifiers = modifiers;
    }

    @Override
    protected String getText() {
        List<String> list = new ArrayList<String>();
        for (Modifier modifier : modifiers) {
            list.add(modifier.toString());
        }
        

        
        return Bundle.ERR_RemoveModsFromInterfaceParam(StringUtils.join(list, ", "), modifiers.size());
    }

    @Override
    protected void performRewrite(JavaFix.TransformationContext ctx) throws Exception {
        TreePath path = ctx.getPath();

        WorkingCopy copy = ctx.getWorkingCopy();
        TreeMaker make = ctx.getWorkingCopy().getTreeMaker();
        ModifiersTree oldModifiersTree = ((MethodTree) path.getLeaf()).getModifiers();

        ModifiersTree newModifiersTree = oldModifiersTree;
        for (Modifier modifier : modifiers) {
            newModifiersTree = make.removeModifiersModifier(newModifiersTree, modifier);
        }

        copy.rewrite(oldModifiersTree, newModifiersTree);
    }
}
