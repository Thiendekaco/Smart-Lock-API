package com.labmanager.project.rest.authentication;


import com.labmanager.project.service.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthenticationRestController {

    AuthenticationService authenticationService;

    @Autowired
    public AuthenticationRestController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @GetMapping("/authentication/privateKey")
    public ResponseEntity<String> getPrivateKeyByModelId(@RequestParam String modelId, @RequestParam String masterPassword) {
        try {
            return ResponseEntity.ok(authenticationService.getPrivateKey(modelId, masterPassword));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot get private key");
        }
    }


    @GetMapping("/authentication/qr")
    public ResponseEntity<String> createCodeQrUri(@RequestParam String modelId, @RequestParam String masterPassword) {
        try {
            return ResponseEntity.ok(authenticationService.createCodeQrUri(modelId, masterPassword));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot create code qr uri");
        }
    }



    @GetMapping("/authentication/verify")
    public ResponseEntity<Boolean> verifyCode(@RequestParam String modelId, @RequestParam String otp) {
        try {
            return ResponseEntity.ok(authenticationService.VerifyOTP(modelId, otp));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }


}
