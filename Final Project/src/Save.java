import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Save {

    public void write(String filePath, String content) {
        try {
            byte[] contentBytes = content.getBytes();
            Path path = Paths.get(filePath);
            Files.write(path, contentBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("File saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving the file.");
        }
    }

//    public LinkedList<String> read(String filePath) {
//        try {
//            Path path = Paths.get(filePath);
//            List<String> lines = Files.readAllLines(path);
//            return new LinkedList<>(lines);
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Error reading the file.");
//            return null;
//        }
//    }

//    public int[][] read(String filePath) {
//        try {
//            Path path = Paths.get(filePath);
//            List<String> lines = Files.readAllLines(path);
//            int[][] result = new int[lines.size()][];
//            for (int i = 0; i < lines.size(); i++) {
//                String[] parts = lines.get(i).split("\\s+");
//                result[i] = new int[parts.length];
//                for (int j = 0; j < parts.length; j++) {
//                    result[i][j] = Integer.parseInt(parts[j]);
//                }
//            }
//            return result;
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Error reading the file.");
//            return null;
//        }
//    }

    public char[][] read(String filePath) {
        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);
            char[][] result = new char[lines.size()][];
            for (int i = 0; i < lines.size(); i++) {
                result[i] = lines.get(i).toCharArray();
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the file.");
            return null;
        }
    }

    public ArrayList<Integer> readIntegers(String filePath) {
        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);
            ArrayList<Integer> integers = new ArrayList<>();
            for (String line : lines) {
                String[] tokens = line.split("\\s+");
                for (String token : tokens) {
                    integers.add(Integer.parseInt(token));
                }
            }
            return integers;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the file.");
            return null;
        }
    }

    public ArrayList<ArrayList<Integer>> readIntegersArray(String filePath) {
        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);
            ArrayList<ArrayList<Integer>> allIntegers = new ArrayList<>();

            for (String line : lines) {
                String[] tokens = line.split("\\s+");
                ArrayList<Integer> integers = new ArrayList<>();
                for (String token : tokens) {
                    integers.add(Integer.parseInt(token));
                }
                allIntegers.add(integers);
            }
            return allIntegers;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the file.");
            return null;
        }
    }

}
