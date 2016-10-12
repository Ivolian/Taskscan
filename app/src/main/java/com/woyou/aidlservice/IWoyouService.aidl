/**
* Version: 1.4
*/

package com.woyou.aidlservice;

import com.woyou.aidlservice.IWoyouCallback;
import android.graphics.Bitmap;
import android.os.Bundle;
import com.woyou.aidlservice.IWoyouReturnValuesCallback;
//import com.woyou.aidlservice.IWoyouTeleCallback;

interface IWoyouService
{

	
	/**
	* 1.1 获取不同颜色的指示灯数量, 目前为2
	*/
	long getMaxLights();
	
	/** 
	* 1.2 开灯
	* @param lightNumber:		指示灯序号, 取值从1到getMaxLights
	* @param blinkOnCycle:		0 不闪烁, 正值循环闪烁, 表示持续亮灯的毫秒数, 不能为负数
	* @param blinkOffCycle:	0 不闪烁, 正值循环闪烁, 表示持续灭灯的毫秒数, 不能为负数
	*/
	boolean switchOn(int lightNumber, int blinkOnCycle, int blinkOffCycle);
	
	/**
	* 1.3 关灯
	* @param lightNumber:		指示灯序号, 取值从1到getMaxLights
	*/	
	boolean switchOff(int lightNumber);

	//////////////////////////////////////////////////////////////////
	
	/**
	* 2.1 打开钱柜
	*/
	void openDrawer();
	
	/**
	* 2.2 取钱柜累计打开次数
	*/		
	long getOpenDrawerTimes();
	
	/////////////////////////////////////////////////////////////////
	
	/**
	* 3.1 取打印机板系列号
	*/		
	String getPrinterSerialNo();
	
	/**
	* 3.2 取打印机主板版本号
	*/
	String getPrinterVersion();	
	
	/**
	* 3.3 取打印机型号
	*/		
	String getPrinterModal();
		
	/**
	* 3.4取切刀累计切纸次数
	*/
	long getCutPaperTimes();
	
	/**
	* 3.5 取打印头打印长度
	*/
	long getPrintedLength();

	/**
	* 3.6 打印机每行最大单字节字符数
	*/
	int getPrtLineChars();
	
	/**
	* 3.7 打印机是否支持切纸
	*/	
	boolean capCutPaper();
	
	/**
	* 3.8 打印机切纸
	*/
	void cutPaper();
	
	/**
	* 3.9 打印条码
	* @param data: 		条码数据
	* @param symbology: 	条码类型
	*    0 -- UPC-A
	*    1 -- UPC-E
	*    2 -- JAN13(EAN13)
	*    3 -- JAN8(EAN8)
	*    4 -- CODE39
	*    5 -- ITF
	*    6 -- CODABAR
	*    7 -- CODE93
	*    8 -- CODE128
	* @param height: 		条码高度, 取值1到255, 默认162
	* @param width: 		条码宽度, 取值2至6, 默认2
	* @param alignment:	条码对齐方式 0--居左 , 1--居中, 2--居右
	* @param textposition:	文字位置 0--不打印文字, 1--文字在条码上方, 2--文字在条码下方, 3--条码上下方均打印
	*/
	void printBarCode(String data, int symbology, int height, int width, int alignment, int textposition);
	
	/**
	* 3.10 打印图片
	* @param bitmap: 		图片
	* @param width: 		图片宽度
	* @param alignment:	对齐方式 0--居左 , 1--居中, 2--居右
	*/
	void printBitmap(in Bitmap bitmap, int width, int alignment);

	/**
	* 3.11 打印正常大小文本
	* @param data:			打印文字
	*/
	void printNormalText(String data);
	
	/**
	* 3.12 打印倍高倍宽文本(四倍正常大小)
	* @param data:			打印文字
	*/
	void printBigText(String data);	
	
	/**
	* 3.13 打印倍宽文本(字体宽度为正常的两倍, 高度不变)
	* @param data:			打印文字
	*/
	void printDoubleWidthText(String data);	

	/**
	* 3.14 打印倍高文本(字体高度为正常的两倍, 宽度不变)
	* @param data:			打印文字
	*/
	void printDoubleHeightText(String data);	

	/**
	* 3.15 自由放大字符打印
	* @param data:   打印的数据
	* @param zoom:　  放大因子(0-7) 
	*/
	void printZoomText(String data, int zoom);
		
	/**
	* 3.16 带指令打印
	* @param data			指令
	*/
	void printRAWData(in byte[] data);
			
	/**
	* 3.17 打印机自检
	*/
	void printerSelfChecking();
		
	/**
	* 3.18 打印二维码
	* @param data:			二维码数据
	* @param modulesize:	二维码块大小(单位:点, 取值 1 至 16 )
	* @param errorlevel:	二维码纠错等级(0 至 3)
	*                0 -- 纠错级别L ( 7%)
	*                1 -- 纠错级别M (15%) 
	*                2 -- 纠错级别Q (25%) 
	*                3 -- 纠错级别H (30%) 
	* @param alignment:	二维码对齐方式 
	*				 0 -- 居左 
	*				 1 -- 居中
	*				 2 -- 居右
	*/
	void printQRCode(String data, int modulesize, int errorlevel, int alignment);
	
	////////////////////////////////////////////////////////////////////////////
	/**
	* 用于事务打印的相关打印接口,不支持极盒N900
	/
	/**
	* 4.1 开启事务打印模式
	*/
	boolean beginTransactionPrint();
	
	/**
	* 4.2 中断事务打印模式
	*/
	boolean cancelTransactionPrint();	
	
	/**
	* 4.3 结束事务打印模式, 同时将事务中的命令集打印出来
	*/
	boolean endTransactionPrint();

	/**
	* 4.4 设置对齐模式, 仅用于事务打印模式
	* @param alignment:	对齐方式 1--居左  2--居中 3--居右
	*/
	boolean setPrintAlignment(int alignment);
	
	/**
	* 4.5 设置文字的水平打印位置, 仅用于事务打印模式
	* @param pos:			开始打印文字的水平位置, 大于getPrtLineChars的POS值将被忽略
	*/
	boolean setPrintPos(int pos);	
	
	/**
	* 4.6 设置黑白反显打印模式, 仅用于事务打印模式
	* @param mode:  0 恢复正常模式  1 选择反显模式
	*/
	boolean setBlackWhiteMode(int mode);
	
	/**
	* 4.7 设置下划线打印模式, 仅用于事务打印模式
	* @param mode:  0 恢复到正常模式  1 选择下划线模式
	*/
	boolean setUnderline(int mode);
		
	/**
	* 4.8 打印机切纸, 仅用于事务打印模式
	*/
	boolean trans_cutPaper();

	
	/**
	* 4.9 打印条码, 仅用于事务打印模式
	* @param data: 		条码数据
	* @param symbology: 	条码类型
	*    0 -- UPC-A
	*    1 -- UPC-E
	*    2 -- JAN13(EAN13)
	*    3 -- JAN8(EAN8)
	*    4 -- CODE39
	*    5 -- ITF
	*    6 -- CODABAR
	*    7 -- CODE93
	*    8 -- CODE128
	* @param height: 		条码高度, 取值1到255, 默认162
	* @param width: 		条码宽度, 取值2至6, 默认2
	* @param alignment:	条码对齐方式 0--居左 , 1--居中, 2--居右
	* @param textposition:	文字位置 0--不打印文字, 1--文字在条码上方, 2--文字在条码下方, 3--条码上下方均打印
	*/
	boolean trans_printBarCode(String data, int symbology, int height, int width, int alignment, int textposition);
	
	/**
	* 4.10 打印图片, 仅用于事务打印模式
	* @param bitmap: 		图片
	* @param width: 		图片宽度
	* @param alignment:	对齐方式 0--居左 , 1--居中, 2--居右
	*/
	boolean trans_printBitmap(in Bitmap bitmap, int width, int alignment);

	/**
	* 4.11 打印正常大小文本, 仅用于事务打印模式
	* @param data:			打印文字
	*/
	boolean trans_printNormalText(String data);
	
	/**
	* 4.12 打印倍高倍宽文本(四倍正常大小), 仅用于事务打印模式
	* @param data:			打印文字
	*/
	boolean trans_printBigText(String data);	
	
	/**
	* 4.13  打印倍宽文本(字体宽度为正常的两倍, 高度不变), 仅用于事务打印模式
	* @param data:			打印文字
	*/
	boolean trans_printDoubleWidthText(String data);	

	/**
	* 4.14 打印倍高文本(字体高度为正常的两倍, 宽度不变), 仅用于事务打印模式
	* @param data:			打印文字
	*/
	boolean  trans_printDoubleHeightText(String data);	
	
	/**
	* 4.15 带指令打印, 仅用于事务打印模式
	* @param data			指令
	*/
	boolean trans_printRAWData(in byte[] data);
	
	/**
	* 4.9 自由放大字符打印, 仅用于事务打印模式
	* @param data:   打印的数据
	* @param zoom:　  放大因子(0-7) 
	*/
	boolean trans_printZoomText(String data, int zoom);
		
	/**
	* 4.16 打印二维码, 仅用于事务打印模式
	* @param data:			二维码数据
	* @param modulesize:	二维码块大小(单位:点, 取值 1 至 16 )
	* @param errorlevel:	二维码纠错等级(0 至 3)
	*                0 -- 纠错级别L ( 7%)
	*                1 -- 纠错级别M (15%) 
	*                2 -- 纠错级别Q (25%) 
	*                3 -- 纠错级别H (30%) 
	* @param alignment:	二维码对齐方式 
	*				 0 -- 居左 
	*				 1 -- 居中
	*				 2 -- 居右
	*/
	boolean trans_printQRCode(String data, int modulesize, int errorlevel, int alignment);
	
	//////////////////////////////////////////////////////////////////////////
	
	/**
	* 0.1 打开局域网调试模式
	*/
	boolean openLanDebug();
	/**
	* 0.2 关闭局域网调试模式
	*/
	boolean closeLanDebug();
	
	/**
	* 注册
	* @param cb:	实现IWoyouCallback接口的回调
	*/
	void registerCallback(IWoyouCallback cb); 
	
	/**
	* 注销回调
	* @param cb:	实现IWoyouCallback接口的回调
	*/
	 void unregisterCallback(IWoyouCallback cb);   	
	 
	//////////////////////////////////////////////////////////////////////////
	/**
	* 扫描接口, 只支持极盒N900
	*/
	
	/**
	* 5.1 初始化扫描仪
	*/
	void initScanner();
	
	/**
	* 5.2 开始扫描
	* @param timeout:  持续时间, 秒
	*/	
	void startScan(int timeout);
	
	/**
	* 5.3 结束扫描
	*/	
	void stopScan();
	
	//////////////////////////////////////////////////////////////////////////
	
	//1.2.3 add
	
		/**
	 * 打印二维码
	 * @param url		二维码内容
	 * @param size		尺寸, 取值范围 1 至 5
	 * @param align		对齐 0--居左 , 1--居中, 2--居右
	 * @return
	 */
	boolean printZQRCode(String url, int size, int align);
	
	/**
	 * 并排打印二维码
	 * @param leftUrl
	 * @param rightUrl
	 * @return
	 */
	boolean printDoubleZQRCode(String leftUrl, String rightUrl);
	
	/**
	 * 并排打印二维码及图片
	 * @param url
	 * @param bytes
	 * @return
	 */
	boolean printZQRCodeImage(String url, in Bitmap bitmap);
		
	/**
	 * 打印多个空白行
	 * @param lines
	 * @return
	 */
	boolean lineWrap(int lines);
	
	//////////////////////////////////////////////////////////////////////////
	
	//1.2.3 add 事务
	
		/**
	 * 打印二维码
	 * @param url		二维码内容
	 * @param size		尺寸, 取值范围 1 至 5
	 * @param align		对齐 0--居左 , 1--居中, 2--居右
	 * @return
	 */
	boolean trans_printZQRCode(String url, int size, int align);
	
	/**
	 * 并排打印二维码
	 * @param leftUrl
	 * @param rightUrl
	 * @return
	 */
	boolean trans_printDoubleZQRCode(String leftUrl, String rightUrl);
	
	/**
	 * 并排打印二维码及图片
	 * @param url
	 * @param bytes
	 * @return
	 */
	boolean trans_printZQRCodeImage(String url, in Bitmap bitmap);
	
	
	/**
	 * 打印多个空白行
	 * @param lines
	 * @return
	 */
	boolean trans_lineWrap(int lines);	 
	
	/** 
	* 第二种开灯方式
	* @param lightNumber:		指示灯序号, 取值从1到getMaxLights
	* @param blinkOnCycle:		0 不闪烁, 正值循环闪烁, 表示持续亮灯的毫秒数, 不能为负数
	* @param blinkOffCycle:	0 不闪烁, 正值循环闪烁, 表示持续灭灯的毫秒数, 不能为负数
	* @param times:			循环次数, 在闪烁情况下, 此参数有效, 0或负数为无限循环, 正数为循环次数
	*/
	boolean switchOnTimes(int lightNumber, int blinkOnCycle, int blinkOffCycle, int times);	
	
	/**
	* 列对齐方式打印
	* @param colsText	各列文本数据
	* @param colsWidth 各列宽度(以英文字符计算, 每个中文字符占两个英文字符, 每个宽度大于0, 总宽度不大于getPrtLineChars)
	* @param colsAlign	各列对齐方式(0居左, 1居中, 2居右)
	* 备注: 三个参数的数组长度应该一致, 如果colsText[i]的宽度大于colsWidth[i], 则文本换行
	*/
	boolean printColumnsText(in String[] colsText, in int[] colsWidth, in int[] colsAlign);
	
	/**
	* 列对齐方式打印, 仅用于事务打印模式
	* @param colsText	各列文本数据
	* @param colsWidth 各列宽度(以英文字符计算, 每个中文字符占两个英文字符, 每个宽度大于0, 总宽度不大于getPrtLineChars)
	* @param colsAlign	各列对齐方式(0居左, 1居中, 2居右)
	* 备注: 三个参数的数组长度应该一致, 如果colsText[i]的宽度大于colsWidth[i], 则文本换行
	*/
	boolean trans_printColumnsText(in String[] colsText, in int[] colsWidth, in int[] colsAlign);
	
	/**
	* 取WoyouService服务版本
	*/
	String getServiceVersion();	
	
	/**
	* 查询切刀切纸次数
	* @param callback   查询结果通过callback返回
	*/
	boolean getCutPaperTimesCallback(in IWoyouReturnValuesCallback callback);
	
	/**
	* 查询打开钱箱次数
	* @param callback   查询结果通过callback返回
	*/
	boolean getOpenDrawerTimesCallback(in IWoyouReturnValuesCallback callback);
	
	/**
	* 查询打印长度
	* @param callback   查询结果通过callback返回
	*/
	boolean getPrintedLengthCallback(in IWoyouReturnValuesCallback callback);	
	
	/**
	* 查询打印板型号
	* @param callback   查询结果通过callback返回
	*/
	boolean getPrinterModelCallback(in IWoyouReturnValuesCallback callback);
	
	/**
	* 查询打印板设备系列号
	* @param callback   查询结果通过callback返回
	*/
	boolean getPrinterSerialNoCallback(in IWoyouReturnValuesCallback callback);
	
	/**
	* 查询打印板版本号
	* @param callback   查询结果通过callback返回
	*/
	boolean getPrinterVersionCallback(in IWoyouReturnValuesCallback callback);
	
	void directIN(in byte[] data);
	
	void directOUT(out Bundle params, int timeout);
	
	/**
	* 同一行内打印不同大小的文本
	* @param texts	  各部分文本数据
	* @param sizes  各部分文本的大小(字体高宽有一个字节表示，高4位是宽度放大倍数，低4位是高度放大倍数，取值均为0至7，0为标准大小，7是8倍标准大小)
	*        如0x12,0x33,0x40,0x77分别代表2倍宽3倍高，4倍宽4倍高，5倍宽1倍高，8倍宽8倍高。组合起来共有64种大小变化。
	* 备注：    同一行字符的放大倍数不同时，所有的字符以底线对齐。尺寸设置如果超出上述范围，该尺寸被忽略。
	*        两参数的数组长度应该一致
	*/
	boolean printSizesText(in String[] texts, in byte[] sizes);
	
	/**
	* 事务打印：同一行内打印不同大小的文本
	* @param texts	  各部分文本数据
	* @param sizes  各部分文本的大小(字体高宽有一个字节表示，高4位是宽度放大倍数，低4位是高度放大倍数，取值均为0至7，0为标准大小，7是8倍标准大小)
	*        如0x12,0x33,0x40,0x77分别代表2倍宽3倍高，4倍宽4倍高，5倍宽1倍高，8倍宽8倍高。组合起来共有64种大小变化。
	* 备注：    同一行字符的放大倍数不同时，所有的字符以底线对齐。尺寸设置如果超出上述范围，该尺寸被忽略。
	*        两参数的数组长度应该一致
	*/
	boolean trans_printSizesText(in String[] texts, in byte[] sizes);
		
	//Version 1.5.0
		
	/**
	* 并排打印二维码(两个二维码必须采用相同的modulesize/errorlevel/alignment)
	* @param code1:		二维码数据
	* @param code2:		二维码数据
	* @param modulesize:	二维码块大小(单位:点, 取值 1 至 16 )
	* @param errorlevel:	二维码纠错等级(0 至 3)
	*                0 -- 纠错级别L ( 7%)
	*                1 -- 纠错级别M (15%) 
	*                2 -- 纠错级别Q (25%) 
	*                3 -- 纠错级别H (30%) 
	* @param alignment:	二维码对齐方式 
	*				 0 -- 居左 
	*				 1 -- 居中
	*				 2 -- 居右
	*/
	boolean printDoubleQRCode(String code1, String code2, int modulesize, int errorlevel, int alignment);

	/**
	* 事务打印：并排打印二维码(两个二维码必须采用相同的modulesize/errorlevel/alignment)
	* @param code1:		二维码数据
	* @param code2:		二维码数据
	* @param modulesize:	二维码块大小(单位:点, 取值 1 至 16 )
	* @param errorlevel:	二维码纠错等级(0 至 3)
	*                0 -- 纠错级别L ( 7%)
	*                1 -- 纠错级别M (15%) 
	*                2 -- 纠错级别Q (25%) 
	*                3 -- 纠错级别H (30%) 
	* @param alignment:	二维码对齐方式 
	*				 0 -- 居左 
	*				 1 -- 居中
	*				 2 -- 居右
	*/
	boolean trans_printDoubleQRCode(String code1, String code2, int modulesize, int errorlevel, int alignment);	

	//客显接口
	
	/**
	* 选择客显类型，如果使用USB客显应将类型设置为USB，如果使用串口客显应将类型设置为串口(串口接口看上去像四芯的电话插头)
	* @param type   
	* 串口客显:CDType.SERIAL_PORT
	* USB客显:CDType.USB
	*/
	boolean setCDType(int type);
		
	/**
	* 客显显示文本
	* @param text	         要显示的文本
	* @param xPos      在屏中显示水平起始位置(取值范围0至480)
	* @param yPos      在屏中显示垂直起始位置(取值范围0至272)
	* @param textColor    文字颜色(RGB)
	* @param bgColor    背景颜色(RGB)
	* @param size      文字大小(取值：0小字体；1大字体)
	*/
	boolean showCDText(String text, int xPos, int yPos, long textColor, long bgColor, int size);
	
	/**
	* 显示客显TF卡根目录picture文件夹下的图片，仅支持bmp和jpg两种图片格式
	* @param fileName   图片文件名， 不带路径
	* @param xPos      在屏中显示水平起始位置(取值范围0至480)
	* @param yPos      在屏中显示垂直起始位置(取值范围0至272)
	* @param width     屏中显示区域宽度(xPos + width <= 480)
	* @param height    屏中显示区域高度(yPos + height <= 272)
	*/
	boolean showCDImage(String fileName, int xPos, int yPos, int width, int height);
	
	/**
	* 将输入的字符串转换成二维码并显示
	* @param qrcode   二维码数据
	* @param level    二维码纠错级别(取值范围0至3)
	*/
	boolean showCDQRCode(String qrcode, int level);
	
	
	/**
	* 向客显发送指令
	* @param command    指令字节
	*/
	boolean sendCommandToCD(in byte[] command);
	
	/**
	* 设置背景颜色(首先要调用setCDScreenType切换到自定义界面)
	* @param color	颜色值
	*/
	boolean setCDBgColor(long color);
	
	/**
	* 切换屏幕画面模式
	* @param mode	 
	* 时间显示界面(默认界面):CDScreenType.DEFAULT
	* 内置收银界面(可显示金额与商品名称):CDScreenType.COLLECT_MONEY
	* 用户自定义界面(可显示二维码、图片、文字):CDScreenType.CUSTOM
	* @return true or false
	*/
	boolean setCDScreenType(int mode);
	
	/**
	 * 该命令只在收银模式界面有效
	 * 显示"单价"、"总价"、"收款"、"找零"字符和金额
	 * @param type
	 * 	当 type=Cashier.DEFAULT，不显示状态字符。
	 * 	当 type=Cashier.UNIT_PRICE，显示“单价”字符。
	 * 	当 type=Cashier.TOTAL，显示“总价”字符。
	 * 	当 type=Cashier.RECEIVE，显示“收款”字符。
	 * 	当 type=Cashier.CHANGE，显示“找零”字符。
	 * @param moneyStr 金额字符串(仅支持阿拉伯数字0-9或小数点或-号)
	 * @return true or false
	 */
	boolean showCDMoney(int type, String moneyStr);
	
	
	/**
	 * 该命令只在收银模式界面有效
	 * 用于在收银界面中显示商品名称
	 * @param name 商品名称
	 * @return true or false
	 */
	boolean showCDProductName(String name);
	
	
	/**
	 * 设置初始界面的时间
	 * 格式:yyyy-MM-dd HH:mm:ss
	 * @param date 日期字符串
	 * @return true or false
	 */
	boolean setCDDateByStr(String date);
	
	/**
	 * 设置初始界面的时间
	 * @return true or false
	 */
	boolean setCDDate(int year, int month, int day, int hour, int minute, int second);
	
	/**
	 * 设置初始界面的标题(需要重启客显后才能生效)
	 * @param title 标题
	 * @return true or false
	 */
	boolean setCDTitle(String title);
	
	//未开发
	/**
	* 注册来电侦听
	* cb:	实现IWoyouTeleCallback接口的回调
	*/
	//void registerTeleCallback(IWoyouTeleCallback cb); 
	
	/**
	* 反注册来电侦听
	* cb:	实现IWoyouTeleCallback接口的回调
	*/
	 //void unregisterTeleCallback(IWoyouTeleCallback cb);   	
		
}