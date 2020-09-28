package fachlogik;

import java.math.BigDecimal;

public class Ultraschall extends Untersuchung
{
	private static BigDecimal PreisProMinute = new BigDecimal("2.0");

	public Ultraschall()
	{
		super();
		this.setBezeichnung("US");
	}

	public static BigDecimal getPreisProMinute()
	{
		return PreisProMinute;
	}
}
