package ideapot.quizzy;


import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ContactsAdapter extends CursorAdapter{

	LayoutInflater inflater;
    Context context;
	
	public ContactsAdapter(Context contextt) {
		super(contextt, null, 0);
		
		inflater = LayoutInflater.from(contextt);
        context = contextt;
		
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		
		final ViewHolder holder = (ViewHolder)view.getTag();
        final Context c = context;

		holder.text1.setText(cursor.getString(0));
		holder.text2.setText(cursor.getString(1));
        holder.name = cursor.getString(0);

        holder.invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    Log.w("SMS", holder.text2.getText().toString());
                    SmsManager.getDefault().sendTextMessage(holder.text2.getText().toString(), null, "Check out this amazing game QuizJab. Download it today from "+"https://play.google.com/store/apps/details?id=ideapot.talentseal.buz&hl=en", null, null);
                    Toast.makeText(c, "QuizJab Invitation Sent", Toast.LENGTH_LONG).show();

                }
                catch (IllegalArgumentException ex){Log.w("SMS_ERROR", ex.getMessage());}
                catch (Throwable ex){Log.w("SMS_ERROR", ex.getMessage());}

            }
        });

        String photoUri = cursor.getString(2);

		if(photoUri != null){

			Uri photo = Uri.parse(photoUri);

			Log.w("Path", photo.getEncodedPath());

			AssetFileDescriptor afd = null;

            InputStream inputStream = null;

			try {

                inputStream = context.getContentResolver().openInputStream(photo);

                holder.pic.setImageBitmap(imageCirclexClip(BitmapFactory.decodeStream(inputStream)));

			} catch (Exception e) {

                Log.w("Error", "File Not Found");
			}

			
		}else{

            holder.pic.setImageBitmap(imageCirclexClip(BitmapFactory.decodeResource(context.getResources(), R.drawable.dummy_image)));


        }

		
	}

	@Override
	public View newView(final Context context, Cursor cursor, ViewGroup parent) {

		Log.w("Column Name", "Testing Done");
		
		View itemLayout =  inflater.inflate(R.layout.friend_list_item, parent, false);

		
		final ViewHolder holder = new ViewHolder();
		holder.text1 = (TextView)itemLayout.findViewById(R.id.friend_name);
		holder.text2 = (TextView)itemLayout.findViewById(R.id.friend_mobile);
		holder.text2.setVisibility(View.VISIBLE);
        holder.invite = (Button)itemLayout.findViewById(R.id.friend_invite);
		
		holder.pic = (ImageView)itemLayout.findViewById(R.id.list_image);
		holder.value = false;
		
		itemLayout.setTag(holder);

		return itemLayout;
	}

    public  Bitmap imageCirclexClip(Bitmap sourceBitmap){

        if(sourceBitmap == null){

            sourceBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.james);
        }

        int targetWidth = 100;
        int targetheight = 100;

        Bitmap outputBitmap = Bitmap.createBitmap(targetWidth, targetheight, Bitmap.Config.ARGB_8888);

        Path path = new Path();
        path.addCircle(targetWidth/2, targetheight/2, targetWidth/2, Path.Direction.CCW);

        Canvas canvas = new Canvas(outputBitmap);
        canvas.clipPath(path);

        Rect src = new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight());
        Rect out = new Rect(0, 0, targetWidth, targetheight);

        Bitmap source = sourceBitmap;

        canvas.drawBitmap(source, src, out, null);

        return outputBitmap;
    }

	
	
}
