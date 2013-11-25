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
