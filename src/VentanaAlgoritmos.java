import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class VentanaAlgoritmos extends JFrame {

	private static final long serialVersionUID = -7486936667403701871L;
	private JPanel contentPane;
	private JTextField textExactitud;
	private JTable tablaEntrenamiento;
	private JTable tablaPrueba;

	private DatosCSV datosOrigen;
	private JComboBox<String> comboAlgoritmos;
	private JButton btnAplicar;
	
	public DatosCSV getDatosOrigen() 
	{
		return datosOrigen;
	}

	public void setDatosOrigen(DatosCSV datosOrigen) 
	{
		this.datosOrigen = datosOrigen;
	}
	
	public void inicializar()
	{
		this.tablaEntrenamiento.setModel(this.datosOrigen.getDatosAsTableModel(false));
		
		DefaultTableModel modelo1 = new DefaultTableModel();
		
		for (int i = 0; i < datosOrigen.getAtributos().getNodos().size(); i++)
		{
			modelo1.addColumn(this.datosOrigen.getAtributos().getNodos().get(i).getValor());
		}
		
		this.tablaPrueba.setModel(modelo1);
		this.btnAplicar.setEnabled(true);
		textExactitud.setText("");
	}
	
	/**
	 * Create the frame.
	 */
	public VentanaAlgoritmos() 
	{
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
		
		comboAlgoritmos = new JComboBox<String>();
		comboAlgoritmos.setModel(new DefaultComboBoxModel<String>(new String[] {"ZeroR", "OneR", "NaiveBayes"}));
		
		JLabel lblExactitud = new JLabel("Exactitud");
		
		textExactitud = new JTextField();
		textExactitud.setEditable(false);
		textExactitud.setColumns(10);
		
		JScrollPane scrollTablaPrincipal = new JScrollPane();
		
		JScrollPane scrollSugerencia = new JScrollPane();
		
		btnAplicar = new JButton("Aplicar");
		btnAplicar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (tablaEntrenamiento != null && tablaEntrenamiento.getRowCount() != 0)
				{
					//Obtenemos los datos del Algoritmo
					DatosCSV datosEntrenamiento = DatosCSV.getDatosFromTableModel((DefaultTableModel) tablaEntrenamiento.getModel());
					DatosCSV datosPrueba;
					
					if (tablaPrueba.getRowCount() == 0)
					{
						datosPrueba = datosEntrenamiento;
						tablaPrueba.setModel(tablaEntrenamiento.getModel());
					}
					else
					{
						datosPrueba = DatosCSV.getDatosFromTableModel((DefaultTableModel) tablaPrueba.getModel());
					}
					
					datosPrueba.setNombreClase(datosOrigen.getNombreClase());
					datosEntrenamiento.setNombreClase(datosOrigen.getNombreClase());
					
					String nombreAlgoritmo = (String) comboAlgoritmos.getSelectedItem();
					Algoritmo algoritmo;
					
					//Decidimos que algoritmo sera
					if (nombreAlgoritmo == "ZeroR")
					{
						algoritmo = new ZeroR(datosEntrenamiento, datosPrueba);
					}
					else if (nombreAlgoritmo == "OneR")
					{
						algoritmo = new OneR(datosEntrenamiento, datosPrueba);
					}
					else
					{
						algoritmo = new NaiveBayes(datosEntrenamiento, datosPrueba);
					}
					
					algoritmo.calcular();
					
					//Ponemos los datos nuevos
					DefaultTableModel modelo = (DefaultTableModel) tablaPrueba.getModel();
					modelo.addColumn("Resultado Algoritmo");
					
					for (int i = 0; i < algoritmo.getResultado().size(); i++)
					{
						modelo.setValueAt(algoritmo.getResultado().get(i).getValor(), i, modelo.getColumnCount()-1);
					}
					
					textExactitud.setText(Double.toString(algoritmo.getExactitud()));
					btnAplicar.setEnabled(false);
				}	
			}
		});
		
		
		JButton btnFlechaIzq = new JButton();
		btnFlechaIzq.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				int[] posRows = tablaPrueba.getSelectedRows();
				
				for (int j = 0; j < posRows.length; j++)
				{
					Vector<String> valores = new Vector<String>();

					int posRow = tablaPrueba.getSelectedRow(); 
					
					//Copiamos el valor de la fila
					for (int i = 0; i < tablaPrueba.getColumnCount(); i++)
					{
						valores.add((String) tablaPrueba.getValueAt(posRow, i));
					}
					
					//Quitamos dicha fila
					DefaultTableModel modelo = (DefaultTableModel) tablaPrueba.getModel();
					modelo.removeRow(posRow);
					
					modelo = (DefaultTableModel) tablaEntrenamiento.getModel();
					modelo.addRow(valores);
					
				}
				
				tablaPrueba.clearSelection();
				tablaEntrenamiento.clearSelection();				
			}
		});
		btnFlechaIzq.setIcon(flecha);
		btnFlechaIzq.setBorderPainted(false); 
		btnFlechaIzq.setContentAreaFilled(false); 
		btnFlechaIzq.setFocusPainted(false); 
		btnFlechaIzq.setOpaque(false);
		
		JButton btnFlechaDer = new JButton();
		btnFlechaDer.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				int[] posRows = tablaEntrenamiento.getSelectedRows();
				
				for (int j = 0; j < posRows.length; j++)
				{
					Vector<String> valores = new Vector<String>();

					if (tablaEntrenamiento.getRowCount() == 1)
					{
						break;
					}

					int posRow = tablaEntrenamiento.getSelectedRow(); 
					
					//Copiamos el valor de la fila
					for (int i = 0; i < tablaEntrenamiento.getColumnCount(); i++)
					{						
						valores.add((String) tablaEntrenamiento.getValueAt(posRow, i));
					}
					
					//Quitamos dicha fila
					DefaultTableModel modelo = (DefaultTableModel) tablaEntrenamiento.getModel();
					modelo.removeRow(posRow);
					
					modelo = (DefaultTableModel) tablaPrueba.getModel();
					modelo.addRow(valores);
				}
				
				tablaPrueba.clearSelection();
				tablaEntrenamiento.clearSelection();				
			}
		});
		btnFlechaDer.setIcon(flecha2);
		btnFlechaDer.setBorderPainted(false); 
		btnFlechaDer.setContentAreaFilled(false); 
		btnFlechaDer.setFocusPainted(false); 
		btnFlechaDer.setOpaque(false);
		
		JButton btnInicializar = new JButton("inicializar");
		btnInicializar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				inicializar();
			}
		});
		
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
							.addPreferredGap(ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
							.addComponent(lblExactitud)
							.addGap(18)
							.addComponent(textExactitud, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(31)
							.addComponent(btnInicializar)
							.addGap(47))))
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
								.addComponent(lblSeleccioneAlgoritmo)
								.addComponent(btnInicializar))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollTablaPrincipal, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollSugerencia, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(102)
							.addComponent(btnFlechaIzq)
							.addGap(49)
							.addComponent(btnFlechaDer)))
					.addContainerGap(52, Short.MAX_VALUE))
		);
		
		tablaPrueba = new JTable();
		scrollSugerencia.setViewportView(tablaPrueba);
		
		tablaEntrenamiento = new JTable();
		scrollTablaPrincipal.setViewportView(tablaEntrenamiento);
		contentPane.setLayout(gl_contentPane);
	}
}
