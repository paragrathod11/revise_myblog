package com.myblog.mapper;

import com.myblog.entity.Post;
import com.myblog.payload.PostDto;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Api(tags = "Post Mapper", description = "Provides mapping between Post and PosDto")
@Component
public class PostMapper {

    private ModelMapper mapper;
    public PostMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public PostDto toDto(Post post){
        return mapper.map(post, PostDto.class);
    }

    public Post toEntity(PostDto postDto){
        return mapper.map(postDto, Post.class);
    }

}
