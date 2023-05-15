package dadm.alsadel.mygymbro.domain.model
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class TrainingPlan(val username:String, val plan:HashMap<String,List<Exercise>>){}
