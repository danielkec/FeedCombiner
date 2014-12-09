package cz.kec.wls.feedcombiner.beans;

import cz.kec.wls.feedcombiner.exceptions.WrongIntervalException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import static org.junit.Assert.*;

/**
 * Didnt manage to get embeded container working, so if it would, this would be
 * test of the RefreshFeedsTimerBean..
 *
 * @see RefreshFeedsTimerBean
 * @author Daniel Kec <daniel at kecovi.cz>
 */
public class RefreshFeedsTimerBeanTestNo {

    private static EJBContainer ejbContainer;
    private static Context ctx;

    public RefreshFeedsTimerBeanTestNo() {
    }

    @BeforeClass
    public static void setUpClass() {
        Map properties = new HashMap();
        File f = new File("*");
        System.out.println(">>>" + (new File("target/classes")).getAbsolutePath());
        properties.put(EJBContainer.MODULES, new File("target/classes"));
        ejbContainer = EJBContainer.createEJBContainer(properties);
//        ejbContainer = new EJBContainerProviderImpl().createEJBContainer(properties);
        System.out.println("Starting the container");
        ctx = ejbContainer.getContext();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        ejbContainer.close();
        System.out.println("Closing the container");
    }

    /**
     * Test of initTimer method, of class RefreshFeedsTimerBean.
     */
    @Test
    public void testInitTimer() throws Exception {
        System.out.println("initTimer");
        RefreshFeedsTimerBean instance = (RefreshFeedsTimerBean) ejbContainer.getContext().lookup("java:global/classes/RefreshFeedsTimerBean");
        instance.initTimer();
    }

    /**
     * Test of getTimerIntervalInSeconds method, of class RefreshFeedsTimerBean.
     */
    @Test
    public void testGetTimerIntervalInSeconds() throws Exception {
        System.out.println("getTimerIntervalInSeconds");
        RefreshFeedsTimerBean instance = (RefreshFeedsTimerBean) ejbContainer.getContext().lookup("java:global/classes/RefreshFeedsTimerBean");
        int expResult = RefreshFeedsTimerBean.DEFAULT_INTERVAL;
        int result = instance.getTimerIntervalInSeconds();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTimeLeft method, of class RefreshFeedsTimerBean.
     */
    @Test
    public void testGetTimeLeft() throws Exception {
        System.out.println("getTimeLeft");
        RefreshFeedsTimerBean instance = (RefreshFeedsTimerBean) ejbContainer.getContext().lookup("java:global/classes/RefreshFeedsTimerBean");
        int result = instance.getTimeLeft();
        if (result != 0) {
            Thread.currentThread().sleep(1 * 1000);
            assertEquals(result - 1, result);
        } else {
            Thread.currentThread().sleep(1 * 1000);
            assertEquals(instance.getTimerIntervalInSeconds(), result);
        }
    }

    /**
     * Test of setTimerIntevalInSeconds method, of class RefreshFeedsTimerBean.
     */
    @Test(expected = WrongIntervalException.class)
    public void testSetTimerIntevalInSeconds() throws WrongIntervalException, NamingException {
        System.out.println("setTimerIntevalInSeconds");
        RefreshFeedsTimerBean instance = (RefreshFeedsTimerBean) ejbContainer.getContext().lookup("java:global/classes/RefreshFeedsTimerBean");
        int intervalDuration = 2;
        instance.setTimerIntevalInSeconds(intervalDuration);
        fail("Expected WrongIntervalException");
    }

}
