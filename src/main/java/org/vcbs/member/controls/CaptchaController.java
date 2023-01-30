package org.vcbs.member.controls;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vcbs.member.services.CaptchaImage;

import java.io.*;


@RestController
public class CaptchaController {

    @GetMapping("/api/captcha")
    public CaptchaImage.CaptchaImageInfo captcha() throws IOException {
        return new CaptchaImage().getRandomCodeImage();
    }

}
