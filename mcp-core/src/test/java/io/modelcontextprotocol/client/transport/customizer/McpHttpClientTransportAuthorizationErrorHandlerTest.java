/*
 * Copyright 2026-2026 the original author or authors.
 */
package io.modelcontextprotocol.client.transport.customizer;

import java.net.URI;
import java.net.http.HttpResponse;

import io.modelcontextprotocol.common.McpTransportContext;
import io.modelcontextprotocol.client.transport.HttpRequestSnapshot;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;

/**
 * @author Daniel Garnier-Moiroux
 */
class McpHttpClientTransportAuthorizationErrorHandlerTest {

	private final HttpResponse.ResponseInfo responseInfo = mock(HttpResponse.ResponseInfo.class);

	private final HttpRequestSnapshot requestSnapshot = new HttpRequestSnapshot(URI.create("http://localhost/mcp"),
			"GET", java.net.http.HttpHeaders.of(java.util.Map.of(), (a, b) -> true));

	private final McpTransportContext context = McpTransportContext.EMPTY;

	@Test
	void whenTrueThenRetry() {
		McpHttpClientTransportAuthorizationErrorHandler handler = McpHttpClientTransportAuthorizationErrorHandler
			.fromSync((info, snapshot, ctx) -> true);
		StepVerifier.create(handler.handle(responseInfo, requestSnapshot, context)).expectNext(true).verifyComplete();
	}

	@Test
	void whenFalseThenError() {
		McpHttpClientTransportAuthorizationErrorHandler handler = McpHttpClientTransportAuthorizationErrorHandler
			.fromSync((info, snapshot, ctx) -> false);
		StepVerifier.create(handler.handle(responseInfo, requestSnapshot, context)).expectNext(false).verifyComplete();
	}

	@Test
	void whenExceptionThenPropagate() {
		McpHttpClientTransportAuthorizationErrorHandler handler = McpHttpClientTransportAuthorizationErrorHandler
			.fromSync((info, snapshot, ctx) -> {
				throw new IllegalStateException("sync handler error");
			});
		StepVerifier.create(handler.handle(responseInfo, requestSnapshot, context))
			.expectErrorMatches(t -> t instanceof IllegalStateException && t.getMessage().equals("sync handler error"))
			.verify();
	}

}
