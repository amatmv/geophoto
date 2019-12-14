package org.udg.pds.todoandroid.entity;
import android.graphics.Bitmap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PostEnvio {
  public String text;
  public Long parentPost = null;
  public Bitmap img1;
  public Bitmap img2;
  public Bitmap img3;
  public Bitmap img4;
}
