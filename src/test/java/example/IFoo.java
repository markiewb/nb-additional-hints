package example;

public interface IFoo {

    public static final String _publicstaticfinal = "XXX";
    public static String _publicstatic = "XXX";
    public final String _publicfinal = "XXX";
    public String _public = "XXX";
    static final String _staticfinal = "XXX";
    static String _static = "XXX";
    final String _final = "XXX";

    public abstract void getFooA();

    public void getFooB();

    abstract void getFooC();

    void getFooD();
}
