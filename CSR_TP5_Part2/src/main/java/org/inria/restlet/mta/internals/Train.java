package org.inria.restlet.mta.internals;

/**
 * 
 * @author Vivien Busson - Lucas Lelièvre | CSR TP5 | M1 MIAGE - 2017.
 *
 */

public class Train extends Thread {

	// Déclarations des attributs.
	private int ARRET_TRAIN;
	private int CAPACITE_TRAIN;
	private int VITESSE_TRAIN;
	private int nbPlacesLibre;
	private int idTrain;
	private EspaceQuai eq;
	private String etatTrain = "En route vers la gare";

	/**
	 * Constructeur de Train.
	 * 
	 * @param i
	 *            = Id du train.
	 * @param capacite
	 *            = Capacité du train générée aléatoirement.
	 * @param eq
	 *            = espaceQuai.
	 */
	public Train(int i, int capacite, EspaceQuai eq) {
		this.eq = eq;
		this.idTrain = i;
		this.CAPACITE_TRAIN = capacite;
		this.nbPlacesLibre = capacite;
		this.VITESSE_TRAIN = (int) (Math.random() * (300 - 50) + 1);
		this.ARRET_TRAIN = (int) (Math.random() * (5000 - 500) + 1);
	}

	/**
	 * Délai d'entrée en gare, puis traitement.
	 */
	public synchronized void run() {
		try {
			sleep(10000 / VITESSE_TRAIN);
			traitementTrains();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cherche une voie libre.
	 * Prends une voie, puis attends le temps de faire monter les passagers.
	 * Quitte la voie.
	 * @throws InterruptedException
	 */
	public void traitementTrains() throws InterruptedException {
		this.etatTrain = "En attente d'une voie libre";
		
		// trouver voie libre.
		eq.trouverVoieLibre();
		// Prendre Voie.
		eq.prendreVoie(this);
		
		this.etatTrain = "Garé";
		try {
			sleep(this.ARRET_TRAIN);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Quitter Voie.
		eq.quitterVoie(this);
		this.etatTrain = "Reparti";
	}

	/**
	 * Si il reste une place libre, retourne vrai, sinon retourne faux.
	 * @return
	 */
	public boolean monter() {
		if (nbPlacesLibre != 0) {
			nbPlacesLibre--;
			return true;
		} else {
			return false;
		}
	}

	// ------ GETTERS - SETTERS ------

	public int getARRET_TRAIN() {
		return ARRET_TRAIN;
	}

	public void setARRET_TRAIN(int aRRET_TRAIN) {
		ARRET_TRAIN = aRRET_TRAIN;
	}

	public int getCAPACITE_TRAIN() {
		return CAPACITE_TRAIN;
	}

	public void setCAPACITE_TRAIN(int cAPACITE_TRAIN) {
		CAPACITE_TRAIN = cAPACITE_TRAIN;
	}

	public int getVITESSE_TRAIN() {
		return VITESSE_TRAIN;
	}

	public void setVITESSE_TRAIN(int vITESSE_TRAIN) {
		VITESSE_TRAIN = vITESSE_TRAIN;
	}

	public int getNbPlacesLibre() {
		return nbPlacesLibre;
	}

	public void setNbPlacesLibre(int nbPlacesLibre) {
		this.nbPlacesLibre = nbPlacesLibre;
	}

	public int getIdTrain() {
		return idTrain;
	}

	public void setIdTrain(int idTrain) {
		this.idTrain = idTrain;
	}

	public EspaceQuai getEq() {
		return eq;
	}

	public void setEq(EspaceQuai eq) {
		this.eq = eq;
	}

	public String getEtatTrain() {
		return etatTrain;
	}

	public void setEtatTrain(String etatTrain) {
		this.etatTrain = etatTrain;
	}
	
	

}