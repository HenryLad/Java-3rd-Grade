package htl._014contactmanager.view;

import htl._014contactmanager.model.ContactType;
import htl._014contactmanager.model.Country;
import htl._014contactmanager.model.Location;
import htl._014contactmanager.model.contact;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class ContactView {

    private final VBox root = new VBox();

    private final HBox hboxSearch = new HBox();
    private final TextField tfSearchField = new TextField();
    private final Button btnSearch = new Button("Search");
    private final HBox HboxEdit = new HBox();

    private final HBox hBoxId = new HBox();
    private final Label lblId = new Label("ID");
    private final TextField tfId = new TextField();

    private final HBox hBoxName = new HBox();
    private final Label lblName = new Label("Name");
    private final TextField tfName = new TextField();

    private final HBox hBoxTelephone = new HBox();
    private final Label lblTelephone = new Label("Telefon");
    private final TextField tfTelephone = new TextField();

    private final HBox hBoxEmail = new HBox();
    private final Label lblEmail = new Label("Adresse");
    private final TextField tfEmail = new TextField();

    // Contact type ComboBox
    private final HBox hBoxType = new HBox();
    private final Label lblType = new Label("Typ");
    private final ComboBox<ContactType> cmbContactType = new ComboBox<>();

    // Location ComboBox
    private final HBox hBoxLocation = new HBox();
    private final Label lblLocation = new Label("Ort");
    private final ComboBox<Location> cmbLocation = new ComboBox<>();
    
    // Country ComboBox
    private final HBox hBoxCountry = new HBox();
    private final Label lblCountry = new Label("Land");
    private final ComboBox<Country> cmbCountry = new ComboBox<>();

    private final Button btnAdd = new Button("Add");
    private final Button btnEdit = new Button("Edit");
    private final Button btnsave = new Button("Save");
    private final Button btnDelete = new Button("Delete");

    public Button getBtnAdd() {
        return btnAdd;
    }

    public Button getBtnEdit() {
        return btnEdit;
    }

    public Button getBtnsave() {
        return btnsave;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    // Replace ListView with TreeView
    private final TreeView<Object> tvContacts = new TreeView<>();

    public ContactView() {
        init();
    }

    private void init(){
        // Root
        root.setSpacing(10);
        root.setPadding(new Insets(20));

        // Search
        hboxSearch.setSpacing(10);
        hboxSearch.setPadding(new Insets(0,0, 10 , 10));
        hboxSearch.getChildren().addAll(tfSearchField, btnSearch);

        // Contact TreeView
        tvContacts.setPrefHeight(200);

        hBoxId.setSpacing(10);
        hBoxId.setPadding(new Insets(0, 0, 10, 10));
        lblId.setPrefWidth(50);
        tfId.setEditable(false);
        tfId.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
        hBoxId.getChildren().addAll(lblId, tfId);

        hBoxName.setSpacing(10);
        hBoxName.setPadding(new Insets(0, 0, 10, 10));
        lblName.setPrefWidth(50);
        hBoxName.getChildren().addAll(lblName, tfName);

        hBoxTelephone.setSpacing(10);
        hBoxTelephone.setPadding(new Insets(0, 0, 10, 10));
        lblTelephone.setPrefWidth(50);
        hBoxTelephone.getChildren().addAll(lblTelephone, tfTelephone);

        hBoxEmail.setSpacing(10);
        hBoxEmail.setPadding(new Insets(0, 0, 10, 10));
        lblEmail.setPrefWidth(50);
        hBoxEmail.getChildren().addAll(lblEmail, tfEmail);

        // Contact Type
        hBoxType.setSpacing(10);
        hBoxType.setPadding(new Insets(0, 0, 10, 10));
        lblType.setPrefWidth(50);
        cmbContactType.getItems().addAll(ContactType.values());
        hBoxType.getChildren().addAll(lblType, cmbContactType);
        
        // Country
        hBoxCountry.setSpacing(10);
        hBoxCountry.setPadding(new Insets(0, 0, 10, 10));
        lblCountry.setPrefWidth(50);
        hBoxCountry.getChildren().addAll(lblCountry, cmbCountry);
        
        // Location
        hBoxLocation.setSpacing(10);
        hBoxLocation.setPadding(new Insets(0, 0, 10, 10));
        lblLocation.setPrefWidth(50);
        hBoxLocation.getChildren().addAll(lblLocation, cmbLocation);

        HboxEdit.setSpacing(10);
        HboxEdit.setPadding(new Insets(0, 0, 10, 10));
        HboxEdit.getChildren().addAll(btnAdd, btnEdit, btnsave, btnDelete);

        // Generate View
        root.getChildren().addAll(
            hboxSearch, 
            tvContacts, 
            hBoxId, 
            hBoxName, 
            hBoxTelephone, 
            hBoxEmail, 
            hBoxType,
            hBoxCountry,
            hBoxLocation,
            HboxEdit
        );
    }

    public TextField getTfId() {
        return tfId;
    }

    public TextField getTfName() {
        return tfName;
    }

    public TextField getTfTelephone() {
        return tfTelephone;
    }

    public TextField getTfEmail() {
        return tfEmail;
    }
    
    public ComboBox<ContactType> getCmbContactType() {
        return cmbContactType;
    }
    
    public ComboBox<Country> getCmbCountry() {
        return cmbCountry;
    }
    
    public ComboBox<Location> getCmbLocation() {
        return cmbLocation;
    }

    public void setTfNameEditable(boolean editable) {
        tfName.setEditable(editable);
        updateTextFieldBackground(tfName, editable);
    }

    public void setTfTelephoneEditable(boolean editable) {
        tfTelephone.setEditable(editable);
        updateTextFieldBackground(tfTelephone, editable);
    }

    public void setTfEmailEditable(boolean editable) {
        tfEmail.setEditable(editable);
        updateTextFieldBackground(tfEmail, editable);
    }

    public void setTfSearchFieldEditable(boolean editable) {
        tfSearchField.setEditable(editable);
        updateTextFieldBackground(tfSearchField, editable);
    }
    
    public void setCmbContactTypeDisabled(boolean disabled) {
        cmbContactType.setDisable(disabled);
    }
    
    public void setCmbCountryDisabled(boolean disabled) {
        cmbCountry.setDisable(disabled);
    }
    
    public void setCmbLocationDisabled(boolean disabled) {
        cmbLocation.setDisable(disabled);
    }

    public void setBtnAddDisabled(boolean disabled) {
        btnAdd.setDisable(disabled);
    }

    public void setBtnEditDisabled(boolean disabled) {
        btnEdit.setDisable(disabled);
    }

    public void setBtnsaveDisabled(boolean disabled) {
        btnsave.setDisable(disabled);
    }

    public void setBtnDeleteDisabled(boolean disabled) {
        btnDelete.setDisable(disabled);
    }

    private void updateTextFieldBackground(TextField textField, boolean editable) {
        if (!editable) {
            textField.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        } else {
            textField.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }

    public void setAllFieldsEditable(boolean editable) {
        setTfNameEditable(editable);
        setTfTelephoneEditable(editable);
        setTfEmailEditable(editable);
        setCmbContactTypeDisabled(!editable);
        setCmbCountryDisabled(!editable);
        setCmbLocationDisabled(!editable);
    }


    public VBox getRoot() { return root; }
    public TextField getTfSearchField() { return tfSearchField; }
    public Button getBtnSearch() { return btnSearch; }
    public TreeView<Object> getTvContacts() { return tvContacts; }
}

