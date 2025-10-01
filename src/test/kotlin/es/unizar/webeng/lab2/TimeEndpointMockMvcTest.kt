package es.unizar.webeng.lab2

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.LocalDateTime

@WebMvcTest(TimeController::class)
class TimeEndpointMockMvcTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var timeProvider: TimeProvider

    @Test
    fun `devuelve la hora mockeada`() {
        val mockTime = LocalDateTime.of(2025, 1, 1, 12, 0)
        Mockito.`when`(timeProvider.now()).thenReturn(mockTime)

        val result = mockMvc.get("/time")
            .andExpect { status { isOk() } }
            .andReturn()

        val response = objectMapper.readTree(result.response.contentAsString)
        assertEquals("2025-01-01T12:00:00", response["time"].asText())
    }

    @Test
    fun `devuelve otra hora mockeada`() {
        val mockTime = LocalDateTime.of(2030, 6, 15, 8, 30)
        Mockito.`when`(timeProvider.now()).thenReturn(mockTime)

        val result = mockMvc.get("/time")
            .andExpect { status { isOk() } }
            .andReturn()

        val response = objectMapper.readTree(result.response.contentAsString)
        assertEquals("2030-06-15T08:30:00", response["time"].asText())
    }
}
