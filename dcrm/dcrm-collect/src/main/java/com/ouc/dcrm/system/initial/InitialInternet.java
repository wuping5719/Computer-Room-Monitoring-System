package com.ouc.dcrm.system.initial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.ouc.dcrm.system.util.Instrument;

/**
 * @author WuPing
 * @version 2016年12月20日 下午4:23:11
 */

public class InitialInternet {
    public Socket server;
    public BufferedReader in;
    public PrintWriter out;
    public int port;
    public String IPAddress;

    public InitialInternet(Instrument instrument) {
	this.port = instrument.commInterface.port;
	this.IPAddress = instrument.commInterface.IPAddress;

	try {
	    if (instrument.attribution.model.equals("DA100")
		    || instrument.attribution.model.equals("MX100")) {
		server = null;
	    } else {
		server = new Socket(IPAddress, port);
	    }
	} catch (UnknownHostException e) {
	    server = null;
	} catch (IOException e) {
	    server = null;
	}
	
	try {
	    if (server == null) {
		in = null;
	    } else {
		in = new BufferedReader(new InputStreamReader(
			server.getInputStream()));
	    }
	} catch (IOException e) {
	    in = null;
	}
	
	try {
	    if (server == null) {
		out = null;
	    } else {
		out = new PrintWriter(server.getOutputStream());
	    }
	} catch (IOException e) {
	    out = null;
	}
    }

    public void closeSocket() throws IOException {
	server.close();
    }
}
