package org.inria.restlet.mta.internals;

import java.util.ArrayList;

/**
 * 
 * @author Vivien Busson - Lucas Lelièvre | CSR TP5 | M1 MIAGE - 2017.
 *
 */

public class EspaceQuai {

	// Déclarations des attributs.
	private ArrayList<Train> listTrain;
	private int nbVoies;

	/**
	 * Constructeur de EspaceQuai.
	 * 
	 * @param voies
	 */
	public EspaceQuai(int voies) {
		this.nbVoies = voies;
		listTrain = new ArrayList<Train>(voies);
	}

	/**
	 * Tant qu'il n'y a pas de voies, on attends (on endort le processus). 
	 */
	public synchronized void trouverVoieLibre() {
		
		while (nbVoies == 0) {
			try {
				System.out.println("###### Aucunes voies de dispo ######");
				
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Ajoute le train passé en paramètre de la liste des trains à quai.
	 * Décrémentation du nbVoies et notifie qu'un train est disponible
	 * 
	 * @param train
	 */
	public synchronized void prendreVoie(Train train) {
		listTrain.add(train);
		nbVoies--;
		System.out.println("++++++ Le train n° " + train.getIdTrain() + " entre en voie. ++++++");
		System.out.println("Il reste " + this.nbVoies + " voies de disponible.");
		notifyAll();
	}

	/**
	 * Supprime le train passé en paramètre de la liste des trains à quai.
	 * Incrémentation du nbVoies et notifie qu'une voie est disponible.
	 * 
	 * @param train
	 */
	public synchronized void quitterVoie(Train train) {
		listTrain.remove(train);
		nbVoies++;
		System.out.println("------ Le train n° " + train.getIdTrain() + " quitte une voie. ------");
		System.out.println("Il reste " + this.nbVoies + " voies de disponible.");
		notifyAll();
	}

	/**
	 * 
	 * @return faux si la liste des trains à quais est vide. Vrai sinon.
	 */
	public boolean trainDisponible() {
		return !listTrain.isEmpty();
	}

	/**
	 * Vérifie si il y à un train disponible à quai puis parcours les trains
	 * disponble pour trouver une place libre.
	 * Si pas de trains dispo, on attends, si pas de places dispo, on attends.
	 * @param voyageur
	 */
	public synchronized void monterDansTrain(Voyageur voyageur) {
		boolean trainDispo = false;
		while (!trainDispo) {
			// Si il y à ou plusieurs trains de dispo.
			if (!listTrain.isEmpty()) {
				for (Train train : listTrain) {
					if (train.monter()) {
						System.out.println("Le voyageur n° " + voyageur.getIdVoyageur() + " prends le train n° "
								+ train.getIdTrain());
						voyageur.setVoyageurParti(true);
						return;
					}
				}
				if (!trainDispo) {
					try {
						System.out.println(
								"Le voyageur n° " + voyageur.getIdVoyageur() + " attends qu'un train soit disponible.");
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// ------ GETTERS - SETTERS ------

	public ArrayList<Train> getListTrain() {
		return listTrain;
	}

	public void setListTrain(ArrayList<Train> listTrain) {
		this.listTrain = listTrain;
	}

	public int getNbVoies() {
		return nbVoies;
	}

	public void setNbVoies(int nbVoies) {
		this.nbVoies = nbVoies;
	}

}
