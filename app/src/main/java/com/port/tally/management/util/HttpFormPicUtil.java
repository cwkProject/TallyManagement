package com.port.tally.management.util;

import com.port.tally.management.bean.FormPicFile;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpFormPicUtil {

	public static String post(String actionUrl,Map<String, String> params,
			FormPicFile[] files) {
		try {
			String enterNewline="\r\n";
			String fix="--";
			String boundary = "######";
			String MULTIPART_FORM_DATA = "multipart/form-data";

			URL url = new URL(actionUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true); 
			conn.setDoOutput(true); 
			conn.setUseCaches(false); 
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA
					+ "; boundary=" + boundary);
			DataOutputStream ds=new DataOutputStream(conn.getOutputStream());
			Set<String> keySet=params.keySet();
			Iterator<String> it=keySet.iterator();
			 
			while(it.hasNext()){
				String key=it.next();
				String value=params.get(key);
				ds.writeBytes(fix + boundary + enterNewline);
				ds.writeBytes("Content-Disposition: form-data;"
						+ "name=\""+key+"\""+ enterNewline);
				ds.writeBytes(enterNewline);
				ds.writeBytes(value);
				ds.writeBytes(enterNewline);
			}
			 
			if(files!=null&&files.length>0){
				for(int i=0;i<files.length;i++){
					ds.writeBytes(fix + boundary + enterNewline);
					ds.writeBytes("Content-Disposition: form-data;"
							+ "name=\""+files[0].getFormname() +"\""+"; filename=\""+ enterNewline);
					ds.writeBytes(enterNewline);
					byte[] buffer =files[0].getData();
					ds.write(buffer);
					ds.writeBytes(enterNewline);
				}
			}
			ds.writeBytes(fix + boundary + fix + enterNewline);
			ds.flush();

			
			InputStream is = conn.getInputStream();
			int ch;
			StringBuilder b = new StringBuilder();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			ds.close();
			
		   conn.disconnect();
		  return b.toString().trim();
		} catch (Exception e) {
			return "" + e;
		}
	}

}
