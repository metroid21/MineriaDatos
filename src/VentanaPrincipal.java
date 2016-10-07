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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

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
import java.awt.Component;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
 
public class VentanaPrincipal<E> extends JFrame {
 
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
	private JList<? extends E> listaAtributos;
	private JScrollPane scrollPaneAtributos;
	private ListDataListener listDataListener;
	private JTable tableCalculo;
	private JLabel txtEstado;
	private JMenuItem mntmIngresarExprecionregular;
	private ExpresionRegular ventanaExpresiones; 

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
		 //Cargamos los cambios en un modelo de tabla
		 modeloTablaCSV = datos.getDatosAsTableModel();
		 tablaCSV.setModel(modeloTablaCSV);
		 tablaCSV.getColumn("Agregar").setCellRenderer(new ButtonRenderer());
		 tablaCSV.getColumn("Agregar").setCellEditor(new ButtonEditor(new JCheckBox()));
		 tablaCSV.getColumn("Eliminar").setCellRenderer(new ButtonRenderer());
		 tablaCSV.getColumn("Eliminar").setCellEditor(new ButtonEditor(new JCheckBox()));
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
		ventanaExpresiones = new ExpresionRegular();
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
		                 ventanaExpresiones.setDatos(datos);

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
		
		JMenu mnPreprocesamiento = new JMenu("Preprocesamiento");
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
		
		JMenu mnDatamining = new JMenu("DataMining");
		menuBar.add(mnDatamining);
		
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
								.addComponent(scrollPaneCSV, GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(scrollPaneFrecuencia, GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(scrollPaneCalculo, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE))))
						.addComponent(txtEstado, GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addComponent(txtEstado)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPaneCSV, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
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
		
		JButton btnAadir = new JButton("Agregar");
		btnAadir.addActionListener(new ActionListener() 
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
		
		JButton btnQuitar = new JButton("Quitar");
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
		
		contentPane.setLayout(gl_contentPane);
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
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
		listaAtributos.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mousePressed(MouseEvent e) 
			{
				txtEstado.setText("Creando Tabla de Frecuencia y Estadistica");
				
				DefaultTableModel modelo = datos.getTablaFrecuencia(listaAtributos.getSelectedValue().toString());
                tablaFrecuencia.setModel(modelo);

				DefaultTableModel modelo2 = datos.getTablaEstadistica(listaAtributos.getSelectedValue().toString());
				tableCalculo.setModel(modelo2);

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

class ButtonRenderer extends JButton implements TableCellRenderer {

	  public ButtonRenderer() {
	    setOpaque(true);
	  }

	  public Component getTableCellRendererComponent(JTable table, Object value,
	      boolean isSelected, boolean hasFocus, int row, int column) {
	    if (isSelected) {
	      setForeground(table.getSelectionForeground());
	      setBackground(table.getSelectionBackground());
	    } else {
	      setForeground(table.getForeground());
	      setBackground(UIManager.getColor("Button.background"));
	    }
	    setText((value == null) ? "" : value.toString());
	    return this;
	  }
}

class ButtonEditor extends DefaultCellEditor {
	  protected JButton button;

	  private String label;

	  private boolean isPushed;

	  public ButtonEditor(JCheckBox checkBox) {
	    super(checkBox);
	    button = new JButton();
	    button.setOpaque(true);
	    button.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        fireEditingStopped();
	      }
	    });
	  }

	  public Component getTableCellEditorComponent(JTable table, Object value,
	      boolean isSelected, int row, int column) {
	    if (isSelected) {
	      button.setForeground(table.getSelectionForeground());
	      button.setBackground(table.getSelectionBackground());
	    } else {
	      button.setForeground(table.getForeground());
	      button.setBackground(table.getBackground());
	    }
	    label = (value == null) ? "" : value.toString();
	    button.setText(label);
	    isPushed = true;
	    return button;
	  }

	  public Object getCellEditorValue() {
	    if (isPushed) {
	      // 
	      // 
	      JOptionPane.showMessageDialog(button, label + ": Ouch!");
	      //((DefaultTableModel)this.getModel()).removeRow(rowToRemove);
	      // System.out.println(label + ": Ouch!");
	    }
	    isPushed = false;
	    return new String(label);
	  }

	  public boolean stopCellEditing() {
	    isPushed = false;
	    return super.stopCellEditing();
	  }

	  protected void fireEditingStopped() {
	    super.fireEditingStopped();
	  }
}