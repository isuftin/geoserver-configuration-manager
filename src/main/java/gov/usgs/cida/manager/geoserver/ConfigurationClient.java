package gov.usgs.cida.manager.geoserver;

import gov.usgs.cida.manager.geoserver.model.GeoserverConfig;
import gov.usgs.cida.manager.geoserver.model.ModelVerifyException;
import java.io.IOException;
import org.kohsuke.args4j.CmdLineException;
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
	
	public static void main(String[] args) {
		ConfigurationClient client = null;
		try {
			client = new ConfigurationClientBuilder().buildClient(args);
		} catch (CmdLineException | IOException ex) {
			LOGGER.error("There was an error when creating the configuration client", ex);
		}
		
		if (client != null) {
			try {
				client.run();
			} catch (ModelVerifyException ex) {
				LOGGER.error("There was an error while verifying the configuration", ex);
			}
		}
	}

	private boolean run() throws ModelVerifyException {
		this.config.verify();
		return true;
	}

}
