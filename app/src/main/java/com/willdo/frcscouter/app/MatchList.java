package com.willdo.frcscouter.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class MatchList extends ListActivity {
    private MatchDbAdapter mDbHelper;
    private Cursor mMatchCursor;
    private EditText mTeamNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);

        mTeamNumber = (EditText) findViewById(R.id.teamNumber);

        mDbHelper = new MatchDbAdapter(this);
        mDbHelper.open();

        mMatchCursor = mDbHelper.fetchAllMatches();
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
        switch( item.getItemId() ) {
            case R.id.action_done:
                doneAction();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void filterButtonAction( View view ) {
        String filterTeam = mTeamNumber.getText().toString();
        mMatchCursor = mDbHelper.fetchTeamMatchCursor(filterTeam);
        fillData();
    }

    private void fillData() {
        // Get all of the notes from the database and create the item list
        startManagingCursor(mMatchCursor);

        String[] from = new String[] {MatchDbAdapter.KEY_TEAM, MatchDbAdapter.KEY_MATCH,
                MatchDbAdapter.KEY_CRITA, MatchDbAdapter.KEY_CRITB, MatchDbAdapter.KEY_CRITC, MatchDbAdapter.KEY_CRITD, MatchDbAdapter.KEY_CRITE, MatchDbAdapter.KEY_CRITF};
        int[] to = new int[] { R.id.teamText, R.id.matchText, R.id.rowCritA, R.id.rowCritB, R.id.rowCritC, R.id.rowCritD, R.id.rowCritE, R.id.rowCritF };

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes =
                new SimpleCursorAdapter(this, R.layout.match_row, mMatchCursor, from, to);
        setListAdapter(notes);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Cursor c = mMatchCursor;
        c.moveToPosition(position);
        final long rowId = c.getLong(c.getColumnIndexOrThrow(MatchDbAdapter.KEY_ROWID));

        Intent i = new Intent(this, DataInput.class);
        i.putExtra(MatchDbAdapter.KEY_ROWID, rowId);
        startActivity(i);
    }

    public void doneAction() {
        Intent intent = new Intent(this, TeamList.class);
        startActivity(intent);
    }
}
