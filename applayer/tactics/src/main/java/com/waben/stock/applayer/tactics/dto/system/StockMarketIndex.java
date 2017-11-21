package com.waben.stock.applayer.tactics.dto.system;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 股票市场指数
 * 
 * @author luomengan
 *
 */
public class StockMarketIndex {

	/**
	 * 上证指数
	 */
	private BigDecimal szzs;
	/**
	 * 上证指数相对于上一天收盘价的波动
	 */
	private BigDecimal szzsWave;
	/**
	 * 上证指数相对于上一天收盘价的波动比例
	 */
	private BigDecimal szzsWaveRatio;
	/**
	 * 深圳成指
	 */
	private BigDecimal szcz;
	/**
	 * 深圳成指相对于上一天收盘价的波动
	 */
	private BigDecimal szczWave;
	/**
	 * 深圳成指相对于上一天收盘价的波动比例
	 */
	private BigDecimal szczWaveRatio;
	/**
	 * 创业板指
	 */
	private BigDecimal cybz;

	/**
	 * 创业板指相对于上一天收盘价的波动
	 */
	private BigDecimal cybzWave;
	/**
	 * 创业板指相对于上一天收盘价的波动比例
	 */
	private BigDecimal cybzWaveRatio;

	public void setSzzs(BigDecimal szzs, BigDecimal szzsBefore) {
		this.szzs = szzs;
		this.szzsWave = szzs.subtract(szzsBefore);
		this.szczWaveRatio = szzs.subtract(szzsBefore).multiply(new BigDecimal(100)).divide(szzsBefore, 2,
				RoundingMode.HALF_UP);
	}

	public void setSzcz(BigDecimal szcz, BigDecimal szczBefore) {
		this.szcz = szcz;
		this.szczWave = szcz.subtract(szczBefore);
		this.szczWaveRatio = szcz.subtract(szczBefore).multiply(new BigDecimal(100)).divide(szczBefore, 2,
				RoundingMode.HALF_UP);
	}

	public void setCybz(BigDecimal cybz, BigDecimal cybzBefore) {
		this.cybz = cybz;
		this.cybzWave = cybz.subtract(cybzBefore);
		this.cybzWaveRatio = cybz.subtract(cybzBefore).multiply(new BigDecimal(100)).divide(cybzBefore, 2,
				RoundingMode.HALF_UP);
	}

	public BigDecimal getSzzs() {
		return szzs;
	}

	public void setSzzs(BigDecimal szzs) {
		this.szzs = szzs;
	}

	public BigDecimal getSzzsWave() {
		return szzsWave;
	}

	public void setSzzsWave(BigDecimal szzsWave) {
		this.szzsWave = szzsWave;
	}

	public BigDecimal getSzzsWaveRatio() {
		return szzsWaveRatio;
	}

	public void setSzzsWaveRatio(BigDecimal szzsWaveRatio) {
		this.szzsWaveRatio = szzsWaveRatio;
	}

	public BigDecimal getSzcz() {
		return szcz;
	}

	public void setSzcz(BigDecimal szcz) {
		this.szcz = szcz;
	}

	public BigDecimal getSzczWave() {
		return szczWave;
	}

	public void setSzczWave(BigDecimal szczWave) {
		this.szczWave = szczWave;
	}

	public BigDecimal getSzczWaveRatio() {
		return szczWaveRatio;
	}

	public void setSzczWaveRatio(BigDecimal szczWaveRatio) {
		this.szczWaveRatio = szczWaveRatio;
	}

	public BigDecimal getCybz() {
		return cybz;
	}

	public void setCybz(BigDecimal cybz) {
		this.cybz = cybz;
	}

	public BigDecimal getCybzWave() {
		return cybzWave;
	}

	public void setCybzWave(BigDecimal cybzWave) {
		this.cybzWave = cybzWave;
	}

	public BigDecimal getCybzWaveRatio() {
		return cybzWaveRatio;
	}

	public void setCybzWaveRatio(BigDecimal cybzWaveRatio) {
		this.cybzWaveRatio = cybzWaveRatio;
	}

}
