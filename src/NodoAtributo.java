
public class NodoAtributo extends NodoCSV
{
	/**
	 * Valor 1: Atributo
	 * Valor 2: Nominal
	 * Valor 3: Entero
	 * Valor 4: Ordinal
	 * Valor 5: Real
	 */
	private Integer tipo = 0; 
	
	private String expresionRegular = "";
	
	public String getExpresionRegular() 
	{
		return expresionRegular;
	}

	public void setExpresionRegular(String expresionRegular) 
	{
		this.expresionRegular = expresionRegular;
	}

	public String getNombreTipo()
	{
		if (this.tipo == 1)
		{
			return "Atributo";
		}
		else if (this.tipo == 2)
		{
			return "Nominal";
		}
		else if (this.tipo == 3)
		{
			return "Entero";
		}
		else if (this.tipo == 4)
		{
			return "Ordinal";
		}
		else if (this.tipo == 5)
		{
			return "Real";
		}
		else
		{
			return "?????";
		}
	}

	public Integer getTipo() 
	{
		return tipo;
	}
	
	public void setTipo(Integer tipo) 
	{
		this.tipo = tipo;
	}
	
	public String toString()
	{
		String myString = "";
				
		myString = "{";
		myString += this.getId();
		myString += ", ";
		myString += this.isEliminado();
		myString += ", ";
		myString += this.getTipo();
		myString += ", ";
		myString += this.getValor();
		myString += ", ";
		myString += this.getExpresionRegular();
		myString += '}';
		
		return myString;
	}
}
