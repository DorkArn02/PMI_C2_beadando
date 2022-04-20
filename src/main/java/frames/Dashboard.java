package frames;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import utils.XmlHandler2;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;
import javax.swing.border.BevelBorder;

import static utils.LanguageUtils.*;

/**
 * This is a JFrame where you can navigate between frames
 */
public class Dashboard extends JFrame {
	private final AddPatient a;
	private final AddDisease ad;
	private final DeletePatient dp;
	private final EditPatient ep;
	private final ViewMedicalHistory vmh;
	private final ListExport pm;
	private final Logger logger = Logger.getLogger(Dashboard.class.getName());

	/**
	 *
	 * @param xmlHandler2 Getting the Xml Handler Class from MainApp
	 */
	public Dashboard(XmlHandler2 xmlHandler2) throws IOException {;
		setResizable(false);

		getClass().getResource("");
		InputStream medical_history_icon = getClass().getResourceAsStream("/outline_history_black_24dp.png");
		InputStream add_disease_icon = getClass().getResourceAsStream("/outline_medication_black_24dp.png");
		InputStream list_export_icon = getClass().getResourceAsStream("/outline_add_black_24dp.png");
		InputStream add_patient_icon = getClass().getResourceAsStream("/outline_person_add_black_24dp.png");
		InputStream delete_patient_icon = getClass().getResourceAsStream("/outline_person_remove_black_24dp.png");
		InputStream edit_patient_icon = getClass().getResourceAsStream("/outline_edit_black_24dp.png");
		InputStream background_image = getClass().getResourceAsStream("/medical_bck.jpg");
		
		setTitle(DASHBOARD_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 536);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		a = new AddPatient(xmlHandler2);
		ad = new AddDisease(xmlHandler2);
		dp = new DeletePatient(xmlHandler2);
		ep = new EditPatient(xmlHandler2);
		vmh = new ViewMedicalHistory(xmlHandler2);
		pm = new ListExport(xmlHandler2);

		// Medical history button
		JButton btnKrtrtnet = new JButton(MEDICAL_HISTORY);
		btnKrtrtnet.addActionListener(e -> {
			if(!vmh.isVisible()) {
				vmh.setVisible(true);
				logger.info("Medical history window opened!");
			}
		});

		btnKrtrtnet.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnKrtrtnet.setIcon(new ImageIcon(ImageIO.read(medical_history_icon)));
		btnKrtrtnet.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnKrtrtnet.setBounds(392, 315, 241, 100);
     	contentPane.add(btnKrtrtnet);

		 // List / export button
		JButton btnLista = new JButton(LIST_EXPORT);
		btnLista.addActionListener(e -> {
			if(!pm.isVisible()) {
				pm.setVisible(true);
				logger.info("List / export window opened!");
			}
		});
		btnLista.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnLista.setIcon(new ImageIcon(ImageIO.read(list_export_icon)));
		btnLista.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnLista.setBounds(392, 204, 241, 100);
		contentPane.add(btnLista);

		// Add new diagnosis button
		JButton btnBetegsgRgztse = new JButton(ADD_NEW_DIAGNOSIS);
		btnBetegsgRgztse.addActionListener(e -> {
			if(!ad.isVisible()) {
				ad.setVisible(true);
				logger.info("Add diagnosis window opened!");
			}
		});

		btnBetegsgRgztse.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnBetegsgRgztse.setIcon(new ImageIcon(ImageIO.read(add_disease_icon)));
		btnBetegsgRgztse.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBetegsgRgztse.setBounds(392, 93, 241, 100);
		contentPane.add(btnBetegsgRgztse);

		// Edit patient data button
		JButton btnPciensAdatmdosts = new JButton(EDIT_PATIENT_DATA);
		btnPciensAdatmdosts.addActionListener(e -> {
			if(!ep.isVisible())
			{
				ep.setVisible(true);
				logger.info("Edit patient window opened!");
			}
		});

		btnPciensAdatmdosts.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnPciensAdatmdosts.setIcon(new ImageIcon(ImageIO.read(edit_patient_icon)));
		btnPciensAdatmdosts.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnPciensAdatmdosts.setBounds(10, 315, 241, 100);
		contentPane.add(btnPciensAdatmdosts);

		// Delete patient data button
		JButton btnPciensTrlse = new JButton(DELETE_PATIENT_DATA);
		btnPciensTrlse.addActionListener(e -> {
			if(!dp.isVisible()) {
				dp.setVisible(true);
				logger.info("Delete patient data window opened!");
			}
		});
		btnPciensTrlse.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnPciensTrlse.setIcon(new ImageIcon(ImageIO.read(delete_patient_icon)));
		btnPciensTrlse.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnPciensTrlse.setBounds(10, 204, 241, 100);
		contentPane.add(btnPciensTrlse);

		JLabel lblHeading = new JLabel(MAIN_TITLE);
		lblHeading.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblHeading.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeading.setBounds(0, 0, 674, 52);
		contentPane.add(lblHeading);

		// Add new patient button
		JButton btnNewButton = new JButton(ADD_NEW_PATIENT);
		btnNewButton.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnNewButton.addActionListener(e -> {
			if(!a.isVisible()) {
				a.setVisible(true);
				logger.info("Add new patient window opened!");
			}
		});

		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setIcon(new ImageIcon(ImageIO.read(add_patient_icon)));
		btnNewButton.setBounds(10, 93, 241, 100);
		contentPane.add(btnNewButton);

		ImageIcon icon = new ImageIcon(ImageIO.read(background_image));

		JLabel imgBackground = new JLabel(icon);
		imgBackground.setLocation(0, 0);
		imgBackground.setSize(684, 505);
		getContentPane().add(imgBackground);

		// Window close confirm dialog
		addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
			    int confirmed = JOptionPane.showConfirmDialog(null, 
			        CONFIRM_EXIT, EXIT_LABEL,
			        JOptionPane.YES_NO_OPTION);

			    if (confirmed == JOptionPane.YES_OPTION) {
			      dispose();
			    }else {
			    	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			    }
			  }
			});
		
	}	
}
