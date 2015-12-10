package gov.usgs.cida.manager.geoserver;

import gov.usgs.cida.manager.geoserver.model.GeoserverConfig;
import java.io.IOException;
import java.io.PrintStream;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.ParserProperties;
import org.slf4j.LoggerFactory;

/**
 *
 * @author isuftin
 */
public class ConfigurationClient {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ConfigurationClient.class);
	private final GeoserverConfig config;

	public ConfigurationClient(GeoserverConfig config) {
		this.config = config;
	}
	
	public static void main(String[] args) throws CmdLineException, IOException {
		ConfigurationClient client = new ConfigurationClientBuilder().buildClient(args);
		if (client != null) {
			client.run();
		}
	}

	private boolean run() {
		return true;
	}

}
