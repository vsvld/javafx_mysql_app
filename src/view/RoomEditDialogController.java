package view;


import entity.Room;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.MessageBox;

public class RoomEditDialogController {
    @FXML
    private TextField roomNameField;
    @FXML
    private TextField pc1numberField;
    @FXML
    private TextField pc2numberField;
    @FXML
    private TextField pc1powerField;
    @FXML
    private TextField pc2powerField;
    @FXML
    private TextField workedHoursField;

    private Stage dialogStage;
    private Room room;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * @param dialogStage stage of this dialog, which to be set
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * @param room room to be edited in the dialog
     */
    public void setRoom(Room room) {
        this.room = room;
        roomNameField.setText(room.getName());
        pc1numberField.setText(Integer.toString(room.getPc1number()));
        pc2numberField.setText(Integer.toString(room.getPc2number()));
        pc1powerField.setText(Double.toString(room.getPc1power()));
        pc2powerField.setText(Double.toString(room.getPc2power()));
        workedHoursField.setText(Double.toString(room.getWorkedHours()));
    }

    /**
     * @return true if the user clicked OK, false otherwise
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {

        // first check if filled
        if (areFieldsFilled()) {
            // then catch invalid arguments exceptions
            try {
                room.setName(roomNameField.getText());
                room.setPc1number(Integer.parseInt(pc1numberField.getText()));
                room.setPc2number(Integer.parseInt(pc2numberField.getText()));
                room.setPc1power(Double.parseDouble(pc1powerField.getText()));
                room.setPc2power(Double.parseDouble(pc2powerField.getText()));
                room.setWorkedHours(Double.parseDouble(workedHoursField.getText()));
                room.calculateEnergyConsumption();
                okClicked = true;
                dialogStage.close();
            } catch (IllegalArgumentException e) {
                MessageBox.show(Alert.AlertType.ERROR, "Invalid fields", "Please correct invalid fields!", e.getMessage());
            }
        }

    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean areFieldsFilled() {
        String errorMessage = "";
        if (roomNameField.getText() == null || roomNameField.getText().length() == 0)
            errorMessage += "No room name passed!\n";
        if (pc1numberField.getText() == null || pc1numberField.getText().length() == 0)
            errorMessage += "Number of computers of type 1 cannot be blank!\n";
        if (pc1powerField.getText() == null || pc1powerField.getText().length() == 0)
            errorMessage += "Power of computers of type 1 cannot be blank!\n";
        if (pc2numberField.getText() == null || pc2numberField.getText().length() == 0)
            errorMessage += "Number of computers of type 2 cannot be blank!\n";
        if (pc2powerField.getText() == null || pc2powerField.getText().length() == 0)
            errorMessage += "Power of computers of type 2 cannot be blank!\n";
        if (workedHoursField.getText() == null || workedHoursField.getText().length() == 0)
            errorMessage += "Worked hours number cannot be blank!\n";

        if (errorMessage.length() == 0) {
            return true;
        } else {
            MessageBox.show(Alert.AlertType.ERROR, "Blank fields", "Please fill all fields!", errorMessage);
            return false;
        }
    }
}
