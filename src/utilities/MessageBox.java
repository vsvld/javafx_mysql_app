package utilities;

import javafx.scene.control.Alert;

/**
 * @author Vsevolod Al'okhin
 */
public class MessageBox {
    public static void show(Alert.AlertType messageType, String title, String header, String content) {
        Alert alert = new Alert(messageType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
