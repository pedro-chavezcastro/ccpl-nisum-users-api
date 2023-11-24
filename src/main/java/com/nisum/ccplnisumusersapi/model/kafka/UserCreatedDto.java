package com.nisum.ccplnisumusersapi.model.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class UserCreatedDto implements Serializable {

    private static final long serialVersionUID = -4204843113504524374L;

    private String name;
    private String email;

}
