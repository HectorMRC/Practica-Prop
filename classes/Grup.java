package classes;

import java.util.*;
import restriccions.*;
import utils.*;

/**
 * 
 * @author hector.morales.carnice@est.fib.upc.edu
 *
 */
public class Grup {
	/**
	 * Identifica al Grup d'una assignatura concreta. 
	 */
	private int numero;
	/**
	 * Numera les places obertes que té el Grup.
	 */
	private int places;
	/**
	 * Franja horaria:
	 * Només pot pendre els valors {M,T,MT,NAN}, els quals signifiquen:
	 * matí, tarda, ambdós o cap dels dos, respectivament.
	 */
	private String franja;
	
	/**
	 * Identifica l'assignatura a la qual pertany el Grup.
	 */
	private Assignatura assig;
	/**
	 * Registra tots els subGrups que pertanyen al Grup.
	 */
	private HashSet<SubGrup> subGrups;
	/**
	 * Registra totes les sessions a les quals pertany el Grup.
	 */
	private HashSet<SessioGAssignada> sessions;
	
	/**
	 * Linca el grup amb la seva restricció d'hores aptes.
	 */
	private HoresAptesGrupSubGrup horesAptes;
	/**
	 * Linca el grup amb la seva restricció de grups amb
	 * els quals no es pot solapar.
	 */
	private SolapamentsGrupSubGrup disjunts;
	
	////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////  PRIVADES  /////////////////////////////////////
	/**
	 * Retorna la suma total de places assignades als subGrups d'un grup.
	 * @return Un enter suerior o igual a 0.
	 */
	private int calculaCapacitatTotal() {
		int calcPlaces = 0;
		for(SubGrup subg: subGrups) calcPlaces += subg.getPlaces();
		
		return calcPlaces;
	}
	
	/**
	 * Comprova la integritat de la sessió respecte al Grup.
	 * @param sessio Referencia la sessio a comprovar.
	 * @retrun Excepció codificada en forma d'enter.
	 */
	private int checkSessioAdequada(SessioGAssignada sessio) {
		if(sessio.getGrup().getNumero() != this.numero) return 68;
		else if(!sessio.getGrup().getAssignatura().getNom().equals(this.getAssignatura().getNom())) return 69;
		
		return 0;
	}
	
	/**
	 * Desassigna una sessió del Grup i l'esborra del set, si hi és.
	 * Altrament no fa res.
	 * @param tipus Identifica el tipus de la sessio
	 * @param hores Identifica el temps de durada de la sessió en hores
	 * @param unlink Precisa la necessitat de trencar la navegavilitat
	 * de la sessioGrup cap a la sessioAssignada, o no.
	 * @retrun Excepció codificada en forma d'enter.
	 */
	private int desassignaSessio(String tipus, int hores, boolean unlink){
		SessioGAssignada sessio = this.getSessio(tipus, hores);
		
		//Eliminació de la sessio a la classe Grup
		sessions.removeIf(item -> item.getSessioGrup().getTipus().equals(tipus) &&
								  item.getSessioGrup().getHores() == hores);
		
		//Elimina la sessio a la classe SessioGrup
		if(unlink) return sessio.getSessioGrup().eliminarSessio(sessio);
		else return 0;
	}
	
	/**
	 * Actualitza l'estat de les horesAptes segons la franja horaria
	 * del grup que es preten assignar.
	 * @param franja Indica si el grup ha de ser de matí, tarda, o qualsevol d'ambdós.
	 * @return Excepció codificada en forma d'enter.
	 */
	private int actualitzaHoresAptes(String franja) {		
		return 0;
	}
	
	/**
	 * Inicialitza adequadament les restriccions del Grup.
	 * @throws Excepciño rebuda durant la donada d'alta de les restriccions.
	 */
	private void iniRestriccions() throws Exception {
		HoresAptesGrupSubGrup horesApt = new HoresAptesGrupSubGrup(this, null);
		SolapamentsGrupSubGrup disjunts = new SolapamentsGrupSubGrup(this, null);
		
		this.horesAptes = horesApt;
		this.disjunts = disjunts;
	}
	
	////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////  PÚBLIQUES  /////////////////////////////////////
	/** 
	 * Constructora de la classe Grup.
	 * Predeterminadament els parametres de la classe: numero, places, franja;
	 * prenen els valors -1, -1, "NAN" respectivament. Mentre que el conjunt de
	 * subGrups inicialment és buit.
	 * @throws Exception
	 */
	public Grup(Assignatura assig, int numero) throws Exception {
		ExceptionManager.thrower(this.setNumero(numero));
		ExceptionManager.thrower(this.setAssignatura(assig));
		this.iniRestriccions();
		
		places = 0;
		franja = new String("MT");
		
		subGrups = new HashSet<>();
	}
	
	/** 
	 * Constructora de la classe Grup.
	 * @param numero Indentifica el grup. 
	 * @param places Descriu la capacitat del grup.
	 * @param franja Descriu la franja horaria del grup.
	 * @throws Exception
	 */
	public Grup(Assignatura assig, int numero, int places, String franja) throws Exception {
		ExceptionManager.thrower(this.setNumero(numero));
		ExceptionManager.thrower(this.setPlaces(places));
		ExceptionManager.thrower(this.setFranja(franja));
		/* Arribats aquí, tots els parametres entrats son adequats,
		 * per tant, es pot procedir a linkar la classe:*/
		ExceptionManager.thrower(this.setAssignatura(assig));
		this.iniRestriccions();
		
		subGrups = new HashSet<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////  SETTERS  /////////////////////////////////////
	/** 
	 * Assigna un numero identificatiu al grup.
	 * @param numero Identifica el grup. 
	 * @retrun Excepció codificada en forma d'enter.
	 */
	public int setNumero(int numero) {
		if(numero <= 0) return 50;
		else if(this.numero == numero) return 1; //En cas de fer un canvi inutil.
		else if(this.getAssignatura() != null && assig.checkGrup(numero)) return 51;

		this.numero = numero;
		return 0;
	}
	
	/** 
	 * Assigna una quantitat de places al grup.
	 * @param places Indica el nombre de places assignades al grup. 
	 * @retrun Excepció codificada en forma d'enter.
	 */
	public int setPlaces(int places){
		if(places <= 0) return 52;
		else if(this.getPlaces() == places) return 1; //En cas d'un canvi inutil.
		else if(places < this.getPlacesAssignades()) return 53;
		
		this.places = places;
		return 0;
	}
	
	/** 
	 * Assigna una franja horaria al grup.
	 * @param franja Descriu la nova franja horaria del grup.
	 * @retrun Excepció codificada en forma d'enter.
	 */
	public int setFranja(String franja) {
		if(franja == null) return 54;
		else if(!"MT".contains(franja.toUpperCase())) return 54;
		
		this.actualitzaHoresAptes(franja.toUpperCase());
		this.franja = franja.toUpperCase();
		return 0;
	}
	
	/**
	 * Assigna l'assignatura a la qual pertany el Grup.
	 * @param assig Referencia l'assignatura a la qual pertany el grup.
	 * @return Excepció codificada en forma d'enter.
	 */
	public int setAssignatura(Assignatura assig) {
		if(assig == null) return 55;
		else if(this.getAssignatura() != null) { //En cas d'una modificació:
			if(this.assig.getNom().equals(assig.getNom())) return 1;
			else if(assig.checkGrup(this.getNumero())) return 51;
		}
		
		this.assig = assig;
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
	 * @throws Exception rebuda al donar d'alta la restricció.
	 */
	public int setHoresAptes(Map<Integer, int[]> franja, boolean apte, boolean force) {
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
	 * Restringeix la possibilitat de que aquest grup es solapi amb el grup
	 * i/o subGrups passats per parametre; sempre i quan aquests compleixin
	 * les restriccions d'integritat globals.
	 * @param grup Referencia el grup a restringir.
	 * @param subGrup Referencia el subGrup a restringir.
	 * @return Excepció codificada en forma d'enter.
	 */
	public int setSolapament(Grup grup, SubGrup subGrup, boolean permet) {
		if(grup == null || subGrup == null) return 250; // no poden ser els dos nulls.
		else if(grup != null && this.equals(grup)) return 251; //Un grup no pot ser disjunt amb si mateix.
		
		return this.disjunts.setSolapament(grup, subGrup, permet);
	}
	
	////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////  GETTERS  /////////////////////////////////////
	/** 
	 * Retorna el número queidentifica al grup.
	 * @return Un enter qualsevol.
	 */
	public int getNumero() {
		return numero;
	}
	
	/** 
	 * Retorna la quantitat de places que te assignades el grup.
	 * @return Un enter superior o igual a 0.
	 */
	public int getPlaces() {
		return this.places;
	}
	
	/** 
	 * Retorna el nombre de places total sumada entre tots els subGrups del grup.
	 * @return Un enter superior o igual a 0.
	 */
	public int getPlacesAssignades() {
		return calculaCapacitatTotal();
	}
	
	/** 
	 * Retorna un String amb valor "M", "T", "MT" o "NAN" segons si la franja horaria
	 * assignada al grup és de matí, tarda, ambdos, o cap dels dos respectivament.
	 * @return Un String que identifica la franja horaria.
	 */
	public String getFranja() {
		return franja;
	}
	
	/** 
	 * Retorna tot el conjunt de SubGrups creats al Grup.
	 * @return Un HashSet de SubGrups amb tots els subgrups del Grup.
	 */
	public HashSet<SubGrup> getAllSubGrups() {
		return subGrups;
	}
	
	/** 
	 * Retorna, si existeix en el Grup, el SubGrup identificat per número;
	 * altrament retorna null. 
	 * @param numero Descriu el subGrup que es preten obtenir.
	 * @return Un SubGrup amb l'identificació numero pertanyent el Grup.
	 */
	public SubGrup getSubGrup(int numero) {
		for(SubGrup subg: subGrups)
			if(subg.getNumero() == numero) return subg;
		
		return null;
	}
	
	/**
	 * Retorna l'assignatura a la qual pertany el grup.
	 * @return La classe assignatura a la qual pertany el grup.
	 */
	public Assignatura getAssignatura() {
		return assig;
	}
	
	/**
	 * Retorna totes les sessions a les quals està assignat el Grup.
	 * @return Un HashSet de SessioAssignada buit, o amb múltiples sessions.
	 */
	public HashSet<SessioGAssignada> getSessions() {
		return sessions;
	}
	
	/**
	 * Retorna, si hi és, la referencia a la sessió del Grup amb
	 * el tipus i hores corresponents; altrament retorna null.
	 * @param tipus Identifica el tipus de la sessió.
	 * @param hores Identifica la durada en hores de la sessió.
	 * @return Una classe de tipus SessioGAssignada o un null.
	 */
	public SessioGAssignada getSessio(String tipus, int hores) {
		for(SessioGAssignada sessio: sessions)
			if(sessio.getSessioGrup().getTipus().equals(tipus) && sessio.getSessioGrup().getHores() == hores)
				return sessio;
		
		return null;
	}
	
	/**
	 * Retorna la restriccio d'hores aptes.
	 * @return Restriccions.
	 */
	public HoresAptesGrupSubGrup getRestriccioHoresAptes() {
		return this.horesAptes;
	}
	
	/**
	 * Retorna la restricció de no solpament.
	 * @return Restricció.
	 */
	public SolapamentsGrupSubGrup getSolapaments() {
		return this.disjunts;
	}
	
	////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////  MODIFICADORES  /////////////////////////////////
	/**
	 * Afegeix la quantitat entrada per parametre de places al Grup si, 
	 * i només si, nplaces > 0; altrament llança una Excepció.
	 * @param incr Quantitat de places a afegir.
	 * @return Excepció codificada en forma d'enter.
	 */
	public int obrirPlaces(int nplaces) {
		if(nplaces < 0) return 71;
		this.places += nplaces;
		return 0;
	}
	
	/**
	 * Tanca la quantita entrada per paràmetre de places al Grup si,
	 * i només si, nplaces > 0 i no incongrueix amb les places obertes 
	 * als seus subGrups; altrament llança una excepció.
	 * @param nplaces Quantitat de places a tancar.
	 * @return Excepció codificada en forma d'enter.
	 */
	public int tancarPlaces(int nplaces) {
		if(nplaces < 0) return 56;
		else if(this.places - nplaces < 0) return 57;
		
		this.places -= nplaces;
		return 0;
	}
	
	/** 
	 * Crea un nou subGrup dins el grup corresponent sempre i quan els atributs entrats
	 * compleixin amb les especificacions. Altrament llança una excepció.
	 * @param numero Indica el numero que identificarà al subGrup.
	 * @param places Indica quantes places ha de tenir el subGrup.
	 * @param incr 	 Permet que, enlloc de fer saltar una excepció quan un subgrup subruix al total de places del grup,
	 * la diferencia de places necessaries per obrir el subgrup s'incremente al total de places del grup.
	 * @return Excepció codificada en forma d'enter.
	 * @throws Excepció rebuda durant la creació del subgrup.
	 */
	public int altaSubGrup(int numero, int places, boolean incr) throws Exception{
		if(this.checkSubGrup(numero)) return 58;
		else if(places > this.places) return 59;
		else if(places < 0) return 60;
		else if((this.places - calculaCapacitatTotal()) < places){
			if(incr) this.places += places - (this.places - calculaCapacitatTotal());
			else return 61;
		}
		
		subGrups.add(new SubGrup(this, numero, places));
		return 0;
	}
	
	/**
	 * Elimina el subgrup amb el numero definit, si existeix; altrament no fa res.
	 * @param numero Descriu el grup que es preten eliminar.
	 * @return Excepció codificada en forma d'enter.
	 */
	public int baixaSubGrup(int numero) {
		subGrups.removeIf(item -> item.getNumero() == numero);
		return 0;
	}
	
	/**
	 * Assigna un nova sessió al Grup i l'enregistra.
	 * @param tipus Identifica el tipus de la sessio de grup.
	 * @param hores Identifica el temps de durada de la sessió en hores
	 * @return Excepció codificada en forma d'enter.
	 * @throws Exceptions llençada durant la donada d'alta d'una SessioAssignada.
	 */
	public int assignaSessio(String tipus, int hores) throws Exception {
		if(this.checkSessio(tipus, hores)) return 62;
		else if(!assig.checkSessioG(tipus, hores)) return 63;
		
		SessioGrup sessioGrup = assig.getSessioG(tipus, hores);
		SessioGAssignada sessio = new SessioGAssignada(this, sessioGrup);
		
		//Enllaç amb la classe SessioGrup
		int checker;
		if((checker = sessioGrup.afegirSessio(sessio)) != 0) return checker;
		else sessions.add(sessio); //Enllaç amb la classe Grup:
		return 0;
	}
	
	/**
	 * Desassigna aquella sessió del Grup igual a la sessió afegida per
	 * paràmetre i l'esborra del set, si hi és.
	 * Altrament no fa res.
	 * @param sessio Referencia a la sessió que és preten esborrar.
	 * @return Excepció codificada en forma d'enter.
	 */
	public int desassignaSessio(SessioGAssignada sessio) {
		int checker;
		if((checker = this.checkSessioAdequada(sessio)) != 0) return checker;
		else return this.desassignaSessio(sessio.getSessioGrup().getTipus(), sessio.getSessioGrup().getHores(), true);
	}
	
	/**
	 * Afegeix una nova sessió al Grup si, i només si, compleix les restriccions
	 * d'integritat del grup.
	 * @param sessio Referencia a la SessioGAssignada que es preten afegir.
	 * @return Excepció codificada en forma d'enter.
	 */
	public int afegeixSessio(SessioGAssignada sessio){
		if(sessions.contains(sessio)) return 64;
		else if(sessio.getGrup().getNumero() != this.numero) return 65;
		else if(!sessio.getSessioGrup().getAssignatura().getNom().equals(this.getAssignatura().getNom())) return 65;
		else if(this.checkSessio(sessio.getSessioGrup().getTipus(), sessio.getSessioGrup().getHores())) return 66;
		
		sessions.add(sessio);
		return 0;
	}
	
	/**
	 * Elimina del set la sessio passada per parametre, si hi és.
	 * Altrament no fa res.
	 * @param sessio Referencia a la sessio que es preten esborrar.
	 * @throws Exception
	 */
	public int eliminaSessio(SessioGAssignada sessio){
		int checker;
		if((checker = this.checkSessioAdequada(sessio)) != 0) return checker;
		else return this.desassignaSessio(sessio.getSessioGrup().getTipus(), sessio.getSessioGrup().getHores(), false);
	}
	
	/**
	 * Restaura les hores aptes per defecte.
	 * @return Excepció codificada en forma d'enter.
	 */
	public int resetHoresAptes() {
		if(this.horesAptes == null) return 0;
		else return this.horesAptes.restore();
	}
	
	////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////  CONSULTORES  ///////////////////////////////////
	/** 
	 * Retorna quants subgrups hi ha en un grup.
	 * @return nombre enter superior o igual a 0.
	 */
	public int quantsSubGrups() {
		return subGrups.size();
	}
	
	/** 
	 * Retorna true si, i només si, existeix un subgrup identificat pel numero
	 * passat per parametre.
	 * @param numero Descriu el subGrup que es pretén cercar.
	 * @return Descriu l'existencia d'un subGrup dins al Grup.
	 */
	public boolean checkSubGrup(int numero) {
		for(SubGrup subg: subGrups)
			if(subg.getNumero() == numero) return true;
		
		return false;
	}

	/**
	 * Retorna a quantes sessions està assignat el Grup.
	 * @return Un enter superior o igual a 0.
	 */
	public int quantesSessions() {
		return sessions.size();
	}
	
	/**
	 * Retorna true si, i només si, el Grup està assignat a una sessió de l'assginatura
	 * identificada per nomAssign i del tipus indicat. Altrament retorna false.
	 * @param tipus Identifica el tipus de la sessió
	 * @param hores Identifica el temps de durada de la sessió en hores
	 * @return Descriu l'existencia d'una sessió del grup per una assignatura i
	 * tipus de sessió concreta
	 */
	public boolean checkSessio(String tipus, int hores) {
		return this.getSessio(tipus, hores) != null;
	}

	/**
	 * Retorna true si, i només si, el Grup està assignat a una sessió del
	 * tipus indicat (Sense tenir en compte la duració)
	 * @param tipus Identifica el tipus de la sessió
	 * @return Un boolea que descriu l'existencia d'una sessió del tipus
	 * corresponent.
	 */
	public boolean checkSessio(String tipus) {
		for(SessioGAssignada sessio: sessions)
			if(sessio.getSessioGrup().getTipus().equals(tipus)) return true;
		
		return false;
	}

	/**
	 * Retorna true si, i només si, el Grup entrat es identic al grup en
	 * qüestió.
	 * @param subGrup Referencia al Grup a comparar.
	 * @return Un booleà que representa la similitud entre ambdós.
	 */
	public boolean equals(Grup grup) { 
		return this.getNumero() == grup.getNumero() &&
				this.getAssignatura().getNom().equals(grup.getAssignatura().getNom()) &&
				this.getAssignatura().getPlaEstudis().getNom().equals(grup.getAssignatura().getPlaEstudis().getNom());
	}
}
