package cn.mobai.zjt;

import android.content.Context;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import android.hardware.Camera;
import android.util.Log;
import android.widget.Toast;

public class XposedHookMain implements IXposedHookLoadPackage {

  private String TAG_NAME = "ZJT";

  @Override
  public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
    if ("com.huiruan.zjt".equals(loadPackageParam.packageName)) {
      String ip = HttpUtil.performGetRequest("https://api.ipify.org/");

      XSharedPreferences prefs = new XSharedPreferences("cn.mobai.zjt", "ZJT-XPOSED");
      prefs.makeWorldReadable();

      String uuid = prefs.getString("uuid", "");
      String key = prefs.getString("key", "");

      XposedBridge.log("uuid：" + uuid);
      XposedBridge.log("key：" + key);

      String res = HttpUtil.performPostRequest("http://121.43.237.237:25960/getDeviceInfo", "key="+key+"&device="+uuid+"&ip="+ip);

      XposedHelpers.findAndHookMethod("android.app.Application", loadPackageParam.classLoader, "attach", Context.class, new XC_MethodHook() {
        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
          super.afterHookedMethod(param);
          Context context = (Context) param.args[0];
          ClassLoader classLoader = context.getClassLoader();

          if (res == null || "".equals(res)) {
            Toast.makeText(context, "未注册", Toast.LENGTH_LONG).show();
            return;
          }
          Toast.makeText(context, "注册成功", Toast.LENGTH_LONG).show();
          hookCheck225(classLoader);
        }
      });
    }
  }

  // versionName 2.2.5
  private void hookCheck225(ClassLoader classLoader) {
    // root
    XposedHelpers.findAndHookMethod("o3.b", classLoader, "a", new XC_MethodHook() {
      @Override
      protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        param.setResult(false);
      }
    });
    // 虚拟机
    XposedHelpers.findAndHookMethod("com.snail.antifake.deviceid.AndroidDeviceIMEIUtil", classLoader, "isRunOnEmulator", Context.class, new XC_MethodHook() {
      @Override
      protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        param.setResult(false);
      }
    });
    // vpn
    XposedHelpers.findAndHookMethod("com.huiruan.zjt.viewmodel.SecureViewModel", classLoader, "g", new XC_MethodHook() {
      @Override
      protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        param.setResult(false);
      }
    });
    // 禁止setParameters
//    XposedHelpers.findAndHookMethod("android.hardware.Camera", classLoader, "setParameters", Camera.Parameters.class, new XC_MethodReplacement() {
//      @Override
//      protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
//        return null;
//      }
//    });
    // 禁止强制前置摄像头
//    XposedHelpers.findAndHookMethod("android.hardware.Camera", classLoader, "open", int.class, new XC_MethodReplacement() {
//      @Override
//      protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
//        XposedBridge.log(String.valueOf(param.args[0]));
//        return Camera.open();
//      }
//    });
  }
}
