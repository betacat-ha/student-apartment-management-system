package cn.com.betacat.services;

import cn.com.betacat.pojo.Environment;

public interface EnvironmentService {
    Environment getInfo(int apartmentId);

    boolean updateInfo(int apartmentId, Environment newEnvironment);
}
