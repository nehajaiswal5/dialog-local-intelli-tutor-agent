/**
 * 
 */
package dialog.rest;

import java.util.List;

import javax.ws.rs.Path;

import com.ibm.watson.developer_cloud.dialog.v1.DialogService;
import com.ibm.watson.developer_cloud.dialog.v1.model.Dialog;

/**
 * @author nkumari
 *
 */
@Path("/tutorAgent")
public class DialogRestService {

	DialogService service = new DialogService();
	private String baseURL = "https://gateway.watsonplatform.net/dialog/api";
	private String username = "bde2df45-df2b-4c8d-b884-c48000b3b400";
	private String password = "HmAOgnCFTn12";
	private static String tutorDialogId = "8db55708-dacb-4cd3-88aa-a50058328194";
	private static String dialog_id="";
    
	static {
		dialog_id = "8db55708-dacb-4cd3-88aa-a50058328194";
	  }
	
	public void getDialogs(){
		service.setUsernameAndPassword(username, password);
		
		List<Dialog> dialogs = service.getDialogs();
		System.out.println("==============================");
		System.out.println(dialogs);
		System.out.println("==============================");

	}

	
}
