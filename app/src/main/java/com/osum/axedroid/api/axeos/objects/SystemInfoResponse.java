package com.osum.axedroid.api.axeos.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
{
	"power":	28.347776412963867,
	"voltage":	5335.9375,
	"current":	19187.5,
	"temp":	59.875,
	"vrTemp":	63,
	"maxPower":	40,
	"nominalVoltage":	5,
	"hashRate":	1670.6980490399619,
	"bestDiff":	"5.56G",
	"bestSessionDiff":	"89.6M",
	"stratumDiff":	2330,
	"isUsingFallbackStratum":	0,
	"isPSRAMAvailable":	1,
	"freeHeap":	8395120,
	"coreVoltage":	1220,
	"coreVoltageActual":	1201,
	"frequency":	850,
	"ssid":	"",
	"macAddr":	"",
	"hostname":	"bitaxe",
	"wifiStatus":	"Connected!",
	"wifiRSSI":	-69,
	"apEnabled":	0,
	"sharesAccepted":	12136,
	"sharesRejected":	191,
	"sharesRejectedReasons":	[{
			"message":	"Invalid share",
			"count":	191
		}],
	"uptimeSeconds":	79342,
	"asicCount":	1,
	"smallCoreCount":	2040,
	"ASICModel":	"BM1370",
	"stratumURL":	"",
	"fallbackStratumURL":	"",
	"stratumPort":	3333,
	"fallbackStratumPort":	3333,
	"stratumUser":	"",
	"fallbackStratumUser":	"",
	"version":	"v2.7.0",
	"idfVersion":	"v5.4.1",
	"boardVersion":	"601",
	"runningPartition":	"ota_1",
	"flipscreen":	1,
	"overheat_mode":	0,
	"overclockEnabled":	1,
	"invertscreen":	0,
	"displayTimeout":	-1,
	"autofanspeed":	1,
	"fanspeed":	29,
	"temptarget":	60,
	"fanrpm":	1264
}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemInfoResponse {
    public double power;
    public double voltage;
    public double current;
    public double temp;
    public double vrTemp;
    public int maxPower;
    public int nominalVoltage;
    public double hashRate;
    public String bestDiff;
    public String bestSessionDiff;
    public int stratumDiff;
    public int isUsingFallbackStratum; //boolean
    public int isPSRAMAvailable; // boolean
    public int freeHeap;
    public int coreVoltage;
    public int coreVoltageActual;
    public int frequency;
    public String ssid;
    public String macAddr;
    public String hostname;
    public String wifiStatus;
    public int wifiRSSI;
    public int apEnabled; //boolean
    public int sharesAccepted;
    public int sharesRejected;
    public ShareRejectedReason[] sharesRejectedReasons;
    public int uptimeSeconds;
    public int asicCount;
    public int smallCoreCount;
    public String ASICModel;
    public String stratumURL;
    public String fallbackStratumURL;
    public int stratumPort;
    public int fallbackStratumPort;
    public String stratumUser;
    public String fallbackStratumUser;
    public String version;
    public String idfVersion;
    public String boardVersion;
    public String runningPartition;
    public int flipscreen; // boolean
    public int overheat_mode; //boolean
    public int overclockEnabled;//bolean
    public int invertscreen;//boolean
    public int displayTimeout;
    public int autofanspeed;//boolean
    public int fanspeed;
    public int temptarget;
    public int fanrpm;
}
