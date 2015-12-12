package gov.usgs.cida.manager.geoserver;

import gov.usgs.cida.manager.geoserver.model.Datastore;
import gov.usgs.cida.manager.geoserver.model.Workspace;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.commons.io.FileUtils;
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
	private static File yamlFile;

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
		yamlFile = FileUtils.toFile(this.getClass().getResource("/test.yml"));
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
	public void testCreateAndReadWorkspace() throws MalformedURLException, URISyntaxException {
		System.out.println("testCreateAndReadWorkspace");
		GeoserverInteractions instance = new GeoserverInteractions(
				geoserverEndpoint,
				GeoserverInteractions.DEFAULT_USER,
				GeoserverInteractions.DEFAULT_PASS);
		assertTrue(instance.createWorkspace("workspace.test", null));
		assertTrue(instance.existsWorkspace("workspace.test", false));
		assertTrue(instance.createWorkspace("test.workspace.test", new URI("test.workspace.test")));
		assertTrue(instance.existsWorkspace("test.workspace.test", false));
		assertFalse(instance.existsWorkspace("test.workspace.tesT", false));
		assertFalse(instance.existsWorkspace("this.doesnt.exist", false));
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
	
	@Test
	public void testInteractWithShapefileDatastore() throws MalformedURLException, URISyntaxException {
		System.out.println("testCreateShapefileDatastore");
		GeoserverInteractions instance = new GeoserverInteractions(
				geoserverEndpoint,
				GeoserverInteractions.DEFAULT_USER,
				GeoserverInteractions.DEFAULT_PASS);
		Workspace workspace = new Workspace();
		workspace.setName("testCreateShapefileDatastoreWorkdspace");
		workspace.setUri(new URI("gov.usgs.cida.manager.geoserver"));
		instance.createWorkspace(workspace);
		instance.existsWorkspace(workspace.getName(), false);
		
		Datastore datastore = new Datastore(workspace.getName(), "testCreateShapefileDatastoreWorkdspaceTest", new URL("file:///test"));
		datastore.setDatastoreType(Datastore.TYPE.SHAPEFILE);
		assertTrue(instance.createDataStore(datastore));
		assertTrue(instance.existsDatastore(workspace.getName(), datastore.getName(), false));
		
		assertTrue(instance.removeDataStore(datastore, true));
		assertFalse(instance.existsDatastore(workspace.getName(), datastore.getName(), false));
	}

}
