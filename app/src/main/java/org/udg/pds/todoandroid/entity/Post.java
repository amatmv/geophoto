package org.udg.pds.todoandroid.entity;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Post implements Serializable {
  public Long id;
  public String dateCreated;
  public String text;
  public long userId;
  public List<String> tags;
  public Boolean i_like_it;
  public String images;
}

