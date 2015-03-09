package de.markiewb.netbeans.plugins.hints.structure;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

public class WarnOfDeadInstanceOfTest {

    @Test
    public void testMatchingCases() throws Exception {
        /**
         * <pre>
         * //negative cases -> error hint for instance of
         * if (new java.util.ArrayList() instanceof java.util.Map) {}
         * if (new java.util.ArrayList() instanceof java.util.Set) {}
         * if (new java.util.ArrayList<String>() instanceof java.util.Set) {}
         * //positive cases
         * if (new java.util.ArrayList() instanceof java.util.List) {}
         * if (new java.util.HashSet() instanceof java.util.Set) {}
         * if (new java.util.HashSet<String>() instanceof java.util.Set) {}
         * </pre>
         */
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public class Test {\n"
                        + "    void getMember(){\n"
                        + "         //negative cases -> error hint for instance of\n"
                        + "         if (new java.util.ArrayList() instanceof java.util.Map) {}\n"
                        + "         if (new java.util.ArrayList() instanceof java.util.Set) {}\n"
                        + "         if (new java.util.ArrayList<String>() instanceof java.util.Set) {}\n"
                        + "         //positive cases\n"
                        + "         if (new java.util.ArrayList() instanceof java.util.List) {}\n"
                        + "         if (new java.util.HashSet() instanceof java.util.Set) {}\n"
                        + "         if (new java.util.HashSet<String>() instanceof java.util.Set) {}\n"
                        + "    };\n"
                        + "}"
                )
                .run(WarnOfDeadInstanceOf.class)
                .assertWarnings("4:13-4:63:warning:" + Bundle.ERR_WarnOfDeadInstanceOf("ArrayList", "Map"), "5:13-5:63:warning:" + Bundle.ERR_WarnOfDeadInstanceOf("ArrayList", "Set"), "6:13-6:71:warning:" + Bundle.ERR_WarnOfDeadInstanceOf("ArrayList", "Set"));
    }

    @Test
    public void testMatchingCasesIsSubclass() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public class Test {\n"
                        + "    void getMember(){\n"
                        + "         //non-matching cases - partially compatible\n"
                        + "         java.util.Set a = null; if (a instanceof java.util.HashSet) {}\n"
                        + "         java.lang.Number b = null; if (b instanceof java.lang.Double) {}\n"
                        + "         java.lang.Object c = null; if (c instanceof java.lang.Double) {}\n"
                        + "         java.lang.Double d = null; if (d instanceof java.lang.Object) {}\n"
                        + "         //matching cases - fully incompatible\n"
                        + "         java.util.Set e = null; if (e instanceof java.util.ArrayList) {}\n"
                        + "    };\n"
                        + "}"
                )
                .run(WarnOfDeadInstanceOf.class)
                .assertWarnings("9:37-9:69:warning:" + Bundle.ERR_WarnOfDeadInstanceOf("Set", "ArrayList"));
    }

    @Test(expected = AssertionError.class)
    public void testMatchingCasesIsSubclassCompileError() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public class Test {\n"
                        + "    void getMember(){\n"
                        + "         //compile error\n"
                        + "         java.lang.Double f = null; if (f instanceof java.util.ArrayList) {}\n"
                        + "    };\n"
                        + "}"
                )
                .run(WarnOfDeadInstanceOf.class);
        throw new UnsupportedOperationException("expected java.lang.AssertionError: 5:41 inconvertible types\n  required: ArrayList\n  found:    Double");
    }

}
