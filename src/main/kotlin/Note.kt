import java.time.LocalDateTime

data class Note(
    val id : Int,
    val title : String,
    val text : String,
    val date : Int, //LocalDateTime = LocalDateTime.now(),
    val viewURL : String = "url",
    val comments : Int = 0,
    val readComments : Int = 0,
    val privacyView : PrivacyEnum? = PrivacyEnum.ALL,
    val privacyComments : PrivacyEnum? = PrivacyEnum.ALL
) {

    override fun toString(): String {
        return "$title: $text ($id)"
    }
}