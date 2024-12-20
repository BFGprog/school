package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.InfoService;

@RestController
@RequestMapping("info")
public class InfoController {

    private InfoService infoService;
    private InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("port")
    public Integer getPort() {
        return infoService.getPort();
    }

    @GetMapping("get-time")
    public String getTimeMethod() {
        return infoService.getTimeMethod();
    }

}
