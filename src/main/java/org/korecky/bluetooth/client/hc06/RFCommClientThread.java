package org.korecky.bluetooth.client.hc06;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

/**
 *
 * @author vkorecky
 */
public class RFCommClientThread extends Thread {

    String clientURL;

    public RFCommClientThread(String clientURL) {
        this.clientURL = clientURL;
    }

    public void run() {
        try {
            LocalDevice local = LocalDevice.getLocalDevice();
            System.out.println("Address:" + local.getBluetoothAddress() + "+n" + local.getFriendlyName());
            while (true) {
                StreamConnection con = (StreamConnection) Connector.open(clientURL);
                OutputStream os = con.openOutputStream();
                InputStream is = con.openInputStream();
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader bufReader = new BufferedReader(isr);
                RemoteDevice dev = RemoteDevice.getRemoteDevice(con);

                /**
                 * if (dev !=null) { File f = new File("test.xml"); InputStream
                 * sis = new FileInputStream("test.xml"); OutputStream oo = new
                 * FileOutputStream(f); byte buf[] = new byte[1024]; int len;
                 * while ((len=sis.read(buf))>0 oo.write(buf,0,len);
                 * sis.close(); } *
                 */
                if (con != null) {
                    while (true) {
//                        //sender string
//                        System.out.println("Serverd:" + dev.getBluetoothAddress() + "\r\n" + "Put your string" + "\r\n");
//                        String str = bufReader.readLine();
//                        os.write(str.getBytes());
                        //reciever string
                        byte buffer[] = new byte[1024];
                        int bytes_read = is.read(buffer);
                        String received = new String(buffer, 0, bytes_read);
                        System.out.println(String.format("%s: %s", String.valueOf(dev.getBluetoothAddress()), received));
                    }
                }
            }
        } catch (Exception e) {
            System.err.print(e.toString());
        }
    }
}
