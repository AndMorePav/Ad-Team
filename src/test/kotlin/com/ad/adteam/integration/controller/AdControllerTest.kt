package com.ad.adteam.integration.controller

import com.ad.adteam.controller.AdController
import com.ad.adteam.domain.AdEntity
import com.ad.adteam.domain.UserEntity
import com.ad.adteam.service.AdService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.*
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@WebMvcTest(AdController::class)
@AutoConfigureMockMvc
internal class AdControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
    @MockBean val adService: AdService
) {

    private val baseUrl = "/api/ads"
    private val author = UserEntity(
        1, "test login", "test name",
        "test surname", 18, "test", emptyList()
    )
    private val adEntityList = mutableListOf(
        AdEntity(1, "test title", "test title", author),
        AdEntity(1, "test title2", "test", author)
    )

    @Nested
    @DisplayName("getAdsByUser()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetAdsByUser {
        @Test
        fun `should return all users ads`() {
            //given
            Mockito.`when`(adService.getAdsByUser(anyLong())).thenReturn(adEntityList)
            val userId = 1L
            //when/then
            mockMvc.get("$baseUrl/$userId")
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].title") { value("test title") }
                    jsonPath("$[1].title") { value("test title2") }
                }
        }
    }

    @Nested
    @DisplayName("createAd()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class CreateAd {
        @Test
        fun `should create ad`() {
            //given
            Mockito.`when`(adService.createAd(adEntityList[0])).thenReturn(adEntityList[0])
            //when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(adEntityList[0])
            }
            //then
            performPost
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(adEntityList[0]))
                    }
                }
        }
    }

    @Nested
    @DisplayName("updateAd()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class UpdateAd {
        @Test
        fun `should update an existing ad`() {
            //given
            val updatedAd = AdEntity(1, "Changed Test Title", "Changed Test Content", author)
            Mockito.`when`(adService.updateAd(updatedAd)).thenReturn(updatedAd)
            //when
            val performPatch = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedAd)
            }
            //then
            performPatch
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(updatedAd))
                    }
                }
        }
    }

    @Nested
    @DisplayName("deleteAd()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteAd {
        @Test
        fun `should delete an existing ad`() {
            //given
            val adId = 1L
            //when
            mockMvc.delete("$baseUrl/$adId")
                .andExpect {
                    status { isNoContent() }
                }
        }
    }
}
