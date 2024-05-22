package cn.mobai.zjt;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

public class MainActivity extends Activity {

  private EditText editText;

  private Button button;

  private SharedPreferences sp;

  private String uuid;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

//    editText = (EditText) findViewById(R.id.ed_1);
//
//    button = (Button) findViewById(R.id.bt_1);
//
//    sp = getSharedPreferences("package", Context.MODE_WORLD_READABLE);
//
//    editText.setText(sp.getString("key", ""));
//
//    if ("".equals(sp.getString("uuid", ""))) {
//      uuid = getUUID();
//      sp.edit().putString("uuid", uuid).commit();
//    } else {
//      uuid = sp.getString("uuid", "");
//    }
//
//    button.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        new Thread(new Runnable() {
//          @Override
//          public void run() {
//            String res = HttpUtil.performPostRequest("https://deviinfochejc.newapp.asia/getDeviceInfo", "key="+editText.getText().toString()+"&device="+ uuid +"&ip=127");
//            System.out.println(res);
//          }
//        }).start();
////        sp.edit().putString("key", editText.getText().toString()).commit();
////        Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_LONG).show();
//      }
//    });
  }

  private String getUUID() {
    return UUID.randomUUID().toString();
  }
}