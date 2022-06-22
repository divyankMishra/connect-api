package com.connect.api.service;

import com.connect.api.dto.connection.ConnectionRequestDto;
import com.connect.api.dto.payload.request.ConnectionRequestPayloadDto;
import com.connect.api.dto.payload.response.PageResponseDto;

public interface ConnectionRequestService {
    PageResponseDto<ConnectionRequestDto> getConnectionRequests(int page, int size, String[] sort);

    PageResponseDto<ConnectionRequestDto> getSentConnectionRequests(int page, int size, String[] sort);

    ConnectionRequestDto createConnectionRequest(ConnectionRequestPayloadDto requestPayload);

    void removeConnectionRequest(Long id);

    boolean connectionRequestAction(Boolean accept, Long id);
}
