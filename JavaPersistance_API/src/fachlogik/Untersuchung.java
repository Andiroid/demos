package fachlogik;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Untersuchung
{
	private int id;
	private StringProperty bezeichnung;
    private ObjectProperty<LocalDateTime> beginn;
    private ObjectProperty<LocalDateTime> ende;
	private Patient patient;
	private StringProperty vorname;
	private StringProperty nachname;
	private String kontrastmittel;
	private BigDecimal mengeKM;
	private List<Personal> personal;
	
	public Untersuchung()
	{
		beginn = new SimpleObjectProperty<LocalDateTime>(LocalDateTime.now());
		ende = new SimpleObjectProperty<LocalDateTime>(LocalDateTime.now());
		vorname = new SimpleStringProperty();
		nachname = new SimpleStringProperty();
	}

	public BigDecimal dauer() 
	{
		return new BigDecimal(Duration.between(beginn.get(), ende.get()).toMinutes());
	}

//	public LocalDateTime getBeginn()
//	{
//		return beginn.get();
//	}
//
//	public LocalDateTime getEnde()
//	{
//		return ende.get();
//	}
//
//	public void setBeginn(LocalDateTime beginn)
//	{
//		this.beginn.set(beginn);
//	}
//
//	public void setEnde(LocalDateTime ende)
//	{
//		this.ende.set(ende);
//	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// Implementation SQLite:
	public String getBeginn()
	{
		return beginn.get().toString();
	}

	public String getEnde()
	{
		return ende.get().toString()	;
	}

	public void setBeginn(String beginn)
	{
		this.beginn.set(LocalDateTime.parse(beginn, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}

	public void setEnde(String ende)
	{
		this.ende.set(LocalDateTime.parse(ende, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}

	public ObjectProperty<LocalDateTime> getBeginnProperty()
	{
		return beginn;
	}

	public ObjectProperty<LocalDateTime> getEndeProperty()
	{
		return ende;
	}

	public void setKm(String km)
	{
		this.kontrastmittel = km;
	}

	public Patient getPatient()
	{
		return patient;
	}

	public void setPatient(Patient patient)
	{
		this.patient = patient;
		vorname.set(patient.getVorname());
		nachname.set(patient.getNachname());
	}

	public StringProperty getVornameProperty() {
		return vorname;
	}
	
	public StringProperty getNachnameProperty() {
		return nachname;
	}

	public BigDecimal getMengeKM()
	{
		return mengeKM;
	}

	public void setMengeKM(String mengeKM)
	{
		this.mengeKM = new BigDecimal(mengeKM);
	}

	public String getKm()
	{
		return kontrastmittel;
	}

	public String getBezeichnung()
	{
		return bezeichnung.get();
	}

	public StringProperty getBezeichnungProperty()
	{
		return bezeichnung;
	}

	public void setBezeichnung(String name)
	{
		this.bezeichnung = new SimpleStringProperty(name);
	}

	public List<Personal> getPersonal() {
		return personal;
	}

	public void setPersonal(List<Personal> personal) {
		this.personal = personal;
	}
}
