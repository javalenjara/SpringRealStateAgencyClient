package co.com.udem.reaclient.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import co.com.udem.reaclient.domain.AutenticationRequestDTO;
import co.com.udem.reaclient.domain.AutenticationResponseDTO;
import co.com.udem.reaclient.entities.UserToken;
import co.com.udem.reaclient.repositories.UserTokenRepository;

@RestController
public class RealEstateAgencyClientRestController {
	
	@Autowired
    RestTemplate restTemplate;
   
    @Autowired
    UserTokenRepository userTokenRepository;
   
    @Autowired
    UserToken userToken;
    
    @Autowired
    private LoadBalancerClient loadBalancer;
   
    @PostMapping("/authenticate")
    public String autenticar(@RequestBody AutenticationRequestDTO autenticationRequestDTO) {
    	ServiceInstance serviceInstance = loadBalancer.choose("rea");
    	System.out.println(serviceInstance.getUri());
    	String baseUrl = serviceInstance.getUri().toString();
    	baseUrl = baseUrl + "/auth/signin";
    	ResponseEntity<String> postResponse = restTemplate.postForEntity(baseUrl, autenticationRequestDTO, String.class);
    	System.out.println("Respuesta: " + postResponse.getBody());
    	Gson gson = new Gson();
    	AutenticationResponseDTO autenticationResponseDTO = gson.fromJson(postResponse.getBody(), AutenticationResponseDTO.class);
    	userToken.setUsername(autenticationResponseDTO.getUsername());
    	userToken.setToken(autenticationResponseDTO.getToken());
    	userTokenRepository.save(userToken);
    	return autenticationResponseDTO.getToken();
    }
//   
//    @GetMapping("/consultarClubes")
//    public List<ClubFutbolDTO> consultarClubFutbol() {
//        List<ClubFutbolDTO> clubes = null;
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjYXJsb3MuaGVybmFuZGV6Iiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTU5NTA4MzI3NywiZXhwIjoxNTk1MDg2ODc3fQ.SCFmfzwuE8e2V91RazdlfjV2Xy7Q6FSdnjym_4h5dT0");
//        HttpEntity<String> entity = new HttpEntity<String>(headers);
//        ResponseEntity<String> response = restTemplate.exchange("http://localhost:9090/clubes", HttpMethod.GET, entity, String.class);
//        try {
//            clubes = new ObjectMapper().readValue(response.getBody(), new TypeReference<List<ClubFutbolDTO>>() {});
//        } catch (JsonMappingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (JsonProcessingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return clubes;
//    }

}
