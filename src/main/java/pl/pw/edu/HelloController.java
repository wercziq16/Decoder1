package pl.edu.pw.dekodowaniehuffmanaostateczne;

import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class HelloController {
    private Stage stage;
    private HostServices hostServices;

    private FileChooser fileChooser;
    private DirectoryChooser directoryChooser;

    @FXML
    private TextField inputFilenameTextField;

    @FXML
    private TextField outputDirectoryNameTextField;

    @FXML
    private TextField outputFilenameTextField;

    @FXML
    private ScrollPane treeVisualizationPane;

    private HuffmanTreePane treePane;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public HostServices getHostServices() {
        return hostServices;
    }

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    public void displayTree(Tree tree) {
        treePane.displayTree(tree);
    }

    @FXML
    protected void initialize() {
        fileChooser = new FileChooser();
        directoryChooser = new DirectoryChooser();
        treePane = new HuffmanTreePane();

        treeVisualizationPane.setContent(treePane);
    }

    @FXML
    protected void onInputFileChooserButtonClick() {
        fileChooser.setTitle("Open input file...");

        File inputFile = fileChooser.showOpenDialog(stage);

        if (inputFile != null) {
            inputFilenameTextField.setText(inputFile.getAbsolutePath());
        } else {
            displayError("Error", "Invalid input file path!");
        }
    }

    private void displayError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Pobierz główny Stage z kontrolera FXML
        Stage stage = (Stage) inputFilenameTextField.getScene().getWindow();
        alert.initOwner(stage);

        alert.showAndWait();
    }



    @FXML
    protected void onOutputDirectoryChooserButtonClick() {
        directoryChooser.setTitle("Choose output directory...");

        File directory = directoryChooser.showDialog(stage);

        if (directory != null)
            outputDirectoryNameTextField.setText(directory.getAbsolutePath());
    }

    @FXML
    protected void onDecodeButtonClick() {
        String inputFilename = inputFilenameTextField.getText();
        String outputDirectoryName = outputDirectoryNameTextField.getText();
        String outputFilename = outputFilenameTextField.getText();

        if (inputFilename.isBlank()) {
            return;
        }

        if (outputDirectoryName.isBlank()) {
            return;
        }

        if (outputFilename.isBlank()) {
            return;
        }

        File inputFile = new File(inputFilename);

        if (!inputFile.exists()) {
            return;
        }

        if (inputFile.isDirectory()) {
            return;
        }

        File outputDirectory = new File(outputDirectoryName);

        if (!outputDirectory.isDirectory()) {
            return;
        }

        if (!outputDirectory.exists()) {
            return;
        }

        String completeOutputPath = String.valueOf(Paths.get(outputDirectoryName, outputFilename));

        File outputFile = new File(completeOutputPath);

        if (outputFile.isDirectory()) {
            return;
        }

        if (outputFile.exists()) {
            return;
        }

        try (FileInputStream inputFileStream = new FileInputStream(inputFile)) {
            try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                HuffmanDecoder.decode(inputFileStream, outputStream, this);
            }
        } catch (IOException ignored) {
        }
    }
}