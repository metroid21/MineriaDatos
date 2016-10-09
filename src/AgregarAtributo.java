import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AgregarAtributo extends JFrame {

	private JPanel contentPane;
	private JTable tablaAtributos;
	private  String[] columnNames;
	private String[] fila;

	public JTable getTabla() 
 	{ 
 		return tablaAtributos;
 	}
	public String[] getFila()
	{
		return fila;
	}
	public void setColumnNames(String[] columnas)
	{
		this.columnNames=columnas;
	}
	public String[] getColumNames()
	{
		return columnNames;
	}
	
	public AgregarAtributo(String[] nomColumnas) {
		String[] valFila = new String[nomColumnas.length];
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 777, 177);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnInsertar = new JButton("Insertar");
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(int i=0;i<tablaAtributos.getColumnCount();i++){
					valFila[i] = (String) tablaAtributos.getModel().getValueAt(0, 1);
				}
				fila=valFila;
				dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(661)
							.addComponent(btnInsertar))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 722, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(29, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(btnInsertar)
					.addContainerGap(22, Short.MAX_VALUE))
		);
		tablaAtributos = new JTable();
		tablaAtributos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			nomColumnas
		));
		((DefaultTableModel) tablaAtributos.getModel()).insertRow(0, new Object[]{});
		
		scrollPane.setViewportView(tablaAtributos);
		contentPane.setLayout(gl_contentPane);
		
	}
}
