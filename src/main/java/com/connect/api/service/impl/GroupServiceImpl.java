package com.connect.api.service.impl;

import com.connect.api.dto.group.GroupDto;
import com.connect.api.dto.payload.request.GroupPayloadDto;
import com.connect.api.dto.payload.response.PageResponseDto;
import com.connect.api.dto.post.UserMinDto;
import com.connect.api.model.group.Group;
import com.connect.api.repository.GroupRepository;
import com.connect.api.repository.UserRepository;
import com.connect.api.service.GroupService;
import com.connect.api.util.ConverterUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PageResponseDto<GroupDto> getGroups(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageRequest = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, sortBy);
        Page<Group> page = groupRepository.findAll(pageRequest);
        return new PageResponseDto<>(page.isLast(),
                null,
                page.getTotalElements(),
                (long) page.getNumberOfElements(),
                (long) page.getTotalPages(),
                (long) page.getNumber(),
                page.getContent().stream().map(ConverterUtil::getGroupDto).toList()
        );
    }

    @Override
    public GroupDto createGroup(GroupPayloadDto groupPayloadDto) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Set<String> membersToBeAdded = new HashSet<>();
        membersToBeAdded.add(currentUserName);
        if(CollectionUtils.isNotEmpty(groupPayloadDto.getMemberUsernames())) membersToBeAdded.addAll(groupPayloadDto.getMemberUsernames());
        Group group = Group.builder()
                .createdAt(new Date())
                .admin(userRepository.findByUsername(currentUserName))
                .name(groupPayloadDto.getName())
                .description(groupPayloadDto.getDescription())
                .members(userRepository.findUsersByUsernameIn(new ArrayList<>(membersToBeAdded)))
                .build();
        Group savedGroup = groupRepository.save(group);
        GroupDto groupDto = ConverterUtil.getGroupDto(savedGroup);
        groupDto.setMembers(savedGroup.getMembers().stream().map(UserMinDto::new).toList());
        return groupDto;
    }

    @Override
    public GroupDto getGroup(Long id) {
        Group group = groupRepository.findById(id).orElseThrow();
        GroupDto groupDto = ConverterUtil.getGroupDto(group);
        groupDto.setMembers(group.getMembers().stream().map(UserMinDto::new).toList());
        return groupDto;
    }
}
