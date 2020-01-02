package contactsapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.net.MalformedURLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.collections.FXCollections;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import java.util.Comparator;

public class ContactsApplicationController {
    
    Contact CurrentContact = null;
    
    @FXML private TextField LastNameLabel;
    @FXML private TextField FirstNameLabel;
    @FXML private TextField EmailLabel;
    @FXML private TextField PhoneLabel;
    @FXML private TextField AddressLabel;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button saveButton;
    @FXML private ImageView ImageTile;
    @FXML private ListView<Contact> ContactList;
    private final ObservableList<Contact> Contacts = FXCollections.observableArrayList();
    
    @FXML private void AddContact() {
        //create contact
        CurrentContact = null;
        ClearContact();
    }
    
    @FXML private void DeleteContact() {
        ClearContact();
        Contacts.remove(CurrentContact);
        SortContacts();
        ContactList.setItems(null);
        ContactList.setItems(Contacts);
        ClearContact();
        //do not auto-select next contact
    }
    
    @FXML private void SaveContact() {
        if (!LastNameLabel.getText().isEmpty() || !FirstNameLabel.getText().isEmpty()){
            //modify existing selected contact
            if (CurrentContact != null){
                CurrentContact.setFirstName(FirstNameLabel.getText());
                CurrentContact.setLastName(LastNameLabel.getText());
                CurrentContact.setImage(ImageTile.getImage());
                CurrentContact.setEmail(EmailLabel.getText());
                CurrentContact.setPhone(PhoneLabel.getText());
                CurrentContact.setAddress(AddressLabel.getText());
            } else {
                Contact newContact = new Contact(LastNameLabel.getText(), FirstNameLabel.getText(), EmailLabel.getText(), PhoneLabel.getText(), AddressLabel.getText(), ImageTile.getImage());
                Contacts.add(newContact);
            }
            SortContacts();
            ContactList.setItems(Contacts);
        } else {
            Alert alert = new Alert(AlertType.ERROR, "Please enter a name");
            alert.setGraphic(null);
            alert.setHeaderText("");
            alert.showAndWait();
            
            if (alert.getResult() == ButtonType.OK){
                alert.close();
            }
        }
    }
    
    @FXML private void ChooseImage() throws MalformedURLException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select an image");
        File file = chooser.showOpenDialog(new Stage());
        if (file != null){
            String filepath = file.toURI().toURL().toString();
            Image image = new Image(filepath);
            ImageTile.setImage(image);
        }
    }
    
    Comparator<? super Contact> CompareContacts = new Comparator<Contact>() {
        @Override
        public int compare(Contact Contact1, Contact Contact2) {
            return Contact1.getName().compareToIgnoreCase(Contact2.getName());
        }
    };
    
    private void SortContacts(){
        FXCollections.sort(Contacts, CompareContacts);
    }
    
    public void initialize() {
        ContactList.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Contact>(){
                @Override
                public void changed(ObservableValue<? extends Contact> ov, Contact oldValue, Contact newValue){
                    if (newValue != null){
                        LastNameLabel.setText(newValue.getLastName());
                        FirstNameLabel.setText(newValue.getFirstName());
                        AddressLabel.setText(newValue.getAddress());
                        EmailLabel.setText(newValue.getEmail());
                        PhoneLabel.setText(newValue.getPhone());
                        if (newValue.getImage() != null){
                            ImageTile.setImage(newValue.getImage());
                        } else {
                            ImageTile.setImage(null);
                        }
                    } else {
                        ClearContact();
                    }
                    CurrentContact = newValue;
                }
            }
        );
        
        ContactList.setCellFactory(
            new Callback<ListView<Contact>, ListCell<Contact>>(){
                @Override
                public ListCell<Contact> call(ListView<Contact> listView){
                    return new ContactListCell();
                }
            }
        );
    }
    
    private void ClearContact(){
        LastNameLabel.setText("");
        FirstNameLabel.setText("");
        EmailLabel.setText("");
        PhoneLabel.setText("");
        AddressLabel.setText("");
        ImageTile.setImage(null);
    }
}