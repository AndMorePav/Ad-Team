package com.ad.adteam.integration.controller

import com.ad.adteam.controller.UserController
import com.ad.adteam.domain.UserEntity
import com.ad.adteam.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.*
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*


@WebMvcTest(UserController::class)
@AutoConfigureMockMvc
internal class UserControllerTest @Autowired constructor(
        val mockMvc: MockMvc,
        val objectMapper: ObjectMapper,
        @MockBean val userService: UserService
) {

    private val baseUrl = "/api/users"
    private val userEntityList = mutableListOf(
            UserEntity(1, "test", "test", "test", 18, "test", emptyList()),
            UserEntity(2, "test2", "test2", "test2", 18, "test2", emptyList())
    )

    @Nested
    @DisplayName("getUsers()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetUsers {
        @Test
        fun `should return all users`() {
            //given
            Mockito.`when`(userService.getUsers()).thenReturn(userEntityList)
            //when/then
            mockMvc.get(baseUrl)
                    .andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$[0].login") { value("test") }
                        jsonPath("$[1].login") { value("test2") }
                    }
        }
    }

    @Nested
    @DisplayName("getUser()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetUserEntity {
        @Test
        fun `should return separate user`() {
            //given
            val userId = 1L
            Mockito.`when`(userService.getUser(anyLong())).thenReturn(userEntityList[0])
            //when/then
            mockMvc.get("$baseUrl/$userId")
                    .andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$.login") { value("test") }
                    }
        }
    }

    @Nested
    @DisplayName("createUser()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class CreateUserEntity {
        @Test
        fun `should return created user`() {
            //given
            Mockito.`when`(userService.createUser(userEntityList[0])).thenReturn(userEntityList[0])
            //when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(userEntityList[0])
            }
            //then
            performPost
                    .andExpect {
                        status { isCreated() }
                        content {
                            contentType(MediaType.APPLICATION_JSON)
                            json(objectMapper.writeValueAsString(userEntityList[0]))
                        }
                    }
        }
    }

    @Nested
    @DisplayName("updateUser()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class UpdateUserEntity {
        @Test
        fun `should update an existing user`() {
            //given
            val userId = 1L
            val updatedUserEntity = UserEntity(1, "updated", "updated", "updated", 18, "updated",emptyList())
            Mockito.`when`(userService.updateUser(userId, updatedUserEntity)).thenReturn(updatedUserEntity)
            //when
            val performPatch = mockMvc.patch("$baseUrl/$userId") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedUserEntity)
            }
            //then
            performPatch
                    .andExpect {
                        status { isOk() }
                        content {
                            contentType(MediaType.APPLICATION_JSON)
                            json(objectMapper.writeValueAsString(updatedUserEntity))
                        }
                    }
        }

        @Test
        fun `should return BAD_REQUEST when user doesn't exist`() {
            //given
            val userId = -1L
            Mockito.`when`(userService.updateUser(userId, userEntityList[0])).thenThrow(IllegalArgumentException())
            //when/then
            mockMvc.patch("$baseUrl/$userId")
                    .andExpect {
                        status { isBadRequest() }
                    }
        }
    }

    @Nested
    @DirtiesContext
    @DisplayName("DeleteUser()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteUserEntity {
        @Test
        fun `should delete the user with the given id`() {
            //given
            val userId = 1L
            //when/then
            mockMvc.delete("$baseUrl/$userId")
                    .andExpect {
                        status { isNoContent() }
                    }
        }

        @Test
        fun `should return NO_CONTENT when user doesn't exist`() {
            //given
            val userId = -1L
            Mockito.`when`(userService.deleteUser(userId)).thenThrow(NoSuchElementException())
            //when/then
            mockMvc.delete("$baseUrl/$userId")
                    .andExpect {
                        status { isNotFound() }
                    }
        }
    }
}
