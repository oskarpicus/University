package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Program;

import java.io.File;
import java.io.IOException;

public class ProgramFactory {
    public static Program get(File file) throws IOException {
        return new ObjectMapper().readValue(file, new TypeReference<Program>() {
        });
    }
}
