package es.unizar.webeng.lab2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TimeEndpointIntegrationTest(
    @Autowired val restTemplate: TestRestTemplate,
    @LocalServerPort val port: Int
) {
    @Test
    fun `time endpoint returns current server time`() {
        val response = restTemplate.getForEntity("http://localhost:$port/time", TimeDTO::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isNotNull
        val now = LocalDateTime.now()
        // Comprobamos que la hora devuelta está cerca de la actual (unos segundos de margen)
        assertThat(response.body!!.time).isBeforeOrEqualTo(now)
        assertThat(response.body!!.time).isAfter(now.minusMinutes(1))
    }
}

