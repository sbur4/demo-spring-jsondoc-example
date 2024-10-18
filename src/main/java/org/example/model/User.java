package org.example.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiObject(name = "User", description = "Object for user information")
//@ApiObject
public class User {
    @ApiObjectField(description = "The unique identifier of the user")
    private long id;

    @ApiObjectField(description = "The user's name", required = true)
    private String name;

    @ApiObjectField(description = "The user's creation date", required = true)
    @JsonDeserialize(as = Date.class)
    @JsonSerialize(as = Date.class)
    private Date createdAt;
}
