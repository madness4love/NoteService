import org.junit.Assert.*
import org.junit.Test

class NoteServiceTest {

    @Test
    fun add() {
        val expected = Note(
            id = 1,
            title = "Title",
            text = "text",
            date = 210421,
            privacyView = PrivacyEnum.ALL,
            privacyComments = PrivacyEnum.ALL
        )

        NoteService.add(
            title = "Title",
            text = "text",
            date = 210421,
            privacyView = PrivacyEnum.ALL,
            privacyComments = PrivacyEnum.ALL
        )

        val result = NoteService.getById(1)

        assertEquals(expected, result)
    }

    @Test
    fun printNoteComments() {
        NoteService.add("First note", "text",210421)
        NoteService.add("Second note", "text2", 301585)
        NoteService.createComment(1, "first note comment2", 210422)
        NoteService.createComment(2, "second note comment", 210420)

        val expected = "First note: text (1)\n" +
                "first note comment2\n" +
                "Second note: text2 (2)\n" +
                "second note comment\n"

        val result = NoteService.printNotesComments()

        assertEquals(expected, result)

    }

    @Test
    fun createComment_success() {
        NoteService.add("First note", "text",210421)
        NoteService.add("Second note", "text2", 301585)

        NoteService.createComment(1, "first note comment2", 210422)

        val expected = Comment(1,1, "first note comment2", 210422)

        val result = NoteService.getComment(1)[0]

        assertEquals(expected, result)

    }

    @Test
    fun createComment_noteCommentsIntIncrease() {
        NoteService.add("First note", "text",210421)
        NoteService.add("Second note", "text2", 301585)

        NoteService.createComment(1, "first note comment2", 210422)

        val expected = 1

        val result = NoteService.getById(1).comments

        assertEquals(expected, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun createComment_exception() {
        NoteService.add("First note", "text",210421)
        NoteService.add("Second note", "text2", 301585)

        NoteService.createComment(234, "first note comment2", 210422)
    }

    @Test
    fun delete_success() {
        NoteService.add("First note", "text",210421)
        NoteService.add("Second note", "text2", 301585)

        val result = NoteService.delete(1)

        assertTrue(result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun delete_exception() {
        NoteService.add("First note", "text",210421)
        NoteService.add("Second note", "text2", 301585)

        NoteService.delete(34)
    }

    @Test
    fun deleteComment_success() {
        NoteService.add("First note", "text",210421)
        NoteService.createComment(1, "first note comment2", 210422)

        assertTrue(NoteService.deleteComment(1))
    }

    @Test(expected = CommentNotFoundException::class)
    fun deleteComment_exception() {
        NoteService.add("First note", "text",210421)
        NoteService.createComment(1, "first note comment2", 210422)
        NoteService.deleteComment(2342)
    }

    @Test
    fun edit_success() {
        NoteService.add("First note", "text", 210421)
        val expected = Note(1,"new", "new text", 210421)
        NoteService.edit(1,"new","new text")

        val result = NoteService.getById(1)

        assertEquals(expected,result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun edit_exception() {
        NoteService.add("First note", "text", 210421)

        NoteService.edit(345,"new","new text")
    }

    @Test
    fun editComment_success() {
        NoteService.add("First note", "text",210421)
        NoteService.createComment(1, "first note comment2", 210422)

        NoteService.editComment(1, "new comment")

        val expected = "new comment"
        val result = NoteService.getComment(1)[0].message

        assertEquals(expected, result)
    }

    @Test(expected = CommentNotFoundException::class)
    fun editComment_noComment() {
        NoteService.add("First note", "text",210421)
        NoteService.createComment(1, "first note comment2", 210422)

        NoteService.editComment(23, "new comment")
    }

    @Test(expected = CommentNotFoundException::class)
    fun editComment_commentDeleted() {
        NoteService.add("First note", "text",210421)
        NoteService.createComment(1, "first note comment2", 210422)
        NoteService.deleteComment(1)

        NoteService.editComment(1, "new comment")
    }

    @Test(expected = CommentLengthTooSmallException::class)
    fun editComment_commentTooSmall() {
        NoteService.add("First note", "text",210421)
        NoteService.createComment(1, "first note comment2", 210422)

        NoteService.editComment(1, "n")
    }

    @Test
    fun get_successDefaultSort() {
        NoteService.add("First note", "text", 210421)
        NoteService.add("Second note", "text2", 301585)
        NoteService.add("Third note", "text3", 210420)
        NoteService.add("Forth note", "text4", 123456)

        val note1 = NoteService.getById(1)
        val note2 = NoteService.getById(3)

        val expected = mutableListOf(note1, note2)
        val result = NoteService.get(arrayListOf(1, 3))

        assertEquals(expected, result)

    }

    @Test
    fun get_successDescendingSort() {
        NoteService.add("First note", "text", 210421)
        NoteService.add("Second note", "text2", 301585)
        NoteService.add("Third note", "text3", 210420)
        NoteService.add("Forth note", "text4", 123456)

        val note1 = NoteService.getById(1)
        val note2 = NoteService.getById(3)

        val expected = mutableListOf(note2, note1)
        val result = NoteService.get(arrayListOf(1, 3), 1)

        assertEquals(expected, result)

    }

    @Test(expected = NoteNotFoundException::class)
    fun get_exception() {
        NoteService.add("First note", "text", 210421)
        NoteService.add("Second note", "text2", 301585)

        NoteService.get(arrayListOf(1, 3))
    }

    @Test
    fun getById_success() {
        NoteService.add("First note", "text", 210421)
        NoteService.add("Second note", "text2", 301585)

        val expected = Note(2, "Second note", "text2", 301585)
        val result = NoteService.getById(2)

        assertEquals(expected, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun getById_exception() {
        NoteService.add("First note", "text", 210421)
        NoteService.add("Second note", "text2", 301585)

        NoteService.getById(15)
    }

    @Test
    fun getComment_successDefaultSort() {
        NoteService.add("First note", "text", 210421)
        NoteService.add("Second note", "text2", 301585)
        NoteService.createComment(1,"first note comment1", 210421)
        NoteService.createComment(2,"first note comment2", 210422)
        NoteService.createComment(1,"first note comment3", 210420)
        NoteService.createComment(2,"first note comment4", 210427)

        val comment1 = Comment(1, 1, "first note comment1", 210421)
        val comment2 = Comment(3, 1,"first note comment3", 210420)

        val expected = mutableListOf(comment1, comment2)
        val result = NoteService.getComment(1)

        assertEquals(expected, result)

    }

    @Test
    fun getComment_successDescendingSort() {
        NoteService.add("First note", "text", 210421)
        NoteService.add("Second note", "text2", 301585)
        NoteService.createComment(1,"first note comment1", 210421)
        NoteService.createComment(2,"first note comment2", 210422)
        NoteService.createComment(1,"first note comment3", 210420)
        NoteService.createComment(2,"first note comment4", 210427)

        val comment1 = Comment(1, 1, "first note comment1", 210421)
        val comment2 = Comment(3, 1,"first note comment3", 210420)

        val expected = mutableListOf(comment2, comment1)
        val result = NoteService.getComment(1, 1)

        assertEquals(expected, result)

    }

    @Test(expected = NoteNotFoundException::class)
    fun getComment_exception() {
        NoteService.add("First note", "text", 210421)
        NoteService.add("Second note", "text2", 301585)
        NoteService.createComment(1,"first note comment1", 210421)
        NoteService.createComment(2,"first note comment2", 210422)
        NoteService.createComment(1,"first note comment3", 210420)
        NoteService.createComment(2,"first note comment4", 210427)

       NoteService.getComment(5)
    }

    @Test
    fun restoreComment_success() {
        NoteService.add("First note", "text", 210421)
        NoteService.createComment(1,"first note comment1", 210421)
        NoteService.createComment(1,"first note comment3", 210420)
        NoteService.deleteComment(1)

        assertTrue(NoteService.restoreComment(1))
    }

    @Test(expected = CommentNotFoundException::class)
    fun restoreComment_commentDoesntExist() {
        NoteService.add("First note", "text", 210421)
        NoteService.createComment(1,"first note comment1", 210421)
        NoteService.createComment(1,"first note comment3", 210420)

        NoteService.restoreComment(24)
    }

    @Test(expected = CommentNotFoundException::class)
    fun restoreComment_commentDoesntDeleted() {
        NoteService.add("First note", "text", 210421)
        NoteService.createComment(1,"first note comment1", 210421)
        NoteService.createComment(1,"first note comment3", 210420)

        NoteService.restoreComment(1)
    }








}
