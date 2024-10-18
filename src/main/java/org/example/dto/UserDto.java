package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.pojo.ApiVisibility;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiObject(name = "UserDto", description = "Object for user information", visibility = ApiVisibility.PUBLIC)
public class UserDto {
    @ApiObjectField(description = "The name of the user")
    private String name;
}
