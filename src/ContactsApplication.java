import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ContactsApplication {

    private static void createDataFileIfNotExists() throws IOException {
        String directory = "data";
        String filename = "Contacts.txt";

        Path dataDirectory = Paths.get(directory);
        Path dataFile = Paths.get(directory, filename);

        if (Files.notExists(dataDirectory)) {
            Files.createDirectories(dataDirectory);
        }

        if (!Files.exists(dataFile)) {
            Files.createFile(dataFile);

        }
    }

    //    private static List<Contact> loadContacts() {
//       List<Contact> contacts = new ArrayList<>();
//        try (Scanner scanner = new Scanner(new File(filename))) {
//            while (scanner.hasNextLine()) {
//                String line = scanner.nextLine();
//                String[] parts = line.split("\\|");
//               String name = parts[0].trim();
//                String phoneNumber = parts[1].trim();
//                Contact contact = new Contact(name, phoneNumber);
//                contacts.add(contact);
//            }
//      } catch (FileNotFoundException e) {
//          e.printStackTrace();
//       }
//
//       return contacts;
//   }
    public static void defaultContact() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String userName;
//        String userPhone;
        System.out.println("Add New Contact, Yes or No");
        userName = scanner.nextLine();
        if (userName.equalsIgnoreCase("Yes")) {
            System.out.println("Enter contact name");
            String addContact = scanner.nextLine();
            System.out.println("Enter contact phone number");
            String addPhone = scanner.nextLine().trim();
            System.out.println("Contact has been added");

            List<String> contactList = Arrays.asList(addContact + " | " + addPhone);
            Path filename = Paths.get("data", "Contacts.txt");
            Files.write(filename, contactList, StandardOpenOption.APPEND);
            System.out.println(addContact + " " + addPhone);
        }
    }

    public static List<Contact> showContacts() throws IOException {
        Path contactsPath = Paths.get("data", "contacts.txt");
        List<String> contactList = Files.readAllLines(contactsPath);
        System.out.println("Name | Phone Number");
        System.out.println("-------------------");
        for (int i = 0; i < contactList.size(); i += 1) {
            System.out.println((i + 1) + ": " + contactList.get(i));
        }
        return null;
    }

    public static void searchContacts() throws IOException {
        Path contactsPath = Paths.get("data", "contacts.txt");
        List<String> contactList = Files.readAllLines(contactsPath);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a name to search: ");
        String searchName = scanner.nextLine().trim();

        for (String contact : contactList) {
            String[] contactInfo = contact.split("\\|");
            String name = contactInfo[0].trim();
            if (name.toLowerCase().contains(searchName.toLowerCase())) {
                System.out.println("Found contact: " + contact);
            }
        }

    }

    // https://www.youtube.com/watch?v=ij07fW5q4oo
    // **File objs are older and platform dependent.
    public static void deleteContact() throws IOException {
        Path contactsPath = Paths.get("data", "contacts.txt");
        List<String> contactList = Files.readAllLines(contactsPath);

        // Creating a temp file
        String tempFile = "temp.txt";
        // creates a file obj representing original contacts file
        File oldFile = new File(contactsPath.toUri());
        //creates a file obj representing temp file
        File newFile = new File(tempFile);

        //creates a FileWriter object to write to the temporary file, with true indicating that new content should be appended to the file.
        FileWriter fw = new FileWriter(tempFile, true);
        // creates a buffered writer obj to improve write performance
        BufferedWriter bw = new BufferedWriter(fw);
        // creates printWriter obj for writing formatted text to BW
        PrintWriter pw = new PrintWriter(bw);

        // creates a fileReader obj to read from contact list
        FileReader fr = new FileReader(String.valueOf(contactsPath));
        // creates a bufferedReader obj to improve read performance
        BufferedReader br = new BufferedReader(fr);

        // declares a string var to store current line being read
        String currentLine;
        // declares a var to keep track of line number
        int line = 0;

        //while loop condition reads each line of file until it reches the end (which would return null)
        while ((currentLine = br.readLine()) != null) {
            line++;
            // declares var to hold deleted line
            int deletedLine = 0;
            // checks if current line being read is different than one being deleted and sif it is then it stores it into temp file
            if (line != deletedLine) {
                pw.println(currentLine);
            }
        }
        // closing all readers, writers, and buffers
        pw.flush();
        pw.close();
        fr.close();
        br.close();
        bw.close();
        fw.close();

        // deletes the original contact file
        oldFile.delete();
        //creates a File obj representing the original contacts path
        File dump = new File(contactsPath.toUri());
        //renames temp file to the original contacts file name
        newFile.renameTo(dump);

        Scanner scanner = new Scanner(System.in);
        System.out.println("You must enter the full name in order to delete. Please provide the name:");
        String searchNameToDelete = scanner.nextLine().trim();

        // creates new filewriter obj to overwrite the original contacts file name
        FileWriter fw2 = new FileWriter(String.valueOf(contactsPath), false);
        //creates new BW obj
        BufferedWriter bw2 = new BufferedWriter(fw2);
        //creates new PW obj
        PrintWriter pw2 = new PrintWriter(bw2);

        for (String contact : contactList) {
            String[] contactInfo = contact.split("\\|");
            String name = contactInfo[0].trim();
            //checks if the current contacts name is not equal to the name provided by user, if it is true then it writes contact to the new file
            if (!name.equalsIgnoreCase(searchNameToDelete)) {
                pw2.println(contact);
            }
        }
        System.out.println(searchNameToDelete + " has been deleted");

        pw2.flush();
        pw2.close();
        bw2.close();
        fw2.close();
    }


    public static void main(String[] args) throws IOException {
//        createDataFileIfNotExists();
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            System.out.println("\n1. View contacts.\n2. Add a search contact.\n3. Search contact by name.\n4. Delete an existing contact.\n5. Exit\n");

            int userInterfaceInput = scanner.nextInt();


            switch (userInterfaceInput) {
                case 1 -> showContacts();
                case 2 -> defaultContact();
                case 3 -> searchContacts();
                case 4 -> deleteContact();
                case 5 -> {
                    System.out.println("Exiting program...");
                    flag = false;
                    System.exit(0);
                }
                default -> System.out.println("Enter a valid number");
            }
        }
    }
}

