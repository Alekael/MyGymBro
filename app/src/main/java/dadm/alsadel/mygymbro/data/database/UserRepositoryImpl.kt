package dadm.alsadel.mygymbro.data.database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import dadm.alsadel.mygymbro.data.FirebaseConnection
import dadm.alsadel.mygymbro.domain.model.Session
import dadm.alsadel.mygymbro.domain.model.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val firebaseConnection: FirebaseConnection) : UserRepository {

    private val dbUser : DatabaseReference = firebaseConnection.db.getReference("Users")
    private val dbSessions : DatabaseReference = firebaseConnection.db.getReference("Sessions")
    private val dbTrainingPlans : DatabaseReference = firebaseConnection.db.getReference("TrainingPlans")

    /**
     * Crea un nuevo usuario en la base de datos recibiendo como parametro un objeto de tipo User
     */
    override suspend fun addUser(user: User) {
        dbUser.child(user.nickname).setValue(user)
    }

    /**
     * Devuelve un usuario que pertenezca al nickname que se le pasa por parámetro
     */
    override suspend fun getUserByNickName(nickname: String): DatabaseReference {
        return dbUser.child(nickname)
    }

    /**
     * Devuelve un usuario que pertenezca al email que se le pasa por parámetro
     */
    override suspend fun getUserByEmail(email: String): Query {
        return dbUser.orderByChild("email").equalTo(email)
    }

    /**
     * Devuelve las sesiones de entrenamiento realizados que pertenece al usuario con el nickname que se pasa por parámetro
     */
    override suspend fun getUserSessionsByNickname(nickname: String): Query {
        return dbSessions.child(nickname)
    }

    /**
     * Devuelve los entrenamientos que debería de realizar el usuario en un día en concreto
     * Parametros: nickname del usuario y el dia de la semana
     */
    override suspend fun getUserTrainingByNickname(nickname: String, dayOfWeek: String): Query {
        return dbTrainingPlans.child(nickname).child(dayOfWeek)
    }

    /**
     * Añade la sesión de entrenamiento realizada por el usuario
     * Parametros: nickname del usuario, número de la sesión y
     * la sesión que ha realizado (con su tiempo invertido y la hora en la que ha terminado la sesión)
     */
    override suspend fun addUserSession(nickname: String, number: String, session: Session) {
        dbSessions.child(nickname).child(number).setValue(session)
    }

}