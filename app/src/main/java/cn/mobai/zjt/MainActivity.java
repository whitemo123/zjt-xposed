package cn.mobai.zjt;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

public class MainActivity extends Activity {

  private EditText editText;

  private Button button;

  private DataUtil dataUtil;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    editText = (EditText) findViewById(R.id.ed_1);

    button = (Button) findViewById(R.id.bt_1);

    dataUtil = new DataUtil(this);

    if ("".equals(dataUtil.getString("uuid", ""))) {
      dataUtil.putString("uuid", getUUID());
    }

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        dataUtil.putString("key", editText.getText().toString());
      }
    });
  }

  private String getUUID() {
    return UUID.randomUUID().toString();
  }
}