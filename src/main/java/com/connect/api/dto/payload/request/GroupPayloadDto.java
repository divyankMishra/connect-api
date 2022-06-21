package com.connect.api.dto.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class GroupPayloadDto implements Serializable {
    @NotBlank
    @Size(max = 50,message = "Group name can not exceed 50 characters.")
    private final String name;
    @Size(max = 255,message = "Group description can not exceed 255 characters.")
    private final String description;
    private final Set<String> memberUsernames = new LinkedHashSet<>();
}
