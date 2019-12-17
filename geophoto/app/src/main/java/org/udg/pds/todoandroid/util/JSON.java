package org.udg.pds.todoandroid.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

public class JSON {

  public static String toJSON(Object obj) throws IOException {
    StringWriter sw = new StringWriter();
    new ObjectMapper().writeValue(sw, obj);
    return sw.toString();
  }
}
