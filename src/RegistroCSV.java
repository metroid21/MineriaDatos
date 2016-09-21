import java.util.LinkedList;

public class RegistroCSV 
{
	/**
	 * Identificador de la Instancia
	 */
	private Integer id = 0;
	
	/**
	 * Lista con el conjunto de nodos que componen un registro
	 */
	private LinkedList<NodoCSV> nodos = null;
	
	public RegistroCSV()
	{
		this.id = 0;
		this.nodos = new LinkedList<NodoCSV>();
	}
	
	public RegistroCSV(Integer iden)
	{
		this.id = iden;
		this.nodos = new LinkedList<NodoCSV>();
	}
	
	public RegistroCSV(Integer iden, LinkedList<NodoCSV> puntero)
	{
		this.id = iden;
		this.nodos = puntero;
	}
	
	public Integer getId() 
	{
		return id;
	}
	
	public void setId(Integer id) 
	{
		this.id = id;
	}
	
	public LinkedList<NodoCSV> getNodos() 
	{
		return nodos;
	}
	
	public void setNodos(LinkedList<NodoCSV> nodos) 
	{
		this.nodos = nodos;
	}
	
	
}
