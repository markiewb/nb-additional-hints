/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.replaceplus;

import de.markiewb.netbeans.plugins.hints.replaceplus.ReplacePlusHint;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;
import org.openide.util.Lookup;
import org.openide.util.datatransfer.ExClipboard;

/* TODO to make this test work:
 - add test dependency on Java Hints Test API (and JUnit 4)
 - to ensure that the newest Java language features supported by the IDE are available,
 regardless of which JDK you build the module with:
 -- for Ant-based modules, add "requires.nb.javac=true" into nbproject/project.properties
 -- for Maven-based modules, use dependency:copy in validate phase to create
 target/endorsed/org-netbeans-libs-javacapi-*.jar and add to endorseddirs
 in maven-compiler-plugin and maven-surefire-plugin configuration
 See: http://wiki.netbeans.org/JavaHintsTestMaven
 */
public class CopyJoinedStringToClipboardFixTest {

    @Test
    public void testFixWorkingOnlyLiterals() throws Exception {
        HintTest.create().
                input("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=\"A\"+\"B\"+\"C\";\n"
                + "    }\n"
                + "}\n"). 
                run(ReplacePlusHint.class).
                findWarning("3:19-3:19:hint:" + Bundle.DN_ReplacePlus()).
                applyFix(Bundle.LBL_CopyJoinedStringToClipboardFix()).
                assertCompilable().
                assertOutput("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=\"A\"+\"B\"+\"C\";\n"
                + "    }\n"
                + "}\n");

        assertEquals("ABC", getClipboardContent());
    }

    @Test
    public void testFixWorkingOnlyLiteralsWithLineBreaks() throws Exception {
        HintTest.create().
                input("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=\"A\\n\"+\"B\\r\"+\"C\";"
                + "    }\n"
                + "}\n").
                run(ReplacePlusHint.class).
                findWarning("3:19-3:19:hint:" + Bundle.DN_ReplacePlus()).
                applyFix(Bundle.LBL_CopyJoinedStringToClipboardFix()).
                assertCompilable().
                assertOutput("package test;\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        String foo=\"A\\n\"+\"B\\r\"+\"C\";"
                + "    }\n"
                + "}\n");
        assertEquals("A\nB\rC", getClipboardContent());
    }

    private String getClipboardContent() throws UnsupportedFlavorException, IOException {
        ExClipboard clipboard = Lookup.getDefault().
                lookup(ExClipboard.class);
        return ((String) clipboard.getData(DataFlavor.stringFlavor));
    }
}
