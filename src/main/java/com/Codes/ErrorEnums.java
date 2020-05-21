package com.Codes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorEnums {

	MEDIA_FORMAT_INVALID_CODE("0", InnerConstants.MEDIA_FORMAT_INVALID_MESSAGE),
	MOBILE_INVALID_CODE("1", InnerConstants.MOBILE_INVALID_MESSAGE),
	ADDRESS_INVALID_CODE("2", InnerConstants.ADDRESS_INVALID_MESSAGE),
	EMAIL_INVALID_CODE("3", InnerConstants.EMAIL_INVALID_MESSAGE),
	MOBILE_IN_USE_INVALID_CODE("4", InnerConstants.MOBILE_IN_USE_INVALID_MESSAGE),
	EMAIL_IN_USE_INVALID_CODE("5", InnerConstants.EMAIL_IN_USE_INVALID_MESSAGE),
	MOBILE_NOT_FOUND_INVALID_CODE("6", InnerConstants.MOBILE_NOT_FOUND_INVALID_MESSAGE),
	EMAIL_NOT_FOUND_INVALID_CODE("7", InnerConstants.EMAIL_NOT_FOUND_INVALID_MESSAGE),
	JSON_INVALID_CODE("8", InnerConstants.JSON_INVALID_MESSAGE),
	SUCCESS_CODE(InnerConstants.SUCCESS_MESSAGE);

	private String code;
	private String codeDetails;
	private String message;

	ErrorEnums(String code, String codeDetails){
		this.code=code;
		this.codeDetails=codeDetails;
		this.message=InnerConstants.INVALID_MESSAGE;
	}

	ErrorEnums(String message){
		this.message=message;
	}

	public String toString(){
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public class InnerConstants {

		public static final String MEDIA_FORMAT_INVALID_MESSAGE = "Unsupported Media Type";
		public static final String MOBILE_INVALID_MESSAGE = "Mobile No. is invalid!";
		public static final String ADDRESS_INVALID_MESSAGE = "Address is invalid!";
		public static final String EMAIL_INVALID_MESSAGE = "Email Id is invalid!";
		public static final String MOBILE_IN_USE_INVALID_MESSAGE = "Mobile No. already in use";
		public static final String EMAIL_IN_USE_INVALID_MESSAGE = "Email already in use";
		public static final String MOBILE_NOT_FOUND_INVALID_MESSAGE = "No Mobile No. found";
		public static final String EMAIL_NOT_FOUND_INVALID_MESSAGE = "No EmailId found";
		public static final String JSON_INVALID_MESSAGE = "Json format is not correct!";

		public static final String INVALID_MESSAGE = "Validation Failed!";
		public static final String SUCCESS_MESSAGE = "Success!";
	}
}
