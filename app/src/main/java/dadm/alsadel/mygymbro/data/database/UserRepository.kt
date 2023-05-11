package dadm.alsadel.mygymbro.data.database

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import dadm.alsadel.mygymbro.domain.model.User

interface UserRepository {

    suspend fun addUser(user: User)

    suspend fun getUserByNickName(nickName : String) : DatabaseReference

    suspend fun getUserByEmail(email : String) : Query
}