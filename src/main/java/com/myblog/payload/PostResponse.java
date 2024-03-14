package com.myblog.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private List<PostDto> content;
    private long pageNo;
    private long pageSize;
    private long totalElement;
    private long totalPages;
    private boolean last;


}
