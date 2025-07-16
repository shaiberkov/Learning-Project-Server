package org.example.learningprojectserver.controller;


import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.service.ActiveUserService;
import org.springframework.web.bind.annotation.*;

import static org.example.learningprojectserver.constants.ControllerConstants.ActiveUser.*;

@RestController
@RequestMapping(CONNECTED_USERS_BASE_PATH)
@RequiredArgsConstructor
public class ActiveUserController {

    private final ActiveUserService activeUserService;

    @PostMapping(CONNECT_USER)
    public void userConnected(@RequestParam String userId) {
        activeUserService.connectUser(userId);

    }

    @PostMapping(DISCONNECT_USER)
    public void userDisconnected(@RequestParam String userId) {
        activeUserService.disconnectUser(userId);
}
}
