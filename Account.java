
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Account {
    /* A class for holding a user's name, password, and all the notes they've created or imported in a session */
    String name;
    String password;
    ArrayList<Note> noteList = new ArrayList<Note>(); 


    // Class Constructor
    public Account(String selectedName, String selectedPassword){
        name = selectedName;
        password = selectedPassword;
    }


    public void displayAllNotes(){
        // Displays every single note currently loaded/imported
        System.out.println("You have the following notes stored from this session:\n");
        for (int i = 0; i < noteList.size(); i++){
            System.out.println("Note " + (i + 1) + " - Title: " + noteList.get(i).title);
        }
    }

    public void saveAllNotes(){
        // Loops through all notes in the list, saving them to their own files
        for (int i = 0; i < noteList.size(); i++){
            noteList.get(i).saveNote(name);
        }
    }

    public void createNewNote(String content,String title){
        // Creates a note object from a provided title and contents, adding it to the note list
        Note newNote = new Note(noteList.size(),content,title);
        noteList.add(newNote);

    }

    public void importNote(String title){
        /* Imports notes from a text file and adds them to the notelist*/
        // TODO: ensure that notes can only be imported by the author

        // Initialize password variable
        String importedContent = "";

        try {
            File noteFile = new File(name + title + ".txt");
            Scanner noteReader = new Scanner(noteFile);
            while (noteReader.hasNextLine()) {
                importedContent = noteReader.nextLine();
            }
        noteReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        createNewNote(importedContent,title);
    }

    public void displayNote(int noteId){
        // Prints a single note's contents
        System.out.println(noteList.get(noteId - 1).getNoteContents());
    }

    // Getters and Setters
    public String getAccountName() {
        return name;
    }
}
