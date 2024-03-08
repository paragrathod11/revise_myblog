package com.myblog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private long id;
    @NotEmpty
    @Size(min = 2, message = "Name should have at least 2 character.")
    private String name;
    @NotEmpty
    @Email(message = "Email should have at least 12 characters.")
    private String email;
    @NotEmpty
    @Size(min = 2, message = "Body should have at least 2 characters.")
    private String body;

}
