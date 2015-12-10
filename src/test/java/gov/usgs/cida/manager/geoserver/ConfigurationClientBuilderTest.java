package gov.usgs.cida.manager.geoserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kohsuke.args4j.CmdLineException;

/**
 *
 * @author isuftin
 */
public class ConfigurationClientBuilderTest {

	public ConfigurationClientBuilderTest() {
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
	 * Test of buildClient method, of class ConfigurationClientBuilder.
	 */
	@Test
	public void testHelp() throws CmdLineException, IOException {
		System.out.println("testHelp");
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream st = new PrintStream(os);
		ConfigurationClientBuilder subject = new ConfigurationClientBuilder(st);
		ConfigurationClient buildClient = subject.buildClient(new String[]{"-h"});
		assertNull(buildClient);
		String clientBuildOutout = os.toString();
		assertTrue(clientBuildOutout.contains("java Client [options...] arguments..."));
	}

	@Test
	public void testFileLocating() throws CmdLineException, IOException, URISyntaxException {
		System.out.println("testFileLocating");
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream st = new PrintStream(os);
		ConfigurationClientBuilder subject = new ConfigurationClientBuilder(st);
		ConfigurationClient buildClient = subject.buildClient(new String[]{"-f", FileUtils.toFile(this.getClass().getResource("/test.yml")).getAbsolutePath()});
		assertNotNull(buildClient);
	}
	
	

}
