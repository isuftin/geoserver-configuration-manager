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
public class GeoserverInteractionsTest implements IntegrationTests {

	private static String host;
	private static int port;
	private static URL geoserverEndpoint;

	public GeoserverInteractionsTest() {
	}

	@BeforeClass
	public static void setUpClass() throws MalformedURLException {
		host = System.getProperty("geoserver.host");
		port = Integer.parseInt(System.getProperty("geoserver.port"));
		geoserverEndpoint = new URL(String.format("http://%s:%s/geoserver", host, port));
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
	 * Test of isAvailable method, of class GeoserverInteractions.
	 */
	@Test
	public void testIsAvailable() throws MalformedURLException {
		System.out.println("testIsAvailable");
		GeoserverInteractions instance = new GeoserverInteractions(
				geoserverEndpoint, 
				GeoserverInteractions.DEFAULT_USER, 
				GeoserverInteractions.DEFAULT_PASS);
		assertTrue(instance.isAvailable());
	}

	@Test
	public void testExistsDatastore() throws MalformedURLException {
		System.out.println("testExistsDatastore");
		GeoserverInteractions instance = new GeoserverInteractions(
				geoserverEndpoint, 
				GeoserverInteractions.DEFAULT_USER, 
				GeoserverInteractions.DEFAULT_PASS);
		assertTrue(instance.existsDatastore("topp", "states_shapefile", false));
		assertTrue(instance.existsDatastore("topp", "taz_shapes", false));
		assertFalse(instance.existsDatastore("topp", "tasmania_wateR_bodies", false));
	}

}