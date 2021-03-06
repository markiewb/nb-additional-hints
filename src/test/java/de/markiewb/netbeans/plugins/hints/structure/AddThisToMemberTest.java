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
                        + "    };\n"
                        + "}"
                )
                .run(AddThisToMember.class)
                .assertWarnings("6:9-6:18:warning:" + Bundle.ERR_AddThisToMember())
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
                        + "    };\n"
                        + "}");

    }

    @Test
    public void testMemberMethod_AlreadyThis() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public class Test {\n"
                        + "    void getMember(){\n"
                        + "         this.getMember();\n"
                        + "    };\n"
                        + "}"
                )
                .run(AddThisToMember.class)
                .assertWarnings();

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
                        + "         variable = 43;\n"
                        + "    };\n"
                        + "}"
                )
                .run(AddThisToMember.class)
                .assertWarnings("6:9-6:17:warning:" + Bundle.ERR_AddThisToMember())
                .findWarning("6:9-6:17:warning:" + Bundle.ERR_AddThisToMember())
                .applyFix()
                .assertCompilable()
                .assertOutput(
                        "package example;\n"
                        + "public class Test {\n"
                        + "    int variable=42;\n"
                        + "    static void staticMember() {};\n"
                        + "    void getMember(){\n"
                        + "         staticMember();\n"
                        + "         this.variable = 43;\n"
                        + "    };\n"
                        + "}");

    }

    @Test
    public void testMemberVariable_AlreadyThis() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public class Test {\n"
                        + "    int variable=42;\n"
                        + "    static void staticMember() {};\n"
                        + "    void getMember(){\n"
                        + "         staticMember();\n"
                        + "         this.variable = 43;\n"
                        + "    };\n"
                        + "}"
                )
                .run(AddThisToMember.class)
                .assertWarnings();

    }

    @Test
    public void testMemberVariable_Super() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public class Test{void member(){};}\n"
                        + "class SubClass extends Test{\n"
                        + "    void init(){\n"
                        + "         super.member();\n"
                        + "    };\n"
                        + "}"
                )
                .run(AddThisToMember.class)
                .assertWarnings();

    }

    @Test
    public void testMemberVariable_MemberInAnnotation_TypeA() throws Exception {
        HintTest.create()
                .input(
                        "    public class Test {\n"
                        + "        @SuppressWarnings(\"ABC\")\n"
                        + "        void method() {\n"
                        + "        }\n"
                        + "    }"
                )
                .run(AddThisToMember.class)
                .assertWarnings();
    }

    @Test
    public void testMemberVariable_MemberInAnnotation_TypeB() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public class Test {\n"
                        + "    @SuppressWarnings(value=\"ABC\")\n"
                        + "    void getMember(){\n"
                        + "    }\n"
                        + "}"
                )
                .run(AddThisToMember.class)
                .assertWarnings();
    }

    @Test
    public void testMemberVariable_MemberInAnnotation_TypeC() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public class Test {\n"
                        + "    @SuppressWarnings({\"ABC\"})\n"
                        + "    void getMember(){\n"
                        + "    }\n"
                        + "}"
                )
                .run(AddThisToMember.class)
                .assertWarnings();
    }

    @Test
    public void testMemberVariable_MemberInAnnotation_TypeD() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public class Test {\n"
                        + "    @SuppressWarnings(value={\"ABC\"})\n"
                        + "    void getMember(){\n"
                        + "    }\n"
                        + "}"
                )
                .run(AddThisToMember.class)
                .assertWarnings();
    }

}
