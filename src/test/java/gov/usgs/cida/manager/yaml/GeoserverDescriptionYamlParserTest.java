package gov.usgs.cida.manager.yaml;

import gov.usgs.cida.manager.geoserver.model.Datastore;
import gov.usgs.cida.manager.geoserver.model.GeoserverConfig;
import gov.usgs.cida.manager.geoserver.model.Workspace;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
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
		result.setUsername("geoserver");
		result.setPassword("admin");
		result.setEndpoint(new URL("http://127.0.0.1:8080/geoserver"));
		assertNotNull(result);
		result.verify();
		
		assertTrue(result.isStopOnError());
		
		System.out.println(result.getWorkspaces());
		// Test workspaces
		assertEquals(result.getWorkspaces().size(), 2);
		assertEquals(result.getWorkspaces().get(0).getName(), "yaml.test.workspace");
		assertNotNull(result.getWorkspaces().get(0).getUri());
		assertEquals(result.getWorkspaces().get(1).getName(), "another.yaml.test.workspace");
		assertNull(result.getWorkspaces().get(1).getUri());
		
		// Test datastores
		Workspace workspace = result.getWorkspaces().get(0);
		assertEquals(workspace.getDatastores().size(), 1);
		Datastore datastore = workspace.getDatastores().get(0);
		assertEquals(datastore.getName(), "yaml.test.store");
		assertEquals(datastore.getUrl().toString(), "file:/some/directory/that/im/sure/is/awesome");
		assertEquals(datastore.getDescription(), "A test datastore");
		assertTrue(datastore.isEnabled());
		
	}

}
