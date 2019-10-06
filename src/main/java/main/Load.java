package main;

import model.SubjectDomain;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.List;

public class Load {
    public static <T> void loadList(ResultSet rs, List<T> listT, Class<T> type) {
        try {
            while (rs.next()) {
                T t = type.getDeclaredConstructor().newInstance();
                for (Field field : type.getDeclaredFields()) {
                    field.setAccessible(true);
                    Object value = null;
                    if(!field.getType().equals(SubjectDomain.class))
                        value = rs.getObject(field.getName().toLowerCase(),field.getType());
                    else{
                        String string = rs.getObject(field.getName().toLowerCase(),String.class);
                        SubjectDomain sd = SubjectDomain.valueOf(string);
                        value = sd;
                    }
                    field.set(t, value);
                }
                listT.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
