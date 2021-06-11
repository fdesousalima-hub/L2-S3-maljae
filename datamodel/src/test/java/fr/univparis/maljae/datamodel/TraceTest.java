package fr.univparis.maljae.datamodel;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;
import java.io.IOException;

public class TraceTest{

  @Test
  public void versionMustBeAccessible(){
  /* This function verify that the the Trace directory exist*/
  assertTrue(!Configuration.getTraceDirectory().equals(""));
  }

  @Test
  public void traceDirectoryMustExsistAndBeADirectory(){
    try {
      File f = new File("../"+Configuration.getTraceDirectory());
      assertTrue(f.exists());
      assertTrue(f.isDirectory());}
    catch (NullPointerException e){
      e.getMessage();
      assertTrue(false);}}


}
