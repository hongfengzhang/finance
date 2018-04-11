package com.waben.stock.interfaces.service.activity;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waben.stock.interfaces.dto.activity.ActivityDto;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 
 * 
 * @author guowei 2018/4/10
 *
 */
public interface ActivityMngInterface {
	
	/**
	 * 保存活动
	 * @param adto
	 * @return
	 */
	@RequestMapping(value = "/saveActivity", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<ActivityDto> saveActivity(@RequestBody ActivityDto adto);
	
	/**
	 * 获取活动列表
	 * @param pageno
	 * @param pagesize
	 * @return
	 */
	@RequestMapping(value = "/getActivityList", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<List<ActivityDto>> getActivityList(@RequestParam int pageno,@RequestParam Integer pagesize);
	
	/**
	 * 设置活动生效状态
	 * @param activityId
	 * @return
	 */
	@RequestMapping(value = "/setValid/{activityId}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<Void> setValid(@PathVariable long activityId);
	
	
	/**
	 * 根据Id获取活动信息
	 * @param activityId
	 * @return
	 */
	@RequestMapping(value = "/getActivity/{activityId}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<ActivityDto> getActivity(@PathVariable long activityId);
}
