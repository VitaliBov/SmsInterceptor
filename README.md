# SmsInterceptor

> Simple, easy implementation of sms code auto input for Android

> You can see the result below:

[ ![Download](https://api.bintray.com/packages/vitalibov/maven/smsinterceptor/images/download.svg) ](https://bintray.com/vitalibov/maven/smsinterceptor/_latestVersion)
[![License](http://img.shields.io/:license-mit-blue.svg)](http://doge.mit-license.org)

<p align="center">
  <img src="https://github.com/VitaliBov/SmsInterceptor/blob/master/sample.gif" width="300"/>
</p>

---

- [Usage](#usage)
- [Developed by](#developed-by)
- [License](#license)

---

## Usage

### Step 1
Just add link to repository and dependency:

#### Gradle:
```xml
implementation 'com.github.vitalibov:smsinterceptor:1.0'
```
#### Maven: 
```xml
<dependency>
  <groupId>com.github.vitalibov</groupId>
  <artifactId>smsinterceptor</artifactId>
  <version>1.0</version>
  <type>pom</type>
</dependency>
```

### Step 2
Add permissions to AndroidManifest.xml:

```xml
<uses-permission android:name="android.permission.READ_SMS" />
<uses-permission android:name="android.permission.RECEIVE_SMS" />
```

### Step 3
Add listener to Activity/Fragment:

```java
public class AuthActivity extends AppCompatActivity implements OnMessageListener
```

and override:
```java
@Override
public void messageReceived(String message) {
    // You can perform your validation here
    etAuthPassword.setText(message);
}
```

### Step 4
Create interceptor class:

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_auth);
    SmsInterceptor smsInterceptor = new SmsInterceptor(this, this);
    // Not necessary
    smsInterceptor.setRegex(SMS_CODE_REGEX);
    smsInterceptor.setPhoneNumber(PHONE_NUMBER);
}
```

### Step 5
Register and be sure to unregister:

```java
@Override
protected void onResume() {
    super.onResume();
    smsInterceptor.register();
}

@Override
protected void onPause() {
    super.onPause();
    smsInterceptor.unregister();
}
```

### Step 6
Don’t forget to add permissions:

```java
@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    smsInterceptor.onRequestPermissionsResult(requestCode, permissions, grantResults);
}
```

That's all! Enjoy using!

---

## Developed by

Vitali Bovkunovich - vitalibof@gmail.com

---

## License

- **[MIT license](https://github.com/VitaliBov/SmsInterceptor/blob/master/LICENSE)**
- Copyright 2018 © <a href="https://github.com/VitaliBov" target="_blank">Vitali Bovkunovich</a>.
