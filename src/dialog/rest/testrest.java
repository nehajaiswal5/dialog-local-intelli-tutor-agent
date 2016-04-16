package dialog.rest;
import java.util.List;

import javax.ws.rs.Path;

import com.ibm.watson.developer_cloud.dialog.v1.DialogService;
import com.ibm.watson.developer_cloud.dialog.v1.model.Conversation;
import com.ibm.watson.developer_cloud.dialog.v1.model.Dialog;

public class testrest {
 
	private String baseURL = "https://gateway.watsonplatform.net/dialog/api";
	private static String username = "bde2df45-df2b-4c8d-b884-c48000b3b400";
	private static String password = "HmAOgnCFTn12";
	private static String tutorDialogId = "6b4d66b7-226e-40cc-bb2a-8ab78b7eb5ee";
	private static String dialog_id="";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DialogService dialogService = new DialogService();
		dialogService.setUsernameAndPassword(username, password);
		
		List<Dialog> dialogs = dialogService.getDialogs();
		System.out.println("==============================");
		System.out.println(dialogs);
		System.out.println("==============================");
		
		Conversation conversation = dialogService.createConversation("6b4d66b7-226e-40cc-bb2a-8ab78b7eb5ee");
		dialogService.converse(conversation, "Hi");
		
		//dialogService.getConversationData(params)
		
		List<Dialog> dialogs1 = dialogService.getDialogs();
		System.out.println("==============================");
		System.out.println(dialogs1);
		System.out.println("==============================");

		
	}

}
