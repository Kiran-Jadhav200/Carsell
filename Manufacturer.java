import java.util.*;
/**
 * Stores and retrieves Car objects
 * @
 *
 * PUBLIC FEATURES:
 * // Constructors
 *    public Manufacturer(String nam, Car c)
 *
 * // Methods
 *    public void addCar(Car c)
 *    public int carCount()
 *    public Car[] getAllCars()
 *    public Car[] getAllCars()
 *    public String getManufacturerName()
 *    private Car[] resizeArray(Car[] c, int extendBy)
 *    public void setManufacturersName(String nam)
 *
 * COLLABORATORS:
 *    Car
 *
 * @version 1.0, 16 Oct 2004
 * @author Adam Black
 */
public class Manufacturer implements java.io.Serializable
{
	private String manufacturer;		//name of manufacturer
	private Car[] car = new Car[0];		//stores information about all the cars by a manufacturer

	/**
	 * @param nam name of manufacturer
	 * @param c Car object to add to manufacturer
	 */
	public Manufacturer(String nam, Car c)
	{
		manufacturer = nam.toUpperCase();
		addCar(c);
	}

	/**
	 * add a new car to manufacturer
	 *
	 * @param c Car to add to manufacturer
	 */
	public void addCar(Car c)
	{
		car = resizeArray(car, 1);
		car[car.length - 1] = c;
	}

	/**
	 * count cars by manufacturer
	 * @return number of cars in manufacturer
	 */
	public int carCount()
	{
		return car.length;
	}

	/**
	 * get all cars by manufacturer
	 * @return array of Car objects by manufacturer
	 */
	public Car[] getAllCars()
	{
		return car;
	}

	/*public Car getCar(int n)
	{
		Car returnCar;

		try
		{
			returnCar = car[n];
		}
		catch (Exception exp)
		{
			returnCar = null;
		}

		return returnCar;
	}*/

	public String getManufacturerName()
	{
		return manufacturer;
	}

	/**
	 * resize the array by a number of element
	 *
	 * @param c array object to extend
	 * @param extendBy number to extend by
	 * @return resultant, extended array
	 */
	private Car[] resizeArray(Car[] c, int extendBy)
	{
		Car[] result = new Car[c.length + extendBy];

		for (int i = 0; i < c.length; i++)
		{
			result[i] = c[i];
		}

		return result;
	}

	/*public Car[] search(int minPrice, int maxPrice, int minDistance, int maxDistance)
	{
		Vector result = new Vector();
		int price;
		double distance;

		for (int i = 0; i < car.length; i++)
		{
			price = car[i].getPrice();
			distance = car[i].getKilometers();

			if (price >= minPrice && price <= maxPrice)
 				if (distance >= minDistance && distance <= maxDistance)
					result.add(car[i]);
		}

		return CarSalesSystem.vectorToCar(result);
	}*/

	/*public Car[] search(int minAge, int maxAge)
	{
		Vector result = new Vector();

		for (int i = 0; i < car.length; i++)
		{
			if (car[i].getAge() >= minAge && car[i].getAge() <= maxAge)
			{
				result.addElement(car[i]);
			}
		}

		return CarSalesSystem.vectorToCar(result);
	}*/

	public void setManufacturersName(String nam)
	{
		manufacturer = nam.toUpperCase();
	}
}