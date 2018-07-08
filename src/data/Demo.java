package data;




public class Demo {
	
	public static void main(String[] args) throws Exception {
		//load("matlab/net.mat")
		
		
		
		MatLabController.predirCredibilite(Controler.getCredibles().get(5));
		
		MatLabController.predirCredibilite(Controler.getNonCredibles().get(5));

		System.out.println(Controler.getCredibles().get(5).toString()+" --> "+Controler.getCredibles().get(5).getId());
		
		System.out.println(Controler.getNonCredibles().get(5).toString()+"---> "+Controler.getNonCredibles().get(5).getId());
		//Controler.initBDD("DCC");
		//Controler.init();
		//Controler.vide();
		
		//Controler.separer("data.txt");
		
/*		System.out.println("Interet --> "+Controler.predireInteret()+"$ Minimal Revenu ---> "+Controler.predireMinRevenu()+"$ ");
		
		System.out.println(Controler.profiles.get(10).profClass().name());
*/		
		System.out.println("Done !!");
	}
	
	
	
	
}