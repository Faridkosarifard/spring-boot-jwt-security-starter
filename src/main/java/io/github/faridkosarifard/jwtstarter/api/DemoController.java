package io.github.faridkosarifard.jwtstarter.api;

import io.github.faridkosarifard.jwtstarter.api.dto.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
@Tag(name = "Authorization demos")
public class DemoController {

    @Operation(summary = "Requires any authenticated user")
    @GetMapping("/authenticated")
    public MessageResponse authenticated() {
        return new MessageResponse("Reached an authenticated endpoint");
    }

    @Operation(summary = "Requires ROLE_USER")
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public MessageResponse user() {
        return new MessageResponse("Reached a ROLE_USER endpoint");
    }

    @Operation(summary = "Requires ROLE_MODERATOR or ROLE_ADMIN")
    @GetMapping("/moderator")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public MessageResponse moderator() {
        return new MessageResponse("Reached a moderator endpoint");
    }

    @Operation(summary = "Requires ROLE_ADMIN")
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public MessageResponse admin() {
        return new MessageResponse("Reached a ROLE_ADMIN endpoint");
    }

    @Operation(summary = "Requires the admin:write permission")
    @GetMapping("/admin-write")
    @PreAuthorize("hasAuthority('admin:write')")
    public MessageResponse adminWrite() {
        return new MessageResponse("Reached an admin:write endpoint");
    }
}
