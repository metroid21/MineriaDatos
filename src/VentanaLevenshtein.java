import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.awt.event.ActionEvent;

public class VentanaLevenshtein extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textPalabra;
	private JTextField textSugerencia;
	private JComboBox<String> comboAtributos;
	private DatosCSV datos;
	
	public DatosCSV getDatos() 
	{
		return datos;
	}

	public void setDatos(DatosCSV datos) 
	{
		this.datos = datos;
	}
	
	public void refrescarAtributos()
	{
		if (datos != null)
		{
			DefaultComboBoxModel<String> rModel = new DefaultComboBoxModel<String>(datos.getAtributosAsStringArray());
			rModel.insertElementAt("Selecionar", 0);
			rModel.setSelectedItem("Selecionar");
			this.comboAtributos.setModel(rModel);

			this.textSugerencia.setText("");
		}
	}

	public VentanaLevenshtein() 
	{
		datos = null;

		setResizable(false);
		setTitle("Levenshtein");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 402, 120);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblInstancia = new JLabel("Atributo");
		
		JLabel lblPalabra = new JLabel("Palabra");
		
		textPalabra = new JTextField();
		textPalabra.setColumns(10);
		
		JButton btnComparar = new JButton("Comparar");
		btnComparar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (datos != null)
				{
					String atributo = (String)comboAtributos.getSelectedItem();
					
					if (atributo.equals("Selecionar"))
					{
						textSugerencia.setText("No Calculable");
					}
					else if (textPalabra.getText().equals(""))
					{
						textSugerencia.setText("Inserte una palabra para comparar");
					}
					else
					{
						String input = textPalabra.getText();
						LinkedList<String> palabras = datos.getDistintos(atributo);
						textSugerencia.setText(CalculadorLevenshtein.masParecido(input, palabras));
					}
				}
			}
		});
		
		comboAtributos = new JComboBox<String>();
		
		textSugerencia = new JTextField();
		textSugerencia.setEditable(false);
		textSugerencia.setColumns(10);
		
		JLabel lblSugerencia = new JLabel("Sugerencia");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblInstancia)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(comboAtributos, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblPalabra))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(12)
							.addComponent(btnComparar)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblSugerencia)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(textSugerencia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textPalabra, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInstancia)
						.addComponent(lblPalabra)
						.addComponent(textPalabra, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboAtributos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textSugerencia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSugerencia)
						.addComponent(btnComparar))
					.addContainerGap(13, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
