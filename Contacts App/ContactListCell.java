package contactsapp;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class ContactListCell extends ListCell<Contact> {
    private VBox vbox = new VBox(8.0);
    private ImageView thumbnail = new ImageView();
    private Label label = new Label();
    
    public ContactListCell(){
        vbox.setAlignment(Pos.CENTER);
        thumbnail.setPreserveRatio(true);
        thumbnail.setFitHeight(100.0);
        vbox.getChildren().add(thumbnail);
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);
        vbox.getChildren().add(label);
        setPrefWidth(USE_PREF_SIZE);
    }
    
    @Override
    protected void updateItem(Contact item, boolean empty){
        super.updateItem(item, empty);
        if (empty || item == null){
            setGraphic(null);
        } else {
            if (item.getImage() != null){
                thumbnail.setImage(item.getImage());
            } else {
                thumbnail.setImage(null);
            }
            label.setText(item.getName());
            setGraphic(vbox);
        }
    }
}