package com.willdo.frcscouter.app;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class TeamList extends ListActivity {

    private MatchDbAdapter mDbHelper;
    private Cursor mTeamCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);

        mDbHelper = new MatchDbAdapter(this);
        mDbHelper.open();
        fillData();
    }

    private void fillData() {
        // Get all of the notes from the database and create the item list
        mTeamCursor = mDbHelper.fetchAllTeams();
        startManagingCursor(mTeamCursor);

        String[] from = new String[] {MatchDbAdapter.KEY_TEAM,
                MatchDbAdapter.KEY_CRITA, MatchDbAdapter.KEY_CRITB, MatchDbAdapter.KEY_CRITC, MatchDbAdapter.KEY_CRITD, MatchDbAdapter.KEY_CRITE, MatchDbAdapter.KEY_CRITF};
        int[] to = new int[] { R.id.teamText, R.id.rowCritA, R.id.rowCritB, R.id.rowCritC, R.id.rowCritD, R.id.rowCritE, R.id.rowCritF  };

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes =
                new SimpleCursorAdapter(this, R.layout.team_row, mTeamCursor, from, to);
        setListAdapter(notes);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Cursor c = mTeamCursor;
        c.moveToPosition(position);
        Intent i = new Intent(this, TeamDetail.class);
        i.putExtra(MatchDbAdapter.KEY_TEAM, c.getInt(
                c.getColumnIndexOrThrow(MatchDbAdapter.KEY_TEAM)));
        startActivity(i);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.team_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

}
