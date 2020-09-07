package sample.serialization;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.*;

import java.io.*;
import java.time.LocalDate;

public class MySerialization implements Serialization {


    @Override
    public void serialize(ObservableList<Employee> list, String fileName) throws Exception{
        FileWriter writer = new FileWriter(fileName);
        for (Employee edition : list){
            writer.append(edition.writeData());
            writer.append('\n');
        }
        writer.close();
    }

    @Override
    public ObservableList<Employee>  deserialize (String fileName) throws Exception{
        FileReader reader = new FileReader(fileName);
        BufferedReader bufReader = new BufferedReader(reader);
        ObservableList<Employee> list = FXCollections.observableArrayList();
        String line = bufReader.readLine();
        while(line != null){
            String[] tokens = line.split(";");
            switch (tokens[0]) {
                case "Manager":{
                    Manager manager = new Manager(
                            tokens[3], tokens[4],
                            tokens[5], Boolean.parseBoolean(tokens[6]),
                            Manager.ManageType.valueOf(tokens[1]),
                            Integer.parseInt(tokens[2])
                    );
                    list.add(manager);
                    break;
                }
                case "Programmer":{
                    Programmer magazine = new Programmer(
                            tokens[5], tokens[6],
                            tokens[7], Boolean.parseBoolean(tokens[8]),
                            Programmer.ProgType.valueOf(tokens[1]),
                            Boolean.parseBoolean(tokens[2]),
                            Integer.parseInt(tokens[3]),
                            Programmer.Quality.valueOf(tokens[4])
                    );
                    list.add(magazine);
                    break;
                }
                case "Tester":{
                    Tester book = new Tester(
                            tokens[4], tokens[5],
                            tokens[6], Boolean.parseBoolean(tokens[7]),
                            Integer.parseInt(tokens[2]),
                            Tester.Quality.valueOf(tokens[3]),
                            Tester.TestType.valueOf(tokens[1])
                    );
                    list.add(book);
                    break;
                }
                case "Designer":{
                    Designer book = new Designer(
                            tokens[4], tokens[5],
                            tokens[6], Boolean.parseBoolean(tokens[7]),
                            Integer.parseInt(tokens[2]),
                            Designer.Quality.valueOf(tokens[3]),
                            Designer.DesignType.valueOf(tokens[1])
                    );
                    list.add(book);
                    break;
                }
            }
            line = bufReader.readLine();
        }
        reader.close();
        bufReader.close();
        return list;
    }
}
