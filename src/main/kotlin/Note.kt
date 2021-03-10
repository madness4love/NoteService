import java.time.LocalDateTime

data class Note(
    val id : Int,
    val ownerId : Int,
    val title : String,
    val text : String,
    val date : LocalDateTime,
    val comments : Int = 0,
    val readComments : Int = 0,
    val viewURL : String,
    val privacyView : PrivacyEnum? = PrivacyEnum.ALL,
    val privacyComments : PrivacyEnum? = PrivacyEnum.ALL,
)