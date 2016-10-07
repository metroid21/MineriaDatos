 
public class NodoCSV 
{
	/**
	 * Identificador de la instancia
	 */
	private Integer id = 0;
		
	/**
	 * Valor de dicho objeto como cadena
	 */
	private String valor = "";
	
	/**
	 * Responde si fue eliminada la instancia durante el programa
	 */
	private boolean eliminado = true; 
	
	public NodoCSV() 
	{
		this.id        = 0;
		this.valor     = "";
		this.eliminado = true;
	}

	public NodoCSV(Integer iden, String val, boolean elim)
	{
		this.id        = iden;
		this.valor     = val;
		this.eliminado = elim;
	}

	public Integer getId() 
	{
		return id;
	}
	
	public void setId(Integer id) 
	{ 
		this.id = id;
	}
		
	public String getValor() 
	{
		return valor;
	}
	
	public void setValor(String valor) 
	{
		this.valor = valor;
	}
	
	public boolean isEliminado() 
	{
		return eliminado;
	}
	
	public void setEliminado(boolean eliminado) 
	{
		this.eliminado = eliminado;
	}
		
	public String toString()
	{
		String myString = "";
				
		myString = "{";
		myString += this.id;
		myString += ", ";
		myString += this.eliminado;
		myString += ", ";
		myString += this.valor;
		myString += '}';
		
		return myString;
	}
}
