package view;

import utils.Env;

public class ViewConfig { // PEUT POTENTIELLEMENT ÊTRE INTEGRÉE DANS UNE AUTRE CLASSE DE VUE
    public static final String NAME = Env.getDotenv().get("STORE_NAME");
}
