package de.markiewb.netbeans.plugins.hints.arrays;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.NewArrayTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreePath;
import de.markiewb.netbeans.plugins.hints.tochar.*;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.copy;
import java.util.List;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.netbeans.api.java.source.CompilationInfo;
import org.netbeans.api.java.source.GeneratorUtilities;
import org.netbeans.api.java.source.TreeMaker;
import org.netbeans.api.java.source.TreePathHandle;
import org.netbeans.api.java.source.TypeMirrorHandle;
import org.netbeans.api.java.source.WorkingCopy;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
import org.netbeans.spi.java.hints.ErrorDescriptionFactory;
import static org.netbeans.spi.java.hints.ErrorDescriptionFactory.forName;
import org.netbeans.spi.java.hints.Hint;
import org.netbeans.spi.java.hints.HintContext;
import org.netbeans.spi.java.hints.JavaFix;
import org.netbeans.spi.java.hints.JavaFix.TransformationContext;
import static org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix;
import org.netbeans.spi.java.hints.TriggerPattern;
import org.netbeans.spi.java.hints.TriggerTreeKind;
import org.openide.util.NbBundle.Messages;

@Messages({
    "# {0} - class",
    "ERR_ToArrayZero=Replace with 'new {0}[0]'",
    "DN_ToArrayZero=Change array declaration to zero-based form f.e new Object[0]",
    "DESC_ToArrayZero=Changes an array declaration to a zero-based form. For example <tt>'new String[] {}'</tt> will be transformed to <tt>\"new String[0]\"</tt>",
    "ERR_ToArrayEmpty=Convert string to char",
    "DN_ToArrayEmpty=Convert string to char",
    "DESC_ToArrayEmpty=Converts a single string literal to a char. For example <tt>\"c\"</tt> will be transformed to <tt>'c'</tt>",})
public class NewArrayFix {

    @Hint(displayName = "#DN_ToArrayZero", description = "#DESC_ToArrayZero", category = "Suggestions", hintKind = Hint.Kind.INSPECTION, severity = Severity.HINT)
    @TriggerPattern("new $type[] {}")
    public static ErrorDescription toZeroArray(HintContext ctx) {
        
        
        final String clazz = ctx.getVariableNames().get("$type");

//        Fix fix = rewriteFix(ctx, Bundle.ERR_ToArrayZero(clazz), ctx.getPath(), "new $type[0]");
        TypeMirror resType = ctx.getInfo().getTrees().getTypeMirror(ctx.getPath()); 
        ChangeArrayTypeFix fix = new ChangeArrayTypeFix(TreePathHandle.create(ctx.getPath(), ctx.getInfo()), TypeMirrorHandle.create(resType), null);
//        return forName(ctx, ctx.getPath(), Bundle.ERR_ToArrayZero(clazz), fix);
        return org.netbeans.spi.editor.hints.ErrorDescriptionFactory.forTree(ctx, ctx.getPath(), "MSG", fix); 
    } 

    
    private static class ChangeArrayTypeFix extends JavaFix {
        private final TypeMirrorHandle ctype;
        private final TreePathHandle colReference;
        private final String typeName;

        public ChangeArrayTypeFix(TreePathHandle handle, TypeMirrorHandle ctype, String typeName) {
            super(handle);
            this.ctype = ctype;
            this.typeName = typeName;
            this.colReference = colReference;
        }

        @Override
        protected String getText() {
            return Bundle.ERR_ToArrayZero(typeName);
        }
        
        private int numberOfDimensions(TypeMirror arr) {
            int dim = 0;
            while (arr.getKind() == TypeKind.ARRAY) {
                arr = ((ArrayType)arr).getComponentType();
                dim++;
            }
            return dim;
        }
        
        private void rewriteNewArrayTree(WorkingCopy copy, TreeMaker mk, TreePath natPath, TypeMirror compType) {
            NewArrayTree nat = (NewArrayTree)natPath.getLeaf();
            TypeMirror existing = copy.getTrees().getTypeMirror(natPath);
            int existingDim = numberOfDimensions(existing);
            int newDim = numberOfDimensions(compType);
            
            if (existingDim == newDim + 1 /* newDim is counted from component type, lacks the enclosing array */) {
                // simple, number of dimensions does not change
                copy.rewrite(nat.getType(), mk.Type(compType));
                return;
            }
            List<ExpressionTree> l = new ArrayList<ExpressionTree>(nat.getDimensions().subList(
                    0, Math.min(newDim + 1, nat.getDimensions().size())));
            Tree replacement = mk.NewArray(mk.Type(compType), l, null);
            GeneratorUtilities.get(copy).copyComments(nat, replacement, true);
            GeneratorUtilities.get(copy).copyComments(nat, replacement, false);
            
            copy.rewrite(nat, replacement);
        }

        @Override
        protected void performRewrite(TransformationContext ctx) throws Exception {
            WorkingCopy wc = ctx.getWorkingCopy();
            TreePath path = ctx.getPath();
            TypeMirror compType = ctype.resolve(wc);
            if (compType == null) {
                return;
            }
            TreeMaker mk = wc.getTreeMaker();
            Tree l = path.getLeaf();
            if (l.getKind() == Tree.Kind.NEW_ARRAY) {
                NewArrayTree nat = (NewArrayTree)l;
                // if there are some initializers, we should probably rewrite the whole expression.
                if (nat.getInitializers() == null) {
                    rewriteNewArrayTree(wc, mk, path, compType);
                    return;
                }
            }
            if (colReference == null) {
                return;
            }
            // replace the entire tree
            TreePath colRef = colReference.resolve(wc);
            GeneratorUtilities gu = GeneratorUtilities.get(wc);
            Tree lc = gu.importComments(l, wc.getCompilationUnit());
            Tree newArrayTree = mk.NewArray(mk.Type(compType), Collections.<ExpressionTree>singletonList(
                    mk.MethodInvocation(Collections.<ExpressionTree>emptyList(), 
                        mk.MemberSelect((ExpressionTree)colRef.getLeaf(), "size"), // NOI18N
                        Collections.<ExpressionTree>emptyList())),
                    null);
            gu.copyComments(lc, newArrayTree, true);
            gu.copyComments(lc, newArrayTree, false);
            wc.rewrite(lc, newArrayTree);
        }
        
    } 
    
    @Hint(displayName = "#DN_ToArrayEmpty", description = "#DESC_ToArrayEmpty", category = "Suggestions", hintKind = Hint.Kind.INSPECTION, severity = Severity.HINT)
    @TriggerTreeKind(Tree.Kind.STRING_LITERAL)
    public static ErrorDescription convertFromStringToArrayEmpty(HintContext ctx) {

        LiteralTree lit = (LiteralTree) ctx.getPath().getLeaf();
        String toString = lit.getValue().toString();
        if (toString.length() == 1) {
            Fix fix = rewriteFix(ctx, Bundle.ERR_ToArrayEmpty(), ctx.getPath(), "'" + toString + "'");
            return forName(ctx, ctx.getPath(), Bundle.ERR_ToArrayEmpty(), fix);
        } else {
            return null;
        }
    }

}
