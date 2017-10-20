package be.steformations.it.client.http.json;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;

import be.steformations.it.client.DateFormatCtrl;
import be.steformations.it.client.http.beans.DateResult;

public class DateFormatJsonDateResultCallback implements RequestCallback{

	private DateFormatCtrl controller;
	
	public DateFormatJsonDateResultCallback(DateFormatCtrl controller) {
		super();
		this.controller = controller;
	}

	@Override
	public void onResponseReceived(Request request, Response response) {
		String json = response.getText();
		GWT.log("DateFormatJsonDateResultCallback.onResponseReceived() => " + json);	
		com.github.nmorel.gwtjackson.client.ObjectMapper<DateResult>
			mapper = GWT.create(DateResultObjectMapper.class);
		DateResult result = mapper.read(json);
		GWT.log(result.toString());
		this.controller.setDateResult(result);
	}

	@Override
	public void onError(Request request, Throwable exception) {
		Window.alert(exception.getMessage());		
	}
	
}
