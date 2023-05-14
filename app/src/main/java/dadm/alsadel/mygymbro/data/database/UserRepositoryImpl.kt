package dadm.alsadel.mygymbro.data.database

import android.provider.ContactsContract.CommonDataKinds.Nickname
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import dadm.alsadel.mygymbro.data.FirebaseConnection
import dadm.alsadel.mygymbro.data.Session
import dadm.alsadel.mygymbro.domain.model.User
import java.time.DayOfWeek
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val firebaseConnection: FirebaseConnection) : UserRepository {

    private val dbUser : DatabaseReference = firebaseConnection.db.getReference("Users")
    private val dbSessions : DatabaseReference = firebaseConnection.db.getReference("Sessions")
    private val dbTrainingPlans : DatabaseReference = firebaseConnection.db.getReference("TrainingPlans")

    override suspend fun addUser(user: User) {
        dbUser.child(user.nickname).setValue(user)
    }

    override suspend fun getUserByNickName(nickname: String): DatabaseReference {
        return dbUser.child(nickname)
    }

    override suspend fun getUserByEmail(email: String): Query {
        return dbUser.orderByChild("email").equalTo(email)
    }

    override suspend fun getUserSessionsByNickname(nickname: String): Query {
        return dbSessions.child(nickname)
    }

    override suspend fun getUserTrainingByNickname(nickname: String, dayOfWeek: String): Query {
        return dbTrainingPlans.child(nickname).child(dayOfWeek)
    }

    override suspend fun addUserSession(nickname: String, number: String, session: Session) {
        dbSessions.child(nickname).child(number).setValue(session)
    }

}