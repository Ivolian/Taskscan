/**
* Version: 1.2
*/

package com.woyou.aidlservice;

interface IWoyouCallback {
	/**
	* 返回值, 如取打印机主板系列号, 由于是串口读取操作, 耗时较多, 为了不影响调用主进程, 采用得到结果后回调
	*/
	void onReturnValue(String val);
	
	/**
	* 硬件如果有故障, 自动回调主进程, 传入错误代码及错误描述
	* msg: 结果信息
	*/
	void  onRaiseException(int code, String e);
	
	/**
	* 硬件指令操作成功回调函数
	* msg: 结果描述
	*/
	void onSuccessOperation(String msg);
}