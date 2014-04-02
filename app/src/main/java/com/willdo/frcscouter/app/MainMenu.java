package com.willdo.frcscouter.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainMenu extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        /* TODO: Database initialization */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addButtonAction( View view ) {
        Intent intent = new Intent(this, DataInput.class);
        startActivity(intent);
    }
    public void editButtonAction( View view ) {
        Intent intent = new Intent(this, MatchList.class);
        startActivity(intent);
    }
    public void calcButtonAction( View view ) {
        Intent intent = new Intent(this, RankingConfig.class);
        startActivity(intent);
    }
    public void clearButtonAction( View view ) {
        DialogFragment clearDialog = new DialogFragment() {
            @Override
            public Dialog onCreateDialog( Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.clear_warning)
                        .setPositiveButton(R.string.clear_confirm, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // FIRE ZE MISSILES!
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                return builder.create();
            }

        };
        clearDialog.show(getSupportFragmentManager(), "clear");
    }
    public void rankButtonAction( View view ) {
        Intent intent = new Intent(this, TeamList.class);
        startActivity(intent);
    }
}
