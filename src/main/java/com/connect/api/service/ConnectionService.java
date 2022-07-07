package com.connect.api.service;

import com.connect.api.dto.connection.ConnectionDto;
import com.connect.api.dto.payload.response.PageResponseDto;
import org.springframework.data.domain.Pageable;

public interface ConnectionService {
    PageResponseDto<ConnectionDto> getCurrentUserConnections(Pageable pageable);
}
