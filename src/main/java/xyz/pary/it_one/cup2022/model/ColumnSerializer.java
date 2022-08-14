package xyz.pary.it_one.cup2022.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

public class ColumnSerializer extends StdSerializer<Column> {

    public ColumnSerializer() {
        super(Column.class);
    }

    @Override
    public void serialize(Column c, JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException {
        generator.writeStartObject();
        generator.writeStringField("title", c.getTitle().toUpperCase());
        int i = c.getType().lastIndexOf("(");
        String type = i == -1 ? c.getType() : c.getType().substring(0, i);
        generator.writeStringField("type", type);
        generator.writeEndObject();
    }
}
