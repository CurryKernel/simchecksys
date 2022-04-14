/*
 * Copyright (c) mokeeqian 2021.
 * Bugs and suggestions please contact me via mokeeqian@gmail.com
 */

package ncu.edu.simchecksys.service;



import ncu.edu.simchecksys.entity.SimResult;
import ncu.edu.simchecksys.util.PageBean;

import java.util.List;

public interface SimResultService {
    List<SimResult> getAllSimresult();
    List<SimResult> getSimResultByUserName(String username);
    PageBean<SimResult> getPageResult(Integer page, Integer limit);
}
