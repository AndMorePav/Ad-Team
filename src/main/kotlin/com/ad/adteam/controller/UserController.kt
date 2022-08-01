package com.ad.adteam.controller

import com.ad.adteam.domain.UserEntity
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
    fun createUser(@RequestBody userEntity: UserEntity) = userService.createUser(userEntity)

    @PatchMapping("/{userId}")
    fun updateUser(@PathVariable userId: Long, @RequestBody userEntity: UserEntity) = userService.updateUser(userId, userEntity)

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMapping(@PathVariable userId: Long): Unit = userService.deleteUser(userId)
}