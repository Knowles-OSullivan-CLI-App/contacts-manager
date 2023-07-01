import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
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
            String addPhone = String.valueOf(scanner.nextInt());
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
            if (name.equalsIgnoreCase(searchName)) {
                System.out.println("Found contact: " + contact);
            }
        }

    }



    public static void main(String[] args) throws IOException {

//        List<src.Contact> contacts = showContacts();
//        createDataFileIfNotExists();
//        defaultContact();
//        showContacts();
        searchContacts();





    }
}