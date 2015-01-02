package example;

public class DeadInstanceOfExample {

    public void test() {
        //negative cases -> error hint for instance of
        if (new java.util.ArrayList() instanceof java.util.Map) {}
        if (new java.util.ArrayList() instanceof java.util.Set) {}
        if (new java.util.ArrayList<String>() instanceof java.util.Set) {}
        //positive cases
        if (new java.util.ArrayList() instanceof java.util.List) {}
        if (new java.util.HashSet() instanceof java.util.Set) {}
        if (new java.util.HashSet<String>() instanceof java.util.Set) {}
    }
}
