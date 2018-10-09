
import java.util.Vector;

import Excepcions.JugadorExisteix;
import Excepcions.JugadorNoExisteix;
import Excepcions.LlistaBuida;
import Excepcions.LlistaPlena;
public class HashTable<K,E> implements InterficieHash<K,E> {
	private Vector<NodeHash> vector;
	int numElements;
	int MAX_ELEMENTS;
	final int fi_llista=-1;
	final int pos_buida=-2;
	int excedents;
	int segLliure;
	
	
	public class NodeHash{
		K k;
		E e;
		int nodeseguent;
	}
	
	/**
	 * Metode que crea una estructura HashTable, sense cap element, amb el nombre màxim d’elements que li passem com a paràmetre.
	 * @param midaMaxima mida de la taula
	 */
	public HashTable(int midaMaxima){
		init(midaMaxima);
	}
	
	/**
	 * Funcio de hash
	 * @param k clau
	 * @return la posicio de la taula on es troba l'element
	 */
	private int h(K k){
		int i=(k.hashCode()%excedents);
		return i;
	}

	/**
	 * Metode que afegeix un nou parell clau-valor a la taula en el lloc que li pertoca segons la funció de hashing.
	 */
	public void addElement(K k, E e) throws LlistaPlena, JugadorExisteix{
		//Obtenim index de la taula
		int i=h(k);
		int iant=i;
		
		//Ara cal veure si l'element ja hi es
		while((i>=0)&&!(k.equals(vector.get(i).k))){
			iant=i;
			i=vector.get(i).nodeseguent;
		}
		if(i==pos_buida){
			//La clau no hi era, insercio a la zona principal
			vector.get(iant).k=k;
			vector.get(iant).e=e;
			vector.get(iant).nodeseguent=fi_llista;
			numElements++;
		}
		else{ if(i==fi_llista){
			//La clau no hi era, insercio a la zona d'excedents
			//Reserva bloc de la zona d'excedents
			int nou=segLliure;
			
			if(nou==fi_llista) throw new LlistaPlena();
			segLliure=vector.get(nou).nodeseguent;
			
			//Inicialitzem bloc
			vector.get(nou).k=k;
			vector.get(nou).e=e;
			vector.get(nou).nodeseguent=fi_llista;
			
			//Encadena al final llista sinonims
			vector.get(iant).nodeseguent=nou;
			numElements++;
		}
		else{
			//La clau ja hi era. Sobreescriu valor
			vector.get(i).e=e;
			throw new JugadorExisteix();
		}
		}
		if((getLoadFactor()>0.75)||(segLliure==fi_llista)){
			redimensionar();
			
		}
			
		}
	
	/**
	 * Metode que crea una estructura HashTable, sense cap element, amb el nombre màxim d’elements que li passem com a paràmetre.
	 * @param midaMaxima mida de la taula
	 */
	public void init(int midaMaxima){
		numElements=0;
		vector=new Vector<NodeHash>(midaMaxima);
		MAX_ELEMENTS=midaMaxima;
		excedents=(int)(MAX_ELEMENTS*0.75);
		
		//Zona d'excedents
		for(int i=0; i<excedents; i++){
			vector.add(new NodeHash());
			vector.get(i).nodeseguent=pos_buida;
		}
		segLliure=excedents;
		for(int i=excedents; i<MAX_ELEMENTS-1; i++){
			vector.add(new NodeHash());
			vector.get(i).nodeseguent=i+1;
		}
		vector.add(new NodeHash());
		vector.get(MAX_ELEMENTS-1).nodeseguent=fi_llista;
	}
	/**
	 * Metode que redimensiona la taula de hash
	 */
	private void redimensionar(){
		Vector<NodeHash> auxiliar=vector;
		MAX_ELEMENTS=(int)(MAX_ELEMENTS*1.5);
		vector=new Vector<NodeHash>(MAX_ELEMENTS);
		init((int)(MAX_ELEMENTS*1.5));
		
				
		for(NodeHash node : auxiliar){
			if((node.k!=null)&&(node.e!=null)){
				try{
					addElement(node.k, node.e);
				}catch(LlistaPlena e){
				}catch(JugadorExisteix e){
				}
			}
			
		}	
	
	}

	/**
	 * Metode que retorna el Valor corresponent a la Clau que passem com a paràmetre.
	 */
	public E getValue(K k) throws JugadorNoExisteix{
		int i=h(k);
		
		while((i>=0)&&!(k.equals(vector.get(i).k))){
			i=vector.get(i).nodeseguent;
		}
		if(i<0) throw new JugadorNoExisteix();
		else return vector.get(i).e;
	}

	/**
	 * Metode que retorna el nombre d’elements guardats a la taula.
	 */
	public int getNumKeys() {
		return numElements;
	}

	/**
	 * Metode que retorna un vector amb totes les Claus que hi ha a la taula. Dóna error si no hi ha cap Clau.
	 */
	public Vector<K> getAllKeys() throws LlistaBuida {
		if(numElements==0) throw new LlistaBuida();
		Vector<K> vector2=new Vector<K>(numElements);
		
		for(int i=0; i<MAX_ELEMENTS; i++){
			K clau=vector.get(i).k;
			if(clau!=null){
				vector2.add(clau);
			}
		}
				
		return vector2;
	}

	/**
	 * Metode que retorna el factor de càrrega de la taula.
	 */
	public float getLoadFactor() {
		return (numElements/MAX_ELEMENTS);
	}

	/**
	 * Metode que imprimeix per pantalla les posicions de la taula que tenen algun element guardat, indicant quants elements hi ha en cadascuna d’elles.
	 */
	public void printStatus() throws LlistaBuida {
		if(numElements==0) throw new LlistaBuida();
		int contador=0;
		
		for(int i=0; i<excedents; i++){
			int posicio=vector.get(i).nodeseguent;
			
			if((posicio!=-2)&&(posicio==-1)){
				System.out.println("La posicio de la llista "+ i +" no te cap colisio");
			}
			if((posicio!=-2)&&(posicio!=-1)){
				contador=0;
				while(posicio!=-1){
					contador++;
					posicio=vector.get(posicio).nodeseguent;
				}
				System.out.println("La posicio de la llista "+ i +" te "+ contador +" colisions");
			}
			
		}
		
	}


	/**
	 * Metode que elimina de la taula l’element identificat per la Clau que passem per parametre.
	 */
	public void removeElement(K k) throws JugadorNoExisteix {
		int posicio=0;
				
		int i = h(k);
		NodeHash actual, nodeanterior, seguent;
		NodeHash partprincipal;
		
		actual = vector.get(i);
		nodeanterior=vector.get(i);
		partprincipal=vector.get(i);
		
		//Busco l'element a la taula
		while((k!=actual.k)&&(actual.nodeseguent!=-1)){
			nodeanterior=actual;
			posicio = actual.nodeseguent;
			actual=vector.get(posicio);
		}
		if(actual.k==null) throw new JugadorNoExisteix();
		
		//Si l'element esta a la zona principal
		if(actual==partprincipal){
			//Miro si te colisions o no
			if(actual.nodeseguent==-1){
				actual.k=null;
				actual.e=null;
				actual.nodeseguent=-2;
				numElements--;
			}
			else{
				posicio=actual.nodeseguent;
				seguent=vector.get(posicio);
				actual.e=seguent.e;
				actual.k=seguent.k;
				
				actual.nodeseguent=seguent.nodeseguent;
				
				seguent.nodeseguent=segLliure;
				segLliure=actual.nodeseguent;
				numElements--;		
			}	
		}
		//Si l'element esta a la zona d'excedents
		else{
			int aux=nodeanterior.nodeseguent;
			nodeanterior.nodeseguent=partprincipal.nodeseguent;
		
			partprincipal.e=null;
			partprincipal.k=null;
			partprincipal.nodeseguent=segLliure;
			segLliure=aux;
			numElements--;
		}
	}
		
	
	}
	
	
	

