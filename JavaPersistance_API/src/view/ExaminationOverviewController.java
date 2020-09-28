package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

import fachlogik.ComputerTomographie;
import fachlogik.Endoskopie;
import fachlogik.MagnetResonanz;
import fachlogik.Patient;
import fachlogik.Personal;
import fachlogik.Roentgen;
import fachlogik.Ultraschall;
import fachlogik.Untersuchung;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import main.MainApp;
import persistence.ExaminationDao;

import static main.MainApp.*;

public class ExaminationOverviewController {
    @FXML
    public TabPane tabP;
    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, String> firstNameColumnP;
    @FXML
    private TableColumn<Patient, String> lastNameColumnP;
    
    @FXML
    private TableView<Personal> personTable;
    @FXML
    private TableColumn<Personal, String> firstNameColumn;
    @FXML
    private TableColumn<Personal, String> lastNameColumn;
    
    @FXML
    private TableView<Untersuchung> examinationTable;
    @FXML
    private TableColumn<Untersuchung, String> typeColumn;
    @FXML
    private TableColumn<Untersuchung, String> eFirstNameColumn;
    @FXML
    private TableColumn<Untersuchung, String> eLastNameColumn;
    @FXML
    private TableColumn<Untersuchung, LocalDateTime> startColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label svnrLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label birthdayLabel;

    @FXML
    private Label firstNameLabelP;
    @FXML
    private Label lastNameLabelP;
    @FXML
    private Label addressLabelP;
    @FXML
    private Label svnrLabelP;
    @FXML
    private Label genderLabelP;
    @FXML
    private Label birthdayLabelP;

    @FXML
    private Label startLabel;
    @FXML
    private Label endLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label patientLabel;
    @FXML
    private Label mediumLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Label staffLabel;

    private MainApp mainApp;
    
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @FXML
    private void initialize() {
        firstNameColumnP.setCellValueFactory(cellData -> cellData.getValue().getVornameProperty());
        lastNameColumnP.setCellValueFactory(cellData -> cellData.getValue().getNachnameProperty());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getVornameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNachnameProperty());

        typeColumn.setCellValueFactory(cellData -> cellData.getValue().getBezeichnungProperty());
        eFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getVornameProperty());
        eLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNachnameProperty());
        startColumn.setCellValueFactory(cellData -> cellData.getValue().getBeginnProperty());
        startColumn.setCellFactory(cell -> new TableCell<Untersuchung, LocalDateTime>() {
        	@Override
        	protected void updateItem(LocalDateTime item, boolean empty) {
        		super.updateItem(item, empty);
        		if (empty)
        			setText(null);
        		else
        			setText(String.format(item.format(formatter)));
        	}
        });
        
        showPatientDetails(null);
        showPersonDetails(null);
        showExaminationDetails(null);
        
        patientTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPatientDetails(newValue));
		patientTable.setPlaceholder(new Label("Keine Patienten vorhanden"));
        personTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));
		personTable.setPlaceholder(new Label("Keine Personen vorhanden"));
        examinationTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showExaminationDetails(newValue));
        examinationTable.setPlaceholder(new Label("Keine Untersuchungen vorhanden"));

    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        patientTable.setItems(mainApp.getPatientData());
        patientTable.getSelectionModel().selectFirst();
        personTable.setItems(mainApp.getPersonData());
        personTable.getSelectionModel().selectFirst();
        examinationTable.setItems(mainApp.getExaminationData());
        examinationTable.getSelectionModel().selectFirst();
    }

    private void showPatientDetails(Patient patient) {
        if (patient != null) {
            firstNameLabelP.setText(patient.getVorname());
            lastNameLabelP.setText(patient.getNachname());
            addressLabelP.setText(patient.getAdresse());
            genderLabelP.setText(patient.getGeschlecht().toString());
            svnrLabelP.setText(Long.toString(patient.getSvnr()));
            birthdayLabelP.setText(dateFormatter.format(patient.getGeburtsdatum()));
        } else {
            firstNameLabelP.setText("");
            lastNameLabelP.setText("");
            addressLabelP.setText("");
            genderLabelP.setText("");
            svnrLabelP.setText("");
            birthdayLabelP.setText("");
        }
    }

    private void showPersonDetails(Personal person) {
        if (person != null) {
            firstNameLabel.setText(person.getVorname());
            lastNameLabel.setText(person.getNachname());
            addressLabel.setText(person.getAdresse());
            genderLabel.setText(person.getGeschlecht().toString());
            svnrLabel.setText(Long.toString(person.getSvnr()));
            birthdayLabel.setText(dateFormatter.format(person.getGeburtsdatum()));
        } else {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            addressLabel.setText("");
            genderLabel.setText("");
            svnrLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    private void showExaminationDetails(Untersuchung examination) {
        if (examination != null) {
        	//startLabel.setText(examination.getBeginn().format(formatter));
        	//endLabel.setText(examination.getEnde().format(formatter));
        	// Implementation SQLite:
        	startLabel.setText(examination.getBeginn());
        	endLabel.setText(examination.getEnde());
        	typeLabel.setText(examination.getBezeichnung());
        	patientLabel.setText(examination.getPatient().getNachname()+" "+examination.getPatient().getVorname());
        	mediumLabel.setText(examination.getKm());
        	if (examination.getMengeKM()!=null)
        		quantityLabel.setText(examination.getMengeKM().toString());
        	else
            	quantityLabel.setText("");
        	StringJoiner joiner = new StringJoiner(", ", "", "");
            if (examination.getPersonal() != null)
            	for (Personal p:examination.getPersonal())
            		joiner.add(p.toString());
            staffLabel.setText(joiner.toString());
        } else {
        	startLabel.setText("");
        	endLabel.setText("");
        	typeLabel.setText("");
        	patientLabel.setText("");
        	mediumLabel.setText("");
        	quantityLabel.setText("");
        	staffLabel.setText("");
        }
    }

    @FXML
    void handleNewPatient() {
        editMode = false;
        Patient tempPatient = new Patient(0, "2000-01-01", null, null, null, null, null);
        boolean okClicked = mainApp.showPersonEditDialog(tempPatient);
        if (okClicked) {
            mainApp.getPatientData().add(tempPatient);
 			patientTable.getSelectionModel().select(tempPatient);
       }
    }

    @FXML
    void handleEditPatient() {
        editMode = true;
        Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPatient);
            if (okClicked) {
                showPatientDetails(selectedPatient);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Patient Selected");
            alert.setContentText("Please select a patient in the table.");

            alert.showAndWait();
        }
    }    
    
    @FXML
    void handleDeletePatient() {
        int selectedIndex = patientTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            // Todo
            ExaminationDao ed = new ExaminationDao();
            ed.deletePatient(patientTable.getSelectionModel().getSelectedItem());
            patientTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Patient Selected");
            alert.setContentText("Please select a patient in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    void handleNewPerson() {
        editMode = false;
        Personal tempPerson = new Personal(0, "2000-01-01", null, null, null, null, 0);
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
 			personTable.getSelectionModel().select(tempPerson);
       }
    }

    @FXML
    void handleEditPerson() {
        editMode = true;
        Personal selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }    
    
    @FXML
    void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            ExaminationDao ed = new ExaminationDao();
            ed.deletePerson(personTable.getSelectionModel().getSelectedItem());
            personTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    void handleNewExaminationCT() {
        editMode = false;
        exType = "CT";
        ComputerTomographie tempExam = new ComputerTomographie();
        boolean okClicked = mainApp.showExaminationEditDialog(tempExam);
        if (okClicked) {
            mainApp.getExaminationData().add(tempExam);
 			examinationTable.getSelectionModel().select(tempExam);
       }
    }

    @FXML
    void handleNewExaminationEN() {
        editMode = false;
        exType = "EN";
    	Endoskopie tempExam = new Endoskopie();
        boolean okClicked = mainApp.showExaminationEditDialog(tempExam);
        if (okClicked) {
            mainApp.getExaminationData().add(tempExam);
 			examinationTable.getSelectionModel().select(tempExam);
       }
    }

    @FXML
    void handleNewExaminationMR() {
        editMode = false;
        exType = "MR";
    	MagnetResonanz tempExam = new MagnetResonanz();
        boolean okClicked = mainApp.showExaminationEditDialog(tempExam);
        if (okClicked) {
            mainApp.getExaminationData().add(tempExam);
 			examinationTable.getSelectionModel().select(tempExam);
       }
    }

    @FXML
    void handleNewExaminationRT() {
        editMode = false;
        exType = "RT";
    	Roentgen tempExam = new Roentgen();
        boolean okClicked = mainApp.showExaminationEditDialog(tempExam);
        if (okClicked) {
            mainApp.getExaminationData().add(tempExam);
 			examinationTable.getSelectionModel().select(tempExam);
       }
    }

    @FXML
    void handleNewExaminationUS() {
        editMode = false;
        exType = "US";
    	Ultraschall tempExam = new Ultraschall();
        boolean okClicked = mainApp.showExaminationEditDialog(tempExam);
        if (okClicked) {
            mainApp.getExaminationData().add(tempExam);
 			examinationTable.getSelectionModel().select(tempExam);
       }
    }

    @FXML
    void handleEditExamination() {
        editMode = true;
        Untersuchung selectedExam = examinationTable.getSelectionModel().getSelectedItem();
        if (selectedExam != null) {
            boolean okClicked = mainApp.showExaminationEditDialog(selectedExam);
            if (okClicked) {
                showExaminationDetails(selectedExam);
            }
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No examination Selected");
            alert.setContentText("Please select a examination in the table.");
            alert.showAndWait();
        }
    }


    @FXML
    void handleDeleteExamination() {
        int selectedIndex = examinationTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            ExaminationDao ed = new ExaminationDao();
            ed.deleteExamination(examinationTable.getSelectionModel().getSelectedItem());
            examinationTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No examination Selected");
            alert.setContentText("Please select a examination in the table.");
            alert.showAndWait();
        }
    }

    @FXML
    private void toggleTab(){
        tabIndex = tabP.getSelectionModel().getSelectedIndex();
    }
}
