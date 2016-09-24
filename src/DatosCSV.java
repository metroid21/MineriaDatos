import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

import javax.swing.table.DefaultTableModel;

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
	
	/**
	 * Camino hasta llegar al archivo cargado
	 */
	private String caminoArchivo = "";
	
	public DatosCSV() 
	{
		this.nombreArchivo = "";
		this.caminoArchivo = "";
		this.atributos     = new RegistroCSV();
		this.datos         = new LinkedList<RegistroCSV>();
		this.cambios       = new LinkedList<Cambio>();
	}

	public DatosCSV(String archivo) 
	{
		this.nombreArchivo = archivo;
		this.caminoArchivo = "";
		this.atributos     = new RegistroCSV();
		this.datos         = new LinkedList<RegistroCSV>();
		this.cambios       = new LinkedList<Cambio>();
	}

	public DatosCSV(RegistroCSV atributos, LinkedList<RegistroCSV> datos, LinkedList<Cambio> cambios) 
	{
		this.nombreArchivo = "";
		this.caminoArchivo = "";		
		this.atributos     = atributos;
		this.datos         = datos;
		this.cambios       = cambios;
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
	
	public String getCaminoArchivo() 
	{
		return caminoArchivo;
	}

	public void setCaminoArchivo(String caminoArchivo) 
	{
		this.caminoArchivo = caminoArchivo;
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
	
	public void actualizarFromJTable()
	{
		
	}
	
	public String[] getAtributosAsStringArray()
	{
		String arreglo[] = new String[this.atributos.getNodos().size()];
		
		for (int i = 0; i < this.atributos.getNodos().size(); i++)
		{
			arreglo[i] = this.atributos.getNodos().get(i).getValor();
		}
		
		return arreglo;
	}
	
	public Object[][] getDatosAsObjectArray()
	{
		Object arreglo[][] = new String[this.datos.size()][this.atributos.getNodos().size()];
		
		for (int i = 0; i < this.datos.size(); i++)
		{
			for (int j = 0; j < this.datos.get(i).getNodos().size(); j++)
			{
				arreglo[i][j] = this.datos.get(i).getNodos().get(j).getValor();
			}
		}
				
		return arreglo;
	}
	
	public DefaultTableModel getDatosAsTableModel()
	{
		DefaultTableModel modelo = new DefaultTableModel();
		
		for (int i = 0; i < this.atributos.getNodos().size(); i++)
		{
			modelo.addColumn(this.atributos.getNodos().get(i).getValor());
		}

		for (int i = 0; i < this.datos.size(); i++)
		{
			Object[] newRow = new Object[this.datos.get(i).getNodos().size()];
			for (int j = 0; j < this.datos.get(i).getNodos().size(); j++)
			{
				newRow[j] = this.datos.get(i).getNodos().get(j).getValor();
			}
			modelo.addRow(newRow);
		}
				
		return modelo;
	}	
}
