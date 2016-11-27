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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Transformaciones extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textRango1;
	private JTextField textRango2;
	private JTable tablaResultados;
	private DatosCSV datos;
	private CalculadorTransformaciones CT;
	private JComboBox<String> comboAtributos;
	private JTable tableNuevos;
	
	public void setDatos(DatosCSV datos) 
	{
		this.datos = datos;
	}
	
	public void refrescarAtributos()
	{
		if (datos != null)
		{
			tablaResultados.setModel(datos.getDatosAsTableModel(false));
			
			DefaultComboBoxModel<String> rModel = new DefaultComboBoxModel<String>();
		
			for (int i = 0; i < datos.getAtributos().getNodos().size(); i++)
			{
				if (datos.getAtributos().getNodos().get(i).getTipo() == 3)
				{
					rModel.insertElementAt(datos.getAtributos().getNodos().get(i).getValor(), 0);
				}
			}
			
			rModel.insertElementAt("Selecionar", 0);
			rModel.setSelectedItem("Selecionar");
			
			this.comboAtributos.setModel(rModel);
			
			//this.textCorrelacion.setText("");
		}
	}
	
	
	public Transformaciones() {
		setResizable(false);
		setTitle("Transfromaciones");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 775, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblAtributo = new JLabel("Atributo:");
		comboAtributos = new JComboBox<String>();
		
		JLabel lblRango = new JLabel("Rango");
		
		textRango1 = new JTextField();
		textRango1.setText("0");
		textRango1.setColumns(10);
		
		JLabel label = new JLabel("-");
		
		textRango2 = new JTextField();
		textRango2.setText("1");
		textRango2.setColumns(10);
		
		JScrollPane scrollResultados = new JScrollPane();
		
		
		
		JLabel lblMetodo = new JLabel("Metodo");
		
		final JComboBox<String> comboMetodos = new JComboBox<String>();
		comboMetodos.setModel(new DefaultComboBoxModel<String>(new String[] {"MIN-MAX", "Z-Score","Z-Score Absoluto", "Escalamiento Decimal"}));
		comboMetodos.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String metodo=String.valueOf(comboMetodos.getSelectedItem());
				if(metodo=="MIN-MAX"){
					textRango1.setEditable(true);
					textRango2.setEditable(true);
				}
				else{
					textRango1.setEditable(false);
					textRango2.setEditable(false);					
				}
			}
		});
		
		
		JButton btnAplicar = new JButton("Aplicar");
		btnAplicar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				CT=new CalculadorTransformaciones(datos);
				String metodo=String.valueOf(comboMetodos.getSelectedItem());
				String nombreAtributo=String.valueOf(comboAtributos.getSelectedItem());
				
				if (nombreAtributo != "Selecionar")
				{
					if(metodo=="MIN-MAX")
					{
						boolean correcto = true;
						int val1 = 0;
						int val2 = 1;
						
						try
						{
							val1 = Integer.parseInt(textRango1.getText());
							val2 = Integer.parseInt(textRango2.getText());
						}
						catch (NumberFormatException e)
						{
							correcto = false;
						}
						
						if (correcto)
						{
							CT.minMax(val1,val2,nombreAtributo);							
						}
						
					}else if(metodo=="Z-Score"){
						CT.zScore(nombreAtributo);
					}else if(metodo=="Z-Score Absoluto"){
						CT.zScoreAbs(nombreAtributo);
					}else if(metodo=="Escalamiento Decimal"){
						CT.decimal(nombreAtributo);
					}
					tableNuevos.setModel(datos.getDatosAsTableModel(false));
				}				
			}
		});
		
		JScrollPane scrollNuevos = new JScrollPane();
		
		JLabel lblActual = new JLabel("Actual");
		
		JLabel lblNuevo = new JLabel("Nuevo");
		
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
							.addPreferredGap(ComponentPlacement.RELATED, 5, Short.MAX_VALUE)
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textRango2, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnAplicar)
							.addContainerGap(300, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollResultados, 0, 0, Short.MAX_VALUE)
								.addComponent(lblActual))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblNuevo)
									.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addComponent(scrollNuevos, GroupLayout.PREFERRED_SIZE, 364, GroupLayout.PREFERRED_SIZE)
									.addGap(26))))))
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
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNuevo)
						.addComponent(lblActual))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollResultados, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollNuevos, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		tableNuevos = new JTable();
		scrollNuevos.setViewportView(tableNuevos);
		
		tablaResultados = new JTable();
		scrollResultados.setViewportView(tablaResultados);
		contentPane.setLayout(gl_contentPane);
	}
}
