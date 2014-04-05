package com.willdo.frcscouter.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class TeamDetail extends ActionBarActivity {

    private TextView mTeam;
    private TextView mCritA;
    private TextView mCritB;
    private TextView mCritC;
    private TextView mCritD;
    private TextView mPenalties;
    private TextView mCoop;
    private TextView mDefense;
    int team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);

        mTeam = (TextView) findViewById(R.id.teamNum);
        mCritA = (TextView) findViewById(R.id.criteriaANum);
        mCritB = (TextView) findViewById(R.id.criteriaBNum);
        mCritC = (TextView) findViewById(R.id.criteriaCNum);
        mCritD = (TextView) findViewById(R.id.criteriaDNum);
        mPenalties = (TextView) findViewById(R.id.penaltiesNum);
        mCoop = (TextView) findViewById(R.id.coopNum);
        mDefense = (TextView) findViewById(R.id.defenseNum);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            team = extras.getInt(MatchDbAdapter.KEY_TEAM);
            fillData();
        } else {
            team = -1;
        }
    }

    private void fillData() {
        MatchDbAdapter mDbHelper;
        mDbHelper = new MatchDbAdapter(this);
        mDbHelper.open();
        long[] stats = mDbHelper.getStats(team);
        mTeam.setText(Integer.toString(team));
        mCritA.setText(Long.toString(stats[0]));
        mCritB.setText(Long.toString(stats[1]));
        mCritC.setText(Long.toString(stats[2]));
        mCritD.setText(Long.toString(stats[3]));
        mPenalties.setText(Long.toString(stats[4]));
        mCoop.setText(Long.toString(stats[5]));
        mDefense.setText(Long.toString(stats[6]));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.team_detail, menu);
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
