package cz.kec.wls.feedcombiner.datastore.impl;

import cz.kec.wls.feedcombiner.model.CombinedFeed;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 */
public class InMemoryDataStoreSingletonIT {

    public InMemoryDataStoreSingletonIT() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class InMemoryDataStoreSingleton.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        InMemoryDataStoreSingleton expResult = null;
        InMemoryDataStoreSingleton result = InMemoryDataStoreSingleton.getInstance();

    }

    /**
     * Test of put method, of class InMemoryDataStoreSingleton.
     */
    @Test(expected=ClassCastException.class)
    public void testPutClassCast() {
        System.out.println("put");
        String key = "key";
        String data = "data";
        InMemoryDataStoreSingleton instance = InMemoryDataStoreSingleton.getInstance();
        assertNull(instance.put(key, data));
        assertEquals("data",instance.get(key, String.class));
        Serializable result = instance.get(key, Integer.class);
        fail("Should throw ClassCastException");
    }

//    /**
//     * Test of get method, of class InMemoryDataStoreSingleton.
//     */
//    @Test
//    public void testGet() {
//        System.out.println("get");
//        InMemoryDataStoreSingleton instance = null;
//        Object expResult = null;
//        Object result = instance.get(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of save method, of class InMemoryDataStoreSingleton.
//     */
//    @Test
//    public void testSave() throws Exception {
//        System.out.println("save");
//        OutputStream out = null;
//        InMemoryDataStoreSingleton instance = null;
//        instance.save(out);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of load method, of class InMemoryDataStoreSingleton.
//     */
//    @Test
//    public void testLoad() throws Exception {
//        System.out.println("load");
//        InputStream in = null;
//        InMemoryDataStoreSingleton instance = null;
//        instance.load(in);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

}
