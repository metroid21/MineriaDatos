import java.util.LinkedList;

public class DatosCSV 
{
	private RegistroCSV atributos;
	private LinkedList<RegistroCSV> datos;
	private LinkedList<Cambio> cambios;
	
	public DatosCSV() 
	{
		this.atributos = new RegistroCSV();
		this.datos     = new LinkedList<RegistroCSV>();
		this.cambios   = new LinkedList<Cambio>();
	}

	public DatosCSV(RegistroCSV atributos, LinkedList<RegistroCSV> datos, LinkedList<Cambio> cambios) 
	{
		this.atributos = atributos;
		this.datos = datos;
		this.cambios = cambios;
	}

	public RegistroCSV getAtributos() 
	{
		return atributos;
	}
	
	public void setAtributos(RegistroCSV atributos) 
	{
		this.atributos = atributos;
	}
	
	public LinkedList<RegistroCSV> getDatos() 
	{
		return datos;
	}
	
	public void setDatos(LinkedList<RegistroCSV> datos) 
	{
		this.datos = datos;
	}
	
	public LinkedList<Cambio> getCambios() 
	{
		return cambios;
	}
	
	public void setCambios(LinkedList<Cambio> cambios) 
	{
		this.cambios = cambios;
	}
}
