package de.markiewb.netbeans.plugins.hints.structure;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

public class RemoveNestedModsFromIntfTest {

    @Test
    public void testMatchInterfaces_abstract() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public interface Test {\n"
                        + "    abstract void getFooA();\n"
                        + "}"
                )
                .run(RemoveMethodModsFromIntf.class)
                .findWarning("2:4-2:28:hint:" + Bundle.ERR_RemoveModsFromInterface())
                .applyFix()
                .assertCompilable()
                .assertOutput("package example;\n"
                        + "public interface Test {\n"
                        + "    void getFooA();\n"
                        + "}");

    }

    @Test
    public void testMatchFields_publicstaticfinal() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public interface Test {\n"
                        + "    public static final String FOO=\"XXX\";\n"
                        + "}"
                )
                .run(RemoveFieldModsFromIntf.class)
                .findWarning("2:4-2:41:hint:" + Bundle.ERR_RemoveModsFromField())
                .applyFix()
                .assertCompilable()
                .assertOutput("package example;\n"
                        + "public interface Test {\n"
                        + "    String FOO=\"XXX\";\n"
                        + "}");

    }

    @Test
    public void testMatchFields_publicstatic() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public interface Test {\n"
                        + "    public static String FOO=\"XXX\";\n"
                        + "}"
                )
                .run(RemoveFieldModsFromIntf.class)
                .findWarning("2:4-2:35:hint:" + Bundle.ERR_RemoveModsFromField())
                .applyFix()
                .assertCompilable()
                .assertOutput("package example;\n"
                        + "public interface Test {\n"
                        + "    String FOO=\"XXX\";\n"
                        + "}");

    }

    @Test
    public void testMatchFields_publicfinal() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public interface Test {\n"
                        + "    public final String FOO=\"XXX\";\n"
                        + "}"
                )
                .run(RemoveFieldModsFromIntf.class)
                .findWarning("2:4-2:34:hint:" + Bundle.ERR_RemoveModsFromField())
                .applyFix()
                .assertCompilable()
                .assertOutput("package example;\n"
                        + "public interface Test {\n"
                        + "    String FOO=\"XXX\";\n"
                        + "}");

    }

    @Test
    public void testMatchFields_staticfinal() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public interface Test {\n"
                        + "    static final String FOO=\"XXX\";\n"
                        + "}"
                )
                .run(RemoveFieldModsFromIntf.class)
                .findWarning("2:4-2:34:hint:" + Bundle.ERR_RemoveModsFromField())
                .applyFix()
                .assertCompilable()
                .assertOutput("package example;\n"
                        + "public interface Test {\n"
                        + "    String FOO=\"XXX\";\n"
                        + "}");

    }

    @Test
    public void testMatchFields_static() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public interface Test {\n"
                        + "    static String FOO=\"XXX\";\n"
                        + "}"
                )
                .run(RemoveFieldModsFromIntf.class)
                .findWarning("2:4-2:28:hint:" + Bundle.ERR_RemoveModsFromField())
                .applyFix()
                .assertCompilable()
                .assertOutput("package example;\n"
                        + "public interface Test {\n"
                        + "    String FOO=\"XXX\";\n"
                        + "}");

    }

    @Test
    public void testMatchFields_public() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public interface Test {\n"
                        + "    public String FOO=\"XXX\";\n"
                        + "}"
                )
                .run(RemoveFieldModsFromIntf.class)
                .findWarning("2:4-2:28:hint:" + Bundle.ERR_RemoveModsFromField())
                .applyFix()
                .assertCompilable()
                .assertOutput("package example;\n"
                        + "public interface Test {\n"
                        + "    String FOO=\"XXX\";\n"
                        + "}");

    }

    @Test
    public void testMatchFields_final() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public interface Test {\n"
                        + "    final String FOO=\"XXX\";\n"
                        + "}"
                )
                .run(RemoveFieldModsFromIntf.class)
                .findWarning("2:4-2:27:hint:" + Bundle.ERR_RemoveModsFromField())
                .applyFix()
                .assertCompilable()
                .assertOutput("package example;\n"
                        + "public interface Test {\n"
                        + "    String FOO=\"XXX\";\n"
                        + "}");

    }

    @Test
    public void testMatchInterfaces_public() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public interface Test {\n"
                        + "    public void getFooA();\n"
                        + "}"
                )
                .run(RemoveMethodModsFromIntf.class)
                .findWarning("2:4-2:26:hint:" + Bundle.ERR_RemoveModsFromInterface())
                .applyFix()
                .assertCompilable()
                .assertOutput("package example;\n"
                        + "public interface Test {\n"
                        + "    void getFooA();\n"
                        + "}");

    }

    @Test
    public void testMatchNestedInterface_public() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public interface Test {\n"
                        + "    public interface Inner{};\n"
                        + "}"
                )
                .run(RemoveNestedModsFromIntf.class)
                .findWarning("2:4-2:28:hint:" + Bundle.ERR_RemoveNestedModsFromIntf())
                .applyFix()
                .assertCompilable()
                .assertOutput("package example;\n"
                        + "public interface Test {\n"
                        + "    interface Inner{};\n"
                        + "}");

    }
    @Test
    public void testMatchNestedInterface_static() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public interface Test {\n"
                        + "    static interface Inner{};\n"
                        + "}"
                )
                .run(RemoveNestedModsFromIntf.class)
                .findWarning("2:4-2:28:hint:" + Bundle.ERR_RemoveNestedModsFromIntf())
                .applyFix()
                .assertCompilable()
                .assertOutput("package example;\n"
                        + "public interface Test {\n"
                        + "    interface Inner{};\n"
                        + "}");

    }
    @Test
    public void testMatchNestedInterface_publicstatic() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public interface Test {\n"
                        + "    public static interface Inner{};\n"
                        + "}"
                )
                .run(RemoveNestedModsFromIntf.class)
                .findWarning("2:4-2:35:hint:" + Bundle.ERR_RemoveNestedModsFromIntf())
                .applyFix()
                .assertCompilable()
                .assertOutput("package example;\n"
                        + "public interface Test {\n"
                        + "    interface Inner{};\n"
                        + "}");

    }
    @Test
    public void testMatchNestedClass_public() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public interface Test {\n"
                        + "    public class Inner{};\n"
                        + "}"
                )
                .run(RemoveNestedModsFromIntf.class)
                .findWarning("2:4-2:24:hint:" + Bundle.ERR_RemoveNestedModsFromIntf())
                .applyFix()
                .assertCompilable()
                .assertOutput("package example;\n"
                        + "public interface Test {\n"
                        + "    class Inner{};\n"
                        + "}");

    }

    @Test
    public void testMatchInterfaces_abstract_public() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public interface Test {\n"
                        + "    abstract public void getFooA();\n"
                        + "}"
                )
                .run(RemoveMethodModsFromIntf.class)
                .findWarning("2:4-2:35:hint:" + Bundle.ERR_RemoveModsFromInterface())
                .applyFix()
                .assertCompilable()
                .assertOutput("package example;\n"
                        + "public interface Test {\n"
                        + "    void getFooA();\n"
                        + "}");

    }

    @Test
    public void testMatchInterfaces_public_abstract() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public interface Test {\n"
                        + "    public abstract void getFooA();\n"
                        + "}"
                )
                .run(RemoveMethodModsFromIntf.class)
                .findWarning("2:4-2:35:hint:" + Bundle.ERR_RemoveModsFromInterface())
                .applyFix()
                .assertCompilable()
                .assertOutput("package example;\n"
                        + "public interface Test {\n"
                        + "    void getFooA();\n"
                        + "}");

    }

    @Test
    public void testShouldNotMatchClasses_public_abstract() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public abstract class Test {\n"
                        + "    public abstract void getFooA();\n"
                        + "}"
                )
                .run(RemoveMethodModsFromIntf.class)
                .assertWarnings();

    }

    @Test
    public void testShouldNotMatchClasses_abstract_public() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public abstract class Test {\n"
                        + "    abstract public void getFooA();\n"
                        + "}"
                )
                .run(RemoveMethodModsFromIntf.class)
                .assertWarnings();

    }

    @Test
    public void testShouldNotMatchClasses_public() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public abstract class Test {\n"
                        + "    public void getFooA(){};\n"
                        + "}"
                )
                .run(RemoveMethodModsFromIntf.class)
                .assertWarnings();

    }

    @Test
    public void testShouldNotMatchClasses_abstract() throws Exception {
        HintTest.create()
                .input(
                        "package example;\n"
                        + "public abstract class Test {\n"
                        + "    abstract void getFooA();\n"
                        + "}"
                )
                .run(RemoveMethodModsFromIntf.class)
                .assertWarnings();

    }

}
