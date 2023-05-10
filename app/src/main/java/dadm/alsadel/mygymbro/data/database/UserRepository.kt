package dadm.alsadel.mygymbro.data.database

import dadm.alsadel.mygymbro.domain.model.User

interface UserRepository {

    suspend fun addUser(user: User)

    suspend fun getUser(email : String) : User
}