package com.JSONObjectSerialization;


import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class JSONObjectSerialization extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Creating the initial object
        appendLog("Welcome!");
        SimpleClass sc = new SimpleClass(2.4, 17, true, "thisisit!");
        appendLog("This is the initial object:");
        appendLog(sc.toString());
        
        // Serializing it to JSON string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JSONOutputStream jos = new JSONOutputStream(baos);
        jos.writeObject(sc);
        jos.close();
        
        // Get the string form the output stream and print it
        String jsonString = baos.toString();
        appendLog("This is the serialized object:");
        appendLog(jsonString);
        
        // Deserialize the concrete object from the JSON string
        JSONInputStream jis = new JSONInputStream(jsonString);
        SimpleClass sc2 = null;
        try 
        {
            sc2 = jis.readObject(SimpleClass.class);
        } 
        catch (JSONStreamException e) 
        {
            Log.e("JSONObjectSerialization", "Failed to deserialize the object");
            return;
        }
        
        // Print the deserialized object
        appendLog("This is the de-serialized new object:");
        appendLog(sc2.toString());
        
        // Bye
        appendLog("Enjoy :)");
    }

    /**
     * @param string TODO
     * 
     */
    private void appendLog(String string) 
    {
        ((TextView)findViewById(R.id.textView1)).append(string + "\n");
    }
}