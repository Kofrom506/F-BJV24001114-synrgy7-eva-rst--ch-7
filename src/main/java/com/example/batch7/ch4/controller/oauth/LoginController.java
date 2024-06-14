package com.example.batch7.ch4.controller.oauth;


import com.example.batch7.ch4.config.Config;
import com.example.batch7.ch4.dto.req.LoginModel;
import com.example.batch7.ch4.dto.req.RegisterModel;
import com.example.batch7.ch4.entity.oauth.User;
import com.example.batch7.ch4.repository.oauth.UserRepository;
import com.example.batch7.ch4.service.email.EmailSender;
import com.example.batch7.ch4.service.oauth.UserService;
import com.example.batch7.ch4.utils.EmailTemplate;
import com.example.batch7.ch4.utils.Response;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/login-user")
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    Config config = new Config();

    @Autowired
    public UserService serviceReq;

    @Value("${expired.token.password.minute:}")//FILE_SHOW_RUL
    private int expiredToken;

    @Autowired
    public Response response;

    @Value("${BASEURL:}")//FILE_SHOW_RUL
    private String BASEURL;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Value("${AUTHURL:}")//FILE_SHOW_RUL
    private String AUTHURL;

    @Autowired
    public RegisterController registerController;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    public EmailTemplate emailTemplate;

    @Autowired
    public EmailSender emailSender;

    @Value("${APPNAME:}")//FILE_SHOW_RUL
    private String APPNAME;



    //costum login
//    @PostMapping("")
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<Map> login(@Valid @RequestBody LoginModel objModel) {
//        Map map = serviceReq.login(objModel);
//        return new ResponseEntity<Map>(map, HttpStatus.OK);
//    }

    // membuat login dan register by google dalam satu API
    @PostMapping("/signin_google")
    @ResponseBody
    public ResponseEntity<Map> repairGoogleSigninAction(@RequestParam MultiValueMap<String, String> parameters) throws IOException {

        Map<String, Object> map123 = new HashMap<>();
        Map<String, String> map = parameters.toSingleValueMap();
        String accessToken = map.get("accessToken");

        //validasi goole
        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
        System.out.println("access_token user=" + accessToken);
        Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), new JacksonFactory(), credential).setApplicationName(
                "Oauth2").build();
        Userinfoplus profile= null;
        try {
            profile = oauth2.userinfo().get().execute();
        }catch (GoogleJsonResponseException e)
        {
            //return jika tidak valid si tokennya
            return new ResponseEntity<Map>(response.Error(e.getDetails()), HttpStatus.BAD_GATEWAY);
        }
        profile.toPrettyString();
        User user = userRepository.findOneByUsername(profile.getEmail());
        if (null != user) {
            if(!user.isEnabled()){
                RegisterModel obk = new RegisterModel();
                obk.setUsername(user.getUsername());
                registerController.sendEmailegister(obk);
                map123.put("status", "401");
                map123.put("message", "Your Account is disable. Please chek your email for activation.");
                map123.put("type", "register");
                System.out.println("masuk 2");
                return new ResponseEntity<Map>(map123, HttpStatus.OK);
            }
            for (Map.Entry<String, String> req : map.entrySet()) {
                logger.info(req.getKey());
                logger.info(req.getValue());
            }

            RegisterModel register = new RegisterModel();
            register.setUsername(profile.getEmail());
            register.setPassword(profile.getId());
            register.setFullname(profile.getName());


            String oldPassword = user.getPassword();
//            Boolean isPasswordMatches = true;
//            if (!passwordEncoder.matches(register.getPassword(), oldPassword)) {
//                userRepository.updatePassword(user.getId(), passwordEncoder.encode(register.getPassword()));
//                isPasswordMatches = false;
//            }
            if (!passwordEncoder.matches(register.getPassword(), oldPassword)) {
//                userRepository.updatePassword(user.getId(), passwordEncoder.encode(register.getPassword()));
                System.out.println("update password berhasil");
                user.setPassword(passwordEncoder.encode(register.getPassword()));
                userRepository.save(user);
//                isPasswordMatches = false;
            }
            String url = AUTHURL + "?username=" + register.getUsername() +
                    "&password=" + register.getPassword() +
                    "&grant_type=password" +
                    "&client_id=my-client-web" +
                    "&client_secret=password";
            ResponseEntity<Map> response123 = restTemplateBuilder.build().exchange(url, HttpMethod.POST, null, new
                    ParameterizedTypeReference<Map>() {
                    });

            if (response123.getStatusCode() == HttpStatus.OK) {
                userRepository.save(user);

                map123.put("access_token", response123.getBody().get("access_token"));
                map123.put("token_type", response123.getBody().get("token_type"));
                map123.put("refresh_token", response123.getBody().get("refresh_token"));
                map123.put("expires_in", response123.getBody().get("expires_in"));
                map123.put("scope", response123.getBody().get("scope"));
                map123.put("jti", response123.getBody().get("jti"));
                map123.put("status", "200");
                map123.put("message", "Success");
                map123.put("type", "login");
                System.out.println("masuk 3");
                //update old password : wajib
                user.setPassword(oldPassword);
                userRepository.save(user);
                return new ResponseEntity<Map>(map123, HttpStatus.OK);

            }
        } else {
//            register
            RegisterModel registerModel = new RegisterModel();
            registerModel.setUsername(profile.getEmail());
            registerModel.setFullname(profile.getName());
            registerModel.setPassword(profile.getId());

            ResponseEntity<Map> mapRegister = registerController.saveRegisterManual(registerModel);
            map123.put(config.getCode(), mapRegister.getBody().get("status"));
            map123.put(config.getMessage(), mapRegister.getBody().get("message"));
            map123.put("type", "register");
            map123.put("data", mapRegister.getBody().get("data"));
            System.out.println("masuk 2 register manual");
            return new ResponseEntity<Map>(map123, HttpStatus.OK);
        }
        System.out.println("masuk 1 luar ");
        return new ResponseEntity<Map>(map123, HttpStatus.OK);
    }

//    // Step 2: sendp OTP berupa URL: guna updeta enable agar bisa login:
//    @PostMapping("send-otp")//send OTP
//    public Map sendEmailegister(@RequestBody LoginModel user) {
//        String message = "Thanks, please check your email for activation.";
//
//        if (user.getUsername() == null) return response.isRequired("No email provided");
//        User found = userRepository.findOneByUsername(user.getEmail());
//        if (found == null) return response.notFound("Email not found"); //throw new BadRequest("Email not found");
//
//        String template = emailTemplate.getTalentAcc();
//        if (StringUtils.isEmpty(found.getOtp())) {
//            User search;
//            String otp;
//            do {
//                otp = SimpleStringUtils.randomString(6, true);
//                search = userRepository.findOneByOTP(otp);
//            } while (search != null);
//            Date dateNow = new Date();
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(dateNow);
//            calendar.add(Calendar.MINUTE, expiredToken);
//            Date expirationDate = calendar.getTime();
//
//            found.setOtp(otp);
//            found.setOtpExpiredDate(expirationDate);
//            template = template.replaceAll("\\{\\{USERNAME}}", (found.getUsername()));
//            template = template.replaceAll("\\{\\{VERIF_LINK}}", BASEURL + "register/web/index/" + otp);
//            userRepository.save(found);
//        } else {
//            template = template.replaceAll("\\{\\{USERNAME}}", (found.getUsername()));
//            template = template.replaceAll("\\{\\{VERIF_LINK}}", BASEURL + "register/web/index/" + found.getOtp());
//        }
//        emailSender.sendAsync(found.getUsername(), APPNAME + "- Register", template);
//        return response.Sukses(message);
//    }

}

