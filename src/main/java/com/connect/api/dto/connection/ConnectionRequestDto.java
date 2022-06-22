package com.connect.api.dto.connection;

import com.connect.api.dto.post.UserMinDto;
import com.connect.api.model.connection.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConnectionRequestDto implements Serializable {
    private Long id;
    private UserMinDto sender;
    private UserMinDto receiver;
    private Date createdAt;
    private Status status;
}
