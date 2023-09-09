package com.nisum.ccplnisumusersapi.crosscutting.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageErrorEnum {

    NISUM000("NISUM000", "Error general.");

    private final String code;
    private final String description;

    @JsonCreator
    public static MessageErrorEnum fromCode(String code) {
        for (MessageErrorEnum b : MessageErrorEnum.values()) {
            if (b.code.equals(code)) {
                return b;
            }
        }

        throw new IllegalArgumentException("Unexpected valur '" + code + "'");
    }

}
