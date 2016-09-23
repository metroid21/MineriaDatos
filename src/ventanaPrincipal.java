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
import javax.swing.JTextField;

public class ventanaPrincipal<E> extends JFrame {

	private JPanel contentPane;
	private JTable tablaCSV;
	private JTable tablaFrecuencia;
	private JTextField textFieldNuevoAtributo;

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
		setBounds(100, 100, 800, 398);
		
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
		
		JScrollPane scrollPaneCSV = new JScrollPane();
		
		JScrollPane scrollPaneFrecuencia = new JScrollPane();
		
		JPanel panel = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPaneFrecuencia, GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
						.addComponent(scrollPaneCSV, GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(19)
							.addComponent(scrollPaneCSV, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPaneFrecuencia, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)))
					.addGap(12))
		);
		
		JLabel lblClase = new JLabel("Clase:");
		
		JComboBox comboBoxClases = new JComboBox();
		
		JButton btnAadir = new JButton("Añadir");
		
		JButton btnQuitar = new JButton("Quitar");
		
		JScrollPane scrollPaneAtributos = new JScrollPane();
		
		JButton btnActualizar = new JButton("Actualizar");
		
		textFieldNuevoAtributo = new JTextField();
		textFieldNuevoAtributo.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldNuevoAtributo, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
						.addComponent(scrollPaneAtributos, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(lblClase)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(comboBoxClases, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(16)
								.addComponent(btnAadir)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnQuitar))))
					.addGap(92))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(47)
					.addComponent(btnActualizar)
					.addContainerGap(65, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(24)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblClase)
						.addComponent(comboBoxClases, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnQuitar)
						.addComponent(btnAadir))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPaneAtributos, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textFieldNuevoAtributo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnActualizar)
					.addGap(5))
		);
		
		JList<? extends E> listaAtributos = new JList();
		scrollPaneAtributos.setViewportView(listaAtributos);
		panel.setLayout(gl_panel);
		
		
		tablaFrecuencia = new JTable();
		scrollPaneFrecuencia.setViewportView(tablaFrecuencia);
		
		tablaCSV = new JTable();
		scrollPaneCSV.setViewportView(tablaCSV);
		contentPane.setLayout(gl_contentPane);
	}
}
