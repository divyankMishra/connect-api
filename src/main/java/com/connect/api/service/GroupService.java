package com.connect.api.service;

import com.connect.api.dto.group.GroupDto;
import com.connect.api.dto.payload.request.GroupPayloadDto;
import com.connect.api.dto.payload.response.PageResponseDto;

public interface GroupService {

    PageResponseDto<GroupDto> getGroups(Integer pageNo, Integer pageSize, String sortBy);

    GroupDto createGroup(GroupPayloadDto groupPayloadDto);

    GroupDto getGroup(Long id);

    GroupDto updateGroup(GroupPayloadDto groupPayloadDto, Long id);

    void deleteGroup(Long id);
}
