package com.nisum.ccplnisumusersapi.crosscutting.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageErrorEnum {

    NISUM000("NISUM000", "General exception."),
    NISUM001("NISUM001", "User with id '%s' does not exist."),
    NISUM002("NISUM002", "Email '%s' has already been registered."),
    NISUM003("NISUM003", "Error validating input fields."),
    NISUM004("FORBIDDEN", "Access denied. Error: %s"),
    NISUM005("UNAUTHORIZED", "Authentication Error: %s!");

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
