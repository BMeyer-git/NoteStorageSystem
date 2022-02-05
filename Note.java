
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Note {
    /* A simple class that represents a note among many, belonging to an account */
    int id;
    String contents;
    String title;

    // Class Constructor
    public Note(int newNumber,String newContents,String newTitle){
        id = newNumber;
        title = newTitle;
        contents = newContents;
    }

    public void saveContents(String name){
        /* Saves the note contents to its respective file. */
        try {
            FileWriter myWriter = new FileWriter(name + title + ".txt");
            myWriter.write(contents);
            myWriter.close();
        }
        catch (IOException e) {
            System.out.println("Unable to write to file.");
            e.printStackTrace();
        }
    }

    public void saveNote(String name){
        // Saves a note to it's own file, labeled by author and title
        File noteFile = new File(name + title + ".txt");
        System.out.println("note saved: " + noteFile.getName());
        saveContents(name);
    }

    // Getters and Setters
    public String getNoteContents() {
        return contents;
    }

    public void replaceContents(String newContents){
        contents = newContents;
    }

    public int getId(){
        return id;
    }

    public void setId(int newId){
        id = newId;
    }
}
