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
 * "Portions Copyrighted 2013 rasa-silva"
 * "Portions Copyrighted 2013 Benno Markiewicz"
 */
package de.markiewb.netbeans.plugins.hints.modifiers;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePath;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;

/**
 * 
 * @author https://github.com/rasa-silva
 */
class ModifierUtils {

    static boolean isTopLevelClass(Element element) {

        if (element == null) {
            return false;
        }

        if (element.getKind() == ElementKind.CLASS) {
            TypeElement clazz = (TypeElement) element;
            if (clazz.getNestingKind() == NestingKind.TOP_LEVEL) {
                return true;
            }
        }

        return false;
    }

    static ModifiersTree getModifiersTree(TreePath path, Element element) {
        ModifiersTree modifiers = null;
        Tree leaf = path.getLeaf();
        switch (element.getKind()) {
            case FIELD:
                modifiers = ((VariableTree) leaf).getModifiers();
                break;
            case CLASS:
                modifiers = ((ClassTree) leaf).getModifiers();
                break;
            case METHOD:
                modifiers = ((MethodTree) leaf).getModifiers();
                break;
        }

        return modifiers;
    }
}
