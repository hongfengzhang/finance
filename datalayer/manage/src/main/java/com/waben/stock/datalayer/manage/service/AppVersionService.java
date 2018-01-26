package com.waben.stock.datalayer.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.manage.entity.AppVersion;
import com.waben.stock.datalayer.manage.repository.AppVersionDao;

/**
 * app版本 Service
 * 
 * @author luomengan
 *
 */
@Service
public class AppVersionService {

	@Autowired
	private AppVersionDao dao;

	public AppVersion getCurrentAppVersion() {
		List<AppVersion> versionList = dao.list();
		if (versionList != null && versionList.size() > 0) {
			return versionList.get(0);
		}
		return null;
	}

}
