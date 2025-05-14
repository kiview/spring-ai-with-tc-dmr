package dev.wittek.spring_ai_with_tc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringAiWithTcApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void contextLoads() {
	}

	@Test
	void chatEndpointShouldReturnResponse() {
		ResponseEntity<String> response = restTemplate.getForEntity(
				"http://localhost:" + port + "/chat?message=What is Spring Boot?",
				String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotEmpty();

		System.out.println("Chat response: " + response.getBody());
	}

	@Test
	void chatStreamEndpointShouldReturnResponseStream() {
		webTestClient = WebTestClient.bindToServer()
				.baseUrl("http://localhost:" + port)
				.responseTimeout(Duration.ofSeconds(30))
				.build();

		webTestClient.get()
				.uri("/chat-stream?message=Tell me about Spring AI")
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class)
				.value(response -> assertThat(response).isNotEmpty());
	}

	@Test
	void f1ResultsEndpointShouldReturnF1Data() {

		ResponseEntity<String> response = restTemplate.getForEntity(
				"http://localhost:" + port + "/f1?message=What was the last F1 race of this season and who won?",
				String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotEmpty();

		System.out.println("F1 response: " + response.getBody());

		// Checking for common F1-related terms that should appear in response
		assertThat(response.getBody().toLowerCase()).containsAnyOf("miami", "piastri");

	}

}
