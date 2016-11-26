import java.util.LinkedList;

public class ZeroR extends Algoritmo
{
	ZeroR(DatosCSV e, DatosCSV p) 
	{
		super(e, p);
	}

	public void calcular()
	{
		this.resultado = null;
		
		if (this.datosEntrenamiento != null)
		{
			LinkedList<NodoCSV> resultado = new LinkedList<NodoCSV>();
			
			int posClase = this.datosEntrenamiento.getPosicionAtributo(this.datosEntrenamiento.getNombreClase());
			String moda = this.datosEntrenamiento.getModa(this.datosEntrenamiento.getNombreClase());
			
			if (posClase != -1)
			{			
				for (int i = 0; i < this.datosPrueba.getDatos().size(); i++)
				{
					RegistroCSV<NodoCSV> reg = this.datosPrueba.getDatos().get(i); 
					NodoCSV nodo = reg.getNodos().get(posClase);
					
					if (!nodo.isEliminado())
					{
						resultado.add(new NodoCSV(i, moda, true));
					}
				}
				
				this.setResultado(resultado);
			}
		}
	}
	
}
