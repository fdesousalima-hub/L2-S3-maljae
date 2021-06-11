package fr.univparis.maljae.webserver;

import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.BindException;

/**
 * Unit test for webserver.
 */
public class AppTest{
  /**
    *Setup console redirection.
  */
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams(){
	     System.setOut(new PrintStream(outContent));
	     System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams(){
	     System.setOut(originalOut);
	     System.setErr(originalErr);
    }

    @Test
    public void checkAbout(){
	     App.about();
       assertEquals("maljae server version 0.1 running.\n", outContent.toString());
    }

    @Test
    public void portAvailable(){
      try {
        ServerSocket s = new ServerSocket(8080);}
      catch (IOException e) {
        e.getMessage();
        assertTrue(false);}}
}
