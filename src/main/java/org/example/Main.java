package org.example;

public class Main {

    private static final String BOT_TOKEN = "5808415408:AAGwAgxApnrZ1Yh-mwCqauFksyIfZqjKXo4";
    public static void main(String[] args) {
        TelegramBotApplication application = TelegramBotApplication.builder()
                .botToken(BOT_TOKEN)
                .build();

        application.run();
    }
}