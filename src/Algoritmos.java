import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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
		
		Icon flecha = new ImageIcon("src/flecha.png");
		Icon flecha2 = new ImageIcon("src/flecha2.png");
		
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
		
		
		JButton btnFlechaIzq = new JButton();
		btnFlechaIzq.setIcon(flecha);
		btnFlechaIzq.setBorderPainted(false); 
		btnFlechaIzq.setContentAreaFilled(false); 
		btnFlechaIzq.setFocusPainted(false); 
		btnFlechaIzq.setOpaque(false);
		
		JButton btnFlechaDer = new JButton();
		btnFlechaDer.setIcon(flecha2);
		btnFlechaDer.setBorderPainted(false); 
		btnFlechaDer.setContentAreaFilled(false); 
		btnFlechaDer.setFocusPainted(false); 
		btnFlechaDer.setOpaque(false);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollTablaPrincipal, GroupLayout.PREFERRED_SIZE, 359, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnFlechaIzq)
								.addComponent(btnFlechaDer))
							.addGap(18)
							.addComponent(scrollSugerencia, GroupLayout.PREFERRED_SIZE, 387, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblSeleccioneAlgoritmo)
							.addGap(10)
							.addComponent(comboAlgoritmos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnAplicar)
							.addPreferredGap(ComponentPlacement.RELATED, 244, Short.MAX_VALUE)
							.addComponent(lblExactitud)
							.addGap(18)
							.addComponent(textExactitud, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(180))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
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
								.addComponent(scrollTablaPrincipal, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollSugerencia, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(102)
							.addComponent(btnFlechaIzq)
							.addGap(49)
							.addComponent(btnFlechaDer)))
					.addContainerGap(27, Short.MAX_VALUE))
		);
		
		tablaSugerencia = new JTable();
		scrollSugerencia.setViewportView(tablaSugerencia);
		
		tablaPrincipal = new JTable();
		scrollTablaPrincipal.setViewportView(tablaPrincipal);
		contentPane.setLayout(gl_contentPane);
	}
}
