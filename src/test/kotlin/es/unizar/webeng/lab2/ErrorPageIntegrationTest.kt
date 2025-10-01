package es.unizar.webeng.lab2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ErrorPageIntegrationTest(
    @Autowired val restTemplate: TestRestTemplate,
    @LocalServerPort val port: Int
) {

    @Test
    fun `custom error page is displayed on 404`() {
        val headers = org.springframework.http.HttpHeaders()
        headers.accept = listOf(org.springframework.http.MediaType.TEXT_HTML)
        val entity = org.springframework.http.HttpEntity<String>(headers)
        val response = restTemplate.exchange(
            "http://localhost:$port/noexiste",
            org.springframework.http.HttpMethod.GET,
            entity,
            String::class.java
        )
        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
        assertThat(response.body).contains("¡Ups! Ha ocurrido un error")
        assertThat(response.body).contains("Código:")
        assertThat(response.body).contains("Error:")
        assertThat(response.body).contains("Mensaje:")
        assertThat(response.body).contains("Ruta:")
    }
}
