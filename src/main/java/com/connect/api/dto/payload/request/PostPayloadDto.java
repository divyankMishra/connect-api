package com.connect.api.dto.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostPayloadDto {
    @NotBlank(message = "Please provide title.")
    @Size(max = 50)
    private String title;
    private String description;
    private Long groupId;
}
