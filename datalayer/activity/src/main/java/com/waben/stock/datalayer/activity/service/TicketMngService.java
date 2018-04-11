package com.waben.stock.datalayer.activity.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.activity.entity.TicketAmount;
import com.waben.stock.datalayer.activity.repository.TicketDao;
import com.waben.stock.interfaces.dto.activity.TicketAmountDto;
import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;

@Service
public class TicketMngService {
	
	@Autowired
	private TicketDao td;
	
	@Transactional
	public TicketAmountDto saveTicket(TicketAmountDto t) {
		TicketAmount a = CopyBeanUtils.copyBeanProperties(TicketAmount.class, t, false);
		td.saveTicket(a);
		return CopyBeanUtils.copyBeanProperties(TicketAmountDto.class, a, false);
	}

	
	public List<TicketAmountDto> getTicketList(int pageno, Integer pagesize) {
		if(pagesize == null){
			PageAndSortQuery pq = new PageAndSortQuery();
			pagesize = pq.getSize();
		}
		List<TicketAmount> li = td.getTicketList(pageno, pagesize);
		List<TicketAmountDto> atolist = new ArrayList<>();
		if(li != null){
			for(TicketAmount a : li){
				TicketAmountDto ad  = CopyBeanUtils.copyBeanProperties(TicketAmountDto.class, a, false);
				atolist.add(ad);
			}
		}
		return atolist;
	}

	@Transactional
	public void deleteTicket(long ticketId) {
		td.deleteTicket(ticketId);
	}
}
