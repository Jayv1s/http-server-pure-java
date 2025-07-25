package parser;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class JsonParser<T> {
    HashMap<String, String> attributes;

    public JsonParser() {
        this.attributes = new HashMap<>();
    }

    public T parse(String body, Class<T> clazz) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String newBody = cleanUpBodyString(body);

        String[] splitedString = newBody.split(":"); //2

        for(int i = 0; i < splitedString.length; i+=2) {
            attributes.put(splitedString[i], splitedString[i+1]);
        }

        return convertJsonToObject(clazz);
    }

    private T convertJsonToObject(Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        T obj = clazz.getConstructor().newInstance();

        Field[] fields = clazz.getDeclaredFields();

        for(Field field : fields) {
            field.setAccessible(true);
            field.set(obj, this.attributes.get(field.getName()));
        }

        return obj;
    }

    private String cleanUpBodyString(String body) {
        return body.replace("{", "")
                .replace("}", "")
                .replace("\"", "");
    }
}


//{"name": "Joao"} ----->  <name, Joao>
/*
* body.replace("{", "") => "name": "Joao"}
* body.replace("}", "") => "name": "Joao"
* body..replace("\"", "") => name: Joao
*
* ["name", " Joao"]
* */
