package com.connect.api.controller;

import com.connect.api.dto.group.GroupDto;
import com.connect.api.dto.payload.request.GroupPayloadDto;
import com.connect.api.dto.payload.response.PageResponseDto;
import com.connect.api.service.GroupService;
import com.connect.api.util.ErrorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    PageResponseDto<GroupDto> getGroups(@RequestParam(defaultValue = "0") Integer pageNo,
                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                        @RequestParam(defaultValue = "id") String sortBy) {
        return groupService.getGroups(pageNo, pageSize, sortBy);
    }

    @PostMapping
    ResponseEntity<Object> createGroup(@Valid @RequestBody GroupPayloadDto groupPayloadDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return ErrorUtil.bindingErrors(bindingResult);
        return new ResponseEntity<>(groupService.createGroup(groupPayloadDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    GroupDto getGroup(@PathVariable("id") Long id) {
        return groupService.getGroup(id);
    }

    @PutMapping("/{id}")
    ResponseEntity<Object> updateGroup(@Valid @RequestBody GroupPayloadDto groupPayloadDto, BindingResult bindingResult, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) return ErrorUtil.bindingErrors(bindingResult);
        return new ResponseEntity<>(groupService.updateGroup(groupPayloadDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteGroup(@PathVariable("id") Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
}
