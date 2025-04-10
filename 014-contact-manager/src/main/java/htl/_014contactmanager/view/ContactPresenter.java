package htl._014contactmanager.view;


import htl._014contactmanager.database.ContactRepository;
import htl._014contactmanager.database.CountryRepository;
import htl._014contactmanager.database.LocationRepository;
import htl._014contactmanager.model.ContactType;
import htl._014contactmanager.model.Country;
import htl._014contactmanager.model.Location;
import htl._014contactmanager.model.contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContactPresenter {
    private final ContactView view;

    private final ContactRepository contactRepository;
    private final LocationRepository locationRepository;
    private final CountryRepository countryRepository;
    private final ObservableList<contact> contacts = FXCollections.observableArrayList();
    private final ObservableList<Location> locations = FXCollections.observableArrayList();
    private final ObservableList<Country> countries = FXCollections.observableArrayList();

    private ContactPresenter(ContactView view) {
        this.view = view;
        this.contactRepository = new ContactRepository();
        this.locationRepository = new LocationRepository();
        this.countryRepository = new CountryRepository();
        
        // Configure fields
        view.setAllFieldsEditable(false);
        view.getBtnsave().setDisable(true);
        view.getBtnEdit().setDisable(false);

        loadCountries();
        loadLocations();
        bindViewToModel();
        attachEvents();
        addListeners();
        init();
    }

    private void loadCountries() {
        // Load countries from database
        countries.clear();
        countries.addAll(countryRepository.getAllCountries());
        view.getCmbCountry().setItems(countries);
    }

    private void loadLocations() {
        // Load locations from database
        locations.clear();
        locations.addAll(locationRepository.getAllLocations());
        view.getCmbLocation().setItems(locations);
    }

    private void bindViewToModel() {
        // Note: TreeView is not directly bound to the contacts list
        // It's built in buildTreeView method
    }

    private void attachEvents() {
        view.getBtnSearch().setOnAction(event -> searchContacts());
        view.getBtnAdd().setOnAction(event -> newContact());
        view.getBtnEdit().setOnAction(event -> editContact());
        view.getBtnsave().setOnAction(event -> saveContact());
        view.getBtnDelete().setOnAction(event -> deleteContact());
        
        // Add listener to country combo box to filter locations
        view.getCmbCountry().setOnAction(event -> filterLocationsByCountry());
    }
    
    /**
     * Filter locations in the location dropdown based on selected country
     */
    private void filterLocationsByCountry() {
        Country selectedCountry = view.getCmbCountry().getValue();
        
        if (selectedCountry != null) {
            // Filter locations by the selected country
            List<Location> filteredLocations = locations.stream()
                    .filter(location -> location.getCountry() != null && 
                                       location.getCountry().getId() == selectedCountry.getId())
                    .collect(Collectors.toList());
            
            view.getCmbLocation().setItems(FXCollections.observableArrayList(filteredLocations));
        } else {
            // If no country selected, show all locations
            view.getCmbLocation().setItems(locations);
        }
    }
    
    /**
     * Builds a TreeView of contacts grouped by contact type
     */
    private void buildTreeView() {
        TreeItem<Object> root = new TreeItem<>(null); // Dummy root
        
        // Group contacts by type
        Map<ContactType, List<contact>> contactsByType = contacts.stream()
                .collect(Collectors.groupingBy(contact::getType));
                
        // Create a node for each type
        contactsByType.forEach((type, contactsOfType) -> {
            TreeItem<Object> typeNode = new TreeItem<>(type);
            
            // Add contacts to their type node
            contactsOfType.forEach(contact -> {
                TreeItem<Object> contactNode = new TreeItem<>(contact);
                typeNode.getChildren().add(contactNode);
            });
            
            typeNode.setExpanded(true);
            root.getChildren().add(typeNode);
        });
        
        // Set root node and hide it
        view.getTvContacts().setRoot(root);
        view.getTvContacts().setShowRoot(false);
    }

    private void newContact() {
        clearFields();
        view.setAllFieldsEditable(true);
        
        // Set default values
        view.getCmbContactType().setValue(ContactType.NONE);
        
        // Disable appropriate buttons during edit mode
        view.setBtnAddDisabled(false);
        view.setBtnEditDisabled(true);
        view.setBtnDeleteDisabled(true);
        view.setBtnsaveDisabled(false);
    }

    private void editContact() {
        TreeItem<Object> selectedItem = view.getTvContacts().getSelectionModel().getSelectedItem();
        if (selectedItem != null && selectedItem.getValue() instanceof contact) {
            view.setAllFieldsEditable(true);

            // Disable appropriate buttons during edit mode
            view.setBtnAddDisabled(false);
            view.setBtnEditDisabled(true);
            view.setBtnDeleteDisabled(true);

            // Enable save button
            view.setBtnsaveDisabled(false);
            
            // Get the contact's location and set country
            contact selectedContact = (contact) selectedItem.getValue();
            if (selectedContact.getLocation() != null && selectedContact.getLocation().getCountry() != null) {
                view.getCmbCountry().setValue(selectedContact.getLocation().getCountry());
                filterLocationsByCountry(); // Update locations based on country
            }
        }
    }

    private void saveContact() {
        TreeItem<Object> selectedItem = view.getTvContacts().getSelectionModel().getSelectedItem();
        
        // Check if we are editing an existing contact
        if (selectedItem != null && selectedItem.getValue() instanceof contact) {
            contact selectedContact = (contact) selectedItem.getValue();
            updateContactFromForm(selectedContact);
            contactRepository.updateContact(selectedContact);
        } else {
            // Create a new contact
            String name = view.getTfName().getText();
            String phone = view.getTfTelephone().getText();
            String address = view.getTfEmail().getText();
            ContactType type = view.getCmbContactType().getValue();
            Location location = view.getCmbLocation().getValue();

            if (!name.isEmpty() && !phone.isEmpty() && !address.isEmpty()) {
                contactRepository.addContact(name, phone, address, type, location);
            }
        }
        
        // Refresh contacts from database and rebuild tree
        reloadContactList();
        clearFields();

        // Reset UI state
        resetUIState();
    }
    
    // Helper method to update a contact object from the form
    private void updateContactFromForm(contact contact) {
        contact.setName(view.getTfName().getText());
        contact.setNumber(view.getTfTelephone().getText());
        contact.setAdress(view.getTfEmail().getText());
        contact.setType(view.getCmbContactType().getValue());
        contact.setLocation(view.getCmbLocation().getValue());
    }

    private void deleteContact() {
        TreeItem<Object> selectedItem = view.getTvContacts().getSelectionModel().getSelectedItem();
        if (selectedItem != null && selectedItem.getValue() instanceof contact) {
            contact selectedContact = (contact) selectedItem.getValue();
            contactRepository.deleteContact(selectedContact.getId());
            contacts.remove(selectedContact);
            
            // Rebuild the tree after deletion
            buildTreeView();
            clearFields();

            // Reset UI state
            resetUIState();
        }
    }

    // Helper method to clear fields
    private void clearFields() {
        view.getTfId().clear();
        view.getTfName().clear();
        view.getTfTelephone().clear();
        view.getTfEmail().clear();
        view.getCmbContactType().setValue(null);
        view.getCmbCountry().setValue(null);
        view.getCmbLocation().setValue(null);
    }

    // Helper method to reset the UI state
    private void resetUIState() {
        // Disable editing for all fields
        view.setAllFieldsEditable(false);

        // Enable buttons
        view.setBtnAddDisabled(false);
        view.setBtnEditDisabled(false);
        view.setBtnDeleteDisabled(false);

        // Disable save button
        view.setBtnsaveDisabled(true);
    }

    private void searchContacts() {
        String search = view.getTfSearchField().getText().toLowerCase();
        if(!search.isEmpty()){
            // Search only through contact nodes, not group nodes
            for (TreeItem<Object> typeNode : ((TreeItem<Object>)view.getTvContacts().getRoot()).getChildren()) {
                for (TreeItem<Object> contactItem : typeNode.getChildren()) {
                    if (contactItem.getValue() instanceof contact) {
                        contact c = (contact) contactItem.getValue();
                        if (c.getName().toLowerCase().contains(search)) {
                            // Select and scroll to the matching contact
                            view.getTvContacts().getSelectionModel().select(contactItem);
                            view.getTvContacts().scrollTo(view.getTvContacts().getSelectionModel().getSelectedIndex());
                            return;
                        }
                    }
                }
            }
        }
    }

    private void addListeners() {
        // React to selections in the tree
        view.getTvContacts().getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
            if (newValue != null && newValue.getValue() instanceof contact) {
                contact selectedContact = (contact) newValue.getValue();
                view.getTfId().setText(String.valueOf(selectedContact.getId()));
                view.getTfName().setText(selectedContact.getName());
                view.getTfTelephone().setText(selectedContact.getNumber());
                view.getTfEmail().setText(selectedContact.getAdress());
                view.getCmbContactType().setValue(selectedContact.getType());
                
                // Set country and location
                if (selectedContact.getLocation() != null) {
                    view.getCmbLocation().setValue(selectedContact.getLocation());
                    
                    if (selectedContact.getLocation().getCountry() != null) {
                        view.getCmbCountry().setValue(selectedContact.getLocation().getCountry());
                        filterLocationsByCountry(); // Update locations based on country
                    }
                } else {
                    view.getCmbCountry().setValue(null);
                    view.getCmbLocation().setValue(null);
                }
            }
        });
    }

    private void init() {
        // Load contacts from db
        reloadContactList();
    }
    
    private void reloadContactList() {
        contacts.clear();
        List<contact> allContacts = contactRepository.getAllContacts();
        if (allContacts != null) {
            contacts.addAll(allContacts);
        }
        
        // Build the TreeView with the updated contacts
        buildTreeView();
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