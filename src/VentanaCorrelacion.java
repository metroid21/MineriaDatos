import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaCorrelacion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textCorrelacion;
	
	private CalculadorCorrelacion calculador;
	private DatosCSV datos;
	private JComboBox<String> comboAtributo1;
	private JComboBox<String> comboAtributo2;
	
	public CalculadorCorrelacion getCalculador() 
	{
		return calculador;
	}

	public DatosCSV getDatos() 
	{
		return datos;
	}

	public void setDatos(DatosCSV datos) 
	{
		this.datos = datos;
		this.calculador = new CalculadorCorrelacion(this.datos);
	}
	
	public void refrescarAtributos()
	{
		if (datos != null)
		{
			DefaultComboBoxModel<String> rModel = new DefaultComboBoxModel<String>(datos.getAtributosAsStringArray());
			rModel.insertElementAt("Selecionar", 0);
			rModel.setSelectedItem("Selecionar");
			this.comboAtributo1.setModel(rModel);

			DefaultComboBoxModel<String> rModel2 = new DefaultComboBoxModel<String>(datos.getAtributosAsStringArray());
			rModel2.insertElementAt("Selecionar", 0);
			rModel2.setSelectedItem("Selecionar");
			this.comboAtributo2.setModel(rModel2);
			
			this.textCorrelacion.setText("");
		}
	}

	public VentanaCorrelacion() 
	{
		datos = null;
		calculador = null;
		setResizable(false);
		setTitle("Correlaci\u00F3n");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 344, 148);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblAtributo = new JLabel("Atributo 1");
		
		comboAtributo1 = new JComboBox<String>();
		
		JLabel lblAtributo_1 = new JLabel("Atributo 2");
		
		comboAtributo2 = new JComboBox<String>();
		
		JButton btnCalcular = new JButton("Calcular");
		btnCalcular.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String atr1 = (String) comboAtributo1.getSelectedItem();
				String atr2 = (String) comboAtributo2.getSelectedItem();
				double valor = calculador.calcularCorrelacion(atr1, atr2);
				
				if (valor == -777777 || (atr1.equals(atr2)))
				{
					textCorrelacion.setText("No Calculable");
				}
				else
				{
					textCorrelacion.setText(Double.toString(valor));
				}
			}
		});
		
		JLabel lblCorrelacin = new JLabel("Correlaci\u00F3n");
		
		textCorrelacion = new JTextField();
		textCorrelacion.setEditable(false);
		textCorrelacion.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblAtributo)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(comboAtributo1, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblAtributo_1)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(comboAtributo2, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnCalcular))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblCorrelacin)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textCorrelacion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(100, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAtributo)
						.addComponent(comboAtributo1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCalcular))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblAtributo_1)
						.addComponent(comboAtributo2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(21)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCorrelacin)
						.addComponent(textCorrelacion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(41, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
