package com.ouc.dcrms.util;

//保存需采集数据的设备信息
public class InstrumentArray {

    private int instrumentID = 0;
    
    private String sensorsList;  // 传感器序列
    
    private Instrument instrument;

    public int getInstrumentID() {
	return instrumentID;
    }

    public void setInstrumentID(int instrumentID) {
	this.instrumentID = instrumentID;
    }

    public String getSensorsList() {
	return sensorsList;
    }

    public void setSensorsList(String sensorsList) {
	this.sensorsList = sensorsList;
    }

    public Instrument getInstrument() {
	return instrument;
    }

    public void setInstrument(Instrument instrument) {
	this.instrument = instrument;
    }
}
