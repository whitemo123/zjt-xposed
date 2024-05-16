package cn.mobai.zjt;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.UUID;

public class MainActivity extends Activity {

  private EditText editText;

  private Button button;

  private LinearLayout ll1, ll2;

  private SharedPreferences sp;

  private String uuid;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    editText = (EditText) findViewById(R.id.ed_1);

    button = (Button) findViewById(R.id.bt_1);

    ll1 = (LinearLayout) findViewById(R.id.ll1);
    ll2 = (LinearLayout) findViewById(R.id.ll2);

    sp = getSharedPreferences("package", Context.MODE_WORLD_READABLE);

    editText.setText(sp.getString("key", ""));

    if ("".equals(sp.getString("uuid", ""))) {
      uuid = getUUID();
      sp.edit().putString("uuid", uuid).commit();
    } else {
      uuid = sp.getString("uuid", "");
    }

    if (!"".equals(sp.getString("key", ""))) {

    }

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getDeviceInfo();
      }
    });
  }

  public void getDeviceInfo() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          String key = "".equals(sp.getString("key", "")) ? editText.getText().toString() : sp.getString("key", "");
          String res = HttpUtil.performPostRequest("https://deviinfochejc.newapp.asia/getDeviceInfo", "key="+key+"&device="+ uuid +"&ip=127");
          JSONObject json = new JSONObject(res);
          if (json.getInt("code") == 200) {
            String time = json.getString("data");
            runOnUiThread(new Runnable() {
              @Override
              public void run() {
//                    Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_LONG).show();
                sp.edit().putString("key", editText.getText().toString()).commit();
                sp.edit().putString("time", time).commit();
                ll1.setVisibility(View.INVISIBLE);
                ll2.setVisibility(View.VISIBLE);
              }
            });
          } else {
            String err = json.getString("msg");
            System.out.println(err);
            runOnUiThread(new Runnable() {
              @Override
              public void run() {
                ll1.setVisibility(View.VISIBLE);
                ll2.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, err, Toast.LENGTH_LONG).show();
              }
            });
          }
        } catch (Exception e) {
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
          });
        }
      }
    }).start();
  }

  private String getUUID() {
    return UUID.randomUUID().toString();
  }
}