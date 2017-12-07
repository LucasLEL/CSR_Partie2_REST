package org.inria.restlet.mta.internals;

/**
 * 
 * @author Vivien Busson - Lucas Lelièvre | CSR TP5 | M1 MIAGE - 2017.
 *
 */

public class Voyageur extends Thread {
	
	//Déclarations des attributs.
	private int idVoyageur;
	private EspaceVente ev;
	private EspaceQuai eq;
	private boolean voyageurParti;
	private String etatVoyageur = "En route vers la gare";
	
	/**
	 * Contructeur de Voyageur
	 * @param id = Id voyageur.
	 * @param eq = EspaceQuai.
	 * @param ev = EspaceVente.
	 */
	public Voyageur(int id, EspaceQuai eq, EspaceVente ev){
		this.idVoyageur = id;
		this.eq = eq;
		this.ev = ev;
		this.voyageurParti = false;
	}
	
	/**
	 * Vérifie que le billet à bien été acheté, si oui fait monter le voyageur dans un train.
	 */
	public void run(){
		try {
			if (acheterBillet()) {
				
				monterTrain();
				this.etatVoyageur = "Monté dans un train";
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Si un billet est disponible, l'impression du ticket se lance.
	 * @return vrai si le voyageur a acheté son billet, faux sinon.
	 * @throws InterruptedException
	 */
	public boolean acheterBillet() throws InterruptedException {
		if(ev.billetsDispo()) {
			ev.impressionBillet();
			System.out.println("Le voyageur "+ this.idVoyageur + " a acheté son billet");
			this.etatVoyageur = "Muni d'un ticket";
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Vérifie qu'un train est disponible et fait monter le voyageur.
	 * @throws InterruptedException
	 */
	public void monterTrain() throws InterruptedException {
		while(!eq.trainDisponible()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!this.voyageurParti) {
			eq.monterDansTrain(this);
		}
	}

	//------ GETTERS - SETTERS ------
	
	public int getIdVoyageur() {
		return idVoyageur;
	}

	public void setIdVoyageur(int idVoyageur) {
		this.idVoyageur = idVoyageur;
	}

	public EspaceVente getEv() {
		return ev;
	}

	public void setEv(EspaceVente ev) {
		this.ev = ev;
	}

	public EspaceQuai getEq() {
		return eq;
	}

	public void setEq(EspaceQuai eq) {
		this.eq = eq;
	}

	public boolean isVoyageurParti() {
		return voyageurParti;
	}

	public void setVoyageurParti(boolean voyageurParti) {
		this.voyageurParti = voyageurParti;
	}

	public String getEtatVoyageur() {
		return etatVoyageur;
	}

	public void setEtatVoyageur(String etatVoyageur) {
		this.etatVoyageur = etatVoyageur;
	}
	
	
}
