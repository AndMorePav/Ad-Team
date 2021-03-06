package com.ad.adteam.controller

import com.ad.adteam.domain.User
import com.ad.adteam.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getUsers() = userService.getUsers()

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long) = userService.getUser(userId)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody user: User) = userService.createUser(user)

    @PatchMapping("/{userId}")
    fun updateUser(@PathVariable userId: Long, @RequestBody user: User) = userService.updateUser(userId, user)

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMapping(@PathVariable userId: Long): Unit = userService.deleteUser(userId)
}