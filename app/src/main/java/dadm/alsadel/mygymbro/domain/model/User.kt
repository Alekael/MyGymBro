package dadm.alsadel.mygymbro.domain.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(val email: String, val nickname: String, val age: Int?, val weight: Double?,
           val height: Double?, val gender: String?, val level: String?, val interest: String?, val days: MutableList<String>?, val time: Double?) {
}