package dadm.alsadel.mygymbro.data.database

import com.google.firebase.database.DatabaseReference
import dadm.alsadel.mygymbro.domain.model.User

interface UserRepository {

    suspend fun addUser(user: User)

    suspend fun getUserByNickName(nickName : String) : DatabaseReference
}