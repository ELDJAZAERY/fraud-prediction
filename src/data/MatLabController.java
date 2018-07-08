package data;

import java.io.StringWriter;

import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;


public class MatLabController {
	
	static MatlabEngine mateng;
	static{
		try {
			mateng = MatlabEngine.startMatlab();
		} catch (EngineException | IllegalArgumentException | IllegalStateException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		System.out.println("lance");
	}
	
	
	public static void execute() throws Exception{
		System.out.println("Etape une ");
		
		MatlabEngine mateng = MatlabEngine.startMatlab();
		
		System.out.println("Etape 2 ");
		mateng.eval("syms x;",null,null);
		mateng.eval("f = sin(x);",null,null);
		
		System.out.println("Etape 3 ");
		StringWriter output = new StringWriter();
		mateng.eval("5",output,null);
		System.out.println("Etape 4 ");
		System.out.println("matlab output = "+output.toString());

	}
	
	
	public static int predirCredibilite(Profile profile) throws Exception{
		StringWriter output = new StringWriter();
		//mateng = MatlabEngine.startMatlab();
		
		mateng.eval("load('matlab/net.mat');",null,null);
		//mateng.eval("x",output,null);
		mateng.eval("predir = net("+profile.toString()+")",output,null);
		
		System.out.println(output.toString());
		
		if(output.toString().contains("0")) { System.out.println(profile.getId()+"--> Predit Credible");  return 0; }
		System.out.println(profile.getId()+"--> Predit InCredible");
		return 1;
	}

}
