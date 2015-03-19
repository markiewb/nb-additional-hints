package example;

import java.util.Date;

public class ReplacePlus {

    public static void testA() {
         String foo  = "O   utput contains " + 4 + " entries";
//        String foo = MessageFormat.format("Output contains {0} entries", 4);
//        String foo = String.format("Output contains %s entries", 4);
//        String foo = new StringBuilder().append("Output contains ").append(4).append(" entries").toString();

    }
    
    public static void testThrow() {
        new RuntimeException("Output contains "+4+" entries");
        throw new RuntimeException("Output contains "+4+" entries");
    }

    public static void testMethodInvocation() {
        System.err.println("Output contains "+4+" entries");
    }

    public static void testStringConcat() {
        String foo = "Output contains " + 4 + " entries" + " and more at " + new java.util.Date();
//        String foo = MessageFormat.format("Output contains {0} entries and more at {1}", 4, new Date());
//        String foo = String.format("Output contains %s entries and more at %s", 4, new Date());
//        String foo = new StringBuilder().append("Output contains ").append(4).append(" entries").toString();

    }

        public static void testStringConcatWithLineBreaks() {
                String foo="A\n"+ "B\n"+"C";
                String bar="package test;\n" 
                + "public class Test {\n" 
                + "    public static void main(String[] args) {\n"
                + "        String foo=\"A\\n\"+\"B\\r\"+\"C\";"
                + "    }\n"
                + "}\n";
    }

    @SuppressWarnings("A" + "B")
    private static void testBreakStrings() {
//        String bar="select *"
//                + "from"
//                + table
//                + "where"
//                + "bla="+var;
        String foo = 4+"Hello,\n\r" + "world\n" + "!\n" + " ljlkj";  
    }
    

    public static void testStringConcatA() {
        String foo = "A" + 4;
//        String foo = MessageFormat.format("A{0}", 4);
//        String foo = String.format("A%s", 4);
//        String foo = new StringBuilder().append("A").append(4).toString();

    }

    public static void testStringConcatB() {
        String foo = 4 + "B";
//        String foo = MessageFormat.format("{0}B", 4);
//        String foo = String.format("%sB", 4);
//        String foo = new StringBuilder().append(4).append("B").toString();
    }

    public static void testStringConcatC() {
        String foo = "A" + "B";
//         String foo = String.format("AB");
//         String foo = new StringBuilder().append("AB").toString();
    }

    public static void testStringSubsequent() {
        String foo = "A" + 4 + 9.0 + 'c';
//        String foo = MessageFormat.format("A{0}{1}{2}", 4, 9.0, 'c');
//        String foo = String.format("A%s%s%s", 4, 9.0, 'c');
//        String foo = new StringBuilder().append("A").append(4).append(9.0).append('c').toString();

    }

    public static void testString() {
        System.out.println("a"+3+"B");
        String barString="a"+3+"B";
        String foo;
        foo = "a"+3+"B";
//        "a"+3+"B";
    }
}