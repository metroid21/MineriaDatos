import java.util.LinkedList;

public class ZeroR extends Algoritmo
{
	ZeroR(DatosCSV d) 
	{
		super(d);
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
	
}
