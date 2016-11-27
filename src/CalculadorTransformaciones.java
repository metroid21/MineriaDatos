import javax.swing.table.DefaultTableModel;

public class CalculadorTransformaciones {

	private DatosCSV datos;
	private DatosCSV datosNuevos;
	private double nuevoValor;
	
	public DatosCSV getDatos() {
		return datos;
	}

	public void setDatos(DatosCSV datos) {
		this.datos = datos;
	}

	public DatosCSV getDatosNuevos() {
		return datosNuevos;
	}

	public void setDatosNuevos(DatosCSV datosNuevos) {
		this.datosNuevos = datosNuevos;
	}

	public CalculadorTransformaciones(DatosCSV datosN){
		this.datos=datosN;
		DefaultTableModel modelo = this.datos.getDatosAsTableModel(true);
		this.datosNuevos = DatosCSV.getDatosFromTableModel(modelo);
	}
	
	public void minMax(int min, int max, String nombreAtributo){	
		int pos = datos.getPosicionAtributo(nombreAtributo);
		double minA=datos.getMayor(nombreAtributo);
		double maxA=datos.getMenor(nombreAtributo);
		double datoIni;
		for (int i = 0; i < datos.getDatos().size(); i++)
		{
			datoIni=Double.parseDouble(datos.getDatos().get(i).getNodos().get(pos).getValor());
			nuevoValor=((datoIni-minA)/(maxA-minA))*(max-min)+min;
			//System.out.println(valor);
			datosNuevos.getDatos().get(i).getNodos().get(pos).setValor(String.valueOf(nuevoValor));
		}
	}
	
	public void zScore(String nombreAtributo){
		int pos = datos.getPosicionAtributo(nombreAtributo);
		double media=datos.getMedia(nombreAtributo);
		double dEstandar=datos.getDesviacionEstandar(nombreAtributo);
		double datoIni;
		for(int i=0;i < datos.getDatos().size();i++){
			datoIni=Double.parseDouble(datos.getDatos().get(i).getNodos().get(pos).getValor());
			nuevoValor=(datoIni-media)/dEstandar;
			//System.out.println("*"+nuevoValor);
			datosNuevos.getDatos().get(i).getNodos().get(pos).setValor(String.valueOf(nuevoValor));
		}
	}
	
	public void zScoreAbs(String nombreAtributo){
		int pos = datos.getPosicionAtributo(nombreAtributo);
		double media=datos.getMedia(nombreAtributo);
		double dAbsoluta=datos.getDesviacionAbsoluta(nombreAtributo);
		double datoIni;
		for(int i=0;i < datos.getDatos().size();i++){
			datoIni=Double.parseDouble(datos.getDatos().get(i).getNodos().get(pos).getValor());
			nuevoValor=(datoIni-media)/dAbsoluta;
			//System.out.println(nuevoValor);
			datosNuevos.getDatos().get(i).getNodos().get(pos).setValor(String.valueOf(nuevoValor));
		}
	}
	
	public void decimal(String nombreAtributo){
		int pos = datos.getPosicionAtributo(nombreAtributo);
		double maxAbs=Math.abs(datos.getMayor(nombreAtributo));
		double datoIni;
		int divisor=10;
		while((maxAbs/divisor)>1){
			divisor=divisor*10;
		}
		for(int i=0;i < datos.getDatos().size();i++){
			datoIni=Double.parseDouble(datos.getDatos().get(i).getNodos().get(pos).getValor());
			nuevoValor=datoIni/divisor;
			//System.out.println(nuevoValor);
			datosNuevos.getDatos().get(i).getNodos().get(pos).setValor(String.valueOf(nuevoValor));
		}
	}
}
