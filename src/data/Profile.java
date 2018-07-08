package data;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;



@Entity
@Table(name = "Profile")
public class Profile {

	@Transient
	static int cptId=0;
	
	@Id
    @Column(name = "id", unique = true, nullable = false)
	private int id;
 
	
	private int  limitBAL , sex, education , mariage ,age,credible;
	
/*	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	@Transient*/
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<Payement> historiePay = new ArrayList<>();
	
	
	@Transient
	private int ScorePoint=0; 
	
	@Transient
	private boolean blocked = false;
	
	
	public Profile() {}
	public Profile(int id) {
		this.id=id;
	}

	public Profile(int id, int limitBAL, int sex, int education, int mariage, int age, int credible,
			List<Payement> historiePay) {
		this.id = id;
		this.limitBAL = limitBAL;
		this.sex = sex;
		this.education = education;
		this.mariage = mariage;
		this.age = age;
		this.credible = credible;
		this.historiePay = historiePay;
		
		cptId++;
	}

	public Profile(int id, int limitBAL, int sex, int education, int mariage, int age, int credible){
		this.id = id;
		this.limitBAL = limitBAL;
		this.sex = sex;
		this.education = education;
		this.mariage = mariage;
		this.age = age;
		this.credible = credible;
		
		cptId++;
	}

	public Profile(int limitBAL, int sex, int education, int mariage, int age, int credible){
		this.id=cptId;
		this.limitBAL = limitBAL;
		this.sex = sex;
		this.education = education;
		this.mariage = mariage;
		this.age = age;
		this.credible = credible;
		
		cptId++;
	}
	
	public void addPayement(int an,int mois,int bille,int montant,int payStatus){
		historiePay.add(new Payement(an, mois, bille, montant, payStatus,this));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLimitBAL() {
		return limitBAL;
	}

	public void setLimitBAL(int limitBAL) {
		this.limitBAL = limitBAL;
	}

	public String getSex() {
		if(sex==1) return "Male" ;
		else return "Female" ;
	}

	public int getIntSex() {return sex;}
	public int getIntEducation() {return education;}
	public int getSituationFamillial() {return mariage;}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getEducation() {
		// 1 = graduate school; 2 = university; 3 = high school; 4 = others
		String s;
		switch (education) {
			case 1: s="graduate school" ; break;
			case 2: s="university" ; break;
			case 3: s="high school" ; break;
			default:s="others" ; break;
		}
		
		return s;
	}


	public void setEducation(int education) {
		this.education = education;
	}
	
	public String getMariage() {
		String s;
		switch (mariage) {
			case 1: s="married" ; break;
			case 2: s="single"  ; break;
			default: s="others" ; break;
		}
		
		return s;
	}

	public void setMariage(int mariage) {
		this.mariage = mariage;
	}

	public boolean credible() {
		return credible==0;
	}

	public void setCredible(int credible) {
		this.credible = credible;
	}

	public void predirCredibilite() throws Exception{
		credible = MatLabController.predirCredibilite(this);
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public List<Payement> getHistoriePay() {
		return historiePay;
	}

	public void setHistoriePay(List<Payement> historiePay) {
		this.historiePay = historiePay;
	}

	public boolean getCredible() {
		return credible==0;
	}

	public int getBallance(){
		int balance = 0;
		if(historiePay.size()!=0)
			balance = historiePay.get(historiePay.size()-1).getBille();
		return balance;
	}
	
	public int getScorePoint() {
		if(ScorePoint!=0) return ScorePoint;
		
		int scor=0;
		
		for(Payement p:historiePay){
			if(p.getPayStatus()==-1)
				scor+=10;
			else {
				if(p.getPayStatus()==0)
					scor-= 10;
				else
					scor -= 10 * p.getPayStatus() ;
			}
		}
		
		return scor>0 ? scor : 0 ;
	}
	
	public void setScorePoint(int scorePoint) {
		ScorePoint = scorePoint;
	}

	public ProfClass profClass(){
		int scor = getScorePoint();
		if(scor>=60) return ProfClass.Gold;
		if(scor>=40) return ProfClass.Silver;
		if(scor>=20) return ProfClass.Bronze;
		if(blocked())return ProfClass.Blocked;
		return ProfClass.Simple;
	}
	
	
	public boolean blocked(){
		blocked = defaultPayement(3);
		return blocked;
	}
		
	
	public int getPenalite(){
		int penalite = 0;
		if(!getCredible()){
			penalite+=30;
			if(!historiePay.isEmpty()){
				Payement pay = historiePay.get(historiePay.size()-1);
				penalite+= pay.getBille() * 0.1 ;
			}
		}
		return penalite;
	}
	
	public int getMinRevenu(){
		int rev =0;
		if(getCredible() && !historiePay.isEmpty()){
			Payement pay = historiePay.get(historiePay.size()-1);
			rev=pay.getMinimaPay(); 
		}		
		return rev;
	}
	
	public int getMaxRevenu(){
		int rev =0;
		if(getCredible() && !historiePay.isEmpty()){
			Payement pay = historiePay.get(historiePay.size()-1);
			rev=pay.getBille(); 
		}		
		return rev;
	}
	
	public int DPTotal(){
		int dpt = 0;
		for(Payement p:historiePay)
			if(p.getPayStatus()!=-1) dpt++;
		
		return dpt;
	}
	
	public int DPSucses(){
		Collections.sort(historiePay);
		int dps = 0;
		for(Payement p:historiePay)
			if(p.getPayStatus()!=-1) dps++; else dps=0;
		
		return dps;
	}

	public String rappelle(){
		
		if(defaultPayement(4)) return juge();
		if(defaultPayement(2)) return avertissement();
		if(rapPaye()) return rappelle_paiement();
		return "";
	}
		
	private boolean defaultPayement(int maxRetard){
		if(DPSucses() >= maxRetard) return true;
		return false;
	}
	
	private boolean rapPaye(){
		if(!blocked() && !credible()){
			return true;
		}
		return false;
	}

	
	private String rappelle_paiement(){
		if(historiePay.isEmpty()) return "";
		return "Payement Reminder : Dear Client this is a friendly reminder \n that you have to pay your bill before the 01/10/2017 \n ";
	}
	
	private String avertissement(){
		int nbscc = DPSucses();
		if(nbscc==2)
			return "Payement Warning : Dear Client , you are two months late for your payement , \n Your account maybe blocked if you don't pay your bill before the 01/10/2017";
		else if(nbscc==3) return "Blocked";
		else return "";
	}

	private String juge(){
		int nbscc = DPSucses();
		if(nbscc==5)
			return " Judgement Warning : Dear Client , you are five months late for your payement , \n we are sorry to inform you that we are suing you if you don't pay your bill \n before the 01/10/2017";
		else if(nbscc==6) return "Judgement";
		else return "";
	}

	@Override
	public String toString() {
		String s , status="" , billes="" , baillements="" ;
		s="" + limitBAL + "," + sex + "," + education
				+ "," + mariage + "," + age ;
		
		for(Payement p:historiePay){
			status+=","+p.getPayStatus();
			billes+=","+p.getBille();
			baillements+=","+p.getMontant();
		}
		
		s+=status+billes+baillements;
		
		return s;
	}

	@Override
	public boolean equals(Object obj) {
		return id == ((Profile) obj).getId();
	}
	
}
