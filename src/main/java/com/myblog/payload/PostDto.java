package com.myblog.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

@ApiModel(description = "Post model information")
public class PostDto {

    @ApiModelProperty(value = "The unique identifier of the post")
    private long id;
    @NotEmpty
    @Size(min = 2, message = "Title should have at least 2 character.")
    @ApiModelProperty(value = "The title of the post", example = "My Post Title")
    private String title;
    @NotEmpty
    @Size(min = 10, message = "Description should have at least 10 character.")
    @ApiModelProperty(value = "The description of the post", example = "A detailed description of my post")
    private String description;
    @NotEmpty
    @Size(min = 5, message = "Content should have at least 5 character.")
    @ApiModelProperty(value = "The content of the post", example = "This is the main content of my post")
    private String content;

    @ApiModelProperty(value = "Set of comments associated with the post")
    private Set<CommentDto> comments;

    public PostDto(long id, String title, String description, String content) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
    }
}
