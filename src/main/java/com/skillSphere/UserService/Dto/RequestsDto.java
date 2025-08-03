package com.skillSphere.UserService.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestsDto {


        @JsonProperty("sender")
        private UserDto sender;

        @JsonProperty("reciever")
        private UserDto Receiver;

}
