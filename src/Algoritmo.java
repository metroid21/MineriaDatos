import java.util.LinkedList;

public class Algoritmo 
{
	protected DatosCSV datosEntrenamiento;
	protected DatosCSV datosPrueba;
	protected LinkedList<NodoCSV> resultado;
	
	Algoritmo(DatosCSV entrenamiento, DatosCSV prueba)
	{
		this.setDatosEntrenamiento(entrenamiento);
		this.setDatosPrueba(prueba);
	}
		
	public DatosCSV getDatosEntrenamiento() 
	{
		return datosEntrenamiento;
	}

	public void setDatosEntrenamiento(DatosCSV datosEntrenamiento) 
	{
		this.datosEntrenamiento = datosEntrenamiento;
	}

	public DatosCSV getDatosPrueba() 
	{
		return datosPrueba;
	}

	public void setDatosPrueba(DatosCSV datosPrueba) 
	{
		this.datosPrueba = datosPrueba;
	}

	public LinkedList<NodoCSV> getResultado() 
	{
		return resultado;
	}

	public void setResultado(LinkedList<NodoCSV> resultado) 
	{
		this.resultado = resultado;
	}

	public void calcular()
	{
		
	}
	
	public double getExactitud()
	{
		double exactitud = -1;
				
		if (resultado != null && datosPrueba != null)
		{
			exactitud = 0;			
			double contadorCorrectos = 0;
			double contadorTotal = 0;
			int interno = 0;
			int posClase = datosPrueba.getPosicionAtributo(datosEntrenamiento.getNombreClase());
						
			if (posClase != -1)
			{
				for (int i = 0; i < datosPrueba.getDatos().size(); i++)
				{
					RegistroCSV<NodoCSV> reg = datosPrueba.getDatos().get(i); 
					NodoCSV nodo = reg.getNodos().get(posClase);
					
					if (!nodo.isEliminado())
					{
						contadorTotal++;
						
						if (nodo.getValor().equals(resultado.get(interno).getValor()))
						{
							contadorCorrectos++;
						}
						
						//System.out.println(i + " " + contadorCorrectos + " " + contadorTotal + " " + 
						//				   nodo.getValor() + " "  + resultado.get(interno).getValor());
					}
										
					interno++;
				}
				
				exactitud = (contadorCorrectos/contadorTotal)*100;
			}
		}
		
		return exactitud;
	}
}
