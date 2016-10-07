import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
 
public class LectorCSV 
{ 
	DatosCSV cargarCSV(String nombreArchivo)
	{
		int iteracionesRegistro = 0;
		DatosCSV datosNuevos = new DatosCSV();
		
		try  
		{
			//Creamos las variables
			FileReader f = new FileReader(nombreArchivo);
			BufferedReader b = new BufferedReader(f);
			String cadena = ""; 
			RegistroCSV<NodoCSV> reg = null;
						
			NodoCSV myNodo = new NodoCSV();
			String valorNodo = ""; 

			//Cargamos el archivo CSV
		    while((cadena = b.readLine())!=null) 
		    {		    	
		    	String myCad = cadena;
		    	int estado = 1;
				reg = new RegistroCSV<NodoCSV>();
				
			    if (datosNuevos.getDatos().isEmpty())
			    {
			    	reg.setId(1);
			    }
			    else
			    {
			    	reg.setId(datosNuevos.getDatos().getLast().getId()+1);
			    }
		    									
				myCad += ",";
				
		        for (int i = 0; i < myCad.length(); i++)
		        {
		        	char myChar = myCad.charAt(i); 
		        	
		        	if (estado == 1)
		        	{
		        		if (Character.isSpaceChar(myChar))
						{
		        			estado = 1;
		        		}
		        		else if (Character.isDigit(myChar))
		        		{
		        			valorNodo += myChar;
		        			estado = 2;
		        		}
		        		else if (myChar == '+' || myChar == '-')
		        		{
		        			valorNodo += myChar;
		        			estado = 5;
		        		}
		        		else if (myChar == '\'' || myChar == '"')
		        		{
		        			estado = 4;
		        		}
		        		else if (myChar == ',')
		        		{
		        			estado = 8;
		        		}
		        		else
		        		{
		        			valorNodo += myChar;
		        			estado = 3;
		        		}
		        	}
		        	else if (estado == 2)
		        	{
		        		if (Character.isDigit(myChar))
		        		{
		        			valorNodo += myChar;
		        			estado = 2;
		        		}
		        		else if (myChar == '.')
		        		{
		        			valorNodo += myChar;
		        			estado = 6;
		        		}
		        		else if (myChar == ',')
		        		{
		        			estado = 8;
		        		}
		        		else
		        		{
		        			valorNodo += myChar;
		        			estado = 3;
		        		}
		        	}
		        	else if (estado == 3)
		        	{
		        		if (myChar == ',')
		        		{
		        			estado = 8;
		        		}
		        		else
		        		{
		        			valorNodo += myChar;
		        		}
		        	}
		        	else if (estado == 4)
		        	{
		        		if (myChar == '\'' || myChar == '"')
		        		{
		        			estado = 7;
		        		}
		        		else
		        		{
		        			valorNodo += myChar;
		        		}
		        	}
		        	else if (estado == 5)
		        	{
		        		if (Character.isDigit(myChar))
		        		{
		        			valorNodo += myChar;
		        			estado = 2;
		        		}
		        		else
		        		{
		        			estado = 3;
		        			valorNodo += myChar;
		        		}
		        	}
		        	else if (estado == 6)
		        	{
		        		if (Character.isDigit(myChar))
		        		{
		        			valorNodo += myChar;
		        		}
		        		else if (myChar == ',')
		        		{
		        			estado = 8;
		        		}
		        		else
		        		{
		        			estado = 3;
		        			valorNodo += myChar;
		        		}
		        	}
		        	else if (estado == 7)
		        	{
		        		if (myChar == ',')
		        		{
		        			estado = 8;
		        		}
		        	}
		        	else if (estado == 8)
		        	{
		        		myNodo.setValor(valorNodo.trim());
		        		myNodo.setEliminado(false);

		        		if (reg.getNodos().isEmpty())
		        		{
		        			myNodo.setId(1);
		        		}
		        		else
		        		{
		        			myNodo.setId(reg.getNodos().getLast().getId()+1);
		        		}
		        		       				        		
		        		reg.getNodos().add(myNodo);
		        				        		
		        		myNodo = new NodoCSV();
		        		valorNodo = "";
		        		
		        		if (Character.isSpaceChar(myChar))
						{
		        			estado = 1;
		        		}
		        		else if (Character.isDigit(myChar))
		        		{
		        			valorNodo += myChar;
		        			estado = 2;
		        		}
		        		else if (myChar == '+' || myChar == '-')
		        		{
		        			valorNodo += myChar;
		        			estado = 5;
		        		}
		        		else if (myChar == '\'' || myChar == '"')
		        		{
		        			estado = 4;
		        		}
		        		else if (myChar == ',')
		        		{
		        			estado = 8;
		        		}		        		
		        		else
		        		{
		        			valorNodo += myChar;
		        			estado = 3;
		        		}
		        	}
		        	else 
		        	{
		        		estado = 0;
		        	}
		        }
		        
			    if (valorNodo != "")
			    {
		    		myNodo.setValor(valorNodo.trim());
		    		myNodo.setEliminado(false);
		
		    		if (reg.getNodos().isEmpty()) 
		    		{
		    			myNodo.setId(1);
		    		}
		    		else
		    		{
		    			myNodo.setId(reg.getNodos().getLast().getId()+1);
		    		}
		    				        		
		    		reg.getNodos().add(myNodo);
		    		
	        		myNodo = new NodoCSV();
	        		valorNodo = "";	    		
			    }
		        			    
		        if (iteracionesRegistro == 0)
		        {
		        	RegistroCSV<NodoAtributo> misAtributos = new RegistroCSV<NodoAtributo>();
		        	
		        	for (int k = 0; k < reg.getNodos().size(); k++)
		        	{
		        		NodoAtributo nodoNuevo = new NodoAtributo();
		        		NodoCSV nodoViejo = reg.getNodos().get(k);

		        		nodoNuevo.setEliminado(nodoViejo.isEliminado());
		        		nodoNuevo.setValor(nodoViejo.getValor());
		        		nodoNuevo.setId(nodoViejo.getId());
		        		
		        		misAtributos.getNodos().add(nodoNuevo);
		        	}
		        	
		        	datosNuevos.setAtributos(misAtributos);
		        }
		        else
		        {
		        	datosNuevos.getDatos().add(reg);
		        }
		        
		        iteracionesRegistro++;
		    }
		    		
			b.close();
		} 
		catch (IOException e) 
		{
			return null;
		}
		
		return datosNuevos;
	}

	DatosCSV cargarCSVDelimitadores(String nombreArchivo)
	{
		DatosCSV datosNuevos = new DatosCSV();
		String cadena = ""; 
		
		try 
		{
			//Creamos las variables
			FileReader f = new FileReader(nombreArchivo);
			BufferedReader b = new BufferedReader(f);
			String cadenaTemp = "";
						
			//Cargamos el archivo CSV
		    while((cadenaTemp = b.readLine())!=null) 
		    {		    
		    	cadena += cadenaTemp;
		    }
		    		
			b.close();
		} 
		catch (IOException e) 
		{
			return null;
		}
		
		//Comenzamos la lectura de caracteres por caracteres
		String temp = "";
		boolean analizandoAtributos = true;
		NodoAtributo nodoActualAtributo = new NodoAtributo();
		NodoCSV nodoActualCSV = new NodoCSV();
		RegistroCSV<NodoAtributo> atributosActuales = new RegistroCSV<NodoAtributo>();
		RegistroCSV<NodoCSV> registroActual = new RegistroCSV<NodoCSV>();
		int contadorRegistro = 1;
		int contadorNodo = 0;
		boolean comillasOn = false;
		
		for (int i = 0; i < cadena.length(); i++)
		{
			char letra = cadena.charAt(i);
			
			if (analizandoAtributos)
			{
				if (letra == '~' && !comillasOn)
				{
					atributosActuales.getNodos().add(nodoActualAtributo);
					nodoActualAtributo = new NodoAtributo();
					contadorNodo = 0;
				}
				else if (letra == '%' && !comillasOn)
				{
					datosNuevos.setAtributos(atributosActuales);
					analizandoAtributos = false;
				}
				else if (letra == '"')
				{
					comillasOn = !comillasOn;
				}
				else if (letra == '|' && !comillasOn)
				{
					if (contadorNodo == 0)
					{
						nodoActualAtributo.setId(Integer.parseInt(temp));
					}
					else if (contadorNodo == 1)
					{
						nodoActualAtributo.setValor(temp);
					}
					else if (contadorNodo == 2)
					{
						nodoActualAtributo.setTipo(Integer.parseInt(temp));
					}
					else if (contadorNodo == 3)
					{
						nodoActualAtributo.setExpresionRegular(temp);
					}
					else if (contadorNodo == 4)
					{
						nodoActualAtributo.setEliminado(Boolean.parseBoolean(temp));
					}
					
					contadorNodo++;
					temp = "";
				}
				else
				{
					temp += letra;
				}
			}
			else
			{
				if (letra == '~' && !comillasOn)
				{
					registroActual.getNodos().add(nodoActualCSV);
					nodoActualCSV = new NodoCSV();
					contadorNodo = 0;
				}
				else if (letra == '%' && !comillasOn)
				{
					//END
				}
				else if (letra == '*' && !comillasOn)
				{
					registroActual.setId(contadorRegistro);
					datosNuevos.getDatos().add(registroActual);
					registroActual = new RegistroCSV<NodoCSV>();
					contadorRegistro++;
				}
				else if (letra == '|' && !comillasOn)
				{
					if (contadorNodo == 0)
					{
						nodoActualCSV.setId(Integer.parseInt(temp));
					}
					else if (contadorNodo == 1)
					{
						nodoActualCSV.setValor(temp);
					}
					else if (contadorNodo == 2)
					{
						nodoActualCSV.setEliminado(Boolean.parseBoolean(temp));
					}
					
					contadorNodo++;
					temp = "";
				}
				else if (letra == '"')
				{
					comillasOn = !comillasOn;
				}
				else
				{
					temp += letra;
				}
			}
		}
		
		return datosNuevos;
	}
}
