package com.kodex.rednavigation.database.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kodex.rednavigation.DatabaseRepository
import com.kodex.rednavigation.model.Note
import com.kodex.rednavigation.utils.Constants
import com.kodex.rednavigation.utils.Constants.Keys.ACTIVITY
import com.kodex.rednavigation.utils.Constants.Keys.SUBTITLE
import com.kodex.rednavigation.utils.Constants.Keys.TITLE
import com.kodex.rednavigation.utils.FIREBASE_ID
import com.kodex.rednavigation.utils.LOGIN
import com.kodex.rednavigation.utils.PASSWORD


class AppFirebaseRepository : DatabaseRepository {

    private val auth = FirebaseAuth.getInstance()
    //val database = Firebase.database("https://chatfirebase-e00a0-default-rtdb.europe-west1.firebasedatabase.app")

    private val database = Firebase.database.reference
     .child(auth.currentUser?.uid.toString())

    override val readAll: LiveData<List<Note>> = AllNotesLiveData()

    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        val noteId = database.push().key.toString()
        val mapNotes = hashMapOf<String, Any>()

        mapNotes[FIREBASE_ID] = noteId
        mapNotes[Constants.Keys.TITLE] = note.title
        mapNotes[Constants.Keys.SUBTITLE] = note.subtitle

        database.child(noteId)
            .updateChildren(mapNotes)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { Log.d("checkData", "Failed to update note") }
    }
    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        val noteId = note.firebaseId
        val mapNotes = hashMapOf<String, Any>()

        mapNotes[FIREBASE_ID] = noteId
        mapNotes[Constants.Keys.TITLE] = note.title
        mapNotes[Constants.Keys.SUBTITLE] = note.subtitle

        database.child(noteId)
            .updateChildren(mapNotes)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { Log.d("checkData", "Failed to update note") }
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        database.child(note.firebaseId)
            .removeValue()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { Log.d("checkData", "Failed to delete note") }
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun connectToDatabase(onSuccess: () -> Unit, onFile: (String) -> Int) {
        auth.signInWithEmailAndPassword(LOGIN, PASSWORD)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener{
                auth.createUserWithEmailAndPassword(LOGIN, PASSWORD)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener{onFile(it.message.toString())}
            }
    }
}
