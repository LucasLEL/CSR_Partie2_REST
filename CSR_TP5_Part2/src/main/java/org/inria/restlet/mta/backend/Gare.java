package org.inria.restlet.mta.backend;

import java.util.ArrayList;
import java.util.List;

import org.inria.restlet.mta.internals.*;


public class Gare {

	// Déclarations des attributs.
	private int NB_VOIES = 2;
	private int NB_BILLETS_MAX = 700;
	private int NB_TRAINS = 10;
	private int NB_VOYAGEURS = 200;
	private ArrayList<Train> listTrains;
	private ArrayList<Voyageur> listVoyageurs;
	private EspaceQuai eq = new EspaceQuai(NB_VOIES);
	private EspaceVente ev = new EspaceVente(NB_BILLETS_MAX);

	/**
	 * Contructeur de la Gare.
	 * 
	 * @throws InterruptedException
	 */
	public Gare() throws InterruptedException {
		
		
		// TRAINS
		listTrains = new ArrayList<Train>();
		// Créations des trains.
		for (int i = 0; i < NB_TRAINS; i++) {
			int capacite = (int) (Math.random() * (100 - 50));
			Train train = new Train(i, capacite, eq);
			System.out.println("Le train n° " + train.getIdTrain() + " a une capacité de : " + capacite);
			listTrains.add(train);
		}
		for (int i = 0; i < NB_TRAINS; i++) {
			this.getListTrains().get(i).start();
		}
		
		// VOYAGEURS
		listVoyageurs = new ArrayList<Voyageur>();
		for (int i = 0; i < NB_VOYAGEURS; i++) {
			Voyageur voyageur = new Voyageur(i, eq, ev);
			listVoyageurs.add(voyageur);
		}
		for (int i = 0; i < NB_VOYAGEURS; i++) {
			this.getListVoyageurs().get(i).start();
		}
		for (int i = 0; i < NB_VOYAGEURS; i++) {
			this.getListVoyageurs().get(i).join();
		}


		
	}

	public static void main(String[] args) throws InterruptedException {
		new Gare();
	}

	/**
	 * Ajouter un train depuis la méthode "post" dans restlet
	 * @param id : identifiant du train
	 * @param capacite : capacite du train
	 * @return le train créé
	 */
	public Train ajoutTrain(int id, int capacite) {
		Train train = new Train(id, capacite, this.eq);
		listTrains.add(train);
		train.start();

		return train;
	}

	/**
	 * Ajouter un voyageur depuis la méthode "post" dans restlet
	 * @param id : identifiant du voyageur
	 * @return le voyageur créé
	 */
	public Voyageur ajoutVoyageur(int id) {
		Voyageur voyageur = new Voyageur(id, this.eq, this.ev);
		listVoyageurs.add(voyageur);
		voyageur.start();

		return voyageur;
	}

	// ------ GETTERS - SETTERS ------

	public synchronized ArrayList<Train> getListTrains() {
		return listTrains;
	}

	public void setListTrains(ArrayList<Train> listTrains) {
		this.listTrains = listTrains;
	}

	public synchronized ArrayList<Voyageur> getListVoyageurs() {
		return listVoyageurs;
	}

	public void setListVoyageurs(ArrayList<Voyageur> listVoyageurs) {
		this.listVoyageurs = listVoyageurs;
	}

	public EspaceQuai getEq() {
		return eq;
	}

	public void setEq(EspaceQuai eq) {
		this.eq = eq;
	}

	public EspaceVente getEv() {
		return ev;
	}

	public void setEv(EspaceVente ev) {
		this.ev = ev;
	}

}
