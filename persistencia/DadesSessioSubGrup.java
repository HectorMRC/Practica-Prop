package persistencia;

import java.io.*;
import java.util.*;
import utils.*;

/**
 * 
 * @author eric.casanovas@est.fib.upc.edu
 *
 */

public class DadesSessioSubGrup extends ExportaImporta {
	
	/**
	 * Instancia de la propia classe seguint el patró de disseny singleton
	 */
	private static DadesSessioSubGrup instancia = new DadesSessioSubGrup();
	
	/**
	 * Creadora buida
	 */
	private DadesSessioSubGrup() {};
	
	/**
	 * Retorna l'única instancia de la classe
	 * @return una instancia de la classe 
	 */
	public static DadesSessioSubGrup getInstancia() {
		return instancia;
	}
	
	/**
	 * Exporta una SessioSubGrup
	 * @param ssg sessió de sub grup que volem exportar
	 * @param crea true si volem que escrigui al fitxer, false si només volem retornar la codificació
	 * @return la codificació de la SessioSubGrup
	 */
	public void exportaSessioSubGrup(String path, HashSet<String> equip, int hores,
			String tipus, int nsessions, HashSet<Pair<Integer, Integer>> nsubgrups, boolean crea) {
		String str = "SessioSubGrup".concat(endl);
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
		if (nsubgrups.size() == 0) str = str.concat("none");
		for (Pair<Integer, Integer> n : nsubgrups) {
			if (first) first = false;
			else str = str.concat(",");
			str = str.concat(String.valueOf(n.first).concat("-").concat(String.valueOf(n.second)));
		}
		str = str.concat(endl).concat("END SESSIOSG").concat(endl);
		exporta(path, str, crea);
	}

	public String importaSessioSubGrup(String path, String nomPE, String nomA, List<String> f) {
		try {
			boolean assignada = true;
			if (f == null) {
				assignada = false;
				String s;
				f = new ArrayList<String>();
				File file = new File(path); 
				BufferedReader br = new BufferedReader(new FileReader(file));
				while ((s = br.readLine()) != null) {
					f.add(s);
				}
				br.close();
			}
			int i = 0;
			if (!f.get(i).equals("SessioSubGrup")) return "no conte una sessio subgrup el fitxer";
			while (i < f.size() && f.get(i++).equals("SessioSubGrup")) {
				if (i + 4 > f.size()) return "error llargada de sessio subgrup";
				HashSet<String> equip = new HashSet<String>();
				int hores;
				String tipus;
				String error;
				int nsessions;
				String[] equipament = f.get(i++).split(",");
				for (int j = 0; j < equipament.length; ++j) {
					equip.add(equipament[j]);
				}
				hores = Integer.parseInt(f.get(i++));
				tipus = f.get(i++);
				nsessions = Integer.parseInt(f.get(i++));
				if (!f.get(i++).equals("END SESSIOG")) return "error en acabar fitxer sessiosubgrup";
				if ((error = cp.creaSessioSubGrupImportada(nomPE, nomA, equip, hores, tipus, nsessions)) != null) return error;
				if (assignada) {
					String[] a = f.get(i).split(",");
					for (String as : a) {
						String[] aa = as.split("-");
						if (aa.length != 2) return "error a sessio subgrup";
						if ((error = cp.creaSessioSubGrupAImportada(nomPE, nomA, tipus, hores, Integer.parseInt(aa[0]), Integer.parseInt(aa[1]))) != null) {
							cp.eliminaSessioSubGrup(nomPE, nomA, tipus, hores);
							return error;
						}
					}
				}
			}
			return null;			
		}
		catch (Exception e) {
			return e.getMessage();
		}
	}
}
