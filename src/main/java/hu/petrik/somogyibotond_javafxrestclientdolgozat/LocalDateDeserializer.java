package hu.petrik.somogyibotond_javafxrestclientdolgozat;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class LocalDateDeserializer implements JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        String tmpJson = json.getAsString();
        if (json.getAsString().length() >= 12) {
            tmpJson = tmpJson.substring(0,12);
        }else {
            tmpJson = tmpJson.substring(0,11);
        }
        return LocalDate.parse(tmpJson.trim(),
                DateTimeFormatter.ofPattern("MMM d, yyyy").withLocale(Locale.ENGLISH));
    }


}