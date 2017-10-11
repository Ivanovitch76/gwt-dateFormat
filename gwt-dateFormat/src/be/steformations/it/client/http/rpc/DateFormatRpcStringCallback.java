package be.steformations.it.client.http.rpc;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import be.steformations.it.client.DateFormatCtrl;

public class DateFormatRpcStringCallback 
			implements AsyncCallback<String>{

	private DateFormatCtrl controller;
	
	public DateFormatRpcStringCallback(DateFormatCtrl controller) {
		super();
		this.controller = controller;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.getMessage());
	}

	@Override
	public void onSuccess(String result) {
		GWT.log("DateFormatRpcStringCallback.onSuccess() =>" + result);
		this.controller.setFormatedDate(result);
	}

}
