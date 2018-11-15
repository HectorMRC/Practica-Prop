package classes;
import java.util.*;
import restriccions.*;
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
	 * Plans d'Estudis que pertanyen a l'Assignatura
	 */
	PlaEstudis plaEstudis;
		
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
	
	/**
	 * Lincament a NoCiclesRequisits
	 */
	private NoCiclesRequisits noCicles;
	
	/**
	 * Lincament a HoresSenseClasseAssignatura
	 */
	private HoresSenseClasseAssignatura horesAptes;
	
	/**
	 * Lincament a Correquisit
	 */
	private Correquisit corr;
	
	/**
	 * Lincament a NoSolaparAssignatura
	 */
	private NoSolaparAssignatura solapament;
	
	/////////////////////////////////////////////////////////////
	//////////////////////  Constructora  ///////////////////////
		
	/**
	 * Creadora de Assignatura amb parametres.
	 * @param nom: nom de l'assignatura.
	 */
	public Assignatura(PlaEstudis plaEst, String nom) throws Exception {
		 this.noCicles = new NoCiclesRequisits(this);
		ExceptionManager.thrower(this.setNom(plaEst,nom));
		ExceptionManager.thrower(this.setPlaEstudis(plaEst));
		this.sessionsG = new HashSet<SessioGrup>();
		this.sessionsSG = new HashSet<SessioSubGrup>();
		this.grups = new HashSet<Grup>();


	}
		
	/////////////////////////////////////////////////////////////
	//////////////////////// Setters  //////////////////////////
		
	/**
	 * Assigna el nom de l'Assignatura.
	 * @param nom: nom de l'Assignatura que entra l'usuari.
	 * @throws Exception si nom no cambia o nom ja existeix en el Pla d'Estudis.
	 */
	public int setNom(PlaEstudis plaEst, String nom) throws Exception {
		if (nom == null) return 30;
		else if(this.nom != null && this.nom.equals(nom)) return 1;
		else if (plaEst == null) return 31;
		else if(plaEst.checkAssignatura(nom)) return 32;
		this.nom = nom;
		return 0;
	}
	
	/**
	 * Assigna el Pla d'Estudis que pertany l'assignatura.
	 * @param PlaEst: pla d'estudis.
	 * @throws .Excepció codificada en forma d'enter.
	 */
	public int setPlaEstudis(PlaEstudis plaEst) {
		if (plaEst == null) return 31;
		if (plaEst == this.plaEstudis) return 1;
		this.plaEstudis = plaEst;
		return 0;
	}
	
	/**
	 * Indica amb quina assignatura no es pot solapar l'assignatura actual.
	 * @param assig: assignatura que no es pot solapar.
	 * @return Excepció codificada en forma d'enter.
	 */	
	public int setSolapament(Assignatura assig) throws Exception {
		if (assig == null) return 40;
		solapament.addNoSolapar(this, assig);
		return 0;
	}
	
	/**
	 * Retorna cert si a es igual a l'assignatura actual.
	 * @param a: Assignatura que volem comprobar si es igual.
	 * @return Excepció codificada en forma d'enter.
	 */
	public int setCorrequisit(Assignatura assig) throws Exception {
		if (assig == null) return 40;
		corr.addNoSolapar(this, assig);
		return 0;
	}
	
	/**
	 * Assigna la restricció d'hores aptes per aquest grup.
	 * @param franja indica per cada dia quines hores poden o no ser assignades.
	 * En cas de que sigui null per defecte s'assignen les hores lectives del
	 * pla d'estudis corresponent.
	 * @param apte Indica si l'acció que es preten fer es permetre o denegar aquelles hores.
	 * @param force Permet forçar l'assignació de la franja encara que aquesta violi en 
	 * part les hores lectives del pla d'estudis.
	 * @return Excepció codificada en forma d'enter.
	 */
	public int setHoresAptes(Map<Integer, int[]> franja, boolean apte, boolean force) throws Exception {
		if(this.horesAptes == null) {
			HoresSenseClasseAssignatura hores = new HoresSenseClasseAssignatura(this);
			this.horesAptes = hores;
		}
		
		if(franja == null) return 0;
		else for(Map.Entry<Integer, int[]> iter: franja.entrySet()) {
			int checker = 0;
			if(apte) checker = this.horesAptes.permetHores(force, Integer.valueOf(iter.getKey()), iter.getValue());
			else checker = this.horesAptes.permetHores(force, Integer.valueOf(iter.getKey()), iter.getValue());
			if(checker != 0) return checker;
		}
		
		return 0;
	}
	
	/**
	 * Assigna un requisit a l'assignatura.
	 * @param assigs: Assignatura requisit.
	 * @return Excepció codificada en forma d'enter.
	 */
	public int setRequisit(Assignatura assigs) throws Exception {
		if (assigs == null) ExceptionManager.thrower(40);
		else {
			this.noCicles.afegeixRequisits(assigs);
			if (noCicles.isReachable(this,this)) {
			 	ExceptionManager.thrower(42); //TODO: numero excepcio
			}
		}
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
	 * Retorna la sessioG = Tipus + Hores de l'Assignatura.
	 * @return SessioG de l'Assignatura.
	 */
	public SessioGrup getSessioG(String tipus, int hores) throws Exception {
		for(SessioGrup s: sessionsG)
			if (s.getHores() == hores && s.getTipus().equals(tipus) && s.getAssignatura().getNom().equals(this.nom) && s.getAssignatura().getPlaEstudis().getNom().equals(this.getPlaEstudis().getNom())) return s;
		return null;
	}
	
	/**
	 * Retorna totes les sessionsG de l'assignatura.
	 * @return SessioG de l'Assignatura.
	 */
	public HashSet<SessioGrup> getSessionsG() throws Exception {
		return this.sessionsG;
	}
	
	/**
	 * Retorna totes les sessionsSG de l'assignatura.
	 * @return SessioSG de l'Assignatura.
	 */
	public HashSet<SessioSubGrup> getSessionsSG() throws Exception {
		return this.sessionsSG;
	}
	
	/**
	 * Retorna la sessioSG = Tipus + Hores de l'Assignatura.
	 * @return SessioSG de l'Assignatura.
	 */
	public SessioSubGrup getSessioSG(String tipus, int hores) throws Exception {
		if (tipus == null) ExceptionManager.thrower(38);
		for(SessioSubGrup s: sessionsSG)
			if (s.getHores() == hores && s.getTipus().equals(tipus) && s.getAssignatura().getNom().equals(this.nom) && s.getAssignatura().getPlaEstudis().getNom().equals(this.getPlaEstudis().getNom())) return s;
		return null;
	}
		
	/**
	 * Retorna el Grup = idGrup de l'Assignatura.
	 * @return Grup de l'Assignatura.
	 */
	public Grup getGrup(int idgrup) {
		for(Grup g: grups)
			if(g.getNumero() == idgrup && g.getAssignatura().getNom().equals(this.getNom())) return g;
		return null;
	}	
		
	/**
	 * Retorna el Pla d'Estudis de l'Assignatura.
	 * @return Pla d'Estudis de l'Assignatura.
	 */
	public PlaEstudis getPlaEstudis() {
		return this.plaEstudis;
	}
	
	/**
	 * Retorna l'instancia NoSolaparAssignatura de l'Assignatura.
	 * @return NoSolaparAssignatura.
	 */
	public NoSolaparAssignatura getSolapaments() {
		return this.solapament;
	}
	
	/**
	 * Retorna l'instancia NoSolaparAssignatura de l'Assignatura.
	 * @return NoSolaparAssignatura.
	 */
	public Correquisit getCorrequisit() {
		return this.corr;
	}
	
	/**
	 * Retorna l'instancia HoresSenseClasseAssignatura de l'Assignatura.
	 * @return HoresSenseClasseAssignatura.
	 */
	public HoresSenseClasseAssignatura getHoresAptes() {
		return this.horesAptes;
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
		if (franja == null) return 35;
		if (this.checkGrup(numero)) return 36;
		if (capacitat < 0) return 37;
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
		else return 36;
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
		if (tipus == null) return 38;
		if (this.checkSessioG(tipus,hores)) return 39;
		else if (hores < 0) return 36;
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
		if (tipus == null) return 38;
		if (this.checkSessioSG(tipus,hores)) return 41;
		else if (hores < 0) return 36;
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
		if (tipus == null) return 38;
		if (checkSessioG(tipus,hores)) sessionsG.removeIf(item -> item.getHores() == hores && item.getTipus().equals(tipus));
		else return 39;
		return 0;
	}
	
	/**
	 * Dona de baixa la  Sessio = Hores + Tipus sempre i quant existeixi en aquesta Assignatura.
	 * @param hores: hores de la sessio que volem donar de baixa.
	 * @param tipus: tipus de la sessio que volem donar de baixa.
	 * @return Excepció codificada en forma d'enter.
	 */
	public int baixaSessioSG(String tipus, int hores) throws Exception {
		if (tipus == null) return 38;
		if (checkSessioSG(tipus,hores)) sessionsSG.removeIf(item -> item.getHores() == hores && item.getTipus().equals(tipus));
		else return 41;
		return 0;
	}
	
	/**
	 * Elimina un requisit a l'assignatura.
	 * @param assigs: Requisit a eliminar.
	 * @return Excepció codificada en forma d'enter.
	 */
	public int delRequisit(Assignatura assigs) throws Exception {  //TODO:
		if (assigs == null) ExceptionManager.thrower(40);
		if(!this.noCicles.treuRequisit(assigs)) ExceptionManager.thrower(43);
		return 0;
	}
	/**
	 * Elimina un correquisit de l'assignatura actual.
	 * @param a: Correquisit que volem eliminar.
	 * @return Excepció codificada en forma d'enter.
	 */	
	public int delCorrequisit(Assignatura assig) throws Exception {
		if (assig == null) return 40;
		corr.delNoSolapar(this, assig);
		return 0;
	}
	
	/**
	 * Elimina una instància de no-solapament de l'assignatura actual.
	 * @param assig: assignatura que vull eliminar de no-solapar.
	 * @return Excepció codificada en forma d'enter.
	 */	
	public int delSolapament(Assignatura assig) throws Exception {
		if (assig == null) return 40;
		corr.delNoSolapar(this, assig);
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
			if (g.getNumero() == numero && g.getAssignatura().getNom().equals(this.nom)) return true;
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
		if (tipus == null) ExceptionManager.thrower(38);
		for(Sessio s : sessionsG) {
			if (s.getHores() == hores && s.getTipus().equals(tipus) && s.getAssignatura().getNom().equals(this.nom) && s.getAssignatura().getPlaEstudis().getNom().equals(this.getPlaEstudis().getNom())) return true;
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
		if (tipus == null) ExceptionManager.thrower(38);		
		for(Sessio s : sessionsSG) {
			if (s.getHores() == hores && s.getTipus().equals(tipus) && s.getAssignatura().getNom().equals(this.nom) && s.getAssignatura().getPlaEstudis().getNom().equals(this.getPlaEstudis().getNom())) return true;
		}
		return false;
	}
		//TODO
	/*public int delHoresAptes(Map<Integer, int[]> franja, boolean force) throws Exception {
		if(this.horesAptes == null) {
			HoresSenseClasseAssignatura hores = new HoresSenseClasseAssignatura(this);
			this.horesAptes = hores;
		}
		
		if(franja == null) return 0;
		else for(Map.Entry<Integer, int[]> iter: franja.entrySet()) {
			int checker = 0;
			if(apte) checker = this.horesAptes.permetHores(force, Integer.valueOf(iter.getKey()), iter.getValue());
			else checker = this.horesAptes.permetHores(force, Integer.valueOf(iter.getKey()), iter.getValue());
			if(checker != 0) return checker;
		}
		
		return 0;
	}*/
	
	/**
	 * Retorna cert si a es igual a l'assignatura actual.
	 * @param a: Assignatura que volem comprobar si es igual.
	 * @return Excepció codificada en forma d'enter.
	 */
	public boolean esIgual(Assignatura a) throws Exception {
		if (a == null) ExceptionManager.thrower(40);
		return a.getNom().equals(this.nom ) && a.getPlaEstudis().getNom().equals(this.plaEstudis.getNom());
	}
	
	/**
	 * Retorna el numero de SessionsG donades d'alta
	 * @return assignatures.size()
	 */
	public int quantesSessionsG() {
		return this.sessionsG.size();
	}
	
	/**
	 * Retorna el numero de sessionsSG donades d'alta
	 * @return assignatures.size()
	 */
	public int quantesSessionsSG() {
		return this.sessionsSG.size();
	}
	 
}
