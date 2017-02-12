package com.ouc.dcrms.collect.initial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.ouc.dcrms.collect.util.Instrument;

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
	this.port = instrument.getCommInterface().getPort();
	this.IPAddress = instrument.getCommInterface().getIPAddress();

	try {
	    if (instrument.getAttribution().getModel().equals("DA100")
		    || instrument.getAttribution().getModel().equals("MX100")) {
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
