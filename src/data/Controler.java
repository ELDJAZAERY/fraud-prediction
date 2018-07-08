package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


@SuppressWarnings("unchecked")
public class Controler {
	
	public static final String dataSource = "DCC_Data.txt";	
	
	public static ArrayList<Profile> profiles = new ArrayList<>();
	public static void loadData() throws Exception{
		profiles = readFile(dataSource);
	}

	private static ArrayList<Profile> readFile(String nomF) throws Exception{
		bufR = new BufferedReader(new FileReader(nomF));

		String ligne , data[];
		
		ArrayList<Profile> profiles = new ArrayList<>();
		Profile profile ;
		
		while ((ligne=bufR.readLine())!=null){
			data = ligne.split("\t");
			int id = Integer.parseInt(data[0]),limitBAL = Integer.parseInt(data[1]),
					sex = Integer.parseInt(data[2]),education = Integer.parseInt(data[3]) , mariage =Integer.parseInt(data[4]) ,age = Integer.parseInt(data[5]),
					
					payStatus_September = Integer.parseInt(data[6]), payStatus_August=Integer.parseInt(data[7]) ,
					payStatus_July = Integer.parseInt(data[8]),payStatus_June = Integer.parseInt(data[9]),
					payStatus_May= Integer.parseInt(data[10]),payStatus_April =Integer.parseInt(data[11]),
					
					bill_September = Integer.parseInt(data[12]), bill_August=Integer.parseInt(data[13]) ,
					bill_July = Integer.parseInt(data[14]),bill_June = Integer.parseInt(data[15]),
					bill_May= Integer.parseInt(data[16]),bill_April =Integer.parseInt(data[17]),
	
					pay_September = Integer.parseInt(data[18]), pay_August=Integer.parseInt(data[19]) ,
					pay_July = Integer.parseInt(data[20]) , pay_June = Integer.parseInt(data[21]),pay_May= Integer.parseInt(data[22]),
					pay_April =Integer.parseInt(data[23]),
					credible = 0 ; // tt les users sont credible avant la prediction
					
			
			profile = new Profile(id, limitBAL, sex, education, mariage, age, credible);

			
			profile.addPayement(2018,9,bill_September,pay_September, payStatus_September);
			profile.addPayement(2018,8,bill_August,pay_August, payStatus_August);
			profile.addPayement(2018,7,bill_July,pay_July, payStatus_July);
			profile.addPayement(2018,6,bill_June,pay_June, payStatus_June);
			profile.addPayement(2018,5,bill_May,pay_May, payStatus_May);
			profile.addPayement(2018,4,bill_April,pay_April, payStatus_April);
			
			// appliquer la prediction a tout les profiles
			profile.predirCredibilite();
			profiles.add(profile);
		}
		
		return profiles;	
	}

	
	
	public static void init(){
		if(profiles.size()!=0) return;
        session.beginTransaction();
        
		Query query = session.createQuery("select id from Profile");
		ArrayList<Integer> ids = (ArrayList<Integer>) query.list();
		
		Profile profile;
        for(Integer id:ids){
            profile = (Profile) session.get(Profile.class,id);
            profiles.add(profile);
            Profile.cptId = Math.max(Profile.cptId,id);
        }
	
        Profile.cptId++;
        session.getTransaction().commit();
	}
	
	private static ArrayList<Profile> profilesNonCredible = new ArrayList<>();

	static SessionFactory factory;
	static Session session ;
	private static BufferedReader bufR;
	
	static{
		// create session factory
		factory = new Configuration()
					.configure()
							.buildSessionFactory();

		// create session
		session = factory.openSession();
		init();
		clear();
	}
	
	public static void clear(){
		for(int i=0;i<200;i++) System.out.println("");
		for(Profile p:profiles) if(p.credible()) System.out.println(p.getId()+"--> Predit Credible");
		else System.out.println(p.getId()+"--> Predit InCredible");
    }
	
	public static ArrayList<Profile> getNonCredibles(){
		init();
		if(!profilesNonCredible.isEmpty()) return profilesNonCredible;
		ArrayList<Profile> nonCredible = new ArrayList<>();
		
		for(Profile p:profiles)
			if(!p.credible()) nonCredible.add(p);
		
		profilesNonCredible = nonCredible;
		return nonCredible;
	}

	public static ArrayList<Profile> getCredibles(){
		init();
		ArrayList<Profile> Credible = new ArrayList<>();
		
		for(Profile p:profiles)
			if(p.credible()) Credible.add(p);
		
		return Credible;
	}

	public static Profile getProfile(int id){
		if(id>=profiles.size() || id<0) return null;
		return profiles.get(id);
	}
	
	public static void initBDD(String nomF) throws Exception{
		ArrayList<Profile> profiles = readFile(nomF+".txt"); 
		session.beginTransaction();
		
		System.out.println("Sauvgarde !!");
		for(Profile p:profiles){
			for(Payement pay:p.getHistoriePay()) session.save(pay);
			session.save(p);
		}
		
		session.getTransaction().commit();
		System.out.println("Done !!");
	}
	
	

	

	
	public static void separer(String nomF) throws Exception{
		bufR = new BufferedReader(new FileReader(nomF));
		
		int nbNonCredMax = 250 , nbCredMax = 1000 ,cptNonCred = 0 , cptCred = 0;
		ArrayList<String> DataOut = new ArrayList<>();
		
		
		String ligne , data[];
		
		while ((ligne=bufR.readLine())!=null){
			data = ligne.split("\t");

			if(Integer.parseInt(data[24])==0){
				if(cptCred < nbCredMax){
					DataOut.add(ligne);
					cptCred++;					
				}
			}else if(cptNonCred < nbNonCredMax){
				DataOut.add(ligne);
				cptNonCred++;
			}
			
		}
		sauvgarder_in_file(DataOut,"DCC");
	}
	
	public static void addProfile(Profile prof){
		profiles.add(prof);
		session = factory.openSession();
		session.beginTransaction();
		
		for(Payement pay:prof.getHistoriePay()) session.save(pay);
		session.save(prof);
		
		session.getTransaction().commit();
	}
	
	public static int sauvgarder_in_file(ArrayList<String> outMessage , String nomF) throws Exception{
		File f = new File(nomF+".txt");
		
		if(!f.exists()) f.createNewFile();
		else{
		  f.delete();
		  return sauvgarder_in_file(outMessage,nomF);
		}

		BufferedWriter buf = new BufferedWriter(new FileWriter(f));
		
		String data="";
		for(String s:outMessage) 
			data+=s+"\n";
		
		buf.write(data);
		
		buf.close();
		
		return 1;
	}

	
	public static int predireMinRevenu(){
		int minRev = 0;
		
		for(Profile p:profiles)
			minRev+=p.getMinRevenu();
		
		return minRev;
	}
	
	public static int predireMaxRevenu(){
		int maxRev = 0;
		
		for(Profile p:profiles)
			maxRev+=p.getMaxRevenu();
		
		return maxRev;
	}
	
	public static int predireInteret(){
		int interet = 0;
		
		for(Profile p:profiles)
			interet+=p.getPenalite();
		
		return interet;
	}
	
	public static void vide(){
		
	}
}
