data class Comment(
    val id : Int,
    val noteId : Int,
    val message : String,
    val date : Int, //LocalDateTime = LocalDateTime.now(),
    val replyTo : Int? = null,
    val delete : Boolean = false
) {
    override fun toString(): String {
        return message
    }
}