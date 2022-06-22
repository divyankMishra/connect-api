package com.connect.api.service.impl;

import com.connect.api.dto.connection.ConnectionRequestDto;
import com.connect.api.dto.payload.request.ConnectionRequestPayloadDto;
import com.connect.api.dto.payload.response.PageResponseDto;
import com.connect.api.model.User;
import com.connect.api.model.connection.ConnectionRequest;
import com.connect.api.model.connection.Status;
import com.connect.api.repository.ConnectionRequestRepository;
import com.connect.api.repository.UserRepository;
import com.connect.api.service.ConnectionRequestService;
import com.connect.api.util.ConverterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ConnectionRequestServiceImpl implements ConnectionRequestService {

    ConnectionRequestRepository connectionRequestRepository;

    UserRepository userRepository;

    @Autowired
    public ConnectionRequestServiceImpl(ConnectionRequestRepository connectionRequestRepository, UserRepository userRepository) {
        this.connectionRequestRepository = connectionRequestRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ConnectionRequestDto createConnectionRequest(ConnectionRequestPayloadDto connectionRequest) {
        User sender = userRepository.findByUsername(getUsername());
        User receiver = userRepository.findByUsername(connectionRequest.getUsername());
        //TODO Under normal scenario we will not render or show user who are already connection
        // or have their connection request in database. Meaning to reach here one will have to make explicit api call to check this scenario.
        // Here once connection is implemented put connection already exists check also.
        if (connectionRequestRepository.existsBySenderAndReceiver(sender, receiver))
            throw new RuntimeException("Request already exists!");
        return ConverterUtil.getConnectionRequestDto(false, connectionRequestRepository.save(ConnectionRequest.builder()
                .createdAt(new Date())
                .sender(sender)
                .receiver(receiver)
                .status(Status.PENDING)
                .build()));
    }

    @Override
    public void removeConnectionRequest(Long id) {
        User currentUser = userRepository.findByUsername(getUsername());
        ConnectionRequest request = connectionRequestRepository.findById(id).orElseThrow();
        if (request.getSender().equals(currentUser) || request.getReceiver().equals(currentUser)) {
            connectionRequestRepository.delete(request);
            return;
        }
        throw new RuntimeException("This user is not authorized for this action");

    }

    @Override
    public PageResponseDto<ConnectionRequestDto> getConnectionRequests(int page, int size, String[] sort) {
        return getConnectionRequestDtoPageResponseDto(connectionRequestRepository.findByReceiver_Username(getUsername(), getPageable(page, size, sort)), true);
    }

    //TODO this can be reused in pagination everywhere but let's try passing pageable directly in controller param and see
    private Pageable getPageable(int page, int size, String[] sort) {
        List<Sort.Order> sortOrder = Arrays.stream(sort)
                .map(sortEl -> {
                    String[] params = sortEl.contains(";") ? sortEl.split(";") : new String[]{sortEl, "desc"};
                    return new Sort.Order(Sort.Direction.fromString(params[1]), params[0]);
                }).toList();
        Pageable pageReq = PageRequest.of(page, size, Sort.by(sortOrder));
        return pageReq;
    }

    private String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public PageResponseDto<ConnectionRequestDto> getSentConnectionRequests(int page, int size, String[] sort) {
        return getConnectionRequestDtoPageResponseDto(connectionRequestRepository.findBySender_Username(getUsername(), getPageable(page, size, sort)), false);
    }

    private PageResponseDto<ConnectionRequestDto> getConnectionRequestDtoPageResponseDto(Page<ConnectionRequest> page, boolean populateSender) {
        PageResponseDto<ConnectionRequestDto> pageResponseDto = new PageResponseDto<>(page.isLast(),
                null,
                page.getTotalElements(),
                (long) page.getNumberOfElements(),
                (long) page.getTotalPages(),
                (long) page.getNumber(),
                page.getContent().stream().map(content -> ConverterUtil.getConnectionRequestDto(populateSender, content)).toList());
        return pageResponseDto;
    }
}
