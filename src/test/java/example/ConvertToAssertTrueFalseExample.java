package example;

public class ConvertToAssertTrueFalseExample {

    public static void main(String[] args) {
        boolean actual = false;
        org.junit.Assert.assertEquals("Not correct", true, actual);
        junit.framework.Assert.assertEquals("Not correct", true, actual);
        org.junit.Assert.assertEquals(true, actual);
        junit.framework.Assert.assertEquals(true, actual);
        org.junit.Assert.assertEquals(false, actual);
        junit.framework.Assert.assertEquals(false, actual);

        org.junit.Assert.assertEquals("Not correct", null, actual);
        junit.framework.Assert.assertEquals("Not correct", null, actual);
        org.junit.Assert.assertEquals(null, actual);
        junit.framework.Assert.assertEquals(true, actual);
        org.junit.Assert.assertEquals(null, actual);
        junit.framework.Assert.assertEquals(null, actual);
    }

}
