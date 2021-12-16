package com.example.aptitude;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int n1,n2,n3,n4,n5,x,y;
    int ANS,userop,op1=0,op=0;    // n = number ; ANS = correct answer ; userop = User option;
    int t,f,n=0,m,option=0;     // t = true   ; f = false ; n = no of questions ; m = no.of questions to find time per question as n constantly changes.
    int flag=0;
    
    TextView que;         // we can create object of a view in this class and use it in different methods.
    EditText count;          // we can Initialize the object only after the setContentView method in onCreate method.
    TextView remarks;
    Button start,next,reset,check;
    RadioButton rb1,rb2,rb3,rb4;
    RadioGroup rg;

    // For stop watch
    Chronometer chronometer;
    boolean running=false;
    long pauseoffset;






    public void setQuestion() {
        Random r=new Random();
        String opstr=" ";
        // we have to reset the t and f variables .
        // Random numbers generated
        n1=r.nextInt(90)+10;   // n1 and n2 for addition and subtraction
        n2=r.nextInt(90)+10;
        n3=r.nextInt(10)+5;    //n3 and n4 for multiplication and division
        n4=r.nextInt(10)+5;
        n5=r.nextInt(4)+1;     // n5 for deciding the option for answer. (1,2,3,4)

        // To avoid repeating same number.(operator)
        while(op1==op){
            op=r.nextInt(3)+1;
        }
        op1=op;

        if(op<=3){
            switch (op){
                case 1:
                    opstr=" + ";
                    x=n1;
                    y=n2;
                    ANS=x+y;
                    break;
                case 2:
                    opstr=" - ";
                    x=n1;
                    y=n2;
                    ANS=x-y;
                    break;
                case 3:
                    opstr=" X ";
                    x=n3;
                    y=n4;
                    ANS=x*y;
                    break;
            }

        }
        else{
            opstr=" / ";
            x=n3*n4;
            y=n4;
            ANS=n3;

        }
        que.setText(x+opstr+y+"  =");                  //  The question will be displayed in que TextField.



        option=0;
        /*switch(n5){
            case 1:
                option=1;
                rb1.setText(Integer.toString(ANS));
                rb2.setText(Integer.toString(r.nextInt(20)+ANS-10));
                rb3.setText(Integer.toString(r.nextInt(20)+ANS-10));
                rb4.setText(Integer.toString(r.nextInt(20)+ANS-10));
                break;
            case 2:
                option=2;
                rb2.setText(Integer.toString(ANS));
                rb1.setText(Integer.toString(r.nextInt(20)+ANS-10));
                rb3.setText(Integer.toString(r.nextInt(20)+ANS-10));
                rb4.setText(Integer.toString(r.nextInt(20)+ANS-10));
                break;
            case 3:
                option=3;
                rb3.setText(Integer.toString(ANS));
                rb1.setText(Integer.toString(r.nextInt(20)+ANS-10));
                rb2.setText(Integer.toString(r.nextInt(20)+ANS-10));
                rb4.setText(Integer.toString(r.nextInt(20)+ANS-10));
                break;
            case 4:
                option=4;
                rb4.setText(Integer.toString(ANS));
                rb1.setText(Integer.toString(r.nextInt(20)+ANS-10));
                rb2.setText(Integer.toString(r.nextInt(20)+ANS-10));
                rb3.setText(Integer.toString(r.nextInt(20)+ANS-10));
                break;

        }
*/
        
        String temp="";
        int ch1=0,ch2=0,ch3=0,ch4=0;    // variables used to create choices that will not be the same.
        // Creating array so that we can use desired radio button in for loop by passing the integer i to array 'rba[]'.
        RadioButton rba[]=new RadioButton[5];
        rba[1]=rb1;
        rba[2]=rb2;
        rba[3]=rb3;
        rba[4]=rb4;
        // For setting the options for a question.
        for(int i=1;i<=4;i++){
            if(i==n5){
                option=i;
                rba[i].setText(Integer.toString(ANS));
            }
            else{
                // To make sure that any two options will not be same,So that we won't have two answers.
                while(ch1==ch3 || ch2==ch3 || ch3==ANS) {
                    ch3=r.nextInt(10)+ANS-10;
                }
                ch1=ch2;
                ch2=ch3;
                // Setting the option which is not repeated.
                rba[i].setText(Integer.toString(ch3));
            }
        }
        


    }




    public void start(View view){

        if(count.getText().toString().trim().length()==0){     // To check whether the no of questions field is empty or not .
            Toast.makeText(this, "Please Enter no of Questions", Toast.LENGTH_SHORT).show();
        }


        else if(n!=0 && flag==1){                                        // TO make sure that start button will not accept  "no of questions field"  before completing the previous questions.
            Toast.makeText(this, "First Complete the questions", Toast.LENGTH_SHORT).show();
        }

        else {


            n = Integer.parseInt(count.getText().toString());
            if (n == 0) {
                Toast.makeText(this, "Number should be greater than 0", Toast.LENGTH_SHORT).show();
            } else {

                // for stop watch
//                if (!running) {
//                    chronometer.setBase(SystemClock.elapsedRealtime());
//                    chronometer.start();
//                    running = true;
//                }
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    if(running)
                       chronometer.stop();
                    else
                        chronometer.start();




                flag = 1;
                remarks.setVisibility(View.INVISIBLE);
                next.setVisibility(View.VISIBLE);
                reset.setVisibility(View.VISIBLE);
                que.setVisibility(View.VISIBLE);


                m = n;
                t = 0;                                             // total no of questions are calculated on basis of t+f (true+flase).
                f = 0;                                             // when we want to run the questions for the second time by entering "no of questions" ,
                this.setQuestion();
                rg.setVisibility(View.VISIBLE);


            }
        }

     //  To disable start button for some time
        start.setEnabled(false);
        start.postDelayed(new Runnable() {
            public void run() {
                start.setEnabled(true);          // If the start button object is created in this class.There is unknown error
                                                 // suggesting to make the object to final.'final Button start=...'
            }
        }, 1000);

    }




    public void next(View view){
        if(rg.getCheckedRadioButtonId()==-1){     // to check whether the answer field is empty or not.
            Toast.makeText(this, "Must Choose an option", Toast.LENGTH_SHORT).show();     // For pop up message.
        }

        else{                                                 // If the answer is not empty
            n--;                                              // 1. no of questions remaining will be decremented by 1.
            count.setText(Integer.toString(n));

            if(rb1.isChecked()==true){
                userop=Integer.parseInt(rb1.getTag().toString());
            }
            else if(rb2.isChecked()==true){
                userop=Integer.parseInt(rb2.getTag().toString());
            }
            else if(rb3.isChecked()==true){
                userop=Integer.parseInt(rb3.getTag().toString());
            }
            else{
                userop=Integer.parseInt(rb4.getTag().toString());
            }


            if(option==userop){                                       // 2. The answer will be verified.
                t++;
            }
            else{
                f++;
            }

            if(n>0){                                         // 4. If the no of questions remaining is not equal to 0. New question will be generated.
                this.setQuestion();

            }
            else{                                             // 5. If the no of questions remaining is 0 then
                //For stop watch
//                if(running){
//                    chronometer.stop();
//                    pauseoffset=SystemClock.elapsedRealtime()-chronometer.getBase();
//                    running=false;
//                }

                chronometer.stop();
                pauseoffset=SystemClock.elapsedRealtime()-chronometer.getBase();
                running=true;



                que.setText("completed");                                                            // 5.1 . Message will be Displayed as Completed.
                rg.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "Enter the no of questions to try again", Toast.LENGTH_LONG).show();
                remarks.setText("No of Questions completed : "+(t+f)+"\nCorrect : "+t+"\nIncorrect : "+f+"\nTotal Time : "+(pauseoffset/1000.0)+"  sec"+"\nTime per Question: "+(pauseoffset/m)/1000.0+"  sec");   // 5.3 . The output that is supposed to be shown when reset
                remarks.setVisibility(View.VISIBLE);                                                                          // button is pressed is displayed.
                count.setText("0");                           // 5.4 . the no of questions field is set to '0'. So that start button can take input Entered.
                flag=0;

                Log.i("TAG", "next: "+(pauseoffset/1000.0));

                next.setVisibility(View.INVISIBLE);




            }

            rg.clearCheck();    // Removes any checked buttons.

        }


        next.setEnabled(false);
        next.postDelayed(new Runnable() {
            public void run() {
                next.setEnabled(true);          // If the start button object is created in this class.There is unknown error
                // suggesting to make the object to final.'final Button start=...'
            }
        }, 500);


    }




    public void reset(View view){
        n=0;
        flag=0;
        count.setText(Integer.toString(n));
        rg.setVisibility(View.INVISIBLE);
        que.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);
        remarks.setVisibility(View.INVISIBLE);
        running=true;


        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.stop();




        reset.setEnabled(false);
        reset.postDelayed(new Runnable() {
            public void run() {
                reset.setEnabled(true);          // If the start button object is created in this class.There is unknown error
                // suggesting to make the object to final.'final Button start=...'
            }
        }, 1000);
    }










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        que=(TextView)findViewById(R.id.que);
        count=(EditText)findViewById(R.id.count);
        remarks=(TextView)findViewById(R.id.remarks);
        start=(Button)findViewById(R.id.start);
        reset=(Button)findViewById(R.id.reset);
        next=(Button)findViewById(R.id.next);
        rb1=(RadioButton) findViewById(R.id.rb1);
        rb2=(RadioButton) findViewById(R.id.rb2);
        rb3=(RadioButton) findViewById(R.id.rb3);
        rb4=(RadioButton) findViewById(R.id.rb4);
        rg=(RadioGroup)findViewById(R.id.rg);

        chronometer=findViewById(R.id.chronometer);

    }
}
