package org.example.learningprojectserver.controller;


import org.example.learningprojectserver.service.ActiveUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Learning-App/Active-User")
public class ActiveUserController {

    private final ActiveUserService activeUserService;
   @Autowired
    public ActiveUserController(ActiveUserService activeUsersService) {
        this.activeUserService = activeUsersService;
    }

    @PostMapping("/connect-user")
    public void userConnected(@RequestParam String userName) {
        activeUserService.connectUser(userName);
    }

    @PostMapping("/disconnect-user")
    public void userDisconnected(@RequestParam String userName) {
        activeUserService.disconnectUser(userName);
}
}
