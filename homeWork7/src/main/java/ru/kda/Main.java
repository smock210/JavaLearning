package ru.kda;

import ru.kda.web.servlets.MainWebServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        MainWebServer server = new MainWebServer(8080);
        server.start();
    }
}
