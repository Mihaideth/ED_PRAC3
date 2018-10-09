
import java.util.Vector;

import Excepcions.JugadorExisteix;
import Excepcions.JugadorNoExisteix;
import Excepcions.LlistaBuida;
import Excepcions.LlistaPlena;


public interface InterficieHash<K,E> {
	
	
	void addElement(K k, E e) throws LlistaPlena, JugadorExisteix;
	
	int getNumKeys();
	
	Vector<K> getAllKeys() throws LlistaBuida; 
	
	E getValue(K k) throws JugadorNoExisteix;
	
	float getLoadFactor();
	
	void printStatus() throws LlistaBuida;
	
	void removeElement(K k) throws JugadorNoExisteix;

}
