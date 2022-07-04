package com.connect.api.service.impl;

import com.connect.api.dto.connection.ConnectionDto;
import com.connect.api.dto.payload.response.PageResponseDto;
import com.connect.api.dto.post.UserMinDto;
import com.connect.api.model.User;
import com.connect.api.model.connection.Connection;
import com.connect.api.repository.ConnectionRepository;
import com.connect.api.repository.UserRepository;
import com.connect.api.service.ConnectionService;
import com.connect.api.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository connectionRepository;

    private final UserRepository userRepository;

    @Autowired
    public ConnectionServiceImpl(ConnectionRepository connectionRepository, UserRepository userRepository) {
        this.connectionRepository = connectionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PageResponseDto<ConnectionDto> getCurrentUserConnections(Pageable pageable) {
        User currentUser = userRepository.findByUsername(Util.getCurrentUserName());
        Page<Connection> connections = connectionRepository.findConnectionsByUser(currentUser, pageable);
        return getConnectionDtoPage(connections);
    }

    private PageResponseDto<ConnectionDto> getConnectionDtoPage(Page<Connection> page) {
        return new PageResponseDto<>(page.isLast(),
                null,
                page.getTotalElements(),
                (long) page.getNumberOfElements(),
                (long) page.getTotalPages(),
                (long) page.getNumber(),
                page.getContent().stream().map(el -> new ConnectionDto(el.getId(), new UserMinDto(el.getConnection()), el.getCreatedAt())).toList());
    }

}
