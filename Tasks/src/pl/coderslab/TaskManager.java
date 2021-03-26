package pl.coderslab;

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
//                    removeTask();
                    System.out.println("Value was successfully deleted");
                    break;
                case "list":
                    printTab(tasks);
                    break;
                case "exit":
                    System.out.println("Bye, bye.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
            showOption(OPTIONS);
        }
    }
    public static void showOption(String[] option){
        System.out.println("Please select an option:");
        for (String choice : option){
            System.out.println(choice);
        }
    }
    public static String[][] dowlandDataToTab(String file){
        Path path = Paths.get(file);
        if(!Files.exists(path)){
            System.out.println("File not exist");
//            Terminate JVM
            System.exit(0);
        }
        String[][] tableTasks = null;
        try {
            List<String> data = Files.readAllLines(path);
//            Create new table[][] size -> with i = data size, j = length of all elements splited by ","
            tableTasks = new String[data.size()][data.get(0).split(",").length];
//            fill the tabel tasks
            for (int i = 0; i < data.size(); i++) {
//                Create new table[] with splited parts
                String[] split = data.get(i).split(",");
//                fill the j with splited data
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
            System.out.print((i+1)+ " : ");
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



    }
}
