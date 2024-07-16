package test11;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ApiExplorer {
    public static void main(String[] args) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/1352000/ODMS_STAT_24/callStat24Api?serviceKey=w4BT36DgyXraOOMfPR%2F7t42yv36o2O0kzKf9wgEpFNRq81snBPj1KVI1JewUIelwMCe8CU9Xl4LI9dGficMarA%3D%3D&pageNo=1&numOfRows=10&apiType=XML&year=2019");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "w4BT36DgyXraOOMfPR%2F7t42yv36o2O0kzKf9wgEpFNRq81snBPj1KVI1JewUIelwMCe8CU9Xl4LI9dGficMarA%3D%3D");
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("apiType","UTF-8") + "=" + URLEncoder.encode("XML", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("year","UTF-8") + "=" + URLEncoder.encode("2019", "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/xml");
        System.out.println("Response code: " + conn.getResponseCode());

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        // XML
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new java.io.ByteArrayInputStream(sb.toString().getBytes()));
            
            doc.getDocumentElement().normalize();
            
            NodeList headerList = doc.getElementsByTagName("header");
            if (headerList.getLength() > 0) {
                Element header = (Element) headerList.item(0);
                String resultCode = header.getElementsByTagName("resultCode").item(0).getTextContent();
                String resultMsg = header.getElementsByTagName("resultMsg").item(0).getTextContent();
                System.out.println("Result Code: " + resultCode);
                System.out.println("Result Message: " + resultMsg);
            }
            
            NodeList itemsList = doc.getElementsByTagName("item");
            for (int i = 0; i < itemsList.getLength(); i++) {
                Element item = (Element) itemsList.item(i);
                
                String year = item.getElementsByTagName("year").item(0).getTextContent();
                String all = item.getElementsByTagName("all").item(0).getTextContent();
                String age65_69 = item.getElementsByTagName("age65-69").item(0).getTextContent();
                String age70_74 = item.getElementsByTagName("age70-74").item(0).getTextContent();
                String age75_79 = item.getElementsByTagName("age75-79").item(0).getTextContent();
                String age80_84 = item.getElementsByTagName("age80-84").item(0).getTextContent();
                String age85_89 = item.getElementsByTagName("age85-89").item(0).getTextContent();
                String age90u = item.getElementsByTagName("age90u").item(0).getTextContent();
                
                System.out.println("Year: " + year);
                System.out.println("All: " + all);
                System.out.println("Age 65-69: " + age65_69);
                System.out.println("Age 70-74: " + age70_74);
                System.out.println("Age 75-79: " + age75_79);
                System.out.println("Age 80-84: " + age80_84);
                System.out.println("Age 85-89: " + age85_89);
                System.out.println("Age 90+: " + age90u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}