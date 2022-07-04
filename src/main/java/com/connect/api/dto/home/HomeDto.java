package com.connect.api.dto.home;

import com.connect.api.dto.group.GroupDto;
import com.connect.api.dto.post.PostDto;
import com.connect.api.dto.post.UserMinDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record HomeDto(UserMinDto user, List<PostDto> posts, List<GroupDto> groups) {
    //TODO Add Connection Dto list.
}
