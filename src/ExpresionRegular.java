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

public class ExpresionRegular extends JFrame {

	private JPanel panelPrincipal;
	private JTable tablaAtributos;
	private JTextField textField;

	public ExpresionRegular() {
		setTitle("Expresiones Regulares");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 552, 283);
		panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelPrincipal);
		
		JScrollPane panelTabla = new JScrollPane();
		
		JComboBox comboBox = new JComboBox();
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JButton btnGuardar = new JButton("Guardar");
		
		JButton btnAplicar = new JButton("Aplicar");
		GroupLayout gl_panelPrincipal = new GroupLayout(panelPrincipal);
		gl_panelPrincipal.setHorizontalGroup(
			gl_panelPrincipal.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelPrincipal.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelPrincipal.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelPrincipal.createSequentialGroup()
							.addComponent(panelTabla, GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
							.addGap(6))
						.addGroup(gl_panelPrincipal.createSequentialGroup()
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnGuardar)
							.addGap(18)
							.addComponent(btnAplicar)
							.addContainerGap())))
		);
		gl_panelPrincipal.setVerticalGroup(
			gl_panelPrincipal.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelPrincipal.createSequentialGroup()
					.addContainerGap(31, Short.MAX_VALUE)
					.addGroup(gl_panelPrincipal.createParallelGroup(Alignment.LEADING)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelPrincipal.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnGuardar)
							.addComponent(btnAplicar)))
					.addGap(18)
					.addComponent(panelTabla, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		tablaAtributos = new JTable();
		panelTabla.setViewportView(tablaAtributos);
		panelPrincipal.setLayout(gl_panelPrincipal);
	}
}
