import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.awt.GridBagLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class ventanaPrincipal extends JFrame {

	private JPanel contentPane;
	private JTable tablaCSV;
	private JTable tablaFrecuencia;

	public static void main(String[] args) 
	{
		LectorCSV lect = new LectorCSV();
		DatosCSV datos = new DatosCSV();
		lect.cargarCSV("SacramentocrimeJanuary2006.csv", datos);
		datos.escribirArchivoString("ejemplo.txt");
		datos.escribirArchivoCSV("ejemplo.csv");
				
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ventanaPrincipal frame = new ventanaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ventanaPrincipal() {
		setTitle("Proyecto MIneria");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 350);
		
		/*Iconos*/
		Icon iconoAbrir = new ImageIcon("src/iconoAbrir.png");
		Icon iconoGuardar = new ImageIcon("src/iconoGuardar.png");
		Icon iconoSalir = new ImageIcon("src/iconoSalir.png");
		
		/*Barra de menú*/
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		/*Menú Archivo*/
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		/*Elementos del menú Archivo*/
		
		JMenuItem mntmAbrir = new JMenuItem("Leer",iconoAbrir);
		mnArchivo.add(mntmAbrir);
	
		JMenuItem mntmGuardar = new JMenuItem("Guardar",iconoGuardar);
		mnArchivo.add(mntmGuardar);
		
		JMenuItem mntmSalir = new JMenuItem("Salir",iconoSalir);
		mnArchivo.add(mntmSalir);
		
		JMenu mnPreprocesamiento = new JMenu("Preprocesamiento");
		menuBar.add(mnPreprocesamiento);
		
		JMenuItem mntmTablaDeFrecuencia = new JMenuItem("Tabla de Frecuencia");
		mnPreprocesamiento.add(mntmTablaDeFrecuencia);
		
		JMenu mnDatamining = new JMenu("DataMining");
		menuBar.add(mnDatamining);
		//mntmSalir.addActionListener((ActionEvent event)->{
			//System.exit(0);
		//});
		
		/*Grid Principal*/
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		/*ComboboxClases*/
		JComboBox comboBoxClase = new JComboBox();
		
		JScrollPane scrollPaneCSV = new JScrollPane();
		
		JScrollPane scrollPaneFrecuencia = new JScrollPane();
		
		JLabel lblClase = new JLabel("Clase:");
		
		JButton btnAadir = new JButton("Añadir");
		
		JButton btnBorrar = new JButton("Borrar");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(54)
									.addComponent(lblClase)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(comboBoxClase, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(66)
									.addComponent(btnAadir)
									.addGap(18)
									.addComponent(btnBorrar)))
							.addGap(334)
							.addComponent(scrollPaneCSV, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(421)
							.addComponent(scrollPaneFrecuencia, GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(19)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPaneCSV, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(comboBoxClase, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblClase))
							.addPreferredGap(ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnAadir)
								.addComponent(btnBorrar))
							.addGap(30)))
					.addGap(7)
					.addComponent(scrollPaneFrecuencia, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
		);
		
		tablaFrecuencia = new JTable();
		scrollPaneFrecuencia.setViewportView(tablaFrecuencia);
		
		tablaCSV = new JTable();
		scrollPaneCSV.setViewportView(tablaCSV);
		contentPane.setLayout(gl_contentPane);
	}
}
