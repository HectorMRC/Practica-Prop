package domini;

import domini.classes.*;
import java.util.*;
import utils.*;

/**
 * 
 * @author eric.casanovas@est.fib.upc.edu
 * @autor hector.morales.carnice@est.fib.upc.edu
 *
 */
public final class ControladorDomini {
	/**
	 * Crea, si pot, un campus amb nomCampus.
	 * @param nomCampus Indica el nom del nou campus.
	 * @return Un String null, si s'ha pogut crear; altrament conté el titol d'error.
	 */
	public String CrearCampus(String campus) {
		try {
			Campus.newCampus(campus);
			
		} catch(Exception e) {
			return e.toString();
		}
		
		return null;
	}
	
	public String EliminarCampus(String campus) {
		Campus.eliminarCampus(campus);
		return null;
	}
	
	public String CrearAula(String campus, String aula, int capacitat) {
		try {			
			Campus.getCampus(campus).altaAula(aula, capacitat);
		}
		catch(Exception e) {
			return e.toString();
		}
		
		return null;
	}
	
	public String EliminarAula(String campus, String aula) {
		if(Campus.getCampus(campus) == null) return "El campus no existeix";
		Campus.getCampus(campus).baixaAula(aula);
		return null;
	}
	
	public String CrearPlaEstudis(String plaEstudis) {
		try {
			PlaEstudis.newPlaEstudis(plaEstudis);
			
		} catch(Exception e) {
			return e.toString();
		}
		
		return null;
	}
	
	public String EliminaPlaEstudis(String plaEstudis) {
		PlaEstudis.eliminaPlaEstudis(plaEstudis);
		return null;
	}
	
	public String CrearAssignatura(String plaEstudis, String assignatura) {
		try {
			PlaEstudis.getPlaEstudis(plaEstudis).altaAssignatura(assignatura);
		}
		catch(Exception e) {
			return e.toString();
		}
		
		return null;
	}

	public String EliminarAssignatura(String plaEstudis, String assignatura) {
		try {
			PlaEstudis.getPlaEstudis(plaEstudis).baixaAssignatura(assignatura);
		}
		catch(Exception e) {
			return e.toString();
		}
		
		return null;
	}

	public String CrearGrup(String plaEstudis, String assignatura, int grup, int capacitat) {
		try {
			PlaEstudis.getPlaEstudis(plaEstudis).getAssignatura(assignatura).altaGrup(grup, capacitat, "MT");
		}
		catch(Exception e) {
			return e.toString();
		}
		
		return null;
	}
	
	public String EliminarGrup(String plaEstudis, String assignatura, int grup) {
		PlaEstudis.getPlaEstudis(plaEstudis).getAssignatura(assignatura).baixaGrup(grup);
		return null;
	}
	
	public String CrearSubGrup(String plaEstudis, String assignatura, int grup, int subGrup, int places, boolean force) {
		try {
			PlaEstudis.getPlaEstudis(plaEstudis).getAssignatura(assignatura).getGrup(grup).altaSubGrup(subGrup, places, force);
		}
		catch(Exception e) {
			return e.toString();
		}
		
		return null;
	}
	
	public String EliminaSubGrup(String plaEstudis, String assignatura, int grup, int subGrup) {
		PlaEstudis.getPlaEstudis(plaEstudis).getAssignatura(assignatura).getGrup(grup).baixaSubGrup(subGrup);
		return null;
	}
	
	public String CrearSessioGrup(String plaEstudis, String assignatura, String tipus, int hores) {
		try {
			PlaEstudis.getPlaEstudis(plaEstudis).getAssignatura(assignatura).altaSessioG(tipus, hores);
		}
		catch(Exception e) {
			return e.toString();
		}
		
		return null;
	}
	
	public String EliminaSessioGrup(String plaEstudis, String assignatura, String tipus, int hores) {
		try {
			PlaEstudis.getPlaEstudis(plaEstudis).getAssignatura(assignatura).baixaSessioG(tipus, hores);
		}
		catch(Exception e) {
			return e.toString();
		}
		
		return null;
	}

	public String CrearSessioSubGrup(String plaEstudis, String assignatura, String tipus, int hores) {
		try {
			PlaEstudis.getPlaEstudis(plaEstudis).getAssignatura(assignatura).altaSessioSG(tipus, hores);
		}
		catch(Exception e) {
			return e.toString();
		}
		
		return null;
	}
	
	public String EliminaSessioSubGrup(String plaEstudis, String assignatura, String tipus, int hores) {
		try {
			PlaEstudis.getPlaEstudis(plaEstudis).getAssignatura(assignatura).baixaSessioSG(tipus, hores);
		}
		catch(Exception e) {
			return e.toString();
		}
		
		return null;
	}
	
	public String AssignaSessioGrup(String plaEstudis, String assignatura, String tipus, int hores, int grup) {
		try {
			PlaEstudis.getPlaEstudis(plaEstudis).getAssignatura(assignatura).getSessioG(tipus, hores).assignaSessio(grup);
		}
		catch(Exception e) {
			return e.toString();
		}
		
		return null;
	}

	public String DesassignaSessioGrup(String plaEstudis, String assignatura, String tipus, int hores, int grup) {
		try {
			SessioGAssignada sessio = PlaEstudis.getPlaEstudis(plaEstudis).getAssignatura(assignatura).getGrup(grup).getSessio(tipus, hores);
			PlaEstudis.getPlaEstudis(plaEstudis).getAssignatura(assignatura).getSessioG(tipus, hores).desassignaSessio(sessio);
		}
		catch(Exception e) {
			return e.toString();
		}
		
		return null;
	}
	
	public String AssignaSessioSubGrup(String plaEstudis, String assignatura, String tipus, int hores, int grup, int subgrup) {
		try {
			PlaEstudis.getPlaEstudis(plaEstudis).getAssignatura(assignatura).getSessioSG(tipus, hores).assignaSessio(grup, subgrup);
		}
		catch(Exception e) {
			return e.toString();
		}
		
		return null;
	}
	
	public String DesassignaSessioSubGrup(String plaEstudis, String assignatura, String tipus, int hores, int grup, int subgrup) {
		try {
			SessioSGAssignada sessio = PlaEstudis.getPlaEstudis(plaEstudis).getAssignatura(assignatura).getGrup(grup).getSubGrup(subgrup).getSessio(tipus, hores);
			PlaEstudis.getPlaEstudis(plaEstudis).getAssignatura(assignatura).getSessioSG(tipus, hores).desassignaSessio(sessio);
		}
		catch(Exception e) {
			return e.toString();
		}
		
		return null;
	}
	
	public String HoresAptes(String plaEstudis, String assignatura, int grup, int subgrup, Map<Integer, int[]> franja, boolean apte, boolean force) {
		try {
			if(grup == 0 && subgrup == 0)
				PlaEstudis.getPlaEstudis(plaEstudis).getAssignatura(assignatura).setHoresAptes(franja, apte, force);
			else if(subgrup == 0)
				PlaEstudis.getPlaEstudis(plaEstudis).getAssignatura(assignatura).getGrup(grup).setHoresAptes(franja, apte, force);
			else
				PlaEstudis.getPlaEstudis(plaEstudis).getAssignatura(assignatura).getGrup(grup).getSubGrup(subgrup).setHoresAptes(franja, apte, force);
		}
		catch(Exception e) {
			return e.toString();
		}
		
		return null;
	}

	public String SetSolapamentAssig(String plaEstudis, String assignatura, String assigToRegister, boolean permet) {
		try {
			Assignatura assig = PlaEstudis.getPlaEstudis(plaEstudis).getAssignatura(assigToRegister);
			PlaEstudis.getPlaEstudis(plaEstudis).getAssignatura(assignatura).setSolapament(assig, permet);
		}
		catch(Exception e) {
			return e.toString();
		}
		
		return null;
	}
	
	public String SetSolapamentGrup(String plaEstudis, String assignatura, int grup, String assigToRegister, int numToRegister, boolean permet) {
		try {
			PlaEstudis.getPlaEstudis(plaEstudis).getAssignatura(assignatura).getGrup(grup).setSolapament(assigToRegister, numToRegister, permet);
		}
		catch(Exception e) {
			return e.toString();
		}
		
		return null;
	}
	
	public String SetSolapamentSubGrup(String plaEstudis, String assignatura, int grup, int subgrup, String assigToRegister, int numToRegister, boolean permet) {
		try {
			PlaEstudis.getPlaEstudis(plaEstudis).getAssignatura(assignatura).getGrup(grup).getSubGrup(subgrup).setSolapament(assigToRegister, numToRegister, permet);
		}
		catch(Exception e) {
			return e.toString();
		}
		
		return null;
	}
}
