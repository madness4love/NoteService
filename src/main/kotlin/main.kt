fun main() {
    NoteService.add("First note", "text", 210421)
    NoteService.add("Second note", "text2", 301585)
//    NoteService.add("Third note", "text3", 65, 210420)
//    NoteService.add("Forth note", "text4", 71, 123456)
//
//    //NoteService.printNotes()
//
//    NoteService.createComment(1,"first note comment1", 210421)
    NoteService.createComment(1,"first note comment2", 210422)
//
//
    //NoteService.createComment(234,"second note comment", 210420)
//    NoteService.createComment(2,38, "second note comment2", 210429)
//
//    NoteService.createComment(1,38, "first note comment3", 210420)
//    NoteService.createComment(1,38, "first note comment4", 210427)
//
//    //NoteService.createComment(3,38, "note doesn't exist")
//
    println(NoteService.printNotesComments())
//    println()
//
 //   NoteService.delete(345)
//
println()
////    NoteService.printNotesComments()
////
 NoteService.deleteComment(45)

    println(NoteService.printNotesComments())
//
//    println()
//    NoteService.printNotesComments()
////    println()
////    NoteService.printNotesComments()
////    println()
    //NoteService.edit(45,"new first note", "text")
//
////    NoteService.editComment(1, 38, "edit first note comment")
////    NoteService.printNotesComments()
////    println()
////
  //  NoteService.get(arrayListOf(1, 6))
////    NoteService.get(arrayListOf(1, 3), 1)
//
//    //NoteService.getComment(1, 1)
//
//    NoteService.restoreComment(1)
//    println()
//    NoteService.printNotesComments()
}