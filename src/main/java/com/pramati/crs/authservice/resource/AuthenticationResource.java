
package com.pramati.crs.authservice.resource;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pramati.crs.authservice.service.AuthService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api")
public class AuthenticationResource {

	@Autowired
	private TokenEndpoint tokenEndpoint;

	@Autowired
	private AuthService authService;

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Login successfull."),
			@ApiResponse(code = 401, message = "Unauthorized access.") })
	@ApiOperation(value = "Login")
	public ResponseEntity<OAuth2AccessToken> postAccessToken(Principal principal,
			@RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
		return tokenEndpoint.postAccessToken(principal, parameters);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/logout")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Logout successfull."),
			@ApiResponse(code = 400, message = "Logout Unsuccessfull.") })
	@ApiOperation(value = "Logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {
		boolean revokeStatus = authService.userLogout(request);
		if (revokeStatus) {
			return new ResponseEntity<String>("Logged out user successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Logout is not successfull", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/validateToken", method = RequestMethod.GET)
	public Map<String, ?> validateToken(@RequestParam String accessToken) {
		try {
			return authService.checkToken(accessToken);
		} catch (InvalidTokenException e) {
			throw new InvalidTokenException(e.getMessage());
		}
	}
}
