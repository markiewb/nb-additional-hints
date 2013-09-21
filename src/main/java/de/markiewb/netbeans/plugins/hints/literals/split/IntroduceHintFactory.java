/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.markiewb.netbeans.plugins.hints.literals.split;

import org.netbeans.api.java.source.CancellableTask;
import org.netbeans.api.java.source.CompilationInfo;
import org.netbeans.api.java.source.JavaSource.Phase;
import org.netbeans.api.java.source.JavaSource.Priority;
import org.netbeans.api.java.source.JavaSourceTaskFactory;
import org.netbeans.api.java.source.support.SelectionAwareJavaSourceTaskFactory;
import org.openide.filesystems.FileObject;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service=JavaSourceTaskFactory.class) 
public class IntroduceHintFactory extends SelectionAwareJavaSourceTaskFactory {

    public IntroduceHintFactory() {
        super(Phase.RESOLVED, Priority.BELOW_NORMAL);
    }
    
    @Override
    protected CancellableTask<CompilationInfo> createTask(FileObject file) {
        return new SelectionAwareHint();
    }

}
