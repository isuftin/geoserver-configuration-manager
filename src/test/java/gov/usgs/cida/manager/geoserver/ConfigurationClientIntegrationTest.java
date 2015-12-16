package gov.usgs.cida.manager.geoserver;

import gov.usgs.cida.manager.geoserver.model.ModelVerifyException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Category;
import org.kohsuke.args4j.CmdLineException;

/**
 *
 * @author isuftin
 */
@Category(IntegrationTests.class)
public class ConfigurationClientIntegrationTest {

	private static String host;
	private static int port;
	private static URL geoserverEndpoint;
	private static File yamlFile;

	public ConfigurationClientIntegrationTest() {
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

	@Test
	public void testBuildClient() throws CmdLineException, IOException, URISyntaxException, ModelVerifyException {
		System.out.println("testBuildClient");
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream st = new PrintStream(os);
		ConfigurationClientBuilder subject = new ConfigurationClientBuilder(st);
		ConfigurationClient buildClient = subject.buildClient(new String[]{
			"-f", yamlFile.getAbsolutePath(),
			"-e", geoserverEndpoint.toURI().toASCIIString(),
			"-u", GeoserverInteractions.DEFAULT_USER,
			"-p", GeoserverInteractions.DEFAULT_PASS
		});
		assertNotNull(buildClient);
	}

	@Test
	public void testRunClient() throws CmdLineException, IOException, URISyntaxException, ModelVerifyException, MalformedURLException, ConfigurationClientException {
		System.out.println("testBuildClient");
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream st = new PrintStream(os);
		ConfigurationClientBuilder subject = new ConfigurationClientBuilder(st);
		ConfigurationClient client = subject.buildClient(new String[]{
			"-f", yamlFile.getAbsolutePath(),
			"-e", geoserverEndpoint.toURI().toASCIIString(),
			"-u", GeoserverInteractions.DEFAULT_USER,
			"-p", GeoserverInteractions.DEFAULT_PASS
		});
		assertNotNull(client);
		assertTrue(client.run());
	}

}
