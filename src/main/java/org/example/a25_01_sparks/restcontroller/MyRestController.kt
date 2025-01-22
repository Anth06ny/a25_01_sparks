package org.example.a25_01_sparks.restcontroller

import org.example.a25_01_sparks.model.StudentBean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class MyRestController {




    //http://localhost:8080/testPublic
    @GetMapping("/testPublic")
    fun testPublic(encoder : BCryptPasswordEncoder): String {
        println("/testPublic")
        return "Hello public"
    }

    //http://localhost:8080/testPrivate
    @GetMapping("/testPrivate")
    fun testPrivate(): String {
        println("/testPrivate")
        return "Hello private"
    }

    //http://localhost:8080/testPrivateAdmin
    @GetMapping("/testPrivateAdmin")
    fun testPrivateAdmin(): String {
        println("/testPrivateAdmin")
        return "Hello private admin"
    }



    //http://localhost:8080/test
    @GetMapping("/test")
    fun testMethode(): String {
        println("/test")

        return "helloWorld"
    }

    //http://localhost:8080/getStudent
    @GetMapping("/getStudent")
    fun getStudent(): StudentBean {
        println("/getStudent")
        var student = StudentBean("toto", 12)

        return student
    }

    //http://localhost:8080/max?p1=5&p2=3
    @GetMapping("/max")
    fun max(p1: Int?, p2: Int?): Int? {
        println("/max p1=$p1 p2=$p2")

        if (p1 == null)
            return p2
        else if (p2 == null)
            return p1
        else
            return Math.max(p1, p2)
    }

    //http://localhost:8080/max2?p1=5&p2=3
    @GetMapping("/max2")
    fun max2(p1: String?,  p2: String?): Int? {
        println("/max p1=$p1 p2=$p2")

        var p1Int = p1?.toIntOrNull()
        var p2Int = p2?.toIntOrNull()

        if (p1Int == null)
            return p2Int
        else if (p2Int == null)
            return p1Int
        else
            return Math.max(p1Int, p2Int)
    }

    //http://localhost:8080/receiveStudent
//Json Attendu : {"name": "toto","note": 12}
    @PostMapping("/receiveStudent")
    fun receiveStudent(@RequestBody student: StudentBean) {
        println("/receiveStudent : $student")

        //traitement, mettre en base…
        //Retourner d'autres données
    }

    //http://localhost:8080/increment
    //Json Attendu : {"name": "toto", "note": 12}
    @PostMapping("/increment")
    fun increment(@RequestBody student: StudentBean): StudentBean {
        println("/increment : " + student.name + " : " + student.note)

        student.note++

        return student
    }
}