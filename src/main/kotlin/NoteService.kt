
object NoteService {
    var noteId = 0
    var commentId = 0
    val noteMap = mutableMapOf<Int, Note?>()
    val commentMap = mutableMapOf<Int, Comment?>()


    fun add(
        title: String,
        text: String,
        date: Int,
        privacyView: PrivacyEnum? = PrivacyEnum.ALL,
        privacyComments: PrivacyEnum? = PrivacyEnum.ALL
    ) {

        noteId++
        val note = Note(
            id = noteId,
            title = title,
            text = text,
            date = date,
            privacyView = privacyView,
            privacyComments = privacyComments
        )

        noteMap[noteId] = note
    }

    fun printComments(noteId: Int): String {
        val sb = buildString {
            commentMap.values.forEach() {
                if (it?.noteId == noteId && !it.delete) {
                    append("$it\n")
                }
            }
        }

        return sb.toString()
    }


    fun printNotesComments(): String {
        val sb = buildString {
            noteMap.values.forEach() {
                append("$it\n")
                if (it != null) {
                    append(printComments(it.id))
                }
            }
        }

        return sb.toString()
    }


    fun createComment(noteId: Int, message: String, date: Int, replyTo: Int? = null) {
        noteMap[noteId] ?: throw NoteNotFoundException("Заметка с id $noteId не найдена")

        commentId++
        val note = noteMap.getValue(noteId)
        val comment = Comment(commentId, noteId, message, date, replyTo)
        noteMap[noteId] = note!!.copy(comments = note.comments + 1)
        commentMap[commentId] = comment
    }

    fun delete(noteId: Int): Boolean {
        try {
            noteMap.getValue(noteId)
        } catch (e: NoSuchElementException) {
            throw NoteNotFoundException("Заметка с id $noteId не найдена")
        }

        noteMap.remove(noteId)
        return true

    }


    fun deleteComment(commentId: Int): Boolean {
        commentMap[commentId] ?: throw CommentNotFoundException("Комментарий с id $commentId не существует")
        commentMap[commentId] = commentMap.getValue(commentId)?.copy(delete = true)
        val noteId = commentMap.getValue(commentId)!!.noteId
        noteMap[noteId] = noteMap.getValue(noteId)!!.copy(comments = noteMap.getValue(noteId)!!.comments - 1)
        return true


    }

    fun edit(
        noteId: Int,
        title: String,
        text: String,
        privacyComments: PrivacyEnum? = PrivacyEnum.ALL,
        privacyView: PrivacyEnum? = PrivacyEnum.ALL
    ) {
        noteMap[noteId] = noteMap[noteId]?.copy(
            title = title,
            text = text,
            privacyComments = privacyComments,
            privacyView = privacyView
        ) ?: throw NoteNotFoundException("Заметка с id $noteId не найдена")

    }

    fun editComment(commentId: Int, message: String) {
        commentMap[commentId] ?: throw CommentNotFoundException("Комментарий с id $commentId не существует")
        if (commentMap[commentId]!!.delete) {
            throw CommentNotFoundException("Комментарий с id $commentId удален")
        }
        if (message.length < 2) {
            throw CommentLengthTooSmallException("Минимальная длина комментария - 2 символа")
        }

        commentMap[commentId] = commentMap[commentId]!!.copy(message = message)
    }

    fun get(noteIds: ArrayList<Int>, sort: Int = 0): MutableList<Note> {
        val noteList = mutableListOf<Note>()
        noteIds.forEach {
            noteMap[it] ?: throw NoteNotFoundException("Заметка с id $it не найдена")
            val note = noteMap.getValue(it)
            noteList.add(note!!)
        }

        when (sort) {
            1 -> noteList.sortBy { it.date }
            0 -> noteList.sortedByDescending { it.date }
        }

        return noteList
    }

    fun getById(noteId: Int): Note {
        noteMap[noteId] ?: throw NoteNotFoundException("Заметка с id $noteId не найдена")
        return noteMap.getValue(noteId)!!
    }

    fun getComment(noteId: Int, sort: Int = 0): MutableList<Comment> {
        noteMap[noteId] ?: throw NoteNotFoundException("Заметка с id $noteId не найдена")
        val commentList = mutableListOf<Comment>()
        commentMap.values.forEach {
            if (it?.noteId == noteId && !it.delete) {
                commentList.add(it)
            }
        }

        when (sort) {
            1 -> commentList.sortBy { it.date }
            0 -> commentList.sortedByDescending { it.date }
        }

        return commentList
    }

    fun restoreComment(commentId: Int): Boolean {
        commentMap[commentId] ?: throw CommentNotFoundException("Комментарий с id $commentId не существует")

        val comment = commentMap.getValue(commentId)
        if (!comment!!.delete) {
            throw CommentNotFoundException("Комментарий с id $commentId не удален")
        }

        commentMap[commentId] = comment.copy(delete = false)
        return true
    }

    fun clearData() {
        noteMap.clear()
        commentMap.clear()
    }
}