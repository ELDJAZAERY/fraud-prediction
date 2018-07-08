package application;

import data.Payement;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;


public class LineTabPayement {
	
    private final SimpleStringProperty annee;//
    private final SimpleStringProperty mois;//
    private final SimpleStringProperty Bille;//
    private final SimpleStringProperty montant;
    private final SimpleStringProperty payStatus;
   
    
	public LineTabPayement(Payement pay) {
		this.annee = new SimpleStringProperty(""+pay.getAnnee());
		this.mois = new SimpleStringProperty(""+pay.getMois());
		this.Bille = new SimpleStringProperty(""+pay.getBille());
		this.montant = new SimpleStringProperty(""+pay.getMontant());
		this.payStatus = new SimpleStringProperty(""+pay.getPayStatus());
		
	}



	public LineTabPayement(SimpleStringProperty annee, SimpleStringProperty mois, SimpleStringProperty bille,
		SimpleStringProperty montant, SimpleStringProperty payStatus) {
	this.annee = annee;
	this.mois = mois;
	Bille = bille;
	this.montant = montant;
	this.payStatus = payStatus;
	}

	
	public String getAnnee() {
		return annee.get();
	}

	public String getMois() {
		return mois.get();
	}

	public String getDate(){
		return "01/"+mois.get()+"/2017";
	}
	public String getBille() {
		return Bille.get()+" $";
	}

	public String getMontant() {
		return montant.get()+" $";
	}

	public String getPayStatus() {
		return payStatus.get();
	}

	
	@SuppressWarnings("rawtypes")
	public static TableColumn newColumn(String columnName){
        TableColumn<?, ?> col = new TableColumn(columnName);
		col.setMinWidth(100);
		col.setCellValueFactory(
				new PropertyValueFactory<>(columnName));
		
		return col;
	}
	
}
