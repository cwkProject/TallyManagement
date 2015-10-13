package com.port.tally.management.util;

import com.port.tally.management.bean.FormFile;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpFormUtil {

	public static String post(String actionUrl, Map<String, String> params,
			FormFile[] files) {
		try {
			String enterNewline="\r\n";
			String fix="--";
			String BOUNDARY = "######";
			String MULTIPART_FORM_DATA = "multipart/form-data";

			URL url = new URL(actionUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);// 允许输入
			conn.setDoOutput(true);// 允许输出
			conn.setUseCaches(false);// 不使用Cache
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA
					+ "; boundary=" + BOUNDARY);
			DataOutputStream ds=new DataOutputStream(conn.getOutputStream());
			Set<String> keySet=params.keySet();
			Iterator<String> it=keySet.iterator();
			//循环写入普通文本域
			while(it.hasNext()){
				String key=it.next();
				String value=params.get(key);
				ds.writeBytes(fix + BOUNDARY + enterNewline);
				ds.writeBytes("Content-Disposition: form-data;"
						+ "name=\""+key+"\""+ enterNewline);
				ds.writeBytes(enterNewline);
				ds.writeBytes(value);
				ds.writeBytes(enterNewline);
			}
			//循环写入上传文件
			if(files!=null&&files.length>0){
				for(int i=0;i<files.length;i++){
					ds.writeBytes(fix + BOUNDARY + enterNewline);
					ds.writeBytes("Content-Disposition: form-data;"
							+ "name=\""+files[0].getFormname() +"\""+"; filename=\"" + files[0].getFilname() + "\"" + enterNewline);
					ds.writeBytes(enterNewline);
					byte[] buffer =files[0].getData();
					ds.write(buffer);
					ds.writeBytes(enterNewline);
				}
			}
			ds.writeBytes(fix + BOUNDARY + fix + enterNewline);
			ds.flush();

			StringBuilder sb = new StringBuilder();

			// 上传的表单参数部分，格式请参考文章
			for (Map.Entry<String, String> entry : params.entrySet()) {// 构建表单字段内容
				sb.append("–");
				sb.append(BOUNDARY);
				sb.append("\r\n");
				sb.append("Content-Disposition: form-data; name=\""
						+ entry.getKey() + "\"\r\n\r\n");
				sb.append(entry.getValue());
				sb.append("\r\n");
			}
			DataOutputStream outStream = new DataOutputStream(conn
					.getOutputStream());
			outStream.write(sb.toString().getBytes());// 发送表单字段数据

			// 上传的文件部分，格式请参考文章
			for (FormFile file : files) {
				StringBuilder split = new StringBuilder();
				split.append("–");
				split.append(BOUNDARY);
				split.append("\r\n");
				split.append("Content-Disposition: form-data;name=\""
						+ file.getFormname() + "\";filename=\""
						+ file.getFilname() + "\"\r\n");
				split.append("Content-Type: " + file.getContentType()
						+ "\r\n\r\n");
				outStream.write(split.toString().getBytes());
				outStream.write(file.getData(), 0, file.getData().length);
				outStream.write("\r\n".getBytes());
			}
			byte[] end_data = ("–" + BOUNDARY + "–\r\n").getBytes();// 数据结束标志
			outStream.write(end_data);
			outStream.flush();
			int cah = conn.getResponseCode();
			if (cah != 200)
				throw new RuntimeException("请求url失败");
			
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

