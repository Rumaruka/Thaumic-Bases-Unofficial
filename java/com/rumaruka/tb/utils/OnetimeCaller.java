package com.rumaruka.tb.utils;

public class OnetimeCaller {

    // Code taking in HammerCore by Zeith (Sorry, dude)
    public Runnable call;

    public OnetimeCaller(Runnable run)
    {
        call = run;
    }

    public void call()
    {
        if(call != null)
            call.run();
        call = null;
    }
}
