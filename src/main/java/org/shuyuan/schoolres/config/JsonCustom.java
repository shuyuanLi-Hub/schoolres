package org.shuyuan.schoolres.config;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.Module;
import lombok.SneakyThrows;
import org.shuyuan.schoolres.domain.*;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@JsonComponent
public class JsonCustom
{
    public static class DishesSerializer extends JsonSerializer<Dishes>
    {
        @Override
        public void serialize(Dishes dishes, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException
        {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", dishes.getId());
            jsonGenerator.writeStringField("name", dishes.getName());
            jsonGenerator.writeStringField("desc", dishes.getDescription());
            jsonGenerator.writeStringField("photo", dishes.getPhoto());
            jsonGenerator.writeStringField("shop", dishes.getShop().getName());
            jsonGenerator.writeNumberField("price", dishes.getPrice());
            jsonGenerator.writeNumberField("category", dishes.getCategory());
            jsonGenerator.writeEndObject();
        }
    }

    public static class DishesDeserializer extends JsonDeserializer<Dishes>
    {

        @SneakyThrows
        @Override
        public Dishes deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException
        {
            var token = jsonParser.getCurrentToken();
            Dishes dishes = new Dishes();

            while (!token.equals(JsonToken.END_OBJECT))
            {
                if (!token.equals(JsonToken.FIELD_NAME))
                {
                    jsonParser.nextToken();
                    continue;
                }

                String fieldName = jsonParser.getCurrentName();
                var valueToken = jsonParser.nextToken();
                String value = jsonParser.getText();

                if (fieldName.equals("desc"))
                {
                    dishes.setDescription(value);
                }
                else if (fieldName.equals("shop"))
                {
                    Shop shop = new Shop();
                    shop.setName(value);
                    dishes.setShop(shop);
                }
                else
                {
                    BeanUtils.getPropertyDescriptor(Dishes.class, fieldName)
                            .getWriteMethod()
                            .invoke(dishes, value);
                }
            }
            return dishes;
        }
    }

    public static class UserSerializer extends JsonSerializer<User>
    {
        @Override
        public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException
        {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", user.getId());
            jsonGenerator.writeStringField("name", user.getName());
            jsonGenerator.writeObjectField("date", user.getDate());
            jsonGenerator.writeStringField("passwd", user.getPasswd());
            jsonGenerator.writeStringField("phone", user.getPhone());
            jsonGenerator.writeStringField("email", user.getEmail());
            jsonGenerator.writeStringField("photo", user.getPhoto());
            jsonGenerator.writeStringField("gender", String.valueOf(user.getGender()));
            if (user.getAddresses() != null)
            {
                jsonGenerator.writeFieldName("address");
                jsonGenerator.writeStartArray(user.getAddresses(), user.getAddresses().size());
                for (var addr : user.getAddresses())
                {
                    jsonGenerator.writeString(addr.getDetail());
                }
                jsonGenerator.writeEndArray();
            }
            jsonGenerator.writeEndObject();

        }
    }

    public static class UserDeserializer extends JsonDeserializer<User>
    {

        @SneakyThrows
        @Override
        public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException
        {
            var token = jsonParser.getCurrentToken();
            User user = new User();

            while (!token.equals(JsonToken.END_OBJECT))
            {
                if (!token.equals(JsonToken.FIELD_NAME))
                {
                    jsonParser.nextToken();
                    continue;
                }

                String fieldName = jsonParser.getCurrentName();
                var valueToken = jsonParser.nextToken();
                var value = jsonParser.getText();

                if (fieldName.equals("address"))
                {
                    var address = new Address();
                    address.setDetail(value);
                    user.setAddresses(List.of(address));
                }
                else
                {
                    BeanUtils.getPropertyDescriptor(User.class, fieldName)
                            .getWriteMethod()
                            .invoke(user, value);
                }
            }
            return user;
        }
    }

    public static class OrderSerializer extends JsonSerializer<Order>
    {

        @Override
        public void serialize(Order order, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException
        {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", order.getId());
            jsonGenerator.writeStringField("userName", order.getUser().getName());
            jsonGenerator.writeStringField("phone", order.getUser().getPhone());
            jsonGenerator.writeObjectField("date", order.getDate());
            jsonGenerator.writeStringField("status", order.getStatus() + "");
            if (order.getStatus() == '1')
            {
                jsonGenerator.writeStringField("rider", order.getRider().getOpenId());
            }
            jsonGenerator.writeFieldName("dishes");
            jsonGenerator.writeStartArray(order.getDishes(), order.getDishes().size());
            for (var dishes : order.getDishes())
            {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("dishesName", dishes.getDishes().getName());
                jsonGenerator.writeStringField("shopName", dishes.getShop().getName());
                jsonGenerator.writeStringField("desc", dishes.getDishes().getDescription());
                jsonGenerator.writeNumberField("price", dishes.getDishes().getPrice());
                jsonGenerator.writeNumberField("count", dishes.getCount());
                jsonGenerator.writeNumberField("shopCategory", dishes.getShop().getCategory());
                jsonGenerator.writeStringField("photo", dishes.getDishes().getPhoto());
//                jsonGenerator.writeString(dishes.getDishes().getName());
//                jsonGenerator.writeString(dishes.getShop().getName());
//                jsonGenerator.writeNumber(dishes.getDishes().getPrice());
//                jsonGenerator.writeNumber(dishes.getCount());
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
            jsonGenerator.writeStringField("address", order.getAddress());
            jsonGenerator.writeEndObject();
        }
    }

    public static class OrderDishesSerializer extends  JsonSerializer<Order.OrderDishes>
    {

        @Override
        public void serialize(Order.OrderDishes orderDishes, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException
        {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("name", orderDishes.getDishes().getName());
            jsonGenerator.writeNumberField("order_id", orderDishes.getOrder().getId());
            jsonGenerator.writeStringField("shop", orderDishes.getShop().getName());
            jsonGenerator.writeNumberField("price", orderDishes.getDishes().getPrice());
            jsonGenerator.writeNumberField("count", orderDishes.getCount());
            jsonGenerator.writeNumberField("category", orderDishes.getShop().getCategory());
            jsonGenerator.writeEndObject();
        }
    }
}
