package sample.lists;

import sample.model.Employee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ObjectWrapper implements Serializable {
    private List<Employee> allEditions;

    public ObjectWrapper() {
    }

    public List<Employee> getAllEditions() {
        return allEditions;
    }

    public void setAllEditions(List<Employee> allEditions) {
        this.allEditions = allEditions;
    }

    public byte[] toByteArray() throws Exception{
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(this);
        byte[] byteArr = byteStream.toByteArray();
        objectStream.close();
        byteStream.close();
        return byteArr;
    }

    public static ObjectWrapper getObject(byte[] byteArr) throws Exception{
        ByteArrayInputStream byteStream = new ByteArrayInputStream(byteArr);
        ObjectInputStream objectStream = new ObjectInputStream(byteStream);
        ObjectWrapper wrapper = (ObjectWrapper)objectStream.readObject();
        objectStream.close();
        byteStream.close();
        return wrapper;
    }
}
