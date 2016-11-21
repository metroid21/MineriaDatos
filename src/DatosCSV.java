import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

public class DatosCSV 
{    
	/**
	 * Registro con los atributos
	 */
	private RegistroCSV<NodoAtributo> atributos = null;
	/**
	 * Lista de registro con los datos
	 */
	private LinkedList<RegistroCSV<NodoCSV>> datos = null;
	
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
	
	/**
	 * String con el nombre de la clase para los algoritmos
	 */
	private String nombreClase = "";
		
	public DatosCSV() 
	{
		this.nombreArchivo = "";
		this.caminoArchivo = "";
		this.atributos     = new RegistroCSV<NodoAtributo>();
		this.datos         = new LinkedList<RegistroCSV<NodoCSV>>();
		this.cambios       = new LinkedList<Cambio>();
	}

	public DatosCSV(String archivo) 
	{
		this.nombreArchivo = archivo;
		this.caminoArchivo = "";
		this.atributos     = new RegistroCSV<NodoAtributo>();
		this.datos         = new LinkedList<RegistroCSV<NodoCSV>>();
		this.cambios       = new LinkedList<Cambio>();
	}

	public DatosCSV(RegistroCSV<NodoAtributo> atributos, LinkedList<RegistroCSV<NodoCSV>> datos, LinkedList<Cambio> cambios) 
	{
		this.nombreArchivo = "";
		this.caminoArchivo = "";		
		this.atributos     = atributos;
		this.datos         = datos;
		this.cambios       = cambios;
	}

	public RegistroCSV<NodoAtributo> getAtributos() 
	{
		return atributos;
	}
	
	public void setAtributos(RegistroCSV<NodoAtributo> atributos) 
	{
		this.atributos = atributos;
	}
	
	public LinkedList<RegistroCSV<NodoCSV>> getDatos() 
	{
		return datos;
	}
	
	public void setDatos(LinkedList<RegistroCSV<NodoCSV>> datos) 
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

	public String getNombreClase() 
	{
		return nombreClase;
	}

	public void setNombreClase(String nombreClase) 
	{
		this.nombreClase = nombreClase;
	}

	public String toString()
	{	
		String valorResultante = "";
		
		//Recuperamos los atributos
		if (this.atributos != null)
		{
			valorResultante = "Atributos:\n";
			
			LinkedList<NodoAtributo> listaAtributos = this.atributos.getNodos();
			
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
				
				LinkedList<NodoAtributo> listaAtributos = this.atributos.getNodos();
				
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

	public void escribirArchivoDelimitadores(String nombreArchivo)
	{	
		PrintWriter writer;
		
		try 
		{
			writer = new PrintWriter(nombreArchivo, "UTF-8");
			
			//Recuperamos los atributos
			if (this.atributos != null)
			{			
				LinkedList<NodoAtributo> listaAtributos = this.atributos.getNodos();
				
				for (int i = 0; i < listaAtributos.size(); i++)
				{
					writer.print(listaAtributos.get(i).getId());
					writer.print('|'); 
					writer.print(listaAtributos.get(i).getValor());
					writer.print('|');
					writer.print(listaAtributos.get(i).getTipo());
					writer.print('|');
					writer.print('"');
					writer.print(listaAtributos.get(i).getExpresionRegular());
					writer.print('"');
					writer.print('|');
					writer.print(listaAtributos.get(i).isEliminado());
					writer.print('|');
					writer.print("~");
				}
			}
			else
			{
				writer.print('|');
				writer.print('|');
				writer.print('|');
				writer.print('|');
				writer.print('|');
				writer.print("~");
			}
			
			writer.print('%');
			
			//Recuperamos los datos
			if (this.datos != null)
			{
				for (int j = 0; j < this.datos.size(); j++)
				{
					LinkedList<NodoCSV> nodos = this.datos.get(j).getNodos();
					
					for (int i = 0; i < nodos.size(); i++)
					{
						writer.print(nodos.get(i).getId());
						writer.print('|');
						writer.print(nodos.get(i).getValor());
						writer.print('|');
						writer.print(nodos.get(i).isEliminado());
						writer.print('|');
						writer.print("~");
					}
					
					writer.print('*');
				}
			}
			else
			{
				writer.print('|');
				writer.print('|');
				writer.print('|');
				writer.print("~");
				writer.print('*');
			}
			
			writer.print('%');

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
				LinkedList<NodoAtributo> listaAtributos = this.atributos.getNodos();
				
				String atrData = "";
				for (int i = 0; i < listaAtributos.size(); i++)
				{
					if (!listaAtributos.get(i).isEliminado())
					{
						atrData += listaAtributos.get(i).getValor()  + ",";
					}
				}
				
				atrData = atrData.substring(0, atrData.length()-1);
				writer.println(atrData);
			}
						 
			//Recuperamos los datos
			if (this.datos != null)
			{
				for (int j = 0; j < this.datos.size(); j++)
				{
					LinkedList<NodoCSV> nodos = this.datos.get(j).getNodos();

					String regData = "";
					
					for (int i = 0; i < nodos.size(); i++)
					{
						if (!nodos.get(i).isEliminado())
						{
							regData += nodos.get(i).getValor() + ",";
						}
					}
					
					regData = regData.substring(0, regData.length()-1);
					
					writer.println(regData);
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
	
	private Integer getCantidadAtributos()
	{
		int cantidad = 0;
		
		for (int i = 0; i < this.atributos.getNodos().size(); i++)
		{
			if (!this.atributos.getNodos().get(i).isEliminado())
			{
				cantidad++;
			}
		}
			
		return cantidad;
	}

	private Integer getCantidadDatos()
	{
		int cantidad = 0;
		
		for (int i = 0; i < this.datos.size(); i++)
		{			
			for (int j = 0; j < this.datos.get(i).getNodos().size(); j++)
			{
				if (!this.datos.get(i).getNodos().get(j).isEliminado())
				{
					cantidad++;
					break;
				}
			}
		}
			
		return cantidad;
	}
	
	public boolean actualizarFromCellJTable(int row, int col, String value)
	{
		for (int i = 0, k = 0; i < this.datos.size(); i++)
		{
			boolean eliminado = false;
			for (int j = 0, l = 0; j < this.datos.get(i).getNodos().size(); j++)
			{
				if (!this.datos.get(i).getNodos().get(j).isEliminado())
				{
					if (row == k && col == l)
					{
						String valorA = this.datos.get(i).getNodos().get(j).getValor();
						
						if (valorA == value)
						{
							return false;
						}
						else
						{
							//Creamos el Cambio
							Cambio nuevoCambio = null;
							
							if (cambios.isEmpty())
							{
								nuevoCambio = new Cambio(1, 3);
							}
							else
							{
								nuevoCambio = new Cambio(cambios.size()+1, 3);
							}
							
							NodoCambios nodoC = new NodoCambios(i,j,valorA,value);
							nuevoCambio.getNodos().add(nodoC);
							this.cambios.add(nuevoCambio);
							
							this.datos.get(i).getNodos().get(j).setValor(value);
							return true;
						}
					}
					
					l++;
				}
				else
				{
					eliminado = true;
				}
			}
			
			if (!eliminado)
			{
				k++;
			}
		}
		
		return false;
	}
	
	public String getExpresionRegular(String nombreAtributo)
	{
		for (int i = 0; i < this.atributos.getNodos().size(); i++)
		{
			if (!this.atributos.getNodos().get(i).isEliminado() && 
				 this.atributos.getNodos().get(i).getValor().equals(nombreAtributo))
			{
				return this.atributos.getNodos().get(i).getExpresionRegular();
			}
		}
		
		return "";
	}

	public Integer getTipo(String nombreAtributo)
	{
		for (int i = 0; i < this.atributos.getNodos().size(); i++)
		{
			if (!this.atributos.getNodos().get(i).isEliminado() && 
				 this.atributos.getNodos().get(i).getValor().equals(nombreAtributo))
			{
				return this.atributos.getNodos().get(i).getTipo();
			}
		}
		
		return -1;
	}

	public Integer getPosicionAtributo(String nombreAtributo)
	{
		for (int i = 0; i < this.atributos.getNodos().size(); i++)
		{
			if (!this.atributos.getNodos().get(i).isEliminado() && 
				 this.atributos.getNodos().get(i).getValor().equals(nombreAtributo))
			{
				return i;
			}
		}
		
		return -1;
	}

	public void setExpresionRegular(String nombreAtributo, String nuevaExpresion)
	{		
		for (int k = 0; k < this.atributos.getNodos().size(); k++)
		{			
			if ((!this.atributos.getNodos().get(k).isEliminado()) && 
			   (this.atributos.getNodos().get(k).getValor().equals(nombreAtributo)))
			{				
				this.atributos.getNodos().get(k).setExpresionRegular(nuevaExpresion);
				break;
			}
		}
	}

	public void setTipo(String nombreAtributo, Integer nuevoTipo)
	{
		for (int i = 0; i < this.atributos.getNodos().size(); i++)
		{
			if (!this.atributos.getNodos().get(i).isEliminado() && 
				 this.atributos.getNodos().get(i).getValor().equals(nombreAtributo))
			{
				this.atributos.getNodos().get(i).setTipo(nuevoTipo);
				break;
			}
		}
	}

	public String[] getAtributosAsStringArray()
	{
		LinkedList<String> arreglo = new LinkedList<String>();
		
		for (int i = 0; i < this.atributos.getNodos().size(); i++)
		{
			if (!this.atributos.getNodos().get(i).isEliminado())
			{
				arreglo.add(this.atributos.getNodos().get(i).getValor());
			}
		}
		
		String[] arregloDef = new String[arreglo.size()];
		
		for (int i = 0; i < arreglo.size(); i++)
		{
			arregloDef[i] = arreglo.get(i);
		}
		
		return arregloDef;
	}
	
	public Object[][] getDatosAsObjectArray()
	{
		Object arreglo[][] = new String[this.getCantidadDatos()][this.getCantidadAtributos()];
		
		for (int i = 0, k = 0; i < this.datos.size(); i++)
		{
			boolean eliminado = false;
			for (int j = 0, l = 0; j < this.datos.get(i).getNodos().size(); j++)
			{
				if (!this.datos.get(i).getNodos().get(j).isEliminado())
				{
					arreglo[k][l] = this.datos.get(i).getNodos().get(j).getValor();
					l++;
				}
				else
				{
					eliminado = true;
				}
			}
			
			if (!eliminado)
			{
				k++;
			}
		}
				
		return arreglo;
	}
	
	public DefaultTableModel getDatosAsTableModel()
	{
		DefaultTableModel modelo = new DefaultTableModel();
		
		for (int i = 0; i < this.atributos.getNodos().size(); i++)
		{
			if (!this.atributos.getNodos().get(i).isEliminado())
			{
				modelo.addColumn(this.atributos.getNodos().get(i).getValor());
			}
			
		}
		for (int i = 0; i < this.datos.size(); i++)
		{
			Object[] newRow = new Object[this.datos.get(i).getNodos().size()];
			int k = 0;
			boolean eliminado = true;

			for (int j = 0; j < this.datos.get(i).getNodos().size(); j++)
			{
				if (!this.datos.get(i).getNodos().get(j).isEliminado())
				{
					newRow[k] = this.datos.get(i).getNodos().get(j).getValor();
					k++;
					eliminado = false;
				}
			}
			if (!eliminado)
			{
				modelo.addRow(newRow);
			}
			
		}		
		return modelo;
	}	
	
		
	public DefaultTableModel getTablaFrecuencia(String nombreAtributo)
	{
		int indexAtributos = this.atributoStringToIndex(nombreAtributo);
		
		if (indexAtributos < 0 || indexAtributos > this.atributos.getNodos().size())
		{
			DefaultTableModel modelo = new DefaultTableModel();

			modelo.addColumn("Descripcion");
			modelo.addColumn("Datos de Descripcion");

			Object[] newRow = {"Total Registros ", this.getCantidadDatos()};
			modelo.addRow(newRow);
			
			Object[] newRow1 = {"Total Atributos ", this.getCantidadAtributos()};
			modelo.addRow(newRow1);
			
			Object[] newRow2 = {"Archivo CSV ", this.getNombreArchivo()};
			modelo.addRow(newRow2);
			
			Object[] newRow3 = {"Frontend", "Luis Fernando Guiterrez Gonzalez"};
			modelo.addRow(newRow3);

			Object[] newRow4 = {"Backend", "Jonathan Elias Sandoval Talamantes"};
			modelo.addRow(newRow4);

			Object[] newRow5 = {"Clase", "Mineria de Datos, CUCEI"};
			modelo.addRow(newRow5);

			return modelo;
		}
		else
		{
			LinkedList<String>  datosAtributo      = new LinkedList<String>();
			LinkedList<Integer> contadorPosiciones = new LinkedList<Integer>();
				
			for (int i = 0; i < this.datos.size(); i++)
			{
				if (this.datos.get(i).getNodos().get(indexAtributos).isEliminado())
				{
					continue;
				}
				
				String valor = this.datos.get(i).getNodos().get(indexAtributos).getValor();
				int pos = datosAtributo.indexOf(valor);
				
				if (pos >= 0)
				{
					contadorPosiciones.set(pos, contadorPosiciones.get(pos)+1);
				}
				else
				{
					contadorPosiciones.add(1);
					datosAtributo.add(valor);
				}
			}
	
			DefaultTableModel modelo = new DefaultTableModel();
			
			modelo.addColumn("Atributo");
			modelo.addColumn("Repeticiones");
			
			for (int i = 0; i < datosAtributo.size(); i++)
			{
				Object[] newRow = {datosAtributo.get(i), String.format("%05d", contadorPosiciones.get(i))};
				modelo.addRow(newRow);
			}
			
			return modelo;
		}
	}
	
	public DefaultTableModel getTablaEstadistica(String nombreAtributo)
	{
		int indexAtributos = this.atributoStringToIndex(nombreAtributo);
		
		if (indexAtributos < 0 || indexAtributos > this.atributos.getNodos().size())
		{
			DefaultTableModel modelo = new DefaultTableModel();

			modelo.addColumn("Descripcion");
			modelo.addColumn("Datos de Descripcion");

			Object[] newRow = {"Total Registros ", this.getCantidadDatos()};
			modelo.addRow(newRow);
			
			Object[] newRow1 = {"Total Atributos ", this.getCantidadAtributos()};
			modelo.addRow(newRow1);
			
			Object[] newRow2 = {"Archivo CSV ", this.getNombreArchivo()};
			modelo.addRow(newRow2);
			
			Object[] newRow3 = {"Frontend", "Luis Fernando Guiterrez Gonzalez"};
			modelo.addRow(newRow3);

			Object[] newRow4 = {"Backend", "Jonathan Elias Sandoval Talamantes"};
			modelo.addRow(newRow4);

			Object[] newRow5 = {"Clase", "Mineria de Datos, CUCEI"};
			modelo.addRow(newRow5);

			return modelo;
		}
		else
		{
			LinkedList<String>  datosAtributo      = new LinkedList<String>();
			LinkedList<Integer> contadorPosiciones = new LinkedList<Integer>();
				
			for (int i = 0; i < this.datos.size(); i++)
			{
				if (this.datos.get(i).getNodos().get(indexAtributos).isEliminado())
				{
					continue;
				}
				
				String valor = this.datos.get(i).getNodos().get(indexAtributos).getValor();
				int pos = datosAtributo.indexOf(valor);
				
				if (pos >= 0)
				{
					contadorPosiciones.set(pos, contadorPosiciones.get(pos)+1);
				}
				else
				{
					contadorPosiciones.add(1);
					datosAtributo.add(valor);
				}
			}
	
			DefaultTableModel modelo = new DefaultTableModel();
			
			modelo.addColumn("Descripcion");
			modelo.addColumn("Datos de Descripcion");
			
			Object[] totalRow = {"Registros Distintos", contadorPosiciones.size()};
			modelo.addRow(totalRow);

			double val = this.getMedia(nombreAtributo);
			if (val == -777777)
			{
				Object[] mediaRow = {"Media", "No Calculable"};
				modelo.addRow(mediaRow);
			}
			else
			{
				Object[] mediaRow = {"Media", val};
				modelo.addRow(mediaRow);
			}

			val = this.getMediana(nombreAtributo);
			if (val == -777777)
			{
				Object[] mediaRow = {"Mediana", "No Calculable"};
				modelo.addRow(mediaRow);
			}
			else
			{
				Object[] mediaRow = {"Mediana", val};
				modelo.addRow(mediaRow);
			}

			Object[] modaRow = {"Moda", this.getModa(nombreAtributo)};
			modelo.addRow(modaRow);

			val = this.getDesviacionEstandar(nombreAtributo);
			if (val == -777777)
			{
				Object[] mediaRow = {"Desviacion", "No Calculable"};
				modelo.addRow(mediaRow);
			}
			else
			{
				Object[] mediaRow = {"Desviacion", val};
				modelo.addRow(mediaRow);
			}

			val = this.getMenor(nombreAtributo);
			if (val == -777777)
			{
				Object[] mediaRow = {"Minimo", "No Calculable"};
				modelo.addRow(mediaRow);
			}
			else
			{
				Object[] mediaRow = {"Minimo", val};
				modelo.addRow(mediaRow);
			}

			val = this.getMayor(nombreAtributo);
			if (val == -777777)
			{
				Object[] mediaRow = {"Maximo", "No Calculable"};
				modelo.addRow(mediaRow);
			}
			else
			{
				Object[] mediaRow = {"Maximo", val};
				modelo.addRow(mediaRow);
			}
			
			return modelo;
		}
	}

	public LinkedList<String> getDistintos(String atributo)
	{
		LinkedList<String> valores = new LinkedList<String>();
		
		int pos = this.getPosicionAtributo(atributo);
		
		if (pos != -1)
		{
			for (int i = 0; i < this.datos.size(); i++)
			{
				RegistroCSV<NodoCSV> reg = this.datos.get(i); 
				NodoCSV nodo = reg.getNodos().get(pos);
				
				if (!nodo.isEliminado())
				{
					if (valores.indexOf(nodo.getValor()) < 0)
					{
						valores.add(nodo.getValor());
					}
				}
			}
		}
		
		return valores;
	}
	
	public void agregarAtributo(String nombreNuevo)
	{
		if (nombreNuevo.trim() != "")
		{
			String nombreFinal = nombreNuevo.replace(' ', '_');
			
			//Creamos el Cambio
			Cambio nuevoCambio = null;
			
			if (cambios.isEmpty())
			{
				nuevoCambio = new Cambio(1, 1);
			}
			else
			{
				nuevoCambio = new Cambio(cambios.size()+1, 1);
			}

			//Agregamos el cambio a los atributos
			NodoAtributo nodoAtributo = new NodoAtributo();
			nodoAtributo.setEliminado(false);
			nodoAtributo.setId(this.atributos.getNodos().size()+1);
			nodoAtributo.setValor(nombreFinal);
			
			this.atributos.getNodos().add(nodoAtributo);
			
			nuevoCambio.getNodos().add(new NodoCambios(0, nodoAtributo.getId(), "true", "false"));
			
			//Le agregamos el atributo a todos los nodos
			int tamAtributos = this.atributos.getNodos().size();
			
			for (int i = 0; i < this.datos.size(); i++)
			{
				NodoCSV nodo = new NodoCSV(tamAtributos, "", false);
				this.datos.get(i).getNodos().add(nodo);
				nuevoCambio.getNodos().add(new NodoCambios(nodo.getId(), tamAtributos, "true", "false"));
			}			
			
			this.cambios.add(nuevoCambio);
		}
	}
	
	public void eliminarRegistro(int indice)
	{	
		//Creamos el Cambio
		Cambio nuevoCambio = null;
		
		if (cambios.isEmpty())
		{
			nuevoCambio = new Cambio(1, 1);
		}
		else
		{
			nuevoCambio = new Cambio(cambios.size()+1, 1);
		}
		
		int k=-1;
		for (int j = 0; j < this.datos.size(); j++)
		{
			if (!this.datos.get(j).getNodos().get(0).isEliminado())
			{
				k++;
			}
						
			if (k == indice)
			{
				for (int a = 0; a < this.datos.get(j).getNodos().size(); a++)
				{
					this.datos.get(j).getNodos().get(a).setEliminado(true);
					
					NodoCambios cambioS = new NodoCambios(j,a,"false", "true");
					nuevoCambio.getNodos().add(cambioS);
				}
				
				break;
			}
		}
		
		this.cambios.add(nuevoCambio);			
	}
	
	public void agregarRegistro()
	{
		RegistroCSV<NodoCSV> registroCompleto = new RegistroCSV<NodoCSV>();
		
		//Creamos el Cambio
		Cambio nuevoCambio = null;
		if (cambios.isEmpty())
		{
			nuevoCambio = new Cambio(1, 1);
		}
		else
		{
			nuevoCambio = new Cambio(cambios.size()+1, 1);
		}

		registroCompleto.setId(this.getDatos().getLast().getId()+1);
		
		for (int i = 0; i < this.getDatos().getLast().getNodos().size(); i++)
		{
			NodoCambios nodoC = new NodoCambios(registroCompleto.getId(), i, "false", "true");
			NodoCSV nodo = new NodoCSV();
			nodo.setEliminado(false);
			nodo.setId(i);
			nodo.setValor("");
			
			registroCompleto.getNodos().add(nodo);
			nuevoCambio.getNodos().add(nodoC);
		}
		

		this.datos.add(registroCompleto);
		this.cambios.add(nuevoCambio);	
		
	}
	
	public void imprimirFilas(){
		//Total de registros this.datos.size();
		//Total de columnas this.datos.get(i).getNodos().size()
		//Agarrar ultimo nodo this.datos.getLast.getNodos()
		for (int i = 0; i < this.datos.size(); i++)
		{
			
			for (int j = 0; j < this.datos.get(i).getNodos().size(); j++)
			{
			
				if (!this.datos.get(i).getNodos().get(j).isEliminado())
				{
					System.out.println(this.datos.get(i).getNodos().get(j).getValor());
					//this.datos.addLast(e);
				}
			}
		}		
	}
	
	private int atributoStringToIndex(String nombreAtributo)
	{
		int indexAtributo = -1;
		if (nombreAtributo.trim() != "")
		{
			for (int i = 0; i < this.atributos.getNodos().size(); i++)
			{
				if (this.atributos.getNodos().get(i).getValor().equals(nombreAtributo) ||
					this.atributos.getNodos().get(i).toString().equals(nombreAtributo))
				{
					indexAtributo = i;
					break;
				}
			}
		}

		return indexAtributo;
	}
	
	public void eliminarAtributo(String nombreAtributo)
	{		
		int indexAtributo = this.atributoStringToIndex(nombreAtributo);
		
		if (indexAtributo >= 0 && indexAtributo < this.atributos.getNodos().size())
		{			
			//Creamos el Cambio
			Cambio nuevoCambio = null;
			
			if (cambios.isEmpty())
			{
				nuevoCambio = new Cambio(1, 1);
			}
			else
			{
				nuevoCambio = new Cambio(cambios.size()+1, 1);
			}

			//Agregamos el cambio a los atributos
			this.atributos.getNodos().get(indexAtributo).setEliminado(true);
			nuevoCambio.getNodos().add(new NodoCambios(0, indexAtributo, "false", "true"));
			
			//Actualizamos a los datos		
			for (int i = 0; i < this.datos.size(); i++)
			{
				this.datos.get(i).getNodos().get(indexAtributo).setEliminado(true);
				nuevoCambio.getNodos().add(new NodoCambios(i, indexAtributo, "false", "true"));
			}			
			
			this.cambios.add(nuevoCambio);			
		}
	}
	
	//Funciones Estadísticas
	public double getMedia(String nombreAtributo)
	{
		int indexAtributo = this.atributoStringToIndex(nombreAtributo);
		
		if (indexAtributo >= 0 && indexAtributo < this.atributos.getNodos().size())
		{
			double promedio  = 0;
			int cantidadFila = 0;
			
			if (this.atributos.getNodos().get(indexAtributo).getTipo() != 3)
			{
				return -777777;
			}
			
			for (int i = 0; i < datos.size(); i++)
			{
				NodoCSV nodo = datos.get(i).getNodos().get(indexAtributo);
				
				if (nodo.isEliminado())
				{
					continue;
				}
				else
				{
					cantidadFila++;
					promedio = promedio + Double.parseDouble(nodo.getValor());
				}
			}
			
			promedio = promedio / cantidadFila;
			
			return promedio;
		}
		
		return -777777;
	}
	
	public String getModa(String nombreAtributo)
	{
		int indexAtributo = this.atributoStringToIndex(nombreAtributo);
		
		if (indexAtributo >= 0 && indexAtributo < this.atributos.getNodos().size())
		{
			LinkedList<String>  datosAtributo      = new LinkedList<String>();
			LinkedList<Integer> contadorPosiciones = new LinkedList<Integer>();
				
			for (int i = 0; i < this.datos.size(); i++)
			{
				if (this.datos.get(i).getNodos().get(indexAtributo).isEliminado())
				{
					continue;
				}
				
				String valor = this.datos.get(i).getNodos().get(indexAtributo).getValor();
				int pos = datosAtributo.indexOf(valor);
				
				if (pos >= 0)
				{
					contadorPosiciones.set(pos, contadorPosiciones.get(pos)+1);
				}
				else
				{
					contadorPosiciones.add(1);
					datosAtributo.add(valor);
				}
			}
			
			int indexMayor = 0;
			double mayor   = contadorPosiciones.get(0);
			
			for (int i = 1; i < contadorPosiciones.size(); i++)
			{
				if (contadorPosiciones.get(i) > mayor)
				{
					mayor = contadorPosiciones.get(i);
					indexMayor = i;
				}
			}
			
			return datosAtributo.get(indexMayor);
		}
		
		return "No Existe";
	}

	public double getMediana(String nombreAtributo)
	{
		int indexAtributo = this.atributoStringToIndex(nombreAtributo);
		
		if (indexAtributo >= 0 && indexAtributo < this.atributos.getNodos().size())
		{
			LinkedList<Double>  datosAtributo = new LinkedList<Double>();
				
			if (this.atributos.getNodos().get(indexAtributo).getTipo() != 3)
			{
				return -777777;
			}

			for (int i = 0; i < this.datos.size(); i++)
			{
				NodoCSV nodo = datos.get(i).getNodos().get(indexAtributo);

				if (nodo.isEliminado())
				{
					continue;
				}
								
				datosAtributo.add(Double.parseDouble(nodo.getValor()));
			}

			Collections.sort(datosAtributo, new Comparator<Double>() 
			{
		         @Override
		         public int compare(Double o1, Double o2) 
		         {
		             if (o1 > o2)
		             {
		            	 return -1;
		             }
		             else if (o1 < o2)
		             {
		            	 return 1;
		             }
		             else
		             {
		            	 return 0;
		             }
		         }
		    });
						
			return datosAtributo.get(datosAtributo.size()/2);
		}
		
		return -777777;
	}

	public double getMenor(String nombreAtributo)
	{
		int indexAtributo = this.atributoStringToIndex(nombreAtributo);
		
		if (indexAtributo >= 0 && indexAtributo < this.atributos.getNodos().size())
		{
			double menor = 0;
			
			if (this.atributos.getNodos().get(indexAtributo).getTipo() != 3)
			{
				return -777777;
			}

			for (int i = 0; i < datos.size(); i++)
			{
				NodoCSV nodo = datos.get(i).getNodos().get(indexAtributo);
				
				if (i == 0)
				{
					menor =  Float.parseFloat(nodo.getValor());
					continue;
				}
 
				if (!nodo.isEliminado() && menor > Float.parseFloat(nodo.getValor()))
				{
					menor = Float.parseFloat(nodo.getValor());
				}
			}
						
			return menor;
		}
		
		return -777777;
	}

	public double getMayor(String nombreAtributo)
	{
		int indexAtributo = this.atributoStringToIndex(nombreAtributo);
		
		if (indexAtributo >= 0 && indexAtributo < this.atributos.getNodos().size())
		{
			double mayor = 0;
			
			if (this.atributos.getNodos().get(indexAtributo).getTipo() != 3)
			{
				return -777777;
			}

			for (int i = 0; i < datos.size(); i++)
			{
				NodoCSV nodo = datos.get(i).getNodos().get(indexAtributo);

				if (i == 0)
				{
					mayor =  Float.parseFloat(nodo.getValor());
					continue;
				}				
				
				if (!nodo.isEliminado() && mayor < Float.parseFloat(nodo.getValor()))
				{
					mayor = Float.parseFloat(nodo.getValor());
				}
			}
						
			return mayor;
		}
		
		return -777777;
	}

	public double getDesviacionEstandar(String nombreAtributo)
	{
		int indexAtributo = this.atributoStringToIndex(nombreAtributo);
		
		if (indexAtributo >= 0 && indexAtributo < this.atributos.getNodos().size())
		{
			double promedio = this.getMedia(nombreAtributo);
			double acumulacion = 0;
			int cantidadFilas  = 0;

			if (this.atributos.getNodos().get(indexAtributo).getTipo() != 3)
			{
				return -777777;
			}

			for (int i = 0; i < datos.size(); i++)
			{
				NodoCSV nodo = datos.get(i).getNodos().get(indexAtributo);
				
				if (nodo.isEliminado())
				{
					continue;
				}
				else
				{
					cantidadFilas++;
					float valor = Float.parseFloat(nodo.getValor());
					acumulacion = acumulacion + Math.pow((valor-promedio),2);
				}
			}
			
			acumulacion = (acumulacion/(cantidadFilas-1));
			acumulacion = Math.sqrt(acumulacion);
						
			return acumulacion;
		}
		
		return -777777;
	}
	
	public DefaultTableModel filtrarDatos(String nombreAtributo)
	{
		int indexAtributo = this.atributoStringToIndex(nombreAtributo);
		
		if (indexAtributo >= 0 && indexAtributo < this.atributos.getNodos().size())
		{
			String gramatica = this.atributos.getNodos().get(indexAtributo).getExpresionRegular();
			Pattern pat = Pattern.compile(gramatica);
			
			DefaultTableModel modelo = new DefaultTableModel()
			{
				@Override
			    public boolean isCellEditable(int row, int column) 
				{
					if (column == 0 || column == 1)
					{
						return false;
					}
					else
					{
						return true;
					}
			    }
			};
			
			modelo.addColumn("Registro No.");
			modelo.addColumn("Nodo No.");
			modelo.addColumn("Valor");
			
			for (int i = 0; i < this.datos.size(); i++)
			{
				Object[] newRow = new Object[3];
				boolean eliminado = true;

				if (!this.datos.get(i).getNodos().get(indexAtributo).isEliminado())
				{
					String val = this.datos.get(i).getNodos().get(indexAtributo).getValor();
					Matcher mat = pat.matcher(val);
				    
				    if (!mat.matches())
				    {
						newRow[0] = i;
						newRow[1] = indexAtributo;
						newRow[2] = val;
						eliminado = false;					    	
				    }
				}
				
				if (!eliminado)
				{
					modelo.addRow(newRow);
				}
			}		
			
			return modelo;
		}
		
		return null;
	}

	public DefaultTableModel filtrarDatos(String nombreAtributo, boolean match)
	{
		int indexAtributo = this.atributoStringToIndex(nombreAtributo);
		
		if (indexAtributo >= 0 && indexAtributo < this.atributos.getNodos().size())
		{
			String gramatica = this.atributos.getNodos().get(indexAtributo).getExpresionRegular();
			Pattern pat = Pattern.compile(gramatica);
			
			DefaultTableModel modelo = new DefaultTableModel()
			{
				@Override
			    public boolean isCellEditable(int row, int column) 
				{
					if (column == 0 || column == 1)
					{
						return false;
					}
					else
					{
						return true;
					}
			    }
			};
			
			modelo.addColumn("Registro No.");
			modelo.addColumn("Nodo No.");
			modelo.addColumn("Valor");
			
			for (int i = 0; i < this.datos.size(); i++)
			{
				Object[] newRow = new Object[3];
				boolean eliminado = true;

				if (!this.datos.get(i).getNodos().get(indexAtributo).isEliminado())
				{
					String val = this.datos.get(i).getNodos().get(indexAtributo).getValor();						
					Matcher mat = pat.matcher(val);
				    
				    if (mat.matches() == match)
				    {
						newRow[0] = i;
						newRow[1] = indexAtributo;
						newRow[2] = val;
						eliminado = false;					    	
				    }
				}
				
				if (!eliminado)
				{
					modelo.addRow(newRow);
				}
			}		
			
			return modelo;
		}
		
		return null;
	}
}
