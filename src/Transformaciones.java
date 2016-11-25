import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;

public class Transformaciones extends JFrame {

	private JPanel contentPane;
	private JTextField textRango1;
	private JTextField textRango2;
	private JTable tablaResultados;

	
	public Transformaciones() {
		setResizable(false);
		setTitle("Transfromaciones");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 775, 301);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblAtributo = new JLabel("Atributo:");
		
		JComboBox comboAtributos = new JComboBox();
		
		JLabel lblRango = new JLabel("Rango");
		
		textRango1 = new JTextField();
		textRango1.setColumns(10);
		
		JLabel label = new JLabel("-");
		
		textRango2 = new JTextField();
		textRango2.setColumns(10);
		
		JScrollPane scrollResultados = new JScrollPane();
		
		JButton btnAplicar = new JButton("Aplicar");
		
		JLabel lblMetodo = new JLabel("Metodo");
		
		final JComboBox comboMetodos = new JComboBox();
		comboMetodos.setModel(new DefaultComboBoxModel(new String[] {"MIN-MAX", "Z-Score", "Escalamiento Decimal"}));
		comboMetodos.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String metodo=String.valueOf(comboMetodos.getSelectedItem());
				if(metodo=="Z-Score"||metodo=="Escalamiento Decimal"){
					textRango1.setEditable(false);
					textRango2.setEditable(false);
				}
				else{
					textRango1.setEditable(true);
					textRango2.setEditable(true);
				}
			}
		});
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblAtributo)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(comboAtributos, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblMetodo)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboMetodos, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblRango)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textRango1, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textRango2, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnAplicar))
						.addComponent(scrollResultados, GroupLayout.PREFERRED_SIZE, 728, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAtributo)
						.addComponent(comboAtributos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMetodo)
						.addComponent(comboMetodos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRango)
						.addComponent(textRango1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label)
						.addComponent(textRango2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAplicar))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollResultados, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(15, Short.MAX_VALUE))
		);
		
		tablaResultados = new JTable();
		scrollResultados.setViewportView(tablaResultados);
		contentPane.setLayout(gl_contentPane);
	}
}
