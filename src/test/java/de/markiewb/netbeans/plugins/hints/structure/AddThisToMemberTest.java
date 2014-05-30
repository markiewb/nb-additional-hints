package de.markiewb.netbeans.plugins.hints.structure;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

public class AddThisToMemberTest {

    @Test
    public void testMemberMethod() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public class Test {\n"
                        + "    int variable=42;\n"
                        + "    static void staticMember() {};\n"
                        + "    void getMember(){\n"
                        + "         staticMember();\n"
                        + "         getMember();\n"
                        + "         variable = 43;\n"
                        + "    };\n"
                        + "}"
                )
                .run(AddThisToMember.class)
                .assertWarnings("6:9-6:18:warning:" + Bundle.ERR_AddThisToMember(), "7:9-7:17:warning:" + Bundle.ERR_AddThisToMember())
                .findWarning("6:9-6:18:warning:" + Bundle.ERR_AddThisToMember())
                .applyFix()
                .assertCompilable()
                .assertOutput(
                        "package example;\n"
                        + "public class Test {\n"
                        + "    int variable=42;\n"
                        + "    static void staticMember() {};\n"
                        + "    void getMember(){\n"
                        + "         staticMember();\n"
                        + "         this.getMember();\n"
                        + "         variable = 43;\n"
                        + "    };\n"
                        + "}");

    }

    @Test
    public void testMemberVariable() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public class Test {\n"
                        + "    int variable=42;\n"
                        + "    static void staticMember() {};\n"
                        + "    void getMember(){\n"
                        + "         staticMember();\n"
                        + "         getMember();\n"
                        + "         variable = 43;\n"
                        + "    };\n"
                        + "}"
                )
                .run(AddThisToMember.class)
                .assertWarnings("6:9-6:18:warning:" + Bundle.ERR_AddThisToMember(), "7:9-7:17:warning:" + Bundle.ERR_AddThisToMember())
                .findWarning("7:9-7:17:warning:" + Bundle.ERR_AddThisToMember())
                .applyFix()
                .assertCompilable()
                .assertOutput(
                        "package example;\n"
                        + "public class Test {\n"
                        + "    int variable=42;\n"
                        + "    static void staticMember() {};\n"
                        + "    void getMember(){\n"
                        + "         staticMember();\n"
                        + "         getMember();\n"
                        + "         this.variable = 43;\n"
                        + "    };\n"
                        + "}");

    }

}
