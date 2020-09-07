package sample.pluginsSerialization;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Employee;
import sample.plugins.PluginOne;
import sample.plugins.PluginTwo;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class PlugBinarySerialization implements PlugSerialization {

    @Override
    public void serialize(ObservableList<Employee> list, String fileName, String key, Class plugClass, Method method) throws Exception{
        FileOutputStream fileStream = new FileOutputStream(fileName);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        ArrayList<Employee> arrList = new ArrayList<>(list);
        objectStream.writeObject(arrList);
        byte[] plainText = byteStream.toByteArray();
        objectStream.close();
        byteStream.close();
        byte[] cipherText = (byte[])method.invoke(plugClass.getDeclaredConstructor().newInstance(), plainText, key);
        objectStream = new ObjectOutputStream(fileStream);
        objectStream.write(cipherText);
        objectStream.close();
        fileStream.close();
    }

    @Override
    public ObservableList<Employee>  deserialize (String fileName, String key) throws Exception{
        FileInputStream fileStream = new FileInputStream(fileName);
        ObjectInputStream objectStream = new ObjectInputStream(fileStream);
        byte[] cipherText = objectStream.readAllBytes();
        objectStream.close();
        fileStream.close();
        String[] tokens = fileName.split("[.]");




        String [] Name = (tokens[tokens.length-2]).split("/");
        String str = "sample.plugins." + Name[Name.length-1];




        Class<?> plugClass =Class.forName(str);
        Method method = plugClass.getDeclaredMethod("decrypt", byte[].class, String.class);
        byte[] plainText = (byte[]) method.invoke(plugClass.getDeclaredConstructor().newInstance(), cipherText, key);
        ByteArrayInputStream byteStream = new ByteArrayInputStream(plainText);
        objectStream = new ObjectInputStream(byteStream);
        ArrayList<Employee> arrList = (ArrayList<Employee>)objectStream.readObject();
        ObservableList<Employee> list = FXCollections.observableArrayList();
        list.setAll(arrList);
        objectStream.close();
        byteStream.close();
        return list;
    }


}
