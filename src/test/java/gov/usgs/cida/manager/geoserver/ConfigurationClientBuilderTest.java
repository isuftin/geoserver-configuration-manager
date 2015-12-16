package gov.usgs.cida.manager.geoserver;

import gov.usgs.cida.manager.geoserver.model.ModelVerifyException;
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
	public void testHelp() throws CmdLineException, IOException, ModelVerifyException {
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
	public void testFileLocating() throws CmdLineException, IOException, URISyntaxException, ModelVerifyException {
		System.out.println("testFileLocating");
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream st = new PrintStream(os);
		ConfigurationClientBuilder subject = new ConfigurationClientBuilder(st);
		ConfigurationClient buildClient = subject.buildClient(new String[]{
			"-f", FileUtils.toFile(this.getClass().getResource("/test.yml")).getAbsolutePath(),
			"-e", "https://127.0.0.1:8080/geoserver",
			"-u", "test",
			"-p", "test"
		});
		assertNotNull(buildClient);
	}
	
	@Test(expected = org.kohsuke.args4j.CmdLineException.class)
	public void testMissingParameters() throws CmdLineException, IOException, URISyntaxException, ModelVerifyException {
		System.out.println("testIncorrectParameters");
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream st = new PrintStream(os);
		ConfigurationClientBuilder subject = new ConfigurationClientBuilder(st);
		ConfigurationClient buildClient = subject.buildClient(new String[]{
			"-f", FileUtils.toFile(this.getClass().getResource("/test.yml")).getAbsolutePath(),
			"-e", "https://127.0.0.1:8080/geoserver"
		});
		buildClient.config.verify();
	}
	
	@Test(expected = gov.usgs.cida.manager.geoserver.model.ModelVerifyException.class)
	public void testBlankUsernameParameter() throws CmdLineException, IOException, URISyntaxException, ModelVerifyException {
		System.out.println("testBlankParameters");
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream st = new PrintStream(os);
		ConfigurationClientBuilder subject = new ConfigurationClientBuilder(st);
		ConfigurationClient buildClient = subject.buildClient(new String[]{
			"-f", FileUtils.toFile(this.getClass().getResource("/test.yml")).getAbsolutePath(),
			"-e", "https://127.0.0.1:8080/geoserver",
			"-u", "",
			"-p", "test"
		});
		buildClient.config.verify();
	}
	
	@Test(expected = gov.usgs.cida.manager.geoserver.model.ModelVerifyException.class)
	public void testBlankPasswordParameter() throws CmdLineException, IOException, URISyntaxException, ModelVerifyException {
		System.out.println("testBlankPasswordParameter");
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream st = new PrintStream(os);
		ConfigurationClientBuilder subject = new ConfigurationClientBuilder(st);
		ConfigurationClient buildClient = subject.buildClient(new String[]{
			"-f", FileUtils.toFile(this.getClass().getResource("/test.yml")).getAbsolutePath(),
			"-e", "https://127.0.0.1:8080/geoserver",
			"-u", "test",
			"-p", ""
		});
		buildClient.config.verify();
	}
	
	@Test(expected = org.kohsuke.args4j.CmdLineException.class)
	public void testBlankEndpointParameter() throws CmdLineException, IOException, URISyntaxException, ModelVerifyException {
		System.out.println("testBlankPasswordParameter");
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream st = new PrintStream(os);
		ConfigurationClientBuilder subject = new ConfigurationClientBuilder(st);
		ConfigurationClient buildClient = subject.buildClient(new String[]{
			"-f", FileUtils.toFile(this.getClass().getResource("/test.yml")).getAbsolutePath(),
			"-e", "",
			"-u", "test",
			"-p", "test"
		});
		buildClient.config.verify();
	}

	@Test(expected = gov.usgs.cida.manager.geoserver.model.ModelVerifyException.class)
	public void testNonHTTPSEndpointParameter() throws CmdLineException, IOException, URISyntaxException, ModelVerifyException {
		System.out.println("testBlankPasswordParameter");
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream st = new PrintStream(os);
		ConfigurationClientBuilder subject = new ConfigurationClientBuilder(st);
		ConfigurationClient buildClient = subject.buildClient(new String[]{
			"-f", FileUtils.toFile(this.getClass().getResource("/test.yml")).getAbsolutePath(),
			"-e", "ftp://127.0.0.1:22/geoserver",
			"-u", "test",
			"-p", "test"
		});
		buildClient.config.verify();
	}
}
