package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.UserDto;
import org.example.model.User;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiAuthNone;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.annotation.ApiVersion;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Api(name = "User API", description = "Methods for managing users",
        visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
@ApiVersion(since = "1.0", until = "2.12")
@ApiAuthNone
@Slf4j
@RestController
@RequestMapping("/api")
public class Controller {
    private static final Set<User> users = new HashSet<>();

    @ApiMethod(
            path = "/create/{name}",
            verb = ApiVerb.POST,
            description = "Posts a user by their name",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @PostMapping("/create/{name}")
    public @ApiResponseObject ResponseEntity<User> createUser(@ApiPathParam(name = "name", description = "User name")
                                                              @PathVariable("name") String name) {
        log.info("Attempting to create a new user with name: {}", name);
        Optional<User> opUser = getUserFromUsers(name);
        User user = new User();
        if (!opUser.isPresent()) {
            user.setId(Math.abs(new SecureRandom().nextLong()));
            user.setName(name);
            user.setCreatedAt(getDateFromLocalDate());

            users.add(user);
        }

        return ResponseEntity.ok(user);
    }

    @ApiMethod(
            path = "/read/{name}",
            verb = ApiVerb.GET,
            description = "Reads a user by their name",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @GetMapping("/read/{name}")
    public @ApiResponseObject ResponseEntity<User> readUser(@ApiPathParam(name = "name", description = "User name")
                                                            @PathVariable("name") String name) {
        log.info("Attempting to get a user with name: {}", name);

        User user = users.stream()
                .filter(u -> u.getName().equals(name))
                .findFirst()

                .orElse(new User());
        return ResponseEntity.ok(user);
    }

    @ApiMethod(
            path = "/update/{oldName}/{newName}",
            verb = ApiVerb.PUT,
            description = "Puts a user by their name",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @PutMapping("/update/{oldName}/{newName}")
    public @ApiResponseObject ResponseEntity<Set<User>> updateUser(
            @ApiPathParam(name = "oldName", description = "User old name") @PathVariable("oldName") String oldName,
            @ApiPathParam(name = "newName", description = "User new name") @PathVariable("newName") String newName) {
        log.info("Attempting to update a user with name: {}", newName);
        users.stream()
                .peek(u -> {
                    if (u.getName().equals(oldName)) {
                        u.setName(newName);
                        u.setCreatedAt(getDateFromLocalDate());
                    }
                })
                .collect(Collectors.toSet());

        return ResponseEntity.ok(users);
    }

    @ApiMethod(
            path = "/delete/{name}",
            verb = ApiVerb.DELETE,
            description = "Deletes a user by their name",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @DeleteMapping("/delete/{name}")
    public @ApiResponseObject ResponseEntity<?> deleteUser(@ApiPathParam(name = "name", description = "User name")
                                                           @PathVariable("name") String name) {
        log.info("Attempting to delete a user with name: {}", name);
        Optional<User> opUser = getUserFromUsers(name);
        if (opUser.isPresent()) {
            users.remove(opUser.get());
        }

        return ResponseEntity.noContent().build();
    }

    @ApiMethod(
            path = "/patch/{oldName}/{newName}",
            verb = ApiVerb.PATCH,
            description = "Patches a user by their name",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @PatchMapping("/patch/{oldName}/{newName}")
    public @ApiResponseObject ResponseEntity<Set<User>> patchUser(
            @ApiPathParam(name = "oldName", description = "User old name") @PathVariable("oldName") String oldName,
            @ApiPathParam(name = "newName", description = "User new name") @PathVariable("newName") String newName) {
        log.info("Attempting to patch a user with name: {}", newName);
        users.stream()
                .peek(u -> {
                    if (u.getName().equals(oldName)) {
                        u.setName(newName);
                    }
                })
                .collect(Collectors.toSet());

        return ResponseEntity.ok(users);
    }

    private Optional<User> getUserFromUsers(String name) {
        return users.stream()
                .filter(u -> u.getName().equals(name))
                .findFirst();
    }

    private Date getDateFromLocalDate() {
        return Date.from(LocalDate.now()
                .atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    // dto
    @ApiMethod(
            path = "/dto",
            verb = ApiVerb.GET,
//            description = "Reads a user by their name",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @GetMapping("/dto")
    public @ApiResponseObject ResponseEntity<UserDto> dto(@ApiBodyObject @RequestBody UserDto userDTO) {
        log.info("Attempting to get a user with name: {}", userDTO);
        return ResponseEntity.ok(userDTO);
    }
}