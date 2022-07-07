package com.connect.api.controller;

import com.connect.api.dto.connection.ConnectionDto;
import com.connect.api.dto.payload.response.PageResponseDto;
import com.connect.api.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/me/connections")
public class MyConnectionsController {
    private final ConnectionService connectionService;

    @Autowired
    public MyConnectionsController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @GetMapping
    PageResponseDto<ConnectionDto> getMyConnections(@PageableDefault(page = 0,size = 20) @SortDefault(sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable) {
       return connectionService.getCurrentUserConnections(pageable);
    }
}
