package ideapot.Logs;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ideapot.controller.QuizzyDatabase;
import ideapot.quizzy.R;

public class LogsActivity extends Activity {

    private QuizzyDatabase database;
    private ArrayList<String> newLogs;
    private ArrayAdapter<String> newAdapter;
    private ListView logList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);

        logList = (ListView)findViewById(R.id.activity_logs);

        database = new QuizzyDatabase(getApplicationContext(), "QUIZZY", null, 1);

        Cursor logs = database.getUserLogs();

        newLogs = new ArrayList<String>();

        if(logs.moveToFirst()){

            do {

                newLogs.add(logs.getString(1));

            } while(logs.moveToNext());

        }

        newAdapter = new CommonListAdapter(getApplicationContext(), R.layout.logs_text, newLogs);

        logList.setAdapter(newAdapter);

    }

    private class CommonListAdapter extends ArrayAdapter<String> {

        private Context context;
        private int resource;
        private List<String> logs;

        public  CommonListAdapter(Context context, int resource, List<String> logs){

            super(context, resource, logs);

            this.context = context;
            this.resource = resource;
            this.logs = logs;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){

                convertView = getLayoutInflater().inflate(resource, parent, false);
            }

            ((TextView)convertView.findViewById(R.id.device_name)).setText(logs.get(position));

            return convertView;
        }
    }
}
