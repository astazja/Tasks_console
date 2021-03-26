package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import pl.coderslab.ConsoleColors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    static final String FILE = "Tasks/tasks.csv";
    static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    static String[][] tasks;

    public static void main(String[] args){
        tasks = dowlandDataToTab(FILE);
        showOption(OPTIONS);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String choose  = scanner.nextLine();
            switch (choose) {
                case "add":
                    addTask();
                    System.out.println("Value was sucessfully added");
                    break;
                case "remove":
                    removeTask(tasks, getNumber());
                    System.out.println("Value was successfully deleted");
                    break;
                case "list":
                    printTab(tasks);
                    break;
                case "exit":
                    saveDataToFile(FILE, tasks);
                    System.out.println(ConsoleColors.RED + "Bye, bye.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
            showOption(OPTIONS);
        }
    }
    public static void showOption(String[] option){
        System.out.println(ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET);
        for (String choice : option){
            System.out.println(choice);
        }
    }
    public static String[][] dowlandDataToTab(String file){
        Path path = Paths.get(file);
        if(!Files.exists(path)){
            System.out.println("File not exist");
//  Terminate JVM
            System.exit(0);
        }
        String[][] tableTasks = null;
        try {
            List<String> data = Files.readAllLines(path);
//  Create new table[][] size -> with i = data size, j = length of all elements splited by ","
            tableTasks = new String[data.size()][data.get(0).split(",").length];
//  Fill the table tasks
            for (int i = 0; i < data.size(); i++) {
//  Create new table[] with splited parts
                String[] split = data.get(i).split(",");
//  Fill the j with splited data
                for (int j = 0; j < split.length; j++) {
                    tableTasks[i][j] = split[j];
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return tableTasks;
    }
    public static void printTab(String[][] tasks) {
        for (int i = 0; i < tasks.length; i++) {
            System.out.print((i)+ " : ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static void addTask() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please add task description");
        String description = scan.nextLine();
        System.out.println("Please add task due date");
        String dueDate = scan.nextLine();
        System.out.println("Is your task is important: true/false");
        String isImportant = scan.nextLine();
//      Overwrite the arry
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = description;
        tasks[tasks.length - 1][1] = dueDate;
        tasks[tasks.length - 1][2] = isImportant;
    }
    public static int getNumber() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please select number to remove");
        String number = scan.nextLine();
        while (!isNumberGreatherEqualZero(number)){
            System.out.println("The given value is not a number. Please give me a number greater or equal zero.");
            number = scan.nextLine();
        }
        return Integer.parseInt(number);
    }
    public static void removeTask(String[][] table, int index) {
        try {
            tasks = ArrayUtils.remove(table, (index));
        }catch (IndexOutOfBoundsException e){
            System.out.println("Element not exist in table");
        }
    }
    public static boolean isNumberGreatherEqualZero(String input){
        if(NumberUtils.isParsable(input)) {
            return Integer.parseInt(input) >= 0;
        }
        return false;
    }
    public static void saveDataToFile(String file, String[][] table) {
        Path path = Paths.get(file);
        String[] row = new String[tasks.length];
        for (int i = 0; i < table.length; i++) {
            row[i] = String.join(",", table[i]);
        }
        try{
            Files.write(path, Arrays.asList(row));
        }catch (IOException e){
            System.out.println("Nie można zapisać pliku");
        }
    }
}
