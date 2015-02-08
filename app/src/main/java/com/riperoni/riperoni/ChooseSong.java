package com.riperoni.riperoni;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;


public class ChooseSong extends ActionBarActivity {
    private ArrayList<Song> songArrayList = new ArrayList<Song>();

    public ChooseSong() {
        System.out.println("Made Song Fragment");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_song);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new SongFragment())
                    .commit();
        }
    }

    public void setSongArrayList(ArrayList<Song> songArrayList) {
        this.songArrayList = songArrayList;
    }

    public ArrayList<Song>  getSongArrayList() {
        return this.songArrayList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_song, menu);
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
    public static class SongFragment extends Fragment {
        ArrayList<Song> songArrayList;
        private Context context;
        private Song currentSong;

        private static ArrayList<Song> getSongs(Context context) {
            ArrayList<Song> songList = new ArrayList<Song>();
            String[] retCol = {MediaStore.Audio.Media._ID};
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Cursor cur = contentResolver.query(uri, null, null, null, null);
            if(cur == null) {
                //Severe failure
            }
            else if(!cur.moveToFirst()) {
                //No media
            }
            else {
                int titleColumn = cur.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int idColumn = cur.getColumnIndex(MediaStore.Audio.Media._ID);
                int artistColumn = cur.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int durationColumn = cur.getColumnIndex(MediaStore.Audio.Media.DURATION);
                do {
                    songList.add(new Song(cur.getString(titleColumn),
                        cur.getString(artistColumn),
                        cur.getInt(durationColumn),
                        ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, cur.getLong(idColumn))));
                } while(cur.moveToNext());
            }
            System.out.println(songList.size());
            Iterator<Song> it = songList.iterator();
            while(it.hasNext()){
                System.out.println(it.next().getTitle());
            }
            return songList;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_choose_song, container, false);
            context=container.getContext();
            songArrayList = getSongs(context);
//
//            ArrayAdapter<Song> adapt = new ArrayAdapter<Song>(
//                    getActivity().getApplicationContext(),
//                    R.layout.song_layout,
//                    R.id.song_layout_TextView,
//                    songArrayList);
//
//            ListView litem = (ListView) rootView.findViewById(R.id.songList);
//            litem.setAdapter(adapt);
//            litem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//                }
//            });

            //setCurrentSong

            MainActivity.PlaceholderFragment.setCurrentSong(currentSong);

            return rootView;
        }
    }
}
