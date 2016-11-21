import java.util.LinkedList;

public class NaiveBayes extends Algoritmo
{			
	NaiveBayes(DatosCSV d) 
	{
		super(d);
	}

	public void crearModelo()
	{
		this.modelo = null;
		
		if (this.datos != null)
		{
			//Conseguimos las matrices de cada atributo en relacion de la clase
			LinkedList<MatrizReglas> matrices = new LinkedList<MatrizReglas>();
			MatrizReglas matrizClase = new MatrizReglas();
			LinkedList<NodoCSV> modelo = new LinkedList<NodoCSV>();
			
			int posClase = datos.getPosicionAtributo(datos.getNombreClase());
			
			if (posClase != -1)
			{
				for (int i = 0; i < datos.getAtributos().getNodos().size(); i++)
				{
					NodoCSV nodo = datos.getAtributos().getNodos().get(i); 
					
					if (!nodo.isEliminado() && !nodo.getValor().equals(datos.getNombreClase()))
					{
						MatrizReglas temp = new MatrizReglas();
						temp.crearMatriz(this.datos, nodo.getValor());
						
						if (temp.getMatriz() != null)
						{
							matrices.add(temp);
						}
					}
					else if (nodo.getValor().equals(datos.getNombreClase()))
					{
						MatrizReglas temp = new MatrizReglas();
						temp.crearMatriz(this.datos, nodo.getValor());
						
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
				if (datos.getTipo(matrices.get(i).getNombreAtributo()) == 3)
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

			//Transformamo a las matrices en una matriz de probabilidad			
			for (int i = 0; i < matrices.size(); i++)
			{				
				if (datos.getTipo(matrices.get(i).getNombreAtributo()) == 3)
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

			//Creamos el modelo
			if (!matrices.isEmpty())
			{								
				for (int i = 0; i < datos.getDatos().size(); i++)
				{
					//Obtenemos la lista de valores
					LinkedList<NodoCSV>  nodos = datos.getDatos().get(i).getNodos();
					RegistroCSV<NodoAtributo> atributos = datos.getAtributos();
					
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
								if (datos.getTipo(atributos.getNodos().get(j).getValor()) == 3)
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
									
									/*System.out.println(matrizActual.toString());
									System.out.println(claseTemp + " " + posAttr1 + " " + posClase1);*/
	
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
					
					modelo.add(new NodoCSV(1,claseMayor,false));
				}
				
				this.setModelo(modelo);
			}
		}
	}
}
