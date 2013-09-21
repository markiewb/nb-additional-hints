/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.literals.split;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.netbeans.api.java.source.CancellableTask;
import org.netbeans.api.java.source.CompilationInfo;
import org.netbeans.api.java.source.support.SelectionAwareJavaSourceTaskFactory;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.HintsController;
import org.openide.filesystems.FileObject;

/**
 *
 * @author markiewb
 */
public class SelectionAwareHint implements CancellableTask<CompilationInfo> {

    private AtomicBoolean cancel = new AtomicBoolean();

    SelectionAwareHint() {
    }

    @Override
    public void cancel() {
    }

    @Override
    public void run(CompilationInfo info) throws Exception {

        cancel.set(false);

        FileObject file = info.getFileObject();
        int[] selection = SelectionAwareJavaSourceTaskFactory.getLastSelection(file);


        if (selection == null) {
            //nothing to do....
            HintsController.setErrors(info.getFileObject(), SelectionAwareHint.class.getName(), Collections.<ErrorDescription>emptyList());
        } else {
            HintsController.setErrors(info.getFileObject(), SelectionAwareHint.class.getName(), computeError(info, selection[0], selection[1], cancel));
        }

    }

    private Collection<? extends ErrorDescription> computeError(CompilationInfo info, int selectionStart, int selectionEnd, AtomicBoolean cancel) {
        System.out.println("should i start at" + selectionStart + "/" + selectionEnd + " @ "+ info.getFileObject());
        SplitLiteralOnCaretHint.computeWarningForSelection(info, selectionStart, selectionEnd);
        return Collections.emptyList();
    }

}
