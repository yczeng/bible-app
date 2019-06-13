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
