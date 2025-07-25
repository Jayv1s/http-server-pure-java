package domain;

public class SimpleObject {
    String name;

    public SimpleObject() {}

    public SimpleObject(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "Name: " + name;
    }
}
