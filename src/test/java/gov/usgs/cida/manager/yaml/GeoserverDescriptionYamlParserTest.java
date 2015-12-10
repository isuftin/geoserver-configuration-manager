package gov.usgs.cida.manager.yaml;

import gov.usgs.cida.manager.geoserver.model.GeoserverConfig;
import java.io.File;
import java.net.URISyntaxException;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author isuftin
 */
public class GeoserverDescriptionYamlParserTest {

	private static File yamlTestFile;

	public GeoserverDescriptionYamlParserTest() {
	}


	@Before
	public void setUp() throws URISyntaxException {
		yamlTestFile = FileUtils.toFile(this.getClass().getResource("/test.yml"));
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of parse method, of class GeoserverDescriptionYamlParser.
	 */
	@Test
	public void testParse() throws Exception {
		System.out.println("parse");
		GeoserverDescriptionYamlParser cfg = new GeoserverDescriptionYamlParser(yamlTestFile);
		GeoserverConfig result = cfg.parse();
		
		// Test wipeDefaultWorkspaces
		assertTrue(result.isWipeDefaultWorkspaces());
		
		// Test workspaces
		assertEquals(result.getWorkspaces().size(), 2);
		assertEquals(result.getWorkspaces().get(0).getName(), "yaml.test.workspace");
		assertNotNull(result.getWorkspaces().get(0).getUri());
		assertEquals(result.getWorkspaces().get(1).getName(), "another.yaml.test.workspace");
		assertNull(result.getWorkspaces().get(1).getUri());
		
		// Test datastores
		assertEquals(result.getWorkspaces().get(0).getDatastores().size(), 1);
		assertEquals(result.getWorkspaces().get(0).getDatastores().get(0).getName(), "yaml.test.store");
		assertEquals(result.getWorkspaces().get(0).getDatastores().get(0).getUrl().toString(), "http://biogeo.ucdavis.edu/data/diva/rds/USA_rds.zip");
		assertEquals(result.getWorkspaces().get(0).getDatastores().get(0).getDescription(), "A test datastore");
		assertTrue(result.getWorkspaces().get(0).getDatastores().get(0).isEnabled());
	}

}
