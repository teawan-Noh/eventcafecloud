package com.eventcafecloud.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @GetMapping("/send")
    public MailTO sendTestMail(String email) {
        MailTO mailTO = new MailTO();

        mailTO.setAddress(email);
        mailTO.setTitle("EC2 호스트 승인 메일입니다.");
        mailTO.setMessage("호스트로 승인 되셨습니다. 지금 EC2에 접속해 카페를 등록해보세요!");

        mailService.sendMail(mailTO);

        return mailTO;
    }
}
