package com.willdo.frcscouter.app;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class MatchList extends ListActivity {
    private static final int ACTIVITY_EDIT=1;
    private MatchDbAdapter mDbHelper;
    private Cursor mNotesCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);
        mDbHelper = new MatchDbAdapter(this);
        mDbHelper.open();
        fillData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.match_list, menu);
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

    private void fillData() {
        // Get all of the notes from the database and create the item list
        mNotesCursor = mDbHelper.fetchAllMatches();
        startManagingCursor(mNotesCursor);

        String[] from = new String[] { MatchDbAdapter.KEY_TEAM, MatchDbAdapter.KEY_MATCH };
        int[] to = new int[] { R.id.teamText, R.id.matchText  };

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes =
                new SimpleCursorAdapter(this, R.layout.match_row, mNotesCursor, from, to);
        setListAdapter(notes);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Cursor c = mNotesCursor;
        c.moveToPosition(position);
        Intent i = new Intent(this, DataInput.class);
        i.putExtra(MatchDbAdapter.KEY_ROWID, c.getLong(
                c.getColumnIndexOrThrow(mDbHelper.KEY_ROWID)));
        startActivity(i);
    }
}
