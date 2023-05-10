package dadm.alsadel.mygymbro.data.database

import com.google.firebase.database.DatabaseReference
import dadm.alsadel.mygymbro.data.FirebaseConnection
import dadm.alsadel.mygymbro.domain.model.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val firebaseConnection: FirebaseConnection) : UserRepository {

    private val dbReference : DatabaseReference = firebaseConnection.db.getReference("Users")

    override suspend fun addUser(user: User) {

        dbReference.child(user.nickname).setValue(user)

    }

    override suspend fun getUser(email: String): User {
        TODO("Not yet implemented")
    }

}