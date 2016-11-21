import java.util.LinkedList;

public class ZeroR 
{
	private DatosCSV datos;
	private LinkedList<NodoCSV> modelo;
	
	ZeroR(DatosCSV d)
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
			LinkedList<NodoCSV> modelo = new LinkedList<NodoCSV>();
			
			int posClase = datos.getPosicionAtributo(datos.getNombreClase());
			String moda = datos.getModa(datos.getNombreClase());
			
			if (posClase != -1)
			{
				for (int i = 0; i < datos.getDatos().size(); i++)
				{
					RegistroCSV<NodoCSV> reg = datos.getDatos().get(i); 
					NodoCSV nodo = reg.getNodos().get(posClase);
					
					if (!nodo.isEliminado())
					{
						modelo.add(new NodoCSV(i, moda, true));
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
						
						/*System.out.println(i + " " + contadorCorrectos + " " + contadorTotal + " " + 
										   nodo.getValor() + " "  + modelo.get(interno).getValor());*/
					}
					
					interno++;
				}
				
				exactitud = (contadorCorrectos/contadorTotal)*100;
			}
		}
		
		return exactitud;
	}
}
