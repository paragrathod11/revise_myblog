package com.myblog.mapper;

import com.myblog.entity.Comment;
import com.myblog.payload.CommentDto;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Api(tags = "Comment Mapper", description = "Provides mapping between Comment and CommentDto")
@Component
public class CommentMapper {

    private ModelMapper mapper;
    public CommentMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public CommentDto toDto(Comment comment){
        return mapper.map(comment, CommentDto.class);
    }

    public Comment toEntity(CommentDto commentDto){
        return mapper.map(commentDto, Comment.class);
    }

}
