package pl.jhonylemon.memewebsite.controller.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.authentication.AuthenticationRefreshRequest;
import pl.jhonylemon.memewebsite.dto.authentication.AuthenticationResponse;
import pl.jhonylemon.memewebsite.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.Guest.GUEST_PATH+ApiPaths.Authentication.AUTHENTICATION_PATH)
@CrossOrigin
public class GuestAuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(path = ApiPaths.Authentication.REFRESH_TOKEN)
    @PreAuthorize("permitAll()")
    public ResponseEntity<AuthenticationResponse> refreshJWT(@RequestBody AuthenticationRefreshRequest request){
        return ResponseEntity.ok().body(authenticationService.refreshToken(request));
    }

}
