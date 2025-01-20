package org.example.a25_01_sparks

fun main() {
    var a: String? = null

    println(a?.uppercase() ?: "-")

    toto("", 5)
    toto(b=5)  // toto(null, 5)


    var student = Student("coucou")
    student.name = ""  // student.setName("");

}

data class Student(var name:String? = null)


fun toto(a: String? = null, b: Int = 5): String {
    return a ?: "-"
}