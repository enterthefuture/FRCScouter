package com.willdo.frcscouter.app;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;

public class DataInput extends ActionBarActivity {

    private MatchDbAdapter mDbHelper;

    private EditText mTeam;
    private EditText mMatch;
    private Switch mAlliance;
    private EditText mCritA;
    private EditText mCritB;
    private EditText mCritC;
    private EditText mCritD;
    private EditText mCritE;
    private EditText mCritF;
    private EditText mPenalties;
    private RatingBar mCoop;
    private RatingBar mDefense;
    long mRowId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_input);

        mTeam = (EditText) findViewById(R.id.teamNum);
        mMatch = (EditText) findViewById(R.id.matchNum);
        mAlliance = (Switch) findViewById(R.id.allianceSwitch);
        mCritA = (EditText) findViewById(R.id.criteriaANum);
        mCritB = (EditText) findViewById(R.id.criteriaBNum);
        mCritC = (EditText) findViewById(R.id.criteriaCNum);
        mCritD = (EditText) findViewById(R.id.criteriaDNum);
        mCritE = (EditText) findViewById(R.id.criteriaENum);
        mCritF = (EditText) findViewById(R.id.criteriaFNum);
        mPenalties = (EditText) findViewById(R.id.penaltiesNum);
        mCoop = (RatingBar) findViewById(R.id.coopRating);
        mDefense = (RatingBar) findViewById(R.id.defenseRating);

        mDbHelper = new MatchDbAdapter(this);
        mDbHelper.open();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mRowId = extras.getLong(MatchDbAdapter.KEY_ROWID);
            fillData(mRowId);
        } else {
            mRowId = -1;
        }
    }

//    @Override
//    public void onDestroy(){
//        saveEntry();
//        super.onDestroy();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.data_input, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch( item.getItemId() ) {
            case R.id.action_next:
                saveEntry();
                nextAction();
                return true;
            case R.id.action_done:
                saveEntry();
                doneAction();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void nextAction() {
        Intent intent = new Intent(this, DataInput.class);
        startActivity(intent);
    }
    public void doneAction() {
        Intent intent = new Intent(this, MatchList.class);
        startActivity(intent);
    }
    public void saveEntry() {
        int team = -1;
        if(mTeam != null)
            team = Integer.parseInt(mTeam.getText().toString());

        int match = -1;
        if(mMatch != null)
            match = Integer.parseInt(mMatch.getText().toString());

        int alliance = (mAlliance.isChecked())?0:1;

        int critA = -1;
        if(mCritA != null)
            critA = Integer.parseInt(mCritA.getText().toString());

        int critB = -1;
        if(mCritB != null)
            critB = Integer.parseInt(mCritB.getText().toString());

        int critC =  -1;
        if(mCritC != null)
            critC = Integer.parseInt(mCritC.getText().toString());

        int critD =  -1;
        if(mCritD != null)
            critD = Integer.parseInt(mCritD.getText().toString());

        int critE =  -1;
        if(mCritE != null)
            critE = Integer.parseInt(mCritE.getText().toString());

        int critF =  -1;
        if(mCritF != null)
            critF = Integer.parseInt(mCritF.getText().toString());

        int penalties = -1;
        if(mPenalties != null)
            penalties = Integer.parseInt(mPenalties.getText().toString());

        int coop = (int)(mCoop.getRating()*2);
        int defense = (int)(mDefense.getRating()*2);

        String TAG;

        if(mRowId == -1) {
            TAG="Saving";
            mDbHelper.createMatch(team, match, alliance, critA, critB, critC, critD, critE, critF, penalties, coop, defense);
        } else {
            TAG="Updating";
            mDbHelper.updateMatch( mRowId, team, match, alliance, critA, critB, critC, critD, critE, critF, penalties, coop, defense);
        }

        Log.v(TAG, Integer.toString(team) + " " +
                Integer.toString(match) + " " +
                Integer.toString(alliance) + " " +
                Integer.toString(critA) + " " +
                Integer.toString(critB) + " " +
                Integer.toString(critC) + " " +
                Integer.toString(critD) + " " +
                Integer.toString(critE) + " " +
                Integer.toString(critF) + " " +
                Integer.toString(penalties) + " " +
                Integer.toString(coop) + " " +
                Integer.toString(defense)
        );
    }

    public void fillData( long mRowId) {
        Cursor c = mDbHelper.fetchMatchCursor(mRowId);
        mTeam.setText(String.valueOf(c.getInt(1)));
        mMatch.setText(String.valueOf(c.getInt(2)));
        mAlliance.setChecked(c.getInt(3)==1);
        mCritA.setText(String.valueOf(c.getInt(4)));
        mCritB.setText(String.valueOf(c.getInt(5)));
        mCritC.setText(String.valueOf(c.getInt(6)));
        mCritD.setText(String.valueOf(c.getInt(7)));
        mCritE.setText(String.valueOf(c.getInt(8)));
        mCritF.setText(String.valueOf(c.getInt(9)));
        mPenalties.setText(String.valueOf(c.getInt(10)));
        mCoop.setRating((float)(c.getInt(11) / 2.0));
        mDefense.setRating((float)(c.getInt(12) / 2.0));
    }

}
