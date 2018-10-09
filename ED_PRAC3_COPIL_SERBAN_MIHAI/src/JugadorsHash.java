import Excepcions.JugadorExisteix;
import Excepcions.JugadorNoExisteix;
import Excepcions.LlistaBuida;
import Excepcions.LlistaPlena;


public class JugadorsHash implements Interficie{
	InterficieHash<Integer, Jugador> hashtable;
	
	/**
	 * Metode que crea una estructura HashTable, sense cap element, amb el nombre màxim d’elements que li passem com a paràmetre.
	 * @param midaMaxima mida de la taula
	 */
	public JugadorsHash(int midaMaxima){
		hashtable= new HashTable<Integer, Jugador>(midaMaxima);
	}

	/**
	 * Metode que afegeix un nou parell clau-valor a la taula en el lloc que li pertoca segons la funció de hashing.
	 */
	public void afegirJugador(Jugador j) throws LlistaPlena, JugadorExisteix {
		try{
			hashtable.addElement(j.getId(), j);
		}catch(LlistaPlena e){
			System.out.print(e);
		}catch(JugadorExisteix e){
			System.out.print(e);
		}
		
	}

	/**
	 * Metode que retorna el Valor corresponent a la Clau que passem com a paràmetre.
	 */
	public Jugador getJugador(int id) throws JugadorNoExisteix {
		Jugador variable=null;
		try{
			variable = hashtable.getValue(id);
		}catch(JugadorNoExisteix e){
			
		}
		return variable;
	}

	/**
	 * Metode que retorna el nombre d’elements guardats a la taula.
	 */
	public int getNumJugadors() {
		return hashtable.getNumKeys();
	}

	/**
	 * Metode que retorna un vector amb totes les Claus que hi ha a la taula. Dóna error si no hi ha cap Clau.
	 */
	public int[] getAllJugadors() {
		int i=0;
		int[] ids= new int[getNumJugadors()];
		try{
			for(int id : hashtable.getAllKeys())
				ids[i++]=id;
			
		}catch(LlistaBuida e){
			
		}
		
		return ids;
	}

	/**
	 * Metode que elimina de la taula l’element identificat per la Clau que passem per parametre.
	 */
	public void esborrarJugador(int codi) throws JugadorNoExisteix {
		try{
			hashtable.removeElement(codi);
		}catch(JugadorNoExisteix e){
			System.out.print(e);
		}
		
	}

}
