package co.com.udem.reaclient.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutenticationResponseDTO {
	
	private String username;
	private String token;
	
}
