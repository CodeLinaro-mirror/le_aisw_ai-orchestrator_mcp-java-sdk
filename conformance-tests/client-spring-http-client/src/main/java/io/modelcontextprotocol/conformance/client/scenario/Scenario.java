package io.modelcontextprotocol.conformance.client.scenario;

import io.modelcontextprotocol.client.McpSyncClient;

public interface Scenario {

	default McpSyncClient getMcpClient() {
		throw new IllegalStateException("Client not set");
	}

	void execute(String serverUrl);

}
