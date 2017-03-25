package com.tdin360.zjw.marathon.utils.db.service;

import com.tdin360.zjw.marathon.model.StepModel;
import java.util.List;

/**
 * 计步历史纪录表操作接口
 * Created by admin on 17/3/12.
 */

public interface StepService {

    void addStep(StepModel model);
    boolean deleteStepById(int id);
    StepModel findStepById(int id);
    boolean deleteAll();
    boolean updateStep(StepModel model);
    List<StepModel> getAllStep();
}
