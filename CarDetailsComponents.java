import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * This class contais the group of text fields representing the cars information visually
 * inside a panel.
 * @
 *
 * PUBLIC FEATURES:
 * // Constructors
 *    public CarDetailsComponents()
 *
 * // Methods
 *    public void clearTextFields()
 *    public void componentHidden(ComponentEvent ev)
 *    public void componentMoved(ComponentEvent ev)
 *    public void componentResized(ComponentEvent ev)
 *    public void componentShown(ComponentEvent ev)
 *    public void displayDetails(Car c)
 *    public JPanel getDetailsPanel()
 *    public String getInfoText()
 *    public String getKmText()
 *    public String getManufacturerText()
 *    public String getModelText()
 *    public String getPriceText()
 *    public String getYearText()
 *    public void setFocusManufacturerTextField()
 *
 * COLLABORATORS:
 *
 * @version 1.0, 16 Oct 2004
 * @author Adam Black
 */
public class CarDetailsComponents extends JPanel implements ComponentListener
{
	private JLabel manufacturerLabel = new JLabel("Manufacturer");
	private JLabel yearLabel = new JLabel("Year");
	private JLabel modelLabel = new JLabel("Model");
	private JLabel priceLabel = new JLabel("Price");
	private JLabel kmLabel = new JLabel("Km Traveled");
	private JLabel infoLabel = new JLabel("Extra Information");
	private JTextField manufacturerTextField = new JTextField();
	private JTextField yearTextField = new JTextField();
	private JTextField modelTextField = new JTextField();
	private JTextField priceTextField = new JTextField();
	private JTextField kmTextField = new JTextField();
	private JTextArea infoTextArea = new JTextArea(4, 0);

	private final int divFactor = 27;

	/**
	 * set up a new CarDetailComponents object and return a reference to the object which is a kind
	 * of panel
	 */
	public CarDetailsComponents()
	{
		Insets currentInsets;
		GridBagConstraints gridBagConstraints;
		setLayout(new BorderLayout(0, 20));
		JPanel compPanel = new JPanel(new GridBagLayout());
		String currentFont = manufacturerLabel.getFont().getName();
		currentInsets =  new Insets(0, 10, 0, 30);

		manufacturerLabel.setFont(new Font(currentFont, Font.BOLD, 12));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = currentInsets;
        compPanel.add(manufacturerLabel, gridBagConstraints);

        yearLabel.setFont(new Font(currentFont, Font.BOLD, 12));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = currentInsets;
        compPanel.add(yearLabel, gridBagConstraints);

        modelLabel.setFont(new Font(currentFont, Font.BOLD, 12));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = currentInsets;
        compPanel.add(modelLabel, gridBagConstraints);

        priceLabel.setFont(new Font(currentFont, Font.BOLD, 12));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = currentInsets;
        compPanel.add(priceLabel, gridBagConstraints);

        kmLabel.setFont(new Font(currentFont, Font.BOLD, 12));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = currentInsets;
        compPanel.add(kmLabel, gridBagConstraints);

        infoLabel.setFont(new Font(currentFont, Font.BOLD, 12));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = currentInsets;
        compPanel.add(infoLabel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        compPanel.add(manufacturerTextField, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        compPanel.add(yearTextField, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        compPanel.add(modelTextField, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        compPanel.add(priceTextField, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
		gridBagConstraints.anchor = gridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        compPanel.add(kmTextField, gridBagConstraints);

		infoTextArea.setLineWrap(true);
		currentInsets = new Insets(2, 20, 0, 20);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = gridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
        compPanel.add(new JScrollPane(infoTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), gridBagConstraints);

		// this listens for resize events
		addComponentListener(this);
        add(compPanel, "North");
	}

	/**
	 * clear all the text fields
	 */
	public void clearTextFields()
	{
		manufacturerTextField.setText("");
		yearTextField.setText("");
		modelTextField.setText("");
		priceTextField.setText("");
		kmTextField.setText("");
		infoTextArea.setText("");
	}

	public void componentHidden(ComponentEvent ev) {}

	public void componentMoved(ComponentEvent ev) {}

	/**
	 * when the details panel is resized change the length of the text boxes
	 *
	 * @return ev ComponentEvent object
	 */
	public void componentResized(ComponentEvent ev)
	{
		if (ev.getSource() == this)
		{
			int width = getWidth();

			if (width >= 0)
			{
				/** these text fields had to be resized manually. Using insets didn't work for
				smaller areas of the panel. */
				manufacturerTextField.setColumns(width / divFactor);
				yearTextField.setColumns(width / divFactor);
				modelTextField.setColumns(width / divFactor);
				priceTextField.setColumns(width / divFactor);
				kmTextField.setColumns(width / divFactor);
				infoTextArea.setColumns((width / divFactor) + 3); // this text box is larger
			}
		}
	}

	public void componentShown(ComponentEvent ev){}

	/**
	 * display details about a car through the text box components
	 *
	 * @param c the car to display details about
	 */
	public void displayDetails(Car c)
	{
		manufacturerTextField.setText(c.getManufacturer());
		yearTextField.setText(Integer.toString(c.getYear()));
		modelTextField.setText(c.getModel());
		priceTextField.setText(Integer.toString(c.getPrice()));
		kmTextField.setText(Double.toString(c.getKilometers()));
		infoTextArea.setText(c.getInformation());
	}

	public String getInfoText()
	{
		return infoTextArea.getText();
	}

	public String getKmText()
	{
		return kmTextField.getText();
	}

	public String getManufacturerText()
	{
		return manufacturerTextField.getText();
	}

	public String getModelText()
	{
		return modelTextField.getText();
	}

	public String getPriceText()
	{
		return priceTextField.getText();
	}

	public String getYearText()
	{
		return yearTextField.getText();
	}

	/**
	 * set focus to the manufacturer text field. ie, put the cursor inside it
	 */
	public void setFocusManufacturerTextField()
	{
		manufacturerTextField.grabFocus();
	}
}
