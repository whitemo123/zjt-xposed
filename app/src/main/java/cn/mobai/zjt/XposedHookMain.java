package cn.mobai.zjt;

import android.content.Context;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XposedHookMain implements IXposedHookLoadPackage {

  @Override
  public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
    if ("com.huiruan.zjt".equals(loadPackageParam.packageName)) {
      XposedHelpers.findAndHookMethod("android.app.Application", loadPackageParam.classLoader, "attach", Context.class, new XC_MethodHook() {
        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
          super.afterHookedMethod(param);
          Context context = (Context) param.args[0];
          ClassLoader classLoader = context.getClassLoader();

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
  }
}
