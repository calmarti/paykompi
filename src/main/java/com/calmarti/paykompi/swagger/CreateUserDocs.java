package com.calmarti.paykompi.swagger;

import com.calmarti.paykompi.common.exception.APIErrorDetails;
import com.calmarti.paykompi.domain.user.dto.CreateUserRequestDto;
import com.calmarti.paykompi.domain.user.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//TODO: replicate this template (mutatis mutandi) in rest of User endpoints and then extend for rest for resources
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Create a user",
        description = "Creates a new user ('customer' or 'merchant' type). Returns the created user with generated ID and timestamps.",
        operationId = "createUser",
        tags = {"Users"},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CreateUserRequestDto.class)
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "User created successfully",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = UserResponseDto.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad request",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = APIErrorDetails.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "409",
                        description = "User already exists (duplicate email or username)",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = APIErrorDetails.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized — missing or invalid token",
                        content = @Content(schema = @Schema(hidden = true))
                ),
//                @ApiResponse(
//                        responseCode = "403",
//                        description = "Forbidden — User has not ADMIN role",
//                        content = @Content(schema = @Schema(hidden = true))
//                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Unexpected internal server error",
                        content = @Content(schema = @Schema(hidden = true))
                )
        }

//        ,security = { @SecurityRequirement(name = "bearerAuth") }
)
public @interface CreateUserDocs {
}
