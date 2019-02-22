package pl.mmalkiew.kafka.producer.model;

import java.util.Objects;
import java.util.StringJoiner;

public class Sample {

    private final String name;

    public Sample(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Sample create(String name) {
        Objects.requireNonNull(name, "name == null");

        return new Sample(name);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Sample.class.getSimpleName() + "[", "]").add("name='" + name + "'").toString();
    }
}
