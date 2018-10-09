import Excepcions.*;

public interface Interficie {
	void afegirJugador(Jugador j) throws LlistaPlena, JugadorExisteix;

	Jugador getJugador(int id) throws JugadorNoExisteix;

	int getNumJugadors();

	int[] getAllJugadors();

	void esborrarJugador(int codi) throws JugadorNoExisteix;

}
