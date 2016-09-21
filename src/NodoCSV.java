
public class NodoCSV 
{
	/**
	 * Identificador de la instancia
	 */
	private Integer id = 0;
	
	/**
	 * Valor 1: Atributo
	 * Valor 2: Nominal
	 * Valor 3: Numérico
	 * Valor 4: Ordinal
	 */
	private Integer tipo = 0;
	
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
		this.tipo      = 0;
		this.valor     = "";
		this.eliminado = true;
	}

	public NodoCSV(Integer iden, Integer tipor, String val, boolean elim)
	{
		this.id        = iden;
		this.tipo      = tipor;
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
	
	public Integer getTipo() 
	{
		return tipo;
	}
	
	public void setTipo(Integer tipo) 
	{
		this.tipo = tipo;
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
}
