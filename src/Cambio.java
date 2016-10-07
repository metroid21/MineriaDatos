import java.util.LinkedList;

public class Cambio 
{
	/**
	 * Identificador de la Instancia
	 */ 
	private Integer id = 0;
	
	/** 
	 * Nodos con el registro de los cambios efectados en esta operacion
	 */ 
	private LinkedList<NodoCambios> nodos = null;
	
	/**
	 * Tipo de Cambio realizado
	 * Valor 1: Agregamiento de Atributo
	 * Valor 2: Quitamiento  de Atributo
	 * Valor 3: Cambio de Valores en Registros
	 */
	private Integer tipo = 0;
	
	public Cambio()
	{
		this.id = 0;
		this.nodos = new LinkedList<NodoCambios>();
		this.tipo = 0;
	}
	
	public Cambio(Integer id, LinkedList<NodoCambios> nodos, Integer tipo) 
	{
		this.id = id;
		this.nodos = nodos;
		this.tipo = tipo;
	}

	public Cambio(Integer id, Integer tipo) 
	{
		this.id = id;
		this.nodos = new LinkedList<NodoCambios>();
		this.tipo = tipo;
	}

	public Integer getId() 
	{
		return id;
	}

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public LinkedList<NodoCambios> getNodos() 
	{
		return nodos;
	}

	public void setNodos(LinkedList<NodoCambios> nodos) 
	{
		this.nodos = nodos;
	}

	public Integer getTipo() 
	{
		return tipo;
	}

	public void setTipo(Integer tipo) 
	{
		this.tipo = tipo;
	}

	
}
