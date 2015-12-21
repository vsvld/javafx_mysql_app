package view;

import com.sun.javaws.exceptions.InvalidArgumentException;
import entity.Room;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.MainClass;
import model.DataModel;
import utilities.MessageBox;
import utilities.SummaryInfo;

import java.io.IOException;
import java.sql.SQLException;

public class MainWindowController {
	@FXML
	private TableView<Room> roomTable;
	@FXML
	private TableColumn<Room, String> roomNameColumn;
	@FXML
    private TableColumn<Room, Integer> pc1numberColumn;
    @FXML
    private TableColumn<Room, Integer> pc2numberColumn;
    @FXML
    private TableColumn<Room, Double> pc1powerColumn;
    @FXML
    private TableColumn<Room, Double> pc2powerColumn;
    @FXML
    private TableColumn<Room, Double> workedHoursColumn;
    @FXML
    private TableColumn<Room, Double> energyConsumptionColumn;
    @FXML
    private TextField centerAverageConsumptionField;
    @FXML
    private TextField centerWholeConsumptionField;

    private static MainClass mainApp;
    private static DataModel model;
    private static TableViewSelectionModel<Room> selectionModel;
    private static final String FORMAT_STR = "%5.2f";

    /**
     * Initializes the controller class. This method is automatically called after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        try {
            model = DataModel.getInstance();
            model.load();
        } catch (SQLException e) {
            MessageBox.show(Alert.AlertType.ERROR, "Database problem", "Some database problem when loading", e.getMessage());
            Platform.exit();
        }

        // initializing "rooms" table
        roomNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pc1numberColumn.setCellValueFactory(new PropertyValueFactory<>("pc1number"));
        pc2numberColumn.setCellValueFactory(new PropertyValueFactory<>("pc2number"));
        pc1powerColumn.setCellValueFactory(new PropertyValueFactory<>("pc1power"));
        pc2powerColumn.setCellValueFactory(new PropertyValueFactory<>("pc2power"));
        workedHoursColumn.setCellValueFactory(new PropertyValueFactory<>("workedHours"));
        energyConsumptionColumn.setCellValueFactory(new PropertyValueFactory<>("energyConsumption"));

        selectionModel = roomTable.getSelectionModel();
        calculateSummaryFields();
    }

    public MainWindowController() {
    }

    public void setMainApp(MainClass mainApp) {
        MainWindowController.mainApp = mainApp;
        roomTable.setItems(model.getCache());
    }

    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @param room the room object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showRoomEditDialog(Room room) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainClass.class.getResource("/view/RoomEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Computer Room");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the room into the controller.
            RoomEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setRoom(room);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void handleAdd() {
        Room tempRoom = new Room();
        boolean okClicked = showRoomEditDialog(tempRoom);
        try {
            if (okClicked) {
                model.add(tempRoom);
                calculateSummaryFields();
            }
        } catch (SQLException e) {
            MessageBox.show(Alert.AlertType.ERROR, "Database problem", "Some database problem when adding", e.getMessage());
        } catch (InvalidArgumentException e) {
            MessageBox.show(Alert.AlertType.ERROR, "Invalid fields", "Please correct invalid fields!", e.getMessage());
            handleAdd();
        }
    }

    @FXML
    private void handleDelete() {
        int selectedIndex = selectionModel.getSelectedIndex();
        if (selectedIndex >= 0) {
            try {
                model.delete(selectedIndex);
            } catch (SQLException e) {
                MessageBox.show(Alert.AlertType.ERROR, "Database problem", "Some database problem when deleting", e.getMessage());
            }
            calculateSummaryFields();
        } else {
            // Nothing selected.
            MessageBox.show(AlertType.WARNING, "No selection", "No computer room selected", "Please select computer room in the table!");
        }
    }

    @FXML
    private void handleEdit() {
        int selectedIndex = selectionModel.getSelectedIndex();
        Room selectedRoom = selectionModel.getSelectedItem();

        if (selectedRoom != null) {
            boolean okClicked = showRoomEditDialog(selectedRoom);
            try {
                if (okClicked) {
                    model.edit(selectedIndex);
                    calculateSummaryFields();
                }
            } catch (SQLException e) {
                MessageBox.show(Alert.AlertType.ERROR, "Database problem", "Some database problem when updating", e.getMessage());
            } catch (InvalidArgumentException e) {
                MessageBox.show(Alert.AlertType.ERROR, "Invalid fields", "Please correct invalid fields!", e.getMessage());
                handleEdit();
            }
        } else {
            // Nothing selected.
            MessageBox.show(AlertType.WARNING, "No selection", "No computer room selected", "Please select computer room in the table!");
        }
    }

    @FXML
    private void handleAbout() {
        MessageBox.show(AlertType.INFORMATION, "ComputerCenterEnergyConsumptionApp", "About developer",
                "Vsevolod Al'okhin\nStudent of Master 2 \"Decision Informatics and Statistics for Management\"");
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }

    private void calculateSummaryFields() {
        centerAverageConsumptionField.setText(String.format(FORMAT_STR, SummaryInfo.calculateAverageEnergyConsumption(model.getCache())));
        centerWholeConsumptionField.setText(String.format(FORMAT_STR, SummaryInfo.calculateWholeEnergyConsumption(model.getCache())));
    }
}