package classes;
import java.util.*;
/**
 * 
 * @author adria.manero@est.fib.upc.edu
 *
 */
public class Assignatura {
		
	/////////////////////////////////////////////////////////////
	////////////////////////Variables //////////////////////////
		
	/**
	 * Nom  de l'Assignatura
	 */
	private String nom;
	
	/**
	 * Hores de teoria  de l'Assignatura
	 */
	private int hteo;
		
	/**
	 * Hores de laboratori  de l'Assignatura
	 */
	private int hlab;
		
	/**
	 * Plans d'Estudis que pertanyen a l'Assignatura
	 */
	HashSet<PlaEstudis> plansEstudis;
		
	/**
	 * SessionsGrup que pertanyen a l'Assignatura
	 */
	HashSet<SessioGrup> sessionsG;
	
	/**
	 * SessionsSubGrup que pertanyen a l'Assignatura
	 */
	HashSet<SessioSubGrup> sessionsSG;
		
	/**
	 * Grups que pertanyen a l'Assignatura
	 */
	HashSet<Grup> grups;
		
	/////////////////////////////////////////////////////////////
	//////////////////////// Privades //////////////////////////
		
	/**
	 * Retorna si ja existeix un Pla d'Estudis igual en aquesta Assignatura.
	 * @param nom: Nom del Pla d'Estudis.
	 * @return Cert si el Pla d'Estudis ja existeix o fals altrament.
	 */
	public boolean checkPlaEstudis(String nom) throws Exception {
		if (nom == null) throw new Exception();
		else {
			for(PlaEstudis p : plansEstudis) {
				if (p.getNom().equals(nom)) return true;
			}
			return false;
		}
	}
	
	/////////////////////////////////////////////////////////////
	//////////////////////  Constructora  ///////////////////////
		
	/**
	 * Creadora de Assignatura amb parametres.
	 * @param nom: nom de l'assignatura.
	 * @param hteo: hores de teoria de l'assignatura.
	 * @param hlab: hores de laboratori de l'assignatura.
	 */
	public Assignatura(PlaEstudis plaEst, String nom, int hteo, int hlab) throws Exception {
		ExceptionManager.thrower(this.setNom(plaEst,nom));
		ExceptionManager.thrower(this.setHTeo(hteo));
		ExceptionManager.thrower(this.setHLab(hlab));
	}
		
	/**
	 * Creadora de Assignatura sense parametres.
	 * @param plaEstudis: Pla d'Estudis que pertany l'Assignatura.
	 */
	public Assignatura(PlaEstudis plaEst, String nom) throws Exception {
		ExceptionManager.thrower(this.setNom(plaEst,nom));
		this.hteo = 0;
		this.hlab = 0;
	}
		
	/////////////////////////////////////////////////////////////
	//////////////////////// Setters  //////////////////////////
		
	/**
	 * Assigna el nom de l'Assignatura.
	 * @param nom: nom de l'Assignatura que entra l'usuari.
	 * @throws Exception si nom no cambia o nom ja existeix en el Pla d'Estudis.
	 */
	public int setNom(PlaEstudis plaEst, String nom) {
		if (nom == null) return 29;
		else if(this.nom.equals(nom)) return 1;
		else if(plaEst.checkAssignatura(nom)) return 20;
		this.nom = nom;
		return 0;
	}
		
	/**
	 * Assigna quantes hores de teoria te l'Assignatura.
	 * @param hteo: nombre d'hores de teoria de l'Assignatura que entra l'usuari.
	 * @throws Exception si hteo < 0 o hteo no cambia.
	 */
	public int setHTeo(int hteo) {
		if (this.hteo == hteo) return 1;
		else if (hteo < 0) return 21;// error: les hores de teo són negatives
		this.hteo = hteo;
		return 0;
	}
		
	/**
	 * Assigna quantes hores de laboratori te l'Assignatura.
	 * @param hlab: nombre d'hores de laboratori de l'Assignatura que entra l'usuari.
	 * @throws Exception si hlab < 0 o hlab no cambia.
	 */
	public int setHLab(int hlab) throws Exception {
		if (this.hlab == hlab) return 1;
		else if (hlab < 0) return 22;// error: les hores de lab són negatives
		this.hlab = hlab;
		return 0;
	}
	
	/////////////////////////////////////////////////////////////
	////////////////////////Getters  //////////////////////////
		
	/**
	 * Retorna el Nom de l'Assignatura.
	 * @return Nom de l'Assignatura.
	 */
	public String getNom() {
		return this.nom;
	}
		
	/**
	 * Retorna les hores de teoria de l'Assignatura.
	 * @return Hores de teoria de l'Assignatura.
	 */
	public int getHTeo() {
		return this.hteo;
	}
		
	/**
	 * Retorna les hores de laboratori de l'Assignatura.
	 * @return Hores de laboratori de l'Assignatura.
	 */
	public int getHLab() {
		return this.hlab;
	}
		
	/**
	 * Retorna la sessioG = Tipus + Hores de l'Assignatura.
	 * @return SessioG de l'Assignatura.
	 */
	public SessioGrup getSessioG(String tipus, int hores) throws Exception {
		if (nom == null) throw new Exception();
		else {
			for(SessioGrup s: sessionsG)
				if(s.getTipus().equals(tipus) && s.getHores() == hores) return s;
			return null;
		}
	}
		
	/**
	 * Retorna la sessioSG = Tipus + Hores de l'Assignatura.
	 * @return SessioSG de l'Assignatura.
	 */
	public SessioSubGrup getSessioSG(String tipus, int hores) throws Exception {
		if (tipus == null) throw new Exception();
		else {
			for(SessioSubGrup s: sessionsSG)
				if(s.getTipus().equals(tipus) && s.getHores() == hores) return s;
			return null;
		}
	}
		
	/**
	 * Retorna el Grup = idGrup de l'Assignatura.
	 * @return Grup de l'Assignatura.
	 */
	public Grup getGrup(int idgrup) {
		for(Grup g: grups)
			if(g.getNumero() == idgrup) return g;
		return null;
	}	
			
	/////////////////////////////////////////////////////////////
	//////////////////////// Modificadores  ////////////////////
	
	/** 
	 * Crea un nou Grup dins l'Assignatura corresponent sempre i quant els atributs entrats
	 * compleixin amb les especificacions. Altrament llança una excepció.
	 * @param numero: Indica el numero que identificarà al Grup.
	 * @param capacitat: Indica quina capacitat ha de tenir el Grup.
	 * @return Excepció codificada en forma d'enter.
	 */
	public int altaGrup(int numero, int capacitat, String franja) throws Exception{
		if (franja == null) return 29;
		if (this.checkGrup(numero)) return 23;
		if (capacitat < 0) return 24;
		grups.add(new Grup(this,numero,capacitat,franja));
		return 0;
	}
		
	/**
	 * Dona de baixa el grup = numero sempre i quant existeixi en aquesta Assignatura.
	 * @param numero: numero de grup que volem donar de baixa.
	 * @return Excepció codificada en forma d'enter.
	 */
	public int baixaGrup(int numero) {
		if (checkGrup(numero)) {
			grups.removeIf(item -> item.getNumero() == numero);
			return 0;
		}
		else return 27;
	}
			
	/** 
	 * Crea una nova Sessio dins l'Assignatura corresponent sempre i quant els atributs entrats
	 * compleixin amb les especificacions. Altrament llança una excepció.
	 * @param hores: Indica el numero d'hores de la Sessio.
	 * @param tipus: Indica el tipus de la Sessio
	 * @return Excepció codificada en forma d'enter.
	 * @throws Exception 
	 */
	public int altaSessioG(String tipus, int hores) throws Exception {
		if (tipus == null) return 29;
		if (this.checkSessioG(tipus,hores)) return 25;
		else if (hores < 0) return 26;
		sessionsG.add(new SessioGrup(this,hores,tipus));
		return 0;
	}
	
	/** 
	 * Crea una nova Sessio dins l'Assignatura corresponent sempre i quant els atributs entrats
	 * compleixin amb les especificacions. Altrament llança una excepció.
	 * @param hores: Indica el numero d'hores de la Sessio.
	 * @param tipus: Indica el tipus de la Sessio
	 * @return Excepció codificada en forma d'enter.
	 * @throws Exception 
	 */
	public int altaSessioSG(String tipus, int hores) throws Exception {
		if (tipus == null) return 29;
		if (this.checkSessioSG(tipus,hores)) return 25;
		else if (hores < 0) return 26;
		sessionsSG.add(new SessioSubGrup(this,hores,tipus));
		return 0;
	}
		
	/**
	 * Dona de baixa la  Sessio = Hores + Tipus sempre i quant existeixi en aquesta Assignatura.
	 * @param hores: hores de la sessio que volem donar de baixa.
	 * @param tipus: tipus de la sessio que volem donar de baixa.
	 * @return Excepció codificada en forma d'enter.
	 */
	public int baixaSessioG(String tipus, int hores) throws Exception {
		if (tipus == null) return 29;
		if (checkSessioG(tipus,hores)) sessionsG.removeIf(item -> item.getHores() == hores && item.getTipus().equals(tipus));
		else return 28;
		return 0;
	}
	
	/**
	 * Dona de baixa la  Sessio = Hores + Tipus sempre i quant existeixi en aquesta Assignatura.
	 * @param hores: hores de la sessio que volem donar de baixa.
	 * @param tipus: tipus de la sessio que volem donar de baixa.
	 * @return Excepció codificada en forma d'enter.
	 */
	public int baixaSessioSG(String tipus, int hores) throws Exception {
		if (tipus == null) return 29;
		if (checkSessioSG(tipus,hores)) sessionsSG.removeIf(item -> item.getHores() == hores && item.getTipus().equals(tipus));
		else return 28;
		return 0;
	}
		
	/** 
	 * Crea una nou Pla d'Estudis relacionat amb l'Assignatura sempre i quant els atributs entrats
	 * compleixin amb les especificacions. Altrament llança una excepció.
	 * @param nom: Indica el nom del Pla d'Estudis.
	 * @return Excepció codificada en forma d'enter.
	 * @throws Exception 
	 */
	public int altaPlaEstudis(String nom) throws Exception {
		if (nom == null) return 29;
		if (this.checkPlaEstudis(nom)) return 27;
		plansEstudis.add(new PlaEstudis(nom));
		return 0;
	}
		
	/**
	 * Dona de baixa el PlaEstudis = nom sempre i quant existeixi en aquesta Assignatura.
	 * @param nom: nom del Pla d'Estudis que volem donar de baixa.
	 * @return Excepció codificada en forma d'enter.
	 */
	public int baixaPlaEstudis(String nom) throws Exception {
		if (nom == null) return 29;
		if(checkPlaEstudis(nom)) {
			plansEstudis.removeIf(item -> item.getNom().equals(nom));
		}
		else return 29;
		return 0;
	}
		
	/////////////////////////////////////////////////////////////
	//////////////////////// Funcions  //////////////////////////
		
		/**
	 * Retorna si ja existeix un grup amb el mateix numero en aquesta Assignatura.
	 * @param numero: numero de grup que volem comprobar.
	 * @return Cert si el grup ja existeix o fals altrament.
	 */
	public boolean checkGrup(int numero) {
		for(Grup g : grups) {
			if (g.getNumero() == numero) return true;
		}
		return false;
	}
		
		/**
	 * Retorna si ja existeix una SessioG igual en aquesta Assignatura.
		 * @param hores: Numero d'hores de la sessióG.
		 * @param tipus: Tipus de la sessióG.
		 * @return Cert si la sessióG existeix o fals altrament.
		 */
	public boolean checkSessioG(String tipus, int hores) throws Exception {
		if (tipus == null) throw new Exception();
		for(Sessio s : sessionsG) {
			if (s.getHores() == hores && s.getTipus().equals(tipus)) return true;
		}
		return false;
	}
			
		/**
	 * Retorna si ja existeix una SessioSG igual en aquesta Assignatura.
		 * @param hores: Numero d'hores de la sessióSG.
		 * @param tipus: Tipus de la sessióSG.
		 * @return Cert si la sessióSG existeix o fals altrament.
		 */
	public boolean checkSessioSG(String tipus, int hores) throws Exception {
		if (tipus == null) ExceptionManager.thrower(29);		
		for(Sessio s : sessionsSG) {
			if (s.getHores() == hores && s.getTipus().equals(tipus)) return false;
		}
		return false;
	}
		
}
