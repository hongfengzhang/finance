package com.waben.stock.datalayer.promotion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.promotion.entity.Agent;
import com.waben.stock.datalayer.promotion.repository.AgentDao;

/**
 * 经纪人 Service
 * 
 * @author luomengan
 *
 */
@Service
public class AgentService {

	@Autowired
	private AgentDao agentDao;

	public Agent getAgentInfo(Long id) {
		return agentDao.retrieve(id);
	}

	@Transactional
	public Agent addAgent(Agent agent) {
		return agentDao.create(agent);
	}

	@Transactional
	public Agent modifyAgent(Agent agent) {
		return agentDao.update(agent);
	}

	@Transactional
	public void deleteAgent(Long id) {
		agentDao.delete(id);
	}
	
	@Transactional
	public void deleteAgents(String ids) {
		if(ids != null) {
			String[] idArr= ids.split(",");
			for(String id : idArr) {
				if(!"".equals(id.trim())) {
					agentDao.delete(Long.parseLong(id.trim()));
				}
			}
		}
	}

	public Page<Agent> agents(int page, int limit) {
		return agentDao.page(page, limit);
	}
	
	public List<Agent> list() {
		return agentDao.list();
	}

}
