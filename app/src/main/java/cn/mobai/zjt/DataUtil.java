package cn.mobai.zjt;

import android.content.Context;
import android.content.SharedPreferences;

public class DataUtil {
  private static final String PREF_NAME = "ZJT-XPOSED";
  private SharedPreferences sharedPreferences;

  public DataUtil(Context context) {
    sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_WORLD_READABLE);
  }

  // 存储字符串
  public void putString(String key, String value) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(key, value);
    editor.apply();
  }

  // 获取字符串
  public String getString(String key, String defaultValue) {
    return sharedPreferences.getString(key, defaultValue);
  }

  // 存储整数
  public void putInt(String key, int value) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putInt(key, value);
    editor.apply();
  }

  // 获取整数
  public int getInt(String key, int defaultValue) {
    return sharedPreferences.getInt(key, defaultValue);
  }

  // 存储布尔值
  public void putBoolean(String key, boolean value) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(key, value);
    editor.apply();
  }

  // 获取布尔值
  public boolean getBoolean(String key, boolean defaultValue) {
    return sharedPreferences.getBoolean(key, defaultValue);
  }

  // 清除所有数据
  public void clear() {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.clear();
    editor.apply();
  }

  // 移除特定键的数据
  public void remove(String key) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.remove(key);
    editor.apply();
  }
}
