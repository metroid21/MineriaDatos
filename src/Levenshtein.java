import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Levenshtein extends JFrame {

	private JPanel contentPane;
	private JTextField textPalabra2;
	private JTable tablaResultados;

	public Levenshtein() {
		setResizable(false);
		setTitle("Levenshtein");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 553, 299);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblPalabra1 = new JLabel("Palabra 1");
		
		JLabel lblPalabra2 = new JLabel("Palabra 2");
		
		textPalabra2 = new JTextField();
		textPalabra2.setColumns(10);
		
		JButton btnComparar = new JButton("Comparar");
		
		JComboBox comboPalabra1 = new JComboBox();
		
		JScrollPane scrollTable = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblPalabra1)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(comboPalabra1, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblPalabra2)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textPalabra2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnComparar))
						.addComponent(scrollTable, GroupLayout.PREFERRED_SIZE, 502, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(25, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPalabra1)
						.addComponent(lblPalabra2)
						.addComponent(textPalabra2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnComparar)
						.addComponent(comboPalabra1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(scrollTable, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(29, Short.MAX_VALUE))
		);
		
		tablaResultados = new JTable();
		scrollTable.setViewportView(tablaResultados);
		contentPane.setLayout(gl_contentPane);
	}
}
