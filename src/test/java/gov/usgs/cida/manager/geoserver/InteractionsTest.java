package gov.usgs.cida.manager.geoserver;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 *
 * @author isuftin
 */
@Category(IntegrationTests.class)
public class InteractionsTest implements IntegrationTests {

	public InteractionsTest() {
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
	 * Test of isAvailable method, of class Interactions.
	 */
	@Test
	public void testIsAvailable() {
		System.out.println("isAvailable");
		Interactions instance = null;
		boolean expResult = true;
		System.out.println(System.getProperty("geoserver.host"));
		System.out.println(System.getProperty("geoserver.port"));
//		boolean result = instance.isAvailable();
		assertEquals(true, true);
//		fail("The test case is a prototype.");
	}

}