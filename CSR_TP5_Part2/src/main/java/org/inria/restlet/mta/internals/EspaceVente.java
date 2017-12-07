package org.inria.restlet.mta.internals;

/**
 * 
 * @author Vivien Busson - Lucas Lelièvre | CSR TP5 | M1 MIAGE - 2017.
 *
 */

public class EspaceVente {

	// Déclarations des attributs.
	private int nbBillets;

	/**
	 * Constructeur de Espace Vente.
	 * 
	 * @param nbBilletsMax
	 *            Nombre de billets maximum.
	 */
	public EspaceVente(int nbBilletsMax) {
		this.nbBillets = nbBilletsMax;
	}

	/**
	 * Action d'acheter un billet. Délai d'impression et décrémentation du nombre de
	 * billets disponible.
	 * 
	 * @throws InterruptedException
	 */
	public void impressionBillet() throws InterruptedException {
		this.nbBillets--;
		Thread.sleep(100); // Temps d'impression d'un ticket.
	}

	/**
	 * 
	 * @return vrai si il reste des billets disponible à acheter, faux sinon.
	 */
	public boolean billetsDispo() {
		if (nbBillets != 0) {
			return true;
		} else {
			return false;
		}
	}
}
