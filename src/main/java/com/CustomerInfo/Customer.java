package com.CustomerInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
<<<<<<< HEAD
    @Id
    private String mobileNo;
    private String address;
    private String emailId;
=======
	@Id
	private String mobileNo;
	private String address;
	private String emailId;

	public String toString() {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
>>>>>>> 1589371... Refactored src/java code and changed tests appropriately
}
