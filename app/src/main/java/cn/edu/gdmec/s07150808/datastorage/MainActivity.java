package cn.edu.gdmec.s07150808.datastorage;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.SharedPreferences;
import android.os.Environment;
import android.renderscript.Element;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity {
    private EditText username,password;
  private TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username= (EditText) findViewById(R.id.username);
        password= (EditText) findViewById(R.id.password);
        tv1= (TextView) findViewById(R.id.tv1);
    }
    public void spWrite(View v){
        SharedPreferences user=getSharedPreferences("user",MODE_APPEND);
        SharedPreferences.Editor editor=user.edit();
        editor.putString("username",username.getText().toString().trim());
        editor.putString("password",password.getText().toString().trim());
        editor.commit();
        Toast.makeText(this,"SharedPreferences写入成功",Toast.LENGTH_LONG).show();

    }
    public  void spRead(View v){
        SharedPreferences user=getSharedPreferences("user",MODE_PRIVATE);
        String name=user.getString("username","");
        String pass=user.getString("password","");
        tv1.setText("用户名"+name+"   "+"密码"+pass);
        Toast.makeText(this,"SharedPreferences读取",Toast.LENGTH_LONG).show();
    }
    public void ROMWrite(View v){
        String name=username.getText().toString().trim();
        String pass=password.getText().toString().trim();
        try {
            FileOutputStream fos=openFileOutput("user.txt",MODE_APPEND);
            OutputStreamWriter osw=new OutputStreamWriter(fos);
            BufferedWriter bw=new BufferedWriter(osw);
            bw.write(name+":"+pass);
            bw.flush();
            fos.close();
            Toast.makeText(this,"ROW写入成功",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void ROMRead(View v){
        try {

            FileInputStream fis=openFileInput("user.txt");
          InputStreamReader isr=new InputStreamReader(fis);
            BufferedReader br=new BufferedReader(isr);
         StringBuffer sb=new StringBuffer();
            String s;
            while((s=br.readLine())!=null){
                sb.append(s+"\n");
            }

            fis.close();
            tv1.setText(sb);
            Toast.makeText(this,"ROW读取成功",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void SDWrite(View v){
        String str=username.getText().toString().trim()+":"+password.getText().toString().trim();
        String sdcardroot= Environment.getExternalStorageDirectory().getAbsolutePath();
        String filename=sdcardroot+"/Text.txt";
        File file=new File(filename);
        try {
            FileOutputStream fos=new FileOutputStream(file);
            fos.write(str.getBytes());
            fos.flush();
            fos.close();
            Toast.makeText(this,"SD卡写入成功!",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void SDRead(View v){
        //  获取到外部存储的目录
        String sdcardroot=Environment.getExternalStorageDirectory().getAbsolutePath();
        String filename=sdcardroot+"/Text.txt";
        File file=new File(filename);
        int length= (int) file.length();
        byte []b= new byte[length];
        try {
            FileInputStream fls=new FileInputStream(file);
            fls.read(b,0,length);
            fls.close();
            tv1.setText(new String(b));

            Toast.makeText(this,"SD卡的读取",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
