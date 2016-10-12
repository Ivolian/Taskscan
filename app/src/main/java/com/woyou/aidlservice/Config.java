package com.woyou.aidlservice;

/** 
 * 函数配置选项
 */
public final class Config {

	/**
	 * 客显类型
	 */
	public final static class CDType{
		/**
		 * USB客显
		 */
		public final static int USB = 1;
		
		/**
		 * 串口客显
		 */
		public final static int SERIAL_PORT = 0;
	}
	
	/**
	 * 客显界面模式
	 */
	public final static class CDScreenType{
		
		/**
		 * 时间显示模式(默认界面)
		 */
		public final static int DEFAULT = 0;
		
		/**
		 * 内置收银界面(可显示金额与商品名称)
		 */
		public final static int COLLECT_MONEY = 1;
		
		/**
		 * 用户自定义界面(可显示二维码、图片、文字)
		 */
		public final static int CUSTOM = 2;
	}
	
	/**
	 * 收银台:单价、总计、收款、找零
	 */
	public final static class Cashier{
		
		/**
		 * 默认界面，不显示收银方式
		 */
		public final static int DEFAULT = 0;
		
		/**
		 * 单价
		 */
		public final static int UNIT_PRICE = 1;
		
		/**
		 * 总计
		 */
		public final static int TOTAL = 2;
		
		/**
		 * 收款
		 */
		public final static int RECEIVE = 3;
		
		/**
		 * 找零
		 */
		public final static int CHANGE = 4;
	}
}
