package com.connect.api.dto.group;

import com.connect.api.dto.post.UserMinDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupDto implements Serializable {
    private final Long id;
    private final UserMinDto admin;
    private final Date createdAt;
    private final String name;
    private final String description;
    private List<UserMinDto> members;
}
