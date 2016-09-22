import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
			
			//Cargamos el archivo CSV
		    while((cadena = b.readLine())!=null) 
		    {		        
				reg = new RegistroCSV();
		    	
		        for (int i = 0; i < cadena.length(); i++)
		        {
		        	
		        }
		        
		        if (iteracionesRegistro == 0)
		        {
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
