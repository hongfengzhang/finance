package com.waben.stock.applayer.promotion.reference.organization;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.promotion.reference.fallback.WithdrawalsApplyReferenceFallback;
import com.waben.stock.interfaces.service.organization.WithdrawalsApplyInterface;

/**
 * 提现申请 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "organization", path = "withdrawalsApply", fallback = WithdrawalsApplyReferenceFallback.class, qualifier = "withdrawalsApplyReference")
public interface WithdrawalsApplyReference extends WithdrawalsApplyInterface {

}
