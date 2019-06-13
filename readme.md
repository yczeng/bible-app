### Running Android Studio
`sh /opt/android-studio/bin/studio.sh`

### Making Toasts

```
Context context = getApplicationContext();
CharSequence text = json;
int duration = Toast.LENGTH_LONG;
Toast toast = Toast.makeText(context, text, duration);
toast.show();
```

### Running Tests
`javac -cp .:/usr/share/java/json-simple-1.1.jar test/JSONBible.java test/TestMain.java && java -cp .:/usr/share/java/json-simple-1.1.jar test.TestMain`
