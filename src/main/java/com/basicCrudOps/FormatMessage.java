package com.basicCrudOps;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FormatMessage {
    public String code;
    public String details;
    public String message="Validation Failed";
    FormatMessage(String code, String details){
        this.code=code;
        this.details=details;
    }
    FormatMessage(String message){
        this.message=message;
    }

}
