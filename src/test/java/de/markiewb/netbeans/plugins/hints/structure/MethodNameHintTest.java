package de.markiewb.netbeans.plugins.hints.structure;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

/**
 *
 * @author markiewb
 */
public class MethodNameHintTest {

    @Test
    public void testMethodnameMatchesClassname_NOK() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public class Test {\n"
                        + "    public void Test(){};\n"
                        + "}"
                )
                .run(MethodNameHint.class)
                .findWarning("2:4-2:24:warning:" + Bundle.ERR_MethodnameEqualsClassname());

    }

    @Test
    public void testMethodnameMatchesClassname_OK() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public class Test {\n"
                        + "    public void Foo(){};\n"
                        + "}"
                )
                .run(MethodNameHint.class)
                .assertWarnings();
    }

}
