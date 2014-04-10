package com.willdo.frcscouter.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TeamList extends FragmentActivity {

    private MatchDbAdapter mDbHelper;
    private Cursor mTeamCursor;
    private ListView mTeamList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);
        mTeamList = (ListView) findViewById(R.id.teamList);

        AdapterView.OnItemClickListener l = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor c = mTeamCursor;
                c.moveToPosition(i);
                Intent teamDetail = new Intent(getBaseContext(), TeamDetail.class);
                teamDetail.putExtra(MatchDbAdapter.KEY_TEAM, c.getInt(
                        c.getColumnIndexOrThrow(MatchDbAdapter.KEY_TEAM)));
                startActivity(teamDetail);
            }
        };

        mTeamList.setOnItemClickListener(l);
        mDbHelper = new MatchDbAdapter(this);
        mDbHelper.open();
        // Get all of the notes from the database and create the item list
        mTeamCursor = mDbHelper.fetchAllTeams();
        fillData();
    }

    private void fillData() {
        startManagingCursor(mTeamCursor);

        String[] from = new String[] {MatchDbAdapter.KEY_TEAM,
                MatchDbAdapter.KEY_CRITA, MatchDbAdapter.KEY_CRITB, MatchDbAdapter.KEY_CRITC, MatchDbAdapter.KEY_CRITD, MatchDbAdapter.KEY_CRITE, MatchDbAdapter.KEY_CRITF, MatchDbAdapter.KEY_DEFENSE};
        int[] to = new int[] { R.id.teamText, R.id.rowCritA, R.id.rowCritB, R.id.rowCritC, R.id.rowCritD, R.id.rowCritE, R.id.rowCritF, R.id.rowDefense  };

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes =
                new SimpleCursorAdapter(this, R.layout.team_row, mTeamCursor, from, to);
        mTeamList.setAdapter(notes);
        Log.v("DATA", "DATA FILLED");
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
        switch( item.getItemId() ) {
            case R.id.action_filter:
                filterAction();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void filterAction() {
        DialogFragment filterDialog = new DialogFragment() {

            @Override
            public Dialog onCreateDialog( Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Sort List")
                        .setItems(R.array.filters_array, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String sortId = MatchDbAdapter.KEY_TEAM;

                                switch(which) {
                                    case 1:
                                        sortId = MatchDbAdapter.KEY_CRITA;
                                        break;
                                    case 2:
                                        sortId = MatchDbAdapter.KEY_CRITB;
                                        break;
                                    case 3:
                                        sortId = MatchDbAdapter.KEY_CRITC;
                                        break;
                                    case 4:
                                        sortId = MatchDbAdapter.KEY_CRITD;
                                        break;
                                    case 5:
                                        sortId = MatchDbAdapter.KEY_CRITE;
                                        break;
                                    case 6:
                                        sortId = MatchDbAdapter.KEY_CRITF;
                                        break;
                                    case 7:
                                        sortId = MatchDbAdapter.KEY_DEFENSE;
                                        break;
                                    default:
                                        sortId = MatchDbAdapter.KEY_TEAM;
                                }

                                mTeamCursor = mDbHelper.fetchAllTeams( sortId );
                                fillData();
                            }
                        });
                return builder.create();
            }

        };
        filterDialog.show(TeamList.this.getSupportFragmentManager(), "filter");
    }


}
