package gov.usgs.cida.manager.geoserver;

import gov.usgs.cida.manager.geoserver.model.GeoserverConfig;
import gov.usgs.cida.manager.geoserver.model.ModelVerifyException;
import gov.usgs.cida.manager.geoserver.model.Workspace;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.kohsuke.args4j.CmdLineException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author isuftin
 */
public class ConfigurationClient {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ConfigurationClient.class);
	protected final GeoserverConfig config;
	private GeoserverInteractions interactions;

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
		
		try {
			if (client != null) {
				client.config.verify();
			}
		} catch (ModelVerifyException ex) {
			LOGGER.error("There was an error while verifying the configuration", ex);
		}

		if (client != null) {
			try {
				client.run();
			} catch (MalformedURLException | ConfigurationClientException ex) {
				LOGGER.error("There was an error while running the configuration", ex);
			}
		}
	}

	private boolean run() throws MalformedURLException, ConfigurationClientException {
		this.interactions = new GeoserverInteractions(
				this.config.getEndpoint(),
				this.config.getUsername(),
				this.config.getPassword()
		);

		// First create any missing workspaces
		createMissingWorkspaces();

		return true;
	}

	protected void createMissingWorkspaces() throws ConfigurationClientException {
		List<String> workspaces = this.interactions.getWorkspaceNames();
		List<Workspace> missingWorkspaces = this.config.getWorkspaces().stream().filter(
				(workspace) -> (!workspaces.contains(workspace.getName()))
		).collect(Collectors.toList());

		for (Workspace workspace : missingWorkspaces) {
			if (this.interactions.createWorkspace(workspace)) {
				LOGGER.info("Workspace " + workspace.toString() + " created");
			} else {
				String error = "Workspace " + workspace.toString() + " could not be created";
				if (this.config.isStopOnError()) {
					throw new ConfigurationClientException(error);
				} else {
					LOGGER.warn(error);
				}
			}
		}
	}

}
