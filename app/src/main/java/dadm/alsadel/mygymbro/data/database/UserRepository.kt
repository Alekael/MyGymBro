package dadm.alsadel.mygymbro.data.database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import dadm.alsadel.mygymbro.domain.model.Session
import dadm.alsadel.mygymbro.domain.model.User

interface UserRepository {

    suspend fun addUser(user: User)

    suspend fun getUserByNickName(nickName : String) : DatabaseReference

    suspend fun getUserByEmail(email : String) : Query

    suspend fun getUserSessionsByNickname(nickname: String): Query

    suspend fun getUserTrainingByNickname(nickname: String, dayOfWeek: String): Query

    suspend fun addUserSession(nickname: String, number: String, session: Session)
}