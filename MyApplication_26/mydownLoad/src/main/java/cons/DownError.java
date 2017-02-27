package cons;

/**
 * 下载错误状态类
 * Created by Administrator on 2015/10/31.
 */
public final class DownError {

	/** （开始下载）无网络 */
	public final static int NOT_NETWORK_START = 1;
	
	/** 下载的url不能为空 */
	public final static int URL_NOT_NULL = 2;
	
	/** 任务已在下载中 */
	public final static int TASK_DOWNNING = 3;

	/** 建立下载连接失败 */
	public final static int CONNECT_FAILED = 4;

	/** 文件不存在，无法恢复下载，需重新下载 */
	public final static int FILE_NOT_EXISTS = 5;

	/** 任务并未开始下载，无法继续下载 */
	public final static int TASK_NOT_START_DOWNLOAD = 6;

	/** 超出最大下载任务数 */
	public final static int TASK_OUT_MAX = 7;
	
	/** 任务异常，暂停下载 */
	public final static int TASK_EXCEPTION = 8;
	
	/** （继续下载）无网络 */
	public final static int NOT_NETWORK_RESUME = 9;

}
