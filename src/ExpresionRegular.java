import java.awt.BorderLayout;
import java.awt.EventQueue;

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
import javax.swing.ComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JTextPane;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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

public class ExpresionRegular extends JFrame {

	private JPanel panelPrincipal;
	private JTable tablaAtributos;
	private JTextField txtGramatica;
	private JComboBox comboBox;
	private JButton btnGuardar;
	private JButton btnFiltrar;
	private DatosCSV datos;
	private JComboBox comboTipo;
	
	public JComboBox getComboBox() 
	{
		return comboBox;
	}

	public void setComboBox(JComboBox comboBox) 
	{
		this.comboBox = comboBox;
	}
	
	public void setDatos(DatosCSV d)
	{
		this.datos = d;
	}

	public ExpresionRegular() 
	{
		setTitle("Expresiones Regulares");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 578, 292);
		panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelPrincipal);
		
		JScrollPane panelTabla = new JScrollPane();
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent event) 
            {
                JComboBox comboBoxN = (JComboBox) event.getSource();
                String item = comboBoxN.getSelectedItem().toString();
                
                if (item == "Selecionar")
                {
                	txtGramatica.setText("");
                	comboTipo.setSelectedIndex(-1);
                	
                	txtGramatica.setEnabled(false);
                	comboTipo.setEnabled(false);
                	btnGuardar.setEnabled(false);
                	btnFiltrar.setEnabled(false);
                }
                else
                {
                    txtGramatica.setText(datos.getExpresionRegular(item));
                    comboTipo.setSelectedIndex(datos.getTipo(item));         
                    
                    txtGramatica.setEnabled(true);
                	comboTipo.setEnabled(true);
                	btnGuardar.setEnabled(true);
                	btnFiltrar.setEnabled(true);
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
		
		btnFiltrar = new JButton("Filtrar");
		
		comboTipo = new JComboBox();
		comboTipo.setModel(new DefaultComboBoxModel(new String[] {"Selecionar", "?????", "Nominal", "Entero", "Ordinal", "Real"}));
		GroupLayout gl_panelPrincipal = new GroupLayout(panelPrincipal);
		gl_panelPrincipal.setHorizontalGroup(
			gl_panelPrincipal.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelPrincipal.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelPrincipal.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panelPrincipal.createSequentialGroup()
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(gl_panelPrincipal.createParallelGroup(Alignment.LEADING, false)
								.addComponent(txtGramatica)
								.addComponent(comboTipo, 0, 149, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnGuardar)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnFiltrar)
							.addGap(18))
						.addComponent(panelTabla, GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelPrincipal.setVerticalGroup(
			gl_panelPrincipal.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelPrincipal.createSequentialGroup()
					.addGroup(gl_panelPrincipal.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelPrincipal.createSequentialGroup()
							.addContainerGap(12, Short.MAX_VALUE)
							.addGroup(gl_panelPrincipal.createParallelGroup(Alignment.LEADING)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panelPrincipal.createParallelGroup(Alignment.BASELINE)
									.addComponent(btnGuardar)
									.addComponent(btnFiltrar)))
							.addGap(18))
						.addGroup(gl_panelPrincipal.createSequentialGroup()
							.addComponent(txtGramatica, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboTipo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addComponent(panelTabla, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		tablaAtributos = new JTable();
		panelTabla.setViewportView(tablaAtributos);
		panelPrincipal.setLayout(gl_panelPrincipal);
		
    	txtGramatica.setText("");
    	comboTipo.setSelectedIndex(-1);
    	
    	txtGramatica.setEnabled(false);
    	comboTipo.setEnabled(false);
    	btnGuardar.setEnabled(false);
    	btnFiltrar.setEnabled(false);
	}
}
