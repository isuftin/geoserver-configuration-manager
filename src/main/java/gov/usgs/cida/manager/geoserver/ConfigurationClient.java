package gov.usgs.cida.manager.geoserver;

import gov.usgs.cida.manager.geoserver.model.Datastore;
import gov.usgs.cida.manager.geoserver.model.GeoserverConfig;
import gov.usgs.cida.manager.geoserver.model.ModelVerifyException;
import gov.usgs.cida.manager.geoserver.model.Workspace;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
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

		if (client != null) {
			try {
				client.config.verify();
				try {
					client.run();
				} catch (MalformedURLException | ConfigurationClientException ex) {
					LOGGER.error("There was an error while running the configuration", ex);
				}
			} catch (ModelVerifyException ex) {
				LOGGER.error("There was an error while verifying the configuration", ex);
			}

		}
	}

	protected boolean run() throws MalformedURLException, ConfigurationClientException {
		this.interactions = new GeoserverInteractions(
				this.config.getEndpoint(),
				this.config.getUsername(),
				this.config.getPassword()
		);

		// First create any missing workspaces
		createMissingWorkspaces();

		for (Workspace workspace : this.config.getWorkspaces()) {
			if (this.interactions.existsWorkspace(workspace, true)) {
				if (!workspace.getDatastores().isEmpty()) {
					createMissingDatastores(workspace.getDatastores());
				}
			} else {
				logOrThrowError(String.format("Workspace %s does not exist. Datastores will not be created under this workspace", workspace.toString()));
			}
		}

		return true;
	}

	protected void createMissingDatastores(List<Datastore> datastores) throws ConfigurationClientException {
		for (Datastore datastore : datastores) {
			if (this.interactions.existsDatastore(datastore)) {
				// Datastore already exists. 
				if (!this.interactions.updateDatastore(datastore)) {
					logOrThrowError(String.format("Datastore %s could not be updated", datastore.toString()));
				}
			} else if (!this.interactions.createDataStore(datastore)) {
				// Datastore does not yet exist. Create it.
				logOrThrowError(String.format("Datastore %s could not be created", datastore.toString()));
			}
		}
	}

	protected void createMissingWorkspaces() throws ConfigurationClientException {
		List<String> workspaces = this.interactions.getWorkspaceNames();
		List<Workspace> missingWorkspaces = this.config.getWorkspaces().stream().filter(
				(workspace) -> (!workspaces.contains(workspace.getName()))
		).collect(Collectors.toList());

		for (Workspace workspace : missingWorkspaces) {
			if (this.interactions.createWorkspace(workspace)) {
				LOGGER.info(String.format("Workspace %s created", workspace.toString()));
			} else {
				logOrThrowError(String.format("Workspace %s could not be created", workspace.toString()));
			}
		}
	}

	private void logOrThrowError(String errorMessage) throws ConfigurationClientException {
		if (this.config.isStopOnError()) {
			throw new ConfigurationClientException(errorMessage);
		} else {
			LOGGER.warn(errorMessage);
		}
	}

}
