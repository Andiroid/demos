package fachlogik;

public class Personal extends Person
{
	private long personalnummer;

    public Personal() {
        super();
    }

    public Personal(long svnr, String geburtsdatum, String vorname, String nachname, Geschlecht geschlecht, String adresse, long personalnummer) {
    	super(svnr, geburtsdatum, vorname, nachname, geschlecht, adresse);
    	this.personalnummer = personalnummer;
    }
	
	public long getPersonalnummer()
	{
		return personalnummer;
	}

	public void setPersonalnummer(long personalnummer)
	{
		this.personalnummer = personalnummer;
	}
}
