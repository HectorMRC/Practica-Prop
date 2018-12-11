package persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * 
 * @author eric.casanovas@est.fib.upc.edu
 *
 */

public class DadesPlaEstudis extends ExportaImporta {
	
	/**
	 * Instancia de la propia classe seguint el patró de disseny singleton
	 */
	private static DadesPlaEstudis instancia = new DadesPlaEstudis();
	
	/**
	 * Creadora buida
	 */
	private DadesPlaEstudis() {};
	
	/**
	 * Retorna l'única instancia de la classe
	 * @return una instancia de la classe 
	 */
	public static DadesPlaEstudis getInstancia() {
		return instancia;
	}
	
	/**
	 * Exporta un PlaEstudis
	 * @param PE pla d'estudis que volem exportar
	 * @param crea true si volem que escrigui al fitxer, false si només volem retornar la codificació
	 * @return la codificació del PlaEstudis
	 */
	public void exportaPlaEstudis(String path, String nom, String autor, Map<Integer, boolean[]> franja,
			int[] rang, HashSet<String> nomassig) {
		String str = "PlaEstudis".concat(endl);
		str = str.concat(nom).concat(endl);
		str = str.concat(autor).concat(endl);
		boolean[] b;
		for (int i = 0; i < 7; ++i) {
			b = franja.get(i);
			if (b == null) str = str.concat("n");
			else {
				for (boolean p : b) {
					if (p) str = str.concat("t");
					else str = str.concat("f");
				}
			}
		}
		str = str.concat(endl);
		str = str.concat("mati: ");
		if (rang[0] == -1 || rang[1] == -1) str = str.concat("null ");
		else {
			str = str.concat(String.valueOf(rang[0]).concat(" "));
			str = str.concat(String.valueOf(rang[1]).concat(" "));
		}
		str = str.concat("tarda: ");
		if (rang[2] == -1 || rang[3] == -1) str = str.concat("null").concat(endl);
		else {
			str = str.concat(String.valueOf(rang[0]).concat(" "));
			str = str.concat(String.valueOf(rang[1]).concat(endl));
		}
		ExportaImporta.exporta(path, str, true);
		for (String a : nomassig) {
			cp.getAssignatura(path, nom, a);
		}
		ExportaImporta.exporta(path, "END PE".concat(endl), false);
	}

	public String importaPlaEstudis(String path, String nom, String autor, Map<Integer, boolean[]> lectiu,
			int[] rangDia) {
		try {
			File file = new File(path); 
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			String s; 
			s = br.readLine();
			if (!s.equals("PlaEstudis")) {
				br.close();
				return "No es un fitxer amb un PlaEstudis";
			}
			nom = br.readLine();
			autor = br.readLine();
			char[] c = br.readLine().toCharArray();
			boolean[] franja = new boolean[24];
			int dia = 0;
			int i;
			for (i = 0; i < c.length;) {
				if (c[i] == 'n') {
					franja = null;
					lectiu.put(dia, franja);
					++i;
				}
				else {
					if (i+23 > c.length) {
						br.close();
						return "error falten dades a la franja";
					}
					for (int j = 0; j < 24; ++j) {
						if (c[i+j] == 't') franja[j] = true;
						else if (c[i+j] == 'f') franja[j] = false;
						else {
							br.close();
							return "error no es ni una t, ni una f, ni una n";
						}
					}
					lectiu.put(dia, franja);
					i += 24;
				}
				++dia;
			}
			if (dia != 7) {
				br.close();
				return "No estan tots els dies de la franja de l'horari lectiu";
			}
			if (c.length != i+1) {
				br.close();
				return "Hi ha més lletres de les que toquen";
			}
			s = br.readLine();
			String[] tokens = s.split(" ");
			i = 0;
			if (tokens.length > 6 || tokens.length < 4) {
				br.close();
				return "Rang del dia incorrecte";
			}
			if (tokens[i++] != "mati:") {
				br.close();
				return "Sintaxi incorrecta a rangDia";
			}
			if (tokens[i] == "null") {
				rangDia[0] = -1;
				rangDia[1] = -1;
			}
			else if (tokens[i].chars().allMatch(Character::isDigit) && tokens[i+1].chars().allMatch(Character::isDigit)) {
				rangDia[0] = Integer.valueOf(rangDia[0]);
				rangDia[1] = Integer.valueOf(rangDia[1]);
				++i;
			}
			else {
				br.close();
				return "Sintaxi incorrecta a rangDia";
			}
			++i;
			if (tokens[i] != "tarda:") {
				br.close();
				return "Sintaxi incorrecta a rangDia";
			}
			++i;
			if (tokens[i] == "null") {
				rangDia[2] = -1;
				rangDia[3] = -1;
			}
			else if (tokens[i].chars().allMatch(Character::isDigit) && tokens[i+1].chars().allMatch(Character::isDigit)) {
				rangDia[2] = Integer.valueOf(rangDia[0]);
				rangDia[3] = Integer.valueOf(rangDia[1]);
				++i;
			}
			else {
				br.close();
				return "Sintaxi incorrecta a rangDia";
			}
			br.close();
			return null;
		}
		catch (Exception e) {
			return e.getMessage();
		}
	}
}
