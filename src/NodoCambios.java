
public class NodoCambios 
{
	/**
	 * Identificador del Nodo afectado por el cambio
	 */
	private Integer idNodo = 0;
	
	/**
	 * Identificador del Registro afectado por el cambio
	 */
	private Integer idRegistro = 0;
	
	/**
	 * Valor antiguo antes del cambio
	 */
	private String valorAntiguo = "";
	
	/**
	 * Valor nuevo despues del cambio
	 */
	private String valorNuevo = "";
	
	public NodoCambios()
	{
		this.idNodo       = 0;
		this.idRegistro   = 0;
		this.valorAntiguo = "";
		this.valorNuevo   = "";
	}
	
	public NodoCambios(Integer idN, Integer idR, String antiguo, String nuevo)
	{
		this.idNodo       = idN;
		this.idRegistro   = idR;
		this.valorAntiguo = antiguo;
		this.valorNuevo   = nuevo;
	}
	
	public Integer getIdNodo() 
	{
		return idNodo;
	}
	
	public void setIdNodo(Integer idNodo) 
	{
		this.idNodo = idNodo;
	}
	
	public Integer getIdRegistro() 
	{
		return idRegistro;
	}
	
	public void setIdRegistro(Integer idRegistro) 
	{
		this.idRegistro = idRegistro;
	}
	
	public String getValorAntiguo() 
	{
		return valorAntiguo;
	}
	
	public void setValorAntiguo(String valorAntiguo) 
	{
		this.valorAntiguo = valorAntiguo;
	}
	
	public String getValorNuevo() 
	{
		return valorNuevo;
	}
	
	public void setValorNuevo(String valorNuevo) 
	{
		this.valorNuevo = valorNuevo;
	}
}
