package be.steformations.it.server;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import be.steformations.it.client.http.beans.DateParams;
import be.steformations.it.client.http.beans.DateResult;
import be.steformations.it.client.http.rpc.DateFormatRpcService;

public class DateFormatRpcServiceImpl extends RemoteServiceServlet //extension de HttpServlet 
									  implements DateFormatRpcService  {

	private Calendar calendar = GregorianCalendar.getInstance();
	
	@Override
	public String format(DateParams params) {
		System.out.println("DateFormatRpcServiceImpl.format()" + params);
		calendar.set(Calendar.DAY_OF_MONTH, params.getDay());
		calendar.set(Calendar.MONTH, params.getMonth() -1);
		calendar.set(Calendar.YEAR, params.getYear());
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, new Locale(params.getLocale()));
		String formatted = df.format(calendar.getTime());
		System.out.println(formatted);
		return formatted;
	}

	@Override
	public DateResult formatToObject(DateParams params) {
		System.out.println("DateFormatRpcServiceImpl.formatToObject()");
		calendar.set(Calendar.DAY_OF_MONTH, params.getDay());
		calendar.set(Calendar.MONTH, params.getMonth() -1);
		calendar.set(Calendar.YEAR, params.getYear());
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, new Locale(params.getLocale()));
		String formatted = df.format(calendar.getTime());
		DateResult result = new DateResult();
		result.setDate(calendar.getTime());
		result.setFormattedDate(formatted);
		return result;
	}

}
