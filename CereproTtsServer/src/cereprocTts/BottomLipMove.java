package cereprocTts;

import urbi.UClient;

public class BottomLipMove extends Thread {

    // private Main reeticlient;
    private Boolean runflag;
    private UClient client;

    public BottomLipMove(UClient client) {
        // this.reeticlient = reeticlient;
        runflag = true;
        this.client = client;
    }

    public void setrunflag(Boolean b) {
        runflag = b;
    }

    @Override
    public void run() {
        while (runflag) {
            System.out.println("Lip Move");

            synchronized (client) {
                client.send("Global.servo.bottomLip = 40.0 smooth:0.3s;");
            }
            try {
                sleep(300);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            synchronized (client) {
                client.send("Global.servo.bottomLip = 75.0 smooth:0.3s;");
            }
            try {
                sleep(300);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

}
