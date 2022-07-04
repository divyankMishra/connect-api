package com.connect.api.service.impl;

import com.connect.api.dto.connection.ConnectionRequestDto;
import com.connect.api.dto.payload.request.ConnectionRequestPayloadDto;
import com.connect.api.dto.payload.response.PageResponseDto;
import com.connect.api.model.User;
import com.connect.api.model.connection.Connection;
import com.connect.api.model.connection.ConnectionRequest;
import com.connect.api.model.connection.Status;
import com.connect.api.repository.ConnectionRepository;
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

    private final ConnectionRequestRepository connectionRequestRepository;

    private final ConnectionRepository connectionRepository;
    private final UserRepository userRepository;

    @Autowired
    public ConnectionRequestServiceImpl(ConnectionRequestRepository connectionRequestRepository, UserRepository userRepository, ConnectionRepository connectionRepository) {
        this.connectionRequestRepository = connectionRequestRepository;
        this.userRepository = userRepository;
        this.connectionRepository = connectionRepository;
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
    public boolean connectionRequestAction(Boolean accept, Long id) {
        ConnectionRequest connectionRequest = connectionRequestRepository.findById(id).orElseThrow();
        User user = userRepository.findByUsername(getUsername());
        if (!connectionRequest.getReceiver().equals(user))
            throw new RuntimeException("Only receiver can accept request.");
        if (accept) {
            Connection connection = Connection.builder()
                    .user(connectionRequest.getSender())
                    .connection(connectionRequest.getReceiver())
                    .createdAt(new Date())
                    .build();
            Connection reverseConnection = Connection.builder()
                    .user(connectionRequest.getReceiver())
                    .connection(connectionRequest.getSender())
                    .createdAt(new Date())
                    .build();
            //@TODO Maybe we can save only one way and during lookup we check both column.
            connectionRepository.saveAll(Arrays.asList(connection, reverseConnection));
            return true;
        }
        //@TODO Maybe I can try doing like publish events and create notifications for such scenarios. Like request accepted.
        connectionRequestRepository.delete(connectionRequest);
        return false;
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
        return PageRequest.of(page, size, Sort.by(sortOrder));
    }

    private String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public PageResponseDto<ConnectionRequestDto> getSentConnectionRequests(int page, int size, String[] sort) {
        return getConnectionRequestDtoPageResponseDto(connectionRequestRepository.findBySender_Username(getUsername(), getPageable(page, size, sort)), false);
    }

    private PageResponseDto<ConnectionRequestDto> getConnectionRequestDtoPageResponseDto(Page<ConnectionRequest> page, boolean populateSender) {
        return new PageResponseDto<>(page.isLast(),
                null,
                page.getTotalElements(),
                (long) page.getNumberOfElements(),
                (long) page.getTotalPages(),
                (long) page.getNumber(),
                page.getContent().stream().map(content -> ConverterUtil.getConnectionRequestDto(populateSender, content)).toList());
    }
}
