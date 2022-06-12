import java.util.*;
/**
 * Stores information about cars. Also calculates the cars age from the current date.
 * @
 *
 * PUBLIC FEATURES:
 * // Constructors
 *    public Car()
 *    public Car(String man, String mod, String info)
 *
 * // Methods
 *    public int getAge()
 *    public String getInformation()
 *    public String getInformation()
 *    public double getKilometers()
 *    public String getManufacturer()
 *    public String getModel()
 *    public int getPrice()
 *    public int getYear()
 *    public void setInformation(String info)
 *    public void setKilometers(double km)
 *    public void setManufacturer(String man)
 *    public void setModel(String mod)
 *    public void setPrice(int cost)
 *    public void setYear(int yr)
 *
 * COLLABORATORS:
 *
 * @version 1.0, 16 Oct 2004
 * @author Adam Black
 */
public class Car implements java.io.Serializable
{
	private String model;
	private String manufacturer;
	private String information;
	private int year;
	private int price;
	private double kilometers;

	public Car(){}

	/**
	 * @param man manufacturers name
	 * @param mod model name
	 * @param info extra information about the car
	 */
	public Car(String man, String mod, String info)
	{
		model = mod;
		manufacturer = man.toUpperCase();
		information = info;
	}

	/**
	 * calculates using current year - model year
	 */
	public int getAge()
	{
		GregorianCalendar calendar = new GregorianCalendar();
		return (calendar.get(Calendar.YEAR) - year);
	}

	public String getInformation()
	{
		return information;
	}

	public double getKilometers()
	{
		return kilometers;
	}

	public String getManufacturer()
	{
		return manufacturer;
	}

	public String getModel()
	{
		return model;
	}

	public int getPrice()
	{
		return price;
	}

	public int getYear()
	{
		return year;
	}

	public void setInformation(String info)
	{
		information = info;
	}

	public void setKilometers(double km)
	{
		kilometers = km;
	}

	public void setManufacturer(String man)
	{
		manufacturer = man.toUpperCase();
	}

	public void setModel(String mod)
	{
		model = mod;
	}

	public void setPrice(int cost)
	{
		price = cost;
	}

	public void setYear(int yr)
	{
		year = yr;
	}
}