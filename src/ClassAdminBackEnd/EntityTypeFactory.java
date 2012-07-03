package ClassAdminBackEnd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EntityTypeFactory {
	public EntityType makeEntityType(){
		try{
			EntityType eT;

			System.out.println("Enter the name of the entity type");

			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String name = bufferRead.readLine();

			eT = new EntityType(name);

			return eT;
		}
		catch(IOException e){
			e.printStackTrace();
		}

		return null;
	}
}
