import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
  /* A class that manages the program flow as a whole, directing control between methods */

  public static void setPassword(String account_name){
    /* Creates a password for an account in the event it hasn't been created yet. */
    try {
      // Write to the file named after the current user's username
      FileWriter myWriter = new FileWriter(account_name + ".txt");
      Scanner inputScanner = new Scanner(System.in);

      // Prompt for password and write to the user file
      System.out.println("Enter a new password: ");
      String password = inputScanner.nextLine();
      myWriter.write(password);
      myWriter.close();
      }
      catch (IOException e) {
        System.out.println("Unable to write to file.");
        e.printStackTrace();
      }
    }

    public static String getUsername(){
      /* Prompts the user for their username and returns it to where it's needed. */
      Scanner inputScanner = new Scanner(System.in);
      System.out.println("Enter a Username: ");
      String userName = inputScanner.nextLine();
      return userName;
    }

    public static String readPassword(String account_name){
      /* Retrieves the password of an account as a string from the account file */

      // Initialize password variable so it can be returned
      String password = "";

      try {
        // the account file is named after the username
        File accountFile = new File(account_name + ".txt");
        Scanner accountReader = new Scanner(accountFile);
        while (accountReader.hasNextLine()) {
          // password is stored inside the account file.
          password = accountReader.nextLine();
        }
        accountReader.close();
      } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
      return password;
    }

    public static Boolean isAuthorized(String username){
      /* Takes a username, prompts the user for its password, and repeats until the correct password is presented */
      // Initialize the input scanner
      Scanner inputScanner = new Scanner(System.in);
      String password = readPassword(username);
      while (true) {
        // Prompt for password and store to a string with scanner
        System.out.println("Enter " + username + "'s password: ");
        String passwordAttempt = inputScanner.nextLine();

        // The equals function makes sure the characters are the same, the length comparison ensures there's nothing extra.
        if (password.equals(passwordAttempt) && password.length() == passwordAttempt.length()) {
          return true;
        }
        else {
          System.out.println("Incorrect password!");
        }
      }

    }

    public static void getAccountFile(String currentName){
      /* Creates a file based on the selected account name if it does not exist */

      try {
        // File's name is the username
        File accountFile = new File(currentName + ".txt");
        
        // if a file is created freshly, choose a password
        if (accountFile.createNewFile()) {
          System.out.println("File created: " + accountFile.getName());
          setPassword(currentName);
        // If the file already exists, authenticate the user via password and welcome them into the system
        } else {
          System.out.println("User already exists.");
          if (isAuthorized(currentName)){
            System.out.println("Welcome, " + currentName + "!");
          }
        }
      } catch (IOException e) {
        System.out.println("Unable to create the file.");
        e.printStackTrace();
      }
    }

    public static void readNote(Account currentAccount){
      /* prints the selected note to the console for viewing */

      // Initialize inputscanner, prompt for desired note
      Scanner inputScanner = new Scanner(System.in);
      System.out.println("Which note do you want to read? (enter ID)");
      
      // After a selection is made, display the note
      int selectedId = inputScanner.nextInt();
      currentAccount.displayNote(selectedId);
    }
    public static void main(String[] aargs) {
        /* handles opening of the account and log-in options until the user exits */

        // Acquire user's desired account name
        String chosenName = getUsername();

        // Create a new or open an existing file
        getAccountFile(chosenName);

        // Initialize an account object using the username and reading it's password from the file
        Account currentAccount = new Account(chosenName,readPassword(chosenName));

        // Until the user decides to quit, they will remain logged in
        boolean loggedIn = true;
        while (loggedIn){

            // Initialize the input scanner
            Scanner inputScanner = new Scanner(System.in);

            // Prompt user for input, presenting numbered options
            System.out.println("\nWhat would you like to do?");
            System.out.println("1 - Read a note, 2 - Display all notes, 3 - Write a new note, 4 - Import a note 5 - Save and quit");
            int choice = inputScanner.nextInt();
            switch (choice) {
              case 1:
                readNote(currentAccount);
                break;
              case 2:
                currentAccount.displayAllNotes();
                break;
              case 3:
                System.out.println("Enter a note title: ");
                String newTitle = inputScanner.nextLine();
                newTitle = inputScanner.nextLine();
                System.out.println("Enter the note contents: ");
                String content = inputScanner.nextLine();
                currentAccount.createNewNote(content, newTitle);
                break;
              case 4:
                System.out.println("Enter the name of the note you wish to import:");
                String title = inputScanner.nextLine();
                title = inputScanner.nextLine();
                currentAccount.importNote(title);
                break;
              case 5:
                currentAccount.saveAllNotes();
                loggedIn = false;
                break;
            }
        }
    }
}
