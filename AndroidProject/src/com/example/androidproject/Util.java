package com.example.androidproject;

import java.util.HashMap;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

/*
 * κ°λ°κΈ°κ°? μ€μΌ? ?? ?? λ§μ Util ?΄??€ λ§λ€?΄κ°?κΈ?.
 */
public class Util {
	
	
	/*
	 * [ ?€λ³΄λ ?¨κΈ°λ λ©μ? ]
	 * 
	 * ?Έ?λ‘? Context κ°μ²΄?? ??¬ ?¬μ»€μ€κ°? ?? EditText κ°μ²΄λ₯? ? ?¬??€. 
	 */
	public static void hideKeyboard(Context context, EditText editText){
		InputMethodManager iManager=(InputMethodManager)
				context.getSystemService(Context.INPUT_METHOD_SERVICE);
		
		iManager.hideSoftInputFromWindow(editText.getWindowToken(),0);
	}
	
	/*
	 * [ ? ?€?Έ λ©μΈμ§? ??°κΈ? ]
	 * 
	 * ?Έ?λ‘? Context κ°μ²΄?? ??Έ String κ°μ²΄λ₯? ? ?¬??€. 
	 */
	public static void makeToast(Context context, String msg){
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	

	/*
	 *  [ ?¨κ³Όμ ?±λ‘κ³Ό ?¬?? ?Έ?κ²? ?κΈ°μ? ?΄??€ ]
	 *  
	 *  1. SoundManager.getInstance() ?΄? κ°μ²΄? μ°Έμ‘°κ°μ ?»?΄?¨?€?
	 *  2. init() λ©μ?? ?Έ?λ‘? Context κ°μ²΄λ₯? ? ?¬?΄? μ΄κΈ°? ?΄? ?¬?©??€. 
	 */
	public static class SoundManager{
		private static SoundManager sManager; //?±κΈ??€ 
		private SoundPool soundPool;
		private HashMap<Integer, Integer> map;
		private Context context;
		//?ΈλΆ??? κ°μ²΄ ??±? ? ??λ‘? ??±?λ₯? private λ‘? ?€? 
		private SoundManager(){}
		//μ°Έμ‘°κ°μ λ¦¬ν΄?? λ©μ? ? κ³?
		public static SoundManager getInstance(){
			if(sManager==null){
				sManager=new SoundManager();
			}
			return sManager;
		}
		//μ΄κΈ°? λ©μ? 
		public void init(Context context){
			this.context=context;
			if(soundPool!=null){ //?¬?©?κ³? ?? κ°μ²΄?Όλ©?
				//?? ?΄? .
				soundPool.release();
			}
			soundPool=new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
			//?¬?΄?? ??΄?λ₯? κ΄?λ¦¬ν  HashMap κ°μ²΄ ??±?κΈ?. 
			map=new HashMap<Integer, Integer>();
		}
		//?? ?±λ‘ν? λ©μ?
		public void addSound(int index, int resId){
			//?¬?΄? λ‘λ©?κ³? ??΄? κ°μ λ°μ?¨?€.
			int id=soundPool.load(context, resId, 1);
			//λ°μ?¨ ??΄?λ₯? HashMap κ°μ²΄? ???₯??€.
			map.put(index, id);
		}
		//?? ?¬??? λ©μ?
		public void playSound(int index){
			//HashMap κ°μ²΄? ???₯? id κ°μ λΆλ¬??? ?΄?Ή index ? ?? ?¬???€. 
			soundPool.play(map.get(index), 1, 1, 1, 0, 1);
		}
		//?¬? μ€μ??? λ©μ?
		public void stopSound(int index){
			soundPool.stop(map.get(index));
		}
		//?Ό?? ? ?? λ©μ?
		public void pauseSound(int index){
			soundPool.pause(map.get(index));
		}
		//?¬?? ?? λ©μ?
		public void resumeSound(int index){
			soundPool.resume(map.get(index));
		}
	}//class
	
	//λ°©ν₯ ?Ό? κ°μ λ°μ?€κΈ? ?? ?Έ?°??΄?€ 
	public static interface Orientation{
		public void orientationValue(float[] values);
	}
	/*
	 *  [ λ°©ν₯?Ό?κ°μ ?»?΄?€κΈ? ?? ?΄??€ ]
	 *  
	 *  1. λ°©ν₯?Ό?κ°μ λ°μ ?΄??€?? Util.Orientation ?Έ?°??΄?€λ₯? κ΅¬ν??€.
	 *  2. ?Ό?κ°μ λ°μ orientationValue() λ©μ?λ₯? ?€λ²λΌ?΄?©??€.
	 *  3. Util.OrientationManager.getInstance() ?΄? μ°Έμ‘°κ°μ ?»?΄?¨?€.
	 *  4. init() λ©μ? ?ΈμΆνλ©΄μ Context κ°μ²΄?? Orientation κ°μ²΄λ₯? ? ?¬??€.
	 *  5. orientationValue() λ©μ?? ?Έ?λ‘? ? ?¬?? float[] κ°μ²΄? λ°©ν₯?Ό?κ°μ΄
	 *     ?€?΄??€
	 *     -?€?© : values[0]
	 *     -?ΌμΉ? : values[1]
	 *     -λ‘€λ§ : values[2]
	 *  6. ??΄? ?Ό?κ°μ ?»?΄?¬ ??κ°? ??? relase() λ©μ?λ₯? ?ΈμΆν΄μ€??€.    
	 */
	public static class OrientationManager implements SensorEventListener{
		
		private static OrientationManager oManager;
		
		private SensorManager mSensorManager;
	    private Sensor mAccelerometer;
	    private Sensor mMagnetometer;

	    private float[] mLastAccelerometer = new float[3];
	    private float[] mLastMagnetometer = new float[3];
	    private boolean mLastAccelerometerSet = false;
	    private boolean mLastMagnetometerSet = false;

	    private float[] mR = new float[9];
	    private float[] mOrientation = new float[3];
	    
	    private Orientation orientation;
	    //??±?. 
		private OrientationManager(){}
		//μ°Έμ‘°κ°? λ¦¬ν΄?? λ©μ? 
		public static OrientationManager getInstance(){
			if(oManager==null){
				oManager=new OrientationManager();
			}
			return oManager;
		}
		//μ΄κΈ°? λ©μ? 
		public void init(Context context, Orientation orientation){
			 this.orientation=orientation;
			 mSensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
		     mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		     mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		     mLastAccelerometerSet = false;
		     mLastMagnetometerSet = false;
		     mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		     mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
		}
		//λ¦¬μ€? ?΄?  λ©μ? 
		public void release(){
			mSensorManager.unregisterListener(this);
		}
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {}

		@Override
		public void onSensorChanged(SensorEvent event) {
			if (event.sensor == mAccelerometer) {
	            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
	            mLastAccelerometerSet = true;
	        } else if (event.sensor == mMagnetometer) {
	            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
	            mLastMagnetometerSet = true;
	        }
	        if (mLastAccelerometerSet && mLastMagnetometerSet) {
	            SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
	            SensorManager.getOrientation(mR, mOrientation);
	            Log.i("OrientationTestActivity", String.format("Orientation: %f, %f, %f",
	                                                           mOrientation[0], mOrientation[1], mOrientation[2]));
	            float heading=(float)(mOrientation[0]*(180/Math.PI));
	            if(heading<0){
	            	heading=360+heading;
	            }
	            float pitch=(float)(mOrientation[1]*(180/Math.PI));
	            float rolling=(float)(mOrientation[2]*(180/Math.PI));
	            
	            float[] values={heading,pitch,rolling };
	            
	            orientation.orientationValue(values);
	        }
		}
		
	}
}






















































