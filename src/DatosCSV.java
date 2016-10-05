import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Comparator;
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
			
			Object[] totalRow = {"Registros Distintos", String.format("%05d", contadorPosiciones.size())};
			modelo.addRow(totalRow);

			Object[] mediaRow = {"Media", this.getMedia(nombreAtributo)};
			modelo.addRow(mediaRow);

			Object[] medianaRow = {"Mediana", this.getMediana(nombreAtributo)};
			modelo.addRow(medianaRow);

			Object[] modaRow = {"Moda", this.getModa(nombreAtributo)};
			modelo.addRow(modaRow);

			Object[] desviacionRow = {"Desviacion", this.getDesviacionEstandar(nombreAtributo)};
			modelo.addRow(desviacionRow);

			Object[] minimoRow = {"Minimo", this.getMenor(nombreAtributo)};
			modelo.addRow(minimoRow);

			Object[] maximoRow = {"Maximo", this.getMayor(nombreAtributo)};
			modelo.addRow(maximoRow);

			for (int i = 0; i < datosAtributo.size(); i++)
			{
				Object[] newRow = {datosAtributo.get(i), String.format("%05d", contadorPosiciones.get(i))};
				modelo.addRow(newRow);
			}
			
			return modelo;
		}
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
			NodoCSV nodoAtributo = new NodoCSV();
			nodoAtributo.setEliminado(false);
			nodoAtributo.setId(this.atributos.getNodos().size()+1);
			nodoAtributo.setValor(nombreFinal);
			
			this.atributos.getNodos().add(nodoAtributo);
			
			nuevoCambio.getNodos().add(new NodoCambios(0, nodoAtributo.getId(), "true", "false"));
			
			//Le agregamos el atributo a todos los nodos
			int tamAtributos = this.atributos.getNodos().size();
			
			for (int i = 0; i < this.datos.size(); i++)
			{
				NodoCSV nodo = new NodoCSV(tamAtributos, 4, "", false);
				this.datos.get(i).getNodos().add(nodo);
				nuevoCambio.getNodos().add(new NodoCambios(nodo.getId(), tamAtributos, "true", "false"));
			}			
			
			this.cambios.add(nuevoCambio);
		}
	}
	
	private int atributoStringToIndex(String nombreAtributo)
	{
		int indexAtributo = -1;
		if (nombreAtributo.trim() != "")
		{
			for (int i = 0; i < this.atributos.getNodos().size(); i++)
			{
				if (this.atributos.getNodos().get(i).getValor() == nombreAtributo ||
					this.atributos.getNodos().get(i).toString() == nombreAtributo)
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
				}
				
				if (nodo.getTipo() != 3 && nodo.getTipo() != 5)
				{
					return -777777;
				}
				else
				{
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
				
			for (int i = 0; i < this.datos.size(); i++)
			{
				NodoCSV nodo = datos.get(i).getNodos().get(indexAtributo);

				if (nodo.isEliminado())
				{
					continue;
				}
				
				if (nodo.getTipo() != 3 && nodo.getTipo() != 5)
				{
					return -777777;
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
			
			for (int i = 0; i < datos.size(); i++)
			{
				NodoCSV nodo = datos.get(i).getNodos().get(indexAtributo);
				
				if (nodo.getTipo() != 3 && nodo.getTipo() != 5)
				{
					return -777777;
				}
				else
				{
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
			
			for (int i = 0; i < datos.size(); i++)
			{
				NodoCSV nodo = datos.get(i).getNodos().get(indexAtributo);

				if (nodo.getTipo() != 3 && nodo.getTipo() != 5)
				{
					return -777777;
				}
				else
				{

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
				}

				if (nodo.getTipo() != 3 && nodo.getTipo() != 5)
				{
					return -777777;
				}
				else
				{
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

}
