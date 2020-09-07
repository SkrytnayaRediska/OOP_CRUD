package sample.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import sample.model.*;
import sample.pluginsSerialization.PlugBinarySerialization;
import sample.pluginsSerialization.PlugJsonSerialization;
import sample.pluginsSerialization.PlugMySerialization;
import sample.serialization.BinarySerialization;
import sample.serialization.JsonSerialization;
import sample.serialization.MySerialization;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

import static sample.model.Engineer.Quality.*;
import static sample.model.Manager.ManageType.*;
import static sample.model.Programmer.ProgType.*;
import static sample.model.Tester.TestType.*;
import static sample.model.Designer.DesignType.*;

public class MainController {
    public static final String DATE = "startDate";
    private ObservableList<Employee> editionData = FXCollections.observableArrayList();

    @FXML
    private TableView<Employee> tableEditions;
    @FXML
    private TableColumn<Employee, String> nameColumn;
    @FXML
    private TableColumn<Employee, String> emailColumn;
    @FXML
    private TableColumn<Employee, Boolean> remoteWorkColumn;
    @FXML
    private TableColumn<Employee, Engineer.Quality> qualityColumn;
    @FXML
    private TableColumn<Employee, LocalDate> dateColumn;
    @FXML
    Label startWorkDateLabel;

    @FXML
    Label manageLabel;

    @FXML
    Label typeLabel;

    @FXML
    Label educationLabel;

    @FXML
    Label authorLabel;

    @FXML
    Label pageNumLabel;

    @FXML
    Label officeLabel;

    @FXML
    Label qualityLabel;

    @FXML
    Label sphereLabel;

    @FXML
    DatePicker startWorkDatePicker;

    @FXML
    ComboBox<String> plugComboBox;

    @FXML
    TextField keyTextField;

    @FXML
    ComboBox<Manager.ManageType> manageComboBox;

    @FXML
    ComboBox<Programmer.ProgType> typeComboBox;

    @FXML
    ComboBox<Designer.Quality> qualityComboBox;

    @FXML
    ComboBox<Tester.TestType> sphereComboBox;

    @FXML
    ComboBox<Designer.DesignType> DesignerComboBox;


    @FXML
    TextField officeTextField;

    @FXML
    TextField pageNumTextField;

    @FXML
    CheckBox educationCheckBox;

    @FXML
    TextField authorTextField;

    @FXML
    ComboBox<String> classComboBox;

    @FXML
    TextField nameTextField;

    @FXML
    TextField emailTextField;

    @FXML
    CheckBox remoteWorkCheckBox;

    @FXML
    private ObservableList<String> classes = FXCollections.observableArrayList("Programmer", "Manager", "Designer", "Tester");

    @FXML
    private ObservableList<Engineer.Quality> qualities = FXCollections.observableArrayList(Senior, Middle, Junior);

    @FXML
    private ObservableList<Manager.ManageType> manages = FXCollections.observableArrayList(AccountManager, SalesManager, FinanceManager, AdvertasingManager, ProjectManager);

    @FXML
    private ObservableList<Programmer.ProgType> types = FXCollections.observableArrayList(Backend, Frontend, FullStack, Programmer.ProgType.Web, Game_Developer, Android, iOS);

    @FXML
    private ObservableList<Designer.DesignType> des_types = FXCollections.observableArrayList(Game, Designer.DesignType.Web, Graphic);


    @FXML
    private ObservableList<Tester.TestType> spheres = FXCollections.observableArrayList(Automated, Manual);

    Class<?>[] plugins;


    public void CheckStr(String oldValue, String newValue, TextField d) {
        String regDate = "[a-zA-Z]*";
        Pattern pattern = Pattern.compile(regDate);
        if (!pattern.matcher(newValue).matches())
            d.setText(oldValue);
        else
            d.setText(newValue);
    }

    public void CheckEmail(String oldValue, String newValue, TextField d) {
        String regDate = "[a-zA-Z0-9@-_\\.]*";
        Pattern pattern = Pattern.compile(regDate);
        if (!pattern.matcher(newValue).matches())
            d.setText(oldValue);
        else
            d.setText(newValue);
    }

    public void CheckInt(String oldValue, String newValue, TextField d) {
        String regDate = "([1-9]+[0-9]*)|(^)";
        Pattern pattern = Pattern.compile(regDate);
        if (!pattern.matcher(newValue).matches())
            d.setText(oldValue);
        else
            d.setText(newValue);
    }

    @FXML
    private void deserializeList(ActionEvent actionEvent) throws Exception{
        String key = keyTextField.getText();
        ObservableList<String> pluginList = plugComboBox.getItems();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Document");
        if (plugComboBox.getValue().equals("None")) {
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Binary files", "*.dat"),
                    new FileChooser.ExtensionFilter("JSON Documents", "*.json"),
                    new FileChooser.ExtensionFilter("My Serialization files", "*.txt")
            );
            File file = fileChooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());

            if (file != null) {
                String fileName = file.getPath();
                int index = fileName.lastIndexOf(".");
                String extension = fileName.substring(index + 1);
                if (extension.equals("dat")) {
                    BinarySerialization serialization = new BinarySerialization();
                    editionData.setAll(serialization.deserialize(fileName));
                    tableEditions.refresh();
                } else if (extension.equals("json")) {
                    JsonSerialization serialization = new JsonSerialization();
                    editionData.setAll(serialization.deserialize(fileName));
                    tableEditions.refresh();
                } else if (extension.equals("txt")) {
                    MySerialization serialization = new MySerialization();
                    editionData.setAll(serialization.deserialize(fileName));
                    tableEditions.refresh();
                }
            }
        }
        else{
            if (!key.isEmpty()) {
                ObservableList<FileChooser.ExtensionFilter> filters = FXCollections.observableArrayList();
                for (int i = 1; i < pluginList.size(); i++) {
                    filters.add(new FileChooser.ExtensionFilter("My Serialization files", "*." + pluginList.get(i) + ".txt"));
                    filters.add(new FileChooser.ExtensionFilter("Binary Files", "*." + pluginList.get(i) + ".dat"));
                    filters.add(new FileChooser.ExtensionFilter("JSON Documents", "*." + pluginList.get(i) + ".json"));
                }
                fileChooser.getExtensionFilters().addAll(filters);
                File file = fileChooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());

                if (file != null) {
                    String fileName = file.getPath();
                    int index = fileName.lastIndexOf(".");
                    String extension = fileName.substring(index + 1);
                    if (extension.equals("dat")) {
                        PlugBinarySerialization serialization = new PlugBinarySerialization();
                        editionData.setAll(serialization.deserialize(fileName, key));
                        tableEditions.refresh();
                    } else if (extension.equals("json")) {
                        PlugJsonSerialization serialization = new PlugJsonSerialization();
                        editionData.setAll(serialization.deserialize(fileName, key));
                        tableEditions.refresh();
                    } else if (extension.equals("txt")) {
                        PlugMySerialization serialization = new PlugMySerialization();
                        editionData.setAll(serialization.deserialize(fileName, key));
                        tableEditions.refresh();
                    }
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please, enter the key!");
                alert.showAndWait();
            }
        }
    }


    @FXML
    private void serializeList(ActionEvent actionEvent) throws Exception {
        ObservableList<String> pluginList = plugComboBox.getItems();
        int selectedPlugin = pluginList.indexOf(plugComboBox.getValue());
        String key = keyTextField.getText();
        if (!editionData.isEmpty()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Document");
            if (plugComboBox.getValue().equals("None")) {
                System.out.println("HEREHEREEEEEEEEEEEEEEEEEEE");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Binary files", "*.dat"),
                        new FileChooser.ExtensionFilter("JSON Documents", "*.json"),
                        new FileChooser.ExtensionFilter("My Serialization files", "*.txt")
                );
                File file = fileChooser.showSaveDialog(((Node) actionEvent.getSource()).getScene().getWindow());
                if (file != null) {
                    String fileName = file.getPath();
                    int index = fileName.lastIndexOf(".");
                    String extension = fileName.substring(index + 1);
                    if (extension.equals("dat")) {
                        BinarySerialization serialization = new BinarySerialization();
                        serialization.serialize(editionData, fileName);
                    } else if (extension.equals("json")) {
                        JsonSerialization serialization = new JsonSerialization();
                        serialization.serialize(editionData, fileName);
                    } else if (extension.equals("txt")) {
                        MySerialization serialization = new MySerialization();
                        serialization.serialize(editionData, fileName);
                    }
                }
            }
            else{
                if (!key.isEmpty()) {
                    Class<?> plugClass = plugins[selectedPlugin - 1];
                    Method method = plugClass.getDeclaredMethod("encrypt", byte[].class, String.class);
                    String secExt = plugComboBox.getValue();
                    fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("Binary files", "*." + secExt + ".dat"),
                            new FileChooser.ExtensionFilter("JSON Documents", "*." + secExt + ".json"),
                            new FileChooser.ExtensionFilter("My Serialization files", "*." + secExt + ".txt")
                    );
                    File file = fileChooser.showSaveDialog(((Node) actionEvent.getSource()).getScene().getWindow());
                    if (file != null) {
                        String fileName = file.getPath();
                        int index = fileName.lastIndexOf(".");
                        String extension = fileName.substring(index + 1);
                        if (extension.equals("dat")) {
                            PlugBinarySerialization serialization = new PlugBinarySerialization();
                            serialization.serialize(editionData, fileName, key, plugClass, method);
                        } else if (extension.equals("json")) {
                            PlugJsonSerialization serialization = new PlugJsonSerialization();
                            serialization.serialize(editionData, fileName, key, plugClass, method);
                        } else if (extension.equals("txt")) {
                            PlugMySerialization serialization = new PlugMySerialization();
                            serialization.serialize(editionData, fileName, key, plugClass, method);
                        }
                    }
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Please, enter the key!");
                    alert.showAndWait();
                }
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("List is empty!");
            alert.showAndWait();
        }
    }





    @FXML
    private void initialize() {
        initData();
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getAdditionalInfo().getEmail()));
        remoteWorkColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getAdditionalInfo().isremote_work()));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>(DATE));
        qualityColumn.setCellValueFactory(new PropertyValueFactory<>("quality"));
        tableEditions.setItems(editionData);
        classComboBox.setItems(classes);
        qualityComboBox.setItems(qualities);
        sphereComboBox.setItems(spheres);
        typeComboBox.setItems(types);
        DesignerComboBox.setItems(des_types);
        manageComboBox.setItems(manages);
        classComboBox.getSelectionModel().select("Programmer");
        manageLabel.setVisible(false);
        manageComboBox.setVisible(false);
        typeLabel.setVisible(true);
        typeComboBox.setVisible(true);
        educationLabel.setVisible(true);
        educationCheckBox.setVisible(true);
        authorLabel.setVisible(false);
        authorTextField.setVisible(false);
        pageNumLabel.setVisible(false);
        pageNumTextField.setVisible(false);
        officeLabel.setVisible(true);
        officeTextField.setVisible(true);
        qualityLabel.setVisible(true);
        qualityComboBox.setVisible(true);
        sphereLabel.setVisible(false);
        DesignerComboBox.setVisible(false);
        sphereComboBox.setVisible(false);
        {
            nameTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                CheckStr(oldValue, newValue, nameTextField);
            });
            emailTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                CheckEmail(oldValue, newValue, emailTextField);
            });
            authorTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                CheckStr(oldValue, newValue, authorTextField);
            });
            pageNumTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                CheckInt(oldValue, newValue, pageNumTextField);
            });
            officeTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                CheckInt(oldValue, newValue, officeTextField);
            });
        }
    }

    private void initData()
    {
        editionData.add(new Programmer("Ivan", LocalDate.of(1945, 4, 15).toString(),"ivanov@gmail.com", true, Backend,true, 123, Junior));
    }

    @FXML
    private void showRow()
    {
        Employee edition = tableEditions.getSelectionModel().getSelectedItem();
        if (edition.getClass() == Programmer.class){
            classComboBox.setValue("Programmer");
            nameTextField.setText(edition.getName());
            emailTextField.setText(edition.getAdditionalInfo().getEmail());
            remoteWorkCheckBox.setSelected(edition.getAdditionalInfo().isremote_work());
            startWorkDatePicker.setValue(LocalDate.parse(edition.getStartDate()));
            manageLabel.setVisible(false);
            manageComboBox.setVisible(false);
            typeLabel.setVisible(true);
            typeComboBox.setVisible(true);
            educationLabel.setVisible(true);
            educationCheckBox.setVisible(true);
            educationCheckBox.setSelected(((Programmer) edition).isHigher_education());
            authorLabel.setVisible(false);
            authorTextField.setVisible(false);
            pageNumLabel.setVisible(false);
            pageNumTextField.setVisible(false);
            officeLabel.setVisible(true);
            officeTextField.setVisible(true);
            qualityLabel.setVisible(true);
            qualityComboBox.setVisible(true);
            sphereLabel.setVisible(false);
            DesignerComboBox.setVisible(false);
            sphereComboBox.setVisible(false);
            officeTextField.setText(Integer.toString(((Programmer)edition).getOffice(), 10));
            qualityComboBox.setValue(((Programmer)edition).getQuality());
            typeComboBox.setValue(((Programmer)edition).getProgType());
        }
        if (edition.getClass() == Manager.class){
            classComboBox.setValue("Manager");
            nameTextField.setText(edition.getName());
            emailTextField.setText(edition.getAdditionalInfo().getEmail());
            remoteWorkCheckBox.setSelected(edition.getAdditionalInfo().isremote_work());
            startWorkDatePicker.setValue(LocalDate.parse(edition.getStartDate()));
            manageLabel.setVisible(false);
            manageComboBox.setVisible(true);
            typeLabel.setVisible(false);
            typeComboBox.setVisible(false);
            educationLabel.setVisible(false);
            educationCheckBox.setVisible(false);
            authorLabel.setVisible(false);
            authorTextField.setVisible(false);
            pageNumLabel.setVisible(false);
            pageNumTextField.setVisible(false);
            officeLabel.setVisible(true);
            officeTextField.setVisible(true);
            qualityLabel.setVisible(false);
            qualityComboBox.setVisible(false);
            sphereLabel.setVisible(true);
            sphereComboBox.setVisible(false);
            manageComboBox.setValue(((Manager)edition).getManageType());
            officeTextField.setText(Integer.toString(((Manager)edition).getOffice(), 10));
            DesignerComboBox.setVisible(false);
        }
        if (edition.getClass() == Designer.class){
            classComboBox.setValue("Designer");
            nameTextField.setText(edition.getName());
            emailTextField.setText(edition.getAdditionalInfo().getEmail());
            remoteWorkCheckBox.setSelected(edition.getAdditionalInfo().isremote_work());
            startWorkDatePicker.setValue(LocalDate.parse(edition.getStartDate()));
            manageLabel.setVisible(false);
            manageComboBox.setVisible(false);
            typeLabel.setVisible(false);
            typeComboBox.setVisible(false);
            educationLabel.setVisible(false);
            educationCheckBox.setVisible(false);
            authorLabel.setVisible(false);
            authorTextField.setVisible(false);
            pageNumLabel.setVisible(false);
            pageNumTextField.setVisible(false);
            officeLabel.setVisible(true);
            officeTextField.setVisible(true);
            qualityLabel.setVisible(true);
            qualityComboBox.setVisible(true);
            sphereLabel.setVisible(true);
            sphereComboBox.setVisible(false);
            DesignerComboBox.setVisible(true);
            DesignerComboBox.setValue(((Designer)edition).getType());
            officeTextField.setText(Integer.toString(((Designer)edition).getOffice(), 10));
            qualityComboBox.setValue(((Designer)edition).getQuality());
        }
        if (edition.getClass() == Tester.class){
            classComboBox.setValue("Tester");
            nameTextField.setText(edition.getName());
            emailTextField.setText(edition.getAdditionalInfo().getEmail());
            remoteWorkCheckBox.setSelected(edition.getAdditionalInfo().isremote_work());
            startWorkDatePicker.setValue(LocalDate.parse(edition.getStartDate()));
            manageLabel.setVisible(false);
            manageComboBox.setVisible(false);
            typeLabel.setVisible(false);
            typeComboBox.setVisible(false);
            educationLabel.setVisible(false);
            educationCheckBox.setVisible(false);
            authorLabel.setVisible(false);
            authorTextField.setVisible(false);
            pageNumLabel.setVisible(false);
            pageNumTextField.setVisible(false);
            officeLabel.setVisible(true);
            officeTextField.setVisible(true);
            qualityLabel.setVisible(true);
            qualityComboBox.setVisible(true);
            sphereLabel.setVisible(true);
            sphereComboBox.setVisible(true);
            sphereComboBox.setValue(((Tester)edition).getType());
            DesignerComboBox.setVisible(false);
            officeTextField.setText(Integer.toString(((Tester)edition).getOffice(), 10));
            qualityComboBox.setValue(((Tester)edition).getQuality());
        }
    }

    @FXML
    private void deleteRow()
    {
        Employee edition = tableEditions.getSelectionModel().getSelectedItem();
        if (edition == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nothing selected!");
            alert.showAndWait();
        }
        else{
            int selectedIndex = tableEditions.getSelectionModel().getSelectedIndex();
            editionData.remove(selectedIndex);
            tableEditions.refresh();
        }
    }

    @FXML
    private void generateFields() {
        switch (classComboBox.getValue()) {
            case "Programmer": {
                nameTextField.setText("");
                emailTextField.setText("");
                remoteWorkCheckBox.setSelected(false);
                startWorkDatePicker.setValue(null);
                manageLabel.setVisible(false);
                manageComboBox.setVisible(false);
                typeLabel.setVisible(true);
                typeComboBox.setVisible(true);
                educationLabel.setVisible(true);
                educationCheckBox.setVisible(true);
                authorLabel.setVisible(false);
                authorTextField.setVisible(false);
                pageNumLabel.setVisible(false);
                pageNumTextField.setVisible(false);
                officeLabel.setVisible(true);
                officeTextField.setVisible(true);
                qualityLabel.setVisible(true);
                qualityComboBox.setVisible(true);
                sphereLabel.setVisible(false);
                sphereComboBox.setVisible(false);
                DesignerComboBox.setVisible(false);
                authorTextField.setText("");
                pageNumTextField.setText("");
                officeTextField.setText("");
                qualityComboBox.setValue(null);
                break;
            }
            case "Designer": {
                nameTextField.setText("");
                emailTextField.setText("");
                remoteWorkCheckBox.setSelected(false);
                startWorkDatePicker.setValue(null);
                manageLabel.setVisible(false);
                manageComboBox.setVisible(false);
                typeLabel.setVisible(false);
                typeComboBox.setVisible(false);
                educationLabel.setVisible(false);
                educationCheckBox.setVisible(false);
                authorLabel.setVisible(false);
                authorTextField.setVisible(false);
                pageNumLabel.setVisible(false);
                pageNumTextField.setVisible(false);
                officeLabel.setVisible(true);
                officeTextField.setVisible(true);
                qualityLabel.setVisible(true);
                qualityComboBox.setVisible(true);
                sphereLabel.setVisible(true);
                sphereComboBox.setVisible(false);
                DesignerComboBox.setVisible(true);
                DesignerComboBox.setValue(null);
                authorTextField.setText("");
                pageNumTextField.setText("");
                officeTextField.setText("");
                qualityComboBox.setValue(null);
                break;
            }
            case "Manager": {
                nameTextField.setText("");
                emailTextField.setText("");
                remoteWorkCheckBox.setSelected(false);
                startWorkDatePicker.setValue(null);
                manageLabel.setVisible(false);
                manageComboBox.setVisible(true);
                typeLabel.setVisible(false);
                typeComboBox.setVisible(false);
                educationLabel.setVisible(false);
                educationCheckBox.setVisible(false);
                authorLabel.setVisible(false);
                authorTextField.setVisible(false);
                pageNumLabel.setVisible(false);
                pageNumTextField.setVisible(false);
                officeTextField.setVisible(true);
                officeTextField.setText("");
                officeLabel.setVisible(true);
                qualityLabel.setVisible(false);
                qualityComboBox.setVisible(false);
                sphereLabel.setVisible(true);
                sphereComboBox.setVisible(false);
                manageComboBox.setValue(null);
                DesignerComboBox.setVisible(false);
                break;
            }
            case "Tester": {
                nameTextField.setText("");
                emailTextField.setText("");
                remoteWorkCheckBox.setSelected(false);
                startWorkDatePicker.setValue(null);
                manageLabel.setVisible(false);
                manageComboBox.setVisible(false);
                typeLabel.setVisible(false);
                typeComboBox.setVisible(false);
                educationLabel.setVisible(false);
                educationCheckBox.setVisible(false);
                authorLabel.setVisible(false);
                authorTextField.setVisible(false);
                pageNumLabel.setVisible(false);
                pageNumTextField.setVisible(false);
                officeLabel.setVisible(true);
                officeTextField.setVisible(true);
                qualityLabel.setVisible(true);
                qualityComboBox.setVisible(true);
                sphereLabel.setVisible(true);
                sphereComboBox.setVisible(true);
                sphereComboBox.setValue(null);
                DesignerComboBox.setVisible(false);
                authorTextField.setText("");
                pageNumTextField.setText("");
                officeTextField.setText("");
                qualityComboBox.setValue(null);
                break;
            }
        }
    }

    @FXML
    public void saveNewRec()
    {
        Employee edition = tableEditions.getSelectionModel().getSelectedItem();
        if (edition == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nothing selected!");
            alert.showAndWait();
        }
        else {
            String name = nameTextField.getText();
            String email = emailTextField.getText();
            boolean takeawayPermission = remoteWorkCheckBox.isSelected();
            LocalDate startDate = startWorkDatePicker.getValue();
            switch (classComboBox.getValue()) {
                case "Programmer": {
                    Programmer.ProgType type = typeComboBox.getValue();
                    boolean isColorful = educationCheckBox.isSelected();
                    Programmer.Quality quality = qualityComboBox.getValue();
                    if ((name.equals("")) || (email.equals("")) || (quality == null) || (startDate == null) || (type == null) || (officeTextField.getText().equals(""))){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Please, enter all information!!!");
                        alert.showAndWait();
                    }
                    else {
                        int rage = Integer.parseInt(officeTextField.getText());
                        edition.setName(name);
                        edition.getAdditionalInfo().setEmail(email);
                        edition.getAdditionalInfo().setRemote_work(takeawayPermission);
                        edition.setStartDate(startDate.toString());
                        ((Programmer) edition).setType(type);
                        ((Programmer) edition).setColorful(isColorful);

                        ((Programmer) edition).setQuality(quality);
                        ((Programmer) edition).setOffice(rage);
                    }
                    break;
                }


                case "Manager": {
                    Manager.ManageType manageType = manageComboBox.getValue();
                    if ((name.equals("")) || (email.equals("")) || (startDate == null) || (manageType == null)){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Please, enter all information!!!");
                        alert.showAndWait();
                    }
                    else {
                        int rage = Integer.parseInt(officeTextField.getText());
                        edition.setName(name);
                        edition.getAdditionalInfo().setEmail(email);
                        edition.getAdditionalInfo().setRemote_work(takeawayPermission);
                        edition.setStartDate(startDate.toString());
                        ((Manager) edition).setManageType(manageType);
                        ((Manager) edition).setOffice(rage);
                    }
                    break;
                }
                case "Designer": {
                    Designer.DesignType type = DesignerComboBox.getValue();
                    Designer.Quality quality = qualityComboBox.getValue();
                    if ((name.equals("")) || (email.equals("")) || (quality == null) || (startDate == null) || (type == null) || (officeTextField.getText().equals(""))){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Please, enter all information!!!");
                        alert.showAndWait();
                    }
                    else {
                        int rage = Integer.parseInt(officeTextField.getText());
                        edition.setName(name);
                        edition.getAdditionalInfo().setEmail(email);
                        edition.getAdditionalInfo().setRemote_work(takeawayPermission);
                        edition.setStartDate(startDate.toString());
                        ((Designer) edition).setType(type);

                        ((Designer) edition).setQuality(quality);
                        ((Designer) edition).setOffice(rage);
                    }
                    break;
                }
                case "Tester": {
                    Tester.TestType type = sphereComboBox.getValue();
                    if ((name.equals("")) || (email.equals("")) || (startDate == null) || (officeTextField.getText().equals("")) || (type == null)){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Please, enter all information!!!");
                        alert.showAndWait();
                    }
                    else {
                        int rage = Integer.parseInt(officeTextField.getText());
                        edition.setName(name);
                        edition.getAdditionalInfo().setEmail(email);
                        edition.getAdditionalInfo().setRemote_work(takeawayPermission);
                        edition.setStartDate(startDate.toString());
                        ((Tester) edition).setType(type);
                        ((Tester) edition).setOffice(rage);
                    }
                    break;
                }
            }
        }
        tableEditions.refresh();
    }

    @FXML
    public void createNewRec()
    {
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        boolean takeawayPermission = remoteWorkCheckBox.isSelected();
        LocalDate startDate = startWorkDatePicker.getValue();
        switch (classComboBox.getValue()) {
            case "Programmer": {
                Programmer.ProgType type = typeComboBox.getValue();
                Designer.Quality quality = qualityComboBox.getValue();
                boolean isColorful = educationCheckBox.isSelected();
                if ((name.equals("")) || (email.equals("")) || (startDate == null) || (type == null) || (officeTextField.getText().equals("")) || (quality == null)){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Please, enter all information!!!");
                    alert.showAndWait();
                }
                else {
                    int rage = Integer.parseInt(officeTextField.getText());
                    Programmer newspaper = new Programmer(name, startDate.toString(), email, takeawayPermission, type, isColorful, rage, quality);
                    editionData.add(newspaper);
                }
                break;
            }

            case "Manager":{
                Manager.ManageType manageType = manageComboBox.getValue();
                int rage = Integer.parseInt(officeTextField.getText());
                if ((name.equals("")) || (email.equals("")) || (startDate == null) || (manageType == null)){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Please, enter all information!!!");
                    alert.showAndWait();
                }
                else {
                    Manager manager = new Manager(name, startDate.toString(), email, takeawayPermission, manageType, rage);
                    editionData.add(manager);
                }
                break;
            }
            case "Designer":{
                Designer.Quality quality = qualityComboBox.getValue();
                Designer.DesignType type = DesignerComboBox.getValue();
                if ((name.equals("")) || (email.equals("")) || (startDate == null) || (officeTextField.getText().equals("")) || (quality == null)){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Please, enter all information!!!");
                    alert.showAndWait();
                }
                else {
                    int rage = Integer.parseInt(officeTextField.getText());
                    Designer fictionBook = new Designer(name, startDate.toString(), email, takeawayPermission, rage, quality, type);
                    editionData.add(fictionBook);
                }
                break;
            }
            case "Tester":{
                Tester.Quality quality = qualityComboBox.getValue();
                Tester.TestType type = sphereComboBox.getValue();
                if ((name.equals("")) || (email.equals("")) || (startDate == null) || (officeTextField.getText().equals("")) || (type == null) || (quality == null)){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Please, enter all information!!!");
                    alert.showAndWait();
                }
                else {
                    int rage = Integer.parseInt(officeTextField.getText());
                    Tester tester = new Tester(name, startDate.toString(), email, takeawayPermission, rage, quality, type);
                    editionData.add(tester);
                }
                break;
            }
        }
        tableEditions.refresh();
    }


    @FXML
    public void onClickPlugin(Event event) throws Exception{
        plugins = getClassesFromPackage("sample/plugins/");
        ObservableList<String> pluginList = FXCollections.observableArrayList("None");
        for (Class<?> plugin : plugins) {
            pluginList.add(plugin.toString());
        }
        plugComboBox.setItems(pluginList);
    }

    public static Class<?>[] getClassesFromPackage(String packageName) throws Exception {
        List<Class<?>> list = new ArrayList<>();
        ArrayList<File> fileList = new ArrayList<>();
        Enumeration<URL> urlEnum = Thread.currentThread().getContextClassLoader().getResources(packageName);
        while (urlEnum.hasMoreElements()){
            URL url = urlEnum.nextElement();
            File dir = new File(url.getFile());
            Collections.addAll(fileList, Objects.requireNonNull(dir.listFiles()));
        }
        File[] fileArr = fileList.toArray(new File[]{});
        for (File file: fileArr){
            String fileName = file.getName();
            if (fileName.contains(".")) {
                fileName = fileName.substring(0, fileName.lastIndexOf('.'));
            }
            Class<?> newClass = Class.forName("sample.plugins." + fileName);
            list.add(newClass);
        }
        return list.toArray(new Class<?>[]{});
    }

}