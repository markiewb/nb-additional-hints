package de.markiewb.netbeans.plugins.hints.literals;

import com.sun.source.tree.Scope;
import com.sun.source.tree.Tree;
import com.sun.source.util.SourcePositions;
import com.sun.source.util.TreePath;
import de.markiewb.netbeans.plugins.hints.common.ImportFQNsHack;
import de.markiewb.netbeans.plugins.hints.literals.BuildArgumentsVisitor;
import java.io.IOException;
import org.netbeans.api.java.source.CancellableTask;
import org.netbeans.api.java.source.JavaSource;
import org.netbeans.api.java.source.TreePathHandle;
import org.netbeans.api.java.source.WorkingCopy;
import org.netbeans.spi.editor.hints.ChangeInfo;
import org.netbeans.spi.editor.hints.Fix;
import org.openide.ErrorManager;
import org.openide.loaders.DataObject;

/**
 * @author markiewb
 */
public abstract class AbstractReplaceWithFix implements Fix {

    protected AbstractReplaceWithFix(TreePathHandle handle, DataObject od, BuildArgumentsVisitor.Result data) {
        this.handle = handle;
        this.od = od;
        this.data = data;
    }
    
    private TreePathHandle handle;
    private DataObject od;
    private final BuildArgumentsVisitor.Result data;

    public BuildArgumentsVisitor.Result getData() {
        return data;
    }
    @Override
    public final ChangeInfo implement() throws IOException {
        JavaSource js = JavaSource.forFileObject(od.getPrimaryFile());
        rewrite(js, getNewExpression());

        return null;
    }

    protected final void rewrite(JavaSource js, final String text) {
        try {
            js.runModificationTask(new CancellableTask<WorkingCopy>() {
                public void cancel() {
                }

                public void run(WorkingCopy cont) throws Exception {
                    cont.toPhase(JavaSource.Phase.RESOLVED);
                    TreePath path = handle.resolve(cont);
                    Scope context = cont.getTrees().getScope(path);
                    SourcePositions[] pos = new SourcePositions[1];
                    Tree t = cont.getTreeUtilities().parseExpression(text, pos);
                    cont.getTreeUtilities().attributeTree(t, context);
                    new ImportFQNsHack(cont).scan(new TreePath(path.getParentPath(), t), null);
                    cont.rewrite(path.getLeaf(), t);
                }
            }).
                    commit();
        } catch (IOException ex) {
            ErrorManager.getDefault().
                    notify(ex);
        }
    }

    protected abstract String getNewExpression();

}
