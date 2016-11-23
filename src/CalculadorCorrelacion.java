import java.util.LinkedList;

public class CalculadorCorrelacion 
{
	private DatosCSV datos;
		
	public CalculadorCorrelacion(DatosCSV datosN)
	{
		this.datos = datosN;
	}
	
	private double correlacionPearson(String nombreAtributo1, String nombreAtributo2)
	{
		int pos1 = datos.getPosicionAtributo(nombreAtributo1);
		int pos2 = datos.getPosicionAtributo(nombreAtributo2);
		
		/*System.out.println(pos1);
		System.out.println(pos2);*/
		
		if (pos1 != -1 && pos2 != -1)
		{		
			double promedioAtr1   = datos.getMedia(nombreAtributo1); 
			double desviacionAtr1 = datos.getDesviacionEstandar(nombreAtributo1);
			
			double promedioAtr2   = datos.getMedia(nombreAtributo2);
			double desviacionAtr2 = datos.getDesviacionEstandar(nombreAtributo2);
			
			double totalDatos = datos.getTotal(nombreAtributo1);
			
			/*System.out.println("-A: " + promedioAtr1);
			System.out.println("OA: " + desviacionAtr1);
			System.out.println("-B:" + promedioAtr2);
			System.out.println("OB:" + desviacionAtr2);
			System.out.println("n: " + totalDatos);*/
			
			//calculamos el valor de aibi
			double aibi = 0;
			
			for (int i = 0; i < datos.getDatos().size(); i++)
			{
				double val1 = Double.parseDouble(datos.getDatos().get(i).getNodos().get(pos1).getValor());
				double val2 = Double.parseDouble(datos.getDatos().get(i).getNodos().get(pos2).getValor());

				aibi = aibi + (val1*val2);
			}
			
			//Calculamos el valor de nAB
			double nAB = totalDatos * promedioAtr1 * promedioAtr2;
			
			//Calculamos el valor de nOAOB
			double nOAOB = totalDatos * desviacionAtr1 * desviacionAtr2;
			
			//calculamos el valor de la correlacion			
			return (aibi - nAB)/nOAOB;
		}
		else
		{
			return -777777;
		}
	}
	
	private double correlacionChiCuadrada(String nombreAtributo1, String nombreAtributo2)
	{
		int pos1 = datos.getPosicionAtributo(nombreAtributo1);
		int pos2 = datos.getPosicionAtributo(nombreAtributo2);
		
		/*System.out.println(pos1);
		System.out.println(pos2);*/
		
		if (pos1 != -1 && pos2 != -1)
		{
			//Creamos la matriz de repeticiones
			String claseTemp = datos.getNombreClase();
			
			datos.setNombreClase(nombreAtributo2);
			MatrizReglas matriz = new MatrizReglas();
			matriz.crearMatriz(datos, nombreAtributo1);
			MatrizReglas matrizCopia = new MatrizReglas();
			matrizCopia.crearMatriz(datos, nombreAtributo1);
			
			datos.setNombreClase(claseTemp);
			
			//Sacamos los valores de cada fila y columna
			LinkedList<Double> totalesLados = new LinkedList<Double>();
			LinkedList<Double> totalesArriba = new LinkedList<Double>();
			double total = 0;
			
			for (int i = 0; i < matriz.getHeaderArriba().size(); i++)
			{
				double val = matriz.getAciertosArriba(i);
				total += val;
				totalesArriba.add(val);
			}
			
			for (int i = 0; i < matriz.getHeaderLados().size(); i++)
			{
				totalesLados.add(matriz.getAciertosLados(i));
			}
						
			//Calculamos el valor de eij para cada celda
			for (int i = 0; i < matriz.getHeaderArriba().size(); i++)
			{
				for (int j = 0; j < matriz.getHeaderLados().size(); j++)
				{
					double eij = (totalesLados.get(j) * totalesArriba.get(i))/total;
					matriz.getMatriz()[j][i] = eij;
				}
			}
					
			//System.out.println(matriz.toString());
			
			//Empezamos a calcular el valor final de la correlacion
			double valorCorrelacion = 0;
			
			for (int i = 0; i < matriz.getHeaderArriba().size(); i++)
			{
				for (int j = 0; j < matriz.getHeaderLados().size(); j++)
				{
					double eij = matriz.getMatriz()[j][i];
					double oij = matrizCopia.getMatriz()[j][i];
					double x2 = Math.pow((oij-eij),2)/eij;
										
					valorCorrelacion += x2;
				}
			}
			
			//Calculamos el grado de libertad
			//double gradoLibertad = (matriz.getHeaderArriba().size()-1) * (matriz.getHeaderLados().size()-1);
			//System.out.println("Grado de Libertad: " + gradoLibertad);
			
			//Devolvemos el valor de la correlacion
			return valorCorrelacion;
		}
		else
		{
			return -777777;
		}
	}
	
	public double calcularCorrelacion(String nombreAtributo1, String nombreAtributo2)
	{
		if (datos.getTipo(nombreAtributo1) == 3 && datos.getTipo(nombreAtributo2) == 3)
		{
			return correlacionPearson(nombreAtributo1, nombreAtributo2);
		}
		else
		{
			return correlacionChiCuadrada(nombreAtributo1, nombreAtributo2);
		}
	}
}
