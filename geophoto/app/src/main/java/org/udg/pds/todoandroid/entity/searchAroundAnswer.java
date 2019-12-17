package org.udg.pds.todoandroid.entity;

import java.io.Serializable;

public class searchAroundAnswer implements Serializable {
  public String title;
  public String url;
  public String created_at;
  public Usuari user;
  public String comarca;
  public String provincia;
  public String municipi;

  public searchAroundAnswer(String s1, String s2, String s3, Usuari s4, String s5, String s6, String s7)
  {
    title=s1;
    url=s2;
    created_at=s3;
    user=s4;
    comarca=s5;
    provincia=s6;
    municipi=s7;
  }

}


