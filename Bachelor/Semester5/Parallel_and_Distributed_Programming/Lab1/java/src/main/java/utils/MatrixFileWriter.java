package utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class MatrixFileWriter {
    public void write(String destination, Integer[][] matrix) {
        try (PrintWriter writer = new PrintWriter(destination, StandardCharsets.UTF_8)) {
            for (Integer[] integers : matrix) {
                for (Integer integer : integers) {
                    writer.write(integer + " ");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
