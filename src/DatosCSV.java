import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

public class DatosCSV 
{
	/**
	 * Registro con los atributos
	 */
	private RegistroCSV atributos = null;
	
	/**
	 * Lista de registro con los datos
	 */
	private LinkedList<RegistroCSV> datos = null;
	
	/**
	 * Lista de registros con los cambios realizados
	 */
	private LinkedList<Cambio> cambios = null;
	
	/**
	 * Nombre del archivo cargado
	 */
	private String nombreArchivo = "";
	
	public DatosCSV() 
	{
		this.nombreArchivo = "";
		this.atributos     = new RegistroCSV();
		this.datos         = new LinkedList<RegistroCSV>();
		this.cambios       = new LinkedList<Cambio>();
	}

	public DatosCSV(String archivo) 
	{
		this.setNombreArchivo(archivo);
		this.atributos     = new RegistroCSV();
		this.datos         = new LinkedList<RegistroCSV>();
		this.cambios       = new LinkedList<Cambio>();
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

	public String getNombreArchivo() 
	{
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) 
	{
		this.nombreArchivo = nombreArchivo;
	}
	
	public String toString()
	{	
		String valorResultante = "";
		
		//Recuperamos los atributos
		if (this.atributos != null)
		{
			valorResultante = "Atributos:\n";
			
			LinkedList<NodoCSV> listaAtributos = this.atributos.getNodos();
			
			for (int i = 0; i < listaAtributos.size(); i++)
			{
				valorResultante = valorResultante + "    " + listaAtributos.get(i) + "\n";
			}
			
			valorResultante += "\n";
		}
		else
		{
			valorResultante = "Sin Atributos\n";
		}
		
		System.out.println(valorResultante);
		
		//Recuperamos los datos
		if (this.datos != null)
		{
			for (int j = 0; j < this.datos.size(); j++)
			{
				LinkedList<NodoCSV> nodos = this.datos.get(j).getNodos();
				
				for (int i = 0; i < nodos.size(); i++)
				{
					valorResultante = valorResultante + "  " + nodos.get(i) + "\n";
				}
				
				valorResultante += "\n";
			}

			valorResultante += "\n";
		}
		else
		{
			valorResultante = "Sin Datos del CSV\n";
		}
		
		//System.out.println(valorResultante);
		
		return valorResultante;
	}

	public void escribirArchivoString(String nombreArchivo)
	{	
		PrintWriter writer;
		
		try 
		{
			writer = new PrintWriter(nombreArchivo, "UTF-8");
			
			//Recuperamos los atributos
			if (this.atributos != null)
			{
				writer.print("Atributos:\n");
				
				LinkedList<NodoCSV> listaAtributos = this.atributos.getNodos();
				
				for (int i = 0; i < listaAtributos.size(); i++)
				{
					writer.print("    " + listaAtributos.get(i) + "\n");
				}
				
				writer.print("\n");
			}
			else
			{
				writer.print("Sin Atributos\n");
			}
						
			//Recuperamos los datos
			if (this.datos != null)
			{
				for (int j = 0; j < this.datos.size(); j++)
				{
					LinkedList<NodoCSV> nodos = this.datos.get(j).getNodos();
					
					writer.print("  " + this.datos.get(j).getId() + "\n");
					
					for (int i = 0; i < nodos.size(); i++)
					{
						writer.print("    " + nodos.get(i) + "\n");
					}
					
					writer.print("\n");
				}

				writer.print("\n");
			}
			else
			{
				writer.print("Sin Datos del CSV\n");
			}

			writer.close();
		} 
		catch (FileNotFoundException e1) 
		{
			//e1.printStackTrace();
		} 
		catch (UnsupportedEncodingException e1) 
		{
			//e1.printStackTrace();
		}
	}
	
	public void escribirArchivoCSV(String nombreArchivo)
	{	
		PrintWriter writer;
		
		try 
		{
			writer = new PrintWriter(nombreArchivo, "UTF-8");
			
			//Recuperamos los atributos
			if (this.atributos != null)
			{				
				LinkedList<NodoCSV> listaAtributos = this.atributos.getNodos();
				
				for (int i = 0; i < listaAtributos.size(); i++)
				{
					if (i == listaAtributos.size()-1)
					{
						writer.print(listaAtributos.get(i).getValor());
					}
					else
					{
						writer.print(listaAtributos.get(i).getValor()  + ",");
					}
				}
				
				writer.print("\n");
			}
						 
			//Recuperamos los datos
			if (this.datos != null)
			{
				for (int j = 0; j < this.datos.size(); j++)
				{
					LinkedList<NodoCSV> nodos = this.datos.get(j).getNodos();
										
					for (int i = 0; i < nodos.size(); i++)
					{
						writer.print(nodos.get(i).getValor());
						
						if (i !=nodos.size()-1)
						{
							writer.print(",");
						}
					}
					
					if (j != this.datos.size()-1)
					{
						writer.print("\n");
					}
				}
			}
			else
			{
				writer.print("Sin Datos del CSV\n");
			}

			writer.close();
		} 
		catch (FileNotFoundException e1) 
		{
			//e1.printStackTrace();
		} 
		catch (UnsupportedEncodingException e1) 
		{
			//e1.printStackTrace();
		}
	}	
}
