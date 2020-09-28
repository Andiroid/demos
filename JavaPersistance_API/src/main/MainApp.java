package main;

import java.io.IOException;
import java.time.LocalDateTime;

import fachlogik.Geschlecht;
import fachlogik.MagnetResonanz;
import fachlogik.Patient;
import fachlogik.Person;
import fachlogik.Personal;
import fachlogik.Ultraschall;
import fachlogik.Untersuchung;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import persistence.ExaminationDao;
import view.ExaminationEditDialogController;
import view.ExaminationOverviewController;
import view.PersonEditDialogController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MainApp extends Application {

    public Stage primaryStage;
    private ExaminationOverviewController examinationsController;
    private ObservableList<Patient> patientData = FXCollections.observableArrayList();
    private ObservableList<Personal> personData = FXCollections.observableArrayList();
    private ObservableList<Untersuchung> examinationData = FXCollections.observableArrayList();
    public static int tabIndex;
    public static boolean editMode;
    public static String exType;

	public MainApp() {

		/*
		// test data
	    patientData.add(new Patient(1234, "01.01.1980", "Thomas", "Maier", Geschlecht.MAENNLICH, "1010 Wien, Stephansplatz 13", "WGKK"));
	    patientData.add(new Patient(4321, "01.01.1980", "Margit", "Schmidt", Geschlecht.WEIBLICH, "1050 Wien, Spengergasse 27", "BVA"));
	    personData.add(new Personal(5678, "01.01.1980", "Hans", "Lang", Geschlecht.MAENNLICH, "1010 Wien, Stephansplatz 1", 0));
	    personData.add(new Personal(8765, "01.01.1980", "Ruth", "Kurz", Geschlecht.WEIBLICH, "1050 Wien, Spengergasse 20", 0));

		Ultraschall u = new Ultraschall();
		u.setPatient(patientData.get(0));
		u.setBeginn(LocalDateTime.of(2017,11,21,7,15));
		u.setEnde(LocalDateTime.of(2017,11,21,7,25));
		examinationData.add(u);

		MagnetResonanz m1 = new MagnetResonanz();
		m1.setPatient(patientData.get(1));
		m1.setKm("Artirem Injektionslösung");
		m1.setMengeKM("3");
		m1.setBeginn(LocalDateTime.of(2017,11,21,10,23));
		m1.setEnde(LocalDateTime.of(2017,11,21,10,51));
		examinationData.add(m1);
		*/
        // Todo
        ExaminationDao ed = new ExaminationDao();
        patientData = FXCollections.observableArrayList(ed.allPatients());
        //personData = FXCollections.observableArrayList(ed.allPersons());
        personData = FXCollections.observableArrayList(ed.allOldPersons());
        //examinationData = FXCollections.observableArrayList(ed.allExaminations());
        examinationData = FXCollections.observableArrayList(ed.allActualExaminations());

	}
	
	public ObservableList<Patient> getPatientData() {
		return patientData;
	}

	public ObservableList<Personal> getPersonData() {
	    return personData;
	}

	public ObservableList<Untersuchung> getExaminationData() {
	    return examinationData;
	}

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("UntersuchungsApp");

        showExaminationOverview();
    }

    @Override
    public void stop(){
    	this.primaryStage.close();
    }

    public void showExaminationOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/ExaminationOverview.fxml"));
            TabPane examinationOverview = (TabPane) loader.load();

            examinationsController = loader.getController();
            examinationsController.setMainApp(this);

            primaryStage.setScene(new Scene(examinationOverview));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean showPersonEditDialog(Person person) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Person editieren");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showExaminationEditDialog(Untersuchung exam) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/ExaminationEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Untersuchung editieren");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            ExaminationEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);
            controller.setExamination(exam);
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("jpa");
        EntityManager em = emfactory.createEntityManager();
	    launch(args);
    }

	public ExaminationOverviewController getExaminationsController()
	{
		return examinationsController;
	}

}