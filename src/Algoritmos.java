import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

public class Algoritmos extends JFrame {

	private JPanel contentPane;
	private JTextField textExactitud;
	private JTable tablaPrincipal;
	private JTable tablaSugerencia;

	/**
	 * Create the frame.
	 */
	public Algoritmos() {
		setTitle("Algoritmos");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 863, 368);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblSeleccioneAlgoritmo = new JLabel("Seleccione Algoritmo");
		
		JComboBox comboAlgoritmos = new JComboBox();
		comboAlgoritmos.setModel(new DefaultComboBoxModel(new String[] {"ZeroR", "OneR", "NaiveBayes"}));
		
		JLabel lblExactitud = new JLabel("Exactitud");
		
		textExactitud = new JTextField();
		textExactitud.setEditable(false);
		textExactitud.setColumns(10);
		
		JScrollPane scrollTablaPrincipal = new JScrollPane();
		
		JScrollPane scrollSugerencia = new JScrollPane();
		
		JButton btnAplicar = new JButton("Aplicar");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollTablaPrincipal, GroupLayout.PREFERRED_SIZE, 384, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(scrollSugerencia, GroupLayout.PREFERRED_SIZE, 431, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(12, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblSeleccioneAlgoritmo)
							.addGap(10)
							.addComponent(comboAlgoritmos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnAplicar)
							.addPreferredGap(ComponentPlacement.RELATED, 220, Short.MAX_VALUE)
							.addComponent(lblExactitud)
							.addGap(18)
							.addComponent(textExactitud, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(180))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboAlgoritmos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textExactitud, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblExactitud)
						.addComponent(btnAplicar)
						.addComponent(lblSeleccioneAlgoritmo))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollSugerencia, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollTablaPrincipal, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(29, Short.MAX_VALUE))
		);
		
		tablaSugerencia = new JTable();
		scrollSugerencia.setViewportView(tablaSugerencia);
		
		tablaPrincipal = new JTable();
		scrollTablaPrincipal.setViewportView(tablaPrincipal);
		contentPane.setLayout(gl_contentPane);
	}
}
