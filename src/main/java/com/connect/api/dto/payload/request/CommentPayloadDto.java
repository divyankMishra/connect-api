package com.connect.api.dto.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;


public record CommentPayloadDto(
        @Size(max = 300, message = "Comment can not exceed 300 characters.") @NotBlank(message = "Can not create empty comment.") String comment) implements Serializable {
}
