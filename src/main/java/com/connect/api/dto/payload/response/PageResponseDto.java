package com.connect.api.dto.payload.response;

import com.connect.api.dto.post.LikeDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResponseDto<T> {
    private boolean last;
    private Boolean likeState;
    private Long totalElementCount;
    private Long elementCount;
    private Long totalPages;
    private Long currentPage;
    private List<T> content;
}
