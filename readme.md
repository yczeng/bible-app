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


### Printing to Log

'''
Log.i("tag", "msg");
'''

open LogCat at the bottom of AndroidStudio and search for "tag" to filter for only the relevant log messages


### Running Tests
test/ includes TestMain.java and JSONBible.java
TestMain.java is a testing suite for the classfile JSONBible.java, which should take in a JSONArray in the constructor and have some helper methods to access important data from the bible and simplify those actions.

running the tests requires including json-simple-1.1.jar in the classpath, and it's located at /usr/share/java/json-simple-1.1.jar and all new jar files should be included in a similar fashion.
But you should just be able to run the command below if no new jar files are added:

`sh test/run.sh`
