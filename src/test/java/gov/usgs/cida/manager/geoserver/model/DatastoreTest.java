package gov.usgs.cida.manager.geoserver.model;

import it.geosolutions.geoserver.rest.encoder.GSAbstractStoreEncoder;
import java.net.MalformedURLException;
import java.net.URL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author isuftin
 */
public class DatastoreTest {

	public DatastoreTest() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateDatastoreWithIncorrectType() {
		System.out.println("createDatastoreWithIncorrectType");
		Datastore instance = new Datastore();
		instance.setTypeName("incorrect");
		assertTrue(false); // Fail if we get here
	}
	
	@Test
	public void testCreateDatastoreWithCorrectType() throws MalformedURLException {
		System.out.println("createDatastoreWithCorrectType");
		Datastore instance = new Datastore("workspace", "test", new URL("file:///test"));
		
		instance.setTypeName("shapefile");
		GSAbstractStoreEncoder encoder = instance.getEncoder();
		assertEquals("it.geosolutions.geoserver.rest.encoder.datastore.GSShapefileDatastoreEncoder", encoder.getClass().getName());
		
		instance = new Datastore("workspace",  "test", new URL("file:///test"));
		instance.setTypeName("shapefileDirectory");
		assertEquals("it.geosolutions.geoserver.rest.encoder.datastore.GSDirectoryOfShapefilesDatastoreEncoder", instance.getEncoder().getClass().getName());
		
		instance = new Datastore("workspace", "test");
		assertEquals("it.geosolutions.geoserver.rest.encoder.datastore.GSPostGISDatastoreEncoder", instance.getEncoder().getClass().getName());
		
		instance = new Datastore("workspace",  "testName", "testDbName");
		assertEquals("it.geosolutions.geoserver.rest.encoder.datastore.GSOracleNGDatastoreEncoder", instance.getEncoder().getClass().getName());
		
		instance = new Datastore("workspace",  "testName", "testServerName", "testUsername");
		assertEquals("it.geosolutions.geoserver.rest.encoder.datastore.GSArcSDEDatastoreEncoder", instance.getEncoder().getClass().getName());
		
	}

}
