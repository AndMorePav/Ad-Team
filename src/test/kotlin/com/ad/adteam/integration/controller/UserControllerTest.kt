package com.ad.adteam.integration.controller

import com.ad.adteam.controller.UserController
import com.ad.adteam.domain.User
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
    private val userList = mutableListOf(
            User(1, "test", "test", "test", 18, "test"),
            User(2, "test2", "test2", "test2", 18, "test2")
    )

    @BeforeEach
    fun before() {
        userList.add(User(1, "test", "test", "test", 18, "test"))
    }

    @Nested
    @DisplayName("getUsers()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetUsers {
        @Test
        fun `should return all users`() {
            //given
            Mockito.`when`(userService.getUsers()).thenReturn(userList)
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
    inner class GetUser {
        @Test
        fun `should return separate user`() {
            //given
            val userId = 1L
            Mockito.`when`(userService.getUser(anyLong())).thenReturn(userList[0])
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
    inner class CreateUser {
        @Test
        fun `should return created user`() {
            //given
            Mockito.`when`(userService.createUser(userList[0])).thenReturn(userList[0])
            //when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(userList[0])
            }
            //then
            performPost
                    .andExpect {
                        status { isCreated() }
                        content {
                            contentType(MediaType.APPLICATION_JSON)
                            json(objectMapper.writeValueAsString(userList[0]))
                        }
                    }
        }
    }

    @Nested
    @DisplayName("updateUser()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class UpdateUser {
        @Test
        fun `should update an existing user`() {
            //given
            val userId = 1L
            val updatedUser = User(1, "updated", "updated", "updated", 18, "updated")
            Mockito.`when`(userService.updateUser(userId, updatedUser)).thenReturn(updatedUser)
            //when
            val performPatch = mockMvc.patch("$baseUrl/$userId") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedUser)
            }
            //then
            performPatch
                    .andExpect {
                        status { isOk() }
                        content {
                            contentType(MediaType.APPLICATION_JSON)
                            json(objectMapper.writeValueAsString(updatedUser))
                        }
                    }
        }

        @Test
        fun `should return BAD_REQUEST when user doesn't exist`() {
            //given
            val userId = -1L
            Mockito.`when`(userService.updateUser(userId, userList[0])).thenThrow(IllegalArgumentException())
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
    inner class DeleteUser {
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
