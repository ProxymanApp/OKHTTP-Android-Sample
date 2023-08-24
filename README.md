## OKHTTP-Android-Sample
The sample Android Project demonstrates how to use Proxyman to intercept its HTTPS Traffic.

## How to run
1. Clone and import this project with Android Studio
2. Open Proxyman -> Install the certificate to Android Emulator by following this Doc: https://docs.proxyman.io/debug-devices/android-device/automatic-script-for-android-emulator
3. Run the app on Emulator -> Click on buttons to make HTTPS Connection
4. Observe the traffic logs on Proxyman app.

## Configuration
### res/xml/network_security_config.xml
Source: https://github.com/ProxymanApp/OKHTTP-Android-Sample/blob/master/app/src/main/res/xml/network_security_config.xml

```xml
<network-security-config>
  <debug-overrides>
    <trust-anchors>
      <!-- Trust user added CAs while debuggable only -->
      <certificates src="user" />
      <certificates src="system" />
    </trust-anchors>
  </debug-overrides>

  <base-config cleartextTrafficPermitted="true">
    <trust-anchors>
      <certificates src="system" />
      <certificates src="user" />
    </trust-anchors>
  </base-config>
</network-security-config>
```

### AndroidManifest.xml
Source: https://github.com/ProxymanApp/OKHTTP-Android-Sample/blob/master/app/src/main/AndroidManifest.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="io.approov.shapes">

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher"
        android:supportsRtl="true"
        android:theme="@style/AppTheme"
        android:name="io.approov.shapes.ShapesApp"
        android:networkSecurityConfig="@xml/network_security_config"
    >
        <activity android:name=".MainActivity" android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```
