package htl._014contactmanager.view;


import htl._014contactmanager.database.ContactRepository;
import htl._014contactmanager.model.contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ContactPresenter {
    private final ContactView view;

    private final ContactRepository contactRepository;
    private  final ObservableList<contact> contacts = FXCollections.observableArrayList();

    private ContactPresenter(ContactView view) {
        this.view = view;
        this.contactRepository = new ContactRepository();
        view.getTfEmail().setEditable(false);
        view.getTfTelephone().setEditable(false);
        view.getTfName().setEditable(false);
        view.getTfId().setEditable(false);

        view.getBtnsave().setDisable(true);
        view.getBtnEdit().setDisable(false);


        bindViewToModel();
        attachEvents();
        addListeners();
        init();

    }

    private void bindViewToModel() {
        view.getListView().setItems(contacts);
        // weitere Bindungen für Detailansicht

    }

    private void attachEvents() {
        view.getBtnSearch().setOnAction(event -> searchContact());
        view.getBtnAdd().setOnAction(event -> addContact());
        view.getBtnEdit().setOnAction(event -> editContact());
        view.getBtnsave().setOnAction(event -> saveContact());
        view.getBtnDelete().setOnAction(event -> deleteContact());

    }

    private void editContact() {
        contact selectedContact = view.getListView().getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            view.setAllFieldsEditable(true);

            // Disable appropriate buttons during edit mode
            view.setBtnAddDisabled(false);
            view.setBtnEditDisabled(true);
            view.setBtnDeleteDisabled(true);

            // Enable save button
            view.setBtnsaveDisabled(false);

            // Add change listeners to text fields to detect modifications
            addChangeListeners();
        }
        if(selectedContact == null){
            view.setAllFieldsEditable(true);

            // Enable add button and call addContact method

            view.setBtnAddDisabled(false);
            view.setBtnEditDisabled(true);
            view.setBtnDeleteDisabled(true);
            view.setBtnsaveDisabled(false);

        }
    }

    private void saveContact() {
        contact selectedContact = view.getListView().getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            selectedContact.setName(view.getTfName().getText());
            selectedContact.setNumber(view.getTfTelephone().getText());
            selectedContact.setAdress(view.getTfEmail().getText());
            contactRepository.updateContact(selectedContact);
            view.getListView().refresh();

            // Reset UI state after saving
            resetUIState();
        }
    }

    private void addContact() {
        String name = view.getTfName().getText();
        String phone = view.getTfTelephone().getText();
        String address = view.getTfEmail().getText();

        if (!name.isEmpty() && !phone.isEmpty() && !address.isEmpty()) {
            contactRepository.addContact(name, phone, address);

            // Refresh contacts from database
            contacts.clear();
            contacts.addAll(contactRepository.getAllContacts());
            clearFields();

            // Reset UI state after adding
            resetUIState();
        }
    }

    private void deleteContact() {
        contact selectedContact = view.getListView().getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            contactRepository.deleteContact(selectedContact.getId());
            contacts.remove(selectedContact);
            clearFields();

            // Reset UI state after deleting
            resetUIState();
        }
    }

    // Helper method to clear fields and reset UI state
    private void clearFields() {
        view.getTfId().clear();
        view.getTfName().clear();
        view.getTfTelephone().clear();
        view.getTfEmail().clear();
    }

    // Helper method to reset the UI state
    private void resetUIState() {
        // Disable editing for all fields
        view.setAllFieldsEditable(false);

        // Enable add, edit, delete buttons
        view.setBtnAddDisabled(false);
        view.setBtnEditDisabled(false);
        view.setBtnDeleteDisabled(false);

        // Disable save button since nothing to save
        view.setBtnsaveDisabled(true);

        // Remove any change listeners
        removeChangeListeners();
    }

    // Add change listeners to detect modifications in edit mode
    private void addChangeListeners() {
        // Initially disable save button until changes are made
        view.setBtnsaveDisabled(true);

        view.getTfName().textProperty().addListener((observable, oldValue, newValue) -> {
            view.setBtnsaveDisabled(false);
        });

        view.getTfTelephone().textProperty().addListener((observable, oldValue, newValue) -> {
            view.setBtnsaveDisabled(false);
        });

        view.getTfEmail().textProperty().addListener((observable, oldValue, newValue) -> {
            view.setBtnsaveDisabled(false);
        });
    }

    // Remove change listeners
    private void removeChangeListeners() {
        // Remove listeners by adding null listeners (simplified approach)
        view.getTfName().textProperty().addListener((observable, oldValue, newValue) -> {});
        view.getTfTelephone().textProperty().addListener((observable, oldValue, newValue) -> {});
        view.getTfEmail().textProperty().addListener((observable, oldValue, newValue) -> {});
    }



    private void searchContact() {
        String search = view.getTfSearchField().getText().toLowerCase();
        if(!search.isEmpty()){
            contacts.stream()
                    .filter(contact -> contact.getName().toLowerCase().contains(search))
                    .limit(1)
                    .forEach(contact -> {
                        view.getListView().getSelectionModel().select(contact);
                        view.getListView().scrollTo(contact);
                    });
        }
    }

    private void addListeners() {
        // regaiereun auf eine Slektion ind der liste
        view.getListView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                view.getTfId().setText(String.valueOf(newValue.getId()));
                view.getTfName().setText(newValue.getName());
                view.getTfTelephone().setText(newValue.getNumber());
                view.getTfEmail().setText(newValue.getAdress());
            }
        });
    }

    private void init() {
     // load contacts from db
        contacts.clear();
        contacts.addAll(contactRepository.getAllContacts());
        view.getListView().setItems(contacts);
    }

    public static void show(Stage stage){
        ContactView view = new ContactView();
        ContactPresenter presenter = new ContactPresenter(view);

        Scene scene = new Scene(view.getRoot());
        stage.setTitle("Contact Manager");
        stage.setScene(scene);
        stage.show();
    }
}