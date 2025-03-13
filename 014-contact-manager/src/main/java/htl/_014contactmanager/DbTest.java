package htl._014contactmanager;

import htl._014contactmanager.database.ContactRepository;
import htl._014contactmanager.database.Database;
import htl._014contactmanager.model.contact;

import java.util.List;

public class DbTest {

    public static void main(String[] args) {
        Database database = Database.getInstance();
        ContactRepository contactRepository = new ContactRepository();

        contactRepository.addContact("Max Mustermann", "0664 1234567", "Musterstraße 1");
        contactRepository.addContact("Erika Mustermann", "0664 7654321", "Musterstraße 2");

        List<contact> contacts = contactRepository.getAllContacts();
    }
}
