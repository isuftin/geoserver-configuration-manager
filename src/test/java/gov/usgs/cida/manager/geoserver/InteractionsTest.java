package gov.usgs.cida.manager.geoserver;

import java.net.MalformedURLException;
import java.net.URL;
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
	private static String host;
	private static int port;
	public InteractionsTest() {
	}

	@BeforeClass
	public static void setUpClass() {
		host = System.getProperty("geoserver.host");
		port = Integer.parseInt(System.getProperty("geoserver.port"));
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
	public void testIsAvailable() throws MalformedURLException {
		System.out.println("testIsAvailable");
		Interactions instance = new Interactions(new URL(String.format("http://%s:%s/geoserver", host, port)), "admin", "geoserver");
		assertTrue(instance.isAvailable());
	}

}
