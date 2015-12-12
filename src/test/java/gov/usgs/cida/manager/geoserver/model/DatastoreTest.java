package gov.usgs.cida.manager.geoserver.model;

import it.geosolutions.geoserver.rest.encoder.GSAbstractStoreEncoder;
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
		instance.setType("incorrect");
		assertTrue(false); // Fail if we get here
	}
	
	@Test
	public void testCreateDatastoreWithCorrectType() {
		System.out.println("createDatastoreWithCorrectType");
		Datastore instance = new Datastore();
		
		instance.setType("shapefile");
		Class<? extends GSAbstractStoreEncoder> encoder = instance.getEncoder();
		assertEquals(encoder.getName(), "it.geosolutions.geoserver.rest.encoder.datastore.GSShapefileDatastoreEncoder");
		
		instance = new Datastore();
		instance.setType("shapefileDirectory");
		assertEquals(instance.getEncoder().getName(), "it.geosolutions.geoserver.rest.encoder.datastore.GSDirectoryOfShapefilesDatastoreEncoder");
		
		instance = new Datastore();
		instance.setType("postgis");
		assertEquals(instance.getEncoder().getName(), "it.geosolutions.geoserver.rest.encoder.datastore.GSPostGISDatastoreEncoder");
		
		instance = new Datastore();
		instance.setType("oracle");
		assertEquals(instance.getEncoder().getName(), "it.geosolutions.geoserver.rest.encoder.datastore.GSOracleNGDatastoreEncoder");
		
		instance = new Datastore();
		instance.setType("arcsde");
		assertEquals(instance.getEncoder().getName(), "it.geosolutions.geoserver.rest.encoder.datastore.GSArcSDEDatastoreEncoder");
		
	}

}
