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

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.ParameterizedTypeTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePath;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import org.netbeans.api.java.source.CancellableTask;
import org.netbeans.api.java.source.CompilationInfo;
import org.netbeans.api.java.source.JavaSource;
import org.netbeans.api.java.source.SourceUtils;
import org.netbeans.api.java.source.TreeMaker;
import org.netbeans.api.java.source.TreePathHandle;
import org.netbeans.api.java.source.TreeUtilities;
import org.netbeans.api.java.source.TypeUtilities;
import org.netbeans.api.java.source.WorkingCopy;
import static org.netbeans.modules.java.hints.spiimpl.Utilities.resolveCapturedTypeInt;
import org.netbeans.modules.parsing.api.Source;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
import org.netbeans.spi.java.hints.ErrorDescriptionFactory;
import org.netbeans.spi.java.hints.Hint;
import org.netbeans.spi.java.hints.HintContext;
import org.netbeans.spi.java.hints.JavaFix;
import org.netbeans.spi.java.hints.JavaFixUtilities;
import org.netbeans.spi.java.hints.TriggerTreeKind;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

/**
 *
 * @author markiewb
 */
@NbBundle.Messages({
    "DN_ChangeTypeOfVariable=Change type of variable",
    "DESC_ChangeTypeOfVariable=Changes type of variable <p>Provided by <a href=\"https://github.com/markiewb/nb-additional-hints\">nb-additional-hints</a> plugin</p>",})
public class ChangeTypeOfVariable {

    private static EnumSet<ElementKind> supportedKinds = EnumSet.of(ElementKind.LOCAL_VARIABLE, ElementKind.CLASS, ElementKind.INTERFACE);

    @Hint(displayName = "#DN_ChangeTypeOfVariable", description = "#DESC_ChangeTypeOfVariable", category = "suggestions", hintKind = Hint.Kind.ACTION, severity = Severity.HINT, enabled = true)
    @NbBundle.Messages({"# {0} - to",
        "ERR_ChangeTypeOfVariable=Change to {0}"})
    @TriggerTreeKind(Tree.Kind.IDENTIFIER)
    public static ErrorDescription hint(HintContext ctx) {
        TreePath path = ctx.getPath();

        supportedKinds = EnumSet.of(ElementKind.LOCAL_VARIABLE, ElementKind.CLASS, ElementKind.INTERFACE);
        Element element = ctx.getInfo().getTrees().getElement(path);
        if (!supportedKinds.contains(element.getKind())) {
            return null;
        }

        TypeMirror asType = element.asType();
        DeclaredType originaldt = (DeclaredType) asType;

        Types types = ctx.getInfo().getTypes();
        List<Fix> fixes = new ArrayList<Fix>();
        Set<TypeMirrorWrapper> superTypes = new LinkedHashSet<TypeMirrorWrapper>();
        superTypes.addAll(getSuperTypes(types, asType, ElementKind.INTERFACE));
        superTypes.addAll(getSuperTypes(types, asType, ElementKind.CLASS));
        for (TypeMirrorWrapper typeMirrorWrapper : superTypes) {

            TypeMirror typeMirror = typeMirrorWrapper.getDelegate();
            if (typeMirror.getKind() != TypeKind.DECLARED) {
                continue;
            }
            DeclaredType dt = (DeclaredType) typeMirror;
            TypeElement e = (TypeElement) dt.asElement();
            String qn = e.getQualifiedName().toString();

            String typeName = qn;

            //FIXME add option for this
            if (Object.class.getName().equals(typeName)) {
                continue;
            }

//            TreePath myPath = path.getParentPath();
            TreePath myPath = path.getParentPath();
            final List<? extends TypeMirror> oldTypeArguments = originaldt.getTypeArguments();
            final List<? extends TypeMirror> newTypeArguments = dt.getTypeArguments();
            for (TypeMirror newTypeMirror : newTypeArguments) {
//                newTypeMirror.getKind()==TypeKind.TYPEVAR;
                TypeMirror resolveCapturedTypeInt = resolveCapturedTypeInt(ctx.getInfo(), newTypeMirror);
                Logger.getLogger(ChangeTypeOfVariable.class.getName()).log(Level.INFO, "{0} {1}", new Object[]{resolveCapturedTypeInt, newTypeMirror});
            }

            //ArrayList<String> -> List<E> -> List<String>
//            SourceUtils.
//            org.netbeans.modules.java.hints.spiimpl.Utilities.
//            TypeUtilities tu;
//            tu.
            if (!oldTypeArguments.isEmpty()) {
                //ignore types with type arguments like List<String>
                //FIXME: had to disable this because of issues
                if (oldTypeArguments.size() == 1 && newTypeArguments.size() == 1) {
                    TypeMirror next = newTypeArguments.iterator().next();
                    TypeMirror old = newTypeArguments.iterator().next();
                    boolean checkTypesAssignable = SourceUtils.checkTypesAssignable(ctx.getInfo(), old, next);
                    if (!checkTypesAssignable) {
                        continue;
                    }
                } else {

                    continue;
                }
            }
            //Cornercase:
            if (newTypeArguments.isEmpty() && !oldTypeArguments.isEmpty()) {
                //HashMap<String, Integer> -> Cloneable
                //also replace typeparameters
//                myPath = path.getParentPath();
            }
            //FIXME cornercase
            //String -> Comparable<String>
            StringBuilder newTypeName = new StringBuilder();
            newTypeName.append(typeName);
            //add type arguments like Map<String, Integer>
            StringBuilder sb = new StringBuilder();
            sb.append(typeName);
            if (!newTypeArguments.isEmpty()) {
                sb.append("<").append(newTypeArguments).append(">");
            }
//             String newTypeFormatted = newTypeName.toString().replace("<", "&lt;");
            String newTypeFormatted = sb.toString().replace("<", "&lt;");
            Logger.getLogger(ChangeTypeOfVariable.class.getName()).log(Level.INFO, "message{0}", newTypeName.toString());

            final TreePathHandle create = TreePathHandle.create(myPath, ctx.getInfo());

//            Fix fix = org.netbeans.spi.java.hints.JavaFixUtilities.rewriteFix(ctx, Bundle.ERR_ChangeTypeOfVariable(newTypeFormatted), myPath, sb.toString());
            Fix fix=new MyFix(ctx.getInfo(), myPath, typeName, dt).toEditorFix();
            fixes.add(fix);
        }
        if (!fixes.isEmpty()) {
            Fix[] fixs = fixes.toArray(new Fix[fixes.size()]);

            return ErrorDescriptionFactory.forName(ctx, path, Bundle.DN_ChangeTypeOfVariable(), fixs);
        }
        return null;
    }

    static class MyFix extends JavaFix {
        private String typeName;
        private DeclaredType dt;

        MyFix(CompilationInfo info, TreePath tp, String typeName, DeclaredType dt) {
            super(info, tp);
            this.typeName = typeName;
            this.dt = dt;
        }

        @Override
        protected String getText() {
            return "XXX;" + typeName;
        }

        @Override
        protected void performRewrite(TransformationContext ctx) throws Exception {
            WorkingCopy wc = ctx.getWorkingCopy();

            wc.toPhase(JavaSource.Phase.RESOLVED);
            
            
            TypeElement e = (TypeElement) dt.asElement();
            String qn = e.getQualifiedName().toString();
            TreePath resolve = ctx.getPath();
            if (Tree.Kind.PARAMETERIZED_TYPE != resolve.getLeaf().getKind()) {
                return;
            }

            ParameterizedTypeTree oldVar = (ParameterizedTypeTree) resolve.getLeaf();
//oldVar.
            TreeMaker tm = wc.getTreeMaker();
            List<? extends Tree> typeArguments = oldVar.getTypeArguments();
//            ModifiersTree mods = oldVar.getModifiers();
//            Name name = oldVar.getName();
            Tree type = oldVar.getType();
//                        ExpressionTree init = oldVar.getInitializer();
//            ExpressionTree init = null;
            ExpressionTree QualIdent = tm.QualIdent(typeName);
            List<? extends TypeMirror> typeArguments1 = dt.getTypeArguments();
            List<Tree> typeMirrorList = new ArrayList<Tree>();
            

            for (TypeMirror typeMirror : typeArguments1) {
                Tree t = tm.Type(typeMirror);
                typeMirrorList.add(t);
            }
            
            ParameterizedTypeTree newVar = tm.ParameterizedType(QualIdent, typeMirrorList);
            wc.rewrite(oldVar, newVar);
//                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

static class TypeMirrorWrapper implements Comparable<TypeMirrorWrapper> {

    private final TypeMirror delegate;
    private final Types types;

    private TypeMirrorWrapper(TypeMirror delegate, Types types) {
        this.delegate = delegate;
        this.types = types;
    }

    public TypeMirror getDelegate() {
        return delegate;
    }

    @Override
    public boolean equals(Object obj) {
        return this.delegate.toString().equals(((TypeMirrorWrapper) obj).delegate.toString());
    }

    @Override
    public int hashCode() {
        return this.delegate.toString().hashCode();
    }

    @Override
    public int compareTo(TypeMirrorWrapper o) {
        TypeMirror a = this.delegate;
        TypeMirror b = o.delegate;

        return ((types.isAssignable(b, a)) ? +1 : -1);
    }

}

private static Collection<TypeMirrorWrapper> getSuperTypes(Types types, TypeMirror asType, ElementKind type) {

        Collection<TypeMirrorWrapper> result = new TreeSet<TypeMirrorWrapper>();

        List<? extends TypeMirror> directSupertypes = types.directSupertypes(asType);
        for (TypeMirror typeMirror : directSupertypes) {
            if (type.equals(types.asElement(typeMirror).getKind())) {
                result.add(new TypeMirrorWrapper(typeMirror, types));
            }
        }
        //recursion to get super super super types
        for (TypeMirror typeMirror : directSupertypes) {
            result.addAll(getSuperTypes(types, typeMirror, type));
        }
        return result;
    }

}
