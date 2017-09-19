package com.pr.m1card;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DialerFilter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity  {

    static AndroidSDK mAndroidSDK;

    private Spinner mSpinnerPort;
    private Spinner mSpinnerBaud;
    private Spinner mSpinnerData;
    private Spinner mSpinnerStop;
    private Spinner mSpinnerParity;

    private Spinner mSpinnerBlockNum;
    private Spinner mSpinnerBlockNumWrite;
    private Spinner mSpinnerBlockNum1;
    private Spinner mSpinnerBlockNum2;

    private Button mOpenSerialPort;
    private Button mCloseUart;

    private Button mGetCardNum;
    private Button mGetModel;
    private Button mCloseModel;
    private Button mRfReset;
    private Button mRestartModel;
    private Button mRequest;
    private Button mAnticoll;
    private Button mSelectCard;
    private Button mAuthentication;
    private Button mReadBlock;
    private Button mWriteBlock;
    private Button mReadValue;
    private Button mAddValue;
    private Button mSubtractValue;
    private Button mInitValue;
    private Button mTransmitValue;

    private TextView mCardNum;
    private TextView mModelVersion;
    private TextView mAnticollText;
    private TextView mReadBlockText;

    private EditText mWriteBlockText;
    private EditText mWriteBlockTextNum;

    private String url;
    private int baud;
    private int dataBits;
    private int stopBits;
    private int parity;

    static boolean flagLoop;
    static int intMs;

    private String cardNum; // 获取卡号


    /**
     * 打开串口时获取的句柄
     */
    int hSerial = -1;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            String s = (String) msg.obj;
            mCardNum.setText(s);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        mAndroidSDK = new AndroidSDK();

//        initializeButton();
//        initTextView();
//        initSpinner();
        findPort();

        setButtonStatus(true);
    }

//    private void initSpinner() {
//        mSpinnerPort = (Spinner) findViewById(R.id.spinner_port);
//        mSpinnerBaud = (Spinner) findViewById(R.id.spinner_baud);
//        mSpinnerData = (Spinner) findViewById(R.id.spinner_data);
//        mSpinnerStop = (Spinner) findViewById(R.id.spinner_stop);
//        mSpinnerParity = (Spinner) findViewById(R.id.spinner_parity);
//        mSpinnerBlockNum = (Spinner) findViewById(R.id.spinner_block_num);
//        mSpinnerBlockNumWrite = (Spinner) findViewById(R.id.spinner_block_num_write);
//        mSpinnerBlockNum1 = (Spinner) findViewById(R.id.spinner_block_num1);
//        mSpinnerBlockNum2 = (Spinner) findViewById(R.id.spinner_block_num2);
//
//    }

//    private void initTextView() {
//        mCardNum = (TextView) findViewById(R.id.tv_get_cardnum);
//        mModelVersion = (TextView) findViewById(R.id.tv_get_model);
//        mAnticollText = (TextView) findViewById(R.id.tv_anticoll);
//        mReadBlockText = (TextView) findViewById(R.id.tv_read_block);
//        mWriteBlockText = (EditText) findViewById(R.id.et_write_block);
//        mWriteBlockTextNum = (EditText) findViewById(R.id.et_write_block_num);
//
//    }

    private void findPort() {
        List<String> list = new ArrayList<String>();
        String strKey = "tty";
        File file = new File("/dev/");
        File[] files = file.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].toString().contains(strKey)) {
                    list.add(files[i].toString());
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerPort.setAdapter(adapter);

    }

//    private void initializeButton() {
//        mOpenSerialPort = (Button) findViewById(R.id.btn_openSerialPort);
//        mOpenSerialPort.setOnClickListener(this);
//
//        mCloseUart = (Button) findViewById(R.id.btn_closeSerialPort);
//        mCloseUart.setOnClickListener(this);
//
//        mGetCardNum = (Button) findViewById(R.id.btn_get_cardnum);
//        mGetCardNum.setOnClickListener(this);
//
//        mGetModel = (Button) findViewById(R.id.btn_get_model);
//        mGetModel.setOnClickListener(this);
//
//        mCloseModel = (Button) findViewById(R.id.btn_close_model);
//        mCloseModel.setOnClickListener(this);
//
//        mRestartModel = (Button) findViewById(R.id.btn_restart_model);
//        mRestartModel.setOnClickListener(this);
//
//        mRfReset = (Button) findViewById(R.id.btn_rfreset);
//        mRfReset.setOnClickListener(this);
//
//        mRequest = (Button) findViewById(R.id.btn_request);
//        mRequest.setOnClickListener(this);
//
//        mAnticoll = (Button) findViewById(R.id.btn_anticoll);
//        mAnticoll.setOnClickListener(this);
//
//        mSelectCard = (Button) findViewById(R.id.btn_select_card);
//        mSelectCard.setOnClickListener(this);
//
//        mAuthentication = (Button) findViewById(R.id.btn_authentication);
//        mAuthentication.setOnClickListener(this);
//
//        mReadBlock = (Button) findViewById(R.id.btn_read_block);
//        mReadBlock.setOnClickListener(this);
//
//        mWriteBlock = (Button) findViewById(R.id.btn_write_block);
//        mWriteBlock.setOnClickListener(this);
//
//        mInitValue = (Button) findViewById(R.id.btn_init_block);
//        mInitValue.setOnClickListener(this);
//
//        mReadValue = (Button) findViewById(R.id.btn_read);
//        mReadValue.setOnClickListener(this);
//
//        mAddValue = (Button) findViewById(R.id.btn_add);
//        mAddValue.setOnClickListener(this);
//
//        mSubtractValue = (Button) findViewById(R.id.btn_subtract);
//        mSubtractValue.setOnClickListener(this);
//
//        mTransmitValue = (Button) findViewById(R.id.btn_transmit);
//        mTransmitValue.setOnClickListener(this);
//
//    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//
//            case R.id.btn_openSerialPort:
//
//                url = mSpinnerPort.getSelectedItem().toString();
//                baud = Integer.parseInt(mSpinnerBaud.getSelectedItem().toString());
//                dataBits = Integer.parseInt(mSpinnerData.getSelectedItem()
//                        .toString());
//                stopBits = Integer.parseInt(mSpinnerStop.getSelectedItem()
//                        .toString());
//                parity = Integer.parseInt(mSpinnerParity.getSelectedItem()
//                        .toString());
//
//                hSerial = mAndroidSDK.OpenUart(url, baud, dataBits, stopBits,
//                        parity);
//
//                if (hSerial > 0) {
//                    setButtonStatus(true);
//                }
//                break;
//
//            case R.id.btn_closeSerialPort:
//                mAndroidSDK.CloseUart(hSerial);
//                setButtonStatus(false);
//                break;
//
//            case R.id.btn_get_cardnum:
//                new Thread() {
//                    public void run() {
//                        //for (int i = 0 ;i<10;i++){
//                        Message msg = new Message();
//                        msg.obj = mAndroidSDK.getCardNum().substring(18, 26);
//                        handler.sendMessage(msg);
//                        //}
//                    }
//                }.start();
//
////			cardNum = mAndroidSDK.getCardNum();
////			cardNum = cardNum.substring(18, 26);
////			mCardNum.setText(cardNum);
//
//
//                break;
//
//            case R.id.btn_get_model:
//                mModelVersion.setText(mAndroidSDK.getVersion());
//                break;
//
//            case R.id.btn_close_model:
//                if (mAndroidSDK.closeModule().equals("4141")) {
//                    Toast.makeText(this, "模块关闭成功", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "模块关闭失败", Toast.LENGTH_SHORT).show();
//                }
//                break;
//
//            case R.id.btn_restart_model:
//                if (mAndroidSDK.moduleWarmReset().equals("4242")) {
//                    Toast.makeText(this, "模块重启成功", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "模块重启失败", Toast.LENGTH_SHORT).show();
//                }
//                break;
//
//            case R.id.btn_rfreset:
//                if (mAndroidSDK.rfReset().equals("4343")) {
//                    Toast.makeText(this, "射频重启成功", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "射频重启失败", Toast.LENGTH_SHORT).show();
//                }
//                break;
//
//            case R.id.btn_request:
//                if (mAndroidSDK.request().equals("0400")) {
//                    Toast.makeText(this, "有卡在射频范围内", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "无卡", Toast.LENGTH_SHORT).show();
//                }
//                break;
//
//            case R.id.btn_anticoll:
//                mAnticollText.setText(mAndroidSDK.anticoll());
//                break;
//
//            case R.id.btn_select_card:
//                // TODO:后期询问选卡有什么用
//                mAndroidSDK.selectCard();
//                break;
//
//            case R.id.btn_authentication:
//                if (mAndroidSDK.authentication().equals("4848")) {
//                    Toast.makeText(this, "认证成功", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "认证失败", Toast.LENGTH_SHORT).show();
//                }
//                break;
//
//            case R.id.btn_read_block:
//                mReadBlockText.setText(mAndroidSDK.readBlock(mSpinnerBlockNum
//                        .getSelectedItem().toString()));
//                break;
//
//            case R.id.btn_write_block:
//                String str = mWriteBlockText.getText().toString();
//                if (str.matches("[A-Za-z0-9_]+") && str.length() == 32) {
//                    if (mAndroidSDK
//                            .writeBlock(
//                                    mSpinnerBlockNumWrite.getSelectedItem()
//                                            .toString(), str).equals("00")) {
//                        Toast.makeText(this, "写入成功", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(this, "写入失败", Toast.LENGTH_SHORT).show();
//                    }
//                } else if (str.length() != 32) {
//                    Toast.makeText(this, "输入长度不正确", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "只能输入十六进制数", Toast.LENGTH_SHORT).show();
//                }
//
//                break;
//
//            case R.id.btn_init_block:
//                mAndroidSDK.initBlock(mSpinnerBlockNum1.getSelectedItem()
//                        .toString(), "00000000");
//
//                break;
//
//            case R.id.btn_add:
//                String inputValue = mWriteBlockTextNum.getText().toString();
//
//                String returnValue = mAndroidSDK
//                        .addBlock(mSpinnerBlockNum1.getSelectedItem().toString(),
//                                Integer.parseInt(inputValue));
//
//                Toast.makeText(this, returnValue, Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.btn_subtract:
//                String subtractValue = mWriteBlockTextNum.getText().toString();
//                String strSub = mAndroidSDK
//                        .subractBlock(mSpinnerBlockNum1.getSelectedItem().toString(),
//                                Integer.parseInt(subtractValue));
//
//                Toast.makeText(this, strSub, Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.btn_transmit:
//
//                String strTransmit = mAndroidSDK
//                        .transmitBlock(mSpinnerBlockNum1.getSelectedItem().toString(),
//                                mSpinnerBlockNum2.getSelectedItem().toString());
//
//                Toast.makeText(this, strTransmit, Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.btn_read:
//                String strRead = mAndroidSDK.readBlockToNumber(mSpinnerBlockNum1.getSelectedItem().toString());
//
//                AlertDialog.Builder builder = new Builder(this);
//                builder.setMessage("当前数值： " + strRead);
//                builder.setTitle("提示");
//                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                builder.show();
//
//        }
//    }

    private void setButtonStatus(boolean flag) {
        mOpenSerialPort.setEnabled(!flag);
        mCloseUart.setEnabled(flag);

    }
}
