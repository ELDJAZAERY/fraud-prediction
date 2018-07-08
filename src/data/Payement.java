package data;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "Payement")
public class Payement implements Comparable<Payement>{
	
	@Id @GeneratedValue(strategy= GenerationType.AUTO)
	private int id;
	
	private int Annee , mois ,Bille,montant , payStatus;

	@ManyToOne
	private Profile profile;
	

	public Payement() {}
	public Payement(int annee, int mois, int bille, int montant, int payStatus, Profile profile) {
		super();
		Annee = annee;
		this.mois = mois;
		Bille = bille;
		this.montant = montant;
		this.payStatus = payStatus;
		this.profile = profile;
	}

	public int getAnnee() {
		return Annee;
	}

	public void setAnnee(int annee) {
		Annee = annee;
	}

	public int getMois() {
		return mois;
	}

	public void setMois(int mois) {
		this.mois = mois;
	}

	public int getMontant() {
		return Math.abs(montant);
	}

	public void setMontant(int montant) {
		this.montant = montant;
	}

	public int getBille() {
		return Math.abs(Bille);
	}

	public void setBille(int bille) {
		Bille = bille;
	}

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}
	
	public int getMinimaPay(){
		return (int) (Math.abs(Bille) * 0.3) ;
	}

	
	@Override
	public int compareTo(Payement p) {
		if(mois > p.mois) return 1;
		else if(mois==p.mois) return 0;
		else return -1;
	}

}
