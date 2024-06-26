package cn.com.betacat.pojo;

import lombok.Data;

@Data
public class Environment {
    private int id;
    private int apartmentId;
    private String currentTemperature;
    private String settingTemperature;
    private String switchAirConditioner;
    private String switchFans;
    private String stateDoor;
}
