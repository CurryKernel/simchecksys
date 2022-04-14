
package ncu.edu.simchecksys.service.impl;


import ncu.edu.simchecksys.dao.SimResultDao;
import ncu.edu.simchecksys.entity.SimResult;
import ncu.edu.simchecksys.service.SimResultService;
import ncu.edu.simchecksys.util.PageBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SimResultServiceImpl implements SimResultService {
    private SimResultDao simResultDao;

    public SimResultServiceImpl(SimResultDao simResultDao) {
        this.simResultDao = simResultDao;
    }

    @Override
    public List<SimResult> getAllSimresult() {
            return simResultDao.findAll();
    }

    @Override
    public List<SimResult> getSimResultByUserName(String username) {
        return simResultDao.findByUsername(username);
    }

    @Override
    public PageBean<SimResult> getPageResult(Integer page, Integer limit) {
        HashMap<String, Object> pageMap = new HashMap<>();
        PageBean<SimResult> pageBean = new PageBean<>();

        pageBean.setCurrentPage(page);
        pageBean.setPageSize(limit);
        pageMap.put("start", (page - 1) * limit);
        pageMap.put("limit", pageBean.getPageSize());
        List<SimResult> simResultList = simResultDao.findAllByPage(pageMap);
        pageBean.setList(simResultList);
        int count = simResultDao.countAll();
        pageBean.setTotalRecord(count);
        if (count % limit == 0) {
            pageBean.setTotalPage(count / limit);
        } else {
            pageBean.setTotalPage((count / limit) + 1);
        }
        return pageBean;
    }
}
