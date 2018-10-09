import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

import Excepcions.JugadorExisteix;
import Excepcions.JugadorNoExisteix;
import Excepcions.LlistaPlena;

public class Main {

	static Scanner teclat = new Scanner(System.in);
	static Interficie llistaordenat;
	static Interficie llistadesordenat;
	static Interficie hashtable;
	static long start, end, res, total1=0, total2=0, total3=0;

	public static void Menu() {
		System.out.println("\n\nMENU:");
		System.out.println("\n\t1. Mostrar les dades d’un jugador a partir del seu ID");
		System.out.println("\t2. Llistar tots els equips i les posicions que hi ha dins l’estructura");
		System.out.println("\t3. Llistar tots els jugadors d’un equip");
		System.out.println("\t4. Consultar el nombre d’elements introduïts a l’estructura");
		System.out.println("\t5. Esborrar un jugador");
		System.out.println("\t6. Sortir");
		System.out.print("\n\t\t\tQuina opcio vols escollir?:\n");
	}

	/**
	 * Mètode que borra un jugador segons la id introduida per teclat
	 */
	public static void esborrarJugador() {
		System.out.println("Introdueix l'identificador del jugador que vols esborrar");
		int codi = llegirEnter();
		
		esborrarJugador(llistaordenat, codi);
		esborrarJugador(llistadesordenat, codi);
		esborrarJugador(hashtable, codi);

	}
	
	/**
	 * Mètode que borra un jugador segons la id introduida per teclat
	 * @param llista Interficie
	 * @param codi identificador del jugador
	 */
	public static void esborrarJugador(Interficie llista,int codi){
		try {
			start = System.nanoTime();
			llista.esborrarJugador(codi);
			end = System.nanoTime();
			res = end - start;
			System.out.println("Temps en nanosegons per la ordenada: "+res);
			System.out.println("El jugador ha estat esborrat");
		} catch (JugadorNoExisteix e) {
			System.out.println(e);
		}
	}
	
	

	/**
	 * Mètode que mostra el jugador segons la id introduida per teclat
	 */
	public static void mostraInformacio() {

		System.out.println("Introdueix l'identificador del jugador que vols buscar");
		int codi = llegirEnter();
		mostraInformacio(llistaordenat, codi);
		mostraInformacio(llistadesordenat, codi);
		mostraInformacio(hashtable, codi);
		

	}
	
	/**
	 * Mètode que mostra el jugador segons la id introduida per teclat
	 * @param llista Interficie
	 * @param codi identificador del jugador
	 */
	public static void mostraInformacio(Interficie llista, int codi) {
		try {
			start = System.nanoTime();
			Jugador jug=llista.getJugador(codi);
			if(jug!=null){
				System.out.println(jug.toString());
				end = System.nanoTime();
				res = end - start;
				System.out.println("Temps en nanosegons: "+res);
			}
			if(jug==null) throw new JugadorNoExisteix();

		} catch (JugadorNoExisteix e) {
			System.out.println(e);
		}
	}
	
	
	
	/**
	 * Metode per comprobar que no es repetexin equips o posicions (segons on es cridi)
	 * @param equips llista de strings que conte el nom dels equips
	 * @param n_equips nombre d'equips a la llista
	 * @param equip	nom de l'equip a comprobar
	 * @return si el string ja existeix a la llista o no
	 */
	public static boolean repetits(String[] equips, int n_equips, String equip) {
		boolean repetit = false;
		int i = 0;
		while ((i < n_equips) && (!repetit)) {
			if (equips[i].equalsIgnoreCase(equip))
				repetit = true;
			i++;
		}
		return repetit;

	}
	
	/**
	 * Metode que retorna una llista amb tots els equips diferents
	 * @param llista Interficie
	 * @return la llista d'equips redimensionada
	 */
	public static String[] getEquips(Interficie llista) {

		int[] id = llista.getAllJugadors();
		String[] equips = new String[llista.getNumJugadors()];
		int n_equips = 0;
		for (int i = 0; i < llista.getNumJugadors(); i++) {
			try {
				Jugador jug=llista.getJugador(id[i]);
				if(jug!=null){
					if (!repetits(equips, n_equips, jug.getEquip())) {
						equips[n_equips] = jug.getEquip();
						n_equips++;
					}
				}
			} catch (JugadorNoExisteix e) {
				e.printStackTrace();
			}
		}

		String[] equipsRed = new String[n_equips];
		for (int i = 0; i < n_equips; i++) {
			equipsRed[i] = equips[i];
		}

		return equipsRed;
	}
	
	/**
	 * Metode que retorna una llista amb totes les posicions diferents
	 * @param llista Interficie
	 * @return la llista de posicions redimensionada
	 */
	public static String[] getPosicions(Interficie llista) {

		int[] id = llista.getAllJugadors();
		String[] posicions = new String[llista.getNumJugadors()];
		int n_pos = 0;
		for (int i = 0; i < llista.getNumJugadors(); i++) {
			try {
				Jugador jug=llista.getJugador(id[i]);
				if(jug!=null){
					if (!repetits(posicions, n_pos, jug.getPosicio()))
						posicions[n_pos++] = jug.getPosicio();
						
				}
			} catch (JugadorNoExisteix e) {
				e.printStackTrace();
			}
		}

		String[] posicionsRed = new String[n_pos];
		for (int i = 0; i < n_pos; i++) {
			posicionsRed[i] = posicions[i];
		}

		return posicionsRed;
	}
	
	


	/**
	 * Mètode que mostra tots els equips i posicions que hi ha dins l'estructura
	 */
	public static void llistaEquipsPosicions() {
		
		llistaEquipsPosicions(llistaordenat);
		llistaEquipsPosicions(llistadesordenat);
		llistaEquipsPosicions(hashtable);
				
	
	}
	
	/**
	 * Mètode que mostra tots els equips i posicions que hi ha dins l'estructura
	 * @param llista Interficie
	 */
	public static void llistaEquipsPosicions(Interficie llista){
		start = System.nanoTime();
		String[] equips = getEquips(llista);
		String[] posicions = getPosicions(llista);
		System.out.println("Tots els equips: \n");
		for (int i = 0; i < equips.length; i++) {
			System.out.println(equips[i]);
		}
		System.out.println("Totes les posicions:\n ");
		for (int i = 0; i < posicions.length; i++) {
			System.out.println(posicions[i]);
		}
		end = System.nanoTime();
		res = end - start;
		System.out.println("Temps en nanosegons: "+res);
		
	}
	

	/**
	 * Mètode que mostra tots els jugadors d'un mateix equip introduit per teclat
	 */
	public static void llistaJugadorsEquip() {
		
		System.out.println("Introdueix el nom de l'equip en questio");
		String equip = teclat.nextLine();
		equip = teclat.nextLine();
		llistaJugadorsEquip(llistaordenat, equip);
		llistaJugadorsEquip(llistadesordenat, equip);
		llistaJugadorsEquip(hashtable, equip);
		


		}
		
	/**
	 * Mètode que mostra tots els jugadors d'un mateix equip introduit per teclat
	 * @param llista Interficie
	 * @param equip nom de l'equip
	 */
	public static void llistaJugadorsEquip(Interficie llista, String equip){
		Jugador j = null;
		int[] id = llista.getAllJugadors();
		System.out.println("El jugadors de l'equip:" + equip);
		start = System.nanoTime();
		for (int i = 0; i < id.length; i++) {
			try {
				j = llista.getJugador(id[i]);
			} catch (JugadorNoExisteix e) {
				System.out.println(e);
			}
		if (j.getEquip().equalsIgnoreCase(equip)) {
				System.out.println("\n" + j.getNom());
			}
		}
		end = System.nanoTime();
		res = end - start;
		System.out.println("\nTemps en nanosegons:"+res+"\n");
		
	}
	
		/**
		 * Metode per consultar el nombre d’elements introduïts a l’estructura
		 */
	public static void elementsEstructura(){
		
		elementsEstructura(llistaordenat);
		elementsEstructura(llistadesordenat);
		elementsEstructura(hashtable);
	}
	
	/**
	 * Metode per consultar el nombre d’elements introduïts a l’estructura
	 * @param llista Interficie
	 */
	public static void elementsEstructura(Interficie llista){
		int numElements=0;
		start = System.nanoTime();
		numElements=llista.getNumJugadors();
		System.out.println("El numero d'elements:"+numElements);
		end = System.nanoTime();
		res = end - start;
		System.out.println("Temps en nanosegons:"+res);
	}
	

	/**
	 * Mètode que carrega els jugadors des d'un fitxer
	 * @param hashtable Taula de hash
	 * @param ordenat Interficie jugador
	 * @param desordenat Interficie jugador
	 * @throws IOException IOException Fitxer no trobat
	 */
	public static void ImportarArxiu(Interficie hashtable, Interficie ordenat, Interficie desordenat) throws IOException {
		BufferedReader br = null;
		boolean trobat=false;
		try {
			
			br = new BufferedReader(new FileReader("file.txt"));
			String linea = "";
			linea = br.readLine();
			while (linea != null) {
				StringTokenizer st = new StringTokenizer(linea, ",");
				int id = Integer.parseInt(st.nextToken());
				String equip = st.nextToken();
				int dorsal = Integer.parseInt(st.nextToken());
				String nom = st.nextToken();
				String posicio = st.nextToken();
				Jugador j = new Jugador(id, nom, dorsal, posicio, equip);
				if(trobat==false){
					try{
						try {
							start = System.nanoTime();
							llistaordenat.afegirJugador(j);
							end = System.nanoTime();
							res = end - start;
							total1=total1+res;
						} catch (JugadorExisteix e) {
							System.out.println("El jugador ja existeix, per tant no s'afegeix1");
						}catch (LlistaPlena e) {
							System.out.println("La llista ja esta plena, no es pot continuar afegint");
							trobat=true;
						}
						try {
							start = System.nanoTime();
							llistadesordenat.afegirJugador(j);
							end = System.nanoTime();
							res = end - start;
							total2=total2+res;
						} catch (JugadorExisteix e) {
							System.out.println("El jugador ja existeix, per tant no s'afegeix2");
						}
					}catch (LlistaPlena e) {
							System.out.println("La llista ja esta plena, no es pot continuar afegint");
							trobat=true;
						}
				}
				try {
					start = System.nanoTime();
					try {
						hashtable.afegirJugador(j);
					} catch (LlistaPlena e) {
						e.printStackTrace();
					}
					end = System.nanoTime();
					res = end - start;
					total3=total3+res;
				} catch (JugadorExisteix e) {
					System.out.println("El jugador ja existeix, per tant no s'afegeix3");
				}
				linea = br.readLine();
			}
			System.out.println("Temps en nanosegons per la ordenada: "+total1);
			System.out.println("Temps en nanosegons per la desordenada: "+total2);
			System.out.println("Temps en nanosegons per la hash: "+total3);

		} catch (FileNotFoundException e) {
			System.out.println("Fitxer no trobat");
		} finally {
			br.close();
		}

	}
	

	/**
	 * Metode que comproba que el valor introduit sigui un Enter;
	 * @return retorna el valor que estem comprobant si aquest es valid;
	 */
	public static int llegirEnter() {
		boolean comprobat = false;
		int valor = 0;
		while (!comprobat) {
			try {
				String s = teclat.next();
				valor = Integer.parseInt(s);
				comprobat = true;
			} catch (NumberFormatException e) {
				System.out.print("Error en el format del codi\n");
			}
		}
		return valor;
	}

	public static void main(String[] args) {

		int opcio;
		int capacitat = 0;
		

		System.out.println("Introdueix la capacitat maxima del conjunt");
		capacitat = llegirEnter();
		
		llistaordenat = new Ordenat(capacitat);
		System.out.println("S'ha creat correctament");


		llistadesordenat = new Desordenat(capacitat);
		System.out.println("S'ha creat correctament");

		hashtable = new JugadorsHash(capacitat);
		System.out.println("S'ha creat correctament");
				
		try {
			
			ImportarArxiu(hashtable, llistaordenat, llistadesordenat);
		} catch (IOException e) {
			System.out.println("Error al tractar l'arxiu");

		}
		

		Menu();
		opcio = llegirEnter();

		while (opcio != 6) {
			switch (opcio) {
			case 1:
				mostraInformacio();
				break;
			case 2:
				llistaEquipsPosicions();
				break;
			case 3:
				llistaJugadorsEquip();
				break;
			case 4:
				elementsEstructura();
				break;
			case 5:
				esborrarJugador();
				break;
			}

			Menu();
			opcio = llegirEnter();
		}
	}
}

