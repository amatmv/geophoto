package org.udg.pds.todoandroid.entity;

import java.util.Date;

/**
 * Created by imartin on 12/02/16.
 */
public class Task {
  public Long id;
  public String text;
  public Date dateLimit;
  public Date dateCreated;
  public Boolean completed;
  public Long userId;
}
