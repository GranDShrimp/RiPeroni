package com.riperoni.riperoni;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.lang.Override;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new MainActivity.PlaceholderFragment())
                    .commit();
            //Create Layout here?
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements OnSeekBarChangeListener {

        private static Song currentSong;
        private static MediaPlayer player;

        public static void setCurrentSong(Song newSong) {
            currentSong = newSong;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);


            /*********Action Listeners**********/

            Button songButton = (Button) rootView.findViewById(R.id.button);
            songButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();

                    Song oldSong = currentSong;


                    Intent intent = new Intent(context, ChooseSong.class);
                    startActivity(intent);

                    if(oldSong.getSongUri().compareTo(currentSong().getSongUri()) != 0 ) {
                        player.release();
                        player.create(v.getContext(), currentSong.getSongUri());
                    }
                }
            });

            Button playButton = (Button) rootView.findViewById(R.id.play_button);
            playButton.setOnClickListener(new View.OnLongClickListener() {
                @Override
                public void onClick(View v) {
                    if(player.isPlaying()) {
                        player.pause();
                        v.setBackground(@drawable/play_button);
                    }
                    else {
                        player.start();
                        v.setBackground(@drawable/pause_button);
                    }

                }
            })

            SeekBar songDuration = (SeekBar) rootview.findViewById(R.id.duration_Seekbar);
            songDuration.setOnSeekChangeBarListener(new View.OnSeekChangeBarListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progresValue boolean fromUser,) {
                    progress = progresValue;
                    Toast.maketext(getApplicationContext(), "Changing position in song...", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    Toast.makeText(getApplicationContext(), "Started tracking song position.", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onStopTrackingTouch (SeekBar seekBar) {
                    textView.setText("Covered: " + progress + "/" + seekBar.getMax());
                    Toast.makeText(getApplicationContext(), "Stopped tracking song position.", Toast.LENGTH_SHORT).show();
                }
            });



            return rootView;
        }
    }
}
