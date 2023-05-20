package com.astroexpress.user.utility;

import android.os.CountDownTimer;

public class DynamicCountdownTimer {

    private CountDownTimer timer = null;
    private double negativeBias = 0.00;
    private double addingBias = 0.00;
    private int minutes = 0;
    private int ticks = 0;
    private boolean supressFinish = false;

    public DynamicCountdownTimer(int minutes, int ticks){

        setTimer(minutes, ticks);
    }

    public void updateMinutes(int minutes){
        if (timer != null){
            this.supressFinish = true;
            this.timer.cancel();
            this.timer = null;
            this.minutes = minutes;
            this.addingBias = this.negativeBias + this.addingBias;
            setTimer(this.minutes, this.ticks);
            Start();
        }
    }

    public void setTimer(int minutes, int ticks){
        this.minutes = minutes;
        this.ticks = ticks;
        timer = new CountDownTimer((minutes * 60 * 1000), ticks) {
            @Override
            public void onTick(long l) {
                negativeBias = (minutes * 60 * 1000) - l;
                long calculatedTime = l - (long)addingBias;
                if (calculatedTime <= 0){
                    onFinish();
                }else{
                    callback.onTick(calculatedTime);
                }
            }

            @Override
            public void onFinish() {
                if (!supressFinish){
                    callback.onFinish();
                }
                supressFinish = false;
            }
        };
    }

    public void Start(){
        if (timer != null){
            timer.start();
        }
    }

    public void Cancel(){
        if (timer != null){
            timer.cancel();
        }
    }


    public DynamicCountdownCallback callback = null;

    public void setDynamicCountdownCallback(DynamicCountdownCallback c){
        callback = c;
    }


    public interface DynamicCountdownCallback {
        void onTick(long l);
        void onFinish();
    }
}
