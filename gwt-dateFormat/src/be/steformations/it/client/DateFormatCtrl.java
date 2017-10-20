package be.steformations.it.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.Window;

import be.steformations.it.client.http.beans.DateParams;
import be.steformations.it.client.http.beans.DateResult;
import be.steformations.it.client.http.json.DateFormatJsonDateResultCallback;
import be.steformations.it.client.http.json.DateParamObjectMapper;
import be.steformations.it.client.http.rpc.DateFormatRpcDateResultCallback;
import be.steformations.it.client.http.rpc.DateFormatRpcService;
import be.steformations.it.client.http.rpc.DateFormatRpcServiceAsync;
import be.steformations.it.client.http.rpc.DateFormatRpcStringCallback;
import be.steformations.it.client.http.standard.LocalesRequestCallback;
import be.steformations.it.client.ui.DateFormatterUI;

public class DateFormatCtrl 
	implements ClickHandler{

	private DateFormatterUI ui;
	
	public DateFormatCtrl(DateFormatterUI ui) {
		super();
		this.ui = ui;
		this.ui.getFormatEventSource().addClickHandler(this);
		this.getAvailableLocales();
	}
	
	@Override
	public void onClick(ClickEvent event) {
		GWT.log("DateFormatCtrl.onClick()");
		DateParams params = new DateParams();
		params.setDay(Integer.parseInt(this.ui.getDayInput().getValue()));
		params.setMonth(Integer.parseInt(this.ui.getMonthInput().getValue()));
		params.setYear(this.ui.getYearInput().getValue());
		params.setLocale(this.ui.getLocaleInput().getValue());
		GWT.log(params.toString());
		
		//this.formatDateRpc(params);
		//this.formatDateToObjectRpc(params);
		this.formatDateToObjectJson(params);
	}
	
	public void setFormatedDate(String s){
		GWT.log("DateFormatCtrl.setFormatedDate() => " + s);
		this.ui.getOutput().setText(s);
	}

	public void setDateResult(DateResult result){
		GWT.log("DateFormatCtrl.setDateResult() => " + result.toString());
		this.ui.getOutput().setText(result.getFormattedDate());
	}
	
	public void setAvailableLocales(String[] locales){
		GWT.log("DateFormatCtrl.setAvailableLocales()");
		this.ui.setLocales(locales);
		
	}
	
	private void formatDateToObjectJson(DateParams params){
		GWT.log("DateFormatCtrl.formatDateToObjectJson()");
		DateParamObjectMapper mapper = GWT.create(DateParamObjectMapper.class);
		String json = mapper.write(params);
		GWT.log(json);
		
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST,
				"http://127.0.0.1:8888/json/service");
		RequestCallback callback = new DateFormatJsonDateResultCallback(this);
		builder.setCallback(callback);
		builder.setRequestData(json);
		
		try{
			builder.send();
		}catch(Exception e){
			Window.alert(e.getMessage());
		}
	}
	
	private void formatDateToObjectRpc (DateParams params){
		GWT.log("DateFormatCtrl.formatDateToObjectRpc()");
		DateFormatRpcServiceAsync service = GWT.create(DateFormatRpcService.class);	
		DateFormatRpcDateResultCallback callback = new DateFormatRpcDateResultCallback(this);
		service.formatToObject(params, callback); //post http
	}
	
	@SuppressWarnings("unused")
	private void formatDateRpc(DateParams params){
		GWT.log("DateFormatCtrl.formatDateRpc()");
		DateFormatRpcServiceAsync service = GWT.create(DateFormatRpcService.class);
		DateFormatRpcStringCallback callback = new DateFormatRpcStringCallback(this);
		service.format(params, callback); //appel http
	}
	
	private void getAvailableLocales(){
		GWT.log("DateFormatCtrl.getAvailableLocales()");
		String url = "http://127.0.0.1:8888/available/locales";
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, url);
		LocalesRequestCallback callback = new LocalesRequestCallback(this);
		rb.setCallback(callback);
		try{
			rb.send(); // get http
		} catch(RequestException e){
			Window.alert(e.getMessage());
		}
	}
	
}
