package de.markiewb.netbeans.plugins.hints.structure;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

public class RemoveMethodModsFromIntfTest {

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
