package com.purong;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class NewPrinterActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	 	static boolean flagLoop;
	 	static  int		intMs;
	 	static  String strAscii;
	 	static  String strSendData;
	 	static	long      intSendSize = 0;
	 	
	 	static Mhandler mhandler;
	 	
	 	static 	TextView textSendData;
		private Button btnPrint;		
		private Button btnOpen;
		private Spinner spinner;
		private Spinner spinnerPort;
		private Spinner spinnerTime;
		
		private Button btnDoubleWidth;
		private Button btnDoubleHight;
		private Button btnEnlarge;
		private Button btnVertical;
		private Button btnTurn;
		private Button btnUnderline;
		private Button btnOverline;
		private Button btnBarcode;	
		private Button btnCarsh;
		private Button btnStart;
		private Button btnStop;
		
		private Button btn1;
		private Button btn2;
		private Button btn3;
		private Button btn4;
		
		static serialPort printer;
		private int 	intBaud = 115200;
	
		@Override
		public void onCreate(Bundle savedInstanceState) {
    			super.onCreate(savedInstanceState);
    			setContentView(R.layout.printer);
    			
    			try {
					Process process = Runtime.getRuntime().exec("su");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
    			
    			
    			
    			initView();
		}
		
		
		
		private void initView(){
				
				spinner = (Spinner)this.findViewById(R.id.spinner);
				spinnerPort	= (Spinner)this.findViewById(R.id.spinnerPort);
				spinnerTime = (Spinner)this.findViewById(R.id.spinnerTime);
				
				funcFindPort();
				
				btnPrint = (Button)this.findViewById(R.id.btnPrint);
				btnPrint.setOnClickListener(this);				
				btnOpen = (Button)this.findViewById(R.id.btnOpen);
				btnOpen.setOnClickListener(this);
				btnDoubleWidth = (Button)this.findViewById(R.id.btnDoubleWidth);
				btnDoubleWidth.setOnClickListener(this);
				btnEnlarge = (Button)this.findViewById(R.id.btnEnlarge);
				btnEnlarge.setOnClickListener(this);
				btnVertical = (Button)this.findViewById(R.id.btnVertical);
				btnVertical.setOnClickListener(this);
				btnTurn = (Button)this.findViewById(R.id.btnTurn);
				btnTurn.setOnClickListener(this);
				btnUnderline = (Button)this.findViewById(R.id.btnUnderline);
				btnUnderline.setOnClickListener(this);
				btnOverline = (Button)this.findViewById(R.id.btnOverline);
				btnOverline.setOnClickListener(this);
				btnDoubleHight = (Button)this.findViewById(R.id.btnDoubleHight);
				btnDoubleHight.setOnClickListener(this);
				btnBarcode = (Button)this.findViewById(R.id.btnBarcode);
				btnBarcode.setOnClickListener(this);
				btnCarsh = (Button)this.findViewById(R.id.btnCarsh);
				btnCarsh.setOnClickListener(this);
				btnStart = (Button)this.findViewById(R.id.btnStart);
				btnStart.setOnClickListener(this);
				btnStop  = (Button)this.findViewById(R.id.btnStop);
				btnStop.setOnClickListener(this);
				
				btn1	= (Button)this.findViewById(R.id.btn1);
				btn1.setOnClickListener(this);
				btn2	= (Button)this.findViewById(R.id.btn2);
				btn2.setOnClickListener(this);
				btn3	= (Button)this.findViewById(R.id.btn3);
				btn3.setOnClickListener(this);
				btn4	= (Button)this.findViewById(R.id.btn4);
				btn4.setOnClickListener(this);
		        		        		
				textSendData = (TextView)this.findViewById(R.id.textSendData);
				
				mhandler = new Mhandler();
		        printer = new serialPort();
		        setBtnEnable(false);
		}
		
		
		private void funcFindPort(){
			List<String> list = new ArrayList<String>(); 
			String strKey = "tty";
			File file = new File("/dev/");
			String path;
			File[] files=file.listFiles();
			if (files==null){
				return;
			}
			for(int i = 0;i < files.length;i++)
			{
				if(files[i].toString().contains(strKey))
				{
					list.add(files[i].toString());
				}
			}
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
			spinnerPort.setAdapter(adapter); 
		}
		
		
		
		public void exeShell(String cmd){        
	          
            try{
                 Process p = Runtime.getRuntime().exec(cmd);
                 BufferedReader in = new BufferedReader(
                                     new InputStreamReader(
                               p.getInputStream())); 
                 String line = null;  
                 while ((line = in.readLine()) != null) {  
                    Log.i("exeShell",line);                  
                 }  
                  
            }
            catch(Throwable t)
             {
                  t.printStackTrace();
                 }
        }
		
		@Override
		public void onClick(View v) {
				
				// TODO Auto-generated method stub
	    		switch (v.getId()) {
	    		case 1:
	    			exeShell("chmod 666 /dev");
	    			exeShell("chmod 666 /dev/ttyUSB1");
	    			
	    			if(btnOpen.getText().toString().equals("打开"))
	    			{
	    				intBaud = Integer.parseInt(spinner.getSelectedItem().toString());
	    				if(printer.Open(spinnerPort.getSelectedItem().toString(),intBaud))
//	    				if(printer.Open("/dev/ttyACM2",intBaud))
	    				{
	    					btnOpen.setText("关闭");
	    					setBtnEnable(true);
	    				}
	    			}
	    			else
	    			{
	    				printer.Close();
	    				btnOpen.setText("打开");
	    				setBtnEnable(false);
	    			}
	    			break;
	    		case 2:
	    			printer.Write("-------------------------\n",printer.STRING);
	    			printer.Write("打印测试",printer.STRING);	
	    			printer.Write("\n",printer.STRING);
	    			//printer.Write("1B73009640",printer.CMD);
	    			printer.Write("-------------------------\n",printer.STRING);
	    			break;	 
	    		case 3:
	    			printer.Write("-------------------------\n",printer.STRING);
	    			printer.Write("1B5501",printer.CMD);
	    			printer.Write("测试倍宽 1倍\n",printer.STRING);
	    			printer.Write("1B5502",printer.CMD);
	    			printer.Write("测试倍宽 2倍\n",printer.STRING);
	    			printer.Write("1B5503",printer.CMD);
	    			printer.Write("测试倍宽 3倍\n",printer.STRING);
	    			printer.Write("1B5501",printer.CMD);
	    			printer.Write("-------------------------\n",printer.STRING);	    			
	    			break;
	    		case 4:
	    			printer.Write("-------------------------\n",printer.STRING);
	    			printer.Write("1B5601",printer.CMD);
	    			printer.Write("测试倍高 1倍\n",printer.STRING);
	    			printer.Write("1B5602",printer.CMD);
	    			printer.Write("测试倍高 2倍\n",printer.STRING);
	    			printer.Write("1B5603",printer.CMD);
	    			printer.Write("测试倍高 3倍\n",printer.STRING);
	    			printer.Write("1B5601",printer.CMD);
	    			printer.Write("-------------------------\n",printer.STRING);	    			
	    			break;
	    		case 5:
	    			printer.Write("-------------------------\n",printer.STRING);
	    			printer.Write("1C5701",printer.CMD);
	    			printer.Write("放大1倍\n",printer.STRING);
	    			printer.Write("1C5701",printer.CMD);
	    			
	    			printer.Write("1C5702",printer.CMD);
	    			printer.Write("放大2倍\n",printer.STRING);
	    			printer.Write("1C5701",printer.CMD);
	    			
	    			printer.Write("1C5703",printer.CMD);
	    			printer.Write("放大3倍\n",printer.STRING);
	    			printer.Write("1C5701",printer.CMD);
	    			
	    			printer.Write("1C5704",printer.CMD);
	    			printer.Write("放大4倍\n",printer.STRING);
	    			printer.Write("1C5701",printer.CMD);
	    			printer.Write("-------------------------\n",printer.STRING);
					break;
	    		case 6:
	    			printer.Write("-------------------------\n",printer.STRING);
	    			printer.Write("1C4A",printer.CMD);
	    			printer.Write("纵向打印\n",printer.STRING);
	    			//printer.Write("1C4B",printer.CMD);
	    			printer.Write("1C4900",printer.CMD);
	    			printer.Write("-------------------------\n",printer.STRING);
					break;
	    		case 7:
	    			printer.Write("-------------------------\n",printer.STRING);
	    			printer.Write("1C4900",printer.CMD);
	    			printer.Write("旋转0°\n",printer.STRING);
	    			
	    			printer.Write("1C4901",printer.CMD);
	    			printer.Write("旋转90°\n",printer.STRING);
	    			
	    			printer.Write("1C4901",printer.CMD);
	    			printer.Write("旋转180°\n",printer.STRING);
	    			
	    			printer.Write("1C4901",printer.CMD);
	    			printer.Write("旋转270°\n",printer.STRING);
	    			printer.Write("1C4900",printer.CMD);
	    			printer.Write("-------------------------\n",printer.STRING);
					break;
	    		case 8:
	    			printer.Write("-------------------------\n",printer.STRING);
	    			printer.Write("1C2D01",printer.CMD);
	    			printer.Write("下划线打印\n",printer.STRING);
	    			printer.Write("1C2D00",printer.CMD);
	    			printer.Write("-------------------------\n",printer.STRING);
					break;
	    		case 9:
	    			printer.Write("-------------------------\n",printer.STRING);
	    			printer.Write("1C2B01",printer.CMD);
	    			printer.Write("上划线打印\n",printer.STRING);
	    			printer.Write("1C2B00",printer.CMD);	    			
	    			printer.Write("-------------------------\n",printer.STRING);
					break;	
					
		    	case 10:
		    		printer.Write("-------------------------\n",printer.STRING);
	    			printer.Write("1D5A00",printer.CMD);
	    			printer.Write("1B5A03030605000201010102",printer.CMD);
	    			printer.Write("\n",printer.STRING);
	    			printer.Write("1D5A01",printer.CMD);
	    			printer.Write("1B5A03030605000201010102",printer.CMD);
	    			printer.Write("\n",printer.STRING);
	    			printer.Write("1D5A02",printer.CMD);
	    			printer.Write("1B5A03030605003132330102",printer.CMD);
	    			printer.Write("-------------------------\n",printer.STRING);
	    			break;
		    	case 11:
		    		printer.Write("1B73009640",printer.CMD);
		    		break;
		    		
		    	case 21:
		    		btnStart.setEnabled(false);
		    		btnStop.setEnabled(true);
		    		//strAscii = getASCIIString();
		    		String time = spinnerTime.getSelectedItem().toString();
		    		if(time.contains("1s"))
		    			intMs = 1000;
		    		else if(time.contains("5s"))
		    			intMs = 5000;
		    		else if(time.contains("10s"))
		    			intMs = 10000;
		    		else if(time.contains("20s"))
		    			intMs = 20000;
		    		else if(time.contains("1min"))
		    			intMs = 60 *1000;
		    		flagLoop = true;
		    		Log.i("##############","$$$$$$$$$$$$1"+intMs);
		    		ThreadLoopPrint mthread  = new ThreadLoopPrint();
		    		mthread.start();
		    		break;
		    		
		    	case 22:
		    		btnStart.setEnabled(true);
		    		btnStop.setEnabled(false);
		    		flagLoop = false;
		    		break;
		    		
		    	case 23:
		    		//printer.Write("1B411F10101010010205",printer.CMD);
		    		ThreadCheckCustormDisplay CustormDisplayThread = new ThreadCheckCustormDisplay();
		    		CustormDisplayThread.start();
		    		break;
		    		
		    	case 24:
		    		printer.Write("0C",printer.CMD);
		    		break;
		    		
		    	case 25:
		    		printer.Write("AF021F00FF00",printer.CMD);
		    		break;

		    	case 26:
		    		printer.Write("1B40",printer.CMD);
		    		break;
	    		}
		}
		/*
		String getASCIIString()
		{
			byte[] byteAscii = new byte[100];
			for(byte i = 21;i<=127;i++)
			{
				byteAscii[i-20] = i;
			}
			return byteAscii.toString();
		}
		*/
		
		void setBtnEnable(boolean flag)
		{
			btnPrint.setEnabled(flag);
			btnDoubleWidth.setEnabled(flag);
			btnDoubleHight.setEnabled(flag);
			btnEnlarge.setEnabled(flag);
			btnVertical.setEnabled(flag);
			btnTurn.setEnabled(flag);
			btnUnderline.setEnabled(flag);
			btnOverline.setEnabled(flag);
			btnBarcode.setEnabled(flag);	
			btnCarsh.setEnabled(flag);
			btnStart.setEnabled(flag);
			btnStop.setEnabled(false);
		}
}

class ThreadCheckCustormDisplay extends Thread{
	@Override
	public void run() {
		int intMs = 1000;
		NewPrinterActivity.printer.Write("1B411F01020304050607", NewPrinterActivity.printer.CMD);
		SystemClock.sleep(intMs);
		
		NewPrinterActivity.printer.Write("1B41110A0A0A0A050631", NewPrinterActivity.printer.CMD);
		SystemClock.sleep(intMs);
		
		NewPrinterActivity.printer.Write("1B41020A0A0A0A050607", NewPrinterActivity.printer.CMD);
		SystemClock.sleep(intMs);
		
		NewPrinterActivity.printer.Write("1B41040A0A0A0A0A0A07", NewPrinterActivity.printer.CMD);
		SystemClock.sleep(intMs);
		
		NewPrinterActivity.printer.Write("1B41080A0A0A0A0A0A07", NewPrinterActivity.printer.CMD);
		SystemClock.sleep(intMs);
		
		NewPrinterActivity.printer.Write("1B411F01020304050607", NewPrinterActivity.printer.CMD);
		SystemClock.sleep(intMs);
	}
}

class ThreadLoopPrint extends Thread{
	@Override
	public void run() {
		NewPrinterActivity.intSendSize = 0;
		while(NewPrinterActivity.flagLoop)
		{
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("普融科技打印测试普融科技打\n", NewPrinterActivity.printer.STRING);
			
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("-------------------------\n", NewPrinterActivity.printer.STRING);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("1D5A00", NewPrinterActivity.printer.CMD);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("1B5A03030605000201010102", NewPrinterActivity.printer.CMD);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("\n", NewPrinterActivity.printer.STRING);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("1D5A01", NewPrinterActivity.printer.CMD);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("1B5A03030605000201010102", NewPrinterActivity.printer.CMD);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("\n", NewPrinterActivity.printer.STRING);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("1D5A02", NewPrinterActivity.printer.CMD);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("1B5A03030605003132330102", NewPrinterActivity.printer.CMD);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("-------------------------\n", NewPrinterActivity.printer.STRING);
			
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("-------------------------\n", NewPrinterActivity.printer.STRING);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("1B5501", NewPrinterActivity.printer.CMD);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("测试倍宽 1倍\n", NewPrinterActivity.printer.STRING);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("1B5502", NewPrinterActivity.printer.CMD);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("测试倍宽 2倍\n", NewPrinterActivity.printer.STRING);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("1B5503", NewPrinterActivity.printer.CMD);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("测试倍宽 3倍\n", NewPrinterActivity.printer.STRING);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("1B5501", NewPrinterActivity.printer.CMD);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("-------------------------\n", NewPrinterActivity.printer.STRING);
			
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("-------------------------\n", NewPrinterActivity.printer.STRING);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("1B5601", NewPrinterActivity.printer.CMD);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("测试倍高 1倍\n", NewPrinterActivity.printer.STRING);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("1B5602", NewPrinterActivity.printer.CMD);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("测试倍高 2倍\n", NewPrinterActivity.printer.STRING);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("1B5603", NewPrinterActivity.printer.CMD);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("测试倍高 3倍\n", NewPrinterActivity.printer.STRING);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("1B5601", NewPrinterActivity.printer.CMD);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("-------------------------\n", NewPrinterActivity.printer.STRING);
			
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("-------------------------\n", NewPrinterActivity.printer.STRING);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("1C5701", NewPrinterActivity.printer.CMD);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("放大1倍\n", NewPrinterActivity.printer.STRING);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("1C5701", NewPrinterActivity.printer.CMD);
			
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("1C5702", NewPrinterActivity.printer.CMD);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("放大2倍\n", NewPrinterActivity.printer.STRING);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("1C5701", NewPrinterActivity.printer.CMD);
			
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("1C5703", NewPrinterActivity.printer.CMD);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("放大3倍\n", NewPrinterActivity.printer.STRING);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("1C5701", NewPrinterActivity.printer.CMD);
			
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("1C5704", NewPrinterActivity.printer.CMD);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("放大4倍\n", NewPrinterActivity.printer.STRING);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("1C5701", NewPrinterActivity.printer.CMD);
			NewPrinterActivity.intSendSize += NewPrinterActivity.printer.Write("-------------------------\n", NewPrinterActivity.printer.STRING);
					
			Message message = new Message();
	    	message.what = 1;
	    	NewPrinterActivity.mhandler.sendMessage(message);
			
			SystemClock.sleep(NewPrinterActivity.intMs);
		}
	}
}

class Mhandler extends Handler{
	
	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
		switch (msg.what){
		case 1:
			NewPrinterActivity.textSendData.setText(String.valueOf(NewPrinterActivity.intSendSize));
			break;
		}		   	
	}
}