import java.util.LinkedList;

public class NaiveBayes extends Algoritmo
{			
	NaiveBayes(DatosCSV e, DatosCSV p) 
	{
		super(e, p);
	}

	public void calcular()
	{
		this.resultado = null;
		
		if (this.datosEntrenamiento != null)
		{
			//Conseguimos las matrices de cada atributo en relacion de la clase
			LinkedList<MatrizReglas> matrices = new LinkedList<MatrizReglas>();
			MatrizReglas matrizClase = new MatrizReglas();
			LinkedList<NodoCSV> resultado = new LinkedList<NodoCSV>();
			
			int posClase = this.datosEntrenamiento.getPosicionAtributo(this.datosEntrenamiento.getNombreClase());
			
			if (posClase != -1)
			{
				for (int i = 0; i < this.datosEntrenamiento.getAtributos().getNodos().size(); i++)
				{
					NodoCSV nodo = this.datosEntrenamiento.getAtributos().getNodos().get(i); 
					
					if (!nodo.isEliminado() && !nodo.getValor().equals(this.datosEntrenamiento.getNombreClase()))
					{
						MatrizReglas temp = new MatrizReglas();
						temp.crearMatriz(this.datosEntrenamiento, nodo.getValor());
						
						if (temp.getMatriz() != null)
						{
							matrices.add(temp);
						}
					}
					else if (nodo.getValor().equals(this.datosEntrenamiento.getNombreClase()))
					{
						MatrizReglas temp = new MatrizReglas();
						temp.crearMatriz(this.datosEntrenamiento, nodo.getValor());
						
						if (temp.getMatriz() != null)
						{
							matrizClase = temp;
						}
					}
				}
			}
			
			//Agregamos +1 a cada elemento para evitar que existan ceros
			for (int i = 0; i < matrices.size(); i++)
			{
				if (this.datosEntrenamiento.getTipo(matrices.get(i).getNombreAtributo()) == 3)
				{
					continue;
				}
				
				for (int j = 0; j < matrices.get(i).getHeaderLados().size(); j++)
				{
					for (int k = 0; k < matrices.get(i).getHeaderArriba().size(); k++)
					{
						matrices.get(i).getMatriz()[j][k] = matrices.get(i).getMatriz()[j][k]+1;  
					}
				}
				
				//System.out.println(matrices.get(i).toString());
			}

			//Transformamos a las matrices en una matriz de probabilidad			
			for (int i = 0; i < matrices.size(); i++)
			{				
				if (this.datosEntrenamiento.getTipo(matrices.get(i).getNombreAtributo()) == 3)
				{
					continue;
				}
				
				LinkedList<Double> valoresAciertos = new LinkedList<Double>();
				
				for (int j = 0; j < matrices.get(i).getHeaderArriba().size(); j++)
				{
					valoresAciertos.add(matrices.get(i).getAciertosArriba(j));
				}
								
				for (int j = 0; j < matrices.get(i).getHeaderLados().size(); j++)
				{
					for (int k = 0; k < matrices.get(i).getHeaderArriba().size(); k++)
					{
						matrices.get(i).getMatriz()[j][k] = matrices.get(i).getMatriz()[j][k]/valoresAciertos.get(k);  
					}
				}
				
				//System.out.println(matrices.get(i).toString());
			}
			
			double valor = 0;

			for (int j = 0; j < matrizClase.getHeaderArriba().size(); j++)
			{
				valor = valor + matrizClase.getAciertosArriba(j);
			}

			for (int j = 0; j < matrizClase.getHeaderLados().size(); j++)
			{
				for (int k = 0; k < matrizClase.getHeaderArriba().size(); k++)
				{
					matrizClase.getMatriz()[j][k] = matrizClase.getMatriz()[j][k]/valor;  
				}
			}
			
			//System.out.println(matrizClase.toString());

			//Creamos el resultado
			if (!matrices.isEmpty())
			{								
				for (int i = 0; i < this.datosPrueba.getDatos().size(); i++)
				{
					//Obtenemos la lista de valores
					LinkedList<NodoCSV>  nodos = this.datosPrueba.getDatos().get(i).getNodos();
					RegistroCSV<NodoAtributo> atributos = this.datosPrueba.getAtributos();
					
					String claseMayor = "";
					double valorMayor = 0;
					
					//Sacamos la probabilidad de cada clase
					for (int c = 0; c < matrizClase.getHeaderArriba().size(); c++)
					{
						//Calculamos la probabilidad por cada elemento y los multiplicamos
						double prAcumulado = 1;
						
						for (int j = 0; j < nodos.size(); j++)
						{
							if (j == posClase)
							{
								continue;
							}
							
							double prActual = 1;
							
							int posMatrizAtrActual = -1;
							
							//Buscamos a la matriz del atributo actual
							for (int k = 0; k < matrices.size(); k++)
							{
								if (matrices.get(k).getNombreAtributo().equals(atributos.getNodos().get(j).getValor()))
								{
									posMatrizAtrActual = k;
									break;
								}
							}
							
							//Sacamos el valor de probabilidad para ese atributo
							if (posMatrizAtrActual != -1)
							{
								MatrizReglas matrizActual = matrices.get(posMatrizAtrActual);
								
								//Es un atributo numerico
								if (this.datosPrueba.getTipo(atributos.getNodos().get(j).getValor()) == 3)
								{
									String claseTemp = matrizClase.getHeaderArriba().get(c);									
									int posClase1  = matrizActual.getHeaderArriba().indexOf(claseTemp);

									double promedio = matrizActual.getPromedio(posClase1);
									double desviacion = matrizActual.getDesviacionEstandar(posClase1);
									
									double formula = 1/(Math.sqrt(2*Math.PI*desviacion));
									double parteArribaN = Math.pow((desviacion-promedio), 2);
									double parteArribaD = -2*Math.pow(desviacion, 2);
									formula = formula * Math.exp(parteArribaN/parteArribaD);
									
									/*System.out.println(matrizActual.toString());
									System.out.println("Promedio:" + promedio);
									System.out.println("Desviacion:"+ desviacion);
									System.out.println(claseTemp + " " + formula);
									System.out.println();*/

									prActual = prActual * formula;
								}
								else //Puede ser tratado como nominal
								{									
									String claseTemp = matrizClase.getHeaderArriba().get(c);
									int posAttr1  = matrizActual.getHeaderLados().indexOf(nodos.get(j).getValor());
									int posClase1  = matrizActual.getHeaderArriba().indexOf(claseTemp);
									
									/*System.out.println(claseTemp + " " + posAttr1 + " " + posClase1);
									System.out.println(nodos.get(j).getValor());
									System.out.println(claseTemp);
									System.out.println(matrizActual.toString());*/
	
									prActual = prActual * matrizActual.getMatriz()[posAttr1][posClase1];
								}
							}
														
							/*System.out.println(nodos.get(j));
							System.out.println(prActual);
							System.out.println(prAcumulado);*/
							
							prAcumulado = prAcumulado * prActual;
							
							/*System.out.println(prAcumulado);
							System.out.println();*/
						}
						
						//Multiplicamos por el valor de la clase
						if (matrizClase != null)
						{
							MatrizReglas matrizActual = matrizClase;
							String claseTemp = matrizClase.getHeaderArriba().get(c);
							int posClase1  = matrizActual.getHeaderArriba().indexOf(claseTemp);

							/*System.out.println(posClase1);
							System.out.println(matrizActual.getMatriz()[posClase1][posClase1]);
							System.out.println(prAcumulado);*/

							prAcumulado = prAcumulado * matrizActual.getMatriz()[posClase1][posClase1];
						}
						
						//System.out.println(matrizClase.getHeaderArriba().get(c));
						//System.out.println("Valor de Pr: " + prAcumulado);
						
						if (prAcumulado > valorMayor)
						{
							valorMayor = prAcumulado;
							claseMayor = matrizClase.getHeaderArriba().get(c);
						}
					}
					
					resultado.add(new NodoCSV(1,claseMayor,false));
				}
				
				this.setResultado(resultado);
			}
		}
	}
}
