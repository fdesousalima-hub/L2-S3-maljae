package fr.univparis.maljae.datamodel;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.*;
import java.net.*;

/**
 * Unit test for simple App.
 * Version must be accesssible.
 */
public class ConfigurationTest{
  /**
    This function verify that the value of the attribute called "version" of Configuration class is not empty
   */
    @Test
    public void versionMustBeAccessible(){
    /* This function verify that the value of the attribute called "version" of Configuration class is not empty */
        assertTrue(!Configuration.version.equals(""));
    }

    /**
     * Reading config.json must update the appropriate global variables.
     * This function serves to configure the global variables of Configuration Class according to the values of the data contained
     * in "config.json" and then this function compare the actual state of Configuration with the values ​​that are attaffed
     */
    @Test
    public void configParsing() throws Exception{
      /* This function serves to configure the global variables of Configuration Class according to the values of the data contained
        in "config.json" and then this function compare the actual state of Configuration with the values ​​that are attaffed */
      URL url = this.getClass().getResource("/config.json");   //  this will load the fill "config.json"
      File input = new File(url.getFile());                    //  then the global variables of Configuration
      Configuration.loadFrom(input);                          //  Class will contain the appropriate values
      assertEquals(Configuration.showDateRange(),"2017-01-19 05:00:00 - 2017-01-23 12:00:00");
      assertEquals(Configuration.getMinNbUsersPerTeam(), 3);
      assertEquals(Configuration.getMaxNbUsersPerTeam(), 4);
      assertEquals(Configuration.getDefaultNbTeamsPerSubject(), 3);
      assertEquals(Configuration.getTask(1).toString(),"1\nsome title for task 1\nhttp://somewhere.fr\nA terrific task");
      assertEquals(Configuration.getTask(2).toString(),"2\nsome title for task 2\nhttp://somewhereelse.fr\nA great task");
    }
}
