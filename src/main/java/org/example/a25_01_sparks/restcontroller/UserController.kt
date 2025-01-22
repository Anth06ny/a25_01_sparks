package org.example.a25_01_sparks.restcontroller

import jakarta.validation.Valid
import org.example.a25_01_sparks.model.UserBean
import org.example.a25_01_sparks.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/users")
class UserController {


    //http://localhost:8080/users
    //{"login":"aaa", "password":"bbb"}
    @PostMapping
    fun createUser(@Valid @RequestBody user: UserBean): ResponseEntity<UserBean> {
       return  ResponseEntity(UserService.save(user), HttpStatus.CREATED)
    }

    //http://localhost:8080/users
    @GetMapping
    fun getAllUsers() =  ResponseEntity.ok(UserService.load())

    //http://localhost:8080/users/1
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long?): ResponseEntity<UserBean> {
        val userBean = UserService.findById(id)
        return if (userBean != null) {
            ResponseEntity.ok(userBean)
        }
        else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    //http://localhost:8080/users/1
    //{"login":"aaa", "password":"bbb"}
    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long?,
        @Valid @RequestBody userDetails: UserBean
    ): ResponseEntity<UserBean> {
        val user = UserService.findById(id)
        return if (user != null) {
            userDetails.id = id //écrase celui reçu dans le JSON au cas ou
            UserService.save(userDetails)
            ResponseEntity(userDetails, HttpStatus.OK)
        }
        else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    //http://localhost:8080/users/1
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        return if (UserService.deleteById(id)) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        }
        else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}