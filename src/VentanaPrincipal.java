import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableRowSorter;
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

import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JTextPane;

public class VentanaPrincipal<E> extends JFrame {

	private JPanel contentPane;
	private DatosCSV datos = new DatosCSV();
	private DefaultTableModel modeloTablaCSV;
	private JTable tablaFrecuencia;
	private JTextField textFieldNuevoAtributo;
	private JFileChooser chooser = new JFileChooser();
	private JTable tablaCSV;
	private JScrollPane scrollPaneCSV;
	private JComboBox comboBoxClases;
	private JScrollPane scrollPaneFrecuencia;
	private JList<? extends E> listaAtributos;
	private JScrollPane scrollPaneAtributos;
	private ListDataListener listDataListener;
	private JTable tableCalculo;

	public static void main(String[] args) 
	{					
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void actualizarModelos()
	{
		 //Cargamos los cambios en un modelo de tabla
		 modeloTablaCSV = datos.getDatosAsTableModel();
		 tablaCSV.setModel(modeloTablaCSV);
		 tablaCSV.setPreferredScrollableViewportSize(new Dimension(500, 70));
		 scrollPaneCSV.setViewportView(tablaCSV);
		 
		 //Actualizamos el Select de la Clase
		 DefaultComboBoxModel aModel = new DefaultComboBoxModel(datos.getAtributosAsStringArray());
		 aModel.insertElementAt("Selecionar", 0);
		 aModel.setSelectedItem("Selecionar");
		 comboBoxClases.setModel(aModel);
		 
		 //Actualizamos la lista de los Atributos
		 DefaultListModel lModel = new DefaultListModel();
		 
		 for (int i = 0; i < datos.getAtributos().getNodos().size(); i++)
		 {
			 if (! datos.getAtributos().getNodos().get(i).isEliminado())
			 {
				 lModel.addElement(datos.getAtributos().getNodos().get(i).getValor());
			 }
		 }
		 
		 listaAtributos.setModel(lModel);
		 scrollPaneAtributos.setViewportView(listaAtributos);
		 
		 //Actualizamos el contador de Registros de la Tabla de Frecuencia
         DefaultTableModel modelo = datos.getTablaFrecuencia("");
         tablaFrecuencia.setModel(modelo);
         tablaFrecuencia.setPreferredScrollableViewportSize(new Dimension(500, 70));
         tablaFrecuencia.setAutoCreateRowSorter(true);        		
         scrollPaneFrecuencia.setViewportView(tablaFrecuencia);                               	
	}

	public VentanaPrincipal() 
	{
		setTitle("Proyecto Mineria");
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
		mntmAbrir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				//Creamos la estructura para abrir archivos
				 FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos CSV", "csv");
				 chooser.setCurrentDirectory(new java.io.File("."));
				 chooser.setDialogTitle("Leer CSV");
				 chooser.setApproveButtonText("Leer");
				 chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				 chooser.setFileFilter(filter);
				 
				 if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
				 {
					 //Leemos el archivo csv
					 LectorCSV lect = new LectorCSV();
					 lect.cargarCSV(chooser.getSelectedFile().getAbsolutePath(), datos);
					 
					 datos.setNombreArchivo(chooser.getSelectedFile().getName());
					 datos.setCaminoArchivo(chooser.getSelectedFile().getAbsolutePath());
					 			
					 actualizarModelos();
					 
		             DefaultTableModel modelo = datos.getTablaFrecuencia("");
	                 tablaFrecuencia.setModel(modelo);
	                 tablaFrecuencia.setPreferredScrollableViewportSize(new Dimension(500, 70));
	                 tablaFrecuencia.setAutoCreateRowSorter(true);        		
	                 scrollPaneFrecuencia.setViewportView(tablaFrecuencia);                               	

					 System.out.println("Archivo Leido");
					 
				 } 
				 else 
				 {
					System.out.println("No seleccion ");
				 }
			}
		});
		mnArchivo.add(mntmAbrir);
	
		JMenuItem mntmGuardar = new JMenuItem("Guardar",iconoGuardar);
		mntmGuardar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				if (!datos.getAtributos().getNodos().isEmpty())
				{
					 chooser.setCurrentDirectory(new java.io.File("."));
					 chooser.setDialogTitle("Guardar CSV");
					 chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					 chooser.setApproveButtonText("Guardar");
					 
					 if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
					 {				 				
						 String archivo = chooser.getSelectedFile().getAbsolutePath();
						 					 
						 if (!archivo.endsWith(".csv") && !archivo.endsWith(".dat"))
						 {
							 archivo += ".csv";
						 }
						 
						 if (archivo.endsWith(".dat"))
						 {
							 datos.escribirArchivoString(archivo);
						 }
						 else
						 {
							 datos.escribirArchivoCSV(archivo);
						 }
						 
						 
						 System.out.println("Archivo Guardado");
					 } 
					 else 
					 {
						System.out.println("No seleccion");
					 }
				}
			}
		});
		mnArchivo.add(mntmGuardar);
		
		JMenuItem mntmSalir = new JMenuItem("Salir",iconoSalir);
		mntmSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				System.exit(0);
			}
		});
		mnArchivo.add(mntmSalir);
		
		JMenu mnPreprocesamiento = new JMenu("Preprocesamiento");
		menuBar.add(mnPreprocesamiento);
		
		JMenuItem mntmTablaDeFrecuencia = new JMenuItem("Tabla de Frecuencia");
		mnPreprocesamiento.add(mntmTablaDeFrecuencia);
		
		JMenu mnDatamining = new JMenu("DataMining");
		menuBar.add(mnDatamining);
		
		JMenuItem mntmIngresarExprecionregular = new JMenuItem("Ingresar ExprecionRegular");
		mntmIngresarExprecionregular.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							ExpresionRegular frame = new ExpresionRegular();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		mnDatamining.add(mntmIngresarExprecionregular);
		
		/*Grid Principal*/
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		scrollPaneCSV = new JScrollPane();
		
		scrollPaneFrecuencia = new JScrollPane();
		
		JPanel panel = new JPanel();
		
		JScrollPane scrollPaneCalculo = new JScrollPane();
		
		JTextPane txtpnMensajes = new JTextPane();
		txtpnMensajes.setText("Mesajes Consola");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(txtpnMensajes, GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPaneCSV, GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(scrollPaneFrecuencia, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(scrollPaneCalculo, GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(txtpnMensajes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPaneCSV, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPaneCalculo, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPaneFrecuencia, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE))))
					.addGap(12))
		);
		
		tableCalculo = new JTable();
		scrollPaneCalculo.setViewportView(tableCalculo);
		
		JLabel lblClase = new JLabel("Clase:");
		
		comboBoxClases = new JComboBox();
		comboBoxClases.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent event) 
            {
                JComboBox comboBox = (JComboBox) event.getSource();
                int selected = comboBox.getSelectedIndex()-1;
                
                DefaultTableModel modelo = datos.getTablaFrecuencia(comboBox.getSelectedItem().toString());
                tablaFrecuencia.setModel(modelo);
                tablaFrecuencia.setPreferredScrollableViewportSize(new Dimension(500, 70));
                tablaFrecuencia.setAutoCreateRowSorter(true);        		
        		scrollPaneFrecuencia.setViewportView(tablaFrecuencia);                               	
             }
        });
		
		JButton btnAadir = new JButton("Agregar");
		btnAadir.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (textFieldNuevoAtributo.getText() != "")
				{
					datos.agregarAtributo(textFieldNuevoAtributo.getText());
					textFieldNuevoAtributo.setText("");
					actualizarModelos();
				}
			}
		});
		
		JButton btnQuitar = new JButton("Quitar");
		btnQuitar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (listaAtributos.getSelectedValue() != "")
				{
					datos.eliminarAtributo(listaAtributos.getSelectedValue().toString());
					actualizarModelos();
				}
			}
		});
		
		scrollPaneAtributos = new JScrollPane();
		
		contentPane.setLayout(gl_contentPane);
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modeloTablaCSV.fireTableDataChanged();
			}
		});
		
		textFieldNuevoAtributo = new JTextField();
		textFieldNuevoAtributo.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(55)
					.addComponent(btnActualizar)
					.addContainerGap(57, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(textFieldNuevoAtributo, GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
					.addGap(24))
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPaneAtributos, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addComponent(btnAadir)
							.addGap(12)
							.addComponent(btnQuitar))
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addComponent(lblClase)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(comboBoxClases, 0, 119, Short.MAX_VALUE)))
					.addGap(24))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(24)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblClase)
						.addComponent(comboBoxClases, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPaneAtributos)
					.addGap(10)
					.addComponent(textFieldNuevoAtributo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnQuitar)
						.addComponent(btnAadir))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnActualizar)
					.addContainerGap())
		);
		
		listaAtributos = new JList();
		scrollPaneAtributos.setViewportView(listaAtributos);
		panel.setLayout(gl_panel);
		
		tablaFrecuencia = new JTable();
		scrollPaneFrecuencia.setViewportView(tablaFrecuencia);

		tablaCSV = new JTable();
		tablaCSV.addPropertyChangeListener(new PropertyChangeListener() 
		{
			public void propertyChange(PropertyChangeEvent evt) 
			{
				if (evt.getPropertyName().equals("tableCellEditor"))
				{
					String source = evt.getSource().toString();
					
					int changeColumIndexB = source.indexOf(",editingColumn=");
					int changeRowIndexB   = source.indexOf(",editingRow=");
					int changeGridColorB  = source.indexOf(",gridColor=");

					int changeColumIndexE = changeColumIndexB + 15;
					int changeRowIndexE   = changeRowIndexB   + 12;

					String changeColum = source.substring(changeColumIndexE, changeRowIndexB);
					String changeRow   = source.substring(changeRowIndexE, changeGridColorB);
					
					int col = Integer.parseInt(changeColum);
					int row = Integer.parseInt(changeRow);
					
					if (row >= 0 && col >= 0)
					{						
						String val = tablaCSV.getValueAt(row, col).toString();
						
						datos.actualizarFromCellJTable(row, col, val);
						
						System.out.println(changeColum + " " + changeRow);
						System.out.println(val);
					}
				}
			}
		});
		scrollPaneCSV.setViewportView(tablaCSV);
	}
}
