package htl.javafx;

import htl.javafx.model.Encrypter;
import htl.javafx.util.FileWriterUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EncrypterController {

    private final Encrypter encrypter = new Encrypter();

    @FXML
    private TextField txtAlphabet, txtKey;

    @FXML
    private TextArea txtEncryptedText, txtPlainText;

    @FXML
    public void initialize() {
        // Bind properties
        txtAlphabet.textProperty().bindBidirectional(encrypter.alphabetProperty());
        txtKey.textProperty().bindBidirectional(encrypter.keyProperty());
        txtPlainText.textProperty().bindBidirectional(encrypter.plainTextProperty());
        txtEncryptedText.textProperty().bindBidirectional(encrypter.encryptedTextProperty());

        // Synchronize selections
        txtPlainText.selectedTextProperty().addListener((obs, oldText, newText) -> {
            txtEncryptedText.selectRange(txtPlainText.getSelection().getStart(), txtPlainText.getSelection().getEnd());
        });

        txtEncryptedText.selectedTextProperty().addListener((obs, oldText, newText) -> {
            txtPlainText.selectRange(txtEncryptedText.getSelection().getStart(), txtEncryptedText.getSelection().getEnd());
        });
    }

    @FXML
    public void onStandartButtonClicked(ActionEvent event) {
        encrypter.setDefaultAlphabet();
    }

    @FXML
    public void onRecalcButtonClicked(ActionEvent event) {
        encrypter.generateRandomKey();
    }

    @FXML
    public void onSaveToFileClicked(ActionEvent event) {
        FileWriterUtil.writeToFile("encrypted.txt", encrypter.getEncryptedText());
    }
}
