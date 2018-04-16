package com.waben.stock.applayer.operation.service.fallback;

import java.util.List;

import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.stereotype.Component;

import com.waben.stock.applayer.operation.service.activity.ActivityMngService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.activity.ActivityDto;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 
 * @author guowei 2018/4/11
 *
 */

@Component
public class ActivityMngServiceFallback implements ActivityMngService {

	@Override
	public Response<ActivityDto> saveActivity(ActivityDto adto) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<PageInfo<ActivityDto>> getActivityList(int pageno, Integer pagesize) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<Void> setValid(long activityId) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<ActivityDto> getActivity(long activityId) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<ActivityDto> getActivityByLocation(String location) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
