import java.util.LinkedList;
 
public class RegistroCSV<T> 
{
	/**
	 * Identificador de la Instancia 
	 */ 
	private Integer id = 0;
	
	/**
	 * Lista con el conjunto de nodos que componen un registro
	 */
	private LinkedList<T> nodos = null;
	
	public RegistroCSV()
	{
		this.id = 0;
		this.nodos = new LinkedList<T>();
	}
	
	public RegistroCSV(Integer iden)
	{
		this.id = iden;
		this.nodos = new LinkedList<T>();
	}
	
	public RegistroCSV(Integer iden, LinkedList<T> puntero)
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
	
	public LinkedList<T> getNodos() 
	{
		return nodos;
	}
	
	public void setNodos(LinkedList<T> nodos) 
	{
		this.nodos = nodos;
	}
	
	
}
