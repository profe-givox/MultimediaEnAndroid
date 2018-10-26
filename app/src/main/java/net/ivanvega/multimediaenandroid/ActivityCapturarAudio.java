package net.ivanvega.multimediaenandroid;

import java.io.File;
import java.io.IOException;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ActivityCapturarAudio extends Activity implements OnClickListener {
	Button btnGrabar, btnReproducir;
	boolean flag=true;
	MediaRecorder mr=null;
	MediaPlayer mp = null; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capturar_audio);
		
		//recuperar parametros
		Intent i = getIntent();
		String parametro = i.getStringExtra("miparametro");
		Toast.makeText(getBaseContext(),
				"Esta actividad recibio el siguiente parametro" + parametro
				, Toast.LENGTH_LONG).show();
		
		btnGrabar = (Button)findViewById(R.id.btnGrabar);
		btnReproducir = (Button)findViewById(R.id.btnReproducir);
		btnGrabar.setOnClickListener(this);
		btnReproducir.setOnClickListener(this);
		btnReproducir.setEnabled(false);

		this.checkPermissionExternalStorage(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.equals(btnGrabar)){
			this.onGrabar(flag);
		}
		
		if (v.equals(btnReproducir)){
			this.onReproducir(flag);
		}
	}
	
	private void onGrabar(boolean accion){
		if(accion){
			btnGrabar.setText("Parar grabacion");
			flag = false;
			this.startGrabar();
		}else{
			btnGrabar.setText("Grabar audio");
			btnReproducir.setEnabled(true);
			flag= true;
			this.stopGrabar();
		}
			
	}
	
	private void onReproducir(boolean accion){
		if(accion){
			btnReproducir.setText("Parar reproduccion");
			flag = false;
			btnGrabar.setEnabled(false);
			this.startReproducir();
		}else{
			btnReproducir.setText("Reproducir audio");
			flag = true;
			btnGrabar.setEnabled(true);
			this.stopReproducir();
		}

	}
	
	private void startGrabar() {
		mr = new MediaRecorder();
		mr.setAudioSource(MediaRecorder.AudioSource.MIC);
		mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
		File audio = new File(dir, "migrabacion.3gp");
		mr.setOutputFile(audio.getAbsolutePath());
		mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		try {
			mr.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mr.start();
		

	}
	
	private void stopGrabar() {
		mr.stop();
		mr.release();
		mr=null;
	}
	
	private void startReproducir(){
		mp = new MediaPlayer();
		File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
		try {
			mp.setDataSource(dir.getAbsolutePath() + "/migrabacion.3gp");
			mp.prepare();
			mp.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void stopReproducir(){
		mp.stop();
		mp.release();
		mp = null;
	}

	public void checkPermissionExternalStorage (Activity thisActivity){
		// Here, thisActivity is the current activity
		if (ContextCompat.checkSelfPermission(thisActivity,
				Manifest.permission.RECORD_AUDIO)
				!= PackageManager.PERMISSION_GRANTED) {

			// Should we show an explanation?
			if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
					Manifest.permission.RECORD_AUDIO)) {

				// Show an expanation to the user *asynchronous
				// ly* -- don't block
				// this thread waiting for the user's response! After the user
				// sees the explanation, try again to request the permission.
				Toast.makeText(this,"No hay permiso", Toast.LENGTH_SHORT).show();


			} else {

				// No explanation needed, we can request the permission.

				ActivityCompat.requestPermissions(thisActivity,
						new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
								Manifest.permission.RECORD_AUDIO},
						1001);

				// MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
				// app-defined int constant. The callback method gets the
				// result of the request.
			}
		}else{
			//doThings();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case 1001: {
				//premission to read storage
				if (grantResults.length > 0
						&&( grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)) {

					// permission was granted, yay! Do the
					// contacts-related task you need to do.
					//doThings();
					Toast.makeText(this, "YA PUEDE GRABAR AUDIO, PERMISO CONCEDIDO	", Toast.LENGTH_SHORT).show();
				} else {

					// permission denied, boo! Disable the
					// functionality that depends on this permission.
					Toast.makeText(this, "We Need permission Storage", Toast.LENGTH_SHORT).show();
				}
				return;
			}

			// other 'case' lines to check for other
			// permissions this app might request
		}
	}
}
