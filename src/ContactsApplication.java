
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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

    public static void addContact() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String userName;
        System.out.println("Add New Contact, Yes or No");
        userName = scanner.nextLine();
        if (userName.equalsIgnoreCase("Yes")) {
            System.out.println("Enter contact name");
            String addContact = scanner.nextLine();
            String addPhone = "";
            do {
                System.out.println("Enter contact phone number");
                addPhone = scanner.nextLine().trim();
                if (addPhone.length() != 10) {
                    System.out.println("Phone number is incorrect length try again.");
                }

            } while (addPhone.length() != 10);
            //            https://www.geeksforgeeks.org/substring-in-java/
            addPhone = addPhone.substring(0, 3) + "-" +
                    addPhone.substring(3, 6) + "-" +
                    addPhone.substring(6);

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
        Scanner scanner = new Scanner(System.in);
        System.out.println("You must enter the full name in order to delete. Please provide the name:");
        String searchNameToDelete = scanner.nextLine().trim();

        // creates new file-writer obj to overwrite the original contacts file name
        FileWriter fw2 = new FileWriter(String.valueOf(contactsPath), false);
        //creates new BW obj
        BufferedWriter bw2 = new BufferedWriter(fw2);
        //creates new PW obj
        PrintWriter pw2 = new PrintWriter(bw2);

        String name = null;
        for (String contact : contactList) {
            String[] contactInfo = contact.split("\\|");
            name = contactInfo[0].trim();
            //checks if the current contacts name is not equal to the name provided by user, if it is true then it writes contact to the new file
            if (!name.equalsIgnoreCase(searchNameToDelete)) {
                pw2.println(contact);

            }
            if (searchNameToDelete != name) {
                System.out.println("Please try again.");
                break;

            } else {
                System.out.println(searchNameToDelete + " has been deleted");
            }
        }


        pw2.flush();
        pw2.close();
        bw2.close();
        fw2.close();
    }

    public static void exitProgram(Boolean flag) {
        System.out.println("Exiting program...");
        flag = false;
        System.exit(0);
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            System.out.println("\n1. View contacts.\n2. Add a search contact.\n3. Search contact by name.\n4. Delete an existing contact.\n5. Exit\n");

            int userInterfaceInput = scanner.nextInt();


            switch (userInterfaceInput) {
                case 1 -> showContacts();
                case 2 -> addContact();
                case 3 -> searchContacts();
                case 4 -> deleteContact();
                case 5 -> exitProgram(flag);
                default -> System.out.println("Enter a valid number");
            }
        }
    }
}

