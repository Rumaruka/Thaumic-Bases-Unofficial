package com.rumaruka.tb.utils;

public class OnetimeCaller {

    // Code taking in HammerCore by APengu (Sorry, dude)
    private Runnable call;

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
