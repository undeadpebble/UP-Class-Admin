package ClassAdminBackEnd;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.tmatesoft.sqljet.core.SqlJetException;

import ClassAdminFrontEnd.BackgroundGradientPanel;
import ClassAdminFrontEnd.FadePanel;
import ClassAdminFrontEnd.ReflectionButton;
import ClassAdminFrontEnd.ReflectionButtonWithLabel;
import ClassAdminFrontEnd.ReflectionButtonXLS;
import ClassAdminFrontEnd.ReflectionImagePanel;
import Frames.Frame;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.read.biff.WorkbookParser;

public class XlsImport extends FileImport {
	public ArrayList arr = null;
	private JComboBox cmbSheet = new JComboBox();
	private boolean b = false;
	private final JComboBox cmbHeaders = new JComboBox();
	private File reader;
	private Workbook w;
	private Sheet sheet = null;
	private int headerLine = -1;
	private FileHandler fileHandler;
	private final Object monitor = new Object();
	private JDialog dialog = null;
	private Frame frame;
	private BackgroundGradientPanel backgroundPanel;
	private ReflectionImagePanel containerSelectHeader;

	public XlsImport(Frame frame_) {
		fileHandler = FileHandler.get();
		fileHandler.setXLSImport(this);
		frame = frame_;
	}

	public Boolean fileExists(String in) {
		try {
			reader = new File(in);
			createWorkbook(reader);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void createWorkbook(File f) throws IOException {
		try {
			w = Workbook.getWorkbook(f);
		} catch (BiffException e) {
			// System.out.println("1");
			e.printStackTrace();
		} catch (IOException e) {
			// System.out.println("2");
			e.printStackTrace();
		}
	}

	public void setSheet(int s) {
		sheet = w.getSheet(s);
	}

	public void setHeaderLine(int s) {
		headerLine = s;
	}

	public int getLines() {
		return sheet.getRows();
	}

	private ArrayList importData() {

		ArrayList data = new ArrayList();
		ArrayList headers = null;
		ArrayList records = null;

		if (sheet == null) {
			// System.out.println("No sheet selected -- selecting first sheet for import data");
			sheet = w.getSheet(0);
		}
		if (headerLine == -1) {
			// System.out.println("No headerline selected -- selecting first line for headers");
			headerLine = 0;
		}

		headers = new ArrayList();// get headers
		for (int i = 0; i < sheet.getColumns(); i++) {
			Cell cell = sheet.getCell(i, headerLine);
			headers.add(cell.getContents()); // get header contents and add to
												// header arraylist
			cell = null;
		}
		data.add(headers); // add headers to all data

		records = new ArrayList(); // get records
		for (int j = headerLine + 1; j < sheet.getRows(); j++) {
			ArrayList record = new ArrayList();
			for (int i = 0; i < sheet.getColumns(); i++) {
				Cell cell = sheet.getCell(i, j);
				String pp = cell.getContents(); // get and add cell contents to
												// record
				record.add(pp);
			}
			records.add(record); // add record to records arraylist
			record = null;
		}
		data.add(records); // add records to all data
		records = null;

		return data;
	}

	public ArrayList recordData() {

		arr = null;
		try {
			createImport();
		} catch (SqlJetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}

	public void createImport() throws SqlJetException, IOException {

		dialog = new JDialog(frame, true);
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setBounds(0, 0, 700, 625);
		dialog.setResizable(false);
		dialog.setTitle("Specify Header");

		// set frame icon
		Image icon = Toolkit.getDefaultToolkit().getImage("Logo.png");
		dialog.setTitle("Specify Header");

		
		dialog.setIconImage(icon);

		// create background panel
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		dialog.setContentPane(contentPane);

		backgroundPanel = new BackgroundGradientPanel(contentPane);
		backgroundPanel.setBounds(0, 0, dialog.getWidth(), dialog.getHeight());
		contentPane.add(backgroundPanel);
		backgroundPanel.setLayout(null);

		ReflectionImagePanel containerSelectHeader = new ReflectionImagePanel(ImageIO.read(getClass().getResource(
				"/ClassAdminFrontEnd/resources/SelectHeaderLine.png")));

		ReflectionButtonXLS importButton = new ReflectionButtonXLS(ImageIO.read(getClass().getResource(
				"/ClassAdminFrontEnd/resources/XLSImport.png")));

		containerSelectHeader.setBounds(0, 20, 700, 88);
		backgroundPanel.add(containerSelectHeader);
		importButton.setBounds(585, 480, 70, 80);
		backgroundPanel.add(importButton);

		// Set frame to center of screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int _w = dialog.getSize().width;
		int _h = dialog.getSize().height;
		int x = (dim.width - _w) / 2;
		int y = (dim.height - _h) / 2;
		dialog.setLocation(x, y);

		JLabel lblSheets = new JLabel("Sheet:");
		JLabel lblHeader = new JLabel("HeaderLine:");
		JPanel pnlSheets = new JPanel();
		JPanel pnlHeaders = new JPanel();

		pnlSheets.setOpaque(false);
		pnlHeaders.setOpaque(false);

		lblSheets.setForeground(new Color(0xEDEDED));
		lblHeader.setForeground(new Color(0xEDEDED));

		importButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				setSheet((Integer) cmbSheet.getSelectedItem() - 1);
				setHeaderLine((Integer) cmbHeaders.getSelectedItem() - 1);
				arr = importData();
				dialog.dispose();

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		final JTextArea textArea2 = new JTextArea();
		textArea2.setEditable(false);
		// textArea2.setEnabled(false);
		textArea2.setFocusable(false);

		Sheet s = null;
		int sheet = -1;
		int sheetcount = w.getNumberOfSheets();

		for (int k = 0; k < sheetcount; k++) { // loop through sheets
			sheet = k + 1;
			s = w.getSheet(0);
			cmbSheet.addItem(sheet);
		}
		printSheet(0, textArea2, cmbHeaders);

		cmbSheet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				int selectedSheet = (Integer) cb.getSelectedItem();
				printSheet(selectedSheet - 1, textArea2, cmbHeaders);
				textArea2.setCaretPosition(0);
			}
		});

		textArea2.setPreferredSize(textArea2.getPreferredSize());

		JScrollPane scrollPane = new JScrollPane(textArea2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pnlSheets.add(lblSheets);
		pnlSheets.add(cmbSheet);
		pnlHeaders.add(lblHeader);
		pnlHeaders.add(cmbHeaders);

		textArea2.setCaretPosition(0);
		scrollPane.setBounds(40, 110, 500, 420);
		pnlSheets.setBounds(574, 105, 70, 55);
		pnlHeaders.setBounds(574, 160, 70, 55);

		backgroundPanel.add(scrollPane);
		backgroundPanel.add(pnlSheets);
		backgroundPanel.add(pnlHeaders);

		dialog.setVisible(true);
		
		dialog.setVisible(true);
	}

	public void printAllSheets() {

		Sheet s = null;
		int sheet = -1;
		int sheetcount = w.getNumberOfSheets();

		for (int k = 0; k < sheetcount; k++) { // loop through sheets
			sheet = k + 1;
			s = w.getSheet(k);
			// System.out.print("SHEET " + sheet + "\n");
			for (int j = 0; j < s.getRows(); j++) { // loop through rows
				for (int i = 0; i < s.getColumns(); i++) { // loop through
															// columns
					Cell cell = s.getCell(i, j);
					// System.out.print(cell.getContents() + "\t");
				}
				// System.out.print("\n");
			}
			// System.out.print("\n");
		}

	}

	public void printSheet(int sheet, JTextArea textArea, JComboBox cmb) {
		textArea.setText("");
		textArea.append("Line #\n");
		Sheet s = w.getSheet(sheet); // select sheet to be printed
		for (int j = 0; j < s.getRows(); j++) { // loop through rows
			textArea.append(j + 1 + "\t");
			cmb.addItem(j + 1);
			for (int i = 0; i < s.getColumns(); i++) { // loop through columns
				Cell cell = s.getCell(i, j);
				textArea.append(cell.getContents() + "\t");
				// System.out.print(cell.getContents() + "\t");
			}
			textArea.append("\n");
			// System.out.println(); // end of record
		}
	}

	public ArrayList getHeaders(ArrayList arr) // return header arraylist from
	// arraylist
	{
		ArrayList headers = (ArrayList) arr.get(0);
		return headers;
	}

	public ArrayList getRecords(ArrayList arr) // return record arraylist from
	// arraylist
	{
		ArrayList records = (ArrayList) arr.get(1);
		return records;
	}

	public ArrayList getRecord(ArrayList arr, int index) // return specified
	// record from
	// arraylist
	{
		ArrayList records = (ArrayList) arr.get(1); // get records arraylist
		ArrayList record = (ArrayList) records.get(index); // get specified
		// record in record
		// arraylist
		return record;
	}

	public String getRecordFieldValue(ArrayList arr, int recordIndex, int fieldIndex) // return
																						// specified
																						// field
																						// from
																						// record
																						// in
																						// file
	{
		ArrayList records = (ArrayList) arr.get(1); // get records arraylist
		ArrayList record = (ArrayList) records.get(recordIndex); // get
																	// specified
																	// record in
																	// records
																	// arraylist
		String field = (String) record.get(fieldIndex); // get specified field
		// in record
		return field;
	}

	public void printHeaders(ArrayList headers) // print all headers from
	// headers arraylist
	{
		for (int j = 0; j < headers.size(); j++) {
			// System.out.print(headers.get(j).toString() + "\t");
		}
		// System.out.println();
	}

	public void printRecords(ArrayList records) // print all records from
	// records arraylist
	{
		for (int i = 0; i < records.size(); i++) {
			ArrayList record = (ArrayList) records.get(i); // get record
			// arraylist from
			// records

			for (int j = 0; j < record.size(); j++) // get field from record
			// arraylist
			{
				// System.out.print(record.get(j).toString() + "\t"); // print
				// field
				// data
			}
			// System.out.println(); // new line for next record

		}
	}

	public void printRecord(ArrayList record) // print all information from
	// specified record
	{
		for (int j = 0; j < record.size(); j++) {
			// System.out.print(record.get(j).toString() + "\t"); // print field
			// data
		}
		// System.out.println(); // new line XD
	}

	public void print(ArrayList in) // print entire structure
	{

		ArrayList headers = (ArrayList) in.get(0); // get header arraylist
		ArrayList records = (ArrayList) in.get(1); // get records arraylist

		for (int j = 0; j < headers.size(); j++) // print all headers on header
		// arraylist
		{
			// System.out.print(headers.get(j).toString() + "\t");
		}
		// System.out.println();

		for (int i = 0; i < records.size(); i++) // get each record arraylist in
		// records arraylist
		{
			ArrayList record = (ArrayList) records.get(i); // specify record

			for (int j = 0; j < record.size(); j++) // print all field data from
			// record arraylist
			{
				// System.out.print(record.get(j).toString() + "\t");
			}
			// System.out.println(); // next record to follow

		}// get records
	}
}