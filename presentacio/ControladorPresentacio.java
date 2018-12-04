package presentacio;

import java.util.HashSet;

import java.util.Map;

import domini.ControladorDomini;
import domini.classes.*;
import domini.restriccions.HoresAptes;
import domini.restriccions.Solapaments;
import persistencia.ControladorPersistencia;
import utils.Estructura;
import presentacio.vistes.*;

/**
 * 
 * @author adria.manero@est.fib.upc.edu
 *
 */
public class ControladorPresentacio {

	////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////  INSTANCIA  /////////////////////////////////////
	
	private static ControladorPresentacio current;
	
	public static ControladorPresentacio getInstance() {
		if(current != null) current = new ControladorPresentacio();
		return current;
	}
	
	////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////  ACCIONS  ////////////////////////////////////
	
	public void CrearCampus(String campus) {
		String s = ControladorDomini.getInstance().CrearCampus(campus);
		if(s != null) Main.getInstance().showWarning("Error crear Campus",s);
	}
	
	public void EliminarCampus(String campus) {
		ControladorDomini.getInstance().EliminarCampus(campus);
	}
	
	public void ModificarCampus(String campus, String nom, String autor) {
		String s = ControladorDomini.getInstance().ModificarCampus(campus,nom,autor);
		if(s != null) Main.getInstance().showWarning("Error modificar de Campus",s);
	}
	
	public void CrearAula(String campus, String aula, int capacitat) {
		String s = ControladorDomini.getInstance().CrearAula(campus,aula,capacitat);
		if(s != null) Main.getInstance().showWarning("Error crear Aula",s);
	}
	
	public void EliminarAula(String campus, String aula) {
		String s = ControladorDomini.getInstance().EliminarAula(campus,aula);
		if(s != null) Main.getInstance().showWarning("Error eliminar Aula",s);
	}
	
	public void ModificarAula(String campus, String aula, String nom, int capacitat) {
		String s = ControladorDomini.getInstance().ModificarAula(campus,aula,nom,capacitat);
		if(s != null) Main.getInstance().showWarning("Error modificar Aula",s);
	}
	
	public void CrearPlaEstudis(String plaEstudis) {
		String s = ControladorDomini.getInstance().CrearPlaEstudis(plaEstudis);
		if(s != null) Main.getInstance().showWarning("Error crear Pla d'Estudis",s);
	}
	
	public void EliminaPlaEstudis(String plaEstudis) {
		ControladorDomini.getInstance().EliminaPlaEstudis(plaEstudis);
	}
	
	public void ModificarPlaEstudis(String plaEstudis, String nom, String autor, Map<Integer, boolean[]> lectiu, int[] rang) {
		String s = ControladorDomini.getInstance().ModificarPlaEstudis(plaEstudis,nom,autor, lectiu, rang);
		if(s != null) Main.getInstance().showWarning("Error modificar Pla d'Estudis",s);
		
	}
	
	public void CrearAssignatura(String plaEstudis, String assignatura) {
		String s = ControladorDomini.getInstance().CrearAssignatura(plaEstudis,assignatura);
		if(s != null) Main.getInstance().showWarning("Error crear Assignatura",s);
	}

	public void EliminarAssignatura(String plaEstudis, String assignatura) {
		String s = ControladorDomini.getInstance().EliminarAssignatura(plaEstudis,assignatura);
		if(s != null) Main.getInstance().showWarning("Error eliminar Assignatura",s);
	}

	public void ModificarAssginatura(String plaEstudis, String assignatura, String nom) {
		String s = ControladorDomini.getInstance().ModificarAssignatura(plaEstudis,assignatura,nom);
		if(s != null) Main.getInstance().showWarning("Error modificar Assignatura",s);
	}
	
	public void CrearGrup(String plaEstudis, String assignatura, int grup, int capacitat) {
		String s = ControladorDomini.getInstance().CrearGrup(plaEstudis,assignatura,grup,capacitat);
		if(s != null) Main.getInstance().showWarning("Error crear Grup",s);
	}
	
	public void EliminarGrup(String plaEstudis, String assignatura, int grup) {
		ControladorDomini.getInstance().EliminarGrup(plaEstudis,assignatura,grup);
	}
	
	public void ModificarGrup(String plaEstudis, String assignatura, int grup, int numero, int places, String franja) {
		String s = ControladorDomini.getInstance().ModificarGrup(plaEstudis,assignatura,grup,numero,places,franja);
		if(s != null) Main.getInstance().showWarning("Error modificar Grup",s);
		
	}
	
	public void CrearSubGrup(String plaEstudis, String assignatura, int grup, int subGrup, int places, boolean force) {
		String s = ControladorDomini.getInstance().CrearSubGrup(plaEstudis,assignatura,grup,subGrup,places,force);
		if(s != null) Main.getInstance().showWarning("Error crear SubGrup",s);
	}
	
	public void EliminaSubGrup(String plaEstudis, String assignatura, int grup, int subGrup) {
		ControladorDomini.getInstance().EliminaSubGrup(plaEstudis,assignatura,grup,subGrup);
	}
	
	public void ModificarSubGrup(String plaEstudis, String assignatura, int grup, int subgrup, int numero, int places, boolean incr) {
		String s = ControladorDomini.getInstance().ModificarSubGrup(plaEstudis,assignatura,grup,subgrup,numero,places,incr);
		if(s != null) Main.getInstance().showWarning("Error modificar SubGrup",s);
	}
	
	public void CrearSessioGrup(String plaEstudis, String assignatura, String tipus, int hores) {
		String s = ControladorDomini.getInstance().CrearSessioGrup(plaEstudis,assignatura,tipus,hores);
		if(s != null) Main.getInstance().showWarning("Error crear SessioGrup",s);
	}
	
	public void EliminaSessioGrup(String plaEstudis, String assignatura, String tipus, int hores) {
		String s = ControladorDomini.getInstance().EliminaSessioGrup(plaEstudis,assignatura,tipus,hores);
		if(s != null) Main.getInstance().showWarning("Error eliminar SessioGrup",s);
	}
	
	public void CrearSessioSubGrup(String plaEstudis, String assignatura, String tipus, int hores) {
		String s = ControladorDomini.getInstance().CrearSessioSubGrup(plaEstudis,assignatura,tipus,hores);
		if(s != null) Main.getInstance().showWarning("Error crear SessioSubGrup",s);
	}
	
	public void EliminaSessioSubGrup(String plaEstudis, String assignatura, String tipus, int hores) {
		String s = ControladorDomini.getInstance().EliminaSessioSubGrup(plaEstudis,assignatura,tipus,hores);
		if(s != null) Main.getInstance().showWarning("Error eliminar SessioSubGrup",s);
	}
	
	public void AssignaSessioGrup(String plaEstudis, String assignatura, String tipus, int hores, int grup) {
		String s = ControladorDomini.getInstance().AssignaSessioGrup(plaEstudis,assignatura,tipus,hores,grup);
		if(s != null) Main.getInstance().showWarning("Error assignar SessioGrup",s);
	}

	public void DesassignaSessioGrup(String plaEstudis, String assignatura, String tipus, int hores, int grup) {
		String s = ControladorDomini.getInstance().DesassignaSessioGrup(plaEstudis,assignatura,tipus,hores,grup);
		if(s != null) Main.getInstance().showWarning("Error desassignar SessioGrup",s);
	}
	
	public void AssignaSessioSubGrup(String plaEstudis, String assignatura, String tipus, int hores, int grup, int subgrup) {
		String s = ControladorDomini.getInstance().AssignaSessioSubGrup(plaEstudis,assignatura,tipus,hores,grup,subgrup);
		if(s != null) Main.getInstance().showWarning("Error Assignar SessioSubGrup",s);
	}
	
	public void DesassignaSessioSubGrup(String plaEstudis, String assignatura, String tipus, int hores, int grup, int subgrup) {
		String s = ControladorDomini.getInstance().DesassignaSessioSubGrup(plaEstudis,assignatura,tipus,hores,grup,subgrup);
		if(s != null) Main.getInstance().showWarning("Error Desassignar SessioSubGrup",s);
	}
	
	public void HoresAptes(String plaEstudis, String assignatura, int grup, int subgrup, Map<Integer, int[]> franja, boolean apte, boolean force) {
		String s = ControladorDomini.getInstance().HoresAptes(plaEstudis,assignatura,grup,subgrup,franja,apte,force);
		if(s != null) Main.getInstance().showWarning("Error Hores Aptes",s);
	}

	public void SetSolapamentAssig(String plaEstudis, String assignatura, String assigToRegister, boolean permet) {
		String s = ControladorDomini.getInstance().SetSolapamentAssig(plaEstudis,assignatura,assigToRegister,permet);
		if(s != null) Main.getInstance().showWarning("Error Solapament Assignatura",s);
	}
	
	public void SetSolapamentGrup(String plaEstudis, String assignatura, int grup, String assigToRegister, int numToRegister, boolean permet) {
		String s = ControladorDomini.getInstance().SetSolapamentGrup(plaEstudis,assignatura,grup,assigToRegister,numToRegister,permet);
		if(s != null) Main.getInstance().showWarning("Error Solapament Grup",s);
	}
	public void SetSolapamentSubGrup(String plaEstudis, String assignatura, int grup, int subgrup, String assigToRegister, int numToRegister, boolean permet) {
		String s = ControladorDomini.getInstance().SetSolapamentSubGrup(plaEstudis,assignatura,grup,subgrup,assigToRegister,numToRegister,permet);
		if(s != null) Main.getInstance().showWarning("Error Solapament SubGrup",s);
	}


////////////////////////////////////////////////////////////////////////////////
///////////////////////////////  FUNCIONS PERSISTENCIA  ////////////////////////
	
////////////////////////////////////////////////////////////////////////////////
///////////////////////////////  EXPORTS  //////////////////////////////////////

	public void exportaAssignatura(Assignatura a) {
		String s = ControladorPersistencia.getInstancia().exportaAssignatura(a);
		if(s != null) Main.getInstance().showWarning("Error exportar Assignatura",s);
	}
	
	public void exportaAula(Aula a) {
		String s = ControladorPersistencia.getInstancia().exportaAula(a);
		if(s != null) Main.getInstance().showWarning("Error exportar Aula",s);
	}
	
	public void exportaCampus(Campus c) {
		String s = ControladorPersistencia.getInstancia().exportaCampus(c);	
		if(s != null) Main.getInstance().showWarning("Error exportar Campus",s);
	}
	
	public void exportaData(Data d) {
		String s = ControladorPersistencia.getInstancia().exportaData(d);
		if(s != null) Main.getInstance().showWarning("Error exportar Data",s);
	}
	
	public void exportaGrup(Grup g) {
		String s = ControladorPersistencia.getInstancia().exportaGrup(g);
		if(s != null) Main.getInstance().showWarning("Error exportar Grup",s);
	}
	
	public void exportaHorari(HashSet<Estructura> e) {
		String s = ControladorPersistencia.getInstancia().exportaHorari(e);
		if(s != null) Main.getInstance().showWarning("Error exportar Horari",s);
	}
	
	public void exportaHoresAptes(HoresAptes ha) {
		String s = ControladorPersistencia.getInstancia().exportaHoresAptes(ha);
		if(s != null) Main.getInstance().showWarning("Error exportar HoresAptes",s);
	}
	
	public void exportaPlaEstudis(PlaEstudis pe) {
		String s = ControladorPersistencia.getInstancia().exportaPlaEstudis(pe);
		if(s != null) Main.getInstance().showWarning("Error exportar PlaEstudis",s);
	}
	
	public void exportaSessioGAssignada(SessioGAssignada sga) {
		String s = ControladorPersistencia.getInstancia().exportaSessioGAssignada(sga);
		if(s != null) Main.getInstance().showWarning("Error exportar SessioGAssignada",s);
	}
	
	public void exportaSessioGrup(SessioGrup sg) {
		String s = ControladorPersistencia.getInstancia().exportaSessioGrup(sg);
		if(s != null) Main.getInstance().showWarning("Error exportar SessioGrup",s);
	}
	
	public void exportaSessioSGAssignada(SessioSGAssignada ssga) {
		String s = ControladorPersistencia.getInstancia().exportaSessioSGAssignada(ssga);
		if(s != null) Main.getInstance().showWarning("Error exportar SessioSGAssignada",s);
	}
	
	public void exportaSessioSubGrup(SessioSubGrup ssg) {
		String s = ControladorPersistencia.getInstancia().exportaSessioSubGrup(ssg);
		if(s != null) Main.getInstance().showWarning("Error exportar SessioSubGrup",s);
	}
	
	public void exportaSubGrup(SubGrup sg) {
		String s = ControladorPersistencia.getInstancia().exportaSubGrup(sg);
		if(s != null) Main.getInstance().showWarning("Error exportar SessioSubGrup",s);
	}
	
	public void exportaSolapaments(Solapaments sol) {
		String s = ControladorPersistencia.getInstancia().exportaSolapaments(sol);
		if(s != null) Main.getInstance().showWarning("Error exportar Solapaments",s);
	}
	
	////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////  IMPORTS  //////////////////////////////////////
	
	public void importaAssignatura(String path, PlaEstudis pe) {
		String s = ControladorPersistencia.getInstancia().importaAssignatura(path,pe);
		if(s != null) Main.getInstance().showWarning("Error importar Assignatura",s);
	}
	
	public void importaAula(String path, Campus c) {
		String s = ControladorPersistencia.getInstancia().importaAula(path,c);
		if(s != null) Main.getInstance().showWarning("Error importar Aula",s);
	}
	
	public void importaCampus(String path) {
		String s = ControladorPersistencia.getInstancia().importaCampus(path);
		if(s != null) Main.getInstance().showWarning("Error importar Campus",s);	
	}
	
	public void importaData(String path) {
		String s = ControladorPersistencia.getInstancia().importaData(path);
		if(s != null) Main.getInstance().showWarning("Error importar Data",s);
	}
	
	public void importaGrup(String path, PlaEstudis pe, Assignatura a) {
		String s = ControladorPersistencia.getInstancia().importaGrup(path,pe,a);
		if(s != null) Main.getInstance().showWarning("Error importar Grup",s);
	}
	
	public void importaHorari(String path, PlaEstudis pe, Campus c) {
		String s = ControladorPersistencia.getInstancia().importaHorari(path,pe,c);
		if(s != null) Main.getInstance().showWarning("Error importar Horari",s);
	}
	
	public void importaHoresAptes(String path, PlaEstudis pe) {
		String s = ControladorPersistencia.getInstancia().importaHoresAptes(path,pe);
		if(s != null) Main.getInstance().showWarning("Error importar HoresAptes",s);
	}
	
	public void importaPlaEstudis(String path) {
		String s = ControladorPersistencia.getInstancia().importaPlaEstudis(path);
		if(s != null) Main.getInstance().showWarning("Error importar PlaEstudis",s);
	}
	
	public void importaSessioGAssignada(String path, PlaEstudis pe, Assignatura a, Grup g) {
		String s = ControladorPersistencia.getInstancia().importaSessioGAssignada(path,pe,a,g);
		if(s != null) Main.getInstance().showWarning("Error importar SessioGAssignada",s);
	}
	
	public void importaSessioGrup(String path, PlaEstudis pe, Assignatura a) {
		String s = ControladorPersistencia.getInstancia().importaSessioGrup(path,pe,a);
		if(s != null) Main.getInstance().showWarning("Error importar SessioGrup",s);
	}
	
	public void importaSessioSGAssignada(String path, PlaEstudis pe, Assignatura a, Grup g, SubGrup sg) {
		String s = ControladorPersistencia.getInstancia().importaSessioSGAssignada(path,pe,a,g,sg);
		if(s != null) Main.getInstance().showWarning("Error importar SessioSGAssignada",s);
	}
	
	public void importaSessioSubGrup(String path, PlaEstudis pe, Assignatura a) {
		String s = ControladorPersistencia.getInstancia().importaSessioSubGrup(path,pe,a);
		if(s != null) Main.getInstance().showWarning("Error importar SessioSubGrup",s);
	}
	
	public void importaSubGrup(String path, PlaEstudis pe, Assignatura a, Grup g) {
		String s = ControladorPersistencia.getInstancia().importaSubGrup(path,pe,a,g);
		if(s != null) Main.getInstance().showWarning("Error importar SubGrup",s);
	}
	
	public void importaSolapaments(String path, PlaEstudis pe, Assignatura a, Grup g, SubGrup sg) {
		String s = ControladorPersistencia.getInstancia().importaSolapaments(path,pe,a,g,sg);
		if(s != null) Main.getInstance().showWarning("Error importar Solapaments",s);
	}


}
