package cn.com.betacat.services.impl;

import cn.com.betacat.dao.EnvironmentDao;
import cn.com.betacat.pojo.Environment;
import cn.com.betacat.pojo.Usage;
import cn.com.betacat.services.EnvironmentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EnvironmentServiceImpl implements EnvironmentService {
    @Autowired
    private EnvironmentDao environmentDao;

    @Override
    public Environment getInfo(int apartmentId) {
        return environmentDao.selectByApartmentId(apartmentId);
    }

    @Override
    public boolean updateInfo(int apartmentId, Environment newEnvironment) {
        Environment currentEnvironment = getInfo(apartmentId);

        // 回写数据
        if (newEnvironment.getSettingTemperature() == null || newEnvironment.getSettingTemperature().isEmpty()) {
            currentEnvironment.setSettingTemperature(newEnvironment.getSettingTemperature());
        }

        if (newEnvironment.getSwitchFans() == null || newEnvironment.getSwitchFans().isEmpty()) {
            currentEnvironment.setSwitchFans(newEnvironment.getSwitchFans());
        }

        if (newEnvironment.getSwitchAirConditioner() == null || newEnvironment.getSwitchAirConditioner().isEmpty()) {
            currentEnvironment.setSwitchAirConditioner(newEnvironment.getSwitchAirConditioner());
        }

        if (newEnvironment.getStateDoor() == null || newEnvironment.getStateDoor().isEmpty()) {
            currentEnvironment.setStateDoor(newEnvironment.getStateDoor());
        }
        environmentDao.updateById(currentEnvironment);

        return environmentDao.updateById(currentEnvironment) == 1;
    }
}
