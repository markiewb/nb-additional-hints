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
package de.markiewb.netbeans.plugins.hints.modifiers;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.SourcePositions;
import com.sun.source.util.TreePath;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;
import org.netbeans.spi.java.hints.HintContext;

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
    
    
    /**
     * Workaround: Check if at line of class declaration and not in body of class.
     * @param path
     * @param ctx
     * @return 
     */
    static boolean isCaretAtClassDeclaration(TreePath path, HintContext ctx) {
        SourcePositions sourcePositions = ctx.getInfo().getTrees().getSourcePositions();
        int startPos = (int) sourcePositions.getStartPosition(path.getCompilationUnit(), path.getLeaf());
        int caret = ctx.getCaretLocation();
        String code = ctx.getInfo().getText();
        if (startPos < 0 || caret < 0 || caret < startPos || caret >= code.length()) {
            return false;
        }
        //Workaround: Check if at line of class declaration and not in body of class
        if (code.substring(startPos, caret).contains("{")) {
            return false;
        }
        return true;
    }
}
