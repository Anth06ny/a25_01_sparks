package org.example.a25_01_sparks.services

import org.example.a25_01_sparks.model.UserBean


//Classe qui va simuler une gestion de base de données
object UserService {
    private val list = ArrayList<UserBean>()
    private var idNumber = 1L

    //Jeu de donnée si besoin
//    init {
//        list.add(UserBean(1, "toto", "aaa"));
//        list.add(UserBean(2, "tata", "bbb"));
//    }

    //Sauvegarde Create or Update
    fun save(user: UserBean): UserBean {
        //On regarde s'il n'est pas déjà en base
        val userRegister = findById(user.id)
        if (userRegister != null) {
            //on retire celui en base pour remplacer par celui la
            list.remove(userRegister)
        }
        else {
            //on ajoute un id généré
            user.id = idNumber++
        }
        list.add(user)
        return user
    }

    //Retourne la liste
    fun load() = list

    //Permet de trouver l'utilisateur qui utilise cette session
    fun findById(id: Long?) = list.firstOrNull { it.id == id }

    //Supprime l'élément.Retourne true si la liste a changé
    fun deleteById(id: Long) = list.removeIf { it.id == id }
}