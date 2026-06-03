package com.ust.pos.api.token;
import com.ust.pos.config.JWTUtility;
import com.ust.pos.dto.UserDto;
import com.ust.pos.model.UserRepository;
import com.ust.pos.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;                                    // ← add this
import org.springframework.http.ResponseEntity;                               // ← add this
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;   // ← add this
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenGenerationController {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    // ↓ Replace your old authenticate() with this
    @PostMapping("/api/authenticate")
    @ResponseBody
    public ResponseEntity<?> authenticate(@RequestBody UserDto userDto) {
        try {
            authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDto.getUsername(), userDto.getPassword()
                    )
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
            final String token = jwtUtility.generateToken(userDetails);
            return ResponseEntity.ok(new UserDto(token));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Authentication failed.");
        }
    }

    // ↓ Leave this method completely unchanged
    @PostMapping("/api/validateToken")
    @ResponseBody
    public Boolean validateToken(@RequestBody UserDto jwtRequest) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername
                    (jwtRequest.getUsername());
            return jwtUtility.validateToken(jwtRequest.getToken(), userDetails);
        } catch (Exception e) {
            return false;
        }
    }
}