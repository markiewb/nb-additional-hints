package example;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;
import junit.framework.Assert.*;
import junit.framework.Assert;

/**
 *
 * @author markiewb
 */
public class UpgradeToOJAssertTest {

    public void testX() {
        assertEquals(1, 2);
        junit.framework.Assert.assertEquals(1, 2);
        org.junit.Assert.assertEquals(1, 2);
    }

}
