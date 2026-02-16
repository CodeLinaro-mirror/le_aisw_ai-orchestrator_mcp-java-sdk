package io.modelcontextprotocol.conformance.client;

import io.modelcontextprotocol.conformance.client.scenario.Scenario;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class McpClientController {

	private final Scenario scenario;

	McpClientController(Scenario scenario) {
		this.scenario = scenario;
	}

	@GetMapping("/initialize-mcp-client")
	public String execute() {
		this.scenario.getMcpClient().initialize();
		return "OK";
	}

}
