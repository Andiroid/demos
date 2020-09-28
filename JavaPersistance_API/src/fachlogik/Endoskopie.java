package fachlogik;

import java.math.BigDecimal;

public class Endoskopie extends Untersuchung
{
	private static BigDecimal PreisProMinute = new BigDecimal("3.0");
	private static BigDecimal Fixkosten = new BigDecimal("100.0");
	
	public Endoskopie()
	{
		super();
		this.setBezeichnung("EN");
	}
	
	public static BigDecimal getPreisProMinute()
	{
		return PreisProMinute;
	}
	
	public static BigDecimal getFixkosten()
	{
		return Fixkosten;
	}
}
