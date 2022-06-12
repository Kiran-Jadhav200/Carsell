import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
/**
 * This class is the main JFrame of the application, and deals with the car collection.
 * It also creates instances of the other panels (add car, view car, etc) and incorporates
 * them into the main frame. This is hierarchically, the highest class.
 * @
 *
 * PUBLIC FEATURES:
 * // Constructors
 *    public CarSalesSystem(String f)
 *
 * // Methods
 *    public void aboutMenuItemClicked()
 *    public void actionPerformed(ActionEvent ev)
 *    public void addCarUpdateListener(Object listener)

 *    public int addNewCar(Car c)
 *    public void closing()
 *    public void componentHidden(ComponentEvent ev)
 * 	  public void componentMoved(ComponentEvent ev)
 *	  public void componentResized(ComponentEvent ev)
 *    public void componentShown(ComponentEvent ev)
 *    public static double[] convertToRange(String s)
 *    public Car[] getAllCars()
 *    public boolean getCarsUpdated()
 *    public double getStatistics(int type)
 *    public static void main(String[] args)
 *    public Car[] search(int minAge, int maxAge)
 *    public Car[] search(int minPrice, int maxPrice, double minDistance, double maxDistance)
 *    public void setCarsUpdated()
 *    public void stateChanged(ChangeEvent ev)
 *    public static Car[] vectorToCar(Vector v)
 *
 * COLLABORATORS:
 *    AboutDialog, CarsCollection, WindowCloser, WelcomePanel, AddCarPanel
 *    ShowAllCarsPanel, SearchByAgePanel, SearchByOtherPanel
 *
 * @version 1.0, 16 Oct 2004
 * @author Adam Black
 */
public class CarSalesSystem extends JFrame implements ActionListener, ComponentListener, ChangeListener
{
	/**
	 * the current version of CarSalesSystem
	 */
	public static final double APP_VERSION = 1.0;
	/**
	 * is used as the parameter in the 'getStatistics(int type)' method to indicate you wish to find the
	 * total number of cars in the system
	 */
	public static final int CARS_COUNT = 0;
	/**
	 * is used as the parameter in the 'getStatistics(int type)' method to indicate you wish to find the
	 * total number of manufacturers in the system
	 */
	public static final int MANUFACTURERS_COUNT = 1;
	/**
	 * is used as the parameter in the 'getStatistics(int type)' method to indicate you wish to find the
	 * average price, from the entire collection of cars in the system
	 */
	public static final int AVERAGE_PRICE = 2;
	/**
	 * is used as the parameter in the 'getStatistics(int type)' method to indicate you wish to find the
	 * average distance travelled (in kilometers) from the entire collection of cars in the system
	 */
	public static final int AVERAGE_DISTANCE = 3;
	/**
	 * is used as the parameter in the 'getStatistics(int type)' method to indicate you wish to find the
	 * average age from the entire collection of cars in the system
	 */
	public static final int AVERAGE_AGE = 4;

	private String file;
	private AboutDialog aboutDlg;
	private boolean carsUpdated = false;
	private Vector registeredListeners = new Vector();
	private CarsCollection carCollection;
	private JPanel topPanel = new JPanel(new BorderLayout());
	private JPanel titlePanel = new JPanel(new GridLayout(2, 1));
	private JLabel statusLabel = new JLabel();
	private JLabel pictureLabel = new JLabel();
	private JLabel carCoLabel = new JLabel("My Car Company", JLabel.CENTER);
	private JLabel salesSysLabel = new JLabel("Car Sales System", JLabel.CENTER);
	private JTabbedPane theTab = new JTabbedPane(JTabbedPane.LEFT);
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem aboutItem = new JMenuItem("About");
	private JMenuItem exitItem = new JMenuItem("Exit");
	private WindowCloser closer = new WindowCloser();

	/**
	 * @param f existing binary file for storing/retrieving car data
	 */
	public CarSalesSystem(String f)
	{
		super("Car Sales");

		addWindowListener(closer);
		addComponentListener(this);
		theTab.addChangeListener(this);

		setSize(780, 560);

		Container c = getContentPane();
		carCollection = new CarsCollection();

		file = f;

		try
		{
			carCollection.loadCars(file);
		}
		catch (java.io.FileNotFoundException exp)
		{
			System.out.println("The data file, 'cars.dat' doesn't exist. Plase create an empty file named 'cars.dat'");
			System.exit(0);
		}
		// empty cars.dat file, this error should be ignored
		catch (java.io.EOFException exp){}
		catch (java.io.IOException exp)
		{
			System.out.println("The data file, 'cars.dat' is possibly corrupted. Please delete it and create a new empty data file named cars.dat");
			System.exit(0);
		}
		catch (Exception exp)
		{
			System.out.println("There was an error loading 'cars.dat'. Try deleting and creating a new empty file named 'cars.dat'");
			System.exit(0);
		}

		String currentFont = carCoLabel.getFont().getName();
		carCoLabel.setFont(new Font(currentFont, Font.BOLD, 26));
		salesSysLabel.setFont(new Font(currentFont, Font.PLAIN, 16));

		// create menu bar
		menuBar.add(fileMenu);
		fileMenu.add(aboutItem);
		fileMenu.add(exitItem);
		aboutItem.addActionListener(this);
		exitItem.addActionListener(this);

		// add menu bar
		setJMenuBar(menuBar);

		// set border on status bar label to make it look like a panel
		statusLabel.setBorder(new javax.swing.border.EtchedBorder());

		// load the picture into the top panel
		pictureLabel.setIcon(new ImageIcon("vu.png"));
		titlePanel.add(carCoLabel);
		titlePanel.add(salesSysLabel);
		topPanel.add(pictureLabel, "West");
		topPanel.add(titlePanel, "Center");

		WelcomePanel welcomePanel = new WelcomePanel(this, f);
		AddCarPanel addCarPanel = new AddCarPanel(this);
		ShowAllCarsPanel showAllCarsPanel = new ShowAllCarsPanel(this);
		SearchByAgePanel searchByAgePanel = new SearchByAgePanel(this);
		SearchByOtherPanel searchByOtherPanel = new SearchByOtherPanel(this);

		theTab.add("Welcome", welcomePanel);
		theTab.add("Add a Car", addCarPanel);
		theTab.add("Show all makes and models", showAllCarsPanel);
		theTab.add("Search on age", searchByAgePanel);
		theTab.add("Search on Price and Distance traveled", searchByOtherPanel);

		theTab.addChangeListener(showAllCarsPanel);
		theTab.addChangeListener(welcomePanel);

		theTab.setSelectedIndex(0);

		c.setLayout(new BorderLayout());
		c.add(topPanel, "North");
		c.add(theTab, "Center");
		c.add(statusLabel, "South");
	}

	/**
	 * about menu clicked, show about dialog
	 */
	public void aboutMenuItemClicked()
	{
		// if it doesn't exist, create a new instance, otherwise display the current reference
		if (aboutDlg == null)
			aboutDlg = new AboutDialog(this, "About", true);
		aboutDlg.showAbout();
	}

	/**
	 * receives and handles menu click events
	 *
	 * @param ev ActionEvent object
	 */
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getSource() == aboutItem)
			aboutMenuItemClicked();
		else if (ev.getSource() == exitItem)
			closing();
	}

	/**
	 * adds an object to receive notifications when a car is added to the system
	 *
	 * @param listener a listener object
	 */
	public void addCarUpdateListener(Object listener)
	{
		if (!(listener == null))
			registeredListeners.add(listener);
	}

	/**
	 * add a new car using the CarCollection class
	 *
	 * @param c car object to add
	 * @return whether successful or not. See CarCollection.addCar() for more info
	 */
	public int addNewCar(Car c)
	{
		return carCollection.addCar(c);
	}

	/**
	 * handles closing events for the Car Sales System. Saves any updated data to a binary file
	 */
	public void closing()
	{
		boolean ok;

		if (carsUpdated)
		{
			do
			{
				try
				{
					carCollection.saveCars(file);
					ok = true;
				}
				catch (java.io.IOException exp)
				{
					int result = JOptionPane.showConfirmDialog(this, "The data file could not be written, possibly because you don't have access to this location.\nIf you chose No to retry you will lose all car data from this session.\n\nWould you like to reattempt saving the data file?", "Problem saving data", JOptionPane.YES_NO_OPTION);

					// checks if user wants to reattempt saving the data file
					if (result == JOptionPane.YES_OPTION)
						ok = false;
					else
						ok = true;
				}
			}
			while (!ok);
		}

		System.exit(0);	//shut down jvm
	}

	public void componentHidden(ComponentEvent ev) {}

	public void componentMoved(ComponentEvent ev) {}

	/**
	 * receives events when JFrame is resized and ensures the application doesn't get resized
	 * below a minimum size which could cause display problems in the application
	 *
	 * @param ev ComponentEvent object
	 */
	public void componentResized(ComponentEvent ev)
	{
		if (this == ev.getComponent())
		{
			Dimension size = getSize();

			if (size.height < 530)
				size.height = 530;
			if (size.width < 675)
				size.width = 675;

			setSize(size);
		}
	}

	public void componentShown(ComponentEvent ev) {}

	/**
	 * Converts a range string such as "50-100" or "20+" to a double array with minimum and maximum
	 * bounds
	 *
	 * @param s a string containing a valid range. "200", "100-200", "20+" are all examples
	 * of valid ranges
	 * @return array of 2 elements. First element indicating minimum bounds, second element
	 * indicating maximum bounds. A value of -1 for minimum and maximum bounds indicates an error
	 * in converting the parameter to a proper range. A value of -1 for the maximum bounds and
	 * a value > 0 for the minimum bounds indicates the maximum bounds is infinity in theory.
	 * If both bounds are greater than 0, this indicates a valid range a to b, where a and b
	 * can be equal.
	 */
	public static double[] convertToRange(String s)
	{
		String[] parts = s.trim().split("-");
		double[] bounds = new double[2];

		try
		{
			// if true the range is either of the form 'm+' or 'm'
			if (parts.length == 1)
			{
				String c = s.substring(s.length() - 1);

				// if in the form "m+"
				if (c.equals("+"))
				{
					// get lower bounds from the string
					bounds[0] = Double.parseDouble(s.substring(0, s.length() - 1));
					// no upper maximum specified, infinite
					bounds[1] = -1;
				}
				// if true the number is of the form 'm'
				else
				{
					// upper bounds == lower bounds. The range is actually just a single number
					bounds[0] = Double.parseDouble(s);
					bounds[1] = bounds[0];
				}
			}
			// if true, in the form "m-n"
			else if(parts.length == 2)
			{
				bounds[0] = Double.parseDouble(parts[0]);
				bounds[1] = Double.parseDouble(parts[1]);
				if (bounds[0] > bounds[1])
				{
					//incorrect bounds, example, 10-5
					bounds[0] = -1;
					bounds[1] = -1;
				}
			}
		}
		catch (NumberFormatException exp)
		{
			//incorrect bounds format
			bounds[0] = -1;
			bounds[1] = -1;
		}

		return bounds;
	}

	/**
	 * gets the entire list of cars from CarsCollection
	 *
	 * @return array of cars containing individual details
	 */
	public Car[] getAllCars()
	{
		return carCollection.getAllCars();
	}


	/**
	 * checks if the cars have been updated since last program launch
	 *
	 * @return boolean indicating whether the cars have been updated
	 */
	public boolean getCarsUpdated()
	{
		return carsUpdated;
	}

	/**
	 * Retrieves statistics about the car collection
	 *
	 * @param type can be either CARS_COUNT, MANUFACTURERS_COUNT, AVERAGE_PRICE, AVERAGE_DISTANCE or
	 * AVERAGE_AGE
	 * @return a value with the queried statistic returned
	 */
	public double getStatistics(int type)
	{
		double result = 0;

		if (type == CARS_COUNT)
		{
			result	= carCollection.carsCount();
		}
		else if (type == MANUFACTURERS_COUNT)
		{
			result = carCollection.manufacturerCount();
		}
		else if (type == AVERAGE_PRICE)
		{
			result = carCollection.getAveragePrice();
		}
		else if (type == AVERAGE_DISTANCE)
		{
			result = carCollection.getAverageDistance();
		}
		else if (type == AVERAGE_AGE)
		{
			result = carCollection.getAverageAge();
		}

		return result;
	}

	/**
	 * initialize the CarSalesSystem.
	 *
	 * @param args name of binary file to use is the only argument needed
	 */
	public static void main(String[] args)
	{
		CarSalesSystem carSales = new CarSalesSystem("cars.dat");
		carSales.setVisible(true);
	}

	/**
	 * search by age, using the CarsCollection
	 *
	 * @param minAge minimum age of car
	 * @param maxAge maimum age of car
	 * @return array of cars that match the search criteria
	 */
	public Car[] search(int minAge, int maxAge)
	{
		return carCollection.search(minAge, maxAge);
	}

	/**
	 * Search by age using the CarsCollection
	 *
	 * @param minPrice minimum price of car
	 * @param maxPrice maximum price of car
	 * @param minDistance minimum distance travelled by car
	 * @param maxDistance maximum distance travelled by car
	 * @return array of cars that match the search criteria
	 */
	public Car[] search(int minPrice, int maxPrice, double minDistance, double maxDistance)
	{
		return carCollection.search(minPrice, maxPrice,  minDistance, maxDistance);
	}

	/**
	 * call this method to alert the Car Sales System that a car has been added, and also send
	 * messages to all registered car update listeners
	 */
	public void setCarsUpdated()
	{
		carsUpdated = true;

		for (int i = 0; i < registeredListeners.size(); i++)
		{
			Class[] paramType = {CarUpdateEvent.class};
			Object[] param = {new CarUpdateEvent(this)};

			try
			{
				//get a reference to the method which we want to invoke to the listener
				java.lang.reflect.Method callingMethod = registeredListeners.get(i).getClass().getMethod("carsUpdated", paramType);
				//invoke the method with our parameters
				callingMethod.invoke(registeredListeners.get(i), param);
			}
			catch (NoSuchMethodException exp)
			{
				System.out.println("Warning, 'public carsUpdated(CarEvent)' method does not exist in " + registeredListeners.get(i).getClass().getName() + ". You will not receive any car update events");
			}
			catch (IllegalAccessException exp)
			{
				System.out.println("Warning, the 'public carUpdated(CarEvent)' method couldn't be called for unknown reasons, You will not receive any car update events");
			}
			catch (Exception exp){}
		}
	}

	/**
	 * the selected tab in the JTabbedPane has been changed, so change the title bar text
	 *
	 * @param ev ChangeEvent object
	 */
	public void stateChanged(ChangeEvent ev)
	{
		if (ev.getSource() == theTab)
			statusLabel.setText("Current panel: " + theTab.getTitleAt(theTab.getSelectedIndex()));
	}

	/**
	 * converts a vector to a car array
	 *
	 * @param v vector containing array of Car objects only
	 * @return Car array containing car objects from the vector
	 */
	public static Car[] vectorToCar(Vector v)
	{
		Car[] ret = new Car[v.size()];

		for (int i = 0; i < v.size(); i++)
		{
			ret[i] = (Car)v.elementAt(i);
		}

		return ret;
	}

	class WindowCloser extends WindowAdapter
	{
		/**
		 * calls the car sales system's main closing event
		 *
		 * @param ev WindowEvent object
		 */
		public void windowClosing(WindowEvent ev)
		{
			closing();
		}
	}
}