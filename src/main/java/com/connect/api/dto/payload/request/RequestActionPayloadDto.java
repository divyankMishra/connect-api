package com.connect.api.dto.payload.request;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public record RequestActionPayloadDto(
        @NotNull(message = "Give proper parameter") Boolean accept) implements Serializable {

}
