package de.markiewb.netbeans.plugins.hints.modifiers;

import com.sun.source.tree.Tree;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.java.hints.HintContext;
import org.openide.text.NbDocument;

/**
 *
 * @author markiewb
 */
public class AbstractChangeModifiers {

    private static final Logger LOG = Logger.getLogger(AbstractChangeModifiers.class.getName());

    /**
     * Same as
     * org.netbeans.spi.java.hints.ErrorDescriptionFactory.forSpan(HintContext,
     * int, int, String, Fix[]), but you can set the start/end freely.
     *
     * @param description
     * @param start
     * @param end
     * @return
     */
    protected static ErrorDescription forSpan(ErrorDescription description, int start, int end) {

        return org.netbeans.spi.editor.hints.ErrorDescriptionFactory.createErrorDescription(description.getId(),
                description.getSeverity(), description.getDescription(), description.getDetails(),
                description.getFixes(), description.getFile(), start, end);

    }

    /**
     * Useful to show hints at first line of method not in the whole method body.
     *
     * @param ctx
     * @param modifiers
     * @return
     */
    private static int[] getFirstLineSpan(HintContext ctx, Tree modifiers) {
        int startPosition = (int) ctx.getInfo().getTrees().getSourcePositions()
                .getStartPosition(ctx.getInfo().getCompilationUnit(), modifiers);
        int start;
        int end;
        Document document = null;
        try {
            document = ctx.getInfo().getDocument();
            if (null == document) {
                return null;
            }
            int startLine = NbDocument.findLineNumber((StyledDocument) document, startPosition);
            start = findStart(document, startLine);
            end = findEnd(document, startLine) - 1;
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Could not get document or positions", ex);
            return null;
        }
        if (start > ctx.getCaretLocation() || ctx.getCaretLocation() > end) {
            // outside first line, do not show
            return null;
        }
        return new int[]{start, end};
    }

    private static int findEnd(Document document, int startLine) {
        try {

            return NbDocument.findLineOffset((StyledDocument) document, startLine + 1);
        } catch (Exception e) {
            return document.getLength() - 1;
        }
    }

    private static int findStart(Document document, int startLine) {
        try {
            return NbDocument.findLineOffset((StyledDocument) document, startLine);
        } catch (Exception e) {
            return 0;
        }
    }

    protected static int[] getFirstLineSpan(HintContext ctx, Tree modifiers, Tree tree) {
        int[] spanA = getFirstLineSpan(ctx, modifiers);
        //specialcase, if not modifierTree available
        int[] spanB = getFirstLineSpan(ctx, tree);
        if (null == spanA && null == spanB) {
            return null;
        }
        int[] span = null != spanA ? spanA : spanB;
        return span;
    }
}
