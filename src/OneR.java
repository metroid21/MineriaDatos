import java.util.LinkedList;

public class OneR 
{
	private DatosCSV datos;
	private LinkedList<NodoCSV> modelo;
	
	OneR(DatosCSV d)
	{
		this.setDatos(d);
		this.setModelo(null);
	}
	
	public DatosCSV getDatos() 
	{
		return datos;
	}

	public void setDatos(DatosCSV datos) 
	{
		this.datos = datos;
	}

	public LinkedList<NodoCSV> getModelo() 
	{
		return modelo;
	}

	public void setModelo(LinkedList<NodoCSV> modelo) 
	{
		this.modelo = modelo;
	}
		
	public void crearModelo()
	{
		this.modelo = null;
		
		if (this.datos != null)
		{
			//Conseguimos las matrices de cada atributo en relacion de la clase
			LinkedList<MatrizReglas> matrices = new LinkedList<MatrizReglas>();
			LinkedList<NodoCSV> modelo = new LinkedList<NodoCSV>();
			
			int posClase = datos.getPosicionAtributo(datos.getNombreClase());
			
			if (posClase != -1)
			{
				for (int i = 0; i < datos.getAtributos().getNodos().size(); i++)
				{
					NodoCSV nodo = datos.getAtributos().getNodos().get(i); 
					
					if (!nodo.isEliminado() && !nodo.getValor().equals(datos.getNombreClase()))
					{
						MatrizReglas temp = new MatrizReglas();
						temp.crearMatriz(this.datos, nodo.getValor());
						
						if (temp.getMatriz() != null)
						{
							matrices.add(temp);
						}
					}
				}
			}
			
			//Revisamos que existan matrices para escojer la mejor
			int posMatrizMenor = -1;

			if (!matrices.isEmpty())
			{
				double errorMenor = 1;
				posMatrizMenor = 0;
				
				for (int i = 0; i < matrices.size(); i++)
				{
					/*System.out.println("Matriz ");
					System.out.println(matrices.get(i).toString());*/
					
					double errorTotalActual = 0;
					double totalAcumulado = 0;
					double erroresAcumulado = 0;
					
					LinkedList<String> mayores = matrices.get(i).getMayorAciertos();
					
					for (int j = 0; j < mayores.size(); j++)
					{
						double totalActual = matrices.get(i).getAciertosLados(j);
						String[] trozos = mayores.get(j).split(",");
						double errorActual = totalActual - Double.parseDouble(trozos[0]);
						
						totalAcumulado  += totalActual;
						erroresAcumulado += errorActual;
					}
					
					errorTotalActual = erroresAcumulado/totalAcumulado;
					
					if (errorTotalActual < errorMenor)
					{
						errorMenor = errorTotalActual;
						posMatrizMenor = i;
					}
				}				
			}
						
			//Creamos el modelo
			if (posMatrizMenor != -1)
			{
				LinkedList<String> aciertos = matrices.get(posMatrizMenor).getMayorAciertos();
				String atributo = matrices.get(posMatrizMenor).getNombreAtributo();
				int posAtributo = datos.getPosicionAtributo(atributo);
				
				//System.out.println("Matriz: ");
				//System.out.println(matrices.get(posMatrizMenor).toString());

				/*System.out.println("Reglas usadas: ");
				System.out.println(aciertos);*/
				
				for (int i = 0; i < datos.getDatos().size(); i++)
				{
					String aEvaluar = datos.getDatos().get(i).getNodos().get(posAtributo).getValor();
					
					for (int j = 0; j < aciertos.size(); j++)
					{
						String [] trozos = aciertos.get(j).split(",");
						
						/*System.out.println(i);
						System.out.println(aEvaluar);
						System.out.println(trozos[1]);
						System.out.println(aEvaluar.equals(trozos[1]));*/
						
						if (aEvaluar.equals(trozos[1]))
						{
							modelo.add(new NodoCSV(1,trozos[2],false));
							//System.out.println("Elegido: " + trozos[2]);
							break;
						}
						
						//System.out.println();
					}
				}
				
				this.setModelo(modelo);
			}
		}
	}
	
	public double getExactitud()
	{
		double exactitud = -1;
		
		if (modelo != null && datos != null)
		{
			exactitud = 0;			
			double contadorCorrectos = 0;
			double contadorTotal = 0;
			int interno = 0;
			int posClase = datos.getPosicionAtributo(datos.getNombreClase());
			
			if (posClase != -1)
			{
				for (int i = 0; i < datos.getDatos().size(); i++)
				{
					RegistroCSV<NodoCSV> reg = datos.getDatos().get(i); 
					NodoCSV nodo = reg.getNodos().get(posClase);
					
					if (!nodo.isEliminado())
					{
						contadorTotal++;
						
						if (nodo.getValor().equals(modelo.get(interno).getValor()))
						{
							contadorCorrectos++;
						}
						
						//System.out.println(i + " " + contadorCorrectos + " " + contadorTotal + " " + 
						//				   nodo.getValor() + " "  + modelo.get(interno).getValor());
					}
					
					interno++;
				}
				
				exactitud = (contadorCorrectos/contadorTotal)*100;
			}
		}
		
		return exactitud;
	}
}
