import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
/**
 * A panel used for display information about all cars in the collection
 * @
 *
 * PUBLIC FEATURES:
 * // Constructors
 *    public ShowAllCarsPanel(CarSalesSystem carSys, JPanel dest)
 *
 * // Methods
 *    public void actionPerformed(ActionEvent ev)
 *    public void carsUpdated(CarUpdateEvent ev)
 *    public void stateChanged(ChangeEvent ev)
 *
 * COLLABORATORS:
 *    CarDetailComponents
 *
 * @version 1.0, 16 Oct 2004
 * @author Adam Black
 */
public class ShowAllCarsPanel extends JPanel implements ActionListener, ChangeListener
{
	private CarSalesSystem carSystem;
	private Car[] carList;
	private int currentIndex = 0;
	private JLabel headingLabel = new JLabel("Show all makes and models");
	private JButton previousButton = new JButton("Previous");
	private JButton nextButton = new JButton("Next");
	private JPanel buttonPanel = new JPanel();
	private CarDetailsComponents carComponents = new CarDetailsComponents();
	private boolean carsUpdated = false;

	/**
	 * @param carSys links to a CarSalesSystem object
     * @param dest where the panel will be displayed on the main frame
     */
	public ShowAllCarsPanel(CarSalesSystem carSys)
	{
		carSystem = carSys;
		carList = carSystem.getAllCars();

		if (carList.length > 0)
			carComponents.displayDetails(carList[0]);

		carSys.addCarUpdateListener(this);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		previousButton.addActionListener(this);
		nextButton.addActionListener(this);
		headingLabel.setAlignmentX(0.5f);

		buttonPanel.add(previousButton);
		buttonPanel.add(nextButton);

		add(Box.createVerticalStrut(10));
		add(headingLabel);
		add(Box.createVerticalStrut(15));
		carComponents.add(buttonPanel, "Center");
		add(carComponents);

		carList = carSystem.getAllCars();
	}

	/**
	 * check for button clicks
	 *
	 * @param ev ActionEvent object
	 */
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getSource() == previousButton)
			previousButtonClicked();
		else if (ev.getSource() == nextButton)
			nextButtonClicked();
	}

	/**
	 * this method is invoked when a car has been added to the system.
	 *
	 * @param ev CarUpdateEvent Object
	 */
	public void carsUpdated(CarUpdateEvent ev)
	{
		if (ev.getSource() == carSystem)
		{
			// remember the cars have been updated
			carsUpdated = true;
		}
	}

	/**
	 * when the tab on the main frame gets changed over to this one, we may have to update the
	 * car list with the latest version
	 *
	 * @param ev ChangeEvent object
	 */
	public void stateChanged(ChangeEvent ev)
	{
		// source is a JTabbedPane
		if (ev.getSource() instanceof JTabbedPane)
		{
			JTabbedPane tab = (JTabbedPane)ev.getSource();
			// if selected tab index is the ShowAllCarsPanel, which is this panel
			if (tab.getSelectedIndex() == 2)
			{
				// if the cars have been updated since last time
				if (carsUpdated)
				{
					carList = carSystem.getAllCars();
					if (!(carList == null))
						carComponents.displayDetails(carList[currentIndex]);
					// next time don't bother updating the car list unless a car is added again
					carsUpdated = false;
				}
			}
		}
	}

	/**
	 * display next item in index, otherwise display no next item message dialog
	 */
	private void nextButtonClicked()
	{
		if (currentIndex < carList.length - 1)
		{
			currentIndex++;
			carComponents.displayDetails(carList[currentIndex]);
		}
		else
			JOptionPane.showMessageDialog(carSystem, "You can't navigate any further", "Alert", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * display previous item in index, otherwise display no next item message dialog
	 */
	private void previousButtonClicked()
	{
		if (currentIndex > 0)
		{
			currentIndex--;
			carComponents.displayDetails(carList[currentIndex]);
		}
		else
			JOptionPane.showMessageDialog(carSystem, "You can't navigate any further", "Alert", JOptionPane.ERROR_MESSAGE);
	}
}