package com.connect.api.controller;

import com.connect.api.dto.connection.ConnectionRequestDto;
import com.connect.api.dto.payload.request.ConnectionRequestPayloadDto;
import com.connect.api.dto.payload.request.RequestActionPayloadDto;
import com.connect.api.dto.payload.response.PageResponseDto;
import com.connect.api.service.ConnectionRequestService;
import com.connect.api.util.ErrorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/requests")
public class ConnectionRequestController {
    private final ConnectionRequestService connectionRequestService;

    @Autowired
    public ConnectionRequestController(ConnectionRequestService connectionRequestService) {
        this.connectionRequestService = connectionRequestService;
    }

    @GetMapping
    PageResponseDto<ConnectionRequestDto> getConnectionRequests(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "3") int size,
                                                                @RequestParam(defaultValue = "id") String[] sort) {
        return connectionRequestService.getConnectionRequests(page, size, sort);
    }

    @GetMapping("/sent")
    PageResponseDto<ConnectionRequestDto> getSentConnectionRequests(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "3") int size,
                                                                    @RequestParam(defaultValue = "id") String[] sort) {
        return connectionRequestService.getSentConnectionRequests(page, size, sort);
    }

    @PostMapping
    ResponseEntity<Object> createRequest(@Valid @RequestBody ConnectionRequestPayloadDto requestPayload, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return ErrorUtil.bindingErrors(bindingResult);
        return new ResponseEntity<>(connectionRequestService.createConnectionRequest(requestPayload), HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    ResponseEntity<Object> acceptRejectRequest(@Valid @RequestBody RequestActionPayloadDto action, BindingResult bindingResult, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) return ErrorUtil.bindingErrors(bindingResult);
        return connectionRequestService.connectionRequestAction(action.accept(), id) ? new ResponseEntity<>(HttpStatus.CREATED) : ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> removeRequest(@PathVariable("id") Long id) {
        connectionRequestService.removeConnectionRequest(id);
        return ResponseEntity.noContent().build();
    }
}
