import java.io.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


public class Startup {
	
	 public static void displayResult(InputStream i){
		   BufferedReader lire= new BufferedReader(new InputStreamReader(i));
		   try{
		   String line;
		   do{
			     line= lire.readLine();
			       if(line!=null) System.out.println(line);
			       }
		   while(line!=null);
		   lire.close();
		   }catch(IOException e){}
		   
	}	 
	
	public static void main(String arg[]){
		
		try {
			LocateRegistry.createRegistry(1100);
			new ClientApplication();
		} catch (RemoteException e1) {
			System.out.println("Erreur IOException::L'application n'a pas pu être lancée");
			e1.printStackTrace();
		}
		
		/*try {
			
			Runtime rmiShell = Runtime.getRuntime();
			Runtime appliShell = Runtime.getRuntime();
			File path = new File(".");
			
			Process rmi = rmiShell.exec("rmiregistry",null,path);
			//rmi.waitFor();
			System.out.println("\n---	Service rmi lancee	---");
			//Startup.displayResult(rmi.getErrorStream());
			
			Process launch = appliShell.exec("java ClientApplication",null,path);			
			System.out.println("--	Client	Lancee	---\n");			
			//Startup.displayResult(launch.getErrorStream());
			launch.waitFor();
			if(rmi!=null)rmi.destroy();
			
		} catch (IOException e) {
			System.out.println("Erreur IOException::L'application n'a pas pu être lancée");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Erreur InterruptedException::L'application n'a pas pu être lancée");
			e.printStackTrace();
		}*/
		
	}

}
