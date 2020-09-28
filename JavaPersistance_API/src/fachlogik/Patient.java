package fachlogik;

public class Patient extends Person
{
	private String krankenkasse;

    public Patient() {
        super();
    }

    public Patient(long svnr, String geburtsdatum, String vorname, String nachname, Geschlecht geschlecht, String adresse, String krankenkasse) {
    	super(svnr, geburtsdatum, vorname, nachname, geschlecht, adresse);
    	this.krankenkasse = krankenkasse;
    }
	
	public String getKrankenkasse()
	{
		return krankenkasse;
	}

	public void setKrankenkasse(String krankenkasse)
	{
		this.krankenkasse = krankenkasse;
	}

	@Override
	public String toString() {
		return this.getNachname()+" "+this.getVorname();
	}
}
