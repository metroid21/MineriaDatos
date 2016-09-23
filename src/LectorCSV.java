import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LectorCSV 
{
	void cargarCSV(String nombreArchivo, DatosCSV datosLeidos)
	{
		int iteracionesRegistro = 0;
		
		try 
		{
			//Creamos las variables
			FileReader f = new FileReader(nombreArchivo);
			BufferedReader b = new BufferedReader(f);
			String cadena = ""; 
			RegistroCSV reg = null;
			
			//Ejecutamos los primeros cambios de eliminacion
			Cambio cambioLectura = new Cambio();
			
			cambioLectura.setTipo(3);
			
			if (datosLeidos.getCambios().isEmpty())
			{
				cambioLectura.setId(1);
			}
			else
			{
				cambioLectura.setId(datosLeidos.getCambios().getLast().getId()+1);
			}			
			
			//Eliminamos los datos actuales
			for (int i = 0; i < datosLeidos.getDatos().size(); i++)
			{
				RegistroCSV reg1 = datosLeidos.getDatos().get(i); 
				
				for (int j = 0; j < reg1.getNodos().size(); j++)
				{
					NodoCambios nodo = new NodoCambios(j, i, "false", "true");
					reg1.getNodos().get(j).setEliminado(true);
					cambioLectura.getNodos().add(nodo);
				}
			}
			
			NodoCSV myNodo = new NodoCSV();
			String valorNodo = ""; 

			//Cargamos el archivo CSV
		    while((cadena = b.readLine())!=null) 
		    {		    	
		    	String myCad = cadena;
		    	int estado = 1;
				reg = new RegistroCSV();
				
			    if (datosLeidos.getDatos().isEmpty())
			    {
			    	reg.setId(1);
			    }
			    else
			    {
			    	reg.setId(datosLeidos.getDatos().getLast().getId()+1);
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
		        			myNodo.setTipo(3);
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
		        			myNodo.setTipo(2);
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
		        			myNodo.setTipo(5);
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
		        			myNodo.setTipo(2);
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
		        		
						NodoCambios nodo = new NodoCambios(reg.getId(), myNodo.getId(), "true", "false");
						cambioLectura.getNodos().add(nodo);
		        		
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
		        	for (int k = 0; k < reg.getNodos().size(); k++)
		        	{
		        		reg.getNodos().get(k).setTipo(1);
		        	}
		        	
		        	datosLeidos.setAtributos(reg);
		        }
		        else
		        {
		        	datosLeidos.getDatos().add(reg);
		        }
		        
		        iteracionesRegistro++;
		    }
		    		
			b.close();
			
			//Guardamos los cambios
			datosLeidos.getCambios().add(cambioLectura);
		} 
		catch (IOException e) 
		{
			System.out.println("Archivo no Encontrado");
			//e.printStackTrace();
		}
	}
}
