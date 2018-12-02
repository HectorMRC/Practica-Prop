package persistencia.exports;

import java.util.*;
import utils.*;

public class ExportaEstructura extends Exporta {
	
	/**
	 * Instancia de la propia classe seguint el patró de disseny singleton
	 */
	private static ExportaEstructura instancia = new ExportaEstructura();
	
	/**
	 * Creadora buida
	 */
	private ExportaEstructura() {};
	
	/**
	 * Retorna l'única instancia de la classe
	 * @return una instancia de la classe 
	 */
	public static ExportaEstructura getInstancia() {
		return instancia;
	}
	
	/**
	 * Exporta una Estructura
	 * @param e estructura que volem exportar
	 * @param n número d'horari que estem exportant
	 * @param crea true si volem que escrigui al fitxer, false si només volem retornar la codificació
	 * @return la codificació de l'Estructura
	 */
	static String exportaEstructura(Estructura e, int n, boolean crea) {
		String endl = "\n";
		String str = "Horari ".concat(String.valueOf(n)).concat(endl);
		ExportaSegment es = ExportaSegment.getInstancia();
		HashSet<Segment> s;
		for (int dia = 0; dia < 7; ++dia) {
			str = str.concat(String.valueOf(dia)).concat(endl);
			str = str.concat("{").concat(endl);
			for (int hora = 0; hora < 24; ++hora) {
				str = str.concat(String.valueOf(hora)).concat(endl);
				str = str.concat("{").concat(endl);
				s = e.getAllSegments(dia, hora);
				if (s.isEmpty()) str = str.concat("buit").concat(endl);
				else {
					for (Segment se : s) str = str.concat(es.exportaSegment(se, false));
				}
				str = str.concat("}").concat(endl);
			}
			str = str.concat("}").concat(endl);
		}
		str = str.concat("END").concat(endl);
		if (crea) Exporta.exporta(str);
		return str;
	}
}
