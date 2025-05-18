package com.vincenzo.bikehub.service.rental;

import org.springframework.stereotype.Component;


@Component
public class CommandExecutor {

    public <T> T executeCommand(Command<T> command) {
        return command.execute();
    }
}
