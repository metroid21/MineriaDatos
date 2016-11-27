import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
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
import javax.swing.SwingConstants;
 
public class VentanaPrincipal extends JFrame {
 
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DatosCSV datos = new DatosCSV();
	private DefaultTableModel modeloTablaCSV;
	private JTable tablaFrecuencia;
	private JTextField textFieldNuevoAtributo;
	private JFileChooser chooser = new JFileChooser();
	private JTable tablaCSV;
	private JScrollPane scrollPaneCSV;
	private JComboBox<String> comboBoxClases;
	private JScrollPane scrollPaneFrecuencia;
	private JList<String> listaAtributos;
	private JScrollPane scrollPaneAtributos;
	private JTable tableCalculo;
	private JLabel txtEstado;
	private JMenuItem mntmIngresarExprecionregular;
	private ExpresionRegular ventanaExpresiones; 
	private VentanaAlgoritmos ventanaAlgoritmos;
	private VentanaTransformaciones ventanaTransformaciones;
	private VentanaCorrelacion ventanaCorrelacion;
	private VentanaLevenshtein ventanaLevenshtein;
	private JButton btnAgregarFila;
	private JButton btnEliminarFila;
	private JButton btnQuitar;
	private JButton btnAgregar;
	private JButton btnActualizar;
	private JMenu mnPreprocesamiento;
	private JMenu mnDatamining;

	
	public static void main(String[] args) 
	{					
		EventQueue.invokeLater(new Runnable() 
		{
			public void run()  
			{
				try 
				{					
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.pack();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	private void actualizarModelos()
	{
		listaAtributos.setEnabled(true);
		comboBoxClases.setEnabled(true);
		textFieldNuevoAtributo.setEnabled(true);
		btnAgregar.setEnabled(true);
		btnQuitar.setEnabled(true);
		btnActualizar.setEnabled(true);
		btnEliminarFila.setEnabled(true);
		btnAgregarFila.setEnabled(true);
		mnPreprocesamiento.setEnabled(true);
		mnDatamining.setEnabled(true);
		
		 //Cargamos los cambios en un modelo de tabla
		 modeloTablaCSV = datos.getDatosAsTableModel(true);
		 tablaCSV.setModel(modeloTablaCSV);
		 tablaCSV.setPreferredScrollableViewportSize(new Dimension(500, 70));
		 scrollPaneCSV.setViewportView(tablaCSV);
		 
		 //Actualizamos el Select de la Clase
		 DefaultComboBoxModel<String> aModel = new DefaultComboBoxModel<String>(datos.getAtributosAsStringArray());
		 aModel.insertElementAt("Selecionar", 0);
		 aModel.setSelectedItem("Selecionar");
		 comboBoxClases.setModel(aModel);

		 DefaultComboBoxModel<String> rModel = new DefaultComboBoxModel<String>(datos.getAtributosAsStringArray());
		 rModel.insertElementAt("Selecionar", 0);
		 rModel.setSelectedItem("Selecionar");
		 ventanaExpresiones.getComboBox().setModel(rModel);
		 ventanaExpresiones.setEnableBtn(false);

		 //Actualizamos la lista de los Atributos
		 DefaultListModel<String> lModel = new DefaultListModel<String>();
		 
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
		ventanaExpresiones = new ExpresionRegular();
		ventanaAlgoritmos = new VentanaAlgoritmos();
		ventanaTransformaciones= new VentanaTransformaciones();
		ventanaCorrelacion= new VentanaCorrelacion();
		ventanaLevenshtein = new VentanaLevenshtein();
		
		datos = null;
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
				txtEstado.setText("Cargando Archivo");
				
				//Creamos la estructura para abrir archivos
				 FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos CSV", "csv", "Archivos JL", "jl");
				 chooser.setCurrentDirectory(new java.io.File("."));
				 chooser.setDialogTitle("Leer CSV");
				 chooser.setApproveButtonText("Leer");
				 chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				 chooser.setFileFilter(filter);
				 
				 if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
				 {					 
					 //Leemos el archivo csv
					 LectorCSV lect = new LectorCSV();
					 
					 if (chooser.getSelectedFile().getAbsolutePath().endsWith("csv"))
					 {
						 datos = lect.cargarCSV(chooser.getSelectedFile().getAbsolutePath());
					 }
					 else
					 {
						 datos = lect.cargarCSVDelimitadores(chooser.getSelectedFile().getAbsolutePath());
					 }
					 
					 
					 if (datos != null)
					 {
						 datos.setNombreArchivo(chooser.getSelectedFile().getName());
						 datos.setCaminoArchivo(chooser.getSelectedFile().getAbsolutePath());
						 			
						 actualizarModelos();
						 
			             DefaultTableModel modelo = datos.getTablaFrecuencia("");
		                 tablaFrecuencia.setModel(modelo);
		                 tablaFrecuencia.setPreferredScrollableViewportSize(new Dimension(500, 70));
		                 tablaFrecuencia.setAutoCreateRowSorter(true);        		
		                 scrollPaneFrecuencia.setViewportView(tablaFrecuencia);   

		                 txtEstado.setText("Archivo Terminado"); 
					 }
					 else
					 {
						 txtEstado.setText("Sin Archivo para Cargar");
					 }
				 } 
				 else 
				 {
					 txtEstado.setText("Sin Archivo para Cargar");
				 }
			}
		});
		
		JMenuItem mntmNuevo = new JMenuItem("Nuevo", (Icon) null);
		mntmNuevo.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				datos = new DatosCSV();
				actualizarModelos();
			}
		});
		mnArchivo.add(mntmNuevo);
		mnArchivo.add(mntmAbrir);
	
		JMenuItem mntmGuardar = new JMenuItem("Guardar",iconoGuardar);
		mntmGuardar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				txtEstado.setText("Guardando Archivo");
				
				if (datos != null && !datos.getAtributos().getNodos().isEmpty())
				{
					chooser.setCurrentDirectory(new java.io.File("."));
					chooser.setDialogTitle("Guardar CSV");
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					chooser.setApproveButtonText("Guardar");
					 
					if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
					{				 				
						 String archivo = chooser.getSelectedFile().getAbsolutePath();
						 					 
						 if (!archivo.endsWith(".csv") && !archivo.endsWith(".dat") && !archivo.endsWith(".jl"))
						 {
							 archivo += ".csv";
						 }
						 
						 if (archivo.endsWith(".dat"))
						 {
							 datos.escribirArchivoString(archivo);
						 }
						 else if (archivo.endsWith(".jl"))
						 {
							 datos.escribirArchivoDelimitadores(archivo);
						 }
						 else
						 {
							 datos.escribirArchivoCSV(archivo);
						 }
						 
						 
						 txtEstado.setText("Archivo Guardado");
					 } 
					 else 
					 {
						 txtEstado.setText("Error en nombre de Archivo");
					 }
				}
				else 
				{
					txtEstado.setText("No hay datos para guardar");
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
		
		mnPreprocesamiento = new JMenu("Preprocesamiento");
		mnPreprocesamiento.setEnabled(false);
		menuBar.add(mnPreprocesamiento);
		
		mntmIngresarExprecionregular = new JMenuItem("Expresiones Regulares");
		mnPreprocesamiento.add(mntmIngresarExprecionregular);
		mntmIngresarExprecionregular.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				if (datos != null)
				{
					EventQueue.invokeLater(new Runnable() 
					{
						public void run() 
						{
							try
							{
								ventanaExpresiones.setVisible(true);
				                ventanaExpresiones.setDatos(datos);
							} 
							catch (Exception e) 
							{
								e.printStackTrace();
							}
						}
					});
				}
				else
				{
					txtEstado.setText("No hay Datos para sacar Gramaticas");
				}
			}
		});
		
		mnDatamining = new JMenu("DataMining");
		mnDatamining.setEnabled(false);
		menuBar.add(mnDatamining);
		
		JMenuItem mntmCorrelacion = new JMenuItem("Correlaci\u00F3n");
		mnDatamining.add(mntmCorrelacion);
		mntmCorrelacion.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						try{
							ventanaCorrelacion.setVisible(true);
							ventanaCorrelacion.setDatos(datos);
							ventanaCorrelacion.refrescarAtributos();
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		JMenuItem mntmAlgoritmos = new JMenuItem("Algoritmos");
		mntmAlgoritmos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							
							if (datos.getNombreClase() != "")
							{
								ventanaAlgoritmos.setVisible(true);
								ventanaAlgoritmos.setDatosOrigen(datos);
								ventanaAlgoritmos.inicializar();
								txtEstado.setText("Abriendo Ventana de Algoritmos");
							}
							else
							{
								txtEstado.setText("Selecione una clase primero");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		mnDatamining.add(mntmAlgoritmos);
		
		JMenuItem mntmTransformaciones = new JMenuItem("Transformaciones");
		mntmTransformaciones.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							ventanaTransformaciones.setVisible(true);
							ventanaTransformaciones.setDatos(datos);
							ventanaTransformaciones.refrescarAtributos();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		mnDatamining.add(mntmTransformaciones);
		
		JMenuItem mntmLevenshtein = new JMenuItem("Levenshtein");
		mntmLevenshtein.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						try{
							ventanaLevenshtein.setDatos(datos);
							ventanaLevenshtein.refrescarAtributos();
							ventanaLevenshtein.setVisible(true);
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				});
			}
		});
		mnDatamining.add(mntmLevenshtein);
		
		/*Grid Principal*/
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		scrollPaneCSV = new JScrollPane();
		
		scrollPaneFrecuencia = new JScrollPane();
		
		JPanel panel = new JPanel();
		
		JScrollPane scrollPaneCalculo = new JScrollPane();
		
		txtEstado = new JLabel("Bienvenido");
		txtEstado.setHorizontalAlignment(SwingConstants.CENTER);
		
		btnEliminarFila = new JButton("Eliminar Fila");
		btnEliminarFila.setEnabled(false);
		btnEliminarFila.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				int indice = tablaCSV.getSelectedRow();
			    datos.eliminarRegistro(indice);
			    actualizarModelos();
			}
		});
		
		btnAgregarFila = new JButton("Agregar Fila");
		btnAgregarFila.setEnabled(false);
		btnAgregarFila.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				datos.agregarRegistro();
				actualizarModelos();
			}
		});
		btnAgregarFila.setToolTipText("Recuerde actualizar despues de ingresar los datos");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(scrollPaneFrecuencia, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(scrollPaneCalculo, GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
								.addComponent(scrollPaneCSV, GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addComponent(btnEliminarFila)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnAgregarFila))))
						.addComponent(txtEstado, GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addComponent(txtEstado)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(scrollPaneCSV, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnAgregarFila)
								.addComponent(btnEliminarFila))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPaneCalculo, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPaneFrecuencia, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE))))
					.addGap(9))
		);
		
		tableCalculo = new JTable();
		scrollPaneCalculo.setViewportView(tableCalculo);
		
		JLabel lblClase = new JLabel("Clase:");
		
		comboBoxClases = new JComboBox<String>();
		comboBoxClases.setEnabled(false);
 		comboBoxClases.addActionListener(new ActionListener() 
 		{
             public void actionPerformed(ActionEvent event) 
             {
                 @SuppressWarnings("unchecked")
				JComboBox<String> comboBoxN = (JComboBox<String>) event.getSource();
                 String item = comboBoxN.getSelectedItem().toString();
                 
                 if (item != "Selecionar" && item != "")
                 {
                    datos.setNombreClase(item);
                 }
              }
 		});

		btnAgregar = new JButton("Agregar");
		btnAgregar.setEnabled(false);
		btnAgregar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				txtEstado.setText("Agregando Atributo");
				txtEstado.updateUI();
				
				if (textFieldNuevoAtributo.getText() != "")
				{
					datos.agregarAtributo(textFieldNuevoAtributo.getText());
					textFieldNuevoAtributo.setText("");
					actualizarModelos();
					
					txtEstado.setText("Agregado de Atributo Exitoso");
				}
				else
				{
					txtEstado.setText("No se pudo agregar el Atributo");
				}
			}
		});
		
		btnQuitar = new JButton("Quitar");
		btnQuitar.setEnabled(false);
		btnQuitar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				txtEstado.setText("Quitando Atributo");
				
				if (listaAtributos.getSelectedValue() != "")
				{
					datos.eliminarAtributo(listaAtributos.getSelectedValue().toString());
					actualizarModelos();
					txtEstado.setText("Eliminacion de Atributo Exitosa");
				}
				else
				{
					txtEstado.setText("No se pudo eliminar el Atributo");
				}
			}
		});
		
		scrollPaneAtributos = new JScrollPane();
		scrollPaneAtributos.setEnabled(false);
		
		contentPane.setLayout(gl_contentPane);
		
		btnActualizar = new JButton("Actualizar");
		btnActualizar.setEnabled(false);
		btnActualizar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				actualizarModelos();
			}
		});
		
		textFieldNuevoAtributo = new JTextField();
		textFieldNuevoAtributo.setEnabled(false);
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
							.addComponent(btnAgregar)
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
						.addComponent(btnAgregar))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnActualizar)
					.addContainerGap())
		);
		
		listaAtributos = new JList<String>();
		listaAtributos.setEnabled(false);
		listaAtributos.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mousePressed(MouseEvent e) 
			{
				txtEstado.setText("Creando Tabla de Frecuencia y Estadistica");
				
				if (listaAtributos.getSelectedValue() != null)
				{
					DefaultTableModel modelo = datos.getTablaFrecuencia(listaAtributos.getSelectedValue().toString());
	                tablaFrecuencia.setModel(modelo);

					DefaultTableModel modelo2 = datos.getTablaEstadistica(listaAtributos.getSelectedValue().toString());
					tableCalculo.setModel(modelo2);
				}
				
                tablaFrecuencia.setPreferredScrollableViewportSize(new Dimension(500, 70));
        		scrollPaneFrecuencia.setViewportView(tablaFrecuencia);
        		
        		txtEstado.setText("Cargado de Tablas Completo");
			}
		});
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
					
					txtEstado.setText("Edicion de Celda Realizada");
				}
			}
		});
		scrollPaneCSV.setViewportView(tablaCSV);
		
	}
}

