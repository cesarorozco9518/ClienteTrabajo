import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class Prueba {
	
	
	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Cookie", "JSESSIONID=dksjfghdfkljghdfgkdfhg");
		headers.set("headerOwn", "sdjkdfhghdfjkghdhfj");	
		headers.setContentType(MediaType.TEXT_PLAIN);
		HttpEntity<String> entity = new HttpEntity<String>("", headers);
//		restTemplate.put("http://10.51.58.197:8082/WS_BancaDigital/api/bdm/consulta/ultimosDocumentos", entity);
		restTemplate.exchange("http://10.51.58.197:8082/WS_BancaDigital/api/bdm/consulta/ultimosDocumentos", HttpMethod.POST, entity, String.class);
	}

}
