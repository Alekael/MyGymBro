package dadm.alsadel.mygymbro.data
import com.google.firebase.database.IgnoreExtraProperties
import org.json.JSONArray
import org.json.JSONObject

@IgnoreExtraProperties
data class TrainingPlan(val username:String, val plan:HashMap<String,List<Exercise>>){}
