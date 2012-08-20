package ClassAdminBackEnd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.LinkedList;

public class EntityTypeFactory {
	public EntityType makeEntityType(){
		try{
			EntityType eT;

			System.out.println("Enter the name of the entity type");

			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String name = bufferRead.readLine();
			

			eT = new EntityType(name);
			Global.getGlobal().getActiveProject().getEntityTypes().add(eT);

			return eT;
		}
		catch(IOException e){
			e.printStackTrace();
		}

		return null;
	}
	
	public EntityType makeEntityTypeFileImport(String name, Boolean isTextField){
		
		EntityType eT = new EntityType(name, null, null, null, isTextField, null, 1.0);

		Global.getGlobal().getActiveProject().getEntityTypes().add(eT);
		return eT;
		
	}
}
