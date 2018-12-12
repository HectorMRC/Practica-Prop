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

public class DadesSessioGrup extends ExportaImporta {
	
	/**
	 * Instancia de la propia classe seguint el patró de disseny singleton
	 */
	private static DadesSessioGrup instancia = new DadesSessioGrup();
	
	/**
	 * Creadora buida
	 */
	private DadesSessioGrup() {};
	
	/**
	 * Retorna l'única instancia de la classe
	 * @return una instancia de la classe 
	 */
	public static DadesSessioGrup getInstancia() {
		return instancia;
	}
	
	/**
	 * Exporta una SessioGrup
	 * @param sg sessió de grup que volem exportar
	 * @param crea true si volem que escrigui al fitxer, false si només volem retornar la codificació
	 * @return la codificació de la SessioGrup
	 */
	public void exportaSessioGrup(String path, HashSet<String> equip, int hores,
			String tipus, int nsessions, HashSet<Integer> ngrups, boolean crea) {
		String str = "SessioGrup".concat(endl);
		boolean first = true;  
		if (equip.isEmpty()) str = str.concat("noequip");
		for (String s : equip) {
			if (first) first = false;
			else str = str.concat(",");
			str = str.concat(s);
		}
		str = str.concat(endl);
		str = str.concat(String.valueOf(hores)).concat(endl);
		str = str.concat(tipus).concat(endl);
		str = str.concat(String.valueOf(nsessions)).concat(endl);
		first = true;  
		if (ngrups.size() == 0) str = str.concat("none");
		for (int n : ngrups) {
			if (first) first = false;
			else str = str.concat(",");
			str = str.concat(String.valueOf(n));
		}
		str = str.concat(endl).concat("END SESSIOG").concat(endl);
		exporta(path, str, crea);
	}

	public String importaSessioGrup(String path, String nomPE, String nomA, List<String> f) {
		try {
			if (f == null) {
				String s;
				f = new ArrayList<String>();
				File file = new File(path); 
				BufferedReader br = new BufferedReader(new FileReader(file));
				while ((s = br.readLine()) != null) {
					f.add(s);
				}
				br.close();
			}
			
		}
		catch (Exception e) {
			
		}
		return null;
	}
}
