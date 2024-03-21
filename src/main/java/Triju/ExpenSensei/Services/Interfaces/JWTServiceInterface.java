package Triju.ExpenSensei.Services.Interfaces;

import Triju.ExpenSensei.Entities.User.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTServiceInterface {

    String generateToken(User user);

    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails user);

    String extractUsername(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}
