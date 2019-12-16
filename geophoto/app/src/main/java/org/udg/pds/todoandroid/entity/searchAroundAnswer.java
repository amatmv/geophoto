package org.udg.pds.todoandroid.entity;

import java.io.Serializable;

public class searchAroundAnswer implements Serializable {
  public String title;
  public String thumbnail;
  public String date_taken;
  public String user;

  public searchAroundAnswer(String s1, String s2, String s3, String s4)
  {
    title=s1;
    thumbnail=s2;
    date_taken=s3;
    user=s4;
  }

}


