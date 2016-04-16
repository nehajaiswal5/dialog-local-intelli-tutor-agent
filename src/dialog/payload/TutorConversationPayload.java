/* Copyright IBM Corp. 2015
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dialog.payload;

import java.util.List;

import com.tutor.agent.periodicelements.PeriodicElements.PeriodicElement;


public class TutorConversationPayload {
	
    private String conversationId;
    private String clientId;
    private String input;
    private String wdsResponse;
    
    private List<ElementsPayload> elements;
    
    private PeriodicElement element;
    
    /**
     * @return  the conversation id which is unique to a conversation in WDS
     */
    public String getConversationId() {
        return conversationId;
    }

    /**
     * @param conversationId  the conversation id which is unique to a conversation in WDS
     */
    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    /**
     * @return  the client id which is unique to a session in WDS
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @param clientId  the client id which is unique to a session in WDS
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * @return  the user input for the conversation
     */
    public String getInput() {
        return input;
    }

    /**
     * @param input  the user input for the conversation
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * @return  the text response from WDS
     */
    public String getWdsResponse() {
        return wdsResponse;
    }

    /**
     * @param wdsResponse  the text response from WDS
     */
    public void setWdsResponse(String wdsResponse) {
        this.wdsResponse = wdsResponse;
    }


	public List<ElementsPayload> getElements() {
		return elements;
	}

	public void setElements(List<ElementsPayload> elements) {
		this.elements = elements;
	}

	public PeriodicElement getElement() {
		return element;
	}

	public void setElement(PeriodicElement element) {
		this.element = element;
	}
}
