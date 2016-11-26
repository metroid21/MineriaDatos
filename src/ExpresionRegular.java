 import javax.swing.JFrame;
 import javax.swing.JPanel;
 import javax.swing.border.EmptyBorder;
 import javax.swing.GroupLayout;
 import javax.swing.GroupLayout.Alignment;
 import javax.swing.JScrollPane;
 import javax.swing.LayoutStyle.ComponentPlacement;
 import javax.swing.JTable;
 import javax.swing.JComboBox;
 import javax.swing.JTextField;
 import javax.swing.JButton;
 import java.beans.PropertyChangeListener;
 import java.beans.PropertyChangeEvent;
 import java.awt.event.ActionListener;
 import java.awt.Dimension;
 
 import javax.swing.table.DefaultTableModel;
 import java.awt.event.ActionEvent;
 import javax.swing.DefaultComboBoxModel;
 
 public class ExpresionRegular extends JFrame 
 {
	private static final long serialVersionUID = 1L;
	private JPanel panelPrincipal;
 	private JTable tablaFiltro;
 	private JTextField txtGramatica;
 	private JComboBox<String> comboBox;
 	private JButton btnGuardar;
 	private JButton btnFiltrarDistinto;
 	private DatosCSV datos;
 	private JComboBox<String> comboTipo;
 	private JScrollPane panelTabla;
 	private JButton btnFiltrar;
 	
 	public JComboBox<String> getComboBox() 
 	{ 
 		return comboBox;
 	}
 
 	public void setComboBox(JComboBox<String> comboBox) 
 	{
 		this.comboBox = comboBox;
 	}
 	
 	public void setDatos(DatosCSV d)
 	{
 		this.datos = d;
 	}
 	
 	public void setEnableBtn(boolean x)
 	{
     	txtGramatica.setEnabled(x);
     	comboTipo.setEnabled(x);
     	btnGuardar.setEnabled(x);
     	btnFiltrarDistinto.setEnabled(x);
     	btnFiltrar.setEnabled(x);
 	}
 
 	public ExpresionRegular() 
 	{
 		setTitle("Expresiones Regulares");
 		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
 		setBounds(100, 100, 554, 292);
 		panelPrincipal = new JPanel();
 		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
 		setContentPane(panelPrincipal);
 		setResizable(false);
 		
 		panelTabla = new JScrollPane();
 		
 		comboBox = new JComboBox<String>();
 		comboBox.addActionListener(new ActionListener() 
 		{
             public void actionPerformed(ActionEvent event) 
             {
                 @SuppressWarnings("unchecked")
				 JComboBox<String> comboBoxN = (JComboBox<String>) event.getSource();
                 String item = comboBoxN.getSelectedItem().toString();
                 
                 if (item == "Selecionar")
                 {
                 	txtGramatica.setText("");
                 	comboTipo.setSelectedIndex(-1);
                 	
                 	setEnableBtn(false);
                 }
                 else
                 {
                    txtGramatica.setText(datos.getExpresionRegular(item));
                    comboTipo.setSelectedIndex(datos.getTipo(item));         
                     
                    setEnableBtn(true);
                 }
              }
 		});
 		
 		txtGramatica = new JTextField();
 		txtGramatica.setColumns(10);
 		
 		btnGuardar = new JButton("Guardar");
 		btnGuardar.addActionListener(new ActionListener() 
 		{
 			public void actionPerformed(ActionEvent arg0) 
 			{
 				String atributo  = comboBox.getSelectedItem().toString();
 				String gramatica = txtGramatica.getText();
 				int tipo         = comboTipo.getSelectedIndex();
 
 				datos.setExpresionRegular(atributo, gramatica);
 				datos.setTipo(atributo, tipo);
 			}
 		});
 		
 		btnFiltrarDistinto = new JButton("Filtrar Distinto");
 		btnFiltrarDistinto.addActionListener(new ActionListener() 
 		{
 			public void actionPerformed(ActionEvent e) 
 			{
 				String atributo  = comboBox.getSelectedItem().toString();
 				DefaultTableModel modelo = datos.filtrarDatos(atributo, false);
 				tablaFiltro.setModel(modelo);
 				tablaFiltro.setPreferredScrollableViewportSize(new Dimension(500, 70));
 			}
 		});
 		
 		comboTipo = new JComboBox<String>();
 		comboTipo.setModel(new DefaultComboBoxModel<String>(new String[] {"Selecionar", "?????", "Nominal", "Numerico", "Ordinal", "Booleano"}));
 		
 		btnFiltrar = new JButton("Filtrar");
 		btnFiltrar.addActionListener(new ActionListener() 
 		{
 			public void actionPerformed(ActionEvent e) 
 			{
 				String atributo  = comboBox.getSelectedItem().toString();
 				DefaultTableModel modelo = datos.filtrarDatos(atributo, true);
 				tablaFiltro.setModel(modelo);
 				tablaFiltro.setPreferredScrollableViewportSize(new Dimension(500, 70));
 			}
 		});
 		btnFiltrar.setEnabled(false);
 		GroupLayout gl_panelPrincipal = new GroupLayout(panelPrincipal);
 		gl_panelPrincipal.setHorizontalGroup(
 			gl_panelPrincipal.createParallelGroup(Alignment.LEADING)
 				.addGroup(gl_panelPrincipal.createSequentialGroup()
 					.addContainerGap()
 					.addGroup(gl_panelPrincipal.createParallelGroup(Alignment.LEADING)
 						.addGroup(gl_panelPrincipal.createSequentialGroup()
 							.addComponent(panelTabla, GroupLayout.PREFERRED_SIZE, 525, GroupLayout.PREFERRED_SIZE)
 							.addContainerGap())
 						.addGroup(Alignment.TRAILING, gl_panelPrincipal.createSequentialGroup()
 							.addGroup(gl_panelPrincipal.createParallelGroup(Alignment.TRAILING)
 								.addComponent(comboBox, Alignment.LEADING, 0, 167, Short.MAX_VALUE)
 								.addComponent(comboTipo, 0, 167, Short.MAX_VALUE))
 							.addPreferredGap(ComponentPlacement.RELATED)
 							.addGroup(gl_panelPrincipal.createParallelGroup(Alignment.LEADING)
 								.addComponent(btnFiltrarDistinto)
 								.addComponent(txtGramatica, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
 							.addPreferredGap(ComponentPlacement.RELATED)
 							.addGroup(gl_panelPrincipal.createParallelGroup(Alignment.LEADING, false)
 								.addComponent(btnFiltrar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
 								.addComponent(btnGuardar, GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
 							.addGap(56))))
 		);
 		gl_panelPrincipal.setVerticalGroup(
 			gl_panelPrincipal.createParallelGroup(Alignment.TRAILING)
 				.addGroup(gl_panelPrincipal.createSequentialGroup()
 					.addContainerGap(28, Short.MAX_VALUE)
 					.addGroup(gl_panelPrincipal.createParallelGroup(Alignment.BASELINE)
 						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
 						.addComponent(txtGramatica, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
 						.addComponent(btnFiltrar))
 					.addPreferredGap(ComponentPlacement.RELATED)
 					.addGroup(gl_panelPrincipal.createParallelGroup(Alignment.LEADING)
 						.addGroup(gl_panelPrincipal.createParallelGroup(Alignment.BASELINE)
 							.addComponent(btnFiltrarDistinto)
 							.addComponent(btnGuardar))
 						.addComponent(comboTipo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
 					.addPreferredGap(ComponentPlacement.RELATED)
 					.addComponent(panelTabla, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
 					.addContainerGap())
 		);
		
 		tablaFiltro = new JTable();
 		panelTabla.setViewportView(tablaFiltro);
 		panelPrincipal.setLayout(gl_panelPrincipal);
		
     	txtGramatica.setText("");
     	comboTipo.setSelectedIndex(-1);
     	
     	setEnableBtn(false);
     	
		tablaFiltro.addPropertyChangeListener(new PropertyChangeListener() 
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
						int reg    = Integer.parseInt(tablaFiltro.getValueAt(row, 0).toString());
						int nodo   = Integer.parseInt(tablaFiltro.getValueAt(row, 1).toString());
						String val = tablaFiltro.getValueAt(row, 2).toString();
												
						datos.actualizarFromCellJTable(reg, nodo, val);
					}
				}
			}
		});
		
		panelTabla.setViewportView(tablaFiltro);
 	}
 }