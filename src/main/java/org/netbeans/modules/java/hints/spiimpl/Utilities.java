/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2008-2010 Sun Microsystems, Inc. All rights reserved.
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
 * Portions Copyrighted 2008-2010 Sun Microsystems, Inc.
 */

package org.netbeans.modules.java.hints.spiimpl;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.AssignmentTree;
import com.sun.source.tree.BlockTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.ImportTree;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.NewArrayTree;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.Scope;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.Tree.Kind;
import com.sun.source.tree.TypeParameterTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.SourcePositions;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.TreeScanner;
import com.sun.source.util.Trees;
import com.sun.tools.javac.api.JavacScope;
import com.sun.tools.javac.api.JavacTaskImpl;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.comp.Attr;
import com.sun.tools.javac.comp.AttrContext;
import com.sun.tools.javac.comp.Env;
import com.sun.tools.javac.comp.Todo;
import com.sun.tools.javac.main.JavaCompiler;
import com.sun.tools.javac.parser.JavacParser;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Log;
import com.sun.tools.javadoc.Messager;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.platform.JavaPlatform;
import org.netbeans.api.java.platform.JavaPlatformManager;
import org.netbeans.api.java.queries.SourceForBinaryQuery;
import org.netbeans.api.java.queries.SourceForBinaryQuery.Result2;
import org.netbeans.api.java.source.ClasspathInfo;
import org.netbeans.api.java.source.CompilationInfo;
import org.netbeans.api.java.source.CompilationInfo.CacheClearPolicy;
import org.netbeans.api.java.source.SourceUtils;
import org.netbeans.api.java.source.TreeMaker;
import org.netbeans.spi.editor.hints.Severity;
import org.netbeans.spi.java.classpath.support.ClassPathSupport;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Lookup;
import org.openide.util.NbCollections;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Jan Lahoda
 */
public class Utilities {

    private Utilities() {}
    
    public static Set<Severity> disableErrors(FileObject file) {
        if (file.getAttribute(DISABLE_ERRORS) != null) {
            return EnumSet.allOf(Severity.class);
        }
        if (!file.canWrite() && FileUtil.getArchiveFile(file) != null) {
            return EnumSet.allOf(Severity.class);
        }

        return EnumSet.noneOf(Severity.class);
    }

    private static final String DISABLE_ERRORS = "disable-java-errors";
    

    public static <E> Iterable<E> checkedIterableByFilter(final Iterable raw, final Class<E> type, final boolean strict) {
        return new Iterable<E>() {
            public Iterator<E> iterator() {
                return NbCollections.checkedIteratorByFilter(raw.iterator(), type, strict);
            }
        };
    }
    
//    public static AnnotationTree constructConstraint(WorkingCopy wc, String name, TypeMirror tm) {
//        TreeMaker make = wc.getTreeMaker();
//        ExpressionTree variable = prepareAssignment(make, "variable", make.Literal(name));
//        ExpressionTree type     = prepareAssignment(make, "type", make.MemberSelect((ExpressionTree) make.Type(wc.getTypes().erasure(tm)), "class"));
//        TypeElement constraint  = wc.getElements().getTypeElement(Annotations.CONSTRAINT.toFQN());
//
//        return make.Annotation(make.QualIdent(constraint), Arrays.asList(variable, type));
//    }

    public static ExpressionTree prepareAssignment(TreeMaker make, String name, ExpressionTree value) {
        return make.Assignment(make.Identifier(name), value);
    }

    public static ExpressionTree findValue(AnnotationTree m, String name) {
        for (ExpressionTree et : m.getArguments()) {
            if (et.getKind() == Kind.ASSIGNMENT) {
                AssignmentTree at = (AssignmentTree) et;
                String varName = ((IdentifierTree) at.getVariable()).getName().toString();

                if (varName.equals(name)) {
                    return at.getExpression();
                }
            }

            if (et instanceof LiteralTree/*XXX*/ && "value".equals(name)) {
                return et;
            }
        }

        return null;
    }

    public static List<AnnotationTree> findArrayValue(AnnotationTree at, String name) {
        ExpressionTree fixesArray = findValue(at, name);
        List<AnnotationTree> fixes = new LinkedList<AnnotationTree>();

        if (fixesArray != null && fixesArray.getKind() == Kind.NEW_ARRAY) {
            NewArrayTree trees = (NewArrayTree) fixesArray;

            for (ExpressionTree fix : trees.getInitializers()) {
                if (fix.getKind() == Kind.ANNOTATION) {
                    fixes.add((AnnotationTree) fix);
                }
            }
        }

        if (fixesArray != null && fixesArray.getKind() == Kind.ANNOTATION) {
            fixes.add((AnnotationTree) fixesArray);
        }
        
        return fixes;
    }

    public static boolean isPureMemberSelect(Tree mst, boolean allowVariables) {
        switch (mst.getKind()) {
            case IDENTIFIER: return allowVariables || ((IdentifierTree) mst).getName().charAt(0) != '$';
            case MEMBER_SELECT: return isPureMemberSelect(((MemberSelectTree) mst).getExpression(), allowVariables);
            default: return false;
        }
    }


    

    
    




    static boolean isError(Element el) {
        return (el == null || (el.getKind() == ElementKind.CLASS) && isError(((TypeElement) el).asType()));
    }

    private static boolean isError(TypeMirror type) {
        return type == null || type.getKind() == TypeKind.ERROR;
    }

    private static boolean isStatement(String pattern) {
        return pattern.trim().endsWith(";");
    }

    private static boolean isErrorTree(Tree t) {
        return t.getKind() == Kind.ERRONEOUS || (t.getKind() == Kind.IDENTIFIER && ((IdentifierTree) t).getName().contentEquals("<error>")); //TODO: <error>...
    }
    
    private static boolean containsError(Tree t) {
        return new TreeScanner<Boolean, Void>() {
            @Override
            public Boolean scan(Tree node, Void p) {
                if (node != null && isErrorTree(node)) {
                    return true;
                }
                return super.scan(node, p) ==Boolean.TRUE;
            }
            @Override
            public Boolean reduce(Boolean r1, Boolean r2) {
                return r1 == Boolean.TRUE || r2 == Boolean.TRUE;
            }
        }.scan(t, null);
    }



    private static TypeMirror attributeTree(JavacTaskImpl jti, Tree tree, Scope scope, List<Diagnostic<? extends JavaFileObject>> errors) {
        Log log = Log.instance(jti.getContext());
        JavaFileObject prev = log.useSource(new DummyJFO());
        DiagnosticListener<? super JavaFileObject> oldDiag = log.getDiagnosticListener();
        int origNErrors = log.nerrors;
        int origNWarnings = log.nwarnings;
        boolean origDeferDiagnostic = log.deferDiagnostics;

        log.deferDiagnostics = false;
        log.setDiagnosticListener(new DiagnosticListenerImpl(errors));
        try {
            Attr attr = Attr.instance(jti.getContext());
            Env<AttrContext> env = ((JavacScope) scope).getEnv();
            if (tree instanceof JCExpression)
                return attr.attribExpr((JCTree) tree,env, Type.noType);
            return attr.attribStat((JCTree) tree,env);
        } finally {
            log.useSource(prev);
            log.setDiagnosticListener(oldDiag);
            log.nerrors = origNErrors;
            log.nwarnings = origNWarnings;
            log.deferDiagnostics = origDeferDiagnostic;
        }
    }

    public static @CheckForNull CharSequence getWildcardTreeName(@NonNull Tree t) {
        if (t.getKind() == Kind.EXPRESSION_STATEMENT && ((ExpressionStatementTree) t).getExpression().getKind() == Kind.IDENTIFIER) {
            IdentifierTree identTree = (IdentifierTree) ((ExpressionStatementTree) t).getExpression();
            
            return identTree.getName().toString();
        }

        if (t.getKind() == Kind.IDENTIFIER) {
            IdentifierTree identTree = (IdentifierTree) t;
            String name = identTree.getName().toString();

            if (name.startsWith("$")) {
                return name;
            }
        }
        
        if (t.getKind() == Kind.TYPE_PARAMETER) {
            String name = ((TypeParameterTree) t).getName().toString();

            if (name.startsWith("$")) {
                return name;
            }
        }

        return null;
    }

    public static boolean isMultistatementWildcard(@NonNull CharSequence name) {
        return name.charAt(name.length() - 1) == '$';
    }

    public static boolean isMultistatementWildcardTree(Tree tree) {
        CharSequence name = Utilities.getWildcardTreeName(tree);

        return name != null && Utilities.isMultistatementWildcard(name);
    }

    private static long inc;

    

    private static final class ScannerImpl extends TreePathScanner<Scope, CompilationInfo> {

        @Override
        public Scope visitBlock(BlockTree node, CompilationInfo p) {
            return p.getTrees().getScope(getCurrentPath());
        }

        @Override
        public Scope visitMethod(MethodTree node, CompilationInfo p) {
            if (node.getReturnType() == null) {
                return null;
            }
            return super.visitMethod(node, p);
        }

        @Override
        public Scope reduce(Scope r1, Scope r2) {
            return r1 != null ? r1 : r2;
        }

    }

    private static final class ScopeDescription {
        private final Map<String, TypeMirror> constraints;
        private final Iterable<? extends String> auxiliaryImports;

        public ScopeDescription(Map<String, TypeMirror> constraints, Iterable<? extends String> auxiliaryImports) {
            this.constraints = constraints;
            this.auxiliaryImports = auxiliaryImports;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ScopeDescription other = (ScopeDescription) obj;
            if (this.constraints != other.constraints && (this.constraints == null || !this.constraints.equals(other.constraints))) {
                return false;
            }
            if (this.auxiliaryImports != other.auxiliaryImports && (this.auxiliaryImports == null || !this.auxiliaryImports.equals(other.auxiliaryImports))) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 47 * hash + (this.constraints != null ? this.constraints.hashCode() : 0);
            hash = 47 * hash + (this.auxiliaryImports != null ? this.auxiliaryImports.hashCode() : 0);
            return hash;
        }

    }

//    private static Scope constructScope2(CompilationInfo info, Map<String, TypeMirror> constraints) {
//        JavacScope s = (JavacScope) info.getTrees().getScope(new TreePath(info.getCompilationUnit()));
//        Env<AttrContext> env = s.getEnv();
//
//        env = env.dup(env.tree);
//
//        env.info.
//    }

    public static String toHumanReadableTime(double d) {
        StringBuilder result = new StringBuilder();
        long inSeconds = (long) (d / 1000);
        int seconds = (int) (inSeconds % 60);
        long inMinutes = inSeconds / 60;
        int minutes = (int) (inMinutes % 60);
        long inHours = inMinutes / 60;

        if (inHours > 0) {
            result.append(inHours);
            result.append("h");
        }

        if (minutes > 0) {
            result.append(minutes);
            result.append("m");
        }
        
        result.append(seconds);
        result.append("s");

        return result.toString();
    }

    public static ClasspathInfo createUniversalCPInfo() {
        return Lookup.getDefault().lookup(SPI.class).createUniversalCPInfo();
    }

    @SuppressWarnings("deprecation")
    public static void waitScanFinished() throws InterruptedException {
        SourceUtils.waitScanFinished();
    }

    public static Set<? extends String> findSuppressedWarnings(CompilationInfo info, TreePath path) {
        //TODO: cache?
        Set<String> keys = new HashSet<String>();

        while (path != null) {
            Tree leaf = path.getLeaf();

            switch (leaf.getKind()) {
                case METHOD:
                    handleSuppressWarnings(info, path, ((MethodTree) leaf).getModifiers(), keys);
                    break;
                case CLASS:
                    handleSuppressWarnings(info, path, ((ClassTree) leaf).getModifiers(), keys);
                    break;
                case VARIABLE:
                    handleSuppressWarnings(info, path, ((VariableTree) leaf).getModifiers(), keys);
                    break;
            }

            path = path.getParentPath();
        }

        return Collections.unmodifiableSet(keys);
    }

    private static void handleSuppressWarnings(CompilationInfo info, TreePath path, ModifiersTree modifiers, final Set<String> keys) {
        Element el = info.getTrees().getElement(path);

        if (el == null) {
            return ;
        }

        for (AnnotationMirror am : el.getAnnotationMirrors()) {
            Name fqn = ((TypeElement) am.getAnnotationType().asElement()).getQualifiedName();
            
            if (!fqn.contentEquals("java.lang.SuppressWarnings")) {
                continue;
            }

            for (Entry<? extends ExecutableElement, ? extends AnnotationValue> e : am.getElementValues().entrySet()) {
                if (!e.getKey().getSimpleName().contentEquals("value"))
                    continue;

                e.getValue().accept(new AnnotationValueVisitor<Void, Void>() {
                    public Void visit(AnnotationValue av, Void p) {
                        av.accept(this, p);
                        return null;
                    }
                    public Void visit(AnnotationValue av) {
                        av.accept(this, null);
                        return null;
                    }
                    public Void visitBoolean(boolean b, Void p) {
                        return null;
                    }
                    public Void visitByte(byte b, Void p) {
                        return null;
                    }
                    public Void visitChar(char c, Void p) {
                        return null;
                    }
                    public Void visitDouble(double d, Void p) {
                        return null;
                    }
                    public Void visitFloat(float f, Void p) {
                        return null;
                    }
                    public Void visitInt(int i, Void p) {
                        return null;
                    }
                    public Void visitLong(long i, Void p) {
                        return null;
                    }
                    public Void visitShort(short s, Void p) {
                        return null;
                    }
                    public Void visitString(String s, Void p) {
                        keys.add(s);
                        return null;
                    }
                    public Void visitType(TypeMirror t, Void p) {
                        return null;
                    }
                    public Void visitEnumConstant(VariableElement c, Void p) {
                        return null;
                    }
                    public Void visitAnnotation(AnnotationMirror a, Void p) {
                        return null;
                    }
                    public Void visitArray(List<? extends AnnotationValue> vals, Void p) {
                        for (AnnotationValue av : vals) {
                            av.accept(this, p);
                        }
                        return null;
                    }
                    public Void visitUnknown(AnnotationValue av, Void p) {
                        return null;
                    }
                }, null);
            }
        }
    }

    

    

    

    public interface SPI {
        public ClasspathInfo createUniversalCPInfo();
    }

    @ServiceProvider(service=SPI.class)
    public static final class NbSPIImpl implements SPI {

        public ClasspathInfo createUniversalCPInfo() {
            JavaPlatform select = JavaPlatform.getDefault();

            if (select.getSpecification().getVersion() != null) {
                for (JavaPlatform p : JavaPlatformManager.getDefault().getInstalledPlatforms()) {
                    if (!"j2se".equals(p.getSpecification().getName()) || p.getSpecification().getVersion() == null) continue;
                    if (p.getSpecification().getVersion().compareTo(select.getSpecification().getVersion()) > 0) {
                        select = p;
                    }
                }
            }

            return ClasspathInfo.create(select.getBootstrapLibraries(), ClassPath.EMPTY, ClassPath.EMPTY);
        }

    }
    
    

    

    

    public static long patternValue(Tree pattern) {
        class VisitorImpl extends TreeScanner<Void, Void> {
            private int value;
            @Override
            public Void scan(Tree node, Void p) {
                if (node != null) value++;
                return super.scan(node, p);
            }
            @Override
            public Void visitIdentifier(IdentifierTree node, Void p) {
                if (node.getName().toString().startsWith("$")) value--;
                
                return super.visitIdentifier(node, p);
            }
            @Override
            public Void visitNewClass(NewClassTree node, Void p) {
                return null;
            }
        }

        VisitorImpl vi = new VisitorImpl();

        vi.scan(pattern, null);

        return vi.value;
    }

    public static boolean containsMultistatementTrees(List<? extends Tree> statements) {
        for (Tree t : statements) {
            if (Utilities.isMultistatementWildcardTree(t)) {
                return true;
            }
        }

        return false;
    }

/**
     * Note: may return {@code null}, if an intersection type is encountered, to indicate a 
     * real type cannot be created.
     * <br/>Copied from org.netbeans.modules.java.hints.errors.Utilities.resolveCapturedTypeInt(org.netbeans.api.java.source.CompilationInfo, javax.lang.model.type.TypeMirror):javax.lang.model.type.TypeMirror
     */
    public static TypeMirror resolveCapturedTypeInt(CompilationInfo info, TypeMirror tm) {
        if (tm == null) return tm;
        
        TypeMirror orig = SourceUtils.resolveCapturedType(tm);

        if (orig != null) {
            tm = orig;
        }
        
        if (tm.getKind() == TypeKind.WILDCARD) {
            TypeMirror extendsBound = ((WildcardType) tm).getExtendsBound();
            TypeMirror superBound = ((WildcardType) tm).getSuperBound();
            if (extendsBound != null || superBound != null) {
                TypeMirror rct = resolveCapturedTypeInt(info, extendsBound != null ? extendsBound : superBound);
                if (rct != null) {
                    switch (rct.getKind()) {
                        case WILDCARD:
                            return rct;
                        case ARRAY:
                        case DECLARED:
                        case ERROR:
                        case TYPEVAR:
                        case OTHER:
                            return info.getTypes().getWildcardType(
                                    extendsBound != null ? rct : null, superBound != null ? rct : null);
                    }
                } else {
                    // propagate failure out of all wildcards
                    return null;
                }
            }
        } 
        /*
        else if (tm.getKind() == TypeKind.INTERSECTION) {
            return null;
        }
        */
        
        if (tm.getKind() == TypeKind.DECLARED) {
            DeclaredType dt = (DeclaredType) tm;
            List<TypeMirror> typeArguments = new LinkedList<TypeMirror>();
            
            for (TypeMirror t : dt.getTypeArguments()) {
                TypeMirror targ = resolveCapturedTypeInt(info, t);
                if (targ == null) {
                    // bail out, if the type parameter is a wildcard, it's probably not possible
                    // to create a proper parametrized type from it
                    if (t.getKind() == TypeKind.WILDCARD /*|| t.getKind() == TypeKind.INTERSECTION*/) {
                        return null;
                    }
                    // use rawtype
                    typeArguments.clear();
                    break;
                }
                typeArguments.add(targ);
            }
            
            final TypeMirror enclosingType = dt.getEnclosingType();
            if (enclosingType.getKind() == TypeKind.DECLARED) {
                return info.getTypes().getDeclaredType((DeclaredType) enclosingType, (TypeElement) dt.asElement(), typeArguments.toArray(new TypeMirror[0]));
            } else {
                if (dt.asElement() == null) return dt;
                return info.getTypes().getDeclaredType((TypeElement) dt.asElement(), typeArguments.toArray(new TypeMirror[0]));
            }
        }

        if (tm.getKind() == TypeKind.ARRAY) {
            ArrayType at = (ArrayType) tm;
            TypeMirror tm2 = resolveCapturedTypeInt(info, at.getComponentType());
            return info.getTypes().getArrayType(tm2 != null ? tm2 : tm);
        }
        
        return tm;
    }


    private static final class DummyJFO extends SimpleJavaFileObject {
        private DummyJFO() {
            super(URI.create("dummy.java"), JavaFileObject.Kind.SOURCE);
        }
        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return "";
        }
    };

    /**
     * Only for members (i.e. generated constructor):
     */
    public static List<? extends Tree> filterHidden(TreePath basePath, Iterable<? extends Tree> members) {
        List<Tree> result = new LinkedList<Tree>();

        for (Tree t : members) {
            if (!isSynthetic(basePath != null ? basePath.getCompilationUnit() : null, t)) {
                result.add(t);
            }
        }

        return result;
    }

    private static boolean isSynthetic(CompilationUnitTree cut, Tree leaf) throws NullPointerException {
        JCTree tree = (JCTree) leaf;

        if (tree.pos == (-1))
            return true;

        if (leaf.getKind() == Kind.METHOD) {
            //check for synthetic constructor:
            return (((JCMethodDecl)leaf).mods.flags & Flags.GENERATEDCONSTR) != 0L;
        }

        //check for synthetic superconstructor call:
        if (cut != null && leaf.getKind() == Kind.EXPRESSION_STATEMENT) {
            ExpressionStatementTree est = (ExpressionStatementTree) leaf;

            if (est.getExpression().getKind() == Kind.METHOD_INVOCATION) {
                MethodInvocationTree mit = (MethodInvocationTree) est.getExpression();

                if (mit.getMethodSelect().getKind() == Kind.IDENTIFIER) {
                    IdentifierTree it = (IdentifierTree) mit.getMethodSelect();

                    if ("super".equals(it.getName().toString())) {
                        return ((JCCompilationUnit) cut).endPositions.get(tree) == (-1);
                    }
                }
            }
        }

        return false;
    }

    public static boolean isFakeBlock(Tree t) {
        if (!(t instanceof BlockTree)) {
            return false;
        }

        BlockTree bt = (BlockTree) t;

        if (bt.getStatements().isEmpty()) {
            return false;
        }

        CharSequence wildcardTreeName = Utilities.getWildcardTreeName(bt.getStatements().get(0));

        if (wildcardTreeName == null) {
            return false;
        }

        return wildcardTreeName.toString().startsWith("$$");
    }

    public static boolean isFakeClass(Tree t) {
        if (!(t instanceof ClassTree)) {
            return false;
        }

        ClassTree ct = (ClassTree) t;

        if (ct.getMembers().isEmpty()) {
            return false;
        }

        CharSequence wildcardTreeName = Utilities.getWildcardTreeName(ct.getMembers().get(0));

        if (wildcardTreeName == null) {
            return false;
        }

        return wildcardTreeName.toString().startsWith("$$");
    }

    private static final class DiagnosticListenerImpl implements DiagnosticListener<JavaFileObject> {
        private final Collection<Diagnostic<? extends JavaFileObject>> errors;

        public DiagnosticListenerImpl(Collection<Diagnostic<? extends JavaFileObject>> errors) {
            this.errors = errors;
        }

        public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
            errors.add(diagnostic);
        }
    }

    private static final class OffsetSourcePositions implements SourcePositions {

        private final SourcePositions delegate;
        private final long offset;

        public OffsetSourcePositions(SourcePositions delegate, long offset) {
            this.delegate = delegate;
            this.offset = offset;
        }

        public long getStartPosition(CompilationUnitTree cut, Tree tree) {
            return delegate.getStartPosition(cut, tree) + offset;
        }

        public long getEndPosition(CompilationUnitTree cut, Tree tree) {
            return delegate.getEndPosition(cut, tree) + offset;
        }

    }

    private static final class OffsetDiagnostic<S> implements Diagnostic<S> {
        private final Diagnostic<? extends S> delegate;
        private final long offset;

        public OffsetDiagnostic(Diagnostic<? extends S> delegate, long offset) {
            this.delegate = delegate;
            this.offset = offset;
        }

        public Diagnostic.Kind getKind() {
            return delegate.getKind();
        }

        public S getSource() {
            return delegate.getSource();
        }

        public long getPosition() {
            return delegate.getPosition() + offset;
        }

        public long getStartPosition() {
            return delegate.getStartPosition() + offset;
        }

        public long getEndPosition() {
            return delegate.getEndPosition() + offset;
        }

        public long getLineNumber() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public long getColumnNumber() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getCode() {
            return delegate.getCode();
        }

        public String getMessage(Locale locale) {
            return delegate.getMessage(locale);
        }

    }

    private static class ParserSourcePositions implements SourcePositions {

        private JavacParser parser;

        private ParserSourcePositions(JavacParser parser) {
            this.parser = parser;
        }

        public long getStartPosition(CompilationUnitTree file, Tree tree) {
            return parser.getStartPos((JCTree)tree);
        }

        public long getEndPosition(CompilationUnitTree file, Tree tree) {
            return parser.getEndPos((JCTree)tree);
        }
    }
}
