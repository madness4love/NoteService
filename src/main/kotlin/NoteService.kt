import java.time.LocalDateTime.now

object NoteService {
    var noteId = 0
    val notes = ArrayList<Note>()

    fun add(title : String,
            text : String,
            privacyView : PrivacyEnum? = PrivacyEnum.ALL,
            privacyComments : PrivacyEnum? = PrivacyEnum.ALL) {

        noteId++
        val note = Note(
            id = noteId,
            ownerId = 1,
            title = title,
            text = text,
            date = now(),
            viewURL = "url",
            privacyView = privacyView,
            privacyComments = privacyComments)
        notes.add(note)
    }

    fun createComment(noteId : Int, ownerId : Int, replyTo : Int? = null, message : String) {

    }

}