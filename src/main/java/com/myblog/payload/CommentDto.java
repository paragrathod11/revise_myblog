package com.myblog.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor

@ApiModel(description = "Comment Model Information.")
public class CommentDto {

    @ApiModelProperty(value = "The unique identifier of the comment")
    private long id;
    @NotEmpty
    @Size(min = 2, message = "Name should have at least 2 character.")
    @ApiModelProperty(value = "Name of the commenter", example = "Abc Xyz")
    private String name;
    @NotEmpty
    @Email(message = "Email should have at least 12 characters.")
    @ApiModelProperty(value = "Email of the commenter", example = "abc.xyz@email.com")
    private String email;
    @NotEmpty
    @Size(min = 2, message = "Body should have at least 2 characters.")
    @ApiModelProperty(value = "Content/Body of the comment", example = "This is great post.")
    private String body;

    @ApiModelProperty(value = "Information about the associated post")
    private PostDto post;

    public CommentDto(long id, String name, String email, String body, PostDto post) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
        this.post = post;
    }
}
