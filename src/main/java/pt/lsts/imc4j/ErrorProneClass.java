package pt.lsts.imc4j;

import pt.lsts.imc4j.annotations.Publishes;

import java.io.NotActiveException;

@Publishes(NotActiveException.class)
public class ErrorProneClass {

    void errorProneMethod() {

    }
}
