package co.com.udem.reaclient.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutenticationRequestDTO {
	
	private String username;
	private String password;
	
}
