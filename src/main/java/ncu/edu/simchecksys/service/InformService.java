/*
 * Copyright (c) mokeeqian 2021.
 * Bugs and suggestions please contact me via mokeeqian@gmail.com
 */

package ncu.edu.simchecksys.service;

import ncu.edu.simchecksys.entity.Inform;
import ncu.edu.simchecksys.util.PageBean;

import java.util.List;

public interface InformService {
	Inform selectInform(Integer id);
	PageBean<Inform> selectInforms(String publisher, int page, int limit);
	PageBean<Inform> selectAllInforms(int currentPage);
	PageBean<Inform> selectInformsBySort(Integer type, int currentPage, int limit, String ... department);
	Integer addInforms(List<Inform> list);
	Integer updateInform(Inform inform);
	Integer deleteInforms(List<Integer> ids);
}
