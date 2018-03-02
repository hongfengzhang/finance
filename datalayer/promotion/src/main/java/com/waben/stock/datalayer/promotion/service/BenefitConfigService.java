package com.waben.stock.datalayer.promotion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.promotion.entity.BenefitConfig;
import com.waben.stock.datalayer.promotion.repository.BenefitConfigDao;

/**
 * 分成配置 Service
 * 
 * @author luomengan
 *
 */
@Service
public class BenefitConfigService {

	@Autowired
	private BenefitConfigDao benefitConfigDao;

	public BenefitConfig getBenefitConfigInfo(Long id) {
		return benefitConfigDao.retrieve(id);
	}

	@Transactional
	public BenefitConfig addBenefitConfig(BenefitConfig benefitConfig) {
		return benefitConfigDao.create(benefitConfig);
	}

	@Transactional
	public BenefitConfig modifyBenefitConfig(BenefitConfig benefitConfig) {
		return benefitConfigDao.update(benefitConfig);
	}

	@Transactional
	public void deleteBenefitConfig(Long id) {
		benefitConfigDao.delete(id);
	}
	
	@Transactional
	public void deleteBenefitConfigs(String ids) {
		if(ids != null) {
			String[] idArr= ids.split(",");
			for(String id : idArr) {
				if(!"".equals(id.trim())) {
					benefitConfigDao.delete(Long.parseLong(id.trim()));
				}
			}
		}
	}

	public Page<BenefitConfig> benefitConfigs(int page, int limit) {
		return benefitConfigDao.page(page, limit);
	}
	
	public List<BenefitConfig> list() {
		return benefitConfigDao.list();
	}

}
