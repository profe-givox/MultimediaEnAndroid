package net.ivanvega.multimediaenandroid;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class CapturaFoto extends Activity {
	ImageView img ;
	Uri photoURI;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_captura_foto);
		img = (ImageView)findViewById(R.id.img);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.captura_foto, menu);
		return true;
	}
	
	public void btnCapturaFoto_click(View v) {
		
		Intent camaraFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		File foto=null;

		Uri imageUri;

		try {
			 foto = File.createTempFile("miFoto", ".jpg",
                     Environment.getExternalStorageDirectory());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		photoURI = FileProvider.getUriForFile(this,
				this.getApplicationContext().getPackageName()
						+ ".net.ivanvega.provider", foto);
		camaraFoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

		camaraFoto.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
		
		startActivityForResult(camaraFoto,1
				);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//Bitmap pic = (Bitmap)data.getExtras().get("data");
			

		img.setImageURI(photoURI);

		
//		ByteArrayOutputStream arr = new ByteArrayOutputStream();
//		File file = new File(
//				Environment.getExternalStoragePublicDirectory
//				(		Environment.DIRECTORY_PICTURES)
//						, "miFoto.jpg");
//		try {
//			FileOutputStream fo = new FileOutputStream(file);
//			pic.compress(Bitmap.CompressFormat.JPEG, 0, arr);
//			try {
//				fo.write(arr.toByteArray());
//				fo.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		super.onActivityResult(requestCode, resultCode, data);
	}

}
