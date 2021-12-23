//import org.junit.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MainTest {
    Main myMain;

    @BeforeEach
    void setUp() {
        myMain = new Main();
    }

    @Test
    @DisplayName("Running basic tests")
    void basicTest() throws FileNotFoundException{
        fileReaderTest();
        fileReaderExceptionTest();
    }

    @Test
    @DisplayName("FileReader should work")
    void fileReaderTest() throws FileNotFoundException{
        ArrayList<String> ans = new ArrayList<>(Arrays.asList("test input here!",
                "snd line test input."));
        assertEquals(ans, myMain.readFile("inputData.txt"), "readFile function does not work :< ");

    }

    @Test
    @DisplayName("FileReader should throw exception")
    void fileReaderExceptionTest() {
        Throwable myException = assertThrows(FileNotFoundException.class,
                () -> myMain.readFile("inputData1.txt"),
                "FileException not thrown");
        assertEquals("File Not Found", myException.getMessage(),
                "Wrong Exception message");

    }


}