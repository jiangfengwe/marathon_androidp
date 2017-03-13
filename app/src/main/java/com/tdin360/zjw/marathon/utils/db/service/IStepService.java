package com.tdin360.zjw.marathon.utils.db.service;

import com.tdin360.zjw.marathon.model.StepModel;
import java.util.List;

/**
 * 计步历史纪录表操作接口
 * Created by admin on 17/3/12.
 */

public interface IStepService {

    void addStep(StepModel model);
    void deleteStepById(int id);
    StepModel findStepById(int id);
    void deleteAll();
    boolean updateStep(StepModel model);
    List<StepModel> getAllStep();
}
