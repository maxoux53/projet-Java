package utils;

import io.github.cdimascio.dotenv.Dotenv;

public class Env {
    private static Dotenv dotenv;

    public static Dotenv getDotenv() {
        if (dotenv == null) {
            dotenv = Dotenv.load();
        }

        return dotenv;
    }
}
