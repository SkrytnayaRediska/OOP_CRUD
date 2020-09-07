package sample.pluginsSerialization;

import javafx.collections.ObservableList;
import sample.model.Employee;

import java.lang.reflect.Method;

public interface PlugSerialization {
    void serialize(ObservableList<Employee> list, String fileName, String key, Class plugClass, Method method) throws Exception;
    ObservableList<Employee> deserialize(String fileName, String key) throws Exception;
}
