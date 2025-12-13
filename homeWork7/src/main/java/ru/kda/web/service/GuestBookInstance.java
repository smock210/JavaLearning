package ru.kda.web.service;

import java.util.ArrayList;
import java.util.List;
import ru.kda.web.model.Message;

public class GuestBookInstance {
    private final List<Message> messages = new ArrayList<>();
    private static final GuestBookInstance INSTANCE = new GuestBookInstance();

    private GuestBookInstance() {
    }

    public static GuestBookInstance getInstance() {
        return INSTANCE;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }
}
