package com.games.djc.android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.games.djc.Highscore;

import java.util.Map;

/**
 * Created by Pius on 13.02.2015.
 */
public class HighscoreImpl implements Highscore {

    AndroidApplication context;
    static String lastInputName = "...";

    public HighscoreImpl(AndroidApplication context) {
        this.context = context;
    }

    @Override
    public String getName() {


        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setTitle("Input Name");
        alert.setMessage("");

// Set an EditText view to get user input
        final EditText input = new EditText(context);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                lastInputName = input.getText().toString();
                // Do something with value!


            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                lastInputName = "...";
            }
        });

//        context.postRunnable(alert.show());
        final AlertDialog.Builder fAlert = alert;
        context.postRunnable(new Runnable() {
            @Override
            public void run() {
                fAlert.show();
            }
        });


        return lastInputName;
    }

    @Override
    public void saveScore() {

    }

    @Override
    public Map<String, Long> getScores() {
        return null;
    }
}
