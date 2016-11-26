import java.util.Collections;

public class CalculadorTransformaciones {

	private DatosCSV datos;
	private DatosCSV datosNuevos;
	
	public CalculadorTransformaciones(DatosCSV datosN){
		this.datos=datosN;
		this.datosNuevos=datosN;
	}
	
	public void minMax(int min, int max, String nombreAtributo){
		int pos = datos.getPosicionAtributo(nombreAtributo);
		double minA=datos.getMayor(nombreAtributo);
		double maxA=datos.getMenor(nombreAtributo);
		double valor;
		int datoInt;
		for (int i = 0; i < datos.getDatos().size(); i++)
		{
			datoInt=Integer.parseInt(datos.getDatos().get(i).getNodos().get(pos).getValor());
			valor=((datoInt-minA)/(maxA-minA))*(max-min)+min;
			System.out.println(valor);
			datosNuevos.getDatos().get(i).getNodos().get(pos).setValor(String.valueOf(valor));
		}
	}
	
	public void zeroR(){
		
	}
	
	public void diez(){
		
	}
}
